/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.declaration.type;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.extension.api.declaration.type.annotation.StereotypeTypeAnnotation.fromDefinitions;
import org.mule.metadata.api.annotation.TypeAliasAnnotation;
import org.mule.metadata.api.annotation.TypeAnnotation;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.TypeBuilder;
import org.mule.metadata.api.builder.WithAnnotation;
import org.mule.metadata.java.api.handler.ObjectFieldHandler;
import org.mule.metadata.java.api.handler.ObjectHandler;
import org.mule.metadata.java.api.handler.TypeHandlerManager;
import org.mule.metadata.java.api.utils.ParsingContext;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Extensible;
import org.mule.runtime.extension.api.annotation.dsl.xml.TypeDsl;
import org.mule.runtime.extension.api.annotation.param.stereotype.Stereotype;
import org.mule.runtime.extension.api.declaration.type.annotation.*;
import org.mule.runtime.extension.api.runtime.parameter.Literal;
import org.mule.runtime.extension.api.runtime.parameter.ParameterResolver;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An implementation of {@link ObjectHandler} which allows the type to me enriched with custom
 * type annotations of the Extensions API.
 *
 * @since 1.0
 */
public class ExtensionObjectTypeHandler extends ObjectHandler {

  private final ParameterResolverTypeAnnotation parameterResolverTypeAnnotation = new ParameterResolverTypeAnnotation();
  private final LiteralTypeAnnotation literalTypeAnnotation = new LiteralTypeAnnotation();
  private final TypedValueTypeAnnotation typedValueTypeAnnotation = new TypedValueTypeAnnotation();

  private final Map<Class<?>, ParsingContext> wrappedTypesContexts;

  public ExtensionObjectTypeHandler(ObjectFieldHandler fieldHandler) {
    super(fieldHandler);
    wrappedTypesContexts = ImmutableMap.<Class<?>, ParsingContext>builder()
        .put(ParameterResolver.class, new ParsingContext())
        .put(TypedValue.class, new ParsingContext())
        .put(Literal.class, new ParsingContext())
        .build();
  }

  @Override
  public TypeBuilder<?> handleClass(Class<?> clazz, List<Type> genericTypes, TypeHandlerManager typeHandlerManager,
                                    ParsingContext context, BaseTypeBuilder baseTypeBuilder) {
    TypeBuilder typeBuilder = baseTypeBuilder;
    Class<?> currentClass = clazz;

    if (ParameterResolver.class.isAssignableFrom(clazz)) {
      handleGenericType(clazz, genericTypes, typeHandlerManager, wrappedTypesContexts.get(ParameterResolver.class),
                        baseTypeBuilder, parameterResolverTypeAnnotation);
      currentClass = getGenericClass(genericTypes, 0);
    } else if (TypedValue.class.isAssignableFrom(clazz)) {
      handleGenericType(clazz, genericTypes, typeHandlerManager, wrappedTypesContexts.get(TypedValue.class),
                        baseTypeBuilder, typedValueTypeAnnotation);
      currentClass = getGenericClass(genericTypes, 0);
    } else if (Literal.class.isAssignableFrom(clazz)) {
      handleGenericType(clazz, genericTypes, typeHandlerManager, wrappedTypesContexts.get(Literal.class),
                        baseTypeBuilder, literalTypeAnnotation);
      currentClass = getGenericClass(genericTypes, 0);
    } else {
      typeBuilder = super.handleClass(currentClass, genericTypes, typeHandlerManager, context, baseTypeBuilder);
    }

    if (typeBuilder != null && typeBuilder instanceof WithAnnotation) {
      final WithAnnotation annotatedBuilder = (WithAnnotation) typeBuilder;
      if (currentClass.isAnnotationPresent(Extensible.class)) {
        annotatedBuilder.with(new ExtensibleTypeAnnotation());
      }

      TypeDsl typeDsl = currentClass.getAnnotation(TypeDsl.class);
      if (typeDsl != null) {
        annotatedBuilder.with(new TypeDslAnnotation(typeDsl.allowInlineDefinition(),
                                                    typeDsl.allowTopLevelDefinition(),
                                                    typeDsl.substitutionGroup(),
                                                    typeDsl.baseType()));
      }

      Alias alias = currentClass.getAnnotation(Alias.class);
      annotatedBuilder.with(new TypeAliasAnnotation(alias != null ? alias.value() : currentClass.getSimpleName()));

      Stereotype stereotype = currentClass.getAnnotation(Stereotype.class);
      if (stereotype != null) {
        annotatedBuilder.with(fromDefinitions(singletonList(stereotype.value())));
      }
    }
    return typeBuilder;
  }

  private Class<?> getGenericClass(List<Type> genericTypes, int position) {
    Type type = genericTypes.get(position);
    return type instanceof Class ? (Class<?>) type : Object.class;
  }

  private void handleGenericType(Class<?> clazz, List<Type> genericTypes, TypeHandlerManager typeHandlerManager,
                                 ParsingContext context,
                                 BaseTypeBuilder typeBuilder, TypeAnnotation annotation) {
    checkArgument(!genericTypes.isEmpty(), format("Type %s doesn't have the required generic type", clazz));
    TypeBuilder handle = typeHandlerManager.handle(genericTypes.get(0), context, typeBuilder);
    if (handle instanceof WithAnnotation) {
      ((WithAnnotation) handle).with(annotation);
    }
  }
}
