/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.internal.property;

import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.extension.api.runtime.parameter.Literal;

/**
 * Marker {@link ModelProperty} for {@link ParameterModel}s that indicates that the
 * enriched parameter is considered as a Target type.
 * This is required to handle the parameter as a {@link Literal} one.
 *
 * @since 1.0
 */
public class TargetModelProperty implements ModelProperty {

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "target";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPublic() {
    return false;
  }
}
