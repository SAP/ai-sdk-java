package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

public record SystemMessage(@Nonnull String content) implements Message {
  @Nonnull
  @Override
  public String type() {
    return "system";
  }
}
