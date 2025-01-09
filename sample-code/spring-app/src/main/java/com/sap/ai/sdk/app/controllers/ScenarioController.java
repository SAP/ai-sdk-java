package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.ai.sdk.core.model.AiScenarioList;
import javax.annotation.Nonnull;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Scenario operations */
@RestController
@SuppressWarnings("unused") // debug method that doesn't need to be tested
class ScenarioController {

//  TODO: Fix javadocs

  private static final ScenarioApi CLIENT = new ScenarioApi();
  private final ObjectMapper mapper =
      new ObjectMapper()
          .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
          .registerModule(new JavaTimeModule());

  /**
   * Get the list of available scenarios
   *
   * @return the list of available scenarios
   */
  @GetMapping("/scenarios")
  @Nonnull
  ResponseEntity<String> getScenarios(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    var scenarioList = CLIENT.query("default");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(scenarioList));
    }
    return ResponseEntity.ok(buildScenarioMessage(scenarioList));
  }

  @Nonnull
  AiModelList getModels() {
    return CLIENT.queryModels("foundation-models", "default");
  }

  /**
   * Get the list of available models
   *
   * @return the list of available models
   */
  @GetMapping("/models")
  @Nonnull
  ResponseEntity<String> getModels(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    var modelList = CLIENT.queryModels("foundation-models", "default");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(modelList));
    }
    return ResponseEntity.ok(buildModelMessage(modelList));
  }

  String buildScenarioMessage(AiScenarioList scenarioList) {
    var message = new StringBuilder("The following scenarios are available: ");
    for (var resource : scenarioList.getResources()) {
      message.append(resource.getName()).append(", ");
    }
    message.setCharAt(message.length() - 2, '.');
    return message.toString();
  }

  String buildModelMessage(AiModelList modelList) {
    var message = new StringBuilder("The following models are available: ");
    for (var resource : modelList.getResources()) {
      message.append(resource.getModel()).append(", ");
    }
    message.setCharAt(message.length() - 2, '.');
    return message.toString();
  }
}
