package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
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

  /** Chat request to an LLM through the Orchestration service with a simple prompt. */
  @GetMapping("/completion")
  Object completion(@Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.completion("HelloWorld!");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Asynchronous stream of an LLM chat request
   *
   * @return the emitter that streams the assistant message response
   */
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

  /**
   * Chat request to an LLM through the Orchestration service with a template.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   */
  @GetMapping("/template")
  Object template(@Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.template("German");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /** Chat request to an LLM through the Orchestration service using message history. */
  @GetMapping("/messagesHistory")
  @Nonnull
  Object messagesHistory(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.messagesHistory("What is the capital of France?");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Send an HTTP GET request for input filtering to the Orchestration service.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     * AI Core: Orchestration - Input Filtering</a>
   * @param view an optional HTTP header specifying the desired content type for the response.
   * @param policy path variable specifying the {@link AzureFilterThreshold} the explicitness of
   *     content that should be allowed through the filter
   * @return a {@link ResponseEntity} containing the filtered input. The response is either in JSON
   *     format if the "accept" header specifies "application/json" or in plain content format
   *     otherwise.
   */
  @GetMapping("/inputFiltering/{policy}")
  @Nonnull
  Object inputFiltering(
      @Nullable @RequestParam(value = "view", required = false) final String view,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final OrchestrationChatResponse response;
    try {
      response = service.inputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by input filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Send an HTTP GET request for output filtering to the Orchestration service.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param view an optional HTTP header specifying the desired content type for the response.
   * @param policy a mandatory path variable specifying the {@link AzureFilterThreshold} the
   *     explicitness of content that should be allowed through the filter
   * @return a {@link ResponseEntity} containing the filtered output. The response is either in JSON
   *     format if the "accept" header specifies "application/json" or in plain content format
   *     otherwise.
   */
  @GetMapping("/outputFiltering/{policy}")
  @Nonnull
  Object outputFiltering(
      @Nullable @RequestParam(value = "view", required = false) final String view,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final OrchestrationChatResponse response;
    try {
      response = service.outputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by output filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  Object maskingAnonymization(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.maskingAnonymization(DPIEntities.PERSON);
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Chat request to an LLM through the Orchestration deployment under a specific resource group.
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  Object completionWithResourceGroup(
      @Nullable @RequestParam(value = "view", required = false) final String view,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup) {
    final var response = service.completionWithResourceGroup(resourceGroup, "Hello world!");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  Object maskingPseudonymization(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.maskingPseudonymization(DPIEntities.PERSON);
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }

  /**
   * Using grounding to provide additional context to the AI model.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
   *     AI Core: Orchestration - Grounding</a>
   */
  @GetMapping("/grounding")
  @Nonnull
  Object grounding(@Nullable @RequestParam(value = "view", required = false) final String view) {
    final var response = service.grounding("What does Joule do?");
    if ("json".equals(view)) {
      return response;
    }
    return response.getContent();
  }
}
