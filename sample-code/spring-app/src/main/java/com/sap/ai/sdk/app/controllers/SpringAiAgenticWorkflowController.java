package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiAgenticWorkflowService;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringChatResponse;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the AgenticWorkflow Service */
@SuppressWarnings("unused")
@RestController
@Slf4j
@RequestMapping("/spring-ai-agentic")
public class SpringAiAgenticWorkflowController {

  @Autowired private SpringAiAgenticWorkflowService service;

  @GetMapping("/chain")
  Object completion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response =
        service.runAgent("I want to do a one-day trip to Paris. Help me make an itinerary, please");

    if ("json".equals(format)) {
      return ((OrchestrationSpringChatResponse) response)
          .getOrchestrationResponse()
          .getOriginalResponse();
    }
    return response.getResult().getOutput().getText();
  }
}
