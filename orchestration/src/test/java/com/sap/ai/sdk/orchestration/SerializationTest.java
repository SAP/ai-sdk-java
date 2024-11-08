package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.HIGH;
import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.LOW;
import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.MEDIUM;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.client.model.ModuleResults;
import com.sap.ai.sdk.orchestration.client.model.ModuleResultsOutputUnmaskingInner;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SerializationTest {

  @Test
  void testSerialization() throws IOException {
    var llm = new LlmConfig("gpt-35-turbo-16k", Map.of("temperature", 0.5, "frequency_penalty", 1));
    var template = TemplateConfig.fromMessages(new UserMessage("{{?input}}"));

    var inputFilter = new AzureContentFilter().selfHarm(LOW);
    var outputFilter = new AzureContentFilter().hate(HIGH).selfHarm(MEDIUM).sexual(LOW);

    var masking =
        DpiMaskingConfig.anonymization()
            .withEntities(DPIEntities.ADDRESS, DPIEntities.IBAN, DPIEntities.LOCATION);
    var inputParams = Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    var dto =
        new OrchestrationPrompt(inputParams)
            .withLlmConfig(llm)
            .withTemplate(template)
            .withInputContentFilter(inputFilter)
            .withOutputContentFilter(outputFilter)
            .withMaskingConfig(masking)
            .toCompletionPostRequestDto(DefaultOrchestrationConfig.standalone());

    var actual = OrchestrationClient.JACKSON.valueToTree(dto);

    var expected =
        OrchestrationClient.JACKSON.readValue(
            getClass().getClassLoader().getResource("serializedRequest.json"), JsonNode.class);

    assertThat(actual)
        .withRepresentation(it -> ((JsonNode) it).toPrettyString())
        .isEqualTo(expected);
  }

  @Test
  void testDeserialization() throws IOException {

    var llmResult =
        new LLMModuleResultSynchronous()
            // .xDiscriminatorValue("LLMModuleResultSynchronous")
            .id("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj")
            ._object("chat.completion")
            .created(1721224505)
            .model("gpt-35-turbo-16k")
            .choices(
                List.of(
                    new LLMChoice()
                        // .xDiscriminatorValue("LLMChoice")
                        .index(0)
                        .message(new ChatMessage().role("assistant").content("General Kenobi!"))
                        .finishReason("stop")))
            .usage(new TokenUsage().completionTokens(7).promptTokens(19).totalTokens(26));

    var orchestrationResult =
        new LLMModuleResultSynchronous()
            // .xDiscriminatorValue("LLMModuleResultSynchronous")
            .id("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj")
            ._object("chat.completion")
            .created(1721224505)
            .model("gpt-35-turbo-16k")
            .choices(
                List.of(
                    new LLMChoice()
                        // .xDiscriminatorValue("LLMChoice")
                        .index(0)
                        .message(new ChatMessage().role("assistant").content("General Kenobi!"))
                        .finishReason("stop")))
            .usage(new TokenUsage().completionTokens(7).promptTokens(19).totalTokens(26));

    var inputFilterResult =
        new GenericModuleResult()
            .message("Input filter passed successfully.")
            .data(
                Map.of(
                    "original_service_response",
                    Map.of("Hate", 0, "SelfHarm", 0, "Sexual", 0, "Violence", 2),
                    "checked_text",
                    "Hello there!"));
    var outputFilterResult =
        new GenericModuleResult()
            .message("Output filter passed successfully.")
            .data(
                Map.of(
                    "original_service_response",
                    Map.of("Hate", 0, "SelfHarm", 0, "Sexual", 0, "Violence", 2),
                    "checked_text",
                    "General Kenobi!"));
    var inputMaskingResult =
        new GenericModuleResult()
            .message("Input to LLM is masked successfully.")
            .data(
                Map.of(
                    "masked_template", List.of(Map.of("role", "user", "content", "Hello there!"))));
    List<ModuleResultsOutputUnmaskingInner> outputMaskingResult =
        List.of(
            new LLMChoice()
                // .xDiscriminatorValue("LLMChoice")
                .index(0)
                .message(new ChatMessage().role("assistant").content("Hello there!"))
                .finishReason("stop"));
    var expected =
        new CompletionPostResponse()
            .requestId("26ea36b5-c196-4806-a9a6-a686f0c6ad91")
            .moduleResults(
                new ModuleResults()
                    .templating(List.of(new ChatMessage().role("user").content("Hello there!")))
                    .llm(llmResult)
                    .inputFiltering(inputFilterResult)
                    .outputFiltering(outputFilterResult)
                    .inputMasking(inputMaskingResult)
                    .outputUnmasking(outputMaskingResult))
            .orchestrationResult(orchestrationResult);

    var actual =
        OrchestrationClient.JACKSON.readValue(
            getClass().getClassLoader().getResourceAsStream("serializedResponse.json"),
            CompletionPostResponse.class);

    assertThat(actual).isEqualTo(expected);
  }
}
