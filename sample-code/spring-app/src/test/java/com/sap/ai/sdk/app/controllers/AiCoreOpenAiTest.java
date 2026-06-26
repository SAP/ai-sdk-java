package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.Response.Truncation;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseStatus;
import com.sap.ai.sdk.app.services.AiCoreOpenAiService;
import java.util.List;
import java.util.Map;
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

  @Test
  void testBackgroundMode() throws InterruptedException {
    final var response =
        service.createBackgroundResponseAndPoll("Write a short poem about the sea.");
    assertThat(response).isNotNull();
    assertThat(response.status())
        .satisfiesAnyOf(
            s -> assertThat(s).contains(ResponseStatus.COMPLETED),
            s -> assertThat(s).contains(ResponseStatus.FAILED));
    assertThat(response.output()).isNotEmpty();
    final var message =
        response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    assertThat(message.get().asMessage().content().get(0).asOutputText().text()).isNotBlank();
  }

  @Test
  void testMultiTurnResponse() {
    final var first = service.createPersistentResponse("Count to 3 in Spanish.", false);
    assertThat(first.id()).isNotBlank();

    final var followUp =
        service.createMultiTurnResponse("What did I just ask you to count to?", first.id());
    assertThat(followUp).isNotNull();
    assertThat(followUp.previousResponseId()).contains(first.id());
    final var message =
        followUp.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    assertThat(message.get().asMessage().content().get(0).asOutputText().text())
        .containsIgnoringCase("3");
  }

  @Test
  void testToolCalling() {
    final var response = service.createResponseWithTools("What is the weather in Berlin today?");
    assertThat(response).isNotNull();
    assertThat(response.output()).isNotEmpty();
    final var functionCall =
        response.output().stream().filter(ResponseOutputItem::isFunctionCall).findFirst();
    assertThat(functionCall).isPresent();
    assertThat(functionCall.get().asFunctionCall().name()).isEqualTo("get_weather");
    assertThat(functionCall.get().asFunctionCall().arguments()).contains("Berlin");
  }

  @Test
  void testReasoningEffort() {
    final var response =
        service.createResponseWithReasoning(
            "If a train travels 120 km in 1.5 hours, what is its average speed?",
            ReasoningEffort.HIGH);
    assertThat(response).isNotNull();
    assertThat(response.reasoning().isPresent()).isTrue();
    assertThat(response.reasoning().get().effort()).contains(ReasoningEffort.HIGH);
    final var reasoning =
        response.output().stream().filter(ResponseOutputItem::isReasoning).findFirst();
    assertThat(reasoning).isPresent();
    assertThat(reasoning.get().asReasoning().summary()).isNotEmpty();
    final var message =
        response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    assertThat(message.get().asMessage().content().get(0).asOutputText().text()).contains("80");
  }

  @Test
  void testStructuredOutput() throws JsonProcessingException {
    final var response =
        service.createStructuredResponse("Extract the name and age from: John is 30 years old.");
    assertThat(response).isNotNull();
    final var message =
        response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    final var text = message.get().asMessage().content().get(0).asOutputText().text();
    final var parsed = new ObjectMapper().readValue(text, Map.class);
    assertThat(parsed.get("name")).isEqualTo("John");
    assertThat(parsed.get("age")).isEqualTo(30);
  }

  @Test
  void testStatelessMultiTurn() {
    final var messages =
        List.of(
            ResponseInputItem.ofEasyInputMessage(
                EasyInputMessage.builder()
                    .role(EasyInputMessage.Role.USER)
                    .content("Count to 3 in Spanish.")
                    .build()),
            ResponseInputItem.ofEasyInputMessage(
                EasyInputMessage.builder()
                    .role(EasyInputMessage.Role.ASSISTANT)
                    .content("Uno, dos, tres.")
                    .build()),
            ResponseInputItem.ofEasyInputMessage(
                EasyInputMessage.builder()
                    .role(EasyInputMessage.Role.USER)
                    .content("Now count to 5.")
                    .build()));
    final var response = service.createStatelessMultiTurnResponse(messages);
    assertThat(response).isNotNull();
    final var message =
        response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    final var text = message.get().asMessage().content().get(0).asOutputText().text();
    assertThat(text).containsIgnoringCase("cinco");
  }

  @Test
  void testTruncation() {
    final var first =
        service.createPersistentResponse("Write a short poem about the ocean.", false);
    assertThat(first.id()).isNotBlank();

    final var response =
        service.createResponseWithTruncation("Summarize what I asked.", first.id());
    assertThat(response).isNotNull();
    assertThat(response.previousResponseId()).contains(first.id());
    assertThat(response.truncation()).contains(Truncation.AUTO);
    final var message =
        response.output().stream().filter(ResponseOutputItem::isMessage).findFirst();
    assertThat(message).isPresent();
    assertThat(message.get().asMessage().content()).isNotEmpty();
  }

  @Test
  void testPromptCaching() {
    final var cacheKey = "ai-sdk-java-test-cache-key";
    final var prompt = "What is the capital of France?";

    // First call populates the cache
    final var first = service.createCachedResponse(prompt, cacheKey);
    assertThat(first).isNotNull();
    assertThat(first.promptCacheKey()).contains(cacheKey);
    assertThat(first.usage().isPresent()).isTrue();

    // Second call with the same key — cache hit is server-side and not guaranteed in tests, so we
    // only assert the key is echoed back and usage is accessible
    final var second = service.createCachedResponse(prompt, cacheKey);
    assertThat(second).isNotNull();
    assertThat(second.promptCacheKey()).contains(cacheKey);
    assertThat(second.usage().isPresent()).isTrue();
    assertThat(second.usage().get().inputTokensDetails().cachedTokens()).isGreaterThanOrEqualTo(0);
  }
}
