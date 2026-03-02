package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationClientException.lastError;
import static com.sap.ai.sdk.orchestration.OrchestrationClientException.lastErrorStreaming;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sap.ai.sdk.core.common.ClientError;
import com.sap.ai.sdk.orchestration.model.Error;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ErrorStreaming;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Orchestration error response.
 *
 * @since 1.1.0
 */
public interface OrchestrationError extends ClientError {
  /**
   * Orchestration error response for synchronous requests.
   *
   * @since 1.10.0
   */
  @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
  @Value
  class Synchronous implements OrchestrationError {
    private static Error NOT_FOUND =
        Error.create().requestId("").code(-1).message("Error not found.").location("");
    ErrorResponse errorResponse;

    @Override
    @Nonnull
    public String getMessage() {
      final Error e = lastError(errorResponse.getError()).orElse(NOT_FOUND);
      final String message = e.getMessage();
      return e.getCode() == 500 ? "%s located in %s".formatted(message, e.getLocation()) : message;
    }
  }

  /**
   * Orchestration error response for streaming requests.
   *
   * @since 1.10.0
   */
  @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
  @Value
  class Streaming implements OrchestrationError {
    private static ErrorStreaming NOT_FOUND =
        ErrorStreaming.create().requestId("").code(-1).message("Error not found.").location("");
    ErrorResponseStreaming errorResponse;

    @Override
    @Nonnull
    public String getMessage() {
      final ErrorStreaming e = lastErrorStreaming(errorResponse.getError()).orElse(NOT_FOUND);
      final String message = e.getMessage();
      return e.getCode() == 500 ? "%s located in %s".formatted(message, e.getLocation()) : message;
    }
  }
}
