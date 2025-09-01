package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionResponseMessageRole.ASSISTANT;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CompletionUsage;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenAiTest {
  OpenAiService service;

  @BeforeEach
  void setUp() {
    service = new OpenAiService();
  }

  @Test
  void chatCompletion() {
    final var completion = service.chatCompletion("Who is the prettiest");

    assertThat(completion.getChoice().getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(completion.getContent()).isNotEmpty();
  }

  @Test
  void testMessagesHistory() {
    assertThat(service.messagesHistory("What is the capital of France?").getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionImage() {
    final var completion =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");

    assertThat(completion.getContent()).isNotEmpty();
    assertThat(completion.getChoice().getMessage().getRole()).isEqualTo(ASSISTANT);
  }

  @Test
  void streamChatCompletion() {
    final var userMessage = OpenAiMessage.user("Who is the prettiest?");
    final var prompt = new OpenAiChatCompletionRequest(userMessage);

    final var usageRef = new AtomicReference<CompletionUsage>();
    final var filledDeltaCount = new AtomicInteger(0);

    OpenAiClient.forModel(GPT_4O_MINI)
        .streamChatCompletionDeltas(prompt)
        // foreach consumes all elements, closing the stream at the end
        .forEach(
            delta -> {
              usageRef.compareAndExchange(null, delta.getCompletionUsage());
              final String deltaContent = delta.getDeltaContent();
              log.info("delta: {}", delta);
              if (!deltaContent.isEmpty()) {
                filledDeltaCount.incrementAndGet();
              }
            });

    assertThat(filledDeltaCount.get()).isGreaterThan(0);

    assertThat(usageRef.get().getTotalTokens()).isGreaterThan(0);
    assertThat(usageRef.get().getPromptTokens()).isGreaterThan(0);
    assertThat(usageRef.get().getCompletionTokens()).isGreaterThan(0);
  }

  @Test
  void embedding() {
    final var embedding = service.embedding("Hello world");

    assertThat(embedding.getOriginalResponse().getData().get(0).getEmbedding())
        .hasSizeGreaterThan(1);
    assertThat(embedding.getEmbeddingVectors()).isInstanceOf(ArrayList.class);
    assertThat(embedding.getEmbeddingVectors().get(0)).isInstanceOf(float[].class);

    assertThat(embedding.getOriginalResponse().getModel()).isEqualTo("text-embedding-3-small");
    assertThat(embedding.getOriginalResponse().getObject()).isEqualTo("list");
  }

  @Test
  void chatCompletionWithResource() {
    final var completion =
        service.chatCompletionWithResource("ai-sdk-java-e2e", "Where is the nearest coffee shop?");

    assertThat(completion.getChoice().getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(completion.getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionToolExecution() {
    final var completion = service.chatCompletionToolExecution("Dubai", "°C");

    String content = completion.getContent();

    assertThat(content).contains("°C");
  }
}
