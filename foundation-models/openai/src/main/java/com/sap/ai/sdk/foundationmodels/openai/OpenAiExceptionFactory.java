package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Beta
class OpenAiExceptionFactory implements ClientExceptionFactory<OpenAiClientException, OpenAiError> {

  @Nonnull
  public OpenAiClientException create(
      @Nonnull final String message, @Nullable final Throwable cause) {
    return new OpenAiClientException(message, cause);
  }

  @Nonnull
  @Override
  public OpenAiClientException fromClientError(
      @Nonnull final String message, @Nonnull final OpenAiError openAiError) {
    return new OpenAiClientException(message, openAiError);
  }
}
