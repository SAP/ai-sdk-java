package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /**
   * Creates a new user message from one or more strings.
   *
   * @param message the first message.
   * @param additionalMessages the additional messages.
   */
  @Tolerate
  public UserMessage(@Nonnull final String message, @Nullable final String... additionalMessages) {
    this(MessageContent.text(message, additionalMessages));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add
   * @param additionalMessages additional text to add
   * @return the new message.
   */
  @Nonnull
  public UserMessage andText(
      @Nonnull final String message, @Nullable final String... additionalMessages) {
    var messagesStream =
        (additionalMessages != null)
            ? Stream.concat(Stream.of(message), Stream.of(additionalMessages))
            : Stream.of(message);
    var contentList =
        Stream.concat(content.contentItemList().stream(), messagesStream.map(TextItem::new))
            .toList();
    return new UserMessage(new MessageContent(contentList));
  }

  /**
   * Add an image to the message with the given image URL and detail level.
   *
   * @param imageUrl the URL of the image.
   * @param detailLevel the detail level of the image.
   * @return the new message.
   */
  @Nonnull
  public UserMessage andImage(
      @Nonnull final String imageUrl, @Nonnull final ImageItem.DetailLevel detailLevel) {
    final var contentItems = new LinkedList<>(content.contentItemList());
    contentItems.add(new ImageItem(imageUrl, detailLevel));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL.
   *
   * @param imageUrl the URL of the image.
   * @return the new message.
   */
  @Nonnull
  public UserMessage andImage(@Nonnull final String imageUrl) {
    final var contentItems = new LinkedList<>(content.contentItemList());
    contentItems.add(new ImageItem(imageUrl));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add content in the form of {@link MessageContent} objects to the message.
   *
   * @param messageContents the content to add.
   * @return the new message.
   */
  @Nonnull
  public UserMessage and(@Nonnull final MessageContent... messageContents) {
    final List<ContentItem> combinedItems =
        Stream.concat(
                content.contentItemList().stream(),
                Stream.of(messageContents)
                    .flatMap(contentItem -> contentItem.contentItemList().stream()))
            .toList();
    return new UserMessage(new MessageContent(combinedItems));
  }
}
