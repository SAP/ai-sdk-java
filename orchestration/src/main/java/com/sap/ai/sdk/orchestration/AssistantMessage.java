package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

public record AssistantMessage(@Nonnull String content) implements Message {
  @Nonnull
  @Override
  public String type() {
    return "assistant";
  }
}
