package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringChatResponse;
import javax.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/spring-ai-orchestration")
class SpringAiOrchestrationController {
  @Autowired private SpringAiOrchestrationService service;
  private static final ObjectMapper MAPPER = getOrchestrationObjectMapper();

  @GetMapping("/completion")
  ResponseEntity<String> completion(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    val response = (OrchestrationSpringChatResponse) service.completion();

    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .body(
              MAPPER.writeValueAsString(response.getOrchestrationResponse().getOriginalResponse()));
    }
    return ResponseEntity.ok(response.getResult().getOutput().getContent());
  }

  @GetMapping("/template")
  ResponseEntity<String> template(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    val response = (OrchestrationSpringChatResponse) service.template();

    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .body(
              MAPPER.writeValueAsString(response.getOrchestrationResponse().getOriginalResponse()));
    }
    return ResponseEntity.ok(response.getResult().getOutput().getContent());
  }

  @GetMapping("/masking")
  ResponseEntity<String> masking(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    val response = (OrchestrationSpringChatResponse) service.masking();

    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .body(
              MAPPER.writeValueAsString(response.getOrchestrationResponse().getOriginalResponse()));
    }
    return ResponseEntity.ok(response.getResult().getOutput().getContent());
  }
}
