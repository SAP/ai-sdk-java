package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;

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
    return originalResponse.getFinalResult().getUsage();
  }

  /**
   * Get all messages. This can be used for subsequent prompts as a message history.
   *
   * @throws IllegalArgumentException if the MultiChatMessage type message in chat.
   * @return A list of all messages.
   */
  @Nonnull
  public List<Message> getAllMessages() throws IllegalArgumentException {
    val messages = new ArrayList<Message>();
    for (final ChatMessage chatMessage :
        originalResponse.getIntermediateResults().getTemplating()) {
      if (chatMessage instanceof AssistantChatMessage assistantChatMessage) {
        val toolCalls = assistantChatMessage.getToolCalls();
        if (!toolCalls.isEmpty()) {
          messages.add(new AssistantMessage(toolCalls));
        } else {
          messages.add(
              new AssistantMessage(
                  MessageContent.fromChatMessageContent(assistantChatMessage.getContent())));
        }
      } else if (chatMessage instanceof SystemChatMessage systemChatMessage) {
        messages.add(
            new SystemMessage(
                MessageContent.fromChatMessageContent(systemChatMessage.getContent())));
      } else if (chatMessage instanceof UserChatMessage userChatMessage) {
        messages.add(
            new UserMessage(
                MessageContent.fromUserChatMessageContent(userChatMessage.getContent())));
      } else if (chatMessage instanceof ToolChatMessage toolChatMessage) {
        messages.add(
            new ToolMessage(
                toolChatMessage.getToolCallId(),
                ((ChatMessageContent.InnerString) toolChatMessage.getContent()).value()));
      } else {
        throw new IllegalArgumentException(
            "Messages of type " + chatMessage.getClass() + " are not supported by convenience API");
      }
    }

    messages.add(Message.assistant(getChoice().getMessage().getContent()));
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
    return originalResponse.getFinalResult().getChoices().get(0);
  }
}
