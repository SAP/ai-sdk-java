package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.sap.ai.sdk.app.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import java.util.List;
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

  private String cleanString(String str) {
    return str.replaceAll("\n" + " {8}", " ")
        .replaceAll("\n" + " {4}", " ")
        .replaceAll(" {4}", " ");
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the result object
   */
  @GetMapping("/completion")
  @Nonnull
  ResponseEntity<List<String>> completion() {
    var result = service.completion();
    var content = List.of(result.getContent());
    log.info("Our trusty AI answered with: {}", result.getContent());
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
   * @return the result object
   */
  @GetMapping("/template")
  @Nonnull
  ResponseEntity<List<String>> template() {
    var result = service.template();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  @GetMapping("/template/full")
  @Nonnull
  ResponseEntity<List<String>> templateFull() {
    var result = service.template();
    var content = List.of(cleanString(result.toString()));
    return ResponseEntity.ok(content);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return the result object
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  ResponseEntity<List<String>> messagesHistory() {
    var result = service.messagesHistory();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
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
   * @return the result object
   */
  @GetMapping("/filter/{policy}")
  @Nonnull
  ResponseEntity<List<String>> filter(
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {
    var result = service.filter(policy);
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the result object
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  ResponseEntity<List<String>> maskingAnonymization() {
    var result = service.maskingAnonymization();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  /**
   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
   *
   * @return the result object
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  public ResponseEntity<List<String>> completionWithResourceGroup(
      @PathVariable("resourceGroup") @Nonnull final String resourceGroup) {
    var result = service.completionWithResourceGroup(resourceGroup);
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the result object
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  ResponseEntity<List<String>> maskingPseudonymization() {
    var result = service.maskingPseudonymization();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  /**
   * Using grounding to provide additional context to the AI model.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
   *     AI Core: Orchestration - Grounding</a>
   */
  @GetMapping("/grounding")
  @Nonnull
  ResponseEntity<List<String>> grounding() {
    var result = service.grounding();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }
}
