/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.EnrichableElementDeclarer;
import org.mule.runtime.extension.api.module.ModuleOperationElementDeclaration;
import org.mule.runtime.extension.api.module.ModuleParameterElementDeclaration;

/**
 * Allows configuring an {@link ModuleOperationElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ModuleOperationElementDeclarer
    extends EnrichableElementDeclarer<ModuleOperationElementDeclarer, ModuleOperationElementDeclaration> {

  ModuleOperationElementDeclarer(ModuleOperationElementDeclaration declaration) {
    super(declaration);
  }

  public ModuleOperationElementDeclarer withOutputType(String type) {
    declaration.setOutputType(type);
    return this;
  }

  /**
   * Adds a {@link ComponentElementDeclaration component} to the declaration being built
   *
   * @param component the {@link ComponentElementDeclaration component} to add
   * @return {@code this} declarer
   */
  public ModuleOperationElementDeclarer withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return this;
  }

  public ModuleOperationElementDeclarer withParameter(ModuleParameterElementDeclaration parameter) {
    declaration.addParameter(parameter);
    return this;
  }

  public static ModuleParameterElementDeclarer newParameter(String name) {
    return new ModuleParameterElementDeclarer(new ModuleParameterElementDeclaration(name));
  }

}
