package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import javax.annotation.Nonnull;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public sealed interface Message permits UserMessage, AssistantMessage, SystemMessage {

  /**
   * A convenience method to create a user message.
   *
   * @param msg the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final String msg) {
    return new UserMessage(msg);
  }

  /**
   * A convenience method to create a user message.
   *
   * @param msg the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final MessageContent msg) {
    return new UserMessage(msg);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param msg the message content.
   * @return the assistant message.
   */
  @Nonnull
  static AssistantMessage assistant(@Nonnull final String msg) {
    return new AssistantMessage(msg);
  }

  /**
   * A convenience method to create a system message.
   *
   * @param msg the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final String msg) {
    return new SystemMessage(msg);
  }

  /**
   * A convenience method to create a system message. As of now, only text content is supported for
   * system messages by most AIs.
   *
   * @param msg the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final MessageContent msg) {
    return new SystemMessage(msg);
  }

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  default ChatMessagesInner createChatMessage() {
    final var itemList = this.content().contentItemList();
    if (itemList.size() == 1 && itemList.get(0) instanceof TextItem textItem) {
      return ChatMessage.create().role(role()).content(textItem.text());
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
