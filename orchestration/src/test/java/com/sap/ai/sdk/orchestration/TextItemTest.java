package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TextItemTest {

  @Test
  void testEquals() {
    assertThat(new TextItem("test").equals(null)).isFalse();
  }

  @Test
  void testHashCode() {
    assertThat(new TextItem("test").hashCode()).isEqualTo(new TextItem("test").hashCode());
    assertThat(new TextItem("test").hashCode()).isNotEqualTo(new TextItem("other").hashCode());
  }
}
