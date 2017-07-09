/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module.fluent;

import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;
import org.mule.runtime.api.app.declaration.fluent.EnrichableElementDeclarer;
import org.mule.runtime.extension.api.module.ModuleConfigurationElementDeclaration;
import org.mule.runtime.extension.api.module.ModuleDeclaration;
import org.mule.runtime.extension.api.module.ModuleOperationElementDeclaration;
import org.mule.runtime.extension.api.module.ModulePropertyElementDeclaration;
import org.mule.runtime.extension.api.module.ModuleTopLevelParameterElementDeclaration;

/**
 * Allows configuring an {@link ModuleDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ModuleDeclarer extends EnrichableElementDeclarer<ModuleDeclarer, ModuleDeclaration> {

  ModuleDeclarer(ModuleDeclaration declaration) {
    super(declaration);
  }

  public ModuleDeclarer withGlobalElement(ModulePropertyElementDeclaration element) {
    declaration.addGlobalElement(element);
    return this;
  }

  public ModuleDeclarer withGlobalElement(ModuleOperationElementDeclaration element) {
    declaration.addGlobalElement(element);
    return this;
  }

  public ModuleDeclarer withGlobalElement(ConfigurationElementDeclaration element) {
    declaration.addGlobalElement(new ModuleConfigurationElementDeclaration(element));
    return this;
  }

  public ModuleDeclarer withGlobalElement(TopLevelParameterDeclaration element) {
    declaration.addGlobalElement(new ModuleTopLevelParameterElementDeclaration(element));
    return this;
  }

  public ModuleDeclarer withVendor(String vendor) {
    declaration.setVendor(vendor);
    return this;
  }

  public ModuleDeclarer withPrefix(String prefix) {
    declaration.setPrefix(prefix);
    return this;
  }

  public ModuleDeclarer withNamespace(String namespace) {
    declaration.setNamespace(namespace);
    return this;
  }

  public ModuleDeclarer withMinMuleVersion(String minMuleVersion) {
    declaration.setMinMuleVersion(minMuleVersion);
    return this;
  }

  public static ModuleDeclarer newModule(String name) {
    return new ModuleDeclarer(new ModuleDeclaration(name));
  }

  public static ModuleOperationElementDeclarer newOperation(String name) {
    return new ModuleOperationElementDeclarer(new ModuleOperationElementDeclaration(name));
  }

  public static ModulePropertyElementDeclarer newProperty(String name) {
    return new ModulePropertyElementDeclarer(new ModulePropertyElementDeclaration(name));
  }

}
