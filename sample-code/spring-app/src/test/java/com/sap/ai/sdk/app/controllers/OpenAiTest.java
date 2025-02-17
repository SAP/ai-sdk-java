package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import java.util.concurrent.atomic.AtomicInteger;
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

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionImage() {
    final var completion =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getContent()).isNotEmpty();
  }

  @Test
  void streamChatCompletion() {
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatUserMessage().addText("Who is the prettiest?"));

    final var totalOutput = new OpenAiChatCompletionOutput();
    final var filledDeltaCount = new AtomicInteger(0);
    OpenAiClient.forModel(GPT_35_TURBO)
        .streamChatCompletionDeltas(request)
        .peek(totalOutput::addDelta)
        // foreach consumes all elements, closing the stream at the end
        .forEach(
            delta -> {
              final String deltaContent = delta.getDeltaContent();
              log.info("delta: {}", delta);
              if (!deltaContent.isEmpty()) {
                filledDeltaCount.incrementAndGet();
              }
            });

    // the first two and the last delta don't have any content
    // see OpenAiChatCompletionDelta#getDeltaContent
    assertThat(filledDeltaCount.get()).isGreaterThan(0);

    assertThat(totalOutput.getChoices()).isNotEmpty();
    assertThat(totalOutput.getChoices().get(0).getMessage().getContent()).isNotEmpty();
    assertThat(totalOutput.getPromptFilterResults()).isNotNull();
    assertThat(totalOutput.getChoices().get(0).getContentFilterResults()).isNotNull();
  }

  @Test
  void chatCompletionTools() {
    final var completion = service.chatCompletionTools(12);

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
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

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getContent()).isNotEmpty();
  }
}
