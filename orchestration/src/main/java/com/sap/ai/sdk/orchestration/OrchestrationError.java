package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientError;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Orchestration error response.
 *
 * @since 1.1.0
 */
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
@Value
@Beta
public class OrchestrationError implements ClientError {
  ErrorResponse originalResponse;

  /**
   * Gets the error message from the contained original response.
   *
   * @return the error message
   */
  @Nonnull
  public String getMessage() {
    return originalResponse.getCode() == 500
        ? originalResponse.getLocation()
        : originalResponse.getMessage();
  }
}
