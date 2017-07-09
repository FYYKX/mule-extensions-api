/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module;

import org.mule.runtime.api.app.declaration.EnrichableElementDeclaration;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A programmatic descriptor of a {@link ParameterModel} configuration.
 *
 * @since 1.0
 */
public final class ModuleParameterElementDeclaration extends EnrichableElementDeclaration {

  private String use;
  private String type;
  private String role;
  private String defaultValue;
  private boolean password;

  public ModuleParameterElementDeclaration() {}

  public ModuleParameterElementDeclaration(String name) {
    setName(name);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public void setUse(String use) {
    this.use = use;
  }

  public String getUse() {
    return use;
  }

  public void setPassword(boolean password) {
    this.password = password;
  }

  public boolean isPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
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
