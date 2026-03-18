package com.sap.ai.sdk.foundationmodels.openai.responses;

import com.google.common.annotations.Beta;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseError;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nullable;
import lombok.experimental.StandardException;

@StandardException
public class OpenAiResponsesClientException extends ClientException {

  static final ClientExceptionFactory<OpenAiResponsesClientException, OpenAiResponsesError>
      FACTORY =
          (message, error, cause) ->
              new OpenAiResponsesClientException(message, cause).setClientError(error);

  @Beta
  @Nullable
  public Response getErrorResponse() {
    final var clientError = super.getClientError();
    if (clientError instanceof OpenAiResponsesError openAiError) {
      return openAiError.getErrorResponse();
    }
    return null;
  }
}
