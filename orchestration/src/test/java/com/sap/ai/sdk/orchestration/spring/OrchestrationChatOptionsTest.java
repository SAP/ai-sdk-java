package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_2_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.OrchestrationAiModel;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrchestrationChatOptionsTest {

  static final OrchestrationAiModel CUSTOM_LLM =
      GEMINI_2_5_FLASH
          .withParam(FREQUENCY_PENALTY, 0.5)
          .withParam(MAX_TOKENS, 100)
          .withParam(PRESENCE_PENALTY, 0.5)
          .withParam("stop_sequences", List.of("\n"))
          .withParam(TEMPERATURE, 0.5)
          .withParam("top_k", 50)
          .withParam(TOP_P, 0.5);

  private static void assertCustomLLM(OrchestrationChatOptions opts) {
    assertThat(opts.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
    assertThat(opts.getModelVersion()).isEqualTo(GEMINI_2_5_FLASH.getVersion());
    assertThat(opts.getFrequencyPenalty()).isEqualTo(0.5);
    assertThat(opts.getMaxTokens()).isEqualTo(100);
    assertThat(opts.getPresencePenalty()).isEqualTo(0.5);
    assertThat(opts.getStopSequences()).containsExactly("\n");
    assertThat(opts.getTemperature()).isEqualTo(0.5);
    assertThat(opts.getTopK()).isEqualTo(50);
    assertThat(opts.getTopP()).isEqualTo(0.5);
  }

  @Test
  void testParametersAreInherited() {
    var opts =
        new OrchestrationChatOptions(
            new OrchestrationModuleConfig().withLlmConfig(GEMINI_2_5_FLASH));

    assertThat(opts.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
    assertThat(opts.getModelVersion()).isEqualTo(GEMINI_2_5_FLASH.getVersion());
  }

  @Test
  void testCustomParametersAreInherited() {
    var opts =
        new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(CUSTOM_LLM));

    assertCustomLLM(opts);
  }

  @Test
  void testCopy() {
    var opts =
        new OrchestrationChatOptions(
            new OrchestrationModuleConfig().withLlmConfig(GEMINI_2_5_FLASH));

    var copy = (OrchestrationChatOptions) opts.copy();
    assertThat(copy.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
    assertThat(copy.getModelVersion()).isEqualTo(GEMINI_2_5_FLASH.getVersion());
  }

  @Test
  void testCustomCopy() {
    var opts =
        new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(CUSTOM_LLM));

    var copy = (OrchestrationChatOptions) opts.copy();
    assertCustomLLM(copy);
  }
}
