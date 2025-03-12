package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.model.AiModelBaseData;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.ai.sdk.core.model.AiScenario;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Scenario operations */
@RestController
@SuppressWarnings("unused") // debug method that doesn't need to be tested
class ScenarioController {

  private static final ScenarioApi CLIENT = new ScenarioApi();

  @GetMapping("/scenarios")
  @Nonnull
  Object getScenarios(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var scenarioList = CLIENT.query("default");
    if ("json".equals(format)) {
      return scenarioList;
    }
    final var items =
        scenarioList.getResources().stream()
            .map(AiScenario::getName)
            .collect(Collectors.joining(", "));
    return "The following scenarios are available: %s.".formatted(items);
  }

  @Nonnull
  AiModelList getModels() {
    return CLIENT.queryModels("foundation-models", "default");
  }

  @GetMapping("/models")
  @Nonnull
  Object getModels(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var modelList = getModels();
    if ("json".equals(format)) {
      return modelList;
    }
    final var items =
        modelList.getResources().stream()
            .map(AiModelBaseData::getModel)
            .collect(Collectors.joining(", "));
    return "The following models are available: %s.".formatted(items);
  }
}
