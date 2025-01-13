package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrchestrationChatOptionsTest {

  OrchestrationChatOptions opts;

  @BeforeEach
  void setUp() {
    opts = new OrchestrationChatOptions();
  }

  @Test
  void testHyperParameters() {
    var llm = GEMINI_1_5_FLASH.withParam(TEMPERATURE, 0.5).withParam("maxTokens", 100);
    var cac = opts.withLlmConfig(llm);
    opts.config = cac;

    assertThat(opts.getTemperature()).isEqualTo(0.5);
    assertThat(opts.getMaxTokens()).isEqualTo(100);
  }
}
