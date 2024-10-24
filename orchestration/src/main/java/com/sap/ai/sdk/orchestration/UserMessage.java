package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * A user message.
 *
 * @param content the content.
 * @see Message
 */
public record UserMessage(@Nonnull String content) implements Message {
  @Nonnull
  @Override
  public String type() {
    return "user";
  }
}
