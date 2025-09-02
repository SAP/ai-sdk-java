package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClientException.FACTORY;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiUtils.getOpenAiObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

  private static final ObjectMapper JACKSON = getOpenAiObjectMapper();

  @Nullable private String systemPrompt = null;

  @Nonnull private final Destination destination;
  @Nonnull private final List<Header> customHeaders = new ArrayList<>();

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
   * @return a new OpenAI client.
   */
  @Nonnull
  public static OpenAiClient withCustomDestination(@Nonnull final Destination destination) {
    final OpenAiClient client = new OpenAiClient(destination);

    if (destination.get("URL.queries.api-version").isDefined()) {
      return client;
    }

    return client.withApiVersion(DEFAULT_API_VERSION);
  }

  /**
   * Add a system prompt before user prompts.
   *
   * <p>Note: The system prompt is ignored on chat completions invoked with
   * OpenAiChatCompletionPrompt.
   *
   * @param systemPrompt the system prompt
   * @return the client
   */
  @Nonnull
  public OpenAiClient withSystemPrompt(@Nonnull final String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  /**
   * Create a new OpenAI client with a custom header added to every call made with this client
   *
   * @param key the key of the custom header to add
   * @param value the value of the custom header to add
   * @return a new client.
   * @since 1.11.0
   */
  @Beta
  @Nonnull
  public OpenAiClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new OpenAiClient(this.destination);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }

  /**
   * Generate a completion for the given string prompt as user.
   *
   * @param prompt a text message.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   * @deprecated Use {@link #chatCompletion(OpenAiChatCompletionRequest)} instead.
   */
  @Nonnull
  @Deprecated
  public OpenAiChatCompletionOutput chatCompletion(@Nonnull final String prompt)
      throws OpenAiClientException {
    final OpenAiChatCompletionParameters parameters = new OpenAiChatCompletionParameters();
    if (systemPrompt != null) {
      parameters.addMessages(new OpenAiChatSystemMessage().setContent(systemPrompt));
    }
    parameters.addMessages(new OpenAiChatUserMessage().addText(prompt));
    return chatCompletion(parameters);
  }

  /**
   * Generate a completion for the given conversation and request parameters.
   *
   * @param request the completion request.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   * @since 1.4.0
   */
  @Nonnull
  public OpenAiChatCompletionResponse chatCompletion(
      @Nonnull final OpenAiChatCompletionRequest request) throws OpenAiClientException {
    warnIfUnsupportedUsage();
    final var response = chatCompletion(request.createCreateChatCompletionRequest());
    return new OpenAiChatCompletionResponse(response, request);
  }

  /**
   * Generate a completion for the given low-level request object.
   *
   * @param request the completion request.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   * @since 1.4.0
   */
  @Nonnull
  public CreateChatCompletionResponse chatCompletion(
      @Nonnull final CreateChatCompletionRequest request) throws OpenAiClientException {
    return execute("/chat/completions", request, CreateChatCompletionResponse.class);
  }

  /**
   * Generate a completion for the given conversation and request parameters.
   *
   * @param parameters the completion request.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   * @deprecated Use {@link #chatCompletion(OpenAiChatCompletionRequest)} instead.
   */
  @Nonnull
  @Deprecated
  public OpenAiChatCompletionOutput chatCompletion(
      @Nonnull final OpenAiChatCompletionParameters parameters) throws OpenAiClientException {
    warnIfUnsupportedUsage();
    return execute("/chat/completions", parameters, OpenAiChatCompletionOutput.class);
  }

  /**
   * Stream a completion for the given string prompt as user.
   *
   * <p>Returns a <b>lazily</b> populated stream of text chunks. To access more details about the
   * individual chunks, use {@link #streamChatCompletionDeltas(OpenAiChatCompletionRequest)}.
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
   * @return A stream of text chunks
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @see #streamChatCompletionDeltas(OpenAiChatCompletionRequest)
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String prompt)
      throws OpenAiClientException {
    final var userPrompt = OpenAiMessage.user(prompt);

    final var request =
        systemPrompt != null
            ? new OpenAiChatCompletionRequest(OpenAiMessage.system(systemPrompt), userPrompt)
            : new OpenAiChatCompletionRequest(userPrompt);

    return streamChatCompletionDeltas(request.createCreateChatCompletionRequest())
        .peek(OpenAiClient::throwOnContentFilter)
        .map(OpenAiChatCompletionDelta::getDeltaContent);
  }

  private static void throwOnContentFilter(@Nonnull final OpenAiChatCompletionDelta delta) {
    final String finishReason = delta.getFinishReason();
    if (finishReason != null && finishReason.equals("content_filter")) {
      throw new OpenAiClientException("Content filter filtered the output.");
    }
  }

  /**
   * Stream a completion for the given conversation and request parameters.
   *
   * <p>Returns a <b>lazily</b> populated stream of delta objects. To simply stream the text chunks
   * use {@link #streamChatCompletion(String)}
   *
   * <p>The stream should be consumed using a try-with-resources block to ensure that the underlying
   * HTTP connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var stream = client.streamChatCompletionDeltas(prompt)) {
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
   * @param request The prompt, including a list of messages.
   * @return A stream of message deltas
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @see #streamChatCompletion(String)
   * @since 1.4.0
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final OpenAiChatCompletionRequest request) throws OpenAiClientException {
    return streamChatCompletionDeltas(request.createCreateChatCompletionRequest());
  }

  /**
   * Stream a completion for the given low-level request object. Returns a <b>lazily</b> populated
   * stream of delta objects.
   *
   * @param request The completion request.
   * @return A stream of message deltas
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @see #streamChatCompletionDeltas(OpenAiChatCompletionRequest) for a higher-level API
   * @since 1.4.0
   */
  @Nonnull
  public Stream<OpenAiChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final CreateChatCompletionRequest request) throws OpenAiClientException {
    request.stream(true).streamOptions(new ChatCompletionStreamOptions().includeUsage(true));
    return executeStream("/chat/completions", request, OpenAiChatCompletionDelta.class);
  }

  /**
   * Stream a completion for the given conversation and request parameters.
   *
   * <p>Returns a <b>lazily</b> populated stream of delta objects. To simply stream the text chunks
   * use {@link #streamChatCompletion(String)}
   *
   * <p>The stream should be consumed using a try-with-resources block to ensure that the underlying
   * HTTP connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var stream = client.streamChatCompletionDeltas(request)) {
   *       stream
   *           .peek(delta -> System.out.println(delta.getUsage()))
   *           .map(com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta::getDeltaContent)
   *           .forEach(System.out::println);
   * }
   * }</pre>
   *
   * <p>Please keep in mind that using a terminal stream operation like {@link Stream#forEach} will
   * block until all chunks are consumed. Also, for obvious reasons, invoking {@link
   * Stream#parallel()} on this stream is not supported.
   *
   * @param parameters The prompt, including a list of messages.
   * @return A stream of message deltas
   * @throws OpenAiClientException if the request fails or if the finish reason is content_filter
   * @deprecated Use {@link #streamChatCompletionDeltas(OpenAiChatCompletionRequest)} instead.
   */
  @Nonnull
  @Deprecated
  public Stream<com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta>
      streamChatCompletionDeltas(@Nonnull final OpenAiChatCompletionParameters parameters)
          throws OpenAiClientException {
    warnIfUnsupportedUsage();
    parameters.enableStreaming();
    return executeStream(
        "/chat/completions",
        parameters,
        com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta.class);
  }

  private void warnIfUnsupportedUsage() {
    if (systemPrompt != null) {
      log.warn(
          "Previously set messages will be ignored, set it as an argument of this method instead.");
    }
  }

  /**
   * Get a vector representation of a given request that can be easily consumed by machine learning
   * models and algorithms using high-level request object.
   *
   * @param request the request with input text.
   * @return the embedding response convenience object
   * @throws OpenAiClientException if the request fails
   * @see #embedding(EmbeddingsCreateRequest) for full confgurability.
   * @since 1.4.0
   */
  @Nonnull
  public OpenAiEmbeddingResponse embedding(@Nonnull final OpenAiEmbeddingRequest request)
      throws OpenAiClientException {
    return new OpenAiEmbeddingResponse(embedding(request.createEmbeddingsCreateRequest()));
  }

  /**
   * Get a vector representation of a given inputs using low-level request.
   *
   * @param request the request with input text.
   * @return the embedding output
   * @throws OpenAiClientException if the request fails
   * @see #embedding(OpenAiEmbeddingRequest) for conveninece api
   * @since 1.4.0
   */
  @Nonnull
  public EmbeddingsCreate200Response embedding(@Nonnull final EmbeddingsCreateRequest request)
      throws OpenAiClientException {
    return execute("/embeddings", request, EmbeddingsCreate200Response.class);
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
  @Deprecated
  public OpenAiEmbeddingOutput embedding(@Nonnull final OpenAiEmbeddingParameters parameters)
      throws OpenAiClientException {
    return execute("/embeddings", parameters, OpenAiEmbeddingOutput.class);
  }

  @Nonnull
  private <T> T execute(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<T> responseType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload, this.customHeaders);
    return executeRequest(request, responseType);
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> executeStream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<D> deltaType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload, this.customHeaders);
    return streamRequest(request, deltaType);
  }

  private static void serializeAndSetHttpEntity(
      @Nonnull final BasicClassicHttpRequest request,
      @Nonnull final Object payload,
      @Nonnull final List<Header> customHeaders) {
    try {
      final var json = JACKSON.writeValueAsString(payload);
      request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
      customHeaders.forEach(h -> request.addHeader(h.getName(), h.getValue()));
    } catch (final JsonProcessingException e) {
      throw new OpenAiClientException("Failed to serialize request parameters", e)
          .setHttpRequest(request);
    }
  }

  @Nonnull
  private <T> T executeRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<T> responseType) {
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(
          request, new ClientResponseHandler<>(responseType, OpenAiError.class, FACTORY));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed", e).setHttpRequest(request);
    }
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> streamRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<D> deltaType) {
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return new ClientStreamingHandler<>(deltaType, OpenAiError.class, FACTORY)
          .objectMapper(JACKSON)
          .handleStreamingResponse(client.executeOpen(null, request, null));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed", e).setHttpRequest(request);
    }
  }
}
