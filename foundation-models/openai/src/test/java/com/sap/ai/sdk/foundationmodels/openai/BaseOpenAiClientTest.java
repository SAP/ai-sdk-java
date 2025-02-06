package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@WireMockTest
abstract class BaseOpenAiClientTest {

  protected static final ObjectMapper MAPPER = new ObjectMapper();
  protected static OpenAiClient client;
  protected final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  static void stubForChatCompletion() {

    stubFor(
        post(urlPathEqualTo("/chat/completions"))
            .withQueryParam("api-version", equalTo("2024-02-01"))
            .willReturn(
                aResponse()
                    .withBodyFile("chatCompletionResponse.json")
                    .withHeader("Content-Type", "application/json")));
  }

  static void stubForEmbedding() {
    stubFor(
        post(urlPathEqualTo("/embeddings"))
            .willReturn(
                aResponse()
                    .withBodyFile("embeddingResponse.json")
                    .withHeader("Content-Type", "application/json")));
  }

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = OpenAiClient.withCustomDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  InputStream stubStreamChatCompletion(String responseFile) throws IOException {
    var inputStream = spy(fileLoader.apply(responseFile));

    final var httpClient = mock(HttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

    // Create a mock response
    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
    mockResponse.setEntity(inputStreamEntity);
    mockResponse.setHeader("Content-Type", "text/event-stream");

    // Configure the HttpClient mock to return the mock response
    doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

    return inputStream;
  }

  static void stubForChatCompletionTool() {
    stubFor(
        post(urlPathEqualTo("/chat/completions"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("chatCompletionToolResponse.json")));
  }
}
