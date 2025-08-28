package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CompletionUsage;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
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
  private static final ObjectMapper MAPPER =
      new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

  @GetMapping("/chatCompletion")
  @Nonnull
  Object chatCompletion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.chatCompletion("Who is the prettiest");
    if ("json".equals(format)) {
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
    final var totalUsage = new AtomicReference<CompletionUsage>();
    final Runnable consumeStream =
        () -> {
          try (stream) {
            stream.forEach(
                delta -> {
                  // Instead of getCompletionUsage(MAPPER), we now use getUsage()
                  final var usage = delta.getCompletionUsage();
                  totalUsage.compareAndExchange(null, usage);
                  send(emitter, delta.getDeltaContent());
                });
          } finally {
            send(emitter, "\n\n-----Total Usage-----\n\n" + totalUsage.get());
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
      log.error("Failed to send chunk: {}", e.getMessage(), e);
      emitter.completeWithError(e);
    }
  }

  @GetMapping("/chatCompletionImage")
  @Nonnull
  Object chatCompletionImage(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/chatCompletionToolExecution")
  @Nonnull
  Object chatCompletionToolExecution(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @RequestParam(value = "location", defaultValue = "Dubai") final String location,
      @Nonnull @RequestParam(value = "unit", defaultValue = "°C") final String unit) {
    final var response = service.chatCompletionToolExecution(location, unit);
    if ("json".equals(format)) {
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
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup) {
    final var response =
        service.chatCompletionWithResource(resourceGroup, "Where is the nearest coffee shop?");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }
}
