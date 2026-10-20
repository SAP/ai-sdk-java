package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationUnitTest.CUSTOM_GPT_4O;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConfigToRequestTransformerTest {
  @Test
  void testEmptyTemplateConfig() {
    var systemMessage = new SystemMessage("foo");
    var userMessage = new UserMessage("Hello");

    var expected =
        Template.create()
            .template(List.of(systemMessage.createChatMessage(), userMessage.createChatMessage()));

    var prompt = PromptTemplate.create(systemMessage, userMessage);
    var actual = (Template) ConfigToRequestTransformer.toTemplateModuleConfig(prompt);

    assertThat(actual.getTemplate()).isEqualTo(expected.getTemplate());
    assertThat(actual.getTemplate())
        .describedAs(
            "The template should be copied to not modify an existing config which might be reused.")
        .isNotSameAs(prompt.getChatMessages());
  }

  @Test
  void testPreservesTemplateOptions() {
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

    var prompt =
        PromptTemplate.create(systemMessage, userMessage, userMessage2)
            .withDefaults(Map.of("city", "Paris"));
    var actual = (Template) ConfigToRequestTransformer.toTemplateModuleConfig(prompt);

    assertThat(actual.getTemplate()).isEqualTo(expected.getTemplate());
    assertThat(actual.getDefaults()).containsEntry("city", "Paris");
    assertThat(prompt.getChatMessages()).hasSize(3);
  }

  @Test
  void testMessagesHistory() {
    var systemMessage = new SystemMessage("foo");

    var prompt = PromptTemplate.create("bar").withMessageHistory(List.of(systemMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig(CUSTOM_GPT_4O));

    assertThat(actual.getMessagesHistory()).containsExactly(systemMessage.createChatMessage());
  }

  @Test
  void withCachingConstraintsAppliedTooManyCachePoints() {
    final var cachingCfg = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var maxCachedCheckpoints = cachingCfg.getMaxCheckpointsPerRequest();
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var userMessages = new ArrayList<Message>();
    // intentionally exceed number of checkpoints and tokens by 1
    final var msgText = "bar ".repeat(minTokensPerCheckpoint + 1).trim();
    for (int i = 0; i <= maxCachedCheckpoints; i++) {
      userMessages.add(new UserMessage(msgText, new CacheControl("5m")));
    }

    final var withCappedCacheControl = normalizedCacheControls(userMessages, cachingCfg);
    assertThat(withCappedCacheControl).hasSize(maxCachedCheckpoints);
  }

  @Test
  void withCachingConstraintsAppliedTooFewTokensInCachePoints() {
    final var cachingCfg = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var maxCachedCheckpoints = cachingCfg.getMaxCheckpointsPerRequest();
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var userMessages = new ArrayList<Message>();
    // intentionally exceed number of checkpoints and tokens by 1
    final var msgText = "bar ".repeat(minTokensPerCheckpoint - 1).trim();
    for (int i = 0; i <= maxCachedCheckpoints; i++) {
      userMessages.add(new UserMessage(msgText, new CacheControl("5m")));
    }

    final var withCappedCacheControl = normalizedCacheControls(userMessages, cachingCfg);
    assertThat(withCappedCacheControl).isEmpty();
  }

  @Test
  void withCachingConstraintsAppliedInvalidTTL() {
    final var cachingCfg = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var expectedTTL = cachingCfg.getDefaultTTLValue();
    final var invalidTTL = "99h";
    final var userMessage =
        new UserMessage(
            "Hello world".repeat(minTokensPerCheckpoint + 1), new CacheControl(invalidTTL));

    final var withCappedCacheControl = normalizedCacheControls(List.of(userMessage), cachingCfg);
    assertThat(withCappedCacheControl).hasSize(1);
    assertThat(withCappedCacheControl.get(0).get("ttl").asText()).isEqualTo(expectedTTL);
  }

  @Test
  void withCachingConstraintsAppliedSystemMessageCanBeCached() {
    final var cachingCfg = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    final var minTokensPerCheckpoint = cachingCfg.getMinTokensPerCheckpoint();
    final var systemMessage =
        new SystemMessage("Hello world".repeat(minTokensPerCheckpoint + 1), new CacheControl("5m"));

    final var withCappedCacheControl = normalizedCacheControls(List.of(systemMessage), cachingCfg);
    assertThat(withCappedCacheControl).hasSize(1);
  }

  @Test
  void testUserMessageHistory() {
    var userMessage = new UserMessage("foo");

    var prompt = PromptTemplate.create("bar").withMessageHistory(List.of(userMessage));
    var actual =
        ConfigToRequestTransformer.toCompletionPostRequest(
            prompt, new OrchestrationModuleConfig(CUSTOM_GPT_4O));

    assertThat(actual.getMessagesHistory()).containsExactly(userMessage.createChatMessage());
  }

  @Test
  void testRegistryReferenceParameters() {
    var params = Map.of("city", "Paris");
    var template = PromptTemplate.reference().byId("template-id").andInputParameters(params);
    var request =
        ConfigToRequestTransformer.toCompletionPostRequest(
            template, new OrchestrationModuleConfig(CUSTOM_GPT_4O));
    var modules =
        (com.sap.ai.sdk.orchestration.model.OrchestrationConfigModules.InnerModuleConfigs)
            request.getConfig().getModules();
    assertThat(modules.value().getPromptTemplating().getPrompt()).isEqualTo(template.toLowLevel());
    assertThat(request.getPlaceholderValues()).isEqualTo(params);
    assertThat(request.getMessagesHistory()).isEmpty();
  }

  @Test
  void testParsedTemplateHasEmptyHistoryAndParameters() throws Exception {
    var template =
        PromptTemplate.fromJson(
            """
        {"spec":{"template":[{"role":"user","content":"Hello"}]}}
        """);
    var messages = template.getChatMessages();
    assertThat(messages).isNotEmpty();
    assertThat(messages.get(0)).isExactlyInstanceOf(UserChatMessage.class);
    if (messages.get(0) instanceof UserChatMessage userMessage) {
      assertThat(((UserChatMessageContent.InnerString) userMessage.getContent()).value())
          .isEqualTo("Hello");
    }
    var request =
        ConfigToRequestTransformer.toCompletionPostRequest(
            template, new OrchestrationModuleConfig(CUSTOM_GPT_4O));
    assertThat(request.getMessagesHistory()).isEmpty();
    assertThat(request.getPlaceholderValues()).isEmpty();
  }

  @Test
  void testIdConfigReferenceDefaultsAndWithers() {
    var reference = OrchestrationConfigReference.fromId("config-id");
    var request =
        (com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceById)
            ConfigToRequestTransformer.fromReferenceToCompletionPostRequest(reference);
    assertThat(request.getMessagesHistory()).isEmpty();
    assertThat(request.getPlaceholderValues()).isEmpty();
    var updated =
        reference
            .withInputParameters(Map.of("city", "Paris"))
            .withMessageHistory(List.of(Message.user("Earlier")));
    var updatedRequest =
        (com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceById)
            ConfigToRequestTransformer.fromReferenceToCompletionPostRequest(updated);
    assertThat(updatedRequest.getMessagesHistory()).hasSize(1);
    assertThat(updatedRequest.getPlaceholderValues()).containsEntry("city", "Paris");
  }

  @Test
  void testFallbackCachingUsesEachModelWithoutMutatingTemplate() {
    var model = OrchestrationAiModel.CLAUDE_4_OPUS;
    var tokens = ModelPromptCachingSupport.forModel(model).getMinTokensPerCheckpoint();
    var template =
        PromptTemplate.create(Message.user("word ".repeat(tokens + 1), new CacheControl("5m")));
    var request =
        ConfigToRequestTransformer.toCompletionPostRequest(
            template,
            new OrchestrationModuleConfig(CUSTOM_GPT_4O),
            new OrchestrationModuleConfig(model));
    var modules =
        (com.sap.ai.sdk.orchestration.model.OrchestrationConfigModules.ListOfModuleConfigss)
            request.getConfig().getModules();
    var primary = (Template) modules.values().get(0).getPromptTemplating().getPrompt();
    var fallback = (Template) modules.values().get(1).getPromptTemplating().getPrompt();
    assertThat(cacheControl(primary)).isNull();
    assertThat(cacheControl(fallback)).isNotNull();
    assertThat(cacheControl((Template) template.toLowLevel())).isNotNull();
  }

  private static com.sap.ai.sdk.orchestration.model.CacheControl cacheControl(Template template) {
    var message =
        (com.sap.ai.sdk.orchestration.model.UserChatMessage) template.getTemplate().get(0);
    var content =
        (com.sap.ai.sdk.orchestration.model.UserChatMessageContent
                .ListOfUserChatMessageContentItems)
            message.getContent();
    return content.values().get(0).getCacheControl();
  }

  private static List<com.fasterxml.jackson.databind.JsonNode> normalizedCacheControls(
      List<Message> messages, ModelPromptCachingSupport config) {
    var template =
        PromptTemplate.create(
            messages.get(0), messages.subList(1, messages.size()).toArray(Message[]::new));
    var normalized = ConfigToRequestTransformer.toTemplateModuleConfig(template, config);
    return OrchestrationJacksonConfiguration.getOrchestrationObjectMapper()
        .valueToTree(normalized)
        .findValues("cache_control");
  }

  @Test
  void testToolCheckpointIsPreservedOrRemovedForModel() {
    var model = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    var tool =
        new ToolMessage(
            "call-id",
            "word ".repeat(model.getMinTokensPerCheckpoint() + 1),
            new CacheControl("5m"));
    assertThat(normalizedCacheControls(List.of(tool), model)).hasSize(1);
    assertThat(normalizedCacheControls(List.of(tool), ModelPromptCachingSupport.noCaching()))
        .isEmpty();
  }

  @Test
  void testMissingCacheTtlUsesServiceDefault() throws Exception {
    var model = ModelPromptCachingSupport.forModel(OrchestrationAiModel.CLAUDE_4_OPUS);
    var json =
        """
        {"spec":{"template":[{"role":"system","content":[
          {"type":"text","text":"%s","cache_control":{"type":"ephemeral"}}
        ]}]}}
        """
            .formatted("word ".repeat(model.getMinTokensPerCheckpoint() + 1));
    var template = PromptTemplate.fromJson(json);
    var result = ConfigToRequestTransformer.toTemplateModuleConfig(template, model);
    var caches =
        OrchestrationJacksonConfiguration.getOrchestrationObjectMapper()
            .valueToTree(result)
            .findValues("cache_control");
    assertThat(caches).hasSize(1);
    assertThat(caches.get(0).get("ttl").asText()).isEqualTo("5m");
  }

  @Test
  void testReferenceBuilderReuseDoesNotChangePreviousResult() {
    var builder = PromptTemplate.reference().byScenario("scenario").name("name");
    var first = builder.version("1").andInputParameters(Map.of());
    var expected = first.toLowLevel();
    builder.version("2").andInputParameters(Map.of()).toLowLevel();
    assertThat(first.toLowLevel()).isEqualTo(expected);
    first.withScope(PromptTemplateReference.ScopeEnum.RESOURCE_GROUP).toLowLevel();
    assertThat(first.toLowLevel()).isEqualTo(expected);
    assertThat(first.getReference())
        .isEqualTo(
            com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion.create()
                .scenario("scenario")
                .name("name")
                .version("1"));
  }
}
