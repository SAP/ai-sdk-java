package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.DpiMaskingConfig;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.Map;
import javax.annotation.Nonnull;
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

  OrchestrationClient client = new OrchestrationClient().withLlmConfig(LLM_CONFIG);

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/completion")
  @Nonnull
  public CompletionPostResponse completion() {
    var prompt = new OrchestrationPrompt("What is the capital of France?");

    return client.chatCompletion(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nonnull
  public CompletionPostResponse template() {
    var template = ChatMessage.create().role("user").content("{{?input}}");
    var inputParams = Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    var prompt =
        new OrchestrationPrompt(inputParams)
            .withTemplate(TemplatingModuleConfig.create().template(template));

    return client.chatCompletion(prompt);
  }

  @GetMapping("/masking")
  @Nonnull
  public CompletionPostResponse masking() {
    var masking = DpiMaskingConfig.forAnonymization().withEntities(DPIEntities.EMAIL);

    var prompt =
        new OrchestrationPrompt(
                "Please write 'Hello World!' to me via email. My email address is foo.bar@baz.ai")
            .withMaskingConfig(masking);

    return client.chatCompletion(prompt);
  }
}
