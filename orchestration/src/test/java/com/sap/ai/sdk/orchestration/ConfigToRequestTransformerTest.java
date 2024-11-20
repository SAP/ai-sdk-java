package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.CUSTOM_GPT_35;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.DPIConfig;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.Template;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConfigToRequestTransformerTest {

  @Test
  void testThrowsOnMissingLlmConfig() {
    var config = new OrchestrationModuleConfig();
    assertThatThrownBy(() -> ConfigToRequestTransformer.toModuleConfigs(config))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM config is required");
  }

  @Test
  void testThrowsOnMissingMessages() {
    var prompt = new OrchestrationPrompt(Map.of());

    assertThatThrownBy(() -> ConfigToRequestTransformer.toTemplateModuleConfig(prompt, null))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("A prompt is required");
  }

  @Test
  void testEmptyTemplateConfig() {
    var systemMessage = new ChatMessage().role("system").content("foo");
    var userMessage = new ChatMessage().role("user").content("Hello");

    var expected = new Template().template(List.of(systemMessage, userMessage));

    var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    var actual =
        (Template)
            ConfigToRequestTransformer.toTemplateModuleConfig(
                prompt, new Template().template(List.of()));

    assertThat(actual).isEqualTo(expected);
    assertThat(actual.getTemplate())
        .describedAs(
            "The template should be copied to not modify an existing config which might be reused.")
        .isNotSameAs(expected.getTemplate());
  }

  @Test
  void testMergingTemplateConfig() {
    var systemMessage = new ChatMessage().role("system").content("foo");
    var userMessage = new ChatMessage().role("user").content("Hello ");
    var userMessage2 = new ChatMessage().role("user").content("World");

    var expected = new Template().template(List.of(systemMessage, userMessage, userMessage2));

    var prompt = new OrchestrationPrompt(userMessage2);
    var templateConfig = new Template().template(List.of(systemMessage, userMessage));
    var actual = ConfigToRequestTransformer.toTemplateModuleConfig(prompt, templateConfig);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testMessagesHistory() {
    var systemMessage = new ChatMessage().role("system").content("foo");

    var prompt = new OrchestrationPrompt("bar").messageHistory(List.of(systemMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_35));

    assertThat(actual.getMessagesHistory()).containsExactly(systemMessage);
  }

  @Test
  void testDpiMaskingConfig() {
    var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.ADDRESS);
    var config =
        new OrchestrationModuleConfig()
            .withLlmConfig(CUSTOM_GPT_35)
            .withMaskingConfig(maskingConfig);

    var actual = ConfigToRequestTransformer.toModuleConfigs(config);

    assertThat(actual.getMaskingModuleConfig()).isNotNull();
    assertThat(actual.getMaskingModuleConfig().getMaskingProviders()).hasSize(1);
    DPIConfig dpiConfig = (DPIConfig) actual.getMaskingModuleConfig().getMaskingProviders().get(0);
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
    OrchestrationAiModel aiModel = GPT_4O.withModelParams(params).withModelVersion(version);
    var config = new OrchestrationModuleConfig().withLlmConfig(aiModel);

    var actual = ConfigToRequestTransformer.toModuleConfigs(config);

    assertThat(actual.getLlmModuleConfig()).isNotNull();
    assertThat(actual.getLlmModuleConfig().getModelName()).isEqualTo(GPT_4O.getModelName());
    assertThat(actual.getLlmModuleConfig().getModelParams()).isEqualTo(params);
    assertThat(actual.getLlmModuleConfig().getModelVersion()).isEqualTo(version);

    assertThat(GPT_4O.getModelParams())
        .withFailMessage("Static models should be unchanged")
        .isEmpty();
    assertThat(GPT_4O.getModelVersion())
        .withFailMessage("Static models should be unchanged")
        .isEqualTo("latest");
  }
}
