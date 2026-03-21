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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.hc.core5.http.Header;
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
      final org.apache.hc.client5.http.classic.HttpClient apacheClient =
          ApacheHttpClient5Accessor.getHttpClient(destination);

      final BasicClassicHttpRequest apacheRequest = toApacheRequest(request);

      try {
        return apacheClient.execute(apacheRequest, this::toOpenAiResponse);
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
      // No-op: Apache HttpClient lifecycle is managed by Cloud SDK's ApacheHttpClient5Cache
      // The cache maintains shared instances with automatic cleanup on expiration (default 1h TTL)
      // We fetch the client on each request, so we don't hold any resources to clean up
      log.debug(
          "HttpClient close() called - no action needed, lifecycle managed by Cloud SDK cache");
    }

    @Nonnull
    private BasicClassicHttpRequest toApacheRequest(@Nonnull final HttpRequest request) {
      // Build full URL from baseUrl + pathSegments + queryParams
      final String fullUrl = request.url();

      // Create request with appropriate HTTP method
      final BasicClassicHttpRequest apacheRequest =
          createRequestByMethod(request.method().toString(), fullUrl);

      // Add headers from OpenAI SDK request
      final Headers headers = request.headers();
      for (final String name : headers.names()) {
        for (final String value : headers.values(name)) {
          apacheRequest.addHeader(name, value);
        }
      }

      // Add request body if present
      final HttpRequestBody requestBody = request.body();
      if (requestBody != null) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
          requestBody.writeTo(outputStream);
          final byte[] bodyBytes = outputStream.toByteArray();
          final String contentType =
              requestBody.contentType() != null ? requestBody.contentType() : "application/json";
          apacheRequest.setEntity(new ByteArrayEntity(bodyBytes, ContentType.parse(contentType)));
        } catch (final IOException e) {
          throw new OpenAIIoException("Failed to read request body", e);
        }
      }

      return apacheRequest;
    }

    @Nonnull
    private BasicClassicHttpRequest createRequestByMethod(
        @Nonnull final String method, @Nonnull final String url) {
      return switch (method.toUpperCase()) {
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

    @Nonnull
    private HttpResponse toOpenAiResponse(@Nonnull final ClassicHttpResponse apacheResponse)
        throws IOException {
      // Map status code
      final int statusCode = apacheResponse.getCode();

      // Map headers
      final Map<String, List<String>> headers = new HashMap<>();
      for (final Header header : apacheResponse.getHeaders()) {
        headers.computeIfAbsent(header.getName(), k -> new ArrayList<>()).add(header.getValue());
      }

      // Map response body
      final byte[] body =
          apacheResponse.getEntity() != null
              ? EntityUtils.toByteArray(apacheResponse.getEntity())
              : new byte[0];

      // Build Headers object from map
      final Headers.Builder headersBuilder = Headers.builder();
      headers.forEach(headersBuilder::put);
      final Headers responseHeaders = headersBuilder.build();

      // Return anonymous implementation of HttpResponse interface
      return new HttpResponse() {
        @Override
        public int statusCode() {
          return statusCode;
        }

        @Nonnull
        @Override
        public Headers headers() {
          return responseHeaders;
        }

        @Nonnull
        @Override
        public InputStream body() {
          return new ByteArrayInputStream(body);
        }

        @Override
        public void close() {
          // Body already consumed and stored in byte array
        }
      };
    }
  }
}
