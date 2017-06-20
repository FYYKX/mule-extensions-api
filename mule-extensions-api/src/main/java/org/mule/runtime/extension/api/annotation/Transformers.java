/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import org.mule.runtime.api.meta.model.transformer.TransformerModel;
import org.mule.runtime.extension.api.runtime.transformer.ImplicitTransformer;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Allows to reference a list of classes from which {@link TransformerModel} instances are to be
 * inferred. This annotation is to be used in classes which are also annotated with
 * {@link Extension} and {@link #value()} must reference {@link ImplicitTransformer} classes
 *
 * @since 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Transformers {

  Class<? extends ImplicitTransformer>[] value();
}
