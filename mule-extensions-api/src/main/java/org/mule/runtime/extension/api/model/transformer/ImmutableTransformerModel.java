/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.model.transformer;

import static java.util.Collections.unmodifiableList;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.transformer.TransformerModel;
import org.mule.runtime.extension.api.model.AbstractImmutableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Immutable implementation of {@link TransformerModel}
 *
 * @since 1.0
 */
public class ImmutableTransformerModel extends AbstractImmutableModel implements NamedObject, TransformerModel {

  private final String name;
  private final List<MetadataType> sourceTypes;
  private final MetadataType outputType;

  /**
   * Creates a new instance
   *
   * @param name            the transformer's name
   * @param description     the transformer's description
   * @param sourceTypes     the source types
   * @param outputType      the output type
   * @param modelProperties a {@link Set} of custom properties which extend this model
   */
  public ImmutableTransformerModel(String name,
                                   String description,
                                   List<MetadataType> sourceTypes,
                                   MetadataType outputType,
                                   Set<ModelProperty> modelProperties) {
    super(description, modelProperties);
    checkArgument(name != null && name.length() > 0, "Name attribute cannot be null or blank");
    checkArgument(sourceTypes != null && !sourceTypes.isEmpty(), "Source types cannot be null nor empty");
    checkArgument(outputType != null, "outputType cannot be null");

    this.name = name;
    this.sourceTypes = unmodifiableList(new ArrayList<>(sourceTypes));
    this.outputType = outputType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MetadataType> getSourceTypes() {
    return sourceTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataType getOutputType() {
    return outputType;
  }
}
