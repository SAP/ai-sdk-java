package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.ModuleResults;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class OrchestrationExceptionFactory
    implements ClientExceptionFactory<OrchestrationClientException, OrchestrationError> {

  @Nonnull
  @Override
  public OrchestrationClientException build(
      @Nonnull final String message,
      @Nullable final OrchestrationError clientError,
      @Nullable final Throwable cause) {
    final var inputFilterDetails = extractInputFilterDetails(clientError);
    if (!inputFilterDetails.isEmpty()) {
      return new OrchestrationFilterException.Input(message, cause)
          .setFilterDetails(inputFilterDetails)
          .setClientError(clientError);
    }
    return new OrchestrationClientException(message, cause).setClientError(clientError);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  private Map<String, Object> extractInputFilterDetails(@Nullable final OrchestrationError error) {
    return Optional.ofNullable(error)
        .map(OrchestrationError::getErrorResponse)
        .map(ErrorResponse::getModuleResults)
        .map(ModuleResults::getInputFiltering)
        .map(GenericModuleResult::getData)
        .filter(Map.class::isInstance)
        .map(map -> (Map<String, Object>) map)
        .orElseGet(Collections::emptyMap);
  }
}
