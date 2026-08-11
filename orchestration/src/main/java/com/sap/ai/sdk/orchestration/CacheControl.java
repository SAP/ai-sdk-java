package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import lombok.Getter;

/** Represents CacheControl object, used in prompt caching API */
@Getter
public final class CacheControl {
  private final String ttl;

  /**
   * Constructs cache control object with a ttl
   *
   * @param ttl time to live for the cache
   */
  public CacheControl(@Nonnull final String ttl) {
    this.ttl = ttl;
  }
}
