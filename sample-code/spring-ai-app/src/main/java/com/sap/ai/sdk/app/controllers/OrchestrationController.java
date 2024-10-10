package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {
  // uses defaults from application.yaml
  @Autowired OrchestrationChatModel client;

  String example1() {
    ChatResponse response = client.call(new Prompt("Hello World!"));

    return response.getResult().getOutput().getContent();
  }

  private void example2() {
    List<Message> messages =
        List.of(new SystemMessage("You are a helpful AI."), new UserMessage("Hello World!"));
    client.call(new Prompt(messages));
  }

  private void example3() {
    var opts =
        new OrchestrationChatOptions()
            .withLlmConfig(
                LLMModuleConfig.create().modelName("gpt-3.5-turbo").modelParams(Map.of()))
            .withTemplate(
                TemplatingModuleConfig.create()
                    .template(
                        ChatMessage.create().role("system").content("You are a helpful AI.")));
    var prompt = new Prompt("Hello World!", opts);
  }
}
