package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.ArrayList;
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
    final var choice = getCurrentChoice();

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
   * Get all messages. This can be used for subsequent prompts as a message history.
   *
   * @return A list of all messages.
   */
  @Nonnull
  public List<Message> getAllMessages() {
    final var messages = new ArrayList<Message>();

    for (final ChatMessage chatMessage : originalResponse.getModuleResults().getTemplating()) {
      final var message =
          switch (chatMessage.getRole()) {
            case "user" -> new UserMessage(chatMessage.getContent());
            case "assistant" -> new AssistantMessage(chatMessage.getContent());
            case "system" -> new SystemMessage(chatMessage.getContent());
            default -> throw new IllegalStateException("Unexpected role: " + chatMessage.getRole());
          };
      messages.add(message);
    }

    messages.add(new AssistantMessage(getCurrentChoice().getMessage().getContent()));
    return messages;
  }

  /**
   * Get current choice.
   *
   * @return The current choice.
   */
  @Nonnull
  private LLMChoice getCurrentChoice() {
    //    We expect choices to be defined and never empty.
    return ((LLMModuleResultSynchronous) originalResponse.getOrchestrationResult())
        .getChoices()
        .get(0);
  }
}