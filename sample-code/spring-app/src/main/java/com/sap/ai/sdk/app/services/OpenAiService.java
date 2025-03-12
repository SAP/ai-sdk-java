package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_3_SMALL;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
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
    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(prompt);
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
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatMessage.OpenAiChatUserMessage().addText(message));

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
   * Chat request to OpenAI with a tool.
   *
   * @param months The number of months to be inferred in the tool
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletionTools(final int months) {
    final var question =
        "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after %s months?"
            .formatted(months);
    final var par = Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer")));
    final var function =
        new OpenAiChatCompletionFunction()
            .setName("fibonacci")
            .setDescription("Calculate the Fibonacci number for given sequence index.")
            .setParameters(par);
    final var tool = new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(function);
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatMessage.OpenAiChatUserMessage().addText(question))
            .setTools(List.of(tool))
            .setToolChoiceFunction("fibonacci");

    return OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request);
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
