package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringChatResponse;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequestMapping("/spring-ai-orchestration")
class SpringAiOrchestrationController {
  @Autowired private SpringAiOrchestrationService service;

  @GetMapping("/completion")
  Object completion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.completion();

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/streamChatCompletion")
  @Nonnull
  Flux<String> streamChatCompletion() {
    return service
        .streamChatCompletion()
        .map(chatResponse -> chatResponse.getResult().getOutput().getText());
  }

  @GetMapping("/template")
  Object template(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.template();

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/inputFiltering/{policy}")
  @Nonnull
  Object inputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    final ChatResponse response;
    try {
      response = service.inputFiltering(policy);
    } catch (OrchestrationClientException e) {
      final var msg = "Failed to obtain a response as the content was flagged by input filter.";
      log.debug(msg, e);
      return ResponseEntity.internalServerError().body(msg);
    }

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/outputFiltering/{policy}")
  @Nonnull
  Object outputFiltering(
      @Nullable @RequestParam(value = "format", required = false) final String format,
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {

    val response = service.outputFiltering(policy);

    if (response.hasFinishReasons(Set.of("content_filter"))) {
      return ResponseEntity.internalServerError()
          .body("Failed to obtain a response as the content was flagged by output filter.");
    }

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/masking")
  Object masking(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.masking();

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/toolCalling")
  Object toolCalling(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.toolCalling(true);

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    final AssistantMessage message = response.getResult().getOutput();
    final String text = message.getText();
    return text != null && text.isEmpty() ? message.getToolCalls().toString() : text;
  }

  @GetMapping("/mcp")
  Object mcp(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.toolCallingMcp();

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    final AssistantMessage message = response.getResult().getOutput();
    final String text = message.getText();
    return text.isEmpty() ? message.getToolCalls().toString() : text;
  }

  @GetMapping("/chatMemory")
  Object chatMemory(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.chatMemory();

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }

  @GetMapping("/embed/string")
  Object embedding(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    return service.embed("Hello, world!");
  }
}
