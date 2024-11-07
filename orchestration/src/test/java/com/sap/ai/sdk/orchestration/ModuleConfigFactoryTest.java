package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ModuleConfigFactoryTest {

  @Test
  void testThrowsOnMissingLlmConfig() {
    assertThatThrownBy(() -> ModuleConfigFactory.toModuleConfigsDto(new OrchestrationModuleConfig()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("A prompt is required");
  }

  @Test
  void testThrowsOnMissingMessages() {
    var prompt = new OrchestrationPrompt(Map.of());

    assertThatThrownBy(() -> ModuleConfigFactory.toTemplateModuleConfigDto(prompt, null))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("A prompt is required");
  }

  @Test
  void testEmptyTemplateConfig() {
    var systemMessage = ChatMessage.create().role("system").content("foo");
    var userMessage = ChatMessage.create().role("user").content("Hello");

    var expected = TemplatingModuleConfig.create().template(systemMessage, userMessage);

    var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    var actual =
        ModuleConfigFactory.toTemplateModuleConfigDto(
            prompt, TemplatingModuleConfig.create().template());

    assertThat(actual).isEqualTo(expected);
    assertThat(actual.getTemplate())
        .describedAs(
            "The template should be copied to not modify an existing config which might be reused.")
        .isNotSameAs(expected.getTemplate());
  }

  @Test
  void testMergingTemplateConfig() {
    var systemMessage = ChatMessage.create().role("system").content("foo");
    var userMessage = ChatMessage.create().role("user").content("Hello ");
    var userMessage2 = ChatMessage.create().role("user").content("World");

    var expected =
        TemplatingModuleConfig.create().template(systemMessage, userMessage, userMessage2);

    var prompt = new OrchestrationPrompt(userMessage2);
    var templateConfig = TemplatingModuleConfig.create().template(systemMessage, userMessage);
    var actual = ModuleConfigFactory.toTemplateModuleConfigDto(prompt, templateConfig);

    assertThat(actual).isEqualTo(expected);
  }
}
