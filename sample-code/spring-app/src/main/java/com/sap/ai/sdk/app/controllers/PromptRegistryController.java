package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.prompt.registry.PromptClient;
import com.sap.ai.sdk.prompt.registry.client.DefaultApi;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpec;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Prompt Registry operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
@RequestMapping("/prompt-registry")
class PromptRegistryController {
  static final String NAME = "java-e2e-test";
  private static final DefaultApi client = new PromptClient();

  @GetMapping("/listTemplates")
  PromptTemplateListResponse listTemplates() {
    return client.listPromptTemplates();
  }

  @GetMapping("/createUpdateTemplate")
  PromptTemplatePostResponse createUpdateTemplate() {
    final var spec =
        PromptTemplateSpec.create()
            .template(
                SingleChatTemplate.create()
                    .role("system")
                    .content(
                        "You classify input text into the two following categories: {{?categories}}"),
                SingleChatTemplate.create().role("user").content("{{?inputExample}}"))
            .defaults(Map.of("categories", "Finance, Tech, Sports"));

    final var request =
        PromptTemplatePostRequest.create()
            .name(NAME)
            .version("0.0.1")
            .scenario("categorization")
            .spec(spec);

    return client.createUpdatePromptTemplate(request);
  }

  @GetMapping("/importTemplate")
  PromptTemplatePostResponse importTemplate() {
    final File templateFile = new File("src/main/resources/prompt-template.yaml");
    return client.importPromptTemplate(templateFile);
  }

  @GetMapping("/exportTemplate")
  File exportTemplate() {
    final var template = importTemplate();
    return client.exportPromptTemplate(template.getId());
  }

  @GetMapping("/useTemplate")
  PromptTemplateSubstitutionResponse useTemplate() {
    final var template = createUpdateTemplate();
    return client.parsePromptTemplateById(
        template.getId(),
        false,
        PromptTemplateSubstitutionRequest.create()
            .inputParams(Map.of("inputExample", "I love football")));
  }

  @GetMapping("/deleteTemplate")
  List<PromptTemplateDeleteResponse> deleteTemplate() {
    final PromptTemplateListResponse templates = client.listPromptTemplates();

    return templates.getResources().stream()
        .filter(template -> NAME.equals(template.getName()))
        .map(template -> client.deletePromptTemplate(template.getId()))
        .toList();
  }
}
