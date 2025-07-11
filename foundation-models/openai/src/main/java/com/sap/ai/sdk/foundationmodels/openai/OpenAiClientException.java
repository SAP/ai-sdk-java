package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.StandardException;

/** Generic exception for errors occurring when using OpenAI foundation models. */
@StandardException
public class OpenAiClientException extends ClientException {
  OpenAiClientException(@Nonnull final String message, @Nonnull final OpenAiError clientError) {
    super(message);
    this.clientError = clientError;
  }

  @Beta
  @Nullable
  @Override
  public OpenAiError getClientError() {
    return (OpenAiError) super.getClientError();
  }
}
