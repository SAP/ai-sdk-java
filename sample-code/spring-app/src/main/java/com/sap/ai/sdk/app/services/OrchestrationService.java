package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Grounding;
import com.sap.ai.sdk.orchestration.ImageItem;
import com.sap.ai.sdk.orchestration.LlamaGuardFilter;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationAiModel;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.ResponseJsonSchema;
import com.sap.ai.sdk.orchestration.TemplateConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingFilterSearchConfiguration;
import com.sap.ai.sdk.orchestration.model.LlamaGuard38b;
import com.sap.ai.sdk.orchestration.model.ResponseFormatText;
import com.sap.ai.sdk.orchestration.model.SearchDocumentKeyValueListPair;
import com.sap.ai.sdk.orchestration.model.SearchSelectOptionEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

    var response = client.chatCompletion(prompt, config);
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
    var response = client.chatCompletion(prompt, config.withTemplateConfig(templatingConfig).withGrounding(groundingConfig));
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


//  /**
//   * Chat request to OpenAI through the Orchestration service with a simple prompt.
//   *
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse completion(@Nonnull final String famousPhrase) {
//    val prompt = new OrchestrationPrompt(famousPhrase + " Why is this phrase so famous?");
//    return client.chatCompletion(prompt, config);
//  }

//  /**
//   * Chat request to OpenAI through the Orchestration service with an image.
//   *
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse imageInput(@Nonnull final String pathToImage) {
//    final var llmWithImageSupportConfig =
//        new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
//
//    final var multiMessage =
//        Message.user("What is in this image?").withImage(pathToImage, ImageItem.DetailLevel.LOW);
//    final var prompt = new OrchestrationPrompt(multiMessage);
//    return client.chatCompletion(prompt, llmWithImageSupportConfig);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service with multiple strings.
//   *
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse multiStringInput(@Nonnull final List<String> questions) {
//    final var multiMessage =
//        Message.user(questions.get(0)).withText(questions.get(1)).withText(questions.get(2));
//    final var prompt = new OrchestrationPrompt(multiMessage);
//    return client.chatCompletion(prompt, config);
//  }
//
//  /**
//   * Asynchronous stream of an OpenAI chat request
//   *
//   * @return a stream of assistant message responses
//   */
//  @Nonnull
//  public Stream<String> streamChatCompletion(@Nonnull final String topic) {
//    val prompt =
//        new OrchestrationPrompt(
//            "Please create a small story about " + topic + " with around 700 words.");
//    return client.streamChatCompletion(prompt, config);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service with a template.
//   *
//   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
//   *     AI Core: Orchestration - Templating</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse template(@Nonnull final String language) {
//    val template = Message.user("Reply with 'Orchestration Service is working!' in {{?language}}");
//    val templatingConfig =
//        TemplateConfig.create().withTemplate(List.of(template.createChatMessage()));
//    val configWithTemplate = config.withTemplateConfig(templatingConfig);
//
//    val inputParams = Map.of("language", language);
//    val prompt = new OrchestrationPrompt(inputParams);
//
//    return client.chatCompletion(prompt, configWithTemplate);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service using message history.
//   *
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse messagesHistory(@Nonnull final String prevMessage) {
//    val prompt = new OrchestrationPrompt(Message.user(prevMessage));
//
//    val result = client.chatCompletion(prompt, config);
//
//    // Let's presume a user asks the following follow-up question
//    val nextPrompt =
//        new OrchestrationPrompt(Message.user("What is the typical food there?"))
//            .messageHistory(result.getAllMessages());
//
//    return client.chatCompletion(nextPrompt, config);
//  }
//
//  /**
//   * Apply input filtering for a request to orchestration.
//   *
//   * @link <a
//   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
//   *     AI Core: Orchestration - Input Filtering</a>
//   * @throws OrchestrationClientException if input filter filters the prompt
//   * @param policy the explicitness of content that should be allowed through the filter
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse inputFiltering(@Nonnull final AzureFilterThreshold policy)
//      throws OrchestrationClientException {
//    val prompt =
//        new OrchestrationPrompt("'We shall spill blood tonight', said the operation in-charge.");
//    val filterConfig =
//        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);
//
//    val configWithFilter = config.withInputFiltering(filterConfig);
//
//    return client.chatCompletion(prompt, configWithFilter);
//  }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//  /**
//   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
//   *
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse completionWithResourceGroup(
//      @Nonnull final String resourceGroup, @Nonnull final String famousPhrase) {
//    val destination =
//        new AiCoreService().getInferenceDestination(resourceGroup).forScenario("orchestration");
//    val clientWithResourceGroup = new OrchestrationClient(destination);
//
//    val prompt = new OrchestrationPrompt(famousPhrase + " Why is this phrase so famous?");
//
//    return clientWithResourceGroup.chatCompletion(prompt, config);
//  }
//
//  /**
//   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
//   * SDK. Pseudonymize the user's name and location to protect their privacy.
//   *
//   * @link <a
//   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
//   *     Core: Orchestration - Data Masking</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse maskingPseudonymization(@Nonnull final DPIEntities entity) {
//    val systemMessage =
//        Message.system(
//            """
//                            Please write an initial response to the below user feedback, stating that we are working on the feedback and will get back to them soon.
//                            Please make sure to address the user in person and end with "Best regards, the AI SDK team".
//                            """);
//    val userMessage =
//        Message.user(
//            """
//                            Username: Mallory
//                            userEmail: mallory@sap.com
//                            Date: 2022-01-01
//
//                            I think the SDK is good, but could use some further enhancements.
//                            My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
//                            """);
//
//    val prompt = new OrchestrationPrompt(systemMessage, userMessage);
//    val maskingConfig = DpiMasking.pseudonymization().withEntities(entity, DPIEntities.EMAIL);
//    val configWithMasking = config.withMaskingConfig(maskingConfig);
//
//    return client.chatCompletion(prompt, configWithMasking);
//  }
//
//  /**
//   * Using grounding to provide additional context to the AI model.
//   *
//   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
//   *     AI Core: Orchestration - Grounding</a>
//   * @param userMessage the user message to provide grounding for
//   * @param maskGroundingInput whether to mask the request sent to the Grounding Service
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse grounding(
//      @Nonnull final String userMessage, final boolean maskGroundingInput) {
//    // optional filter for collections
//    val documentMetadata =
//        SearchDocumentKeyValueListPair.create()
//            .key("document metadata")
//            .value("2")
//            .addSelectModeItem(SearchSelectOptionEnum.IGNORE_IF_KEY_ABSENT);
//    // optional filter for document chunks
//    val databaseFilter =
//        DocumentGroundingFilter.create()
//            .dataRepositoryType(DataRepositoryType.VECTOR)
//            .searchConfig(GroundingFilterSearchConfiguration.create().maxChunkCount(1))
//            .addDocumentMetadataItem(documentMetadata);
//
//    val groundingConfig = Grounding.create().filters(databaseFilter);
//    val prompt = groundingConfig.createGroundingPrompt(userMessage);
//    val maskingConfig = // optional masking configuration
//        DpiMasking.anonymization()
//            .withEntities(DPIEntities.SENSITIVE_DATA)
//            .withMaskGroundingInput(maskGroundingInput)
//            .withAllowList(List.of("SAP", "Joule"));
//    val configWithGrounding =
//        config.withGrounding(groundingConfig).withMaskingConfig(maskingConfig);
//
//    return client.chatCompletion(prompt, configWithGrounding);
//  }
//
//  /**
//   * Using grounding via *help.sap.com* to provide additional SAP-specific context to the AI model.
//   *
//   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
//   *     AI Core: Orchestration - Grounding</a>
//   * @param userMessage the user message to provide grounding for
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse groundingHelpSapCom(@Nonnull final String userMessage) {
//    val groundingHelpSapCom =
//        DocumentGroundingFilter.create().dataRepositoryType(DataRepositoryType.HELP_SAP_COM);
//    val groundingConfig = Grounding.create().filters(groundingHelpSapCom);
//    val configWithGrounding = config.withGrounding(groundingConfig);
//
//    val prompt = groundingConfig.createGroundingPrompt(userMessage);
//
//    return client.chatCompletion(prompt, configWithGrounding);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service using response format with JSON
//   * schema.
//   *
//   * @link <a
//   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/structured-output">SAP
//   *     AI Core: Orchestration - Structured Output</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse responseFormatJsonSchema(@Nonnull final String word) {
//    //    Example class
//    class Translation {
//      @JsonProperty(required = true)
//      private String translation;
//
//      @JsonProperty(required = true)
//      private String language;
//    }
//
//    val schema =
//        ResponseJsonSchema.fromType(Translation.class)
//            .withDescription("Output schema for language translation.")
//            .withStrict(true);
//    val configWithResponseSchema =
//        config.withTemplateConfig(TemplateConfig.create().withJsonSchemaResponse(schema));
//
//    val prompt =
//        new OrchestrationPrompt(
//            Message.user("Whats '%s' in German?".formatted(word)),
//            Message.system("You are a language translator."));
//
//    return client.chatCompletion(prompt, configWithResponseSchema);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service using the response format option 'JSON
//   * object'.
//   *
//   * @link <a
//   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/structured-output">SAP
//   *     AI Core: Orchestration - Structured Output</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse responseFormatJsonObject(@Nonnull final String word) {
//    val template = Message.user("What is '%s' in German?".formatted(word));
//    val templatingConfig =
//        TemplateConfig.create()
//            .withTemplate(List.of(template.createChatMessage()))
//            .withJsonResponse();
//    val configWithTemplate = config.withTemplateConfig(templatingConfig);
//
//    val prompt =
//        new OrchestrationPrompt(
//            Message.system(
//                "You are a language translator. Answer using the following JSON format: {\"language\": ..., \"translation\": ...}"));
//
//    return client.chatCompletion(prompt, configWithTemplate);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service using a template from the prompt
//   * registry.
//   *
//   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
//   *     AI Core: Orchestration - Templating</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse templateFromPromptRegistryById(@Nonnull final String topic) {
//    final var llmWithImageSupportConfig =
//        new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
//
//    val template = TemplateConfig.reference().byId("21cb1358-0bf1-4f43-870b-00f14d0f9f16");
//    val configWithTemplate = llmWithImageSupportConfig.withTemplateConfig(template);
//
//    val inputParams = Map.of("language", "Italian", "input", topic);
//    val prompt = new OrchestrationPrompt(inputParams);
//
//    return client.chatCompletion(prompt, configWithTemplate);
//  }
//
//  /**
//   * Chat request to OpenAI through the Orchestration service using a template from the prompt
//   * registry.
//   *
//   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
//   *     AI Core: Orchestration - Templating</a>
//   * @return the assistant response object
//   */
//  @Nonnull
//  public OrchestrationChatResponse templateFromPromptRegistryByScenario(
//      @Nonnull final String topic) {
//    final var llmWithImageSupportConfig =
//        new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
//
//    val template = TemplateConfig.reference().byScenario("test").name("test").version("0.0.1");
//    val configWithTemplate = config.withTemplateConfig(template);
//
//    val inputParams = Map.of("language", "Italian", "input", topic);
//    val prompt = new OrchestrationPrompt(inputParams);
//
//    return client.chatCompletion(prompt, configWithTemplate);
//  }
}
