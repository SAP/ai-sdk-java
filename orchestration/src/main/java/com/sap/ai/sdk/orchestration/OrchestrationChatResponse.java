package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/** Orchestration chat completion output. */
@Value
@RequiredArgsConstructor(access = PACKAGE)
public class OrchestrationChatResponse {
  CompletionPostResponse originalResponse;

  /**
   * Get the message content from the output.
   *
   * <p>Note: If there are multiple choices only the first one is returned
   *
   * @return the message content or empty string.
   * @throws OrchestrationClientException if the content filter filtered the output.
   */
  @Nonnull
  public String getContent() throws OrchestrationClientException {
    final var choices =
        ((LLMModuleResultSynchronous) originalResponse.getOrchestrationResult()).getChoices();

    if (choices.isEmpty()) {
      return "";
    }

    final var choice = choices.get(0);

    if ("content_filter".equals(choice.getFinishReason())) {
      throw new OrchestrationClientException("Content filter filtered the output.");
    }
    return choice.getMessage().getContent();
  }

  /**
   * Get the token usage.
   *
   * @return The token usage.
   */
  @Nonnull
  public TokenUsage getTokenUsage() {
    return ((LLMModuleResultSynchronous) originalResponse.getOrchestrationResult()).getUsage();
  }

  /**
   * Get all messages.
   *
   * @return A list of all messages.
   */
  @Nonnull
  public List<ChatMessage> getAllMessages() {
    final var allMessages = originalResponse.getModuleResults().getTemplating();

    if (allMessages == null) {
      return List.of();
    }
    return allMessages;
  }
}
