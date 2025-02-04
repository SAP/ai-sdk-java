package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public sealed interface Message permits UserMessage, AssistantMessage, SystemMessage {

  /**
   * A convenience method to create a user message from one or more strings.
   *
   * @param message the message content.
   * @param additionalMessages the additional messages.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(
      @Nonnull final String message, @Nullable final String... additionalMessages) {
    return new UserMessage(message, additionalMessages);
  }

  /**
   * A convenience method to create a user message.
   *
   * @param message the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final MessageContent message) {
    return new UserMessage(message);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param message the message content.
   * @return the assistant message.
   */
  @Nonnull
  static AssistantMessage assistant(@Nonnull final String message) {
    return new AssistantMessage(message);
  }

  /**
   * A convenience method to create a system message from one or more strings.
   *
   * @param message the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(
      @Nonnull final String message, @Nullable final String... additionalMessages) {
    return new SystemMessage(message, additionalMessages);
  }

  /**
   * A convenience method to create a system message. As of now, only text content is supported for
   * system messages by most AIs.
   *
   * @param message the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final MessageContent message) {
    return new SystemMessage(message);
  }

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  default ChatMessage createChatMessage() {
    final var itemList = this.content().contentItemList();
    if (itemList.size() == 1 && itemList.get(0) instanceof TextItem textItem) {
      return SingleChatMessage.create().role(role()).content(textItem.text());
    } else {
      return MultiChatMessage.create()
          .role(role())
          .content(
              itemList.stream()
                  .map(
                      contentItem -> {
                        if (contentItem instanceof ImageItem imageItem) {
                          return ImageContent.create()
                              .type(ImageContent.TypeEnum.IMAGE_URL)
                              .imageUrl(
                                  ImageContentImageUrl.create()
                                      .url(imageItem.imageUrl())
                                      .detail(imageItem.detailLevel().toString()));
                        } else {
                          return TextContent.create()
                              .type(TextContent.TypeEnum.TEXT)
                              .text(((TextItem) contentItem).text());
                        }
                      })
                  .toList());
    }
  }

  /**
   * Returns the role of the assistant.
   *
   * @return the role.
   */
  @Nonnull
  String role();

  /**
   * Returns the content of the message.
   *
   * @return the content.
   */
  @Nonnull
  @Beta
  MessageContent content();
}
