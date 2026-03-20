package com.sap.ai.sdk.foundationmodels.openai.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link OpenAiV1Client} Responses API calls using WireMock. */
@WireMockTest
class OpenAiV1ClientResponsesTest {

  private static final String BASE_URL = "http://localhost:8080";
  private static final String AUTH_TOKEN = "test-token-123";
  private OpenAIClient client;

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final var destination =
        DefaultHttpDestination.builder(
                server.getHttpBaseUrl() + "/v2/inference/deployments/test-deployment")
            .header("Authorization", "Bearer test-token-123")
            .header("AI-Client-Type", "AI SDK Java")
            .header("AI-Resource-Group", "default")
            .build();

    client = OpenAiV1Client.fromDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testFromDestinationWithoutAuthorizationHeader() {
    final HttpDestination destination = DefaultHttpDestination.builder(BASE_URL).build();

    assertThatThrownBy(() -> OpenAiV1Client.fromDestination(destination))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No Authorization header found");
  }

  @Test
  void testFromDestinationWithAuthorizationHeader() {
    final HttpDestination destination =
        DefaultHttpDestination.builder(BASE_URL)
            .header("Authorization", "Bearer " + AUTH_TOKEN)
            .build();

    final OpenAIClient client = OpenAiV1Client.fromDestination(destination);

    assertThat(client).isNotNull();
  }

  @Test
  void testCreateResponse() {
    final var params =
        ResponseCreateParams.builder()
            .input("What is the capital of France?")
            .model(ChatModel.GPT_5)
            .build();

    final Response response = client.responses().create(params);

    assertThat(response).isNotNull();
    assertThat(response.id()).isEqualTo("resp_01a38d2783b385be0069bd43d260108193aef990678aa8a0af");
    assertThat(response.status()).isPresent();
    assertThat(response.status().orElseThrow().asString()).isEqualTo("completed");
    assertThat(response.model().toString()).contains("gpt-5-2025-08-07-dz-std");

    assertThat(response.output()).isNotNull();
    assertThat(response.output()).hasSize(2);

    assertThat(response.usage()).isPresent();
    final var usage = response.usage().get();
    assertThat(usage.inputTokens()).isEqualTo(13);
    assertThat(usage.outputTokens()).isEqualTo(59);
    assertThat(usage.totalTokens()).isEqualTo(72);
  }
}
