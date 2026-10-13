package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.anthropic.AnthropicModel.CLAUDE_45_HAIKU;

import com.sap.ai.sdk.foundationmodels.anthropic.AnthropicChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.anthropic.AnthropicChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.anthropic.AnthropicChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.anthropic.AnthropicClient;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Service;

/** Service to interact with the Anthropic Claude model. */
@Service
public class AnthropicService {

  static final AnthropicClient client = AnthropicClient.forModel(CLAUDE_45_HAIKU);

  /**
   * Performs a synchronous chat completion request.
   *
   * @param userMessage the user's message
   * @return the chat completion response
   */
  @Nonnull
  public AnthropicChatCompletionResponse chatCompletion(@Nonnull final String userMessage) {
    final var request =
        new AnthropicChatCompletionRequest().withUserMessage(userMessage).withMaxTokens(1000);
    return client.chatCompletion(request);
  }

  /**
   * Performs a streaming chat completion request.
   *
   * @param userMessage the user's message
   * @return a stream of chat completion deltas
   */
  @Nonnull
  public Stream<AnthropicChatCompletionDelta> streamChatCompletion(
      @Nonnull final String userMessage) {
    final var request =
        new AnthropicChatCompletionRequest().withUserMessage(userMessage).withMaxTokens(1000);
    return client.streamChatCompletion(request);
  }
}
