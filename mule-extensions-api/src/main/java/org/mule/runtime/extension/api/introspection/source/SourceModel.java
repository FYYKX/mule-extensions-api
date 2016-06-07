/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.introspection.source;


import org.mule.runtime.extension.api.introspection.ComponentModel;
import org.mule.runtime.extension.api.introspection.ExtensionModel;

/**
 * A definition of a message source in an {@link ExtensionModel}.
 * <p>
 * Source models implement the flyweight pattern. This means
 * that a given operation should only be represented by only
 * one instance of this class. Thus, if the same operation is
 * contained by different {@link HasSourceModels} instances,
 * then each of those containers should reference the same
 * operation model instance.
 *
 * @since 1.0
 */
public interface SourceModel extends ComponentModel
{

}