package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.util.Arrays;
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

/** Endpoints for OpenAI operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class OpenAiController {
  @Autowired private OpenAiService service;

  @GetMapping("/chatCompletion")
  @Nonnull
  Object chatCompletion(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.chatCompletion("Who is the prettiest");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  @SuppressWarnings("unused") // The end-to-end test doesn't use this method
  @GetMapping("/streamChatCompletionDeltas")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> streamChatCompletionDeltas() {
    final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";
    final var stream = service.streamChatCompletionDeltas(message);
    final var emitter = new ResponseBodyEmitter();
    final Runnable consumeStream =
        () -> {
          final var totalOutput = new OpenAiChatCompletionOutput();
          // try-with-resources ensures the stream is closed
          try (stream) {
            stream
                .peek(totalOutput::addDelta)
                .forEach(delta -> send(emitter, delta.getDeltaContent()));
          } finally {
            send(emitter, "\n\n-----Total Output-----\n\n" + objectToJson(totalOutput));
            emitter.complete();
          }
        };
    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  @SuppressWarnings("unused") // The end-to-end test doesn't use this method
  @GetMapping("/streamChatCompletion")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";
    final var stream = service.streamChatCompletion(message);
    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
        () -> {
          try (stream) {
            stream.forEach(deltaMessage -> send(emitter, deltaMessage));
          } finally {
            emitter.complete();
          }
        };
    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  /**
   * Send a chunk to the emitter
   *
   * @param emitter The emitter to send the chunk to
   * @param chunk The chunk to send
   */
  public static void send(@Nonnull final ResponseBodyEmitter emitter, @Nonnull final String chunk) {
    try {
      emitter.send(chunk);
    } catch (final IOException e) {
      log.error(Arrays.toString(e.getStackTrace()));
      emitter.completeWithError(e);
    }
  }

  /**
   * Convert an object to JSON
   *
   * @param obj The object to convert
   * @return The JSON representation of the object
   */
  private static String objectToJson(@Nonnull final Object obj) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (final JsonProcessingException ignored) {
      return "Could not parse object to JSON";
    }
  }

  @GetMapping("/chatCompletionImage")
  @Nonnull
  Object chatCompletionImage(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/chatCompletionTool")
  @Nonnull
  Object chatCompletionTools(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response =
        service.chatCompletionTools("Calculate the Fibonacci number for given sequence index.");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/embedding")
  @Nonnull
  Object embedding() {
    return service.embedding("Hello world");
  }

  @GetMapping("/chatCompletion/{resourceGroup}")
  @Nonnull
  Object chatCompletionWithResource(
      @Nullable @RequestParam(value = "view", required = false) final String view,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup) {
    final var response =
        service.chatCompletionWithResource(resourceGroup, "Where is the nearest coffee shop?");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }
}
