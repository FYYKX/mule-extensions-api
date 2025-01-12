/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.runtime.source;

import org.mule.api.annotation.NoImplement;

/**
 * Factory object for instances of {@link Source}.
 * <p>
 * Implementations are to be reusable and thread-safe
 *
 * @since 1.0
 */
@NoImplement
public interface SourceFactory {

  /**
   * Creates a new instance of {@link Source}
   *
   * @return a new {@link Source}
   */
  Source createSource();
}
