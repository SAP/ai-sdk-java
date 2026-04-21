package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.openai.models.responses.ResponseOutputItem;
import com.sap.ai.sdk.app.services.AiCoreOpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

    final var message0 = response.output().get(0);
    assertThat(message0.isReasoning()).isTrue();
    final var message1 = response.output().get(1);
    assertThat(message1.isMessage()).isTrue();
    assertThat(message1.asMessage().content()).isNotEmpty();
    final var content = message1.asMessage().content().get(0);
    assertThat(content.isOutputText()).isTrue();
    assertThat(content.asOutputText().text()).containsIgnoringCase("Paris");
  }

  @Test
  void testCreateStreamingResponse() {
    try (var stream = service.createStreamingResponse("What is the capital of France?")) {
      stream.stream()
          .forEach(
              event -> {
                assertThat(event.isValid()).isTrue();

                if (event.isCompleted()) {
                  final var response = event.asCompleted().response();
                  assertThat(response.output()).isNotEmpty();
                  final var message =
                      response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
                  assertThat(message).isPresent();
                  final var content = message.get().asMessage().content();
                  assertThat(content).isNotEmpty();
                  assertThat(content.get(0).isOutputText()).isTrue();
                  assertThat(content.get(0).asOutputText().text()).containsIgnoringCase("Paris");
                }
              });
    }
  }
}
