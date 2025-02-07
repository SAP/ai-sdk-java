package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.List;
import javax.annotation.Nonnull;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public sealed interface Message permits UserMessage, AssistantMessage, SystemMessage {

  /**
   * A convenience method to create a user message from a string.
   *
   * @param message the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final String message) {
    return new UserMessage(message);
  }

  /**
   * A convenience method to create a user message containing only an image.
   *
   * @param imageItem the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final ImageItem imageItem) {
    return new UserMessage(new MessageContent(List.of(imageItem)));
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
   * A convenience method to create a system message from a string.
   *
   * @param message the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final String message) {
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
