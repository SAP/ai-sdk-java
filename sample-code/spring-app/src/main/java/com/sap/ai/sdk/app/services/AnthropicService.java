package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.anthropic.AnthropicModel.CLAUDE_45_HAIKU;

import com.anthropic.core.http.StreamResponse;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.RawMessageStreamEvent;
import com.sap.ai.sdk.foundationmodels.anthropic.AiCoreAnthropicClient;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Service;

/** Service class for Anthropic service using latest convenience api. */
@Service
public class AnthropicService {

  private static final MessageCreateParams.Builder BASE_PARAMS =
      MessageCreateParams.builder().model(CLAUDE_45_HAIKU.name()).maxTokens(1000);

  /**
   * Chat request to Anthropic.
   *
   * @param userMessage the prompt to send to the assistant.
   * @return the assistant message response.
   */
  @Nonnull
  public Message chatCompletion(@Nonnull final String userMessage) {
    return AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU)
        .messages()
        .create(BASE_PARAMS.addUserMessage(userMessage).build());
  }

  /**
   * Asynchronous stream of an OpenAI chat request.
   *
   * @param userMessage the message to send to the assistant.
   * @return the emitter that streams the assistant message response.
   */
  @Nonnull
  public StreamResponse<RawMessageStreamEvent> streamChatCompletion(
      @Nonnull final String userMessage) {
    return AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU)
        .messages()
        .createStreaming(BASE_PARAMS.addUserMessage(userMessage).build());
  }
}
