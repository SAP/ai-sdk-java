package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseStatus;
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

    final var reasoningOutput = response.output().get(0);
    assertThat(reasoningOutput.isReasoning()).isTrue();
    final var messageOutput = response.output().get(1);
    assertThat(messageOutput.isMessage()).isTrue();
    assertThat(messageOutput.asMessage().content()).isNotEmpty();
    final var content = messageOutput.asMessage().content().get(0);
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

  @Test
  void testRetrieveResponse() {
    final var created = service.createPersistentResponse("Say hi.", false);
    assertThat(created.id()).isNotBlank();

    final var retrieved = service.retrieveResponse(created.id());
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.id()).isEqualTo(created.id());

    // Cleanup
    service.deleteResponse(created.id());
  }

  @Test
  void testCancelResponse() {
    final var created =
        service.createPersistentResponse(
            "Write an extremely detailed 10-page essay covering the full history of distributed"
                + " systems from the 1960s to today. Include subsections, citations, and concrete"
                + " examples for every decade.",
            true);
    assertThat(created.id()).isNotBlank();

    final var cancelled = service.cancelResponse(created.id());
    assertThat(cancelled).isNotNull();
    assertThat(cancelled.id()).isEqualTo(created.id());
    assertThat(cancelled.status()).contains(ResponseStatus.CANCELLED);

    // Cleanup
    service.deleteResponse(created.id());
  }

  @Test
  void testDeleteResponse() {
    final var created = service.createPersistentResponse("Say hi.", false);
    assertThat(created.id()).isNotBlank();

    assertThatNoException().isThrownBy(() -> service.deleteResponse(created.id()));
  }
}
