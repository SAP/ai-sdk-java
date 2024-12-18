package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import javax.annotation.Nonnull;
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
  private final ObjectMapper mapper =
      new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion")
  @Nonnull
  ResponseEntity<String> completion(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.completion("HelloWorld!");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @GetMapping("/streamChatCompletion")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    return service.streamChatCompletion(100);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/template")
  @Nonnull
  ResponseEntity<Object> template(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.template("German");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  ResponseEntity<String> messagesHistory(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.messagesHistory("What is the capital of France?");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Apply both input and output filtering for a request to orchestration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     AI Core: Orchestration - Input Filtering</a>
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param policy A high threshold is a loose filter, a low threshold is a strict filter
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/filter/{policy}")
  @Nonnull
  ResponseEntity<String> filter(
      @RequestHeader(value = "accept", required = false) final String accept,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy)
      throws JsonProcessingException {
    final var response = service.filter(policy, "the downtown area");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
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
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.maskingAnonymization(DPIEntities.PERSON);
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  public ResponseEntity<String> completionWithResourceGroup(
      @RequestHeader(value = "accept", required = false) final String accept,
      @PathVariable("resourceGroup") @Nonnull final String resourceGroup)
      throws JsonProcessingException {
    final var response = service.completionWithResourceGroup(resourceGroup, "Hello world!");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
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
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.maskingPseudonymization(DPIEntities.PERSON);
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
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
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.grounding("What does Joule do?");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }
}
