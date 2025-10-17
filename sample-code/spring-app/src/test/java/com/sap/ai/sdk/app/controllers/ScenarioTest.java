package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.model.AiModelBaseData;
import com.sap.ai.sdk.core.model.AiModelVersion;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.orchestration.OrchestrationAiModel;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScenarioTest {

  @Test
  @DisplayName(
      "Declared OpenAI models must be superset of our AI Core account's available OpenAI models")
  @SneakyThrows
  void openAiModelAvailability() {

    // Gather AI Core's list of available OpenAI models
    final var aiModelList = new ScenarioController().getModels().getResources();

    final var availableOpenAiModels =
        aiModelList.stream()
            .filter(model -> model.getExecutableId().equals("azure-openai"))
            .collect(
                () -> new HashMap<String, Boolean>(),
                (list, model) -> list.put(model.getModel(), isDeprecated(model)),
                HashMap::putAll);

    // Gather our declared OpenAI models
    Field[] declaredFields = OpenAiModel.class.getFields();

    // get the models from the OpenAiModel class
    HashMap<String, Boolean> declaredOpenAiModelList = new HashMap<>();
    for (Field field : declaredFields) {
      if (field.getType().equals(OpenAiModel.class)) {
        declaredOpenAiModelList.put(
            ((OpenAiModel) field.get(null)).name(), field.isAnnotationPresent(Deprecated.class));
      }
    }

    // Assert that the declared OpenAI models match the expected list
    assertThat(declaredOpenAiModelList.keySet()).containsAll(availableOpenAiModels.keySet());

    SoftAssertions softly = new SoftAssertions();
    for (var model : availableOpenAiModels.entrySet()) {
      Boolean declaredDeprecated = declaredOpenAiModelList.get(model.getKey());
      softly
          .assertThat(declaredDeprecated)
          .withFailMessage(
              "%s is deprecated:%s on AI Core but deprecated:%s in AI SDK",
              model.getKey(), model.getValue(), declaredDeprecated)
          .isEqualTo(model.getValue());
    }
    softly.assertAll();
  }

  @Test
  @DisplayName(
      "Declared Orchestration models must be superset of our AI Core account's available Orchestration models")
  @SneakyThrows
  void orchestrationAiModelAvailability() {

    // Gather AI Core's list of available Orchestration models
    final var aiModelList = new ScenarioController().getModels().getResources();

    var internalOnlyModels = Set.of("abap-codestral");

    final var availableOrchestrationModels =
        aiModelList.stream()
            .filter(
                model ->
                    model.getAllowedScenarios().stream()
                        .anyMatch(scenario -> scenario.getScenarioId().equals("orchestration")))
            .filter(model -> !model.getModel().contains("embed"))
            .filter(model -> !internalOnlyModels.contains(model.getModel()))
            .collect(
                () -> new HashMap<String, Boolean>(),
                (list, model) -> list.put(model.getModel(), isDeprecated(model)),
                HashMap::putAll);

    // Gather our declared Orchestration models
    Field[] declaredFields = OrchestrationAiModel.class.getFields();

    // get the models from the OrchestrationAiModel class
    HashMap<String, Boolean> declaredOrchestrationModelList = new HashMap<>();
    for (Field field : declaredFields) {
      if (field.getType().equals(OrchestrationAiModel.class)) {
        declaredOrchestrationModelList.put(
            ((OrchestrationAiModel) field.get(null)).getName(),
            field.isAnnotationPresent(Deprecated.class));
      }
    }

    // Assert that the declared Orchestration models match the expected list
    assertThat(declaredOrchestrationModelList.keySet())
        .containsAll(availableOrchestrationModels.keySet());

    SoftAssertions softly = new SoftAssertions();
    for (var model : availableOrchestrationModels.entrySet()) {
      Boolean declaredDeprecated = declaredOrchestrationModelList.get(model.getKey());
      softly
          .assertThat(declaredDeprecated)
          .withFailMessage(
              "%s is deprecated:%s on AI Core but deprecated:%s in AI SDK",
              model.getKey(), model.getValue(), declaredDeprecated)
          .isEqualTo(model.getValue());
    }
    softly.assertAll();
  }

  private static boolean isDeprecated(AiModelBaseData model) {
    Optional<AiModelVersion> version =
        model.getVersions().stream().filter(AiModelVersion::isIsLatest).findFirst();
    if (version.isEmpty()) {
      throw new RuntimeException();
    }
    return version.get().isDeprecated();
  }
}
