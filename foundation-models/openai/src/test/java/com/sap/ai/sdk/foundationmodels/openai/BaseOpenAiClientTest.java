package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.okXml;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.assertj.core.api.SoftAssertions;
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

  static void stubForChatCompletionTool() {
    stubFor(
        post(urlPathEqualTo("/chat/completions"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("chatCompletionToolResponse.json")));
  }

  static void stubForErrorHandling() {
    final var errorJson =
        """
            { "error": { "code": null, "message": "foo", "type": "invalid stuff" } }
            """;
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(serverError())
            .willSetStateTo("1"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("1")
            .willReturn(
                badRequest().withBody(errorJson).withHeader("Content-type", "application/json"))
            .willSetStateTo("2"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("2")
            .willReturn(
                badRequest()
                    .withBody("{ broken json")
                    .withHeader("Content-type", "application/json"))
            .willSetStateTo("3"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("3")
            .willReturn(okXml("<xml></xml>"))
            .willSetStateTo("4"));
    stubFor(post(anyUrl()).inScenario("Errors").whenScenarioStateIs("4").willReturn(noContent()));
  }

  static void assertForErrorHandling(@Nonnull final Runnable request) {

    final var softly = new SoftAssertions();

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Server errors should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("500")
        .extracting(e -> ((OpenAiClientException) e).getHttpResponse())
        .isNotNull();

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Error objects from OpenAI should be interpreted")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("400 (Bad Request): foo");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Failures while parsing error message should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("400")
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(JsonParseException.class);

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Non-JSON responses should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("Failed to parse");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Empty responses should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("is empty");

    softly.assertAll();
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
}
