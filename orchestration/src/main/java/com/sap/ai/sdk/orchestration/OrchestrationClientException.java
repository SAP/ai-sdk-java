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
    setClientError(clientError);
  }

  /**
   * Retrieves the {@link ErrorResponse} from the orchestration service, if available.
   *
   * @return The {@link ErrorResponse} object, or {@code null} if not available.
   */
  @Beta
  @Nullable
  public ErrorResponse getErrorResponse() {
    final var clientError = super.getClientError();
    if (clientError instanceof OrchestrationError orchestrationError) {
      return orchestrationError.getErrorResponse();
    }
    return null;
  }

  /**
   * Retrieves the HTTP status code from the original error response, if available.
   *
   * @return the HTTP status code, or {@code null} if not available
   */
  @Beta
  @Nullable
  public Integer getStatusCode() {
    return Optional.ofNullable(getErrorResponse()).map(ErrorResponse::getCode).orElse(null);
  }
}
