package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.CUSTOM_GPT_4O;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.model.Template;
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
    var systemMessage = new SystemMessage("foo");
    var userMessage = new UserMessage("Hello");

    var expected =
        Template.create()
            .template(List.of(systemMessage.createChatMessage(), userMessage.createChatMessage()));

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
    var systemMessage = new SystemMessage("foo");
    var userMessage = new UserMessage("Hello ");
    var userMessage2 = new UserMessage("World");

    var expected =
        Template.create()
            .template(
                List.of(
                    systemMessage.createChatMessage(),
                    userMessage.createChatMessage(),
                    userMessage2.createChatMessage()));

    var prompt = new OrchestrationPrompt(userMessage2);
    var templateConfig =
        Template.create()
            .template(List.of(systemMessage.createChatMessage(), userMessage.createChatMessage()));
    var actual = ConfigToRequestTransformer.toTemplateModuleConfig(prompt, templateConfig);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testMessagesHistory() {
    var systemMessage = new SystemMessage("foo");

    var prompt = new OrchestrationPrompt("bar").messageHistory(List.of(systemMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_4O));

    assertThat(actual.getMessagesHistory()).containsExactly(systemMessage.createChatMessage());
  }
}
