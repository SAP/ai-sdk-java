package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.sap.ai.sdk.app.OrchestrationService;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@RequestMapping("/orchestration")
class OrchestrationController {
  private final OrchestrationClient client = new OrchestrationClient();
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
    var result = new OrchestrationService().completion();
    var content = List.of(result.getContent());
    log.info("Our trusty AI answered with: {}", result.getContent());
    return ResponseEntity.ok(content);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @SuppressWarnings("unused") // The end-to-end test doesn't use this method
//  TODO: Refactor this.
  @GetMapping("/streamChatCompletion")
  @Nonnull
  public ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var prompt =
        new OrchestrationPrompt("Can you give me the first 100 numbers of the Fibonacci sequence?");
    final var stream = client.streamChatCompletion(prompt, config);

    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
        () -> {
          try (stream) {
            stream.forEach(
                deltaMessage -> {
                  log.info("Controller: {}", deltaMessage);
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
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   * @return the result object
   */
  @GetMapping("/template")
  @Nonnull
  ResponseEntity<List<String>> template() {
    var result = new OrchestrationService().template();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }

  @GetMapping("/template/full")
  @Nonnull
  ResponseEntity<List<String>> templateFull() {
    var result = new OrchestrationService().template();
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
  ResponseEntity<List<String>>  messagesHistory() {
    var result = new OrchestrationService().messagesHistory();
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
    var result = new OrchestrationService().filter(policy);
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
    var result = new OrchestrationService().maskingAnonymization();
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
    var result = new OrchestrationService().completionWithResourceGroup(resourceGroup);
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
    var result = new OrchestrationService().maskingAnonymization();
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
    var result = new OrchestrationService().grounding();
    var content = List.of(result.getContent());
    return ResponseEntity.ok(content);
  }
}
