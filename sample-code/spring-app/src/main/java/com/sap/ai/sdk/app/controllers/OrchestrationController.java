package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@SuppressWarnings("unused")
@RequestMapping("/orchestration")
class OrchestrationController {
  @Autowired private OrchestrationService service;

  @PostMapping("/processInput")
  public ResponseEntity<String> processInput(@RequestParam("userInput") String userInput) {
    log.info("User input: {}", userInput);
    final String output = service.processInput(userInput);
    return ResponseEntity.ok(output);
  }

//  @PostMapping("/processInput")
  ResponseEntity<ResponseBodyEmitter> processInputStream(@RequestParam("userInput") String userInput) {
    final var stream = service.processInputStream(userInput);
    final var emitter = new ResponseBodyEmitter();
    final Runnable consumeStream =
        () -> {
          try (stream) {
            stream.forEach(
                deltaMessage -> {
                  log.info("Service: {}", deltaMessage);
                  send(emitter, deltaMessage);
                });
          } finally {
            emitter.complete();
          }
        };

    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  public static void send(@Nonnull final ResponseBodyEmitter emitter, @Nonnull final String chunk) {
    try {
      emitter.send(chunk);
    } catch (final IOException e) {
      log.error(Arrays.toString(e.getStackTrace()));
      emitter.completeWithError(e);
    }
  }


}
