package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {
  static final String MODEL = "gpt-35-turbo";
  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create().modelName(MODEL).modelParams(Map.of());
  private static final TemplatingModuleConfig TEMPLATE_CONFIG =
      TemplatingModuleConfig.create()
          .template(ChatMessage.create().role("user").content("What is the capital of France?"));

  OrchestrationClient client =
      new OrchestrationClient().withLlmConfig(LLM_CONFIG).withTemplate(TEMPLATE_CONFIG);

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nullable
  public CompletionPostResponse template() {
    var template = ChatMessage.create().role("user").content("{{?input}}");
    var inputParams = Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    var config =
        new OrchestrationConfig().withTemplate(TemplatingModuleConfig.create().template(template));
    var prompt = new OrchestrationPrompt(config, null, inputParams);

    return client.chatCompletion(prompt);
  }

  @GetMapping("/masking")
  @Nullable
  public CompletionPostResponse masking() {

    var masking = MaskingModuleConfig.create()
            .maskingProviders(MaskingProviderConfig.create().type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
                    .method(MaskingProviderConfig.MethodEnum.ANONYMIZATION)
                    .entities(List.of(DPIEntityConfig.create().type(DPIEntities.EMAIL))));

    var messages = List.of(ChatMessage.create().role("user").content("My email is foo.bar@baz.ai"));

    var config = new OrchestrationConfig().withMaskingConfig(masking);
    var prompt = new OrchestrationPrompt(config, messages, null );

    return client.chatCompletion(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/messagesHistory")
  @Nullable
  public CompletionPostResponse messagesHistory() {
    final List<ChatMessage> messagesHistory =
        List.of(
            ChatMessage.create().role("user").content("Let's pretend for a moment France chose the second-largest city to be the new capital of France."),
            ChatMessage.create().role("assistant").content("Okay, weird, but okay.."));

    var prompt = new OrchestrationPrompt(messagesHistory);

    return client.chatCompletion(prompt);
  }
}
