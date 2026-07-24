package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import lombok.Getter;

@Getter
public final class CacheControl {
  private final String ttl;

  public CacheControl(@Nonnull String ttl) {
    this.ttl = ttl;
  }
}
