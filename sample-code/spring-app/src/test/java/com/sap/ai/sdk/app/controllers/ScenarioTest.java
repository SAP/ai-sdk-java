package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.model.AiModelBaseData;
import com.sap.ai.sdk.core.model.AiModelVersion;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

class ScenarioTest {

  @Test
  @DisplayName("Declared OpenAI models must match AI Core's available OpenAI models")
  @DisabledIfSystemProperty(named = "aicore.landscape", matches = "canary")
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
    for (var model : availableOpenAiModels.entrySet()) {
      Boolean declaredDeprecated = declaredOpenAiModelList.get(model.getKey());
      assertThat(declaredDeprecated)
          .withFailMessage(
              "%s is deprecated:%s on AI Core but deprecated:%s in AI SDK",
              model.getKey(), model.getValue(), declaredDeprecated)
          .isEqualTo(model.getValue());
    }
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
