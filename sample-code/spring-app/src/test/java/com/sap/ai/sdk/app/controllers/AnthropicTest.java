package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.AnthropicService;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class AnthropicTest {
  final AnthropicService service = new AnthropicService();

  @Test
  void chatCompletion() {
    final var response = service.chatCompletion("What is the capital of France?");

    assertThat(response).isNotNull();
    assertThat(response.getContent()).containsIgnoringCase("paris");
    assertThat(response.getStopReason()).isEqualTo("end_turn");
    assertThat(response.getUsage()).isNotNull();
    assertThat(response.getUsage().getOutputTokens()).isGreaterThan(0);
  }

  @Test
  void streamChatCompletion() {
    final var fullResponse = new AtomicReference<>("");

    service
        .streamChatCompletion("What is the capital of France?")
        .forEach(delta -> fullResponse.accumulateAndGet(delta.getDeltaContent(), String::concat));

    assertThat(fullResponse.get()).containsIgnoringCase("paris");
  }
}
