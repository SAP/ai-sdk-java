package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity;
import com.sap.ai.sdk.orchestration.DpiMaskingConfig;
import com.sap.ai.sdk.orchestration.LlmConfig;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
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
  static final String MODEL = "gpt-35-turbo";

  private final OrchestrationClient client =
      new OrchestrationClient().withLlmConfig(new LlmConfig(MODEL));

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/completion")
  @Nonnull
  public OrchestrationResponse completion() {
    var prompt = new OrchestrationPrompt("What is the capital of France?");

    return client.chatCompletion(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/systemMessage")
  @Nonnull
  public OrchestrationResponse systemMessage() {
    var prompt =
        new OrchestrationPrompt(
            new SystemMessage("Show the user your superior geographical knowledge!"),
            new UserMessage("What is the capital of France?"));

    return client.chatCompletion(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nonnull
  public OrchestrationResponse template() {
    var templateVariable = TemplateVariable.of("language");
    var templateMessage =
        new UserMessage(
            "Reply with 'The Orchestration Service is working!' in " + templateVariable);
    var inputParams = Map.ofEntries(templateVariable.apply("german"));

    var prompt =
        new OrchestrationPrompt(inputParams)
            .withTemplate(TemplateConfig.fromMessages(templateMessage));

    return client.chatCompletion(prompt);
  }

  @GetMapping("/filter/{level}")
  @Nonnull
  public OrchestrationResponse filter(@Nonnull @PathVariable(name = "level") Sensitivity level) {
    var filter = new AzureContentFilter().hate(level);
    var prompt =
        new OrchestrationPrompt(
                "This prompt demonstrates how to hit the fucking input filter. And hit it hard, like we mean it.")
            .withInputContentFilter(filter);

    // if the level is strict, this will throw, if not it will return a result
    return client.chatCompletion(prompt);
  }

  @GetMapping("/masking")
  @Nonnull
  public OrchestrationResponse masking() {
    var masking = DpiMaskingConfig.pseudonymization().withEntities(DPIEntities.EMAIL);

    var prompt =
        new OrchestrationPrompt(
                """
            Please translate the following into German:

            Hi, my name is Foo Bar and you can reach me under my email address 'foo.bar@baz.ai'.
            """)
            .withMaskingConfig(masking);

    return client.chatCompletion(prompt);
  }
}
