/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.extension.internal.loader.enricher;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;
import static org.mule.runtime.api.tx.TransactionType.LOCAL;
import static org.mule.runtime.extension.api.ExtensionConstants.OPERATION_TRANSACTIONAL_ACTION_PARAMETER_DESCRIPTION;
import static org.mule.runtime.extension.api.ExtensionConstants.TRANSACTION_TYPE_PARAMETER_DESCRIPTION;
import static org.mule.runtime.extension.api.ExtensionConstants.SOURCE_TRANSACTIONAL_ACTION_PARAMETER_DESCRIPTION;
import static org.mule.runtime.extension.api.ExtensionConstants.TRANSACTIONAL_ACTION_PARAMETER_NAME;
import static org.mule.runtime.extension.api.ExtensionConstants.TRANSACTIONAL_TYPE_PARAMETER_NAME;
import static org.mule.runtime.extension.api.annotation.param.display.Placement.ADVANCED_TAB;
import static org.mule.runtime.extension.api.loader.DeclarationEnricherPhase.STRUCTURE;
import static org.mule.runtime.extension.api.tx.OperationTransactionalAction.JOIN_IF_POSSIBLE;
import static org.mule.runtime.extension.api.tx.SourceTransactionalAction.NONE;
import org.mule.metadata.api.ClassTypeLoader;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.declaration.fluent.ComponentDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ExecutableComponentDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterDeclaration;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclaration;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.tx.TransactionType;
import org.mule.runtime.extension.api.declaration.fluent.util.IdempotentDeclarationWalker;
import org.mule.runtime.extension.api.declaration.type.ExtensionsTypeLoaderFactory;
import org.mule.runtime.extension.api.loader.DeclarationEnricher;
import org.mule.runtime.extension.api.loader.DeclarationEnricherPhase;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.api.tx.OperationTransactionalAction;
import org.mule.runtime.extension.api.tx.SourceTransactionalAction;
import org.mule.runtime.extension.internal.property.TransactionalActionModelProperty;
import org.mule.runtime.extension.internal.property.TransactionalTypeModelProperty;

import java.util.Optional;

/**
 * {@link DeclarationEnricher} which enrich transactional {@link ComponentModel component models} adding required
 * transactional parameters to the correspondent model.
 * <p>
 * If the given {@link ComponentModel} already contains the parameter, this one will be enriched to ensure a cross
 * components transactional parameters UX.
 *
 * @since 1.0
 */
public final class TransactionalDeclarationEnricher implements DeclarationEnricher {

  @Override
  public DeclarationEnricherPhase getExecutionPhase() {
    return STRUCTURE;
  }

  @Override
  public void enrich(ExtensionLoadingContext extensionLoadingContext) {
    new EnricherDelegate().enrich(extensionLoadingContext);
  }

  private class EnricherDelegate implements DeclarationEnricher {

    private final MetadataType operationTransactionalActionType;
    private final MetadataType sourceTransactionalActionType;
    private final MetadataType transactionType;

    private EnricherDelegate() {
      ClassTypeLoader typeLoader = ExtensionsTypeLoaderFactory.getDefault().createTypeLoader();
      operationTransactionalActionType = typeLoader.load(OperationTransactionalAction.class);
      sourceTransactionalActionType = typeLoader.load(SourceTransactionalAction.class);
      transactionType = typeLoader.load(TransactionType.class);
    }

    @Override
    public void enrich(ExtensionLoadingContext extensionLoadingContext) {
      new IdempotentDeclarationWalker() {

        @Override
        protected void onSource(SourceDeclaration declaration) {
          addTxParameter(TRANSACTIONAL_ACTION_PARAMETER_NAME, sourceTransactionalActionType, NONE,
                         SOURCE_TRANSACTIONAL_ACTION_PARAMETER_DESCRIPTION, declaration, new TransactionalActionModelProperty());
          addTxParameter(TRANSACTIONAL_TYPE_PARAMETER_NAME, transactionType, LOCAL, TRANSACTION_TYPE_PARAMETER_DESCRIPTION,
                         declaration,
                         new TransactionalTypeModelProperty());
        }

        @Override
        protected void onOperation(OperationDeclaration declaration) {
          addTxParameter(TRANSACTIONAL_ACTION_PARAMETER_NAME, operationTransactionalActionType, JOIN_IF_POSSIBLE,
                         OPERATION_TRANSACTIONAL_ACTION_PARAMETER_DESCRIPTION, declaration,
                         new TransactionalActionModelProperty());
        }
      }.walk(extensionLoadingContext.getExtensionDeclarer().getDeclaration());
    }

    private void addTxParameter(String parameterName, MetadataType metadataType, Object defaultValue, String description,
                                ExecutableComponentDeclaration<?> declaration, ModelProperty modelProperty) {
      if (declaration.isTransactional()) {
        Optional<ParameterDeclaration> parameterDeclaration = isPresent(declaration, metadataType);
        if (parameterDeclaration.isPresent()) {
          enrichTransactionParameter(defaultValue, description, parameterDeclaration.get(), modelProperty);
        } else {
          ParameterDeclaration transactionParameter = new ParameterDeclaration(parameterName);
          transactionParameter.setType(metadataType, false);
          enrichTransactionParameter(defaultValue, description, transactionParameter, modelProperty);
          declaration.getParameterGroup(DEFAULT_GROUP_NAME).addParameter(transactionParameter);
        }
      }
    }

    private void enrichTransactionParameter(Object defaultValue, String description, ParameterDeclaration transactionParameter,
                                            ModelProperty modelProperty) {
      transactionParameter.setExpressionSupport(NOT_SUPPORTED);
      transactionParameter.setRequired(false);
      transactionParameter.setDefaultValue(defaultValue);
      transactionParameter.setDescription(description);
      transactionParameter.addModelProperty(modelProperty);
      transactionParameter.setLayoutModel(LayoutModel.builder().tabName(ADVANCED_TAB).build());
    }

    private Optional<ParameterDeclaration> isPresent(ComponentDeclaration<?> declaration, MetadataType metadataType) {
      return declaration.getParameterGroups()
          .stream()
          .map(group -> group.getParameters().stream())
          .flatMap(stream -> stream)
          .filter(parameterDeclaration -> parameterDeclaration.getType().equals(metadataType))
          .findAny();
    }
  }
}
