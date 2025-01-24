package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Grounding;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingFilterSearchConfiguration;
import com.sap.ai.sdk.orchestration.model.SearchDocumentKeyValueListPair;
import com.sap.ai.sdk.orchestration.model.SearchSelectOptionEnum;
import com.sap.ai.sdk.orchestration.model.Template;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for the Orchestration service */
@Service
@Slf4j
public class OrchestrationService {
  private final OrchestrationClient client = new OrchestrationClient();

  @Getter
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GEMINI_1_5_FLASH.withParam(TEMPERATURE, 0.0));

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse completion(@Nonnull final String famousPhrase) {
    val prompt = new OrchestrationPrompt(famousPhrase + " Why is this phrase so famous?");
    return client.chatCompletion(prompt, config);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String topic) {
    val prompt =
        new OrchestrationPrompt(
            "Please create a small story about " + topic + " with around 700 words.");
    return client.streamChatCompletion(prompt, config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse template(@Nonnull final String language) {
    val template = Message.user("Reply with 'Orchestration Service is working!' in {{?language}}");
    val templatingConfig = Template.create().template(List.of(template.createChatMessage()));
    val configWithTemplate = config.withTemplateConfig(templatingConfig);

    val inputParams = Map.of("language", language);
    val prompt = new OrchestrationPrompt(inputParams);

    return client.chatCompletion(prompt, configWithTemplate);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse messagesHistory(@Nonnull final String prevMessage) {
    val prompt = new OrchestrationPrompt(Message.user(prevMessage));

    val result = client.chatCompletion(prompt, config);

    // Let's presume a user asks the following follow-up question
    val nextPrompt =
        new OrchestrationPrompt(Message.user("What is the typical food there?"))
            .messageHistory(result.getAllMessages());

    return client.chatCompletion(nextPrompt, config);
  }

  /**
   * Apply input filtering for a request to orchestration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     AI Core: Orchestration - Input Filtering</a>
   * @throws OrchestrationClientException if input filter filters the prompt
   * @param policy the explicitness of content that should be allowed through the filter
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse inputFiltering(@Nonnull final AzureFilterThreshold policy)
      throws OrchestrationClientException {
    val prompt =
        new OrchestrationPrompt("'We shall spill blood tonight', said the operation in-charge.");
    val filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);

    val configWithFilter = config.withInputFiltering(filterConfig);

    return client.chatCompletion(prompt, configWithFilter);
  }

  /**
   * Apply output filtering for a request to orchestration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param policy the explicitness of content that should be allowed through the filter
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse outputFiltering(@Nonnull final AzureFilterThreshold policy) {

    val systemMessage = Message.system("Give three paraphrases for the following sentence");
    // Reliably triggering the content filter of models fine-tuned for ethical compliance
    // is difficult. The prompt below may be rendered ineffective in the future.
    val prompt =
        new OrchestrationPrompt("'We shall spill blood tonight', said the operation in-charge.")
            .messageHistory(List.of(systemMessage));
    val filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);

    val configWithFilter = config.withOutputFiltering(filterConfig);
    return client.chatCompletion(prompt, configWithFilter);
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse maskingAnonymization(@Nonnull final DPIEntities entity) {
    val systemMessage =
        Message.system(
            "Please evaluate the following user feedback and judge if the sentiment is positive or negative.");
    val userMessage =
        Message.user(
            """
                            I think the SDK is good, but could use some further enhancements.
                            My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                            """);

    val prompt = new OrchestrationPrompt(systemMessage, userMessage);
    val maskingConfig = DpiMasking.anonymization().withEntities(entity);
    val configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
   *
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse completionWithResourceGroup(
      @Nonnull final String resourceGroup, @Nonnull final String famousPhrase) {
    val destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forScenario("orchestration");
    val clientWithResourceGroup = new OrchestrationClient(destination);

    val prompt = new OrchestrationPrompt(famousPhrase + " Why is this phrase so famous?");

    return clientWithResourceGroup.chatCompletion(prompt, config);
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse maskingPseudonymization(@Nonnull final DPIEntities entity) {
    val systemMessage =
        Message.system(
            """
                            Please write an initial response to the below user feedback, stating that we are working on the feedback and will get back to them soon.
                            Please make sure to address the user in person and end with "Best regards, the AI SDK team".
                            """);
    val userMessage =
        Message.user(
            """
                            Username: Mallory
                            userEmail: mallory@sap.com
                            Date: 2022-01-01

                            I think the SDK is good, but could use some further enhancements.
                            My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                            """);

    val prompt = new OrchestrationPrompt(systemMessage, userMessage);
    val maskingConfig = DpiMasking.pseudonymization().withEntities(entity, DPIEntities.EMAIL);
    val configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Using grounding to provide additional context to the AI model.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
   *     AI Core: Orchestration - Grounding</a>
   * @param userMessage the user message to provide grounding for
   * @return the assistant response object
   */
  @Nonnull
  public OrchestrationChatResponse grounding(@Nonnull final String userMessage) {
    // optional filter for collections
    val documentMetadata =
        SearchDocumentKeyValueListPair.create()
            .key("document metadata")
            .value("2")
            .selectMode(List.of(SearchSelectOptionEnum.IGNORE_IF_KEY_ABSENT));
    // optional filter for document chunks
    val databaseFilter =
        DocumentGroundingFilter.create()
            .id("")
            .dataRepositoryType(DataRepositoryType.VECTOR)
            .searchConfig(GroundingFilterSearchConfiguration.create().maxChunkCount(1))
            .documentMetadata(List.of(documentMetadata));

    val groundingConfig = Grounding.create().filter(databaseFilter);
    val prompt = groundingConfig.createGroundingPrompt(userMessage);
    val configWithGrounding = config.withGrounding(groundingConfig);

    return client.chatCompletion(prompt, configWithGrounding);
  }
}
