package com.sap.ai.sdk.foundationmodels.openai.responses;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
import com.openai.core.http.Headers;
import com.openai.core.http.HttpClient;
import com.openai.core.http.HttpMethod;
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
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpOptions;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
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
  static OpenAIClient fromDestination(@Nonnull final HttpDestination destination) {
    final String baseUrl = destination.getUri().toString();
    final HttpClient httpClient = new AiCoreHttpClientImpl(destination);

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
  static final class AiCoreHttpClientImpl implements HttpClient {

    private final HttpDestination destination;

    AiCoreHttpClientImpl(@Nonnull final HttpDestination destination) {
      this.destination = destination;
      log.debug("Created HTTP client wrapper for destination: {}", destination.getUri());
    }

    @Override
    @Nonnull
    public HttpResponse execute(
        @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
      final var apacheClient = ApacheHttpClient5Accessor.getHttpClient(destination);
      final var apacheRequest = toApacheRequest(request);
      final var isStreaming = isStreamingRequest(request);

      try {
        if (isStreaming) {
          // For streaming, use executeOpen to keep connection open until explicitly closed
          final var apacheResponse = apacheClient.executeOpen(null, apacheRequest, null);
          return createStreamingResponse(apacheResponse);
        } else {
          // For non-streaming, use response handler which auto-closes the response
          return apacheClient.execute(apacheRequest, this::createBufferedResponse);
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
      return CompletableFuture.supplyAsync(() -> execute(request, requestOptions))
          .whenComplete(
              (response, error) ->
                  Optional.ofNullable(request.body()).ifPresent(HttpRequestBody::close));
    }

    @Override
    public void close() {
      // Apache HttpClient lifecycle is managed by Cloud SDK's ApacheHttpClient5Cache
    }

    @Nonnull
    private BasicClassicHttpRequest toApacheRequest(@Nonnull final HttpRequest request) {
      final var fullUrl = request.url();
      final var httpMethod = request.method();
      final var apacheRequest = createRequestByMethod(httpMethod, fullUrl);

      final var headers = request.headers();
      for (final var name : headers.names()) {
        if (name.equals("Authorization")) {
          continue;
        }
        for (final var value : headers.values(name)) {
          apacheRequest.addHeader(name, value);
        }
      }

      // Add request body if present
      final var body = request.body();
      if (body != null) {
        try (final var outputStream = new ByteArrayOutputStream()) {
          body.writeTo(outputStream);
          final var bodyBytes = outputStream.toByteArray();
          final var contentType =
              Optional.ofNullable(ContentType.parse(body.contentType()))
                  .orElse(ContentType.APPLICATION_JSON);
          apacheRequest.setEntity(new ByteArrayEntity(bodyBytes, contentType));
        } catch (final IOException e) {
          throw new OpenAIIoException("Failed to read request body", e);
        }
      }

      return apacheRequest;
    }

    @Nonnull
    private BasicClassicHttpRequest createRequestByMethod(
        @Nonnull final HttpMethod method, @Nonnull final String url) {
      return switch (method.toString().toUpperCase()) {
        case "GET" -> new HttpGet(url);
        case "POST" -> new HttpPost(url);
        case "PUT" -> new HttpPut(url);
        case "DELETE" -> new HttpDelete(url);
        case "PATCH" -> new HttpPatch(url);
        case "HEAD" -> new HttpHead(url);
        case "OPTIONS" -> new HttpOptions(url);
        default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
      };
    }

    /**
     * Detects if the current request is a streaming request by checking for the stream parameter.
     *
     * @param request The HTTP request to check
     * @return true if this is a streaming request, false otherwise
     */
    private boolean isStreamingRequest(@Nonnull final HttpRequest request) {
      final HttpRequestBody requestBody = request.body();
      if (requestBody != null) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
          requestBody.writeTo(outputStream);
          final String bodyContent = outputStream.toString(StandardCharsets.UTF_8);
          // Check for "stream":true or "stream": true in JSON body
          return bodyContent.contains("\"stream\":true")
              || bodyContent.contains("\"stream\": true");
        } catch (final IOException e) {
          log.warn("Failed to read request body for streaming detection", e);
          return false;
        }
      }
      return false;
    }

    @Nonnull
    private Headers extractResponseHeaders(@Nonnull final ClassicHttpResponse apacheResponse) {
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

      // Get live stream - do NOT buffer for streaming responses
      final InputStream liveStream =
          apacheResponse.getEntity() != null
              ? apacheResponse.getEntity().getContent()
              : InputStream.nullInputStream();

      // Return response that keeps connection open until stream is closed
      return new HttpResponse() {
        @Override
        public int statusCode() {
          return statusCode;
        }

        @Nonnull
        @Override
        public Headers headers() {
          return headers;
        }

        @Nonnull
        @Override
        public InputStream body() {
          return liveStream;
        }

        @Override
        public void close() {
          try {
            liveStream.close();
            apacheResponse.close();
            log.debug("Closed streaming response connection");
          } catch (final IOException e) {
            log.warn("Failed to close streaming response", e);
          }
        }
      };
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

      // Buffer response body for non-streaming requests
      final byte[] body =
          apacheResponse.getEntity() != null
              ? EntityUtils.toByteArray(apacheResponse.getEntity())
              : new byte[0];

      // Return response with buffered body
      return new HttpResponse() {
        @Override
        public int statusCode() {
          return statusCode;
        }

        @Nonnull
        @Override
        public Headers headers() {
          return headers;
        }

        @Nonnull
        @Override
        public InputStream body() {
          return new ByteArrayInputStream(body);
        }

        @Override
        public void close() {
          // Body already consumed and stored in byte array, nothing to close
        }
      };
    }
  }
}
