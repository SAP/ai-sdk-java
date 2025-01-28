package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
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
   * @throws IllegalArgumentException if the MultiChatMessage type message in chat.
   * @return A list of all messages.
   */
  @Nonnull
  public List<Message> getAllMessages() throws IllegalArgumentException {
    final var messages = new ArrayList<Message>();

    for (final ChatMessagesInner chatMessage :
        originalResponse.getModuleResults().getTemplating()) {
      if (chatMessage instanceof ChatMessage simpleMsg) {
        final var message =
            switch (simpleMsg.getRole()) {
              case "user" -> Message.user(simpleMsg.getContent());
              case "assistant" -> Message.assistant(simpleMsg.getContent());
              case "system" -> Message.system(simpleMsg.getContent());
              default ->
                  throw new IllegalArgumentException(
                      "Unexpected role: "
                          + simpleMsg.getRole()
                          + ". Only 'user', 'assistant' and 'system' are supported.");
            };
        messages.add(message);
      } else if (chatMessage instanceof MultiChatMessage mCMessage) {
        messages.add(
            switch (mCMessage.getRole()) {
              case "user" ->
                  Message.user(
                      new MessageContentMulti(
                          mCMessage.getContent().toArray(MultiChatMessageContent[]::new)));
              case "system" ->
                  Message.system(
                      new MessageContentMulti(
                          mCMessage.getContent().toArray(MultiChatMessageContent[]::new)));
              default ->
                  throw new IllegalArgumentException(
                      "Unexpected role using MultiChatMessage: "
                          + mCMessage.getRole()
                          + ". Only 'user' and 'system' are supported.");
            });
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
    return ((LLMModuleResultSynchronous) originalResponse.getOrchestrationResult())
        .getChoices()
        .get(0);
  }
}
