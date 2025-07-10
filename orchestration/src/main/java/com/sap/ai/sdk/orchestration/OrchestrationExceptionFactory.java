package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrchestrationExceptionFactory
    implements ClientExceptionFactory<OrchestrationClientException, OrchestrationError> {

  public OrchestrationClientException create(@Nonnull String message, @Nullable Throwable cause) {
    return new OrchestrationClientException(message, cause);
  }

  @Override
  public OrchestrationClientException fromClientError(
      @Nonnull OrchestrationError orchestrationError) {
    return new OrchestrationClientException(orchestrationError);
  }
}
