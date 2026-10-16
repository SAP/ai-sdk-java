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
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.model.ChatResponse;
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

    var metadata = OrchestrationSpringChatResponse.toChatResponseMetadata(moduleResult);

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
  void testGenericFinalResponseKeepsItsGenerationsAndMetadata() {
    var metadata =
        ChatResponseMetadata.builder()
            .id("final-id")
            .model("final-model")
            .usage(new DefaultUsage(7, 3, 10))
            .keyValue("object", "chat.completion")
            .keyValue("created", 123)
            .keyValue("custom", "keep")
            .build();
    var last =
        new ChatResponse(List.of(new Generation(new AssistantMessage("final answer"))), metadata);
    var result = response(counts(11, 5, 16)).withCumulativeUsage(last);
    assertThat(result.getResults()).containsExactlyElementsOf(last.getResults());
    assertThat(result.getMetadata().getId()).isEqualTo("final-id");
    assertThat(result.getMetadata().getModel()).isEqualTo("final-model");
    assertThat(result.getMetadata().<String>get("object")).isEqualTo("chat.completion");
    assertThat(result.getMetadata().<Integer>get("created")).isEqualTo(123);
    assertThat(result.getMetadata().<String>get("custom")).isEqualTo("keep");
    assertThat(result.getMetadata().getRateLimit()).isSameAs(metadata.getRateLimit());
    assertThat(result.getMetadata().getPromptMetadata()).isSameAs(metadata.getPromptMetadata());
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

  private static OrchestrationSpringChatResponse response(TokenUsage usage) {
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
            new OrchestrationPrompt("test"),
            new OrchestrationModuleConfig().withLlmConfig(GPT_4O)));
  }
}
