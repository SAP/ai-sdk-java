package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
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
    final var choice = getChoice();

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
   * @throws UnsupportedOperationException if the MultiChatMessage type message in chat.
   * @return A list of all messages.
   */
  @Nonnull
  public List<Message> getAllMessages() throws UnsupportedOperationException {
    final var messages = new ArrayList<Message>();

    for (final ChatMessagesInner chatMessage :
        originalResponse.getModuleResults().getTemplating()) {
      if (chatMessage instanceof ChatMessage simpleMsg) {
        final var message =
            switch (simpleMsg.getRole()) {
              case "user" -> new UserMessage(simpleMsg.getContent());
              case "assistant" -> new AssistantMessage(simpleMsg.getContent());
              case "system" -> new SystemMessage(simpleMsg.getContent());
              default -> throw new IllegalStateException("Unexpected role: " + simpleMsg.getRole());
            };
        messages.add(message);
      } else if (chatMessage instanceof MultiChatMessage mCMessage) {
        messages.add(new UserMessage(mCMessage.getContent()));
      } else {
        throw new UnsupportedOperationException(
            "Messages of type " + chatMessage.getClass() + " are not supported by convenience API");
      }
    }

    messages.add(new AssistantMessage(getChoice().getMessage().getContent()));
    return messages;
  }

  /**
   * Get the LLM response. Useful for accessing the finish reason or further data like logprobs.
   *
   * @return The (first, in case of multiple) {@link LLMChoice}.
   */
  @Nonnull
  public LLMChoice getChoice() {
    //    We expect choices to be defined and never empty.
    return ((LLMModuleResultSynchronous) originalResponse.getOrchestrationResult())
        .getChoices()
        .get(0);
  }
}
