package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ErrorResponse;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.StandardException;

/** Generic exception for errors occurring when using OpenAI foundation models. */
@StandardException
public class OpenAiClientException extends ClientException {

  OpenAiClientException(@Nonnull final String message, @Nonnull final OpenAiError clientError) {
    super(message);
    setClientError(clientError);
  }

  /**
   * Retrieves the {@link ErrorResponse} from the OpenAI service, if available.
   *
   * @return The {@link ErrorResponse} object, or {@code null} if not available.
   */
  @Beta
  @Nullable
  public ErrorResponse getErrorResponse() {
    final var clientError = super.getClientError();
    if (clientError instanceof OpenAiError openAiError) {
      return openAiError.getErrorResponse();
    }
    return null;
  }
}
