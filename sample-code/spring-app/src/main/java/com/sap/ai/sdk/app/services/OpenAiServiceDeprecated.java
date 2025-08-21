package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_3_SMALL;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatToolCall;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for OpenAI service */
@Service
@Slf4j
@Deprecated
public class OpenAiServiceDeprecated {
  private static final ObjectMapper JACKSON = new ObjectMapper();

  /**
   * Chat request to OpenAI
   *
   * @param prompt The prompt to send to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletion(@Nonnull final String prompt) {
    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @param message The message to send to the assistant
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final String message) {
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatMessage.OpenAiChatUserMessage().addText(message));

    return OpenAiClient.forModel(GPT_4O_MINI).streamChatCompletionDeltas(request);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @param message The message to send to the assistant
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
  public OpenAiChatCompletionOutput chatCompletionImage(@Nonnull final String linkToImage) {
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(
                new OpenAiChatMessage.OpenAiChatUserMessage()
                    .addText("Describe the following image.")
                    .addImage(
                        linkToImage,
                        OpenAiChatMessage.OpenAiChatUserMessage.ImageDetailLevel.HIGH));

    return OpenAiClient.forModel(GPT_4O).chatCompletion(request);
  }

  /**
   * Executes a chat completion request to OpenAI with a tool that calculates the weather.
   *
   * @param location The location to get the weather for.
   * @param unit The unit of temperature to use.
   * @return The assistant message response.
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletionToolExecution(
      @Nonnull final String location, @Nonnull final String unit) {

    // 1. Define the function
    final Map<String, Object> schemaMap = generateSchema(WeatherMethod.Request.class);
    final var function =
        new OpenAiChatCompletionFunction()
            .setName("weather")
            .setDescription("Get the weather for the given location")
            .setParameters(schemaMap);
    final var tool = new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(function);

    final var messages = new ArrayList<OpenAiChatMessage>();
    messages.add(
        new OpenAiChatMessage.OpenAiChatUserMessage()
            .addText("What's the weather in %s in %s?".formatted(location, unit)));

    // Assistant will call the function
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(messages.toArray(OpenAiChatMessage[]::new))
            .setTools(List.of(tool));

    final OpenAiChatCompletionOutput response =
        OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request);

    // 2. Optionally, execute the function.
    final OpenAiChatToolCall toolCall =
        response.getChoices().get(0).getMessage().getToolCalls().get(0);
    final WeatherMethod.Request arguments =
        parseJson(toolCall.getFunction().getArguments(), WeatherMethod.Request.class);
    final WeatherMethod.Response currentWeather = WeatherMethod.getCurrentWeather(arguments);

    final OpenAiChatMessage.OpenAiChatAssistantMessage assistantMessage =
        response.getChoices().get(0).getMessage();
    messages.add(assistantMessage);

    final var toolMessage =
        new OpenAiChatMessage.OpenAiChatToolMessage()
            .setToolCallId(toolCall.getId())
            .setContent(currentWeather.toString());
    messages.add(toolMessage);

    final var finalRequest =
        new OpenAiChatCompletionParameters()
            .addMessages(messages.toArray(OpenAiChatMessage[]::new));

    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(finalRequest);
  }

  private static <T> T parseJson(@Nonnull final String rawJson, @Nonnull final Class<T> clazz) {
    try {
      return JACKSON.readValue(rawJson, clazz);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to parse tool call arguments: " + rawJson, e);
    }
  }

  private static Map<String, Object> generateSchema(@Nonnull final Class<?> clazz) {
    final var jsonSchemaGenerator = new JsonSchemaGenerator(JACKSON);
    try {
      final var schema = jsonSchemaGenerator.generateSchema(clazz);
      return JACKSON.convertValue(schema, new TypeReference<>() {});
    } catch (JsonMappingException e) {
      throw new IllegalArgumentException("Could not generate schema for " + clazz.getName(), e);
    }
  }

  /**
   * Get the embedding of a text
   *
   * @param input The text to embed
   * @return the embedding response
   */
  @Nonnull
  public OpenAiEmbeddingOutput embedding(@Nonnull final String input) {
    final var request = new OpenAiEmbeddingParameters().setInput(input);

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
  public OpenAiChatCompletionOutput chatCompletionWithResource(
      @Nonnull final String resourceGroup, @Nonnull final String prompt) {

    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(GPT_4O);

    return OpenAiClient.withCustomDestination(destination).chatCompletion(prompt);
  }
}
