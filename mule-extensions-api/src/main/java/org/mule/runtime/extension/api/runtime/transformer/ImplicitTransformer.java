/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.runtime.transformer;

import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.metadata.DataType;

import java.util.List;

/**
 * 
 */
public interface ImplicitTransformer extends NamedObject {

  List<DataType> getSourceTypes();

  DataType getOutputType();

  Object transform(Object value);
}
