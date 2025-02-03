package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@SuppressWarnings("unused")
@RequestMapping("/orchestration")
class OrchestrationController {
  @Autowired private OrchestrationService service;
  private static final ObjectMapper MAPPER =
      new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

  /**
   * Chat request to an LLM through the Orchestration service with a simple prompt.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion")
  @Nonnull
  ResponseEntity<String> completion(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.completion("HelloWorld!");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  @GetMapping("/image")
  @Nonnull
  ResponseEntity<String> imageInput(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response =
        service.imageInput(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  @GetMapping("/multiString")
  @Nonnull
  ResponseEntity<String> multiStringInput(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response =
        service.multiStringInput(
            List.of("What is the capital of France?", "What is Chess about?", "What is 2+2?"));
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Asynchronous stream of an LLM chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @GetMapping("/streamChatCompletion")
  @Nonnull
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
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/template")
  @Nonnull
  ResponseEntity<Object> template(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.template("German");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Chat request to an LLM through the Orchestration service using message history.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  ResponseEntity<String> messagesHistory(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.messagesHistory("What is the capital of France?");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Send an HTTP GET request for input filtering to the Orchestration service.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     * AI Core: Orchestration - Input Filtering</a>
   * @param accept an optional HTTP header specifying the desired content type for the response.
   * @param policy path variable specifying the {@link AzureFilterThreshold} the explicitness of
   *     content that should be allowed through the filter
   * @return a {@link ResponseEntity} containing the filtered input. The response is either in JSON
   *     format if the "accept" header specifies "application/json" or in plain content format
   *     otherwise.
   * @throws JsonProcessingException if an error occurs while converting the response to JSON.
   */
  @GetMapping("/inputFiltering/{policy}")
  @Nonnull
  ResponseEntity<String> inputFiltering(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy)
      throws JsonProcessingException {

    final OrchestrationChatResponse response;
    try {
      response = service.inputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by input filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if (accept.equals("application/json")) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok().body(response.getContent());
  }

  /**
   * Send an HTTP GET request for output filtering to the Orchestration service.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param accept an optional HTTP header specifying the desired content type for the response.
   * @param policy a mandatory path variable specifying the {@link AzureFilterThreshold} the
   *     explicitness of content that should be allowed through the filter
   * @return a {@link ResponseEntity} containing the filtered output. The response is either in JSON
   *     format if the "accept" header specifies "application/json" or in plain content format
   *     otherwise.
   * @throws OrchestrationClientException if the output filter filtered the LLM response.
   * @throws JsonProcessingException if an error occurs while converting the response to JSON.
   */
  @GetMapping("/outputFiltering/{policy}")
  @Nonnull
  ResponseEntity<String> outputFiltering(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy)
      throws JsonProcessingException, OrchestrationClientException {

    final var response = service.outputFiltering(policy);
    try {
      if (accept.equals("application/json")) {
        return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
      }
      return ResponseEntity.ok().body(response.getContent());
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by output filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  ResponseEntity<String> maskingAnonymization(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.maskingAnonymization(DPIEntities.PERSON);
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Chat request to an LLM through the Orchestration deployment under a specific resource group.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  public ResponseEntity<String> completionWithResourceGroup(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup)
      throws JsonProcessingException {
    final var response = service.completionWithResourceGroup(resourceGroup, "Hello world!");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  ResponseEntity<String> maskingPseudonymization(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.maskingPseudonymization(DPIEntities.PERSON);
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Using grounding to provide additional context to the AI model.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
   *     AI Core: Orchestration - Grounding</a>
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/grounding")
  @Nonnull
  ResponseEntity<String> grounding(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.grounding("What does Joule do?");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(MAPPER.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }
}
