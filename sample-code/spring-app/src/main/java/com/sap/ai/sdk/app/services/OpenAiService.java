package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_3_SMALL;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiImageItem;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiTool;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for OpenAI service using latest convenience api */
@Service
@Slf4j
public class OpenAiService {
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
   * Chat requests to OpenAI and updating the messages history
   *
   * @param previousMessage The request to send to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionResponse messagesHistory(@Nonnull final String previousMessage) {
    val messagesList = new ArrayList<OpenAiMessage>();
    messagesList.add(OpenAiMessage.user(previousMessage));

    final OpenAiChatCompletionResponse result =
        OpenAiClient.forModel(GPT_4O_MINI)
            .chatCompletion(new OpenAiChatCompletionRequest(messagesList));

    messagesList.add(result.getMessage());
    messagesList.add(OpenAiMessage.user("What is the typical food there?"));

    return OpenAiClient.forModel(GPT_4O_MINI)
        .chatCompletion(new OpenAiChatCompletionRequest(messagesList));
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
    final var request = new OpenAiChatCompletionRequest(OpenAiMessage.user(message));

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
  public OpenAiChatCompletionResponse chatCompletionImage(@Nonnull final String linkToImage) {

    final var userMessage =
        OpenAiMessage.user("Describe the following image.")
            .withImage(linkToImage, OpenAiImageItem.DetailLevel.HIGH);

    return OpenAiClient.forModel(GPT_4O)
        .chatCompletion(new OpenAiChatCompletionRequest(userMessage));
  }

  /**
   * Chat request to OpenAI with tool that gets the weather for a given location and unit. The tool
   * executed and the result is sent back to the assistant.
   *
   * @param location The location to get the weather for.
   * @param unit The unit of temperature to use.
   * @return The assistant message response.
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletionToolExecution(
      @Nonnull final String location, @Nonnull final String unit) {
    final OpenAiClient client = OpenAiClient.forModel(GPT_4O_MINI);

    final var messages = new ArrayList<OpenAiMessage>();
    messages.add(OpenAiMessage.user("What's the weather in %s in %s?".formatted(location, unit)));

    // 1. Define the function
    final List<OpenAiTool> tools =
        List.of(
            OpenAiTool.forFunction(WeatherMethod::getCurrentWeather)
                .withArgument(WeatherMethod.Request.class)
                .withName("weather")
                .withDescription("Get the weather for the given location"));

    // 2. Assistant calls the function
    final var request = new OpenAiChatCompletionRequest(messages).withToolsExecutable(tools);
    final OpenAiChatCompletionResponse response = client.chatCompletion(request);

    // 3. Execute the tool calls
    messages.add(response.getMessage());
    messages.addAll(response.executeTools());

    // 4. Have model run the final request with incorporated tool results
    return client.chatCompletion(request.withMessages(messages));
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
