/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.persistence.model;

import org.mule.runtime.extension.api.annotation.Parameter;

public class ParameterGroupType {

  @Parameter
  private String groupedField;

  @Parameter
  private String anotherGroupedField;
}
