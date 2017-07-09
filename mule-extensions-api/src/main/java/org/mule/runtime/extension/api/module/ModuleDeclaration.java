/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module;

import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.EnrichableElementDeclaration;
import org.mule.runtime.api.app.declaration.GlobalElementDeclaration;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A programmatic descriptor of a mule artifact configuration.
 *
 * @since 1.0
 */
public final class ModuleDeclaration extends EnrichableElementDeclaration {

  private final ModuleGlobalElementDeclarationVisitor declarinExtensionEnricher;
  private List<ModuleGlobalElementDeclaration> globalElements = new LinkedList<>();
  private String minMuleVersion;
  private String namespace;
  private String prefix;
  private String vendor;

  public ModuleDeclaration(String name) {
    setName(name);
    declarinExtensionEnricher = new ModuleGlobalElementDeclarationVisitor() {

      @Override
      public void visit(ModulePropertyElementDeclaration declaration) {
        declaration.setDeclaringExtension(name);
      }

      @Override
      public void visit(ModuleOperationElementDeclaration declaration) {
        declaration.setDeclaringExtension(name);
      }
    };
  }

  /**
   * @return the {@link List} of {@link GlobalElementDeclaration global elements} associated with
   * {@code this} {@link ModuleDeclaration}
   */
  public List<ModuleGlobalElementDeclaration> getGlobalElements() {
    return unmodifiableList(globalElements);
  }

  /**
   * Adds a property to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration.
   *
   * @param declaration the {@link GlobalElementDeclaration} to add.
   */
  public ModuleDeclaration addGlobalElement(ModuleGlobalElementDeclaration declaration) {
    declaration.accept(declarinExtensionEnricher);
    globalElements.add(declaration);
    return this;
  }

  public void setMinMuleVersion(String version) {
    this.minMuleVersion = version;
  }

  public String getMinMuleVersion() {
    return minMuleVersion;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public String getVendor() {
    return vendor;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
