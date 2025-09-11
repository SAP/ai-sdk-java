package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.MessageToolCall;
import java.util.Arrays;
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
    return new UserMessage(new MessageContent(List.of(new TextItem(message))));
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
   * @param messages the messages.
   * @return the assistant message.
   */
  @Nonnull
  static AssistantMessage assistant(@Nonnull final String... messages) {
    final var content = new MessageContent(Arrays.stream(messages).map(TextItem::new).toList());
    return new AssistantMessage(content);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param toolCalls the tool calls.
   * @return the assistant message.
   */
  @Nonnull
  static AssistantMessage assistant(@Nonnull final List<MessageToolCall> toolCalls) {
    return new AssistantMessage(new MessageContent(List.of()), toolCalls);
  }

  /**
   * A convenience method to create a system message from a string.
   *
   * @param message the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final String message) {
    return new SystemMessage(new MessageContent(List.of(new TextItem(message))));
  }

  /**
   * A convenience method to create a tool message from an id and content.
   *
   * @param id the tool id.
   * @param content the tool content.
   * @return the tool message.
   */
  @Nonnull
  static ToolMessage tool(@Nonnull final String id, @Nonnull final String content) {
    //noinspection deprecation: constructor is deprecated, will be made package-private in future
    return new ToolMessage(id, content);
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
  @Beta
  MessageContent content();
}
