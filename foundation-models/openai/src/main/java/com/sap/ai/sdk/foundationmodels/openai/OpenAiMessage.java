package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Interface representing convenience wrappers of chat message to the openai service.
 *
 * @since 1.4.0
 */
@Beta
public sealed interface OpenAiMessage
    permits OpenAiUserMessage, OpenAiAssistantMessage, OpenAiSystemMessage {

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
   * @since 1.3.0
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
  @Beta
  OpenAiMessageContent content();
}
