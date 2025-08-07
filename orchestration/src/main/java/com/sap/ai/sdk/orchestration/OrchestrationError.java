package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientError;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponseStreaming;
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
public abstract class OrchestrationError implements ClientError {

  /**
   * Orchestration error response for synchronous requests.
   *
   * @since 1.10.0
   */
  @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
  @Value
  public static class Synchronous extends OrchestrationError {
    ErrorResponse errorResponse;

    /**
     * Gets the error message from the contained original response.
     *
     * @return the error message
     */
    @Nonnull
    public String getMessage() {
      return errorResponse.getError().getCode() == 500
          ? errorResponse.getError().getMessage()
              + " located in "
              + errorResponse.getError().getLocation()
          : errorResponse.getError().getMessage();
    }
  }

  /**
   * Orchestration error response for streaming requests.
   *
   * @since 1.10.0
   */
  @AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
  @Value
  public static class Streaming extends OrchestrationError {
    ErrorResponseStreaming errorResponse;

    /**
     * Gets the error message from the contained original response.
     *
     * @return the error message
     */
    @Nonnull
    public String getMessage() {
      return errorResponse.getError().getCode() == 500
          ? errorResponse.getError().getMessage()
              + " located in "
              + errorResponse.getError().getLocation()
          : errorResponse.getError().getMessage();
    }
  }
}
