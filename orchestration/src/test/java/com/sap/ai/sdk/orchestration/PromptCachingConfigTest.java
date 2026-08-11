package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PromptCachingConfigTest {

  @Test
  void forModelReturnsNoCachingForUnknownModel() {
    final var unknownModelName = "unknownModel";

    assertThat(PromptCachingConfig.forModel(unknownModelName))
        .isEqualTo(PromptCachingConfig.noCaching());
  }

  @Test
  void forModelReturnsKnownConfigCorrectly() {
    final var knownModelName = OrchestrationAiModel.CLAUDE_4_6_SONNET.getName();

    assertThat(PromptCachingConfig.forModel(knownModelName))
        .isEqualTo(PromptCachingConfig.forModel(OrchestrationAiModel.CLAUDE_4_6_SONNET));
  }
}
