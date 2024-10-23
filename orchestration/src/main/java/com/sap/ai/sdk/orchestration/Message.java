package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents messages that can be sent to and received from the orchestration service as part of a
 * template, message history or LLM response.
 *
 * @see UserMessage
 * @see SystemMessage
 * @see AssistantMessage
 */
public interface Message {

  /**
   * Type (or sometimes role) of the message.
   *
   * @return the type of the message
   */
  @Nonnull
  String type();

  /**
   * Content of the message.
   *
   * @return the content of the message. Can be {@code null}, unless specified otherwise by the
   *     implementing type.
   */
  @Nullable
  String content();
}
