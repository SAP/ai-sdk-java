package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.ClientOptions;
import com.openai.services.blocking.ChatService;
import com.openai.services.blocking.chat.ChatCompletionService;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AiCoreChatService implements ChatService {

  @Delegate private final ChatService delegate;
  private final String deploymentModel;

  @Override
  public @NonNull ChatService withOptions(@NonNull final Consumer<ClientOptions.Builder> consumer) {
    return new AiCoreChatService(delegate.withOptions(consumer), deploymentModel);
  }

  @Override
  public @NonNull WithRawResponse withRawResponse() {
    throw new UnsupportedOperationException(
        "withRawResponse() is not supported for AiCoreChatService");
  }

  @Override
  public @NonNull ChatCompletionService completions() {
    return new AiCoreChatCompletionService(delegate.completions(), deploymentModel);
  }
}
