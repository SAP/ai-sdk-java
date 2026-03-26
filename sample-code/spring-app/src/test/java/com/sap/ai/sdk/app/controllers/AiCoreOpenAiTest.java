package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.AiCoreOpenAiService;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
class AiCoreOpenAiTest {
  AiCoreOpenAiService service;

  @BeforeEach
  void setUp() {
    service = new AiCoreOpenAiService();
  }

  @Test
  void testCreateResponse() {
    final var response = service.createResponse("What is the capital of France?");
    assertThat(response).isNotNull();
    assertThat(response.output()).isNotNull();
    assertThat(response.output().get(1).message().get().content().get(0).asOutputText().text())
        .contains("Paris");
  }

  @Test
  @Disabled("Not yet enables and leads to Internal Server Error (500)")
  void testCreateStreamingResponse() {
    try (final var streamResponse =
        service.createStreamingResponse("What is the capital of France?")) {
      final var events = streamResponse.stream().collect(Collectors.toList());

      assertThat(events).isNotEmpty();

      // Verify we got text deltas
      final var hasTextDeltas =
          events.stream().anyMatch(event -> event.outputTextDelta().isPresent());
      assertThat(hasTextDeltas).isTrue();
    }
  }

  @Test
  @Disabled("/chat/completions endpoint is not in allowed path")
  void testCreateStreamingChatCompletion() {
    try (final var streamResponse =
        service.createStreamingChatCompletion("What is the capital of France?")) {
      final var events = streamResponse.stream().collect(Collectors.toList());

      assertThat(events).isNotEmpty();

      // Verify we got content deltas
      final var hasContentDeltas =
          events.stream()
              .anyMatch(
                  event ->
                      !event.choices().isEmpty()
                          && event.choices().get(0).delta().content().isPresent());
      assertThat(hasContentDeltas).isTrue();
    }
  }
}
