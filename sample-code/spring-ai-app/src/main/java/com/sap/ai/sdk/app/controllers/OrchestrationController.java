package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {

  ChatModel client = new OrchestrationChatModel(new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO));

  @GetMapping("/completion")
  ChatResponse completion() {
    var prompt = new Prompt("What is the capital of France?");

    return client.call(prompt);
  }

  @GetMapping("/template/local")
  ChatResponse templateLocal() {
    var template = new PromptTemplate("input");
    var prompt = template.create(Map.of("input", "Hello World!"));

    return client.call(prompt);
  }
//
//  @GetMapping("/template/remote")
//  ChatResponse templateRemote() {
//    List<Message> messages = List.of(new UserMessage("{{?input}}"));
//    var opts =
//        new OrchestrationChatOptions()
//            .withTemplate(messages)
//            .withTemplateParameters(Map.of("input", "Hello World!"));
//    var prompt = new Prompt(List.of(), opts);
//
//    return client.call(prompt);
//  }

  @GetMapping("/masking")
  ChatResponse masking() {
    var masking =
        DpiMasking.anonymization()
            .withEntities(DPIEntities.EMAIL, DPIEntities.ADDRESS, DPIEntities.LOCATION);

    var opts = new OrchestrationChatOptions();
    opts.setConfig(new OrchestrationModuleConfig().withMaskingConfig(masking));
    var prompt =
        new Prompt(
            "Please write 'Hello World!' to me via email. My email address is foo.bar@baz.ai",
            opts);

    return client.call(prompt);
  }

  @GetMapping("/chatMemory")
  ChatResponse chatMemory() {
    var memory = new InMemoryChatMemory();
    var advisor = new MessageChatMemoryAdvisor(memory);
    ChatClient cl = ChatClient.builder(client).defaultAdvisors(advisor).build();
    var prompt = new Prompt("Hello World!");

    cl.prompt(prompt).call();
    return cl.prompt(prompt).call().chatResponse();
  }
}
