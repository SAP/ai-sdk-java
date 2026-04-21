package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.openai.core.http.Headers;
import com.openai.core.http.HttpMethod;
import com.openai.core.http.HttpRequest;
import com.openai.core.http.HttpRequestBody;
import com.openai.core.http.QueryParams;
import com.openai.errors.OpenAIIoException;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiCoreHttpClientImplTest {

  private AiCoreHttpClientImpl client;
  private HttpDestination destination;

  @BeforeEach
  void setup() {
    destination = DefaultHttpDestination.builder("http://localhost:8080").build();
    client = new AiCoreHttpClientImpl(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @SneakyThrows
  @Test
  void testApacheRequestIsBuildCorrectly() {
    final var mockBody = mock(HttpRequestBody.class);
    doAnswer(
            invocation -> {
              final ByteArrayOutputStream outputStream = invocation.getArgument(0);
              outputStream.write("test body content".getBytes());
              return null;
            })
        .when(mockBody)
        .writeTo(any());
    final var openAiRequest =
        HttpRequest.builder()
            .method(HttpMethod.POST)
            .baseUrl(destination.getUri().toString())
            .pathSegments(List.of("chat", "completions"))
            .queryParams(QueryParams.builder().put("param1", "value1").build())
            .putHeader("Content-Type", "application/json")
            .putHeader("Authorization", "Bearer test-token")
            .body(mockBody)
            .build();

    final var apacheRequest = client.toApacheRequest(openAiRequest);

    assertThat(apacheRequest).isNotNull();
    assertThat(apacheRequest.getMethod()).isEqualTo(HttpMethod.POST.toString());
    assertThat(apacheRequest.getUri())
        .hasPath("/chat/completions")
        .hasParameter("param1", "value1");
    assertThat(apacheRequest.getHeaders())
        .extracting(Header::getName, Header::getValue)
        .containsExactly(tuple("Content-Type", "application/json")); // No "Authorization" header
    assertThat(apacheRequest.getEntity()).isNotNull();
    assertThat(EntityUtils.toString(apacheRequest.getEntity())).isEqualTo("test body content");
  }

  @Test
  void testToApacheRequestWrapsBodyIOException() {
    final var mockBody =
        mock(
            HttpRequestBody.class,
            invocation -> {
              throw new IOException();
            });
    final var openAiRequest =
        HttpRequest.builder()
            .method(HttpMethod.POST)
            .baseUrl(destination.getUri().toString())
            .body(mockBody)
            .build();

    assertThatThrownBy(() -> client.toApacheRequest(openAiRequest))
        .isInstanceOf(OpenAIIoException.class)
        .hasMessageContaining("Failed to read request body");
  }

  @Test
  void testExecuteThrowsForUnknownEndpoint() {
    final var request =
        HttpRequest.builder()
            .pathSegments(List.of("unknown-endpoint"))
            .method(HttpMethod.valueOf("POST"))
            .baseUrl(destination.getUri().toString())
            .build();

    assertThatThrownBy(() -> client.execute(request))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("/unknown-endpoint is not supported in AI Core");
  }

  @Test
  void testExecuteThrowsForDisallowedMethod() {
    final var request =
        HttpRequest.builder()
            .pathSegments(List.of("responses"))
            .method(HttpMethod.valueOf("DELETE")) // DELETE not allowed on /responses
            .baseUrl(destination.getUri().toString())
            .build();

    assertThatThrownBy(() -> client.execute(request))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining(
            "HTTP DELETE method is not supported on endpoint /v1/responses in AI Core");
  }

  @SneakyThrows
  @Test
  void testExecuteReturnsBufferedResponseForNon2xxStreamingResponse() {
    final var apacheClient = mock(CloseableHttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(dest -> apacheClient);
    final var httpResponse = mock(ClassicHttpResponse.class);
    when(apacheClient.executeOpen(any(), any(), any())).thenReturn(httpResponse);
    when(httpResponse.getCode()).thenReturn(400);
    when(httpResponse.getHeaders()).thenReturn(new BasicHeader[0]);
    when(httpResponse.getEntity()).thenReturn(null);
    final var request =
        HttpRequest.builder()
            .method(HttpMethod.POST)
            .baseUrl(destination.getUri().toString())
            .pathSegments(List.of("responses"))
            .putHeader("Accept", "text/event-stream") // Trigger streaming
            .build();

    final var response = client.execute(request);

    assertThat(response).isInstanceOf(AiCoreHttpClientImpl.BufferedHttpResponse.class);
    assertThat(response.statusCode()).isEqualTo(400);
  }

  @Test
  void testExecuteWrapsIOExceptionFromHttpClient() throws IOException {
    final var mockApacheClient = mock(CloseableHttpClient.class);
    when(mockApacheClient.execute(any(), (HttpClientResponseHandler<?>) any()))
        .thenThrow(new IOException("Connection was aborted"));
    ApacheHttpClient5Accessor.setHttpClientFactory(dest -> mockApacheClient);
    final var request =
        HttpRequest.builder()
            .pathSegments(List.of("responses"))
            .method(HttpMethod.valueOf("POST"))
            .baseUrl(destination.getUri().toString())
            .build();

    assertThatThrownBy(() -> client.execute(request))
        .isInstanceOf(OpenAIIoException.class)
        .hasMessageContaining("HTTP request execution failed");
    verify(mockApacheClient, never()).close();
  }

  @Test
  void testStreamingResponseCloseReleasesAllResources() throws IOException {
    final var mockBody = mock(InputStream.class);
    doThrow(new IOException("An IO error occurred.")).when(mockBody).close();
    final var mockApacheResponse = mock(ClassicHttpResponse.class);
    final var response =
        new AiCoreHttpClientImpl.StreamingHttpResponse(
            200, Headers.builder().build(), mockBody, mockApacheResponse);

    assertThatNoException().isThrownBy(response::close);
    verify(mockBody).close();
    verify(mockApacheResponse).close();
  }
}
