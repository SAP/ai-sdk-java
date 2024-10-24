package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * A system message.
 *
 * @param content the content.
 * @see Message
 */
public record SystemMessage(@Nonnull String content) implements Message {
  @Nonnull
  @Override
  public String type() {
    return "system";
  }
}
