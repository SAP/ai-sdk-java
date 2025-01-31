package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartImage.TypeEnum.IMAGE_URL;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartImageImageUrl.DetailEnum.HIGH;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessage.RoleEnum.USER;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionTool.TypeEnum.*;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionConfig;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionPrompt;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingsRequestFactory;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionNamedToolChoice;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionNamedToolChoiceFunction;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartImage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartImageImageUrl;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.model2.FunctionObject;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for OpenAI service */
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
  public OpenAiChatCompletionOutput chatCompletion(@Nonnull final String prompt) {
    return OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final String message) {
    final var request =
        new CreateChatCompletionRequest()
            .addMessagesItem(
                new ChatCompletionRequestUserMessage()
                    .role(USER)
                    .content(
                        ChatCompletionRequestUserMessageContent.create(
                            List.of(
                                new ChatCompletionRequestMessageContentPartText()
                                    .text(message)
                                    .type(TEXT)))))
            .tools(null)
            .functions(null)
            .parallelToolCalls(null);

    return OpenAiClient.forModel(GPT_35_TURBO).streamChatCompletionDeltas(request);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String message) {
    return OpenAiClient.forModel(GPT_35_TURBO)
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
    final var partText =
        new ChatCompletionRequestMessageContentPartText()
            .type(TEXT)
            .text("Describe the following image.");
    final var partImageUrl =
        new ChatCompletionRequestMessageContentPartImageImageUrl()
            .url(URI.create(linkToImage))
            .detail(HIGH);
    final var partImage =
        new ChatCompletionRequestMessageContentPartImage().type(IMAGE_URL).imageUrl(partImageUrl);
    final var userMessage =
        new ChatCompletionRequestUserMessage()
            .role(USER)
            .content(ChatCompletionRequestUserMessageContent.create(List.of(partText, partImage)));
    final var request =
        new CreateChatCompletionRequest()
            .addMessagesItem(userMessage)
            .functions(null)
            .tools(null)
            .parallelToolCalls(null);

    return OpenAiClient.forModel(GPT_4O).chatCompletion(request);
  }

  /**
   * Chat request to OpenAI with a tool.
   *
   * @param description of the function to be sent to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletionTools(@Nonnull final String description) {
    var function =
        new FunctionObject()
            .name("fibonacci")
            .description(description)
            .parameters(
                Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer"))));

    var tool = new ChatCompletionTool().type(FUNCTION).function(function);

    var toolChoice =
        ChatCompletionToolChoiceOption.create(
            new ChatCompletionNamedToolChoice()
                .type(ChatCompletionNamedToolChoice.TypeEnum.FUNCTION)
                .function(new ChatCompletionNamedToolChoiceFunction().name("fibonacci")));

    var config = new OpenAiChatCompletionConfig().tools(List.of(tool)).toolChoice(toolChoice);

    var prompt =
        OpenAiChatCompletionPrompt.create(
            "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?");

    return OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(prompt, config);
  }

  /**
   * Get the embedding of a text
   *
   * @param input The text to embed
   * @return the embedding response
   */
  @Nonnull
  public EmbeddingsCreate200Response embedding(@Nonnull final String input) {
    final var request = OpenAiEmbeddingsRequestFactory.fromStrings(input);

    return OpenAiClient.forModel(TEXT_EMBEDDING_ADA_002).embedding(request);
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
