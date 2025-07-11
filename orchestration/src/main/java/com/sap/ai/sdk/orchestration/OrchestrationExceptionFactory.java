package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Beta
class OrchestrationExceptionFactory
    implements ClientExceptionFactory<OrchestrationClientException, OrchestrationError> {

  public OrchestrationClientException create(
      @Nonnull final String message, @Nullable final Throwable cause) {
    return new OrchestrationClientException(message, cause);
  }

  @Override
  public OrchestrationClientException fromClientError(
      @Nonnull final String message, @Nonnull final OrchestrationError orchestrationError) {
    return new OrchestrationClientException(message, orchestrationError);
  }
}
