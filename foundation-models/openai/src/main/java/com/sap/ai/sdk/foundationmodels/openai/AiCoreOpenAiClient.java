package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
import com.openai.core.http.Headers;
import com.openai.core.http.HttpClient;
import com.openai.core.http.HttpRequest;
import com.openai.core.http.HttpResponse;
import com.openai.errors.OpenAIIoException;
import com.openai.services.blocking.ResponseService;
import com.openai.services.blocking.ResponseServiceImpl;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;

/**
 * Factory for creating OpenAI SDK clients configured for SAP AI Core deployments.
 *
 * <p>This class provides factory methods that return fully configured OpenAI SDK clients using SAP
 * Cloud SDK's Apache HttpClient with automatic OAuth token refresh.
 *
 * @since 1.18.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public class AiCoreOpenAiClient {

  private static final String DEFAULT_RESOURCE_GROUP = "default";

  /**
   * Create an OpenAI client for a deployment serving the specified model using the default resource
   * group.
   *
   * @param model The AI model to target.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static ResponseService responses(@Nonnull final OpenAiModel model) {
    return responses(model, DEFAULT_RESOURCE_GROUP);
  }

  /**
   * Create an OpenAI client for a deployment serving the specified model in the given resource
   * group.
   *
   * @param model The AI model to target.
   * @param resourceGroup The resource group containing the deployment.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static ResponseService responses(
      @Nonnull final OpenAiModel model, @Nonnull final String resourceGroup) {
    final HttpDestination destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);
    return responses(model, destination);
  }

  @Nonnull
  static ResponseService responses(
      @Nonnull final OpenAiModel model, @Nonnull final HttpDestination destination) {
    final var clientOptions =
        ClientOptions.builder()
            .baseUrl(destination.getUri().toString())
            .httpClient(new AiCoreHttpClientImpl(destination))
            .apiKey("unused")
            .build();
    final var chatModel =
        Optional.ofNullable(model.version())
            .map(version -> model.name() + "-" + version)
            .orElseGet(model::name);
    return new AiCoreResponseService(new ResponseServiceImpl(clientOptions), chatModel);
  }

  /**
   * Internal implementation of OpenAI SDK's HttpClient interface using Apache HttpClient from SAP
   * Cloud SDK.
   */
  @Slf4j
  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static final class AiCoreHttpClientImpl implements HttpClient {
    private final HttpDestination destination;

    private static final String SSE_MEDIA_TYPE = "text/event-stream";
    private static final Map<String, Set<String>> ALLOWED_OPERATIONS =
        Map.of(
            "/chat/completions", Set.of("POST"),
            "/responses", Set.of("GET", "POST"),
            "/responses/[^/]+", Set.of("GET", "DELETE"),
            "/responses/[^/]+/compact", Set.of("POST"),
            "/responses/[^/]+/cancel", Set.of("POST"));

    @Override
    @Nonnull
    public HttpResponse execute(
        @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
      validateAllowedOperation(request);
      final var apacheClient = ApacheHttpClient5Accessor.getHttpClient(destination);
      final var apacheRequest = toApacheRequest(request);

      try {
        if (isStreaming(request)) {
          final var apacheResponse = apacheClient.executeOpen(null, apacheRequest, null);
          final int statusCode = apacheResponse.getCode();
          if (statusCode >= 200 && statusCode < 300) {
            return createStreamingResponse(apacheResponse);
          }
          return createBufferedResponse(apacheResponse);
        } else {
          return apacheClient.execute(apacheRequest, this::createBufferedResponse);
        }
      } catch (final IOException e) {
        throw new OpenAIIoException("HTTP request execution failed", e);
      }
    }

    @Override
    @Nonnull
    public CompletableFuture<HttpResponse> executeAsync(
        @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
      return CompletableFuture.supplyAsync(
          () -> execute(request, requestOptions), ThreadContextExecutors.getExecutor());
    }

    @Override
    public void close() {
      // Apache HttpClient lifecycle is managed by Cloud SDK's ApacheHttpClient5Cache
    }

    private static void validateAllowedOperation(@Nonnull final HttpRequest request) {
      final var endpoint = "/" + String.join("/", request.pathSegments());
      final var method = request.method().name();

      // Find matching path pattern
      final var matchingEntry =
          ALLOWED_OPERATIONS.entrySet().stream()
              .filter(entry -> endpoint.matches(entry.getKey()))
              .findFirst()
              .orElseThrow(
                  () ->
                      new UnsupportedOperationException(
                          String.format("Endpoint %s is not supported in AI Core", endpoint)));

      // Validate method
      if (!matchingEntry.getValue().contains(method)) {
        throw new UnsupportedOperationException(
            String.format(
                "HTTP %s method is not supported on endpoint %s in AI Core", method, endpoint));
      }
    }

    @Nonnull
    private ClassicHttpRequest toApacheRequest(@Nonnull final HttpRequest request) {
      final var fullUri = request.url();
      final var method = request.method();
      final var apacheRequest = new BasicClassicHttpRequest(method.name(), fullUri);
      applyRequestHeaders(request, apacheRequest);

      try (var requestBody = request.body()) {
        if (requestBody != null) {
          try (var outputStream = new ByteArrayOutputStream()) {
            requestBody.writeTo(outputStream);
            final var bodyBytes = outputStream.toByteArray();

            final var apacheContentType =
                Optional.ofNullable(requestBody.contentType())
                    .map(ContentType::parse)
                    .orElse(ContentType.APPLICATION_JSON);

            apacheRequest.setEntity(new ByteArrayEntity(bodyBytes, apacheContentType));
            return apacheRequest;
          } catch (final IOException e) {
            throw new OpenAIIoException("Failed to read request body", e);
          }
        }
      }

      return apacheRequest;
    }

    private static void applyRequestHeaders(
        @Nonnull final HttpRequest request, @Nonnull final BasicClassicHttpRequest apacheRequest) {
      final var headers = request.headers();
      for (final var name : headers.names()) {
        if ("Authorization".equalsIgnoreCase(name)) {
          continue;
        }
        for (final var value : headers.values(name)) {
          apacheRequest.addHeader(name, value);
        }
      }
    }

    private static boolean isStreaming(@Nonnull final HttpRequest request) {
      return request.headers().values("Accept").stream()
          .map(value -> Objects.toString(value, "").toLowerCase(Locale.ROOT))
          .anyMatch(value -> value.contains(SSE_MEDIA_TYPE));
    }

    @Nonnull
    private static Headers extractResponseHeaders(
        @Nonnull final ClassicHttpResponse apacheResponse) {
      final var builder = Headers.builder();
      for (final var header : apacheResponse.getHeaders()) {
        builder.put(header.getName(), header.getValue());
      }
      return builder.build();
    }

    @Nonnull
    private HttpResponse createStreamingResponse(@Nonnull final ClassicHttpResponse apacheResponse)
        throws IOException {

      final var statusCode = apacheResponse.getCode();
      final var headers = extractResponseHeaders(apacheResponse);

      final var liveStream =
          apacheResponse.getEntity() != null
              ? apacheResponse.getEntity().getContent()
              : InputStream.nullInputStream();

      return new StreamingHttpResponse(statusCode, headers, liveStream, apacheResponse);
    }

    @Nonnull
    private HttpResponse createBufferedResponse(@Nonnull final ClassicHttpResponse apacheResponse)
        throws IOException {
      try (apacheResponse) {
        final int statusCode = apacheResponse.getCode();
        final Headers headers = extractResponseHeaders(apacheResponse);

        final byte[] body =
            apacheResponse.getEntity() != null
                ? EntityUtils.toByteArray(apacheResponse.getEntity())
                : new byte[0];

        return new BufferedHttpResponse(statusCode, headers, body);
      }
    }

    /**
     * HTTP response for streaming requests. Keeps the connection open and provides a live stream.
     * The stream must be closed by calling {@link #close()}.
     */
    record StreamingHttpResponse(
        int statusCode,
        @Nonnull Headers headers,
        @Nonnull InputStream body,
        @Nonnull ClassicHttpResponse apacheResponse)
        implements HttpResponse {

      @Override
      public void close() {
        try {
          body.close();
          apacheResponse.close();
          log.debug("Closed streaming response connection");
        } catch (final IOException e) {
          log.warn("Failed to close streaming response", e);
        }
      }
    }

    /**
     * HTTP response for buffered requests. The entire response body is loaded into memory. No
     * resources need to be closed.
     */
    record BufferedHttpResponse(int statusCode, @Nonnull Headers headers, @Nonnull byte[] bodyBytes)
        implements HttpResponse {

      @Nonnull
      @Override
      public InputStream body() {
        return new ByteArrayInputStream(bodyBytes);
      }

      @Override
      public void close() {
        // Body already consumed and buffered, nothing to close
      }
    }
  }
}
