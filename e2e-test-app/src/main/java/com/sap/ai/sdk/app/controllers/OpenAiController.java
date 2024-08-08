package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage.ImageDetailLevel;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for OpenAI operations */
@RestController
class OpenAiController {
  /**
   * Chat request to OpenAI
   *
   * @return the assistant message response
   */
  @GetMapping("/chatCompletion")
  @Nonnull
  public static OpenAiChatCompletionOutput chatCompletion() {
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(List.of(new OpenAiChatUserMessage().addText("Who is the prettiest")));

    return OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(request);
  }

  /**
   * Chat request to OpenAI with an image
   *
   * @return the assistant message response
   */
  @GetMapping("/chatCompletionImage")
  @Nonnull
  public static OpenAiChatCompletionOutput chatCompletionImage() {
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(
                List.of(
                    new OpenAiChatUserMessage()
                        .addText("Describe the following image.")
                        .addImage(
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png",
                            ImageDetailLevel.HIGH)));

    return OpenAiClient.forModel(GPT_4O).chatCompletion(request);
  }

  /**
   * Chat request to OpenAI with a tool.
   *
   * @return the assistant message response
   */
  @GetMapping("/chatCompletionTool")
  @Nonnull
  public static OpenAiChatCompletionOutput chatCompletionTools() {
    final var question =
        "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?";
    final var par = Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer")));
    final var function =
        new OpenAiChatCompletionFunction()
            .setName("fibonacci")
            .setDescription("Calculate the Fibonacci number for given sequence index.")
            .setParameters(par);
    final var tool = new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(function);
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(List.of(new OpenAiChatUserMessage().addText(question)))
            .setTools(List.of(tool))
            .setToolChoiceFunction("fibonacci");

    return OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(request);
  }

  /**
   * Get the embedding of a text
   *
   * @return the embedding response
   */
  @GetMapping("/embedding")
  @Nonnull
  public static OpenAiEmbeddingOutput embedding() {
    final var request = new OpenAiEmbeddingParameters().setInput("Hello World");

    return OpenAiClient.forModel(TEXT_EMBEDDING_ADA_002).embedding(request);
  }
}
