package com.sap.ai.sdk.foundationmodels.anthropic;

import static com.sap.ai.sdk.foundationmodels.anthropic.AnthropicClientException.FACTORY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * Client for interacting with Anthropic Claude models via SAP AI Core.
 *
 * <p>Exposes synchronous and streaming chat completion using the invoke and
 * invoke-with-response-stream endpoints, routed through the SAP AI Core inference gateway with SAP
 * destination-based authorization.
 *
 * @since 1.26.0
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnthropicClient {

  private static final String INVOKE_PATH = "invoke";
  private static final String INVOKE_STREAM_PATH = "invoke-with-response-stream";
  private static final ObjectMapper JACKSON = JacksonConfiguration.getDefaultObjectMapper();

  @Nonnull private final Destination destination;

  /**
   * Create a new Anthropic client for the given model using the default resource group.
   *
   * @param model the Anthropic model to use.
   * @return a configured Anthropic client.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  public static AnthropicClient forModel(@Nonnull final AnthropicModel model)
      throws DeploymentResolutionException {
    final var destination = new AiCoreService().getInferenceDestination().forModel(model);
    return forDestination(destination);
  }

  /**
   * Create a new Anthropic client for the given model in a specific resource group.
   *
   * @param model the Anthropic model to use.
   * @param resourceGroup the resource group containing the deployment.
   * @return a configured Anthropic client.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  public static AnthropicClient forModel(
      @Nonnull final AnthropicModel model, @Nonnull final String resourceGroup)
      throws DeploymentResolutionException {
    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);
    return forDestination(destination);
  }

  /**
   * Create a new Anthropic client using a custom destination.
   *
   * @param destination the destination pointing to a Claude model deployment.
   * @return a configured Anthropic client.
   */
  @Nonnull
  public static AnthropicClient forDestination(@Nonnull final Destination destination) {
    return new AnthropicClient(destination);
  }

  /**
   * Perform a synchronous chat completion using the {@code invoke} endpoint.
   *
   * @param request the chat completion request.
   * @return the completion response.
   * @throws AnthropicClientException if the request fails.
   */
  @Nonnull
  public AnthropicChatCompletionResponse chatCompletion(
      @Nonnull final AnthropicChatCompletionRequest request) throws AnthropicClientException {
    return execute(INVOKE_PATH, request, AnthropicChatCompletionResponse.class);
  }

  /**
   * Perform a streaming chat completion using the {@code invoke-with-response-stream} endpoint.
   *
   * <p>Returns a lazily-populated stream of delta events. The stream must be consumed in a
   * try-with-resources block to ensure the underlying HTTP connection is released.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var stream = client.streamChatCompletion(request)) {
   *   stream.map(AnthropicChatCompletionDelta::getDeltaContent).forEach(System.out::print);
   * }
   * }</pre>
   *
   * @param request the chat completion request.
   * @return a stream of delta chunks.
   * @throws AnthropicClientException if the request fails.
   */
  @Nonnull
  public Stream<AnthropicChatCompletionDelta> streamChatCompletion(
      @Nonnull final AnthropicChatCompletionRequest request) throws AnthropicClientException {
    return executeStream(INVOKE_STREAM_PATH, request, AnthropicChatCompletionDelta.class);
  }

  @Nonnull
  private <T> T execute(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<T> responseType) {
    final var httpRequest = buildHttpPost(path, payload);
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(
          httpRequest, new ClientResponseHandler<>(responseType, AnthropicError.class, FACTORY));
    } catch (final IOException e) {
      throw new AnthropicClientException("Request to Anthropic model failed", e)
          .setHttpRequest(httpRequest);
    }
  }

  @Nonnull
  private <D extends AnthropicChatCompletionDelta> Stream<D> executeStream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<D> deltaType) {
    final var httpRequest = buildHttpPost(path, payload);
    try {
      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return new ClientStreamingHandler<>(deltaType, AnthropicError.class, FACTORY)
          .handleStreamingResponse(client.executeOpen(null, httpRequest, null));
    } catch (final IOException e) {
      throw new AnthropicClientException("Streaming request to Anthropic model failed", e)
          .setHttpRequest(httpRequest);
    }
  }

  @Nonnull
  private static HttpPost buildHttpPost(@Nonnull final String path, @Nonnull final Object payload) {
    final var httpRequest = new HttpPost(path);
    try {
      final var json = JACKSON.writeValueAsString(payload);
      httpRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    } catch (final JsonProcessingException e) {
      throw new AnthropicClientException("Failed to serialize request", e)
          .setHttpRequest(httpRequest);
    }
    return httpRequest;
  }
}
