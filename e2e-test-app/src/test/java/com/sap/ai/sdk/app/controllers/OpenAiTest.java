package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpenAiTest {
  @Test
  void chatCompletion() {
    final var completion = OpenAiController.chatCompletion();

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionImage() {
    final var completion = OpenAiController.chatCompletionImage();

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getContent()).isNotEmpty();
  }

  @Test
  void chatCompletionTools() {
    final var completion = OpenAiController.chatCompletionTools();

    final var message = completion.getChoices().get(0).getMessage();
    assertThat(message.getRole()).isEqualTo("assistant");
    assertThat(message.getTool_calls().get(0).getFunction().getName()).isEqualTo("fibonacci");
  }

  @Test
  void embedding() {
    final var embedding = OpenAiController.embedding();

    // {"object":"list","model":"ada","data":[{"object":"embedding","embedding":[-0.0070958645....,-0.0014557659],"index":0}],"usage":{"completion_tokens":null,"prompt_tokens":2,"total_tokens":2}}
    assertThat(embedding.getData().get(0).getEmbedding()).hasSizeGreaterThan(1);
    assertThat(embedding.getModel()).isEqualTo("ada");
    assertThat(embedding.getObject()).isEqualTo("list");
  }
}
