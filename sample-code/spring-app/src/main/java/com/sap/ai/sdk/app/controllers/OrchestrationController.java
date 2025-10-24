package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationFilterException;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutput;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

  @Value("classpath:promptTemplateExample.yaml")
  private Resource localPromptTemplate;

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

  @GetMapping("/inputFiltering/{policy}")
  @Nonnull
  Object inputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final OrchestrationChatResponse response;
    try {
      response = service.inputFiltering(policy);
    } catch (OrchestrationFilterException.Input e) {
      final var msg =
          new StringBuilder(
              "[Http %d] Failed to obtain a response as the content was flagged by input filter. "
                  .formatted(e.getStatusCode()));

      Optional.ofNullable(e.getAzureContentSafetyInput())
          .map(AzureContentSafetyInput::getViolence)
          .filter(rating -> rating.compareTo(policy.getAzureThreshold()) > 0)
          .ifPresent(rating -> msg.append("Violence score %d".formatted(rating.getValue())));

      log.debug(msg.toString(), e);
      return ResponseEntity.internalServerError().body(msg.toString());
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

    final var response = service.outputFiltering(policy);

    final String content;
    try {
      content = response.getContent();
    } catch (OrchestrationFilterException.Output e) {
      final var msg =
          new StringBuilder(
              "Failed to obtain a response as the content was flagged by output filter. ");

      Optional.ofNullable(e.getAzureContentSafetyOutput())
          .map(AzureContentSafetyOutput::getViolence)
          .filter(rating -> rating.compareTo(policy.getAzureThreshold()) > 0)
          .ifPresent(rating -> msg.append("Violence score %d ".formatted(rating.getValue())));

      log.debug(msg.toString(), e);
      return ResponseEntity.internalServerError().body(msg.toString());
    }

    if ("json".equals(format)) {
      return response;
    }
    return content;
  }

  @GetMapping("/llamaGuardFilter/{enabled}")
  @Nonnull
  Object llamaGuardInputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @PathVariable("enabled") final boolean enabled) {

    final OrchestrationChatResponse response;
    try {
      response = service.llamaGuardInputFilter(enabled);
    } catch (OrchestrationFilterException.Input e) {
      var msg =
          "[Http %d] Failed to obtain a response as the content was flagged by input filter. "
              .formatted(e.getStatusCode());
      if (e.getLlamaGuard38b() != null) {
        msg += " Violent crimes are %s".formatted(e.getLlamaGuard38b().isViolentCrimes());
      }
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

  @GetMapping("/maskingRegex")
  @Nonnull
  Object maskingRegex(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.maskingRegex();
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

  @GetMapping("/grounding/{maskGroundingInput}")
  @Nonnull
  Object grounding(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @PathVariable("maskGroundingInput") final boolean maskGroundingInput) {
    final var response = service.grounding("What does Joule do?", maskGroundingInput);
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/groundingSharepoint")
  @Nonnull
  Object groundingSharepoint(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.groundingSharepoint("What is the secret for the AI SDK e2e test?");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/groundingHelpSapCom")
  @Nonnull
  Object groundingHelpSapCom(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = service.groundingHelpSapCom("What is a fuzzy search?");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/responseFormatJsonSchema")
  @Nonnull
  Object responseFormatJsonSchema(
      @RequestParam(value = "format", required = false) final String format) {
    final var response =
        service.responseFormatJsonSchema("apple", OrchestrationService.Translation.class);
    if ("json".equals(format)) {
      return response;
    }
    return response.asEntity(OrchestrationService.Translation.class);
  }

  @GetMapping("/responseFormatJsonObject")
  @Nonnull
  Object responseFormatJsonObject(
      @RequestParam(value = "format", required = false) final String format) {
    final var response = service.responseFormatJsonObject("apple");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/templateFromPromptRegistryById")
  @Nonnull
  Object templateFromPromptRegistryById(
      @RequestParam(value = "format", required = false) final String format) {
    final var response = service.templateFromPromptRegistryById("cloud ERP systems");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/templateFromPromptRegistryByScenario")
  @Nonnull
  Object templateFromPromptRegistryByScenario(
      @RequestParam(value = "format", required = false) final String format) {
    final var response = service.templateFromPromptRegistryByScenario("cloud ERP systems");
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/localPromptTemplate")
  @Nonnull
  Object localPromptTemplate(@RequestParam(value = "format", required = false) final String format)
      throws IOException {
    final var promptTemplate =
        Files.readString(localPromptTemplate.getFile().toPath(), StandardCharsets.UTF_8);
    final var response = service.localPromptTemplate(promptTemplate);
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/translation")
  @Nonnull
  Object translation(@RequestParam(value = "format", required = false) final String format) {
    final var response = service.translation();
    if ("json".equals(format)) {
      return response;
    }
    return response.getContent();
  }

  @GetMapping("/embedding")
  @Nonnull
  Object embedding(@RequestParam(value = "format", required = false) final String format) {
    final var response = service.embed(List.of("Hi SAP Orchestration Service", "I am John Doe"));
    if ("json".equals(format)) {
      return response;
    }
    return response.getEmbeddingVectors();
  }
}
