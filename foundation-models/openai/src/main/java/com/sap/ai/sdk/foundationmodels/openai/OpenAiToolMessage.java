package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessageContentPart;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/**
 * Represents a tool message in the OpenAI service.
 *
 * @since 1.4.0
 */
@Beta
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = PACKAGE)
public class OpenAiToolMessage implements OpenAiMessage {

  /** The role associated with this message. */
  String role = "tool";

  /** The content of the message. */
  OpenAiMessageContent content;

  /**
   * Creates a new tool message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  OpenAiToolMessage(@Nonnull final String message) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(message))));
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
  ChatCompletionRequestToolMessage createChatCompletionRequestMessage()
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
      } else {
        final var errorMessage = "Unknown content type for " + role() + " messages.";
        throw new IllegalArgumentException(errorMessage);
      }
    }
    return new ChatCompletionRequestToolMessage()
        .role(ChatCompletionRequestToolMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestToolMessageContent.create(contentList));
  }
}
