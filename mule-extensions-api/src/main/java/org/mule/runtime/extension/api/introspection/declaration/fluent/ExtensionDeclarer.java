/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.api.introspection.declaration.fluent;

import org.mule.runtime.extension.api.introspection.exception.ExceptionEnricherFactory;
import org.mule.runtime.extension.api.introspection.ModelProperty;
import org.mule.runtime.extension.api.introspection.declaration.type.ExtensionsTypeLoaderFactory;
import org.mule.metadata.api.ClassTypeLoader;

import java.util.Optional;

/**
 * A builder object which allows creating an {@link ExtensionDeclaration}
 * through a fluent API.
 *
 * @since 1.0
 */
public class ExtensionDeclarer extends Declarer<ExtensionDeclaration> implements HasModelProperties<ExtensionDeclarer>, HasExceptionEnricher<ExtensionDeclarer>,
        HasOperationDeclarer, HasConnectionProviderDeclarer, HasSourceDeclarer
{

    private final ClassTypeLoader typeLoader;

    /**
     * Constructor for this descriptor
     */
    public ExtensionDeclarer()
    {
        super(new ExtensionDeclaration());
        typeLoader = ExtensionsTypeLoaderFactory.getDefault().createTypeLoader();
    }

    /**
     * Provides the extension's name
     *
     * @param name the extension's name
     * @return {@code this} descriptor
     */
    public ExtensionDeclarer named(String name)
    {
        declaration.setName(name);
        return this;
    }

    /**
     * Provides the extension's version
     *
     * @param version the extensions version
     * @return {@code this} descriptor
     */
    public ExtensionDeclarer onVersion(String version)
    {
        declaration.setVersion(version);
        return this;
    }

    /**
     * Adds a description
     *
     * @param description a description
     * @return {@code this} descriptor
     */
    public ExtensionDeclarer describedAs(String description)
    {
        declaration.setDescription(description);
        return this;
    }

    /**
     * Adds a config of the given name
     *
     * @param name a non blank name
     * @return a {@link ConfigurationDeclarer} which allows describing the created configuration
     */
    public ConfigurationDeclarer withConfig(String name)
    {
        ConfigurationDeclaration config = new ConfigurationDeclaration(name);
        declaration.addConfig(config);

        return new ConfigurationDeclarer(config, typeLoader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionProviderDeclarer withConnectionProvider(String name)
    {
        ConnectionProviderDeclaration declaration = new ConnectionProviderDeclaration(name);

        final ConnectionProviderDeclarer connectionProviderDeclarer = new ConnectionProviderDeclarer(declaration, typeLoader);
        withConnectionProvider(connectionProviderDeclarer);

        return connectionProviderDeclarer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withConnectionProvider(ConnectionProviderDeclarer declarer)
    {
        declaration.addConnectionProvider(declarer.getDeclaration());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationDeclarer withOperation(String name)
    {
        OperationDeclaration operation = new OperationDeclaration(name);
        final OperationDeclarer operationDeclarer = new OperationDeclarer(operation, typeLoader);
        withOperation(operationDeclarer);

        return operationDeclarer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withOperation(OperationDeclarer declarer)
    {
        declaration.addOperation(declarer.getDeclaration());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceDeclarer withMessageSource(String name)
    {
        SourceDeclaration declaration = new SourceDeclaration(name);

        final SourceDeclarer sourceDeclarer = new SourceDeclarer(declaration, typeLoader);
        withMessageSource(sourceDeclarer);

        return sourceDeclarer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withMessageSource(SourceDeclarer declarer)
    {
        declaration.addMessageSource(declarer.getDeclaration());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExtensionDeclarer withModelProperty(ModelProperty value)
    {
        declaration.addModelProperty(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExtensionDeclarer withExceptionEnricherFactory(Optional<ExceptionEnricherFactory> enricherFactory)
    {
        declaration.setExceptionEnricherFactory(enricherFactory);
        return this;
    }

    public ExtensionDeclarer fromVendor(String vendor)
    {
        declaration.setVendor(vendor);
        return this;
    }
}