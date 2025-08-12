package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
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
@Beta
public interface OrchestrationError extends ClientError {

  /**
   * Gets the error message from the orchestration error.
   *
   * @return the error message
   */
  @Nonnull
  String getMessage();

  /**
   * Orchestration error response for synchronous requests.
   *
   * @since 1.10.0
   */
  @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
  @Value
  class Synchronous implements OrchestrationError {
    ErrorResponse errorResponse;

    @Override
    @Nonnull
    public String getMessage() {
      final Error e = errorResponse.getError();
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
    ErrorResponseStreaming errorResponse;

    @Override
    @Nonnull
    public String getMessage() {
      final ErrorStreaming e = errorResponse.getError();
      final String message = e.getMessage();
      return e.getCode() == 500 ? "%s located in %s".formatted(message, e.getLocation()) : message;
    }
  }
}
