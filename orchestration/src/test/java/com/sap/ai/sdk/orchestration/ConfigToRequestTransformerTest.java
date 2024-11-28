package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.CUSTOM_GPT_35;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.generated.ChatMessage;
import com.sap.ai.sdk.orchestration.generated.Template;
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
    var systemMessage = ChatMessage.create().role("system").content("foo");
    var userMessage = ChatMessage.create().role("user").content("Hello");

    var expected = Template.create().template(List.of(systemMessage, userMessage));

    var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    var actual =
        (Template)
            ConfigToRequestTransformer.toTemplateModuleConfig(
                prompt, Template.create().template(List.of()));

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

    var expected = Template.create().template(List.of(systemMessage, userMessage, userMessage2));

    var prompt = new OrchestrationPrompt(userMessage2);
    var templateConfig = Template.create().template(List.of(systemMessage, userMessage));
    var actual = ConfigToRequestTransformer.toTemplateModuleConfig(prompt, templateConfig);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testMessagesHistory() {
    var systemMessage = ChatMessage.create().role("system").content("foo");

    var prompt = new OrchestrationPrompt("bar").messageHistory(List.of(systemMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_35));

    assertThat(actual.getMessagesHistory()).containsExactly(systemMessage);
  }
}
