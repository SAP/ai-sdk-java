package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

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
  public ResponseEntity<ResponseBodyEmitter> streamChatCompletionDeltas(
      @Nonnull final String message) {
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatMessage.OpenAiChatUserMessage().addText(message));

    final var stream = OpenAiClient.forModel(GPT_35_TURBO).streamChatCompletionDeltas(request);

    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
        () -> {
          final var totalOutput = new OpenAiChatCompletionOutput();
          // try-with-resources ensures the stream is closed
          try (stream) {
            stream
                .peek(totalOutput::addDelta)
                .forEach(delta -> send(emitter, delta.getDeltaContent()));
          } finally {
            send(emitter, "\n\n-----Total Output-----\n\n" + objectToJson(totalOutput));
            emitter.complete();
          }
        };

    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @Nonnull
  public ResponseEntity<ResponseBodyEmitter> streamChatCompletion(@Nonnull final String message) {
    final var stream =
        OpenAiClient.forModel(GPT_35_TURBO)
            .withSystemPrompt("Be a good, honest AI and answer the following question:")
            .streamChatCompletion(message);

    final var emitter = new ResponseBodyEmitter();

    final Runnable consumeStream =
        () -> {
          try (stream) {
            stream.forEach(deltaMessage -> send(emitter, deltaMessage));
          } finally {
            emitter.complete();
          }
        };

    ThreadContextExecutors.getExecutor().execute(consumeStream);

    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  private static String objectToJson(@Nonnull final Object obj) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (final JsonProcessingException ignored) {
      return "Could not parse object to JSON";
    }
  }

  /**
   * Send a chunk to the emitter
   *
   * @param emitter The emitter to send the chunk to
   * @param chunk The chunk to send
   */
  public static void send(@Nonnull final ResponseBodyEmitter emitter, @Nonnull final String chunk) {
    try {
      emitter.send(chunk);
    } catch (final IOException e) {
      log.error(Arrays.toString(e.getStackTrace()));
      emitter.completeWithError(e);
    }
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
   * @param prompt The prompt to send to the assistant
   * @return the assistant message response
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletionTools(@Nonnull final String prompt) {
    final var question =
        "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?";
    final var par = Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer")));
    final var function =
        new OpenAiChatCompletionFunction()
            .setName("fibonacci")
            .setDescription(prompt)
            .setParameters(par);
    final var tool = new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(function);
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatMessage.OpenAiChatUserMessage().addText(question))
            .setTools(List.of(tool))
            .setToolChoiceFunction("fibonacci");

    return OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(request);
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
