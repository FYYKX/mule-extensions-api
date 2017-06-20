/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.runtime.transformer;

import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;

import java.util.List;

/**
 * Implicit transformers are automatically applied by the runtime whenever a component requires a value
 * of type {@code X}, but the assigned value is of a type {@code S}.
 * <p>
 * Implicit transformers are capable of transforming values of a number of {@link #getSourceTypes() source} types
 * and produce a value of a {@link #getOutputType() different} type.
 * <p>
 * When we refer to &quot;value of type&quot;, we actually refer to a Mule {@link DataType}, which is comprised
 * of a Java type and a {@link MediaType}. Using {@link DataType} instead of a plain {@link Class} gives this
 * mechanism a lot of power, since it allows to know only match types but representations as well.
 * <p>
 * For example, let's consider an extension which defines an Invoice object. We can define two different transformers,
 * one which can transform an XML representation of the invoice into an Invoice, and another which accepts a json representation
 * of if.
 * <p>
 * <pre>
 *   public class JsonToInvoice implements ImplicitTransformer {
 *
 *    @Override
 *    public String getName() {
 *      return NAME;
 *    }
 *
 *    @Override
 *    public List<DataType> getSourceTypes() {
 *      return asList(DataType.JSON_STRING);
 *    }
 *
 *    @Override
 *    public DataType getOutputType() {
 *      return DataType.fromType(Invoice.class);
 *    }
 *
 *    @Override
 *    public Object transform(Object value) {
 *      return new Gson().fromJson((String) value, Invoice.class);
 *    }
 *   }
 *
 *   public class XMLToInvoice implements ImplicitTransformer {
 *
 *    @Override
 *    public String getName() {
 *      return "xmlToInvoice";
 *    }
 *
 *    @Override
 *    public List<DataType> getSourceTypes() {
 *      return asList(DataType.builder().type(String.class).mediaType(APPLICATION_XML).build());
 *    }
 *
 *    @Override
 *    public DataType getOutputType() {
 *      return DataType.fromType(Invoice.class);
 *    }
 *
 *    @Override
 *    public Object transform(Object value) {
 *        XStream xStream = new XStream();
 *        xStream.alias("invoice", Invoice.class);
 *       return xStream.fromXML((String) value);
 *    }
 *   }
 * </pre>
 * <p>
 * These transformers are implicit and cannot be invoked from the Mule DSL. They're automatically applied by the runtime.
 *
 * @since 1.0
 */
public interface ImplicitTransformer extends NamedObject {

  /**
   * Returns the {@link DataType types} that are accepted of input of this transformer
   *
   * @return a {@link List} with at least one {@link DataType}
   */
  List<DataType> getSourceTypes();

  /**
   * The {@link DataType} of the values produced by this transformer
   *
   * @return a not-null {@link DataType}
   */
  DataType getOutputType();

  /**
   * Transforms the given value into another one of the {@link #getOutputType() output} type.
   * <p>
   * The {@code value} is guaranteed to be of one of the declared {@link #getSourceTypes() source types}
   *
   * @param value the source value
   * @return A transformed value
   */
  Object transform(Object value);
}
