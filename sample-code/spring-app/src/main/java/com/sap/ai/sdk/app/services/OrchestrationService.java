package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.orchestration.Grounding;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.ResponseJsonSchema;
import com.sap.ai.sdk.orchestration.TemplateConfig;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import javax.annotation.Nonnull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;




/** Service class for the Demo app */
@Service
@Slf4j
public class OrchestrationService {


  static class Translation {
    @JsonProperty(required = true)
    private String input;

    @JsonProperty(required = true)
    private String translation;

    @JsonProperty(required = true)
    private String language;
  }




  @Nonnull
  public String processInput(@Nonnull final String userInput) {
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI.withParam(TEMPERATURE, 0));
    var prompt = new OrchestrationPrompt(userInput);

    var schema = ResponseJsonSchema.fromType(Translation.class).withStrict(true);
    var templateConfig = TemplateConfig.create().withJsonSchemaResponse(schema);

    var response = client.chatCompletion(prompt, config.withTemplateConfig(templateConfig));
    return response.getContent();
  }





















  @Nonnull
  public String processInputWithGrounding(@Nonnull final String userInput) {
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI.withParam(TEMPERATURE, 0));

    var schema = ResponseJsonSchema.fromType(Translation.class).withStrict(true);
    var templatingConfig = TemplateConfig.create().withJsonSchemaResponse(schema);

    var groundingFilter = DocumentGroundingFilter.create().dataRepositoryType(DataRepositoryType.HELP_SAP_COM);
    var groundingConfig = Grounding.create().filters(groundingFilter);
    var prompt = groundingConfig.createGroundingPrompt(userInput);
    var response = client.chatCompletion(
        prompt,
        config.withTemplateConfig(templatingConfig).withGrounding(groundingConfig));
    return response.getContent();
  }


















  @Nonnull
  public String processInput00(@Nonnull final String userInput) {
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO.withParam(TEMPERATURE, 0));
    var prompt = new OrchestrationPrompt(userInput);

    var response = client.chatCompletion(prompt, config);
    return response.getContent();
  }

  @Nonnull
  public String processInput01(@Nonnull final String userInput) {
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI.withParam(TEMPERATURE, 0));
    var prompt = new OrchestrationPrompt(userInput);

    var schema = ResponseJsonSchema.fromType(Translation.class).withStrict(true);
    var templatingConfig = TemplateConfig.create().withJsonSchemaResponse(schema);

    var response = client.chatCompletion(prompt, config.withTemplateConfig(templatingConfig));
    return response.getContent();
  }

}
