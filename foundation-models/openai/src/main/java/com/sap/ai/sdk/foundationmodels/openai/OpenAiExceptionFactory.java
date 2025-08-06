package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class OpenAiExceptionFactory implements ClientExceptionFactory<OpenAiClientException, OpenAiError> {

  @Nonnull
  @Override
  public OpenAiClientException build(@Nonnull String message, @Nullable OpenAiError clientError, @Nullable Throwable cause) {
    return (OpenAiClientException) new OpenAiClientException(message, cause).setClientError(clientError);
  }
}
