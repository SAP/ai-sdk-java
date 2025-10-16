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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /**
   * Creates a new user message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public UserMessage(@Nonnull final String message) {
    this(new MessageContent(List.of(new TextItem(message))));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new TextItem(message));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL and detail level.
   *
   * @param imageUrl the URL of the image.
   * @param detailLevel the detail level of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withImage(
      @Nonnull final String imageUrl, @Nonnull final ImageItem.DetailLevel detailLevel) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new ImageItem(imageUrl, detailLevel));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL.
   *
   * @param imageUrl the URL of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withImage(@Nonnull final String imageUrl) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new ImageItem(imageUrl));
    return new UserMessage(new MessageContent(contentItems));
  }

  @Override
  @Nonnull
  public ChatMessage createChatMessage() {
    final var contentList = new LinkedList<UserChatMessageContentItem>();

    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      return UserChatMessage.create()
          .content(UserChatMessageContent.create(textItem.text()))
          .role(USER);
    }
    for (final ContentItem item : content.items()) {
      if (item instanceof TextItem textItem) {
        contentList.add(UserChatMessageContentItem.create().type(TEXT).text(textItem.text()));
      } else if (item instanceof ImageItem imageItem) {
        final var detail = imageItem.detailLevel().toString();
        final var img = ImageContentUrl.create().url(imageItem.imageUrl()).detail(detail);
        contentList.add(UserChatMessageContentItem.create().type(IMAGE_URL).imageUrl(img));
      }
    }
    return UserChatMessage.create()
        .content(UserChatMessageContent.createListOfUserChatMessageContentItems(contentList))
        .role(USER);
  }
}
