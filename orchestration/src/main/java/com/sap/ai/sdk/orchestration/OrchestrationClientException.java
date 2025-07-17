package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import java.util.Optional;
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

  /**
   * Retrieves the HTTP status code from the original error response, if available.
   *
   * @return the HTTP status code, or {@code null} if not available
   */
  @Beta
  @Nullable
  public Integer getStatusCode() {
    return Optional.ofNullable(getClientError())
        .map(OrchestrationError::getOriginalResponse)
        .map(ErrorResponse::getCode)
        .orElse(null);
  }
}
