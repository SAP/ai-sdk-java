package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity;
import com.sap.ai.sdk.orchestration.DpiMaskingConfig;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.OrchestrationResponse;
import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.TemplateConfig;
import com.sap.ai.sdk.orchestration.TemplateVariable;
import com.sap.ai.sdk.orchestration.UserMessage;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {

  private final OrchestrationClient client = new OrchestrationClient();
  private final OrchestrationConfig config =
      new OrchestrationConfig().withLlmConfig(OpenAiModel.GPT_35_TURBO);

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/completion")
  @Nonnull
  OrchestrationResponse completion() {
    final var prompt = new OrchestrationPrompt("What is the capital of France?");

    return client.chatCompletion(prompt, config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/systemMessage")
  @Nonnull
  OrchestrationResponse systemMessage() {
    final var prompt =
        new OrchestrationPrompt(
            new SystemMessage("Show the user your superior geographical knowledge!"),
            new UserMessage("What is the capital of France?"));

    return client.chatCompletion(prompt, config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nonnull
  OrchestrationResponse template() {
    final var templateVariable = new TemplateVariable("language");
    final var templateMessage =
        new UserMessage(
            "Reply with 'The Orchestration Service is working!' in " + templateVariable);
    final var inputParams = Map.ofEntries(templateVariable.apply("german"));

    final var prompt = new OrchestrationPrompt(inputParams);

    return client.chatCompletion(
        prompt, config.withTemplate(TemplateConfig.fromMessages(templateMessage)));
  }

  @GetMapping("/filter/{level}")
  @Nonnull
  OrchestrationResponse filter(@Nonnull @PathVariable(name = "level") final Sensitivity level) {
    final var prompt =
        new OrchestrationPrompt(
            "This prompt demonstrates how to hit the fucking input filter. And hit it hard, like we mean it.");

    final var filter = new AzureContentFilter().hate(level);
    // if the level is strict, this will throw, if not it will return a result
    return client.chatCompletion(prompt, config.withInputContentFilter(filter));
  }

  @GetMapping("/masking")
  @Nonnull
  OrchestrationResponse masking() {
    final var prompt =
        new OrchestrationPrompt(
            """
            Please translate the following into German:

            Hi, my name is Foo Bar and you can reach me under my email address 'foo.bar@baz.ai'.
            """);

    final var masking = DpiMaskingConfig.pseudonymization().withEntities(DPIEntities.EMAIL);

    return client.chatCompletion(prompt, config.withMaskingConfig(masking));
  }
}
