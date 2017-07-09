/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module.fluent;

import org.mule.runtime.api.app.declaration.fluent.EnrichableElementDeclarer;
import org.mule.runtime.extension.api.module.ModuleParameterElementDeclaration;

public class ModuleParameterElementDeclarer
    extends EnrichableElementDeclarer<ModuleParameterElementDeclarer, ModuleParameterElementDeclaration> {

  ModuleParameterElementDeclarer(ModuleParameterElementDeclaration declaration) {
    super(declaration);
  }

  public ModuleParameterElementDeclarer ofType(String type) {
    declaration.setType(type);
    return this;
  }

  public ModuleParameterElementDeclarer withDefaultValue(String defaultValue) {
    declaration.setDefaultValue(defaultValue);
    return this;
  }

  public ModuleParameterElementDeclarer withUseType(String use) {
    declaration.setUse(use);
    return this;
  }

  public ModuleParameterElementDeclarer withRole(String role) {
    declaration.setRole(role);
    return this;
  }

  public ModuleParameterElementDeclarer asPassword(boolean password) {
    declaration.setPassword(password);
    return this;
  }

}
