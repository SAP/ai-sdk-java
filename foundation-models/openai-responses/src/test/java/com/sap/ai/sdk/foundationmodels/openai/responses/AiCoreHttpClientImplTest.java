package com.sap.ai.sdk.foundationmodels.openai.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.openai.core.RequestOptions;
import com.openai.core.http.Headers;
import com.openai.core.http.HttpMethod;
import com.openai.core.http.HttpRequest;
import com.openai.core.http.HttpRequestBody;
import com.openai.errors.OpenAIIoException;
import com.sap.ai.sdk.foundationmodels.openai.responses.AiCoreOpenAiClient.AiCoreHttpClientImpl;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicHeader;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class AiCoreHttpClientImplTest {

  private static final String TEST_URL = "https://test.api.com/v1/chat/completions";
  private static final DefaultHttpDestination TEST_DESTINATION =
      DefaultHttpDestination.builder("https://test.api.com").build();

  @Test
  void testNonStreamingRequest() throws IOException {
    // Setup: Create a non-streaming request (no "stream":true in body)
    final String requestBodyContent =
        "{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"user\",\"content\":\"Hello\"}]}";
    final String responseBodyContent = "{\"id\":\"resp_123\",\"output\":\"Hi there!\"}";

    final HttpRequest request = mockHttpRequest(requestBodyContent, false);
    final HttpClient mockApacheClient = mock(HttpClient.class);
    final ClassicHttpResponse mockResponse = mockClassicHttpResponse(200, responseBodyContent);

    // Mock the execute method to call the response handler
    when(mockApacheClient.execute(
            any(BasicClassicHttpRequest.class), any(HttpClientResponseHandler.class)))
        .thenAnswer(
            invocation -> {
              HttpClientResponseHandler<com.openai.core.http.HttpResponse> handler =
                  invocation.getArgument(1);
              return handler.handleResponse(mockResponse);
            });

    try (MockedStatic<ApacheHttpClient5Accessor> mockedAccessor =
        mockStatic(ApacheHttpClient5Accessor.class)) {
      mockedAccessor
          .when(() -> ApacheHttpClient5Accessor.getHttpClient(TEST_DESTINATION))
          .thenReturn(mockApacheClient);

      // Execute: Make a non-streaming request
      final AiCoreHttpClientImpl client = new AiCoreHttpClientImpl(TEST_DESTINATION);
      final com.openai.core.http.HttpResponse response =
          client.execute(request, RequestOptions.none());

      // Verify: Response is buffered and can be read
      assertThat(response.statusCode()).isEqualTo(200);
      final String body = new String(response.body().readAllBytes(), StandardCharsets.UTF_8);
      assertThat(body).isEqualTo(responseBodyContent);

      // Verify: Request body was closed
      verify(request.body()).close();
    }
  }

  @Test
  void testStreamingRequest() throws IOException {
    // Setup: Create a streaming request (with "stream":true in body)
    final String requestBodyContent = "{\"model\":\"gpt-4\",\"messages\":[],\"stream\":true}";

    final HttpRequest request = mockHttpRequest(requestBodyContent, false);
    final HttpClient mockApacheClient = mock(HttpClient.class);
    final ClassicHttpResponse mockResponse = mockStreamingResponse();

    // Mock executeOpen for streaming
    when(mockApacheClient.executeOpen(isNull(), any(BasicClassicHttpRequest.class), isNull()))
        .thenReturn(mockResponse);

    try (MockedStatic<ApacheHttpClient5Accessor> mockedAccessor =
        mockStatic(ApacheHttpClient5Accessor.class)) {
      mockedAccessor
          .when(() -> ApacheHttpClient5Accessor.getHttpClient(TEST_DESTINATION))
          .thenReturn(mockApacheClient);

      // Execute: Make a streaming request
      final AiCoreHttpClientImpl client = new AiCoreHttpClientImpl(TEST_DESTINATION);
      final com.openai.core.http.HttpResponse response =
          client.execute(request, RequestOptions.none());

      // Verify: Response is streaming (connection kept open)
      assertThat(response.statusCode()).isEqualTo(200);
      assertThat(response.body()).isNotNull();

      // Verify: Can read from stream
      final byte[] chunk = new byte[10];
      final int read = response.body().read(chunk);
      assertThat(read).isGreaterThan(0);

      // Cleanup: Close the response
      response.close();

      // Verify: Request body was closed
      verify(request.body()).close();
    }
  }

  @Test
  void testStreamingRequestViaQueryParam() throws IOException {
    // Setup: Create a streaming request (with "stream=true" in query params)
    final String requestBodyContent = "{\"model\":\"gpt-4\",\"messages\":[]}";

    final HttpRequest request = mockHttpRequest(requestBodyContent, true);
    final HttpClient mockApacheClient = mock(HttpClient.class);
    final ClassicHttpResponse mockResponse = mockStreamingResponse();

    // Mock executeOpen for streaming
    when(mockApacheClient.executeOpen(isNull(), any(BasicClassicHttpRequest.class), isNull()))
        .thenReturn(mockResponse);

    try (MockedStatic<ApacheHttpClient5Accessor> mockedAccessor =
        mockStatic(ApacheHttpClient5Accessor.class)) {
      mockedAccessor
          .when(() -> ApacheHttpClient5Accessor.getHttpClient(TEST_DESTINATION))
          .thenReturn(mockApacheClient);

      // Execute: Make a streaming request via query param
      final AiCoreHttpClientImpl client = new AiCoreHttpClientImpl(TEST_DESTINATION);
      final com.openai.core.http.HttpResponse response =
          client.execute(request, RequestOptions.none());

      // Verify: Response is streaming
      assertThat(response.statusCode()).isEqualTo(200);
      assertThat(response.body()).isNotNull();

      // Cleanup
      response.close();

      // Verify: Request body was closed
      verify(request.body()).close();
    }
  }

  @Test
  void testRequestBodyClosedOnException() throws IOException {
    // Setup: Create a request that will fail
    final String requestBodyContent = "{\"model\":\"gpt-4\"}";

    final HttpRequest request = mockHttpRequest(requestBodyContent, false);
    final HttpClient mockApacheClient = mock(HttpClient.class);

    // Mock execute to throw IOException
    when(mockApacheClient.execute(
            any(BasicClassicHttpRequest.class), any(HttpClientResponseHandler.class)))
        .thenThrow(new IOException("Network error"));

    try (MockedStatic<ApacheHttpClient5Accessor> mockedAccessor =
        mockStatic(ApacheHttpClient5Accessor.class)) {
      mockedAccessor
          .when(() -> ApacheHttpClient5Accessor.getHttpClient(TEST_DESTINATION))
          .thenReturn(mockApacheClient);

      // Execute: Request fails with exception
      final AiCoreHttpClientImpl client = new AiCoreHttpClientImpl(TEST_DESTINATION);

      assertThatThrownBy(() -> client.execute(request, RequestOptions.none()))
          .isInstanceOf(OpenAIIoException.class)
          .hasMessageContaining("HTTP request execution failed");

      // Verify: Request body was still closed despite exception
      verify(request.body()).close();
    }
  }

  // Helper methods

  private HttpRequest mockHttpRequest(String bodyContent, boolean addStreamQueryParam)
      throws IOException {
    final HttpRequest request = mock(HttpRequest.class);
    final HttpRequestBody requestBody = mock(HttpRequestBody.class);

    when(request.url()).thenReturn(TEST_URL);
    when(request.method()).thenReturn(HttpMethod.POST);
    when(request.headers())
        .thenReturn(Headers.builder().put("Content-Type", "application/json").build());
    when(request.body()).thenReturn(requestBody);
    when(requestBody.contentType()).thenReturn("application/json");

    // Mock queryParams - return a mock QueryParams object
    final com.openai.core.http.QueryParams queryParams =
        mock(com.openai.core.http.QueryParams.class);
    when(request.queryParams()).thenReturn(queryParams);

    // Mock values() method for streaming detection
    if (addStreamQueryParam) {
      when(queryParams.values("stream")).thenReturn(java.util.Collections.singletonList("true"));
    } else {
      when(queryParams.values("stream")).thenReturn(java.util.Collections.emptyList());
    }

    // Mock the writeTo method to write body content
    doAnswer(
            invocation -> {
              ByteArrayOutputStream out = invocation.getArgument(0);
              out.write(bodyContent.getBytes(StandardCharsets.UTF_8));
              return null;
            })
        .when(requestBody)
        .writeTo(any(ByteArrayOutputStream.class));

    return request;
  }

  private ClassicHttpResponse mockClassicHttpResponse(int statusCode, String bodyContent)
      throws IOException {
    final ClassicHttpResponse response = mock(ClassicHttpResponse.class);
    final HttpEntity entity = mock(HttpEntity.class);
    final InputStream bodyStream =
        new ByteArrayInputStream(bodyContent.getBytes(StandardCharsets.UTF_8));

    when(response.getCode()).thenReturn(statusCode);
    when(response.getHeaders())
        .thenReturn(new Header[] {new BasicHeader("Content-Type", "application/json")});
    when(response.getEntity()).thenReturn(entity);
    when(entity.getContent()).thenReturn(bodyStream);

    return response;
  }

  private ClassicHttpResponse mockStreamingResponse() throws IOException {
    final ClassicHttpResponse response = mock(ClassicHttpResponse.class);
    final HttpEntity entity = mock(HttpEntity.class);
    // Create a stream that simulates continuous data
    final InputStream liveStream =
        new ByteArrayInputStream("data: chunk1\ndata: chunk2\n".getBytes(StandardCharsets.UTF_8));

    when(response.getCode()).thenReturn(200);
    when(response.getHeaders())
        .thenReturn(
            new Header[] {
              new BasicHeader("Content-Type", "text/event-stream"),
              new BasicHeader("Transfer-Encoding", "chunked")
            });
    when(response.getEntity()).thenReturn(entity);
    when(entity.getContent()).thenReturn(liveStream);

    return response;
  }
}
