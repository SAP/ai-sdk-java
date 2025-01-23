package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE_LOW_MEDIUM;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.model.DataRepositoryType.VECTOR;
import static com.sap.ai.sdk.orchestration.model.GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfigFiltersInner;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OrchestrationModuleConfigTest {

  @Test
  void testStackingInputAndOutputFilter() {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O);

    final var filter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE_LOW_MEDIUM)
            .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
            .sexual(ALLOW_SAFE_LOW_MEDIUM)
            .violence(ALLOW_SAFE_LOW_MEDIUM);

    final var configWithInputFirst = config.withInputFiltering(filter).withOutputFiltering(filter);
    assertThat(configWithInputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithInputFirst.getFilteringConfig().getInput()).isNotNull();

    final var configWithOutputFirst = config.withOutputFiltering(filter).withInputFiltering(filter);
    assertThat(configWithOutputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithOutputFirst.getFilteringConfig().getOutput()).isNotNull();
  }

  @Test
  void testThrowOnEmptyFilterConfig() {

    final var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O);

    assertThatThrownBy(() -> config.withInputFiltering(new AzureContentFilter()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter category must be set");
    assertThatThrownBy(() -> config.withOutputFiltering(new AzureContentFilter()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter category must be set");
  }

  @Test
  void testDpiMaskingConfig() {
    var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.ADDRESS);
    var config =
        new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withMaskingConfig(maskingConfig);

    assertThat(config.getMaskingConfig()).isNotNull();
    assertThat(config.getMaskingConfig().getMaskingProviders()).hasSize(1);
    DPIConfig dpiConfig = (DPIConfig) config.getMaskingConfig().getMaskingProviders().get(0);
    assertThat(dpiConfig.getMethod()).isEqualTo(DPIConfig.MethodEnum.ANONYMIZATION);
    assertThat(dpiConfig.getEntities()).hasSize(1);
    assertThat(dpiConfig.getEntities().get(0).getType()).isEqualTo(DPIEntities.ADDRESS);

    var configModified = config.withMaskingConfig(maskingConfig);
    assertThat(configModified.getMaskingConfig()).isNotNull();
    assertThat(configModified.getMaskingConfig().getMaskingProviders())
        .withFailMessage("withMaskingConfig() should overwrite the existing config and not append")
        .hasSize(1);
  }

  @Test
  void testParams() {
    // test withParams(Map<String, Object>)
    {
      var params = Map.<String, Object>of("foo", "bar", "fizz", "buzz");

      var modelA = GPT_4O.withParams(params);
      var modelB = modelA.withParams(params);
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParams(Map.of("foo", "bar"));
      assertThat(modelA).isNotEqualTo(modelC);

      var modelD = modelA.withParams(Map.of("foo", "bazz"));
      assertThat(modelA).isNotEqualTo(modelD);
    }

    // test withParam(String, Object)
    {
      var modelA = GPT_4O.withParam("foo", "bar");
      var modelB = modelA.withParam("foo", "bar");
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParam("foo", "bazz");
      assertThat(modelA).isNotEqualTo(modelC);
    }

    // test withParam(Parameter, Object)
    {
      var modelA = GPT_4O.withParam(MAX_TOKENS, 10);
      var modelB = modelA.withParam(MAX_TOKENS, 10);
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParam(MAX_TOKENS, 20);
      assertThat(modelA).isNotEqualTo(modelC);
    }
  }

  @Test
  void testLLMConfig() {
    Map<String, Object> params = Map.of("foo", "bar");
    String version = "2024-05-13";
    OrchestrationAiModel aiModel = GPT_4O.withParams(params).withVersion(version);
    var config = new OrchestrationModuleConfig().withLlmConfig(aiModel);

    assertThat(config.getLlmConfig()).isNotNull();
    assertThat(config.getLlmConfig().getModelName()).isEqualTo(GPT_4O.getName());
    assertThat(config.getLlmConfig().getModelParams()).isEqualTo(params);
    assertThat(config.getLlmConfig().getModelVersion()).isEqualTo(version);

    assertThat(GPT_4O.getParams()).withFailMessage("Static models should be unchanged").isEmpty();
    assertThat(GPT_4O.getVersion())
        .withFailMessage("Static models should be unchanged")
        .isEqualTo("latest");
  }

  @Test
  void testGroundingConfig() {
    var groundingConfig = Grounding.create();
    var config =
        new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withGrounding(groundingConfig);

    assertThat(config.getGroundingConfig()).isNotNull();
    assertThat(config.getGroundingConfig().getType()).isEqualTo(DOCUMENT_GROUNDING_SERVICE);

    GroundingModuleConfigConfig configConfig = config.getGroundingConfig().getConfig();
    assertThat(configConfig).isNotNull();
    assertThat(configConfig.getInputParams()).containsExactly("groundingInput");
    assertThat(configConfig.getOutputParam()).isEqualTo("groundingOutput");

    List<GroundingModuleConfigConfigFiltersInner> filters = configConfig.getFilters();
    assertThat(filters).hasSize(1);
    DocumentGroundingFilter filter = (DocumentGroundingFilter) filters.get(0);
    assertThat(filter.getId()).isEqualTo("someID");
    assertThat(filter.getDataRepositoryType()).isEqualTo(VECTOR);
  }

  @Test
  void testGroundingPrompt() {
    var prompt = Grounding.create().createGroundingPrompt("Hello, World!");
    assertThat(prompt.getMessages()).hasSize(1);
    var message = prompt.getMessages().get(0);
    assertThat(message.content())
        .isEqualTo(
            "{{?groundingInput}} Use the following information as additional context: {{?groundingOutput}}");
  }
}
