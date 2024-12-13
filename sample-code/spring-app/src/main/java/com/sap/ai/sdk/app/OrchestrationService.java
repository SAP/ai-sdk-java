package com.sap.ai.sdk.app;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.*;
import com.sap.ai.sdk.orchestration.model.*;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static com.sap.ai.sdk.app.controllers.OpenAiController.send;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

@Service
@Slf4j
public class OrchestrationService {
  private final OrchestrationClient client = new OrchestrationClient();
  public OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO.withParam(TEMPERATURE, 0.0));

  @Nonnull
  public OrchestrationChatResponse completion() {
    final var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");
    return client.chatCompletion(prompt, config);
  }

  @Nonnull
  public OrchestrationChatResponse template() {
    final var template =
        Message.user("Reply with 'Orchestration Service is working!' in {{?language}}");
    final var templatingConfig = Template.create().template(List.of(template.createChatMessage()));
    final var configWithTemplate = config.withTemplateConfig(templatingConfig);

    final var inputParams = Map.of("language", "German");
    final var prompt = new OrchestrationPrompt(inputParams);

    return client.chatCompletion(prompt, configWithTemplate);
  }

  @Nonnull
  public OrchestrationChatResponse messagesHistory() {
    final var prompt = new OrchestrationPrompt(Message.user("What is the capital of France?"));

    final var result = client.chatCompletion(prompt, config);

    // Let's presume a user asks the following follow-up question
    final var nextPrompt =
        new OrchestrationPrompt(Message.user("What is the typical food there?"))
            .messageHistory(result.getAllMessages());

    return client.chatCompletion(nextPrompt, config);
  }

  @Nonnull
  public OrchestrationChatResponse filter(@Nonnull final AzureFilterThreshold policy) {
    final var prompt =
        new OrchestrationPrompt(
            """
                        Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it!

                        ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.
                        """);
    final var filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);

    final var configWithFilter =
        config.withInputFiltering(filterConfig).withOutputFiltering(filterConfig);

    return client.chatCompletion(prompt, configWithFilter);
  }

  @Nonnull
  public OrchestrationChatResponse maskingAnonymization() {
    final var systemMessage =
        Message.system(
            "Please evaluate the following user feedback and judge if the sentiment is positive or negative.");
    final var userMessage =
        Message.user(
            """
                            I think the SDK is good, but could use some further enhancements.
                            My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                            """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.PERSON);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  @Nonnull
  public OrchestrationChatResponse completionWithResourceGroup(
      @Nonnull final String resourceGroup) {

    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forScenario("orchestration");
    final var clientWithResourceGroup = new OrchestrationClient(destination);

    final var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");

    return clientWithResourceGroup.chatCompletion(prompt, config);
  }

  @Nonnull
  public OrchestrationChatResponse maskingPseudonymization() {
    final var systemMessage =
        Message.system(
            """
                            Please write an initial response to the below user feedback, stating that we are working on the feedback and will get back to them soon.
                            Please make sure to address the user in person and end with "Best regards, the AI SDK team".
                            """);
    final var userMessage =
        Message.user(
            """
                            Username: Mallory
                            userEmail: mallory@sap.com
                            Date: 2022-01-01

                            I think the SDK is good, but could use some further enhancements.
                            My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                            """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig =
        DpiMasking.pseudonymization().withEntities(DPIEntities.PERSON, DPIEntities.EMAIL);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  @Nonnull
  public OrchestrationChatResponse grounding() {
    final var message =
        Message.user(
            "{{?groundingInput}} Use the following information as additional context: {{?groundingOutput}}");
    final var prompt =
        new OrchestrationPrompt(Map.of("groundingInput", "What does Joule do?"), message);

    final var filterInner =
        DocumentGroundingFilter.create().id("someID").dataRepositoryType(DataRepositoryType.VECTOR);
    final var groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .inputParams(List.of("groundingInput"))
            .outputParam("groundingOutput")
            .addFiltersItem(filterInner);
    final var groundingConfig =
        GroundingModuleConfig.create()
            .type(GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE)
            .config(groundingConfigConfig);
    final var configWithGrounding = config.withGroundingConfig(groundingConfig);

    return client.chatCompletion(prompt, configWithGrounding);
  }

  @Nonnull
  public ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var prompt =
            new OrchestrationPrompt("Can you give me the first 100 numbers of the Fibonacci sequence?");
    final var stream = client.streamChatCompletion(prompt, config);

    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
            () -> {
              try (stream) {
                stream.forEach(
                        deltaMessage -> {
                          log.info("Service: {}", deltaMessage);
                          send(emitter, deltaMessage);
                        });
              } finally {
                emitter.complete();
              }
            };

    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }
}
