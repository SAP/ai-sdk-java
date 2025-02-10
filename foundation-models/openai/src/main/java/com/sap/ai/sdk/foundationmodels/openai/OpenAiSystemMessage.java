package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessageContentPart;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/**
 * Represents a chat message as 'system' to OpenAI service. *
 *
 * @since 1.4.0
 */
@Beta
@Value
@Accessors(fluent = true)
public class OpenAiSystemMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Getter(onMethod_ = @Beta)
  @Nonnull
  OpenAiMessageContent content;

  /**
   * Creates a new system message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public OpenAiSystemMessage(@Nonnull final String message) {
    content = new OpenAiMessageContent(List.of(new OpenAiTextItem(message)));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
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
   * @return the corresponding serializable object.
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

    final var contentList = new LinkedList<ChatCompletionRequestSystemMessageContentPart>();
    for (final OpenAiContentItem item : itemList) {
      if (item instanceof OpenAiTextItem textItem) {
        contentList.add(
            new ChatCompletionRequestMessageContentPartText()
                .type(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT)
                .text(textItem.text()));
      } else if (item instanceof OpenAiImageItem) {
        throw new IllegalArgumentException("Image items are not yet supported in system messages");
      }
    }
    return new ChatCompletionRequestSystemMessage()
        .role(ChatCompletionRequestSystemMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestSystemMessageContent.create(contentList));
  }
}
