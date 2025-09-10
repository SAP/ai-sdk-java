package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatModel;
import com.sap.ai.sdk.prompt.registry.PromptClient;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpec;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.spring.SpringAiConverter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Prompt Registry operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
@RequestMapping("/prompt-registry")
class PromptRegistryController {
  static final String NAME = "java-e2e-test";
  private static final PromptClient client = new PromptClient();

  @GetMapping("/listTemplates")
  PromptTemplateListResponse listTemplates() {
    return client.listPromptTemplates();
  }

  @GetMapping("/createTemplate")
  PromptTemplatePostResponse createTemplate() {
    return client.createUpdatePromptTemplate(getTemplate("Finance, Tech, Sports"));
  }

  @GetMapping("/updateTemplate")
  PromptTemplatePostResponse updateTemplate() {
    // create template then update
    client.createUpdatePromptTemplate(getTemplate("Finance, Tech, Sports"));
    return client.createUpdatePromptTemplate(getTemplate("Finance, Tech, Sports, Politics"));
  }

  private PromptTemplatePostRequest getTemplate(final String categories) {
    final var spec =
        PromptTemplateSpec.create()
            .template(
                SingleChatTemplate.create()
                    .role("system")
                    .content(
                        "You classify input text into the two following categories: {{?categories}}"),
                SingleChatTemplate.create().role("user").content("{{?inputExample}}"))
            .defaults(Map.of("categories", categories));

    return PromptTemplatePostRequest.create()
        .name(NAME)
        .version("0.0.1")
        .scenario("categorization")
        .spec(spec);
  }

  @GetMapping("/history")
  PromptTemplateListResponse history() {
    return client.listPromptTemplateHistory("categorization", "0.0.1", NAME);
  }

  @GetMapping("/importTemplate")
  PromptTemplatePostResponse importTemplate() throws IOException {
    final Resource template = new ClassPathResource("prompt-template.yaml");
    return client.importPromptTemplate("default", null, template.getFile());
  }

  @GetMapping("/exportTemplate")
  File exportTemplate() throws IOException {
    final var template = importTemplate();
    return client.exportPromptTemplate(template.getId());
  }

  @GetMapping("/useTemplate")
  PromptTemplateSubstitutionResponse useTemplate() {
    final var template = createTemplate();
    return client.parsePromptTemplateById(
        template.getId(),
        "default",
        null,
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

  @GetMapping("/promptRegistryToSpringAi")
  Generation promptRegistryToSpringAi() {
    val openAiClient = new OpenAiChatModel(OpenAiClient.forModel(OpenAiModel.GPT_4O_MINI));
    val repository = new InMemoryChatMemoryRepository();
    val memory = MessageWindowChatMemory.builder().chatMemoryRepository(repository).build();
    val advisor = MessageChatMemoryAdvisor.builder(memory).build();
    val cl = ChatClient.builder(openAiClient).defaultAdvisors(advisor).build();

    val promptResponse =
        new PromptClient()
            .parsePromptTemplateByNameVersion(
                "categorization",
                "0.0.1",
                "java-e2e-test",
                "default",
                null,
                false,
                PromptTemplateSubstitutionRequest.create()
                    .inputParams(Map.of("inputExample", "I love football")));

    final List<Message> messages = SpringAiConverter.promptTemplateToMessages(promptResponse);
    val prompt = new Prompt(messages);
    val response = cl.prompt(prompt).call().chatResponse();
    return response != null ? response.getResult() : null;
  }
}
