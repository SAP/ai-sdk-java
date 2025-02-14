package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
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
}
