package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@SuppressWarnings("unused")
@RequestMapping("/orchestration")
class OrchestrationController {
  @Autowired private OrchestrationService service;

  @GetMapping("/completion")
  Object completion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.completion("HelloWorld!");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/streamChatCompletion")
  ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var stream = service.streamChatCompletion("developing a software project");
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

  @GetMapping("/template")
  Object template(@Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.template("German");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/messagesHistory")
  @Nonnull
  Object messagesHistory(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.messagesHistory("What is the capital of France?");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/inputFiltering/{policy}")
  @Nonnull
  Object inputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final OrchestrationChatResponse response;
    try {
      response = service.inputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by input filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/outputFiltering/{policy}")
  @Nonnull
  Object outputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final OrchestrationChatResponse response;
    try {
      response = service.outputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by output filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/llamaGuardFilter/{enabled}")
  @Nonnull
  Object llamaGuardInputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @PathVariable("enabled") final boolean enabled) {

    final OrchestrationChatResponse response;
    try {
      response = service.llamaGuardInputFilter(enabled);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by input filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/maskingAnonymization")
  @Nonnull
  Object maskingAnonymization(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.maskingAnonymization(DPIEntities.PERSON);
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  Object completionWithResourceGroup(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup) {
    final var response = service.completionWithResourceGroup(resourceGroup, "Hello world!");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/maskingPseudonymization")
  @Nonnull
  Object maskingPseudonymization(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.maskingPseudonymization(DPIEntities.PERSON);
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/grounding")
  @Nonnull
  Object grounding(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.grounding("What does Joule do?");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/image")
  @Nonnull
  Object imageInput(@RequestParam(value = "format", required = false) final String format) {
    final var response =
        service.imageInput(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/multiString")
  @Nonnull
  Object multiStringInput(@RequestParam(value = "format", required = false) final String format) {
    final var response =
        service.multiStringInput(
            List.of("What is the capital of France?", "What is Chess about?", "What is 2+2?"));
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }
}
