package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.Template;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PromptRegistryTest {

  @Test
  void listTemplates() {
    var controller = new PromptRegistryController();
    var result = controller.listTemplates();
    assertThat(result.getCount()).isGreaterThan(0);
  }

  @Test
  void createDeleteTemplate() {
    var controller = new PromptRegistryController();
    // cleanup
    controller.deleteTemplate();

    // create
    PromptTemplatePostResponse createdTemplate = controller.createTemplate();
    assertThat(createdTemplate.getMessage()).contains("successful");
    assertThat(createdTemplate.getName()).contains(PromptRegistryController.NAME);

    // use template
    PromptTemplateSubstitutionResponse template = controller.useTemplate();
    List<Template> prompt = template.getParsedPrompt();
    assertThat(prompt).hasSize(2);
    SingleChatTemplate userMessage = (SingleChatTemplate) prompt.get(1);
    assertThat(userMessage.getRole()).isEqualTo("user");
    assertThat(userMessage.getContent()).isEqualTo("I love football");

    // cleanup
    List<PromptTemplateDeleteResponse> deletedTemplate = controller.deleteTemplate();
    assertThat(deletedTemplate).hasSize(1);
    assertThat(deletedTemplate.get(0).getMessage()).contains("successful");
  }

  @Test
  void importExportTemplate() {
    var controller = new PromptRegistryController();
    // cleanup
    controller.deleteTemplate();

    // import
    PromptTemplatePostResponse template = controller.importTemplate();
    assertThat(template.getMessage()).contains("successful");

    // export TODO: NOT WORKING

    // cleanup
    List<PromptTemplateDeleteResponse> deletedTemplate = controller.deleteTemplate();
    assertThat(deletedTemplate).hasSize(1);
    assertThat(deletedTemplate.get(0).getMessage()).contains("successful");
  }
}
