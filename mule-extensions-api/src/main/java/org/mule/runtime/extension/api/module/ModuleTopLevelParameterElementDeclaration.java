/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module;

import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;

/**
 * Referable wrapper for {@link ConfigurationElementDeclaration}.
 *
 * @since 1.0
 */
public final class ModuleTopLevelParameterElementDeclaration implements ModuleGlobalElementDeclaration {

  private TopLevelParameterDeclaration delegate;

  public ModuleTopLevelParameterElementDeclaration(TopLevelParameterDeclaration config) {
    delegate = config;
  }

  public TopLevelParameterDeclaration getDeclaration() {
    return delegate;
  }

  @Override
  public void accept(ModuleGlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public boolean equals(Object o) {
    ModuleTopLevelParameterElementDeclaration that = (ModuleTopLevelParameterElementDeclaration) o;
    return delegate.equals(that.delegate);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }
}
