package com.sap.ai.sdk.app.controllers;

import com.anthropic.models.messages.RawMessageStreamEvent;
import com.anthropic.models.messages.TextDelta;
import com.sap.ai.sdk.app.services.AnthropicService;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for Anthropic Claude operations. */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class AnthropicController {

  @Autowired private AnthropicService service;

  /** Initiate new chat by sending message to Anthropic Claude. */
  @GetMapping("/anthropic/sendMessage")
  @Nonnull
  String sendMessage() {
    final var message = service.sendMessage("What is the capital of France?");
    return message.content().stream()
        .filter(block -> block.isText())
        .map(block -> block.text().map(t -> t.text()).orElse(""))
        .reduce("", String::concat);
  }

  /** Initiate new chat by sending message to Anthropic Claude. Stream response. */
  @GetMapping("/anthropic/sendMessageStreamResponse")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> sendMessageStreamResponse() {
    final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";
    ;
    final var emitter = new ResponseBodyEmitter();
    send(emitter, "Model response:\n\n");
    final Runnable consumeStream =
        () -> {
          try (var stream = service.sendMessageStreamResponse(message)) {
            stream.stream()
                .filter(RawMessageStreamEvent::isContentBlockDelta)
                .forEach(
                    deltaMessage ->
                        send(
                            emitter,
                            deltaMessage
                                .asContentBlockDelta()
                                .delta()
                                .text()
                                .orElseGet(() -> TextDelta.of(""))
                                .text()));
          } finally {
            emitter.complete();
          }
        };
    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  /**
   * Send a chunk to the emitter.
   *
   * @param emitter the emitter to send the chunk to.
   * @param chunk the chunk to send.
   */
  public static void send(@Nonnull final ResponseBodyEmitter emitter, @Nonnull final String chunk) {
    try {
      log.warn("sending message to anthropic: {}", chunk);
      emitter.send(chunk);
    } catch (final IOException e) {
      log.error("Failed to send chunk: {}", e.getMessage(), e);
      emitter.completeWithError(e);
    }
  }
}
