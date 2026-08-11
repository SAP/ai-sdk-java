package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.CUSTOM_GPT_4O;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.model.Template;
import java.util.ArrayList;
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
                    userMessage2.createChatMessage()))
            .defaults(Map.of("city", "Paris"));
    expected.setCustomField("country", "France");

    var prompt = new OrchestrationPrompt(userMessage2);
    var templateConfig =
        Template.create()
            .template(List.of(systemMessage.createChatMessage(), userMessage.createChatMessage()))
            .defaults(Map.of("city", "Paris"));
    templateConfig.setCustomField("country", "France");
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

  @Test
  void withCachingConstraintsAppliedTooManyCachePoints() {
    final var cachingCfg = PromptCachingConfig.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var maxCachedCheckpoints = cachingCfg.getMaxCheckpointsPerRequest();
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var userMessages = new ArrayList<Message>();
    // intentionally exceed number of checkpoints and tokens by 1
    final var msgText = "bar ".repeat(minTokensPerCheckpoint + 1).trim();
    for (int i = 0; i <= maxCachedCheckpoints; i++) {
      userMessages.add(new UserMessage(msgText, new CacheControl("5m")));
    }

    final var withCappedCacheControl =
        ConfigToRequestTransformer.withCachingConstraintsApplied(userMessages, cachingCfg);

    var cacheableMessagesAfterConstraintsApplication = 0L;
    for (var msg : withCappedCacheControl) {
      cacheableMessagesAfterConstraintsApplication +=
          msg.content().items().stream()
              .filter((i) -> ((TextItem) i).getCacheControl() != null)
              .count();
    }

    assertThat(cacheableMessagesAfterConstraintsApplication).isEqualTo(maxCachedCheckpoints);
  }

  @Test
  void withCachingConstraintsAppliedTooFewTokensInCachePoints() {
    final var cachingCfg = PromptCachingConfig.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var maxCachedCheckpoints = cachingCfg.getMaxCheckpointsPerRequest();
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var userMessages = new ArrayList<Message>();
    // intentionally exceed number of checkpoints and tokens by 1
    final var msgText = "bar ".repeat(minTokensPerCheckpoint - 1).trim();
    for (int i = 0; i <= maxCachedCheckpoints; i++) {
      userMessages.add(new UserMessage(msgText, new CacheControl("5m")));
    }

    final var withCappedCacheControl =
        ConfigToRequestTransformer.withCachingConstraintsApplied(userMessages, cachingCfg);

    var cacheableMessagesAfterConstraintsApplication = 0L;
    for (var msg : withCappedCacheControl) {
      cacheableMessagesAfterConstraintsApplication +=
          msg.content().items().stream()
              .filter((i) -> ((TextItem) i).getCacheControl() != null)
              .count();
    }

    assertThat(cacheableMessagesAfterConstraintsApplication).isEqualTo(0);
  }

  @Test
  void withCachingConstraintsAppliedInvalidTTL() {
    final var cachingCfg = PromptCachingConfig.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var expectedTTL = cachingCfg.getDefaultTTLValue();
    final var invalidTTL = "99h";
    final var userMessage =
        new UserMessage(
            "Hello world".repeat(minTokensPerCheckpoint + 1), new CacheControl(invalidTTL));

    final var withCappedCacheControl =
        ConfigToRequestTransformer.withCachingConstraintsApplied(List.of(userMessage), cachingCfg);

    var cacheableMessagesAfterConstraintsApplication = 0L;
    var correctedTTLCount = 0L;
    for (var msg : withCappedCacheControl) {
      cacheableMessagesAfterConstraintsApplication +=
          msg.content().items().stream()
              .filter((i) -> ((TextItem) i).getCacheControl().getTtl().equals(invalidTTL))
              .count();
      correctedTTLCount +=
          msg.content().items().stream()
              .filter((i) -> ((TextItem) i).getCacheControl().getTtl().equals(expectedTTL))
              .count();
    }

    assertThat(cacheableMessagesAfterConstraintsApplication).isEqualTo(0);
    assertThat(correctedTTLCount).isEqualTo(1);
  }

  @Test
  void withCachingConstraintsAppliedSystemMessageCanBeCached() {
    final var cachingCfg = PromptCachingConfig.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var systemMessage =
        new SystemMessage("Hello world".repeat(minTokensPerCheckpoint + 1), new CacheControl("5m"));

    final var withCappedCacheControl =
        ConfigToRequestTransformer.withCachingConstraintsApplied(
            List.of(systemMessage), cachingCfg);

    var cacheableMessagesAfterConstraintsApplication = 0L;
    for (var msg : withCappedCacheControl) {
      cacheableMessagesAfterConstraintsApplication +=
          msg.content().items().stream()
              .filter((i) -> ((TextItem) i).getCacheControl() != null)
              .count();
    }

    assertThat(cacheableMessagesAfterConstraintsApplication).isEqualTo(1);
  }

  @Test
  void testUserMessageHistory() {
    var userMessage = new UserMessage("foo");

    var prompt = new OrchestrationPrompt("bar").messageHistory(List.of(userMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_4O));

    assertThat(actual.getMessagesHistory()).containsExactly(userMessage.createChatMessage());
  }
}
