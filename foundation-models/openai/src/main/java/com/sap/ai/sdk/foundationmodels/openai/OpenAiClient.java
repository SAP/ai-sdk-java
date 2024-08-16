package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.Core;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionStream;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import java.io.IOException;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/** Client for interacting with OpenAI models. */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenAiClient {
  private static final String DEFAULT_API_VERSION = "2024-02-01";
  static final ObjectMapper JACKSON;

  static {
    JACKSON =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();
  }

  @Nonnull private final Destination destination;

  /**
   * Create a new OpenAI client for the given foundation model.
   *
   * @param foundationModel the OpenAI model which is deployed.
   * @return a new OpenAI client.
   */
  @Nonnull
  public static OpenAiClient forModel(@Nonnull final OpenAiModel foundationModel) {
    final var destination = Core.getDestinationForModel(foundationModel.model(), "default");
    final var client = new OpenAiClient(destination);
    return client.withApiVersion(DEFAULT_API_VERSION);
  }

  // package private, but prepared in case we want to expose it later
  @Nonnull
  OpenAiClient withApiVersion(@Nonnull final String apiVersion) {
    final var destination =
        DefaultHttpDestination.fromDestination(this.destination)
            // set the API version as URL query parameter
            .property("URL.queries.api-version", apiVersion)
            .build();
    return new OpenAiClient(destination);
  }

  /**
   * Create a new OpenAI client with a <b>custom destination</b>. This can be used to customize the
   * connectivity behaviour of the client. For most use-cases, {@link
   * OpenAiClient#forModel(OpenAiModel)} should be used instead.
   *
   * <p>The destination needs to be configured with all relevant connectivity details relevant for
   * accessing the OpenAI models. For Azure-hosted models on AI Core this includes:
   *
   * <ul>
   *   <li>URL up and including the deployment id
   *   <li>Authorization
   *   <li>Resource Group Header
   *   <li>API Version
   * </ul>
   *
   * @param destination the destination to use for the client
   * @return a new OpenAI client
   * @see OpenAiClient#forModel(OpenAiModel)
   */
  @Nonnull
  public static OpenAiClient withCustomDestination(@Nonnull final Destination destination) {
    return new OpenAiClient(destination);
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param parameters the prompt, including messages and other parameters.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  public OpenAiChatCompletionOutput chatCompletion(
      @Nonnull final OpenAiChatCompletionParameters parameters) throws OpenAiClientException {
    return execute("/chat/completions", parameters, OpenAiChatCompletionOutput.class);
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param parameters the prompt, including messages and other parameters.
   * @return the completion output
   * @throws OpenAiClientException if the request fails
   */
  @Nonnull
  public OpenAiChatCompletionStream stream(@Nonnull final OpenAiChatCompletionParameters parameters)
      throws OpenAiClientException {
    parameters.setStream(true);
    return stream("/chat/completions", parameters, OpenAiChatCompletionStream.class);
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
    serializeAndSetHttpEntity(request, payload);
    return executeRequest(request, responseType);
  }

  @Nonnull
  private <T> T stream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<T> responseType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload);
    return streamRequest(request, responseType);
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
      @SuppressWarnings("UnstableApiUsage")
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(request, new OpenAiResponseHandler<>(responseType, JACKSON));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed.", e);
    }
  }

  @Nonnull
  private <T> T streamRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<T> responseType) {
    try {
      @SuppressWarnings("UnstableApiUsage")
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return (T) OpenAiStreamingHandler.handleResponse(client.executeOpen(null, request, null));
    } catch (final IOException e) {
      throw new OpenAiClientException("Request to OpenAI model failed.", e);
    }
  }
}
