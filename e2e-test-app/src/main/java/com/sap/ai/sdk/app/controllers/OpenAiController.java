package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_4O;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionStream;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage.ImageDetailLevel;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
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
   * Stream chat request to OpenAI
   *
   * @return the emitter that streams the assistant message response
   */
  @GetMapping("/stream")
  @Nonnull
  public static ResponseEntity<ResponseBodyEmitter> stream() {
    final var request =
        new OpenAiChatCompletionParameters()
            .setMessages(
                List.of(
                    new OpenAiChatUserMessage()
                        .addText(
                            "Can you give me the first 100 number of the Fibonacci sequence?")));

    ResponseBodyEmitter emitter = new ResponseBodyEmitter();
    // Start streaming the content asynchronously
    ThreadContextExecutors.getExecutor()
        .submit(
            () -> {
              try (var stream = OpenAiClient.forModel(GPT_35_TURBO).stream(request)) {
                stream
                    .getDeltaStream()
                    .forEach(
                        delta -> {
                          try {
                            // TODO: Change the types to nullable? Maybe create a class
                            //   OpenAiChatCompletionDelta...
                            if (!delta.getChoices().isEmpty()
                                && delta.getChoices().get(0).getMessage() != null
                                && delta.getChoices().get(0).getMessage().getContent() != null) {
                              emitter.send(delta.getChoices().get(0).getMessage().getContent());
                            }
                          } catch (IOException e) {
                            log.error(Arrays.toString(e.getStackTrace()));
                            emitter.completeWithError(e);
                          }
                        });
                // Once all the data is sent, complete the emitter
                emitter.complete();
              } catch (Exception e) {
                emitter.completeWithError(e);
              }
            });
    return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
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
