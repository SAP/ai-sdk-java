package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CacheControlTest {

  @Test
  void getTtl() {
    final String ttl = "5m";
    final CacheControl cacheControl = new CacheControl(ttl);

    assertThat(ttl).isEqualTo(cacheControl.getTtl());
  }
}
