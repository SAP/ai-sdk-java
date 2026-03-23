package com.sap.ai.sdk.foundationmodels.openai.responses;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.client.OpenAIClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStatus;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class AiCoreOpenAiClientTest {

  private OpenAIClient client;

  @BeforeEach
  void setup(@Nonnull final WireMockRuntimeInfo server) {
    // Create destination pointing to WireMock server
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();

    // Create OpenAI client using our custom implementation
    client = AiCoreOpenAiClient.fromDestination(destination);

    // Disable HTTP client caching for tests to ensure fresh clients
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testResponseSuccess() {
    final var params =
        ResponseCreateParams.builder()
            .input("What is the capital of France?")
            .model("gpt-5")
            .build();

    final Response response = client.responses().create(params);

    assertThat(response).isNotNull();
    assertThat(response.id()).isEqualTo("resp_01a38d2783b385be0069bd43d260108193aef990678aa8a0af");
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
    assertThat(response.output()).isNotEmpty();
  }
}
