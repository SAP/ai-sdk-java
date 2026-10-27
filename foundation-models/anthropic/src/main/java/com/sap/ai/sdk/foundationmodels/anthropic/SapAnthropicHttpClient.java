package com.sap.ai.sdk.foundationmodels.anthropic;

import com.anthropic.core.RequestOptions;
import com.anthropic.core.http.Headers;
import com.anthropic.core.http.HttpClient;
import com.anthropic.core.http.HttpRequest;
import com.anthropic.core.http.HttpResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;

/**
 * Bridges the official Anthropic Java SDK's {@link HttpClient} interface to SAP Cloud SDK's Apache
 * HttpClient, enabling SAP AI Core OAuth authentication for Anthropic API requests.
 *
 * <p>Rewrites the Anthropic API URL ({@code /v1/messages}) to the SAP Bedrock-style endpoints
 * ({@code /invoke} or {@code /invoke-with-response-stream}) and transforms the request body to add
 * the required {@code anthropic_version} field and remove the {@code model} field.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@SuppressWarnings(
    "PMD.CloseResource") // client lifecycle is managed by its user, should be explicitly closed
class SapAnthropicHttpClient implements HttpClient {

  private static final String INVOKE_PATH = "invoke";
  private static final String INVOKE_STREAM_PATH = "invoke-with-response-stream";
  private static final String SSE_MEDIA_TYPE = "text/event-stream";
  private static final String ANTHROPIC_VERSION = "bedrock-2023-05-31";

  private final HttpDestination destination;

  @Override
  @Nonnull
  public HttpResponse execute(
      @Nonnull final HttpRequest request, @Nonnull final RequestOptions requestOptions) {
    final var apacheClient = ApacheHttpClient5Accessor.getHttpClient(destination);
    final var isStreaming = isStreaming(request);
    final var apacheRequest = toApacheRequest(request, isStreaming);

    try {
      if (isStreaming) {
        final var apacheResponse = apacheClient.executeOpen(null, apacheRequest, null);
        final int statusCode = apacheResponse.getCode();
        if (statusCode >= 200 && statusCode < 300) {
          return createStreamingResponse(apacheResponse);
        }
        return createBufferedResponse(apacheResponse);
      } else {
        return apacheClient.execute(apacheRequest, SapAnthropicHttpClient::createBufferedResponse);
      }
    } catch (final IOException e) {
      throw new ClientException("Anthropic HTTP request failed", e);
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

  @Nonnull
  private ClassicHttpRequest toApacheRequest(
      @Nonnull final HttpRequest request, final boolean isStreaming) {
    final var targetPath = isStreaming ? INVOKE_STREAM_PATH : INVOKE_PATH;
    final var apacheRequest = new BasicClassicHttpRequest(request.method().name(), targetPath);

    for (final var name : request.headers().names()) {
      if (shouldSkipHeader(name)) {
        continue;
      }
      for (final var value : request.headers().values(name)) {
        apacheRequest.addHeader(name, value);
      }
    }

    try (var requestBody = request.body()) {
      if (requestBody != null) {
        try (var outputStream = new ByteArrayOutputStream()) {
          requestBody.writeTo(outputStream);
          final var bodyBytes = transformBody(outputStream.toByteArray());
          final var contentType =
              requestBody.contentType() != null
                  ? ContentType.parse(requestBody.contentType())
                  : ContentType.APPLICATION_JSON;
          apacheRequest.setEntity(new ByteArrayEntity(bodyBytes, contentType));
        } catch (final IOException e) {
          throw new ClientException("Failed to read Anthropic request body", e);
        }
      }
    }

    return apacheRequest;
  }

  private static boolean shouldSkipHeader(@Nonnull final String name) {
    return "Authorization".equalsIgnoreCase(name)
        || "x-api-key".equalsIgnoreCase(name)
        || "anthropic-version".equalsIgnoreCase(name);
  }

  private static boolean isStreaming(@Nonnull final HttpRequest request) {
    return request.headers().values("Accept").stream()
        .map(value -> Objects.toString(value, "").toLowerCase(Locale.ROOT))
        .anyMatch(value -> value.contains(SSE_MEDIA_TYPE));
  }

  /** Adds {@code anthropic_version} and removes {@code model} from the JSON body. */
  @Nonnull
  private static byte[] transformBody(@Nonnull final byte[] bodyBytes) {
    try {
      final var mapper = JacksonConfiguration.getDefaultObjectMapper();
      final var node = (ObjectNode) mapper.readTree(bodyBytes);
      node.remove("model");
      node.remove("stream");
      node.put("anthropic_version", ANTHROPIC_VERSION);
      return mapper.writeValueAsBytes(node);
    } catch (final IOException e) {
      throw new ClientException("Failed to transform Anthropic request body", e);
    }
  }

  @Nonnull
  private static Headers extractResponseHeaders(@Nonnull final ClassicHttpResponse apacheResponse) {
    final var builder = Headers.builder();
    for (final var header : apacheResponse.getHeaders()) {
      builder.put(header.getName(), header.getValue());
    }
    return builder.build();
  }

  @Nonnull
  private static HttpResponse createStreamingResponse(
      @Nonnull final ClassicHttpResponse apacheResponse) throws IOException {
    final var statusCode = apacheResponse.getCode();
    final var headers = extractResponseHeaders(apacheResponse);
    final var rawStream =
        apacheResponse.getEntity() != null
            ? apacheResponse.getEntity().getContent()
            : InputStream.nullInputStream();
    final var rewritten = rewriteBedrockSseToAnthropicSse(rawStream);
    return new StreamingHttpResponse(statusCode, headers, rewritten, apacheResponse);
  }

  /**
   * SAP's Bedrock endpoint emits {@code data:} lines with no {@code event:} field. The Anthropic
   * SDK's SSE parser requires {@code event:} lines and drops events without one. This method wraps
   * the raw stream and injects {@code event: <type>} before each {@code data:} line, reading the
   * type from the JSON payload.
   */
  @Nonnull
  private static InputStream rewriteBedrockSseToAnthropicSse(@Nonnull final InputStream raw)
      throws IOException {
    final var piped = new PipedInputStream(8192);
    final var out = new PipedOutputStream(piped);
    final var writer = new PrintWriter(out, false, StandardCharsets.UTF_8);
    final var reader = new BufferedReader(new InputStreamReader(raw, StandardCharsets.UTF_8));
    final var mapper = JacksonConfiguration.getDefaultObjectMapper();

    ThreadContextExecutors.getExecutor()
        .execute(
            () -> {
              try (raw;
                  out;
                  writer;
                  reader) {
                String line;
                while ((line = reader.readLine()) != null) {
                  if (line.startsWith("data:")) {
                    final var data = line.substring(5).stripLeading();
                    if (!data.equals("[DONE]")) {
                      try {
                        final var node = mapper.readTree(data);
                        final var type = node.path("type").asText(null);
                        if (type != null) {
                          writer.print("event: " + type + "\n");
                        }
                      } catch (final IOException ignored) {
                        // non-JSON data line — emit as-is without event prefix
                      }
                    }
                  }
                  writer.print(line + "\n");
                }
                writer.flush();
              } catch (final IOException e) {
                log.debug("Error rewriting Bedrock SSE stream", e);
              }
            });

    return piped;
  }

  @Nonnull
  private static HttpResponse createBufferedResponse(
      @Nonnull final ClassicHttpResponse apacheResponse) throws IOException {
    try (apacheResponse) {
      final int statusCode = apacheResponse.getCode();
      final var headers = extractResponseHeaders(apacheResponse);
      final var body =
          apacheResponse.getEntity() != null
              ? EntityUtils.toByteArray(apacheResponse.getEntity())
              : new byte[0];
      return new BufferedHttpResponse(statusCode, headers, body);
    }
  }

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
      } catch (final IOException e) {
        log.debug("Failed to close streaming response body", e);
      }
      try {
        apacheResponse.close();
      } catch (final IOException e) {
        log.debug("Failed to close streaming response connection", e);
      }
    }
  }

  record BufferedHttpResponse(int statusCode, @Nonnull Headers headers, @Nonnull byte[] bodyBytes)
      implements HttpResponse {

    @Nonnull
    @Override
    public InputStream body() {
      return new ByteArrayInputStream(bodyBytes);
    }

    @Override
    public void close() {}
  }
}
