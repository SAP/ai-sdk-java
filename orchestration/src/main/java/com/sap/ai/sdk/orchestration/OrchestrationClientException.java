package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.StandardException;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
public class OrchestrationClientException extends ClientException {

  OrchestrationClientException(
      @Nonnull final String message, @Nonnull final OrchestrationError clientError) {
    super(message);
    this.clientError = clientError;
  }

  @Beta
  @Nullable
  @Override
  public OrchestrationError getClientError() {
    return (OrchestrationError) super.getClientError();
  }
}
