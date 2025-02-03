package com.sap.ai.sdk.orchestration;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull MessageContent content;

  @Override
  @Nonnull
  public MessageContent content() {
    return content;
  }

  /**
   * Creates a new user message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public UserMessage(String singleMessage) {
    content = new MessageContent(singleMessage);
  }

  /**
   * Creates a new user message with the given message content.
   *
   * @param messageContent the message content.
   */
  public UserMessage(MessageContent messageContent) {
    content = messageContent;
  }

  /**
   * Add text to the message.
   *
   * @param messages the text to add.
   * @return the new message.
   */
  @Nonnull
  public UserMessage addText(@Nonnull String... messages) {
    return new UserMessage(
        new MessageContent(
            Stream.concat(
                    content.contentItemList().stream(), Stream.of(messages).map(TextItem::new))
                .toList()));
  }

  /**
   * Add an image to the message with the given image URL and detail level.
   *
   * @param imageUrl the URL of the image.
   * @param detailLevel the detail level of the image.
   * @return the new message.
   */
  @Nonnull
  public UserMessage addImage(@Nonnull String imageUrl, ImageItem.DetailLevel detailLevel) {
    return new UserMessage(
        new MessageContent(
            Stream.concat(
                    content.contentItemList().stream(),
                    Stream.of(new ImageItem(imageUrl, detailLevel)))
                .toList()));
  }

  /**
   * Add an image to the message with the given image URL.
   *
   * @param imageUrl the URL of the image.
   * @return the new message.
   */
  @Nonnull
  public UserMessage addImage(@Nonnull String imageUrl) {
    return new UserMessage(
        new MessageContent(
            Stream.concat(content.contentItemList().stream(), Stream.of(new ImageItem(imageUrl)))
                .toList()));
  }

  /**
   * Add content in the form of {@link MessageContent} objects to the message.
   *
   * @param messageContents the content to add.
   * @return the new message.
   */
  public UserMessage add(@Nonnull MessageContent... messageContents) {
    List<ContentItem> combinedItems =
        Stream.concat(
                Stream.of(messageContents)
                    .flatMap(contentItem -> contentItem.contentItemList().stream()),
                content.contentItemList().stream())
            .toList();
    return new UserMessage(new MessageContent(combinedItems));
  }
}
