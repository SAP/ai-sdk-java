package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import com.sap.ai.sdk.orchestration.OrchestrationFilterException.OrchestrationInputFilterException;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.ModuleResults;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Beta
class OrchestrationExceptionFactory
    implements ClientExceptionFactory<OrchestrationClientException, OrchestrationError> {

  @Nonnull
  public OrchestrationClientException build(
      @Nonnull final String message, @Nullable final Throwable cause) {
    return new OrchestrationClientException(message, cause);
  }

  @Nonnull
  @Override
  public OrchestrationClientException buildFromClientError(
      @Nonnull final String message, @Nonnull final OrchestrationError clientError) {

    final var inputFilterDetails = extractInputFilterDetails(clientError);
    if (!inputFilterDetails.isEmpty()) {
      return new OrchestrationInputFilterException(message, clientError, inputFilterDetails);
    }

    return new OrchestrationClientException(message, clientError);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  private Map<String, Object> extractInputFilterDetails(@Nonnull final OrchestrationError error) {

    return Optional.of(error.getErrorResponse())
        .map(ErrorResponse::getModuleResults)
        .map(ModuleResults::getInputFiltering)
        .map(GenericModuleResult::getData)
        .filter(Map.class::isInstance)
        .map(map -> (Map<String, Object>) map)
        .orElseGet(Collections::emptyMap);
  }
}
