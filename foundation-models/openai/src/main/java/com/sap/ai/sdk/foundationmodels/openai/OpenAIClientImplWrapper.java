package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.models.ChatModel;
import com.openai.services.blocking.ResponseService;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
class OpenAIClientImplWrapper implements OpenAIClient {
  @Nonnull
  @Delegate(excludes = Excludes.class)
  private final OpenAIClientImpl delegate;

  @Nonnull private final ChatModel expectedModel;

  /** Returns an instance of AiCoreResponsesService */
  @Override
  @Nonnull
  public ResponseService responses() {
    return new AiCoreResponsesService(delegate.responses(), expectedModel);
  }

  private interface Excludes {
    ResponseService responses();
  }
}
