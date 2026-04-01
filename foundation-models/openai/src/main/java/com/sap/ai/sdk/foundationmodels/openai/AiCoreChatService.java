package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.ClientOptions;
import com.openai.core.http.QueryParams;
import com.openai.services.blocking.ChatService;
import com.openai.services.blocking.chat.ChatCompletionService;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AiCoreChatService implements ChatService {

  @Delegate private final ChatService delegate;
  private final String deploymentModel;

  @Override
  @Nonnull
  public ChatService withOptions(@Nonnull final Consumer<ClientOptions.Builder> consumer) {
    return new AiCoreChatService(delegate.withOptions(consumer), deploymentModel);
  }

  @Override
  @Nonnull
  public WithRawResponse withRawResponse() {
    throw new UnsupportedOperationException(
        "withRawResponse() is not supported for AiCoreChatService");
  }

  @Override
  @Nonnull
  public ChatCompletionService completions() {
    final var apiVersionQuery = QueryParams.builder().put("api-version", "2024-02-01").build();
    final var completions =
        delegate.completions().withOptions(builder -> builder.queryParams(apiVersionQuery));
    return new AiCoreChatCompletionService(completions, deploymentModel);
  }
}
