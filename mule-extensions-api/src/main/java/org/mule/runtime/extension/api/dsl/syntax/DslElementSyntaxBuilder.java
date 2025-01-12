/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.extension.api.dsl.syntax;

import org.mule.metadata.api.model.MetadataType;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the builder design pattern to create instances of {@link DslElementSyntax}
 *
 * @since 1.0
 */
public final class DslElementSyntaxBuilder {

  private String attributeName = "";
  private String elementName = "";
  private String prefix = "";
  private String namespace = "";
  private boolean isWrapped = false;
  private boolean supportsAttributeDeclaration = true;
  private boolean supportsChildDeclaration = false;
  private boolean supportsTopLevelDeclaration = false;
  private boolean requiresConfig = false;
  private Map<MetadataType, DslElementSyntax> genericChilds = new HashMap<>();
  private Map<String, DslElementSyntax> containedElements = new HashMap<>();

  private DslElementSyntaxBuilder() {}

  /**
   * @return a new instance of {@link DslElementSyntaxBuilder}
   */
  public static DslElementSyntaxBuilder create() {
    return new DslElementSyntaxBuilder();
  }

  /**
   * Adds a {@code name} that describes how this element will be represented as an attribute.
   *
   * @return {@code this} builder instance enriched with the {@code attributeName}
   */
  public DslElementSyntaxBuilder withAttributeName(String attributeName) {
    this.attributeName = attributeName;
    return this;
  }

  /**
   * Adds a {@code name} to the element being declared
   *
   * @return {@code this} builder instance enriched with the {@code elementName}
   */
  public DslElementSyntaxBuilder withElementName(String elementName) {
    this.elementName = elementName;
    return this;
  }

  /**
   * Adds a {@code prefix} to the element being declared
   *
   * @return {@code this} builder instance enriched with the {@code prefix}
   */
  public DslElementSyntaxBuilder withNamespace(String prefix, String namespace) {
    this.prefix = prefix;
    this.namespace = namespace;
    return this;
  }

  /**
   * Declares whether or not {@code this} {@link DslElementSyntax} is a wrapped element.
   *
   * @return {@code this} builder instance enriched with the {@code isWrapped}
   */
  public DslElementSyntaxBuilder asWrappedElement(boolean isWrapped) {
    this.isWrapped = isWrapped;
    return this;
  }

  /**
   * Declares whether or not {@code this} {@link DslElementSyntax} supports to be declared as
   * an attribute in the context for which it was created.
   *
   * @return {@code this} builder instance enriched with {@code supportsAttributeDeclaration}
   */
  public DslElementSyntaxBuilder supportsAttributeDeclaration(boolean supportsAttribute) {
    this.supportsAttributeDeclaration = supportsAttribute;
    return this;
  }

  /**
   * Declares whether or not {@code this} {@link DslElementSyntax} supports to be declared as child element
   * in the context for which it was created.
   *
   * @return {@code this} builder instance enriched with {@code supportsChildDeclaration}
   */
  public DslElementSyntaxBuilder supportsChildDeclaration(boolean supportsChild) {
    this.supportsChildDeclaration = supportsChild;
    return this;
  }

  /**
   * Declares whether or not {@code this} {@link DslElementSyntax} supports to be declared as top level element
   * in the context for which it was created.
   *
   * @return {@code this} builder instance enriched with {@code supportsTopLevelDeclaration}
   */
  public DslElementSyntaxBuilder supportsTopLevelDeclaration(boolean supportsTop) {
    this.supportsTopLevelDeclaration = supportsTop;
    return this;
  }

  /**
   * Declares whether or not {@code this} {@link DslElementSyntax} requires a parameter
   * pointing to a config
   *
   * @return {@code this} builder instance enriched with {@code supportsTopLevelDeclaration}
   */
  public DslElementSyntaxBuilder requiresConfig(boolean requiresConfig) {
    this.requiresConfig = requiresConfig;
    return this;
  }

  /**
   * Adds a {@link DslElementSyntax childElement} declaration to {@code this} {@link DslElementSyntax} that
   * represents a generic type of {@code this} element.
   *
   * @return {@code this} builder instance enriched with the {@code typed} {@link DslElementSyntax childElement}
   */
  public DslElementSyntaxBuilder withGeneric(MetadataType type, DslElementSyntax child) {
    if (child == null) {
      throw new IllegalArgumentException("Invalid child declaration, child element should not be null");
    }

    this.genericChilds.put(type, child);
    return this;
  }

  /**
   * Adds a {@link DslElementSyntax childElement} declaration to {@code this} {@link DslElementSyntax} that
   * can be referenced by {@code name}
   *
   * @return {@code this} builder instance enriched with the {@code named} {@link DslElementSyntax childElement}
   */
  public DslElementSyntaxBuilder containing(String name, DslElementSyntax child) {
    if (child == null) {
      throw new IllegalArgumentException("Invalid child declaration, child element should not be null");
    }

    this.containedElements.put(name, child);
    return this;
  }

  /**
   * @return a new instance of {@link DslElementSyntax}
   */
  public DslElementSyntax build() {
    return new DslElementSyntax(attributeName, elementName, prefix, namespace, isWrapped,
                                supportsAttributeDeclaration, supportsChildDeclaration, supportsTopLevelDeclaration,
                                requiresConfig, genericChilds, containedElements);
  }
}

