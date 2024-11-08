package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.LLM_CONFIG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConfigToRequestTransformerTest {

  @Test
  void testThrowsOnMissingLlmConfig() {
    assertThatThrownBy(
            () -> ConfigToRequestTransformer.toModuleConfigs(new OrchestrationModuleConfig()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM config is required");
  }

  @Test
  void testEmptyTemplateConfig() {
    var systemMessage = ChatMessage.create().role("system").content("foo");
    var userMessage = ChatMessage.create().role("user").content("Hello");

    var expected = TemplatingModuleConfig.create().template(systemMessage, userMessage);

    var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    var actual =
        ConfigToRequestTransformer.toTemplateModuleConfig(
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
    var actual = ConfigToRequestTransformer.toTemplateModuleConfig(prompt, templateConfig);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testMessagesHistory() {
    var systemMessage = ChatMessage.create().role("system").content("foo");

    var prompt = new OrchestrationPrompt("bar").messageHistory(List.of(systemMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig().withLlmConfig(LLM_CONFIG));

    assertThat(actual.getMessagesHistory()).containsExactly(systemMessage);
  }
}
