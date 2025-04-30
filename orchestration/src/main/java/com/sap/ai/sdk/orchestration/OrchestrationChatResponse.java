package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
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
    for (final ChatMessage chatMessage : originalResponse.getModuleResults().getTemplating()) {
      if (chatMessage instanceof AssistantChatMessage assistantChatMessage) {
        var toolCalls = assistantChatMessage.getToolCalls();
        if (!toolCalls.isEmpty()) {
          messages.add(new AssistantMessage(toolCalls));
        } else {
          messages.add(new AssistantMessage(MessageContent.fromChatMessageContent(assistantChatMessage.getContent())));
        }
      } else if (chatMessage instanceof SystemChatMessage systemChatMessage) {
        messages.add(new SystemMessage(MessageContent.fromChatMessageContent(systemChatMessage.getContent())));
      } else if (chatMessage instanceof UserChatMessage userChatMessage) {
        if (userChatMessage.getContent() instanceof UserChatMessageContent.InnerString innerString) {
          messages.add(new UserMessage(innerString.value()));
        } else{
          var items = ((UserChatMessageContent.InnerUserChatMessageContentItems) userChatMessage.getContent()).values();
          var contentItems = new ArrayList<ContentItem>();
          for (var item: items) {
            if (item.getType().equals(UserChatMessageContentItem.TypeEnum.TEXT)) {
              contentItems.add()
            }
          }
          messages.add(new UserMessage())
        }
      } else {
        throw new IllegalArgumentException(
            "Messages of type " + chatMessage.getClass() + " are not supported by convenience API");
      }
    }
//      if (chatMessage instanceof SingleChatMessage simpleMsg) {
//        messages.add(chatMessageIntoMessage(simpleMsg));
//      } else if (chatMessage instanceof MultiChatMessage mCMessage) {
//        messages.add(chatMessageIntoMessage(mCMessage));
//      } else {
//        throw new IllegalArgumentException(
//            "Messages of type " + chatMessage.getClass() + " are not supported by convenience API");
//      }
    }
    messages.add(Message.assistant(getChoice().getMessage().getContent()));
    return messages;
  }

  @Nonnull
  private Message chatMessageIntoMessage(@Nonnull final SingleChatMessage simpleMsg) {
    return switch (simpleMsg.getRole()) {
      case "user" -> Message.user(simpleMsg.getContent());
      case "assistant" -> Message.assistant(simpleMsg.getContent());
      case "system" -> Message.system(simpleMsg.getContent());
      default -> throw new IllegalStateException("Unexpected role: " + simpleMsg.getRole());
    };
  }

  @Nonnull
  private Message chatMessageIntoMessage(@Nonnull final MultiChatMessage mCMessage) {
    return switch (mCMessage.getRole()) {
      case "user" -> new UserMessage(MessageContent.fromMCMContentList(mCMessage.getContent()));
      case "system" -> new SystemMessage(MessageContent.fromMCMContentList(mCMessage.getContent()));
      default ->
          throw new IllegalStateException(
              "Unexpected role with complex message: " + mCMessage.getRole());
    };
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
