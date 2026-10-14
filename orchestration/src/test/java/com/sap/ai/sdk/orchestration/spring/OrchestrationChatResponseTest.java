package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.model.ModuleResults;
import com.sap.ai.sdk.orchestration.model.ResponseChatMessage;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.Generation;

class OrchestrationChatResponseTest {

  @Test
  void testToGeneration() {
    var choice =
        LLMChoice.create()
            .index(0)
            .message(
                ResponseChatMessage.create()
                    .role(ResponseChatMessage.RoleEnum.ASSISTANT)
                    .content("Hello, world!"))
            .finishReason("stop");

    Generation generation = OrchestrationSpringChatResponse.toGeneration(choice);

    assertThat(generation.getOutput().getText()).isEqualTo("Hello, world!");
    assertThat(generation.getMetadata().getFinishReason()).isEqualTo("stop");
    assertThat(generation.getMetadata().<Integer>get("index")).isEqualTo(0);
  }

  @Test
  void testToChatResponseMetadata() {
    var moduleResult =
        LLMModuleResult.create()
            .id("test-id")
            ._object("test-object")
            .created(123456789)
            .model("test-model")
            .choices(List.of())
            .usage(TokenUsage.create().completionTokens(20).promptTokens(10).totalTokens(30));

    var metadata = OrchestrationSpringChatResponse.toChatResponseMetadata(moduleResult, null);

    assertThat(metadata.getId()).isEqualTo("test-id");
    assertThat(metadata.getModel()).isEqualTo("test-model");
    assertThat(metadata.<String>get("object")).isEqualTo("test-object");
    assertThat(metadata.<Integer>get("created")).isEqualTo(123456789);

    var usage = metadata.getUsage();

    assertThat(usage.getPromptTokens()).isEqualTo(10L);
    assertThat(usage.getCompletionTokens()).isEqualTo(20L);
    assertThat(usage.getTotalTokens()).isEqualTo(30L);
  }

  @Test
  void testCumulativeUsageIsAddedOnConstruction() {
    var previousUsage = new DefaultUsage(7, 3, 10);
    var result = response(counts(11, 5, 16), previousUsage);
    assertThat(result.getMetadata().getUsage().getPromptTokens()).isEqualTo(18);
    assertThat(result.getMetadata().getUsage().getCompletionTokens()).isEqualTo(8);
    assertThat(result.getMetadata().getUsage().getTotalTokens()).isEqualTo(26);
  }

  private static TokenUsage counts(Integer prompt, Integer completion, Integer total) {
    return TokenUsage.create().completionTokens(completion).promptTokens(prompt).totalTokens(total);
  }

  private static LLMModuleResult module(TokenUsage usage) {
    return LLMModuleResult.create()
        .id("id")
        ._object("chat.completion")
        .created(1)
        .model("model")
        .choices(List.of())
        .usage(usage);
  }

  private static OrchestrationSpringChatResponse response(
      TokenUsage usage, @Nullable Usage previousUsage) {
    var module = module(usage);
    var dto =
        CompletionPostResponse.create()
            .requestId("request")
            .intermediateResults(ModuleResults.create().llm(module))
            .finalResult(module);
    var client =
        new OrchestrationClient() {
          @Override
          @Nonnull
          public CompletionPostResponse executeRequest(@Nonnull CompletionPostRequest request) {
            return dto;
          }
        };
    return new OrchestrationSpringChatResponse(
        client.chatCompletion(
            new OrchestrationPrompt("test"), new OrchestrationModuleConfig().withLlmConfig(GPT_4O)),
        previousUsage);
  }
}
