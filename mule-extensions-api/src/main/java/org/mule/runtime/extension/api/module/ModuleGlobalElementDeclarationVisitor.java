/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.module;

/**
 * Used in {@link ModuleGlobalElementDeclaration#accept(ModuleGlobalElementDeclarationVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
public interface ModuleGlobalElementDeclarationVisitor {

  default void visit(ModuleConfigurationElementDeclaration declaration) {
    // do nothing
  }

  default void visit(ModulePropertyElementDeclaration declaration) {
    // do nothing
  }

  default void visit(ModuleOperationElementDeclaration declaration) {
    // do nothing
  }

  default void visit(ModuleTopLevelParameterElementDeclaration declaration) {
    //do nothing
  }
}
