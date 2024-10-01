package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.model.AiDeploymentDeletionResponse;
import com.sap.ai.sdk.core.client.model.AiModelBaseData;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioControllerTest {

    @Test
    void testOpenAiModels() {

        ScenarioController controller = new ScenarioController();
        AiModelList models = controller.getModels();
        List<String> expectedModelNames = models.getResources()
            .stream()
            .filter(modelEntry -> modelEntry.getExecutableId().equals("azure-openai"))
            .map(AiModelBaseData::getModel)
            .toList();

        Field[] declaredFields = OpenAiModel.class.getDeclaredFields();
        List<String> existingModels = Arrays.stream(declaredFields)
            .filter(field -> field.getType().equals(OpenAiModel.class))
            .map(field -> Try.of(() -> (OpenAiModel) field.get(null)).getOrNull())
            .filter(Objects::nonNull)
            .map(OpenAiModel::model)
            .toList();

        assertThat(existingModels).hasSameElementsAs(expectedModelNames);
    }
}
