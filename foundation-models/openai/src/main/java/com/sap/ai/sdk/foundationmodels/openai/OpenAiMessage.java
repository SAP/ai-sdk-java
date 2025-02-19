package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/**
 * Interface representing convenience wrappers of chat message to the openai service.
 *
 * @since 1.4.0
 */
@Beta
public interface OpenAiMessage {

  /**
   * A convenience method to create a user message.
   *
   * @param msg the message content.
   * @return the user message.
   */
  @Nonnull
  static OpenAiMessage user(@Nonnull final String msg) {
    return new OpenAiUserMessage(msg);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param msg the message content.
   * @return the assistant message.
   */
  @Nonnull
  static OpenAiMessage assistant(@Nonnull final String msg) {
    return new OpenAiAssistantMessage(msg);
  }

  /**
   * A convenience method to create a system message.
   *
   * @param msg the message content.
   * @return the system message.
   */
  @Nonnull
  static OpenAiMessage system(@Nonnull final String msg) {
    return new OpenAiSystemMessage(msg);
  }
}
