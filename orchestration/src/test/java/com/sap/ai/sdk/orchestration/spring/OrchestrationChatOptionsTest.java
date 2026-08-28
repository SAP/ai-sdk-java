package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_2_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.OrchestrationAiModel;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.ai.support.ToolCallbacks;

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

  private static OrchestrationChatOptions baseOpts() {
    return new OrchestrationChatOptions(
        new OrchestrationModuleConfig().withLlmConfig(GEMINI_2_5_FLASH));
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

  @Test
  void testBuilderModelOverride() {
    var built = baseOpts().mutate().model(GPT_4O.getName()).build();

    assertThat(built.getModel()).isEqualTo(GPT_4O.getName());
    // other fields from source are preserved
    assertThat(built.getModelVersion()).isEqualTo(GEMINI_2_5_FLASH.getVersion());
  }

  @Test
  void testBuilderFrequencyPenalty() {
    var built = baseOpts().mutate().frequencyPenalty(0.7).build();

    assertThat(built.getFrequencyPenalty()).isEqualTo(0.7);
  }

  @Test
  void testBuilderMaxTokens() {
    var built = baseOpts().mutate().maxTokens(200).build();

    assertThat(built.getMaxTokens()).isEqualTo(200);
  }

  @Test
  void testBuilderPresencePenalty() {
    var built = baseOpts().mutate().presencePenalty(0.3).build();

    assertThat(built.getPresencePenalty()).isEqualTo(0.3);
  }

  @Test
  void testBuilderStopSequences() {
    var built = baseOpts().mutate().stopSequences(List.of("stop", "end")).build();

    assertThat(built.getStopSequences()).containsExactly("stop", "end");
  }

  @Test
  void testBuilderTemperature() {
    var built = baseOpts().mutate().temperature(0.9).build();

    assertThat(built.getTemperature()).isEqualTo(0.9);
  }

  @Test
  void testBuilderTopK() {
    var built = baseOpts().mutate().topK(40).build();

    assertThat(built.getTopK()).isEqualTo(40);
  }

  @Test
  void testBuilderTopP() {
    var built = baseOpts().mutate().topP(0.8).build();

    assertThat(built.getTopP()).isEqualTo(0.8);
  }

  @Test
  void testBuilderAllScalarsAtOnce() {
    var built =
        baseOpts()
            .mutate()
            .model(GPT_4O.getName())
            .frequencyPenalty(0.1)
            .maxTokens(50)
            .presencePenalty(0.2)
            .stopSequences(List.of("\n"))
            .temperature(0.6)
            .topK(10)
            .topP(0.95)
            .build();

    assertThat(built.getModel()).isEqualTo(GPT_4O.getName());
    assertThat(built.getFrequencyPenalty()).isEqualTo(0.1);
    assertThat(built.getMaxTokens()).isEqualTo(50);
    assertThat(built.getPresencePenalty()).isEqualTo(0.2);
    assertThat(built.getStopSequences()).containsExactly("\n");
    assertThat(built.getTemperature()).isEqualTo(0.6);
    assertThat(built.getTopK()).isEqualTo(10);
    assertThat(built.getTopP()).isEqualTo(0.95);
  }

  @Test
  void testBuilderOverridesPreserveExistingParams() {
    // Source already has all params; builder should override only the ones specified
    var source =
        new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(CUSTOM_LLM));
    var built = source.mutate().temperature(0.99).build();

    assertThat(built.getTemperature()).isEqualTo(0.99);
    // other params unchanged from CUSTOM_LLM
    assertThat(built.getMaxTokens()).isEqualTo(100);
    assertThat(built.getFrequencyPenalty()).isEqualTo(0.5);
    assertThat(built.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
  }

  @Test
  void testBuilderDoesNotMutateSource() {
    var source = baseOpts();
    source.mutate().temperature(0.5).maxTokens(100).build();

    // source must be unchanged
    assertThat(source.getTemperature()).isNull();
    assertThat(source.getMaxTokens()).isNull();
  }

  @Test
  void testBuilderToolCallbacks() {
    var callbacks = ToolCallbacks.from(new WeatherMethod());
    var built = baseOpts().mutate().toolCallbacks(List.of(callbacks)).build();

    // The built result has the tool callbacks set (setToolCallbacks wires them into template config
    // too)
    assertThat(built.getToolCallbacks()).hasSize(1);
  }

  @Test
  void testBuilderToolContext() {
    var built = baseOpts().mutate().toolContext("key", "value").build();

    assertThat(built.getToolContext()).containsEntry("key", "value");
  }

  @Test
  void testBuilderToolContextMap() {
    var built = baseOpts().mutate().toolContext(Map.of("a", 1, "b", 2)).build();

    assertThat(built.getToolContext()).containsEntry("a", 1).containsEntry("b", 2);
  }

  @Test
  void testCombineWithOrchestrationBuilder() {
    var base = baseOpts();
    var perRequest =
        new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(GPT_4O));

    // Simulate what Spring AI does: starts from base.mutate(), then combines with
    // per-request.mutate()
    var combined = base.mutate().combineWith(perRequest.mutate().temperature(0.7));

    var result = combined.build();
    // Per-request source (GPT_4O) wins for OrchestrationChatOptions-specific config
    assertThat(result.getModel()).isEqualTo(GPT_4O.getName());
    // Per-request temperature override is carried through
    assertThat(result.getTemperature()).isEqualTo(0.7);
  }

  @Test
  void testCombineWithNonOrchestrationBuilderIsNoOp() {
    var base = baseOpts().mutate().temperature(0.4);
    var unrelated = org.springframework.ai.chat.prompt.ChatOptions.builder().temperature(0.9);

    var result = base.combineWith(unrelated).build();

    // combineWith a non-OrchestrationChatOptions.Builder is a no-op; base values survive
    assertThat(result.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
    assertThat(result.getTemperature()).isEqualTo(0.4);
  }

  @Test
  void testMutateProducesOrchestrationChatOptions() {
    var opts = baseOpts();
    var builder = opts.mutate();
    var result = builder.build();

    assertThat(result).isInstanceOf(OrchestrationChatOptions.class);
    assertThat(result.getModel()).isEqualTo(GEMINI_2_5_FLASH.getName());
  }
}
