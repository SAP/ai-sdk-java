package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ModelPromptCachingSupportTest {

  @Test
  void forModelReturnsNoCachingForUnknownModel() {
    final var unknownModelName = "unknownModel";

    assertThat(ModelPromptCachingSupport.forModel(unknownModelName))
        .isEqualTo(ModelPromptCachingSupport.noCaching());
  }

  @Test
  void forModelReturnsKnownConfigCorrectly() {
    final var knownModelName = OrchestrationAiModel.CLAUDE_4_6_SONNET.getName();

    assertThat(ModelPromptCachingSupport.forModel(knownModelName))
        .isEqualTo(ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_6_SONNET));
  }
}
