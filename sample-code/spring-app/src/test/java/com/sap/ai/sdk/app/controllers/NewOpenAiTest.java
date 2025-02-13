package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionResponseMessageRole.ASSISTANT;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.NewOpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.CompletionUsage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class NewOpenAiTest {
  NewOpenAiService service;

  @BeforeEach
  void setUp() {
    service = new NewOpenAiService();
  }

  @Test
  void chatCompletion() {
    final var completion = service.chatCompletion("Who is the prettiest");

    assertThat(completion.getChoice().getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(completion.getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionImage() {
    final var completion =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo(ASSISTANT);
    assertThat(message.getContent()).isNotEmpty();
  }

  @Test
  void streamChatCompletion() {
    final var userMessage = OpenAiMessage.user("Who is the prettiest?");
    final var prompt = new OpenAiChatCompletionRequest(userMessage);

    final var totalOutput = new AtomicReference<CompletionUsage>();
    final var filledDeltaCount = new AtomicInteger(0);
    OpenAiClient.forModel(GPT_35_TURBO)
        .streamChatCompletionDeltas(prompt)
        // foreach consumes all elements, closing the stream at the end
        .forEach(
            delta -> {
              final var usage = delta.getCompletionUsage(new ObjectMapper());
              totalOutput.compareAndExchange(null, usage);
              final String deltaContent = delta.getDeltaContent();
              log.info("delta: {}", delta);
              if (!deltaContent.isEmpty()) {
                filledDeltaCount.incrementAndGet();
              }
            });

    // the first two and the last delta don't have any content
    // see OpenAiChatCompletionDelta#getDeltaContent
    assertThat(filledDeltaCount.get()).isGreaterThan(0);

    assertThat(totalOutput.get().getTotalTokens()).isGreaterThan(0);
    assertThat(totalOutput.get().getPromptTokens()).isEqualTo(14);
    assertThat(totalOutput.get().getCompletionTokens()).isGreaterThan(0);
  }

  @Test
  void chatCompletionTools() {
    final var completion = service.chatCompletionTools(12);

    final var message = completion.getChoice().getMessage();
    assertThat(message.getRole()).isEqualTo(ASSISTANT);
    assertThat(message.getToolCalls()).isNotNull();
    assertThat(message.getToolCalls().get(0).getFunction().getName()).isEqualTo("fibonacci");
  }

  @Test
  void embedding() {
    final var embedding = service.embedding("Hello world");

    assertThat(embedding.getData().get(0).getEmbedding()).hasSizeGreaterThan(1);
    assertThat(embedding.getModel()).isEqualTo("ada");
    assertThat(embedding.getObject()).isEqualTo("list");
  }

  @Test
  void chatCompletionWithResource() {
    final var completion =
        service.chatCompletionWithResource("ai-sdk-java-e2e", "Where is the nearest coffee shop?");

    assertThat(completion.getChoice().getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(completion.getContent()).isNotEmpty();
  }
}
