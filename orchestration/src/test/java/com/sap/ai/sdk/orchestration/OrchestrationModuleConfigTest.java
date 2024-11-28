package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE_LOW_MEDIUM;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
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
}
