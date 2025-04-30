package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.UserChatMessage.RoleEnum.USER;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.IMAGE_URL;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.TEXT;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ImageContentUrl;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public sealed interface Message permits AssistantMessage, SystemMessage, ToolMessage, UserMessage {

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
   * @since 1.3.0
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
    final var contentList = new LinkedList<UserChatMessageContentItem>();

    for (final ContentItem item : this.content().items()) {
      if (item instanceof TextItem textItem) {
        contentList.add(UserChatMessageContentItem.create().type(TEXT).text(textItem.text()));
      } else if (item instanceof ImageItem imageItem) {
        final var detail = imageItem.detailLevel().toString();
        final var img = ImageContentUrl.create().url(imageItem.imageUrl()).detail(detail);
        contentList.add(UserChatMessageContentItem.create().type(IMAGE_URL).imageUrl(img));
      }
    }
    return UserChatMessage.create().content(UserChatMessageContent.create(contentList)).role(USER);
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
