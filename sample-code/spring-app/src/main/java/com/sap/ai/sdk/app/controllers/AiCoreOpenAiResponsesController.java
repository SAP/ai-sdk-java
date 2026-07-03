package com.sap.ai.sdk.app.controllers;

import static com.openai.core.ObjectMappers.jsonMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputMessage;
import com.sap.ai.sdk.app.services.AiCoreOpenAiService;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for OpenAI Responses API operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class AiCoreOpenAiResponsesController {
  @Autowired private AiCoreOpenAiService aiCoreOpenAiService;

  @GetMapping("/responses")
  @Nonnull
  Object createResponse(
      @Nullable @RequestParam(value = "format", required = false) final String format)
      throws JsonProcessingException {
    final var response = aiCoreOpenAiService.createResponse("Who is the prettiest?");
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(jsonMapper().writeValueAsString(response));
    }
    return extractText(response);
  }

  @GetMapping("/responses/persistent")
  @Nonnull
  Object createPersistentResponse(
      @Nullable @RequestParam(value = "format", required = false) final String format)
      throws JsonProcessingException {
    final var response =
        aiCoreOpenAiService.createPersistentResponse("Who is the prettiest?", false);
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(jsonMapper().writeValueAsString(response));
    }
    return extractText(response);
  }

  @GetMapping("/responses/background")
  @Nonnull
  Object createBackgroundResponse(
      @Nullable @RequestParam(value = "format", required = false) final String format)
      throws JsonProcessingException {
    final var response =
        aiCoreOpenAiService.createPersistentResponse(
            "Give me the first 1000 Fibonacci numbers.", true);
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(jsonMapper().writeValueAsString(response));
    }
    return extractText(response);
  }

  @GetMapping("/streamResponses")
  @Nonnull
  Object createStreamingResponse(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var input = "Give me the first 100 Fibonacci numbers.";
    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
        () -> {
          try (var response = aiCoreOpenAiService.createStreamingResponse(input)) {
            response.stream()
                .forEach(
                    event ->
                        event
                            .outputTextDelta()
                            .ifPresent(delta -> OpenAiController.send(emitter, delta.delta())));
          } finally {
            emitter.complete();
          }
        };
    ThreadContextExecutors.getExecutor().execute(consumeStream);
    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  @GetMapping("/responses/retrieve/{responseId}")
  @Nonnull
  Object retrieveResponse(
      @Nonnull @PathVariable("responseId") final String responseId,
      @Nullable @RequestParam(value = "format", required = false) final String format)
      throws JsonProcessingException {
    final var response = aiCoreOpenAiService.retrieveResponse(responseId);
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(jsonMapper().writeValueAsString(response));
    }
    return extractText(response);
  }

  @GetMapping("/responses/{responseId}/cancel")
  @Nonnull
  Object cancelResponse(
      @Nonnull @PathVariable("responseId") final String responseId,
      @Nullable @RequestParam(value = "format", required = false) final String format)
      throws JsonProcessingException {
    final var response = aiCoreOpenAiService.cancelResponse(responseId);
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(jsonMapper().writeValueAsString(response));
    }
    return extractText(response);
  }

  @GetMapping("/responses/delete/{responseId}")
  @Nonnull
  Object deleteResponse(
      @Nonnull @PathVariable("responseId") final String responseId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    aiCoreOpenAiService.deleteResponse(responseId);
    if ("json".equals(format)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body("{\"status\":\"deleted\"}");
    }
    return ResponseEntity.ok("deleted");
  }

  @Nonnull
  private static String extractText(@Nonnull final Response response) {
    return response.output().stream()
        .filter(ResponseOutputItem::isMessage)
        .map(ResponseOutputItem::asMessage)
        .flatMap(message -> message.content().stream())
        .filter(ResponseOutputMessage.Content::isOutputText)
        .map(text -> text.asOutputText().text())
        .collect(Collectors.joining());
  }
}
