package com.sap.ai.sdk.foundationmodels.openai;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Interface representing convenience wrappers of chat message to the openai service.
 *
 * @since 1.4.0
 */
public sealed interface OpenAiMessage
    permits OpenAiUserMessage, OpenAiAssistantMessage, OpenAiSystemMessage, OpenAiToolMessage {

  /**
   * A convenience method to create a user message.
   *
   * @param message the message content.
   * @return the user message.
   */
  @Nonnull
  static OpenAiUserMessage user(@Nonnull final String message) {
    return new OpenAiUserMessage(message);
  }

  /**
   * A convenience method to create a user message containing only an image.
   *
   * @param openAiImageItem the message content.
   * @return the user message.
   */
  @Nonnull
  static OpenAiUserMessage user(@Nonnull final OpenAiImageItem openAiImageItem) {
    return new OpenAiUserMessage(new OpenAiMessageContent(List.of(openAiImageItem)));
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param message the message content.
   * @return the assistant message.
   */
  @Nonnull
  static OpenAiAssistantMessage assistant(@Nonnull final String message) {
    return new OpenAiAssistantMessage(message);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param toolCalls tool calls to associate with the message.
   * @return the assistant message.
   */
  @Nonnull
  static OpenAiAssistantMessage assistant(@Nonnull final List<OpenAiToolCall> toolCalls) {
    return new OpenAiAssistantMessage(
        new OpenAiMessageContent(List.of()), new ArrayList<>(toolCalls));
  }

  /**
   * A convenience method to create a system message.
   *
   * @param message the message content.
   * @return the system message.
   */
  @Nonnull
  static OpenAiSystemMessage system(@Nonnull final String message) {
    return new OpenAiSystemMessage(message);
  }

  /**
   * A convenience method to create a tool message.
   *
   * @param message response of the executed tool call.
   * @param toolCallId identifier of the tool call that the assistant expected.
   * @return the tool message.
   */
  @Nonnull
  static OpenAiToolMessage tool(@Nonnull final String message, @Nonnull final String toolCallId) {
    return new OpenAiToolMessage(message, toolCallId);
  }

  /**
   * Returns the role associated with the message.
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
  OpenAiMessageContent content();
}
