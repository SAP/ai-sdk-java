package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage.ImageDetailLevel;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiDeltaChatCompletion;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for OpenAI operations */
@Slf4j
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
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  // This is a code sample, works like #streamChatCompletion but with a readable code
  @SuppressWarnings("unused")
  @Nonnull
  public static ResponseEntity<ResponseBodyEmitter> streamChatCompletionExample() {
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(
                List.of(
                    new OpenAiChatUserMessage()
                        .addText(
                            "Can you give me the first 100 numbers of the Fibonacci sequence?")));

    final var emitter = new ResponseBodyEmitter();

    // Cloud SDK's ThreadContext is vital for the request to successfully execute.
    ThreadContextExecutors.getExecutor()
        .submit(
            () -> {
              // try-with-resources ensures that the stream is closed after the response is sent.
              try (var stream = OpenAiClient.forModel(GPT_35_TURBO).streamChatCompletion(request)) {

                final var totalOutput = new OpenAiChatCompletionOutput();
                stream
                    .peek(totalOutput::addDelta)
                    .forEach(delta -> send(emitter, delta.getDeltaContent()));

                final String indentedJson = objectToJson(totalOutput);
                send(emitter, "\n\n-----Total Output-----\n\n" + indentedJson);
                emitter.complete();
              }
            });
    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  private static void send(
      @Nonnull final ResponseBodyEmitter emitter, @Nonnull final String chunk) {
    try {
      emitter.send(chunk);
    } catch (final IOException e) {
      log.error(Arrays.toString(e.getStackTrace()));
      emitter.completeWithError(e);
    }
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @SuppressWarnings("unused") // #streamToConsumer is the method that is tested
  @GetMapping("/streamChatCompletion")
  @Nonnull
  public static ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(
                List.of(
                    new OpenAiChatUserMessage()
                        .addText(
                            "Can you give me the first 100 numbers of the Fibonacci sequence?")));

    final var emitter = new ResponseBodyEmitter();
    final var totalOutput = new OpenAiChatCompletionOutput();
    final var consumer =
        new Consumer<OpenAiDeltaChatCompletion>() {
          @Override
          public void accept(@Nonnull final OpenAiDeltaChatCompletion delta) {
            totalOutput.addDelta(delta);
            send(emitter, delta.getDeltaContent());
          }
        };

    // Cloud SDK's ThreadContext is vital for the request to successfully execute.
    ThreadContextExecutors.getExecutor()
        .submit(
            () -> {
              streamToConsumer(request, consumer);
              send(emitter, "\n\n-----Total Output-----\n\n" + objectToJson(totalOutput));
              emitter.complete();
            });
    // TEXT_EVENT_STREAM allows the browser to display the content as it is streamed
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
  }

  /**
   * Streams the OpenAI chat completion into the accept method of the consumer.
   *
   * @param request the chat completion request
   * @param consumer the consumer that asynchronously accepts the chat completion
   */
  static void streamToConsumer(
      @Nonnull final OpenAiChatCompletionParameters request,
      @Nonnull final Consumer<OpenAiDeltaChatCompletion> consumer) {
    // try-with-resources ensures that the stream is closed after the response is sent.
    try (var stream = OpenAiClient.forModel(GPT_35_TURBO).streamChatCompletion(request)) {
      stream.forEach(consumer);
    }
  }

  private static String objectToJson(@Nonnull final Object obj) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (final JsonProcessingException ignored) {
      return "Could not parse object to JSON";
    }
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
