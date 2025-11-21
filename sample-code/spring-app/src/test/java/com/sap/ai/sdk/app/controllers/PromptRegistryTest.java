package com.sap.ai.sdk.app.controllers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

public class PromptRegistryTest {

  static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

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
    List<PromptTemplate> prompt = template.getParsedPrompt();
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
  void importExportTemplate() throws IOException {
    var controller = new PromptRegistryController();
    // cleanup
    controller.deleteTemplate();

    // import
    PromptTemplatePostResponse template = controller.importTemplate();
    assertThat(template.getMessage()).contains("successful");

    // export
    final var exportedTemplate = controller.exportTemplate();

    final var resource = new ClassPathResource("prompt-template.yaml");
    final JsonNode expectedYaml = YAML_MAPPER.readTree(resource.getContentAsString(UTF_8));
    assertThat(YAML_MAPPER.readTree(exportedTemplate)).isEqualTo(expectedYaml);
    Files.deleteIfExists(exportedTemplate.toPath());

    // cleanup
    List<PromptTemplateDeleteResponse> deletedTemplate = controller.deleteTemplate();
    assertThat(deletedTemplate).hasSize(1);
    assertThat(deletedTemplate.get(0).getMessage()).contains("successful");
  }

  @Test
  void history() {
    var controller = new PromptRegistryController();
    // cleanup
    controller.deleteTemplate();

    // create + update
    PromptTemplatePostResponse template = controller.updateTemplate();
    assertThat(template.getMessage()).contains("successful");

    // history
    PromptTemplateListResponse history = controller.history();
    // Bug that doesn't delete prompts fast enough. Should be equal to 2
    assertThat(history.getCount()).isGreaterThanOrEqualTo(2);
    assertThat(history.getResources()).hasSizeGreaterThanOrEqualTo(2);

    // cleanup
    List<PromptTemplateDeleteResponse> deletedTemplate = controller.deleteTemplate();
    assertThat(deletedTemplate).hasSize(1);
    assertThat(deletedTemplate.get(0).getMessage()).contains("successful");
  }

  @Test
  void promptRegistryToSpringAi() {
    var controller = new PromptRegistryController();
    var ChatResponse = controller.promptRegistryToSpringAi();
    assertThat(ChatResponse).isNotNull();
    assertThat(ChatResponse.getOutput().getText()).contains("Sports");
  }
}
