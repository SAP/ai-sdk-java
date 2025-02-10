package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestToolMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestToolMessageContentPart;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/** Represents a tool message in the OpenAI SDK. */
@Value
@Accessors(fluent = true)
public class OpenAiToolMessage implements OpenAiMessage {

  /** The role of the message, which is "tool". */
  String role = "tool";

  /** The content of the message. */
  OpenAiMessageContent content;

  /**
   * Creates a new tool message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public OpenAiToolMessage(@Nonnull final String message) {
    content = new OpenAiMessageContent(List.of(new OpenAiTextItem(message)));
  }

  /**
   * Adds text to the message.
   *
   * @param message the text to add.
   * @return the new message with the added text.
   */
  @Nonnull
  public OpenAiToolMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new OpenAiTextItem(message));
    return new OpenAiToolMessage(new OpenAiMessageContent(contentItems));
  }

  /**
   * Creates a ChatCompletionRequestToolMessage from the current message content.
   *
   * @return the created ChatCompletionRequestToolMessage.
   * @throws IllegalArgumentException if the content contains unsupported items.
   */
  @Nonnull
  public ChatCompletionRequestToolMessage createChatCompletionRequestMessage()
      throws IllegalArgumentException {
    final var itemList = this.content().items();
    if (itemList.size() == 1 && itemList.get(0) instanceof OpenAiTextItem textItem) {
      return new ChatCompletionRequestToolMessage()
          .role(ChatCompletionRequestToolMessage.RoleEnum.fromValue(role()))
          .content(ChatCompletionRequestToolMessageContent.create(textItem.text()));
    }

    final var contentList = new LinkedList<ChatCompletionRequestToolMessageContentPart>();
    for (final OpenAiContentItem item : itemList) {
      if (item instanceof OpenAiTextItem textItem) {
        contentList.add(
            new ChatCompletionRequestMessageContentPartText()
                .type(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT)
                .text(textItem.text()));
      } else if (item instanceof OpenAiImageItem) {
        throw new IllegalArgumentException("Image items are not yet supported in tool messages");
      }
    }
    return new ChatCompletionRequestToolMessage()
        .role(ChatCompletionRequestToolMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestToolMessageContent.create(contentList));
  }
}
