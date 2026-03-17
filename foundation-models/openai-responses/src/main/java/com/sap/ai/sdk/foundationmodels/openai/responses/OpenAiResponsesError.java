package com.sap.ai.sdk.foundationmodels.openai.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseError;
import com.sap.ai.sdk.core.common.ClientError;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Wrapper for Azure OpenAI Responses API error responses.
 *
 * <p>This class wraps Azure's {@link ResponseError} and provides deserialization support using
 * Azure's JSON framework.
 *
 * @since 1.17.0
 */
@Value
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
public class OpenAiResponsesError implements ClientError {
  Response errorResponse;

  @Nonnull
  public String getMessage() {
    return errorResponse.error().get().message();
  }
}
