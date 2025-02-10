package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContentPart;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'assistant' to OpenAI service.
 *
 * @since 1.4.0
 */
@Beta
@Value
@Accessors(fluent = true)
public class OpenAiAssistantMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Getter(onMethod_ = @Beta)
  @Nonnull
  OpenAiMessageContent content;

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public OpenAiAssistantMessage(@Nonnull final String singleMessage) {
    content = new OpenAiMessageContent(List.of(new OpenAiTextItem(singleMessage)));
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding serializable object.
   */
  @Nonnull
  ChatCompletionRequestAssistantMessage createChatCompletionRequestMessage()
      throws IllegalArgumentException {
    final var itemList = this.content().items();
    if (itemList.size() == 1 && itemList.get(0) instanceof OpenAiTextItem textItem) {
      return new ChatCompletionRequestAssistantMessage()
          .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
          .content(ChatCompletionRequestAssistantMessageContent.create(textItem.text()));
    }

    final var contentList = new LinkedList<ChatCompletionRequestAssistantMessageContentPart>();
    for (final OpenAiContentItem item : itemList) {
      if (item instanceof OpenAiTextItem textItem) {
        contentList.add(
            new ChatCompletionRequestMessageContentPartText()
                .type(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT)
                .text(textItem.text()));
      } else if (item instanceof OpenAiImageItem) {
        throw new IllegalArgumentException("Image items are not supported in assistant messages");
      }
    }
    return new ChatCompletionRequestAssistantMessage()
        .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestAssistantMessageContent.create(contentList));
  }
}
