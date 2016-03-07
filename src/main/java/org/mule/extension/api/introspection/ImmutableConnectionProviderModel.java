/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.api.introspection;

import org.mule.api.connection.ConnectionProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Immutable implementation of {@link ConnectionProviderModel}
 *
 * @param <Config>     the generic type for the configuration objects that the returned {@link ConnectionProvider providers} accept
 * @param <Connection> the generic type for the connections that the returned  {@link ConnectionProvider providers} produce
 * @since 1.0
 */
public final class ImmutableConnectionProviderModel<Config, Connection> extends AbstractImmutableModel implements ConnectionProviderModel<Config, Connection>
{

    private final List<ParameterModel> parameterModels;
    private final ConnectionProviderFactory connectionProviderFactory;
    private final Class<Config> configurationType;
    private final Class<Connection> connectionType;

    /**
     * Creates a new instance with the given state
     *
     * @param name                      the provider's name
     * @param description               the provider's description
     * @param configurationType         the {@link Class} of the objects accepted as configs
     * @param connectionType            the {@link Class} of the provided connections
     * @param connectionProviderFactory the {@link ConnectionProviderFactory} used to create realizations of {@code this} model
     * @param parameterModels           a {@link List} with the provider's {@link ParameterModel parameterModels}
     * @param modelProperties           A {@link Map} of custom properties which extend this model
     * @throws IllegalArgumentException if {@code connectionProviderFactory}, {@code configurationType} or {@code connectionType} are {@code null}
     */
    public ImmutableConnectionProviderModel(String name,
                                            String description,
                                            Class<Config> configurationType,
                                            Class<Connection> connectionType,
                                            ConnectionProviderFactory connectionProviderFactory,
                                            List<ParameterModel> parameterModels,
                                            Map<String, Object> modelProperties)
    {
        super(name, description, modelProperties);
        this.parameterModels = Collections.unmodifiableList(parameterModels);

        checkArgument(connectionProviderFactory != null, "connectionProviderFactory cannot be null");
        checkArgument(configurationType != null, "configurationType cannot be null");
        checkArgument(connectionType != null, "connectionType cannot be null");

        this.connectionProviderFactory = connectionProviderFactory;
        this.configurationType = configurationType;
        this.connectionType = connectionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParameterModel> getParameterModels()
    {
        return parameterModels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionProviderFactory getConnectionProviderFactory()
    {
        return connectionProviderFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Config> getConfigurationType()
    {
        return configurationType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Connection> getConnectionType()
    {
        return connectionType;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("ImmutableConnectionProviderModel{")
                .append(super.toString())
                .append(", parameterModels=").append(parameterModels)
                .append(", connectionProviderFactory=").append(connectionProviderFactory)
                .append(", configurationType=").append(configurationType)
                .append(", connectionType=").append(connectionType)
                .append('}').toString();
    }
}