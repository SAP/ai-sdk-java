package com.sap.ai.sdk.foundationmodels.openai.responses;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
import com.openai.core.http.Headers;
import com.openai.core.http.HttpClient;
import com.openai.core.http.HttpRequest;
import com.openai.core.http.HttpRequestBody;
import com.openai.core.http.HttpResponse;
import com.openai.errors.OpenAIIoException;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import org.apache.hc.core5.net.URIBuilder;

/**
 * Factory for creating OpenAI SDK clients configured for SAP AI Core deployments.
 *
 * <p>This class provides factory methods that return fully configured OpenAI SDK clients using SAP
 * Cloud SDK's Apache HttpClient with automatic OAuth token refresh.
 *
 * <p>The returned clients are long-lived and thread-safe. OAuth tokens are automatically refreshed
 * on each request via the Cloud SDK's {@link ApacheHttpClient5Accessor}.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * OpenAIClient client = AiCoreOpenAiClient.forModel(OpenAiModel.GPT_4);
 *
 * ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
 *     .addMessage(ChatCompletionUserMessageParam.builder()
 *         .content(ChatCompletionUserMessageParam.Content.ofTextContent("Hello!"))
 *         .build())
 *     .model("gpt-4")
 *     .build();
 *
 * ChatCompletion response = client.chat().completions().create(params);
 * }</pre>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AiCoreOpenAiClient {

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
  public static OpenAIClient forModel(@Nonnull final AiModel model) {
    return forModel(model, DEFAULT_RESOURCE_GROUP);
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
  public static OpenAIClient forModel(
      @Nonnull final AiModel model, @Nonnull final String resourceGroup) {
    final HttpDestination destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);

    return fromDestination(destination);
  }

  /**
   * Create an OpenAI client from a pre-resolved destination.
   *
   * <p>The destination should point to an OpenAI-compatible deployment. This method is useful for
   * advanced scenarios where you need custom destination configuration.
   *
   * @param destination The destination to use.
   * @return A configured OpenAI client instance.
   */
  @Nonnull
  @SuppressWarnings("PMD.CloseResource")
  static OpenAIClient fromDestination(@Nonnull final HttpDestination destination) {
    final var baseUrl = destination.getUri().toString();
    final var httpClient = new AiCoreHttpClientImpl(destination);

    // Build ClientOptions with our custom HttpClient
    // Note: apiKey is required by SDK but unused since we handle auth via destination headers
    final ClientOptions clientOptions =
        ClientOptions.builder().baseUrl(baseUrl).httpClient(httpClient).apiKey("unused").build();

    return new OpenAIClientImpl(clientOptions);
  }

  /**
   * Internal implementation of OpenAI SDK's HttpClient interface using Apache HttpClient from SAP
   * Cloud SDK.
   *
   * <p>This client leverages Cloud SDK's {@link ApacheHttpClient5Accessor} which provides: -
   * Automatic OAuth token refresh via {@code ApacheHttpClient5Wrapper} - Connection pooling and
   * lifecycle management - Custom headers from destination (including AI-Resource-Group) -
   * Thread-safe request execution
   *
   * <p>Package-private for testing purposes.
   */
  @Slf4j
  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static final class AiCoreHttpClientImpl implements HttpClient {
    private final HttpDestination destination;

    private static final Pattern STREAM_PATTERN = Pattern.compile("\"stream\"\\s*:\\s*true");

    @Override
    @Nonnull
    @SuppressWarnings("PMD.CloseResource")
    public HttpResponse execute(
        @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
      final var apacheClient = ApacheHttpClient5Accessor.getHttpClient(destination);
      final var preparedRequest = prepareRequest(request);
      final boolean isStreaming =
          isStreamingBody(preparedRequest.bodyBytes()) || isStreamingQuery(request);

      try {
        if (isStreaming) {
          final var apacheResponse =
              apacheClient.executeOpen(null, preparedRequest.apacheRequest(), null);
          return createStreamingResponse(apacheResponse);
        } else {
          return apacheClient.execute(
              preparedRequest.apacheRequest(), this::createBufferedResponse);
        }
      } catch (final IOException e) {
        throw new OpenAIIoException("HTTP request execution failed", e);
      } finally {
        Optional.ofNullable(request.body()).ifPresent(HttpRequestBody::close);
      }
    }

    @Override
    @Nonnull
    public CompletableFuture<HttpResponse> executeAsync(
        @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
      return CompletableFuture.supplyAsync(() -> execute(request, requestOptions));
    }

    @Override
    public void close() {
      // Apache HttpClient lifecycle is managed by Cloud SDK's ApacheHttpClient5Cache
    }

    @Nonnull
    @SuppressWarnings("PMD.CloseResource")
    private PreparedRequest prepareRequest(@Nonnull final HttpRequest request) {
      final var fullUri = buildUrlWithQueryParams(request);
      final var method = request.method();
      final var apacheRequest = new BasicClassicHttpRequest(method.name(), fullUri.toString());
      applyRequestHeaders(request, apacheRequest);

      final var requestBody = request.body();
      if (requestBody != null) {
        try (var outputStream = new ByteArrayOutputStream()) {
          requestBody.writeTo(outputStream);
          final var bodyBytes = outputStream.toByteArray();

          final var apacheContentType =
              Optional.ofNullable(requestBody.contentType())
                  .map(ContentType::parse)
                  .orElse(ContentType.APPLICATION_JSON);

          apacheRequest.setEntity(new ByteArrayEntity(bodyBytes, apacheContentType));
          return new PreparedRequest(apacheRequest, bodyBytes);
        } catch (final IOException e) {
          throw new OpenAIIoException("Failed to read request body", e);
        }
      }

      return new PreparedRequest(apacheRequest, null);
    }

    private static URI buildUrlWithQueryParams(@Nonnull final HttpRequest request) {
      try {
        final var uriBuilder = new URIBuilder(request.url());
        final var queryParams = request.queryParams();

        for (final var key : queryParams.keys()) {
          for (final var value : queryParams.values(key)) {
            uriBuilder.addParameter(key, value);
          }
        }

        return uriBuilder.build();
      } catch (URISyntaxException e) {
        throw new OpenAIIoException("Failed to build URI with query parameters", e);
      }
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

    private static boolean isStreamingBody(@Nullable final byte[] bodyBytes) {
      if (bodyBytes == null || bodyBytes.length == 0) {
        return false;
      }
      final var bodyContent = new String(bodyBytes, StandardCharsets.UTF_8);
      return STREAM_PATTERN.matcher(bodyContent).find();
    }

    private static boolean isStreamingQuery(@Nonnull final HttpRequest request) {
      return request.queryParams().values("stream").stream()
          .anyMatch(value -> "true".equalsIgnoreCase(value));
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

    /**
     * Creates a streaming HttpResponse that keeps the connection open and provides a live
     * InputStream.
     *
     * @param apacheResponse Apache HTTP response (must be kept open)
     * @return HttpResponse with live stream
     * @throws IOException if getting the stream fails
     */
    @Nonnull
    private HttpResponse createStreamingResponse(@Nonnull final ClassicHttpResponse apacheResponse)
        throws IOException {

      final var statusCode = apacheResponse.getCode();
      final var headers = extractResponseHeaders(apacheResponse);

      final var liveStream =
          apacheResponse.getEntity() != null
              ? apacheResponse.getEntity().getContent()
              : InputStream.nullInputStream();

      // Return response that keeps connection open until stream is closed
      return new StreamingHttpResponse(statusCode, headers, liveStream, apacheResponse);
    }

    /**
     * Creates a buffered HttpResponse with the entire response body loaded into memory.
     *
     * @param apacheResponse Apache HTTP response (will be consumed and closed)
     * @return HttpResponse with buffered body
     * @throws IOException if reading the response body fails
     */
    @Nonnull
    private HttpResponse createBufferedResponse(@Nonnull final ClassicHttpResponse apacheResponse)
        throws IOException {
      final int statusCode = apacheResponse.getCode();
      final Headers headers = extractResponseHeaders(apacheResponse);

      final byte[] body =
          apacheResponse.getEntity() != null
              ? EntityUtils.toByteArray(apacheResponse.getEntity())
              : new byte[0];

      return new BufferedHttpResponse(statusCode, headers, body);
    }

    record PreparedRequest(@Nonnull ClassicHttpRequest apacheRequest, @Nullable byte[] bodyBytes) {}

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
