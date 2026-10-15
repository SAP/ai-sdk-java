package com.sap.ai.sdk.foundationmodels.anthropic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.anthropic.core.RequestOptions;
import com.anthropic.core.http.HttpMethod;
import com.anthropic.core.http.HttpRequest;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SapAnthropicHttpClientTest {

  private SapAnthropicHttpClient sapClient;
  private HttpClient apacheClient;

  @BeforeEach
  void setup() {
    final var destination = DefaultHttpDestination.builder("http://localhost").build();
    sapClient = new SapAnthropicHttpClient(destination);
    apacheClient = mock(HttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(dest -> apacheClient);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  /**
   * Stubs the (ClassicHttpRequest, HttpClientResponseHandler) overload used by our non-streaming
   * path.
   */
  @SuppressWarnings("unchecked")
  private void stubExecute(final ClassicHttpResponse response) throws IOException {
    doAnswer(
            inv -> {
              final var handler = (HttpClientResponseHandler<Object>) inv.getArgument(1);
              return handler.handleResponse(response);
            })
        .when(apacheClient)
        .execute(any(), any(HttpClientResponseHandler.class));
  }

  private static HttpRequest nonStreamingRequest() {
    return HttpRequest.builder()
        .method(HttpMethod.POST)
        .baseUrl("http://localhost")
        .addPathSegment("v1/messages")
        .putHeader("Content-Type", "application/json")
        .build();
  }

  private static HttpRequest streamingRequest() {
    return HttpRequest.builder()
        .method(HttpMethod.POST)
        .baseUrl("http://localhost")
        .addPathSegment("v1/messages")
        .putHeader("Accept", "text/event-stream")
        .build();
  }

  @Test
  void testSkipsAuthorizationAndApiKeyHeaders() throws IOException {
    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    mockResponse.setEntity(new StringEntity("{}", ContentType.APPLICATION_JSON));
    stubExecute(mockResponse);

    final var request =
        HttpRequest.builder()
            .method(HttpMethod.POST)
            .baseUrl("http://localhost")
            .addPathSegment("v1/messages")
            .putHeader("Authorization", "Bearer secret")
            .putHeader("x-api-key", "key123")
            .putHeader("anthropic-version", "2023-06-01")
            .putHeader("Content-Type", "application/json")
            .build();

    final var response = sapClient.execute(request, RequestOptions.none());
    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void testExecuteAsyncDelegatesToExecute() throws IOException {
    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    mockResponse.setEntity(new StringEntity("{}", ContentType.APPLICATION_JSON));
    stubExecute(mockResponse);

    final var response =
        sapClient.executeAsync(nonStreamingRequest(), RequestOptions.none()).join();

    assertThat(response.statusCode()).isEqualTo(200);
  }

  @Test
  void testStreamingErrorResponseBuffered() throws IOException {
    final var errorBody = "{\"type\":\"error\",\"error\":{\"message\":\"bad\"}}";
    final var mockResponse = new BasicClassicHttpResponse(400, "Bad Request");
    mockResponse.setEntity(new StringEntity(errorBody, ContentType.APPLICATION_JSON));
    doReturn(mockResponse).when(apacheClient).executeOpen(any(), any(), any());

    final var response = sapClient.execute(streamingRequest(), RequestOptions.none());

    assertThat(response.statusCode()).isEqualTo(400);
    assertThat(new String(response.body().readAllBytes(), StandardCharsets.UTF_8)).contains("bad");
  }

  @Test
  void testStreamingNullEntityReturnsEmptyStream() throws IOException {
    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    doReturn(mockResponse).when(apacheClient).executeOpen(any(), any(), any());

    try (var response = sapClient.execute(streamingRequest(), RequestOptions.none())) {
      assertThat(response.statusCode()).isEqualTo(200);
      assertThat(response.body().read()).isEqualTo(-1);
    }
  }

  @Test
  void testBufferedNullEntityReturnsEmptyBody() throws IOException {
    stubExecute(new BasicClassicHttpResponse(200, "OK"));

    final var response = sapClient.execute(nonStreamingRequest(), RequestOptions.none());

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body().readAllBytes()).isEmpty();
  }

  @Test
  void testExecuteThrowsOnIoException() throws IOException {
    doThrow(new IOException("connection refused"))
        .when(apacheClient)
        .execute(any(), any(HttpClientResponseHandler.class));

    assertThatThrownBy(() -> sapClient.execute(nonStreamingRequest(), RequestOptions.none()))
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Anthropic HTTP request failed");
  }

  @Test
  void testSseRewriterHandlesNonJsonDataLine() throws IOException {
    final var sseBytes =
        "data: not-json\n\ndata: {\"type\":\"message_stop\"}\n\n".getBytes(StandardCharsets.UTF_8);
    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    mockResponse.setEntity(
        new InputStreamEntity(new ByteArrayInputStream(sseBytes), ContentType.TEXT_PLAIN));
    doReturn(mockResponse).when(apacheClient).executeOpen(any(), any(), any());

    try (var response = sapClient.execute(streamingRequest(), RequestOptions.none())) {
      final var output = new String(response.body().readAllBytes(), StandardCharsets.UTF_8);
      assertThat(output).contains("data: not-json");
      assertThat(output).contains("event: message_stop");
    }
  }

  @Test
  void testStreamingResponseCloseSwallowsIoException() throws IOException {
    final var brokenStream =
        spy(
            Objects.requireNonNull(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("__files/invokeStreamResponse.txt")));

    final var mockApacheResponse = mock(ClassicHttpResponse.class);
    doReturn(200).when(mockApacheResponse).getCode();
    doReturn(new org.apache.hc.core5.http.message.HeaderGroup().getHeaders())
        .when(mockApacheResponse)
        .getHeaders();
    doReturn(new InputStreamEntity(brokenStream, ContentType.TEXT_EVENT_STREAM))
        .when(mockApacheResponse)
        .getEntity();
    doReturn(mockApacheResponse).when(apacheClient).executeOpen(any(), any(), any());

    final var response = sapClient.execute(streamingRequest(), RequestOptions.none());
    doThrow(new IOException("broken pipe")).when(brokenStream).close();
    doThrow(new IOException("already closed")).when(mockApacheResponse).close();

    // Should not throw even when both close() calls fail
    response.close();
  }
}
