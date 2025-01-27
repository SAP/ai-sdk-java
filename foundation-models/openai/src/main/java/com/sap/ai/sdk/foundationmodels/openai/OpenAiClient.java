package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequest;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;

/** Client for interacting with OpenAI models. */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenAiClient {
  private static final String DEFAULT_API_VERSION = "2024-02-01";
  static final ObjectMapper JACKSON = getDefaultObjectMapper();
  @Nullable private OpenAiMessage systemPrompt = null;

  @Nonnull private final Destination destination;

  static {
    JACKSON.addMixIn(
        ChatCompletionsCreate200Response.class,
        JacksonMixins.DefaultChatCompletionCreate200ResponseMixIn.class);
  }

  /**
   * Create a new OpenAI client for the given foundation model, using the default resource group.
   *
   * @param foundationModel the OpenAI model which is deployed.
   * @return a new OpenAI client.
   * @throws DeploymentResolutionException if no deployment for the given model was found in the
   *     default resource group.
   */
  @Nonnull
  public static OpenAiClient forModel(@Nonnull final OpenAiModel foundationModel)
      throws DeploymentResolutionException {
    final var destination = new AiCoreService().getInferenceDestination().forModel(foundationModel);

    final var client = new OpenAiClient(destination);
    return client.withApiVersion(DEFAULT_API_VERSION);
  }

  /**
   * Create a new OpenAI client targeting the specified API version.
   *
   * @param apiVersion the API version to target.
   * @return a new client.
   */
  @Beta
  @Nonnull
  public OpenAiClient withApiVersion(@Nonnull final String apiVersion) {
    final var newDestination =
        DefaultHttpDestination.fromDestination(this.destination)
            // set the API version as URL query parameter
            .property("URL.queries.api-version", apiVersion)
            .build();
    return new OpenAiClient(newDestination);
  }

  /**
   * Create a new OpenAI client with a custom destination, allowing for a custom resource group or
   * otherwise custom destination. The destination needs to be configured with a URL pointing to an
   * OpenAI model deployment. Typically, such a destination should be obtained using {@link
   * AiCoreService#getInferenceDestination(String)}.
   *
   * <p>Example:
   *
   * <pre>{@code
   * var destination = new AiCoreService().getInferenceDestination("custom-rg").forModel(GPT_4O);
   * OpenAiClient.withCustomDestination(destination);
   * }</pre>
   *
   * @param destination The specific {@link HttpDestination} to use.
   * @see AiCoreService#getInferenceDestination(String)
   */
  @Beta
  @Nonnull
  public static OpenAiClient withCustomDestination(@Nonnull final Destination destination) {
    final OpenAiClient client = new OpenAiClient(destination);

    if (destination.get("URL.queries.api-version").isDefined()) {
      return client;
    }

    return client.withApiVersion(DEFAULT_API_VERSION);
  }

  @Nonnull
  public static CreateChatCompletionRequest toCreateChatCompletionRequest(
      @Nonnull final OpenAiChatCompletionRequest config) {
    var payload = new CreateChatCompletionRequest();

    config.getMessages().stream().map(OpenAiMessage::createDTO).forEach(payload::addMessagesItem);
    // TODO: what if tools, functions, parallelToolCalls are not null?
    payload.functions(null).tools(null).parallelToolCalls(null);

    return payload;
  }

  /**
   * Add a system prompt before user prompts.
   *
   * @param systemPrompt the system prompt
   * @return the client
   */
  @Nonnull
  public OpenAiClient withSystemPrompt(@Nonnull final String systemPrompt) {
    this.systemPrompt = OpenAiMessage.system(systemPrompt);
    return this;
  }

  /**
   * Generate a completion for the given user prompt.
   *
   * @param prompt a text message.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletion(@Nonnull final String prompt)
      throws OpenAiClientException {
    var userPrompt = OpenAiMessage.user(prompt);

    OpenAiChatCompletionRequest parameters =
        (systemPrompt != null)
            ? new OpenAiChatCompletionRequest(systemPrompt, userPrompt)
            : new OpenAiChatCompletionRequest(userPrompt);

    return chatCompletion(toCreateChatCompletionRequest(parameters));
  }

  @Nonnull
  public OpenAiChatCompletionResponse chatCompletion(
      @Nonnull final OpenAiChatCompletionRequest parameters) throws OpenAiClientException {
    warnIfUnsupportedUsage();
    return chatCompletion(toCreateChatCompletionRequest(parameters));
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param parameters the prompt, including messages and other parameters.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletion(
      @Nonnull final CreateChatCompletionRequest parameters) throws OpenAiClientException {
    return new OpenAiChatCompletionResponse(
        execute("/chat/completions", parameters, CreateChatCompletionResponse.class));
  }

  /**
   * Stream a completion for the given prompt. Returns a <b>lazily</b> populated stream of text
   * chunks. To access more details about the individual chunks, use {@link
   * #streamChatCompletionDeltas(CreateChatCompletionRequest)}.
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
   * @see #streamChatCompletionDeltas(CreateChatCompletionRequest)
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String prompt)
      throws OpenAiClientException {
    var parameters = new OpenAiChatCompletionRequest(prompt);

    return streamChatCompletionDeltas(toCreateChatCompletionRequest(parameters))
        .map(OpenAiChatCompletionDelta.class::cast)
        .peek(OpenAiClient::throwOnContentFilter)
        .map(OpenAiChatCompletionDelta::getDeltaContent);
  }

  private static void throwOnContentFilter(
      @Nonnull final com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta delta) {
    final String finishReason = delta.getFinishReason();
    if (finishReason != null && finishReason.equals("content_filter")) {
      throw new OpenAiClientException("Content filter filtered the output.");
    }
  }
  
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final OpenAiChatCompletionRequest parameters) throws OpenAiClientException {
    warnIfUnsupportedUsage();
    return streamChatCompletionDeltas(toCreateChatCompletionRequest(parameters));
  }
  
  /**
   * Stream a completion for the given prompt. Returns a <b>lazily</b> populated stream of delta
   * objects. To simply stream the text chunks use {@link #streamChatCompletion(String)}
   *
   * <p>The stream should be consumed using a try-with-resources block to ensure that the underlying
   * HTTP connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var stream = client.streamChatCompletionDeltas(params)) {
   *       stream
   *           .peek(delta -> System.out.println(delta.getUsage()))
   *           .map(OpenAiChatCompletionDelta::getDeltaContent)
   *           .forEach(System.out::println);
   * }
   * }</pre>
   *
   * <p>Please keep in mind that using a terminal stream operation like {@link Stream#forEach} will
   * block until all chunks are consumed. Also, for obvious reasons, invoking {@link
   * Stream#parallel()} on this stream is not supported.
   *
   * @param parameters The prompt, including messages and other parameters.
   * @return A stream of message deltas
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @see #streamChatCompletion(String)
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final CreateChatCompletionRequest parameters) throws OpenAiClientException {
    parameters.stream(true).streamOptions(new ChatCompletionStreamOptions().includeUsage(true));
    return executeStream("/chat/completions", parameters, OpenAiChatCompletionDelta.class);
  }

  private void warnIfUnsupportedUsage() {
    if (systemPrompt != null) {
      log.warn(
          "Previously set messages will be ignored, set it as an argument of this method instead.");
    }
  }

  /**
   * Get a vector representation of a given input that can be easily consumed by machine learning
   * models and algorithms.
   *
   * @param parameters the input text.
   * @return the embedding output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  public EmbeddingsCreate200Response embedding(@Nonnull final EmbeddingsCreateRequest parameters)
      throws OpenAiClientException {
    return execute("/embeddings", parameters, EmbeddingsCreate200Response.class);
  }

  @Nonnull
  private <T> T execute(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<T> responseType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload);
    return executeRequest(request, responseType);
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> executeStream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<D> deltaType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload);
    return streamRequest(request, deltaType);
  }

  private static void serializeAndSetHttpEntity(
      @Nonnull final BasicClassicHttpRequest request, @Nonnull final Object payload) {
    try {
      final var json = JACKSON.writeValueAsString(payload);
      request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    } catch (final JsonProcessingException e) {
      throw new OpenAiClientException("Failed to serialize request parameters", e);
    }
  }

  @Nonnull
  private <T> T executeRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<T> responseType) {
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(
          request,
          new ClientResponseHandler<>(responseType, OpenAiError.class, OpenAiClientException::new));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed", e);
    }
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> streamRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<D> deltaType) {
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return new ClientStreamingHandler<>(deltaType, OpenAiError.class, OpenAiClientException::new)
          .objectMapper(JACKSON)
          .handleStreamingResponse(client.executeOpen(null, request, null));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed", e);
    }
  }
}
