package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.sap.ai.sdk.app.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@SuppressWarnings("unused")
@RequestMapping("/orchestration")
class OrchestrationController {
  private final OrchestrationClient client = new OrchestrationClient();
  private final OrchestrationService service = new OrchestrationService();
  OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO.withParam(TEMPERATURE, 0.0));

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion")
  @Nonnull
  ResponseEntity<String> completion() {
    final var content = service.completion().getContent();
    log.info("Our trusty AI answered with: {}", content);
    return ResponseEntity.ok(content);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @GetMapping("/streamChatCompletion")
  @Nonnull
  public ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    return service.streamChatCompletion();
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
  ResponseEntity<String> template() {
    return ResponseEntity.ok(service.template().getContent());
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template and full response.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   * @return a ResponseEntity with the full response
   */
  @GetMapping("/template/full")
  @Nonnull
  ResponseEntity<String> templateFull() {
    return ResponseEntity.ok(service.template().toString());
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  ResponseEntity<String> messagesHistory() {
    return ResponseEntity.ok(service.messagesHistory().getContent());
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
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {
    return ResponseEntity.ok(service.filter(policy).getContent());
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
  ResponseEntity<String> maskingAnonymization() {
    return ResponseEntity.ok(service.maskingAnonymization().getContent());
  }

  /**
   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  public ResponseEntity<String> completionWithResourceGroup(
      @PathVariable("resourceGroup") @Nonnull final String resourceGroup) {
    return ResponseEntity.ok(service.completionWithResourceGroup(resourceGroup).getContent());
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
  ResponseEntity<String> maskingPseudonymization() {
    return ResponseEntity.ok(service.maskingPseudonymization().getContent());
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
  ResponseEntity<String> grounding() {
    return ResponseEntity.ok(service.grounding().getContent());
  }
}
