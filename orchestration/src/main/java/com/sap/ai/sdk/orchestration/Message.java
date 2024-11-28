package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
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
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  default ChatMessage createChatMessage() {
    return ChatMessage.create().role(role()).content(content());
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
  String content();
}
