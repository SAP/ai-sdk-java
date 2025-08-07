package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import com.sap.ai.sdk.orchestration.model.Error;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ErrorStreaming;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.ModuleResults;
import com.sap.ai.sdk.orchestration.model.ModuleResultsStreaming;
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
      return new OrchestrationFilterException.Input(message, clientError, inputFilterDetails);
    }

    return new OrchestrationClientException(message, clientError);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  private Map<String, Object> extractInputFilterDetails(@Nonnull final OrchestrationError error) {

    if (error instanceof OrchestrationError.Synchronous synchronousError) {
      return Optional.of(synchronousError.getErrorResponse())
          .map(ErrorResponse::getError)
          .map(Error::getIntermediateResults)
          .map(ModuleResults::getInputFiltering)
          .map(GenericModuleResult::getData)
          .filter(Map.class::isInstance)
          .map(map -> (Map<String, Object>) map)
          .orElseGet(Collections::emptyMap);
    } else if (error instanceof OrchestrationError.Streaming streamingError) {
      return Optional.of(streamingError.getErrorResponse())
          .map(ErrorResponseStreaming::getError)
          .map(ErrorStreaming::getIntermediateResults)
          .map(ModuleResultsStreaming::getInputFiltering)
          .map(GenericModuleResult::getData)
          .filter(Map.class::isInstance)
          .map(map -> (Map<String, Object>) map)
          .orElseGet(Collections::emptyMap);
    }
    return Collections.emptyMap();
  }
}
