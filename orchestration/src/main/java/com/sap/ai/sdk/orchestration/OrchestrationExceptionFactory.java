package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Beta
class OrchestrationExceptionFactory
    implements ClientExceptionFactory<OrchestrationClientException, OrchestrationError> {

  @Nonnull
  public OrchestrationClientException create(
      @Nonnull final String message, @Nullable final Throwable cause) {
    return new OrchestrationClientException(message, cause);
  }

  @Nonnull
  @Override
  public OrchestrationClientException fromClientError(
      @Nonnull final String message, @Nonnull final OrchestrationError clientError) {

    final var inputFilterDetails = extractInputFilterDetails(clientError);
    if (!inputFilterDetails.isEmpty()) {
      return new OrchestrationFilterException.OrchestrationInputFilterException(
          message, clientError, inputFilterDetails);
    }

    return new OrchestrationClientException(message, clientError);
  }

  private Map<String, Object> extractInputFilterDetails(@Nonnull final OrchestrationError error) {

    return Optional.ofNullable(error.getOriginalResponse())
        .flatMap(resp -> Optional.ofNullable(resp.getModuleResults()))
        .flatMap(mr -> Optional.ofNullable(mr.getInputFiltering()))
        .flatMap(iflt -> Optional.ofNullable(iflt.getData()))
        .filter(Map.class::isInstance)
        .map(map -> (Map<String, Object>) map)
        .orElseGet(Collections::emptyMap);
  }
}
