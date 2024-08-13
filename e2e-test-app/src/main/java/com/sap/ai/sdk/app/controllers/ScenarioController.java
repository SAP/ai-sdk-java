package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.core.Core.getClient;

import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.core.client.model.AiScenarioList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Scenario operations */
@RestController
@SuppressWarnings("unused") // debug method that doesn't need to be tested
public class ScenarioController {

  private static final ScenarioApi API = new ScenarioApi(getClient());

  /**
   * Get the list of available scenarios
   *
   * @return the list of available scenarios
   */
  @GetMapping("/scenarios")
  AiScenarioList getScenarios() {
    return API.scenarioQuery("default");
  }

  /**
   * Get the list of available models
   *
   * @return the list of available models
   */
  @GetMapping("/models")
  public AiModelList getModels() {
    return API.modelsGet("foundation-models", "default");
  }
}
