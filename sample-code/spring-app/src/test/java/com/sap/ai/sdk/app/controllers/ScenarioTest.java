package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiModelBaseData;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class ScenarioTest {

  @Test
  public void declaredOpenAiModelsList() {

    // Gather the expected list of OpenAI models
    final AiModelList aiModelListResponse = new ScenarioController().getModels();
    assertThat(aiModelListResponse).isNotNull();

    List<AiModelBaseData> aiModelList = aiModelListResponse.getResources();
    assertThat(aiModelList).isNotNull().isNotEmpty();

    List<String> expectedOpenAiModelList = new ArrayList<>();
    for (AiModelBaseData model : aiModelList) {
      if (model.getExecutableId().equals("azure-openai")) {
        expectedOpenAiModelList.add(model.getModel());
      }
    }

    // Gather the declared OpenAI models
    Field[] declaredFields = OpenAiModel.class.getDeclaredFields();

    List<String> declaredOpenAiModelList = new ArrayList<>();
    for (Field field : declaredFields) {
      if (field.getType().equals(OpenAiModel.class)) {
        OpenAiModel openAiModel = Try.of(() -> (OpenAiModel) field.get(null)).getOrNull();
        if (openAiModel != null) {
          declaredOpenAiModelList.add(openAiModel.model());
        }
      }
    }

    // Compare the declared OpenAI models with the expected list of OpenAI models
    assertThat(declaredOpenAiModelList).hasSameElementsAs(expectedOpenAiModelList);
  }
}
