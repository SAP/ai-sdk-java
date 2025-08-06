package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class OpenAiExceptionFactory implements ClientExceptionFactory<OpenAiClientException, OpenAiError> {

  @Nonnull
  @Override
  public OpenAiClientException build(
      @Nonnull final String message,
      @Nullable final OpenAiError clientError,
      @Nullable final Throwable cause) {
    return new OpenAiClientException(message, cause).setClientError(clientError);
  }
}
