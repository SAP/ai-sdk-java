package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiModelBaseData;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScenarioTest {

  @Test
  @DisplayName("Declared OpenAI models must match AI Core's available OpenAI models")
  @SneakyThrows
  public void openAiModelAvailability() {

    // Gather AI Core's list of available OpenAI models
    final var aiModelList = new ScenarioController().getModels().getResources();

    final var availableOpenAiModels =
        aiModelList.stream()
            .filter(model -> model.getExecutableId().equals("azure-openai"))
            .map(AiModelBaseData::getModel)
            .toList();

    // Gather our declared OpenAI models
    Field[] declaredFields = OpenAiModel.class.getFields();

    // get the models from the OpenAiModel class
    List<String> declaredOpenAiModelList = new ArrayList<>();
    for (Field field : declaredFields) {
      if (field.getType().equals(OpenAiModel.class)) {
        declaredOpenAiModelList.add(((OpenAiModel) field.get(null)).model());
      }
    }

    // Assert that the declared OpenAI models match the expected list
    assertThat(declaredOpenAiModelList).hasSameElementsAs(availableOpenAiModels);
  }
}