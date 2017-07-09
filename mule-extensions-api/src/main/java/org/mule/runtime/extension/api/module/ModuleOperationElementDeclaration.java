/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module;

import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.CustomizableElementDeclaration;
import org.mule.runtime.api.app.declaration.EnrichableElementDeclaration;
import org.mule.runtime.api.app.declaration.MetadataPropertiesAwareElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A programmatic descriptor of an application Flow configuration.
 *
 * @since 1.0
 */
public final class ModuleOperationElementDeclaration extends EnrichableElementDeclaration
    implements ModuleGlobalElementDeclaration, CustomizableElementDeclaration, MetadataPropertiesAwareElementDeclaration {

  private List<ComponentElementDeclaration> components = new LinkedList<>();
  private List<ModuleParameterElementDeclaration> parameters = new LinkedList<>();
  private String outputType;

  public ModuleOperationElementDeclaration(String name) {
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with
   * {@code this}
   */
  public List<ModuleParameterElementDeclaration> getParameters() {
    return parameters;
  }

  /**
   * @return the {@link ParameterElementDeclaration parameters} associated with the given {@code name}
   * or {@link Optional#empty()} otherwise
   */
  public Optional<ModuleParameterElementDeclaration> getParameter(String name) {
    return parameters.stream().filter(p -> p.getName().equals(name)).findFirst();
  }

  /**
   * Adds a {@link ModuleParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param parameter the {@link ModuleParameterElementDeclaration} to associate to {@code this} element declaration
   */
  public void addParameter(ModuleParameterElementDeclaration parameter) {
    this.parameters.add(parameter);
  }

  @Override
  public void setDeclaringExtension(String declaringExtension) {
    super.setDeclaringExtension(declaringExtension);
    parameters.forEach(p -> p.setDeclaringExtension(declaringExtension));
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} contained by
   * {@code this} {@link ScopeElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  /**
   * Adds a {@link ComponentElementDeclaration} as a component contained by {@code this} {@link ScopeElementDeclaration scope}
   * @param declaration the {@link ComponentElementDeclaration} child of {@code this} {@link ScopeElementDeclaration scope}
   * @return {@code this} {@link ScopeElementDeclaration scope}
   */
  public ModuleOperationElementDeclaration addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return this;
  }

  /**
   * Adds a {@link ComponentElementDeclaration} as a component contained by {@code this} {@link ScopeElementDeclaration scope}
   * @param declaration the {@link ComponentElementDeclaration} child of {@code this} {@link ScopeElementDeclaration scope}
   * @return {@code this} {@link ScopeElementDeclaration scope}
   */
  public ModuleOperationElementDeclaration addComponent(int index, ComponentElementDeclaration declaration) {
    components.add(index, declaration);
    return this;
  }

  public void setOutputType(String type) {
    this.outputType = type;
  }

  public String getOutputType() {
    return outputType;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public void accept(ModuleGlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }
}
