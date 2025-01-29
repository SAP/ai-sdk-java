package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringChatResponse;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/spring-ai-orchestration")
class SpringAiOrchestrationController {
  @Autowired private SpringAiOrchestrationService service;

  @GetMapping("/completion")
  Object completion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = (OrchestrationSpringChatResponse) service.completion();

    if ("json".equals(format)) {
      return response.getOrchestrationResponse().getOriginalResponse();
    }
    return response.getResult().getOutput().getContent();
  }

  @GetMapping("/streamChatCompletion")
  @Nonnull
  Flux<String> streamChatCompletion() {
    return service
        .streamChatCompletion()
        .map(chatResponse -> chatResponse.getResult().getOutput().getContent());
  }

  @GetMapping("/template")
  Object template(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = (OrchestrationSpringChatResponse) service.template();

    if ("json".equals(format)) {
      return response.getOrchestrationResponse().getOriginalResponse();
    }
    return response.getResult().getOutput().getContent();
  }

  @GetMapping("/masking")
  Object masking(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = (OrchestrationSpringChatResponse) service.masking();

    if ("json".equals(format)) {
      return response.getOrchestrationResponse().getOriginalResponse();
    }
    return response.getResult().getOutput().getContent();
  }
}
