/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.introspection.declaration.fluent;

/**
 * Contract interface for a declarer in which it's possible
 * to add operations
 *
 * @since 1.0
 */
public interface HasOperationDeclarer
{

    /**
     * Adds an operation of the given {@code name}
     *
     * @param name a non blank name
     * @return a {@link OperationDeclarer} which allows describing the created operation
     */
    OperationDeclarer withOperation(String name);

    void withOperation(OperationDeclarer declarer);
}