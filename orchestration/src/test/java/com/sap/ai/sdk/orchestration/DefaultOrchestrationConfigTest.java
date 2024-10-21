package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class DefaultOrchestrationConfigTest {
  private static final OrchestrationConfig<?> DEFAULT_CONFIG =
      DefaultOrchestrationConfig.standalone()
          .withLlmConfig(mock(LlmConfig.class))
          .withMaskingConfig(mock(MaskingConfig.class));

  @Test
  void testStandalone() {
    var config = DefaultOrchestrationConfig.standalone();

    assertThat(config.withMaskingConfig(null)).isSameAs(config);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testDelegation() {
    var mock = mock(OrchestrationConfig.class);

    var config = DefaultOrchestrationConfig.asDelegateFor(mock);
    assertThat(config.withMaskingConfig(null)).isSameAs(mock);
  }

  @Test
  void testCopy() {
    var config = DefaultOrchestrationConfig.standalone();
    var duplicate = config.copyFrom(DEFAULT_CONFIG);

    assertThat(duplicate)
        .isEqualTo(DEFAULT_CONFIG)
        .hasSameHashCodeAs(DEFAULT_CONFIG)
        .isSameAs(config)
        .isNotSameAs(DEFAULT_CONFIG);
  }

  @Test
  void testApplyingDefaults() {
    var config = DefaultOrchestrationConfig.standalone();
    var llm = new LlmConfig("foo");
    config.withLlmConfig(llm);

    config.copyFrom(DEFAULT_CONFIG);

    assertThat(config)
        .isNotEqualTo(DEFAULT_CONFIG)
        .extracting(OrchestrationConfig::getLlmConfig)
        .extracting(Option::get)
        .isEqualTo(llm);
  }
}
