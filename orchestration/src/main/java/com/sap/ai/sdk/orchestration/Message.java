package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import java.nio.file.Path;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    return user(message, null);
  }

  /**
   * A convenience method to create a user message from a string.
   *
   * @since 1.23.0
   * @param message the message content.
   * @param cacheControl cache checkpoint configuration
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final String message, @Nullable final CacheControl cacheControl) {
    return new UserMessage(message, cacheControl);
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
   * A convenience method to create a user message containing only a file loaded from disk.
   *
   * @param filePath the path to a local file.
   * @return the user message.
   * @since 1.18.0
   */
  @Nonnull
  static UserMessage user(@Nonnull final Path filePath) {
    return new UserMessage(new MessageContent(List.of())).withFile(filePath);
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
    return system(message, null);
  }

  /**
   * A convenience method to create a system message from a string allowing to configure
   * cache checkpoint
   *
   * @since 1.23.0
   * @param message the message content
   * @param cacheControl optional cache checkpoint configuration
   * @return the system message
   */
  @Nonnull
  static SystemMessage system(@Nonnull final String message, @Nullable final CacheControl cacheControl) {
    return new SystemMessage(message, cacheControl);
  }

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  ChatMessage createChatMessage();

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
  MessageContent content();
}
