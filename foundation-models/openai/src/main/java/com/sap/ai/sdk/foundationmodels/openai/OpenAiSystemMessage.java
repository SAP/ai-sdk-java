package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessageContent;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/**
 * Represents a chat message as 'system' to OpenAI service. *
 *
 * @since 1.4.0
 */
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = PACKAGE)
public class OpenAiSystemMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Getter @Nonnull OpenAiMessageContent content;

  /**
   * Creates a new system message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  OpenAiSystemMessage(@Nonnull final String message) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(message))));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   */
  @Nonnull
  public OpenAiSystemMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new OpenAiTextItem(message));
    return new OpenAiSystemMessage(new OpenAiMessageContent(contentItems));
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestSystemMessage} object.
   * @throws IllegalArgumentException if the content contains unsupported items.
   */
  @Nonnull
  ChatCompletionRequestSystemMessage createChatCompletionRequestMessage()
      throws IllegalArgumentException {
    final var itemList = this.content().items();
    if (itemList.size() == 1 && itemList.get(0) instanceof OpenAiTextItem textItem) {
      return new ChatCompletionRequestSystemMessage()
          .role(ChatCompletionRequestSystemMessage.RoleEnum.fromValue(role()))
          .content(ChatCompletionRequestSystemMessageContent.create(textItem.text()));
    }

    final var contentList = new LinkedList<ChatCompletionRequestMessageContentPartText>();
    for (final OpenAiContentItem item : itemList) {
      if (item instanceof OpenAiTextItem textItem) {
        contentList.add(
            new ChatCompletionRequestMessageContentPartText()
                .type(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT)
                .text(textItem.text()));
      } else {
        final var errorMessage = "Unknown content type for " + item.getClass() + " messages.";
        throw new IllegalArgumentException(errorMessage);
      }
    }
    return new ChatCompletionRequestSystemMessage()
        .role(ChatCompletionRequestSystemMessage.RoleEnum.fromValue(role()))
        .content(
            ChatCompletionRequestSystemMessageContent
                .createListOfChatCompletionRequestMessageContentPartTexts(contentList));
  }
}
