package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Response messages from an LLM.
 *
 * @param content the content, if any.
 * @see Message
 */
public record AssistantMessage(@Nullable String content) implements Message {
  @Nonnull
  @Override
  public String type() {
    return "assistant";
  }
}
