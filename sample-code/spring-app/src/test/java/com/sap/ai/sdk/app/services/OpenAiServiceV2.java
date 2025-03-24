package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_3_SMALL;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum.FUNCTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiImageItem;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiToolChoice;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import io.vavr.control.Try;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for OpenAI service using latest convenience api */
@Service
@Slf4j
public class OpenAiServiceV2 {
  private static final ObjectMapper JACKSON = new ObjectMapper();

  /**
   * Chat request to OpenAI
   *
   * @param prompt The prompt to send to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletion(@Nonnull final String prompt) {
    return OpenAiClient.forModel(GPT_4O_MINI)
        .chatCompletion(new OpenAiChatCompletionRequest(prompt));
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final String message) {
    final var request = new OpenAiChatCompletionRequest(OpenAiMessage.user(message));

    return OpenAiClient.forModel(GPT_4O_MINI).streamChatCompletionDeltas(request);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String message) {
    return OpenAiClient.forModel(GPT_4O_MINI)
        .withSystemPrompt("Be a good, honest AI and answer the following question:")
        .streamChatCompletion(message);
  }

  /**
   * Chat request to OpenAI with an image
   *
   * @param linkToImage The link to the image
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletionImage(@Nonnull final String linkToImage) {

    final var userMessage =
        OpenAiMessage.user("Describe the following image.")
            .withImage(linkToImage, OpenAiImageItem.DetailLevel.HIGH);

    return OpenAiClient.forModel(GPT_4O)
        .chatCompletion(new OpenAiChatCompletionRequest(userMessage));
  }

  /**
   * Chat request to OpenAI with a tool.
   *
   * @param months The number of months to be inferred in the tool
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletionTools(final int months) {
    final var function =
        new FunctionObject()
            .name("fibonacci")
            .description("Calculate the Fibonacci number for given sequence index.")
            .parameters(
                Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer"))));

    final var tool = new ChatCompletionTool().type(FUNCTION).function(function);

    final var request =
        new OpenAiChatCompletionRequest(
                "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after %s months?"
                    .formatted(months))
            .withTools(List.of(tool))
            .withToolChoice(OpenAiToolChoice.function("fibonacci"));

    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request);
  }

  /**
   * Executes a chat completion request to OpenAI with a tool that calculates the weather.
   *
   * @param location The location to get the weather for.
   * @param unit The unit of temperature to use.
   * @return The assistant message response.
   */
  @Nonnull
  public CreateChatCompletionResponse chatCompletionToolExecution(
      @Nonnull final String location, @Nonnull final String unit) {

    final var schemaMap =
        Try.of(() -> new JsonSchemaGenerator(JACKSON).generateSchema(WeatherMethod.Request.class))
            .map(schema -> JACKSON.convertValue(schema, Map.class))
            .getOrElseThrow(
                e ->
                    new IllegalArgumentException(
                        "Could not generate schema for WeatherMethod.Request", e));

    final var function =
        new FunctionObject()
            .name("weather")
            .description("Get the weather for the given location")
            .parameters(schemaMap);

    final var tool = new ChatCompletionTool().type(FUNCTION).function(function);

    var userMessage =
        new ChatCompletionRequestUserMessage()
            .role(ChatCompletionRequestUserMessage.RoleEnum.USER)
            .content(
                ChatCompletionRequestUserMessageContent.create(
                    "What's the weather in %s in %s?".formatted(location, unit)));

    final var request =
        new CreateChatCompletionRequest()
            .addMessagesItem(userMessage)
            .tools(List.of(tool))
            .functions(null);

    OpenAiClient client = OpenAiClient.forModel(GPT_4O_MINI);

    final var initialResponse = client.chatCompletion(request);

    final var toolCall = initialResponse.getChoices().get(0).getMessage().getToolCalls().get(0);
    String toolResponseJson;
    try {
      var weatherRequest =
          JACKSON.readValue(toolCall.getFunction().getArguments(), WeatherMethod.Request.class);
      final var toolResponse = new WeatherMethod().getCurrentWeather(weatherRequest);
      toolResponseJson = JACKSON.writeValueAsString(toolResponse);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error parsing tool call arguments", e);
    }

    final var assistantMessage =
        new ChatCompletionRequestAssistantMessage()
            .role(ChatCompletionRequestAssistantMessage.RoleEnum.ASSISTANT)
            .content(
                ChatCompletionRequestAssistantMessageContent.create(
                    initialResponse.getChoices().get(0).getMessage().getContent()))
            .toolCalls(List.of(toolCall));
    request.addMessagesItem(assistantMessage);

    final var toolMessage =
        new ChatCompletionRequestToolMessage()
            .role(ChatCompletionRequestToolMessage.RoleEnum.TOOL)
            .content(ChatCompletionRequestToolMessageContent.create(toolResponseJson))
            .toolCallId(toolCall.getId());
    request.addMessagesItem(toolMessage);

    return client.chatCompletion(request);
  }

  /**
   * Get the embedding of a text
   *
   * @param input The text to embed
   * @return the embedding response
   */
  @Nonnull
  public OpenAiEmbeddingResponse embedding(@Nonnull final String input) {
    final var request = new OpenAiEmbeddingRequest(List.of(input));

    return OpenAiClient.forModel(TEXT_EMBEDDING_3_SMALL).embedding(request);
  }

  /**
   * Chat request to OpenAI filtering by resource group
   *
   * @param resourceGroup The resource group to use
   * @param prompt The prompt to send to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletionWithResource(
      @Nonnull final String resourceGroup, @Nonnull final String prompt) {

    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(GPT_4O);

    return OpenAiClient.withCustomDestination(destination)
        .chatCompletion(new OpenAiChatCompletionRequest(prompt));
  }
}
