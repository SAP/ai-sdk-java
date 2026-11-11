package com.sap.ai.sdk.app.controllers;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

import com.anthropic.models.messages.ContentBlock;
import com.anthropic.models.messages.RawContentBlockDelta;
import com.anthropic.models.messages.RawMessageStreamEvent;
import com.anthropic.models.messages.StopReason;
import com.sap.ai.sdk.app.services.AnthropicService;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class AnthropicTest {
  final AnthropicService service = new AnthropicService();

  @Test
  void chatCompletion() {
    final var message = service.sendMessage("What is the capital of France?");

    assertThat(message.content()).isNotEmpty();
    final var text =
        message.content().stream()
            .filter(ContentBlock::isText)
            .map(b -> b.asText().text())
            .collect(joining());
    assertThat(text).containsIgnoringCase("paris");
    assertThat(message.stopReason()).hasValue(StopReason.END_TURN);
    assertThat(message.usage().outputTokens()).isGreaterThan(0);
  }

  @Test
  void streamChatCompletion() {
    final var fullResponse = new AtomicReference<>("");

    try (var stream = service.sendMessageStreamResponse("What is the capital of France?")) {
      stream.stream()
          .filter(RawMessageStreamEvent::isContentBlockDelta)
          .map(event -> event.asContentBlockDelta().delta())
          .filter(RawContentBlockDelta::isText)
          .map(delta -> delta.asText().text())
          .forEach(text -> fullResponse.accumulateAndGet(text, String::concat));
    }

    assertThat(fullResponse.get()).containsIgnoringCase("paris");
  }
}
