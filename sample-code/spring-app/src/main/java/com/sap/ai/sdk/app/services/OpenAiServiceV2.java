package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_3_SMALL;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiFunctionCall;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiFunctionTool;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiImageItem;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiToolCall;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for OpenAI service using latest convenience api */
@Service
@Slf4j
public class OpenAiServiceV2 {
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
   * Executes a chat completion request to OpenAI with a tool that calculates the weather.
   *
   * @param location The location to get the weather for.
   * @param unit The unit of temperature to use.
   * @return The assistant message response.
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletionToolExecution(
      @Nonnull final String location, @Nonnull final String unit) {
    final var messages = new ArrayList<OpenAiMessage>();
    messages.add(OpenAiMessage.user("What's the weather in %s in %s?".formatted(location, unit)));

    // 1. Define the function
    final var weatherFunction =
        new OpenAiFunctionTool("weather", WeatherMethod.Request.class)
            .withDescription("Get the weather for the given location");

    // 2. Assistant calls the function
    final var request =
        new OpenAiChatCompletionRequest(messages).withOpenAiTools(List.of(weatherFunction));
    final OpenAiChatCompletionResponse response =
        OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request);
    final OpenAiAssistantMessage assistantMessage = response.getMessage();

    // 3. Execute the function
    final OpenAiToolCall toolCall = assistantMessage.toolCalls().get(0);
    if (!(toolCall instanceof OpenAiFunctionCall functionCall)) {
      throw new IllegalArgumentException(
          "Expected a function call, but got: %s".formatted(assistantMessage));
    }
    final WeatherMethod.Request arguments = functionCall.getArgumentsAsObject(weatherFunction);
    final WeatherMethod.Response currentWeather = WeatherMethod.getCurrentWeather(arguments);

    // 4. Send back the results, and the model will incorporate them into its final response.
    messages.add(assistantMessage);
    messages.add(OpenAiMessage.tool(currentWeather.toString(), functionCall.getId()));
    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request.withMessages(messages));
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
