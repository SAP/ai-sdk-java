package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.anthropic.models.messages.StopReason;
import com.sap.ai.sdk.app.services.AnthropicService;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class AnthropicTest {
  final AnthropicService service = new AnthropicService();

  @Test
  void chatCompletion() {
    final var message = service.chatCompletion("What is the capital of France?");

    assertThat(message).isNotNull();
    assertThat(message.content()).isNotEmpty();
    final var text =
        message.content().stream()
            .filter(b -> b.isText())
            .map(b -> b.text().map(t -> t.text()).orElse(""))
            .reduce("", String::concat);
    assertThat(text).containsIgnoringCase("paris");
    assertThat(message.stopReason()).hasValue(StopReason.END_TURN);
    assertThat(message.usage().outputTokens()).isGreaterThan(0);
  }

  @Test
  void streamChatCompletion() {
    final var fullResponse = new AtomicReference<>("");

    try (var stream = service.streamChatCompletion("What is the capital of France?")) {
      stream.stream()
          .filter(event -> event.isContentBlockDelta())
          .map(event -> event.asContentBlockDelta().delta())
          .filter(delta -> delta.isText())
          .map(delta -> delta.asText().text())
          .forEach(text -> fullResponse.accumulateAndGet(text, String::concat));
    }

    assertThat(fullResponse.get()).containsIgnoringCase("paris");
  }
}
