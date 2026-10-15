package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.AnthropicService;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for Anthropic Claude operations. */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class AnthropicController {

  @Autowired private AnthropicService service;

  /** Chat completion request to Anthropic Claude. */
  @GetMapping("/anthropic/chatCompletion")
  @Nonnull
  String chatCompletion() {
    final var message = service.chatCompletion("What is the capital of France?");
    return message.content().stream()
        .filter(block -> block.isText())
        .map(block -> block.text().map(t -> t.text()).orElse(""))
        .reduce("", String::concat);
  }

  /** Streaming chat completion request to Anthropic Claude. */
  @GetMapping("/anthropic/streamChatCompletion")
  @Nonnull
  ResponseBodyEmitter streamChatCompletion() {
    final var emitter = new ResponseBodyEmitter();
    final var stream = service.streamChatCompletion("What is the capital of France?");
    new Thread(
            () -> {
              try (stream) {
                stream.stream()
                    .filter(event -> event.isContentBlockDelta())
                    .map(event -> event.asContentBlockDelta().delta())
                    .filter(delta -> delta.isText())
                    .map(delta -> delta.asText().text())
                    .forEach(
                        text -> {
                          try {
                            emitter.send(text);
                          } catch (final Exception e) {
                            emitter.completeWithError(e);
                          }
                        });
                emitter.complete();
              } catch (final Exception e) {
                emitter.completeWithError(e);
              }
            })
        .start();
    return emitter;
  }
}
