/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.internal.loader.enricher;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;
import static org.mule.runtime.api.meta.model.parameter.ParameterRole.BEHAVIOUR;
import static org.mule.runtime.extension.api.ExtensionConstants.STREAMING_STRATEGY_PARAMETER_DESCRIPTION;
import static org.mule.runtime.extension.api.ExtensionConstants.STREAMING_STRATEGY_PARAMETER_NAME;
import static org.mule.runtime.extension.api.annotation.param.display.Placement.ADVANCED_TAB;
import static org.mule.runtime.extension.api.util.XmlModelUtils.MULE_ABSTRACT_BYTE_STREAMING_STRATEGY_QNAME;
import static org.mule.runtime.extension.api.util.XmlModelUtils.MULE_ABSTRACT_OBJECT_STREAMING_STRATEGY_QNAME;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.ParameterDslConfiguration;
import org.mule.runtime.api.meta.model.declaration.fluent.ComponentDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclaration;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.extension.api.declaration.fluent.util.IdempotentDeclarationWalker;
import org.mule.runtime.extension.api.declaration.type.StreamingStrategyTypeBuilder;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.internal.property.PagedOperationModelProperty;
import org.mule.runtime.extension.internal.property.QNameModelProperty;

import java.io.InputStream;

import javax.xml.namespace.QName;

/**
 * Adds infrastructure parameters to sources and operations which returns {@link InputStream}
 * objects.
 *
 * @since 1.0
 */
public class StreamingDeclarationEnricher extends InfrastructureDeclarationEnricher {

  @Override
  public void enrich(ExtensionLoadingContext extensionLoadingContext) {
    new IdempotentDeclarationWalker() {

      @Override
      protected void onOperation(OperationDeclaration declaration) {
        enrich(declaration);
      }

      @Override
      protected void onSource(SourceDeclaration declaration) {
        enrich(declaration);
      }
    }.walk(extensionLoadingContext.getExtensionDeclarer().getDeclaration());
  }

  private void enrich(ComponentDeclaration declaration) {
    if (declaration.isSupportsStreaming()) {
      MetadataType type;
      QName qName;
      if (declaration.getModelProperty(PagedOperationModelProperty.class).isPresent()) {
        type = new StreamingStrategyTypeBuilder().getObjectStreamingStrategyType();
        qName = MULE_ABSTRACT_OBJECT_STREAMING_STRATEGY_QNAME;
      } else {
        type = new StreamingStrategyTypeBuilder().getByteStreamingStrategyType();
        qName = MULE_ABSTRACT_BYTE_STREAMING_STRATEGY_QNAME;
      }

      addStreamingParameter(declaration, type, qName);
    }
  }

  private ParameterDeclaration addStreamingParameter(ComponentDeclaration declaration,
                                                     MetadataType type,
                                                     QName qName) {
    ParameterDeclaration parameter = new ParameterDeclaration(STREAMING_STRATEGY_PARAMETER_NAME);
    parameter.setDescription(STREAMING_STRATEGY_PARAMETER_DESCRIPTION);
    parameter.setExpressionSupport(NOT_SUPPORTED);
    parameter.setRequired(false);
    parameter.setParameterRole(BEHAVIOUR);
    parameter.setType(type, false);
    parameter.setLayoutModel(LayoutModel.builder().tabName(ADVANCED_TAB).build());
    parameter.setDslConfiguration(ParameterDslConfiguration.builder()
        .allowsInlineDefinition(true)
        .allowsReferences(false)
        .allowTopLevelDefinition(false)
        .build());
    parameter.addModelProperty(new QNameModelProperty(qName));
    markAsInfrastructure(parameter, 2);

    declaration.getParameterGroup(DEFAULT_GROUP_NAME).addParameter(parameter);

    return parameter;
  }
}
