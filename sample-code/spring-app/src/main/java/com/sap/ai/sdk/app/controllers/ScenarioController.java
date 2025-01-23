package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.model.AiModelBaseData;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.ai.sdk.core.model.AiScenario;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Scenario operations */
@RestController
@SuppressWarnings("unused") // debug method that doesn't need to be tested
class ScenarioController {

  private static final ScenarioApi CLIENT = new ScenarioApi();

  /**
   * Get the list of available scenarios.
   *
   * @param accept the accept header
   * @return a response entity with a string representation of the list of available scenarios
   */
  @GetMapping("/scenarios")
  @Nonnull
  ResponseEntity<Object> getScenarios(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept) {
    final var scenarioList = CLIENT.query("default");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(scenarioList);
    }
    final var items =
        scenarioList.getResources().stream()
            .map(AiScenario::getName)
            .collect(Collectors.joining(", "));
    return ResponseEntity.ok("The following scenarios are available: %s.".formatted(items));
  }

  /**
   * Get the list of available models.
   *
   * @return the list of available models
   */
  @Nonnull
  AiModelList getModels() {
    return CLIENT.queryModels("foundation-models", "default");
  }

  /**
   * Get the list of available models.
   *
   * @param accept the accept header
   * @return a response entity with a string representation of the list of available models
   */
  @GetMapping("/models")
  @Nonnull
  ResponseEntity<Object> getModels(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept) {
    final var modelList = getModels();
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(modelList);
    }
    final var items =
        modelList.getResources().stream()
            .map(AiModelBaseData::getModel)
            .collect(Collectors.joining(", "));
    return ResponseEntity.ok("The following models are available: %s.".formatted(items));
  }
}
