package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientError;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ErrorResponse;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * Represents an error response from the OpenAI API.
 *
 * @since 1.3.0
 */
@Beta
@Value
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
public class OpenAiError implements ClientError {
  /** The original error response from the OpenAI API. */
  @Delegate(types = {ClientError.class})
  ErrorResponse originalResponse;

  /**
   * Gets the error message from the contained original response.
   *
   * @return the error message
   */
  @Nonnull
  public String getMessage() {
    return originalResponse.getError().getMessage();
  }
}
