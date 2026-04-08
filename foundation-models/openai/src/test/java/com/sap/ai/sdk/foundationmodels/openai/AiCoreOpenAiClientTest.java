package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStatus;
import com.openai.services.blocking.ResponseService;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class AiCoreOpenAiClientTest {

  private ResponseService responseClient;

  @BeforeEach
  void setup(@Nonnull final WireMockRuntimeInfo server) {
    final HttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    responseClient = AiCoreOpenAiClient.responses(OpenAiModel.GPT_5, destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testResponseServiceSuccessWithMatchingModel() {
    final var params =
        ResponseCreateParams.builder()
            .input("What is the capital of France?")
            .model(ChatModel.GPT_5)
            .build();

    final Response response = responseClient.create(params);

    assertThat(response).isNotNull();
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
  }

  @Test
  void testResponseServiceSuccessWithoutModel() {
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();
    final Response response = responseClient.create(params);

    assertThat(response).isNotNull();
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
  }

  @Test
  void testResponseServiceFailsWithModelMismatch() {
    final var params =
        ResponseCreateParams.builder()
            .input("What is the capital of France?")
            .model(ChatModel.GPT_4) // Different from client's expected model "gpt-5"
            .build();

    assertThatThrownBy(() -> responseClient.create(params))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Model mismatch")
        .hasMessageContaining("gpt-5")
        .hasMessageContaining("gpt-4");
  }

  @Test
  void testResponseServiceWithOptions() {
    final var modifiedService =
        responseClient.withOptions(
            builder -> {
              // Modify some option
              builder.putHeader("X-Custom-Header", "test-value");
            });

    assertThat(modifiedService).isInstanceOf(AiCoreResponseService.class);
  }
}
