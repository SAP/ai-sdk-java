package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

/** Client for interacting with OpenAI models. Allows for convenient string prompts only. */
public interface OpenAiClientWithSystemPrompt {

  /**
   * Generate a completion for the given user prompt.
   *
   * @param prompt a text message.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  OpenAiChatCompletionOutput chatCompletion(@Nonnull final String prompt)
      throws OpenAiClientException;

  /**
   * Stream a completion for the given prompt. Returns a <b>lazily</b> populated stream of text
   * chunks. To access more details about the individual chunks, use {@link
   * OpenAiClient#streamChatCompletionDeltas(OpenAiChatCompletionParameters)}.
   *
   * <p>The stream should be consumed using a try-with-resources block to ensure that the underlying
   * HTTP connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var stream = client.streamChatCompletion("...")) {
   *       stream.forEach(System.out::println);
   * }
   * }</pre>
   *
   * <p>Please keep in mind that using a terminal stream operation like {@link Stream#forEach} will
   * block until all chunks are consumed. Also, for obvious reasons, invoking {@link
   * Stream#parallel()} on this stream is not supported.
   *
   * @param prompt a text message.
   * @return A stream of message deltas
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @see OpenAiClient#streamChatCompletionDeltas(OpenAiChatCompletionParameters)
   */
  @Nonnull
  Stream<String> streamChatCompletion(@Nonnull final String prompt) throws OpenAiClientException;
}
