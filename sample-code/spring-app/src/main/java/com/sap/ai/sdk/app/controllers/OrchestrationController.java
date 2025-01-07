package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.OrchestrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@SuppressWarnings("unused")
@RequestMapping("/orchestration")
class OrchestrationController {
  @Autowired private OrchestrationService service;

  @PostMapping("/processInput")
  public ResponseEntity<String> processInput(@RequestParam("userInput") String userInput) {
    log.info("User input: {}", userInput);
    final String output = service.processInput(userInput);
    return ResponseEntity.ok(output);
  }
}
