package com.sap.ai.sdk.orchestration;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@SuppressWarnings("UnstableApiUsage")
@WireMockTest
public class OrchestrationResponseHandlerTest {
  private OrchestrationClient client;

  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create().modelName("gpt-35-turbo-16k").modelParams(Map.of());

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationClient(destination).withLlmConfig(LLM_CONFIG);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testSuccessResponse() {
    var response =
        ok().withBodyFile("serializedResponse.json").withHeader("Content-Type", "application/json");
    stubFor(post(anyUrl()).willReturn(response));

    var result = client.chatCompletion("Hello there!");

    assertThat(result).isEqualTo("General Kenobi!");
  }

  @Test
  void testGenericErrorHandling() {
    stubFor(post(anyUrl()).willReturn(serverError()));

    assertThatThrownBy(() -> client.chatCompletion("Hello World!"))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("500 Server Error");
  }

  @Test
  void testOrchestrationErrorParsing() {
    stubFor(
        post(anyUrl())
            .willReturn(
                badRequest()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("errorResponse.json")));

    assertThatThrownBy(() -> client.chatCompletion("Hello World!"))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request")
        .hasMessageContaining("'orchestration_config' is a required property");
  }
}
