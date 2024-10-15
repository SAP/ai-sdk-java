package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.DpiMaskingConfig;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {

  // uses defaults from application.yaml
  @Autowired OrchestrationChatModel client;

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

  @GetMapping("/template/remote")
  ChatResponse templateRemote() {
    List<Message> messages = List.of(new UserMessage("{{?input}}"));
    var opts =
        new OrchestrationChatOptions()
            .withTemplate(messages)
            .withTemplateParameters(Map.of("input", "Hello World!"));
    var prompt = new Prompt(List.of(), opts);

    return client.call(prompt);
  }

  @GetMapping("/masking")
  ChatResponse masking() {
    var masking = DpiMaskingConfig.forAnonymization().withEntities(DPIEntities.EMAIL);

    var opts = new OrchestrationChatOptions().withMaskingConfig(masking);
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
