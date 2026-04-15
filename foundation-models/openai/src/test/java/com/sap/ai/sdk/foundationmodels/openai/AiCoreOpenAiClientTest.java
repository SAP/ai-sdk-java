package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStatus;
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

  private HttpDestination destination;

  @BeforeEach
  void setup(@Nonnull final WireMockRuntimeInfo server) {
    destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testSimpleResponseServiceCall() {
    final var responseClient = AiCoreOpenAiClient.forDestination(destination).responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();
    final Response response = responseClient.create(params);

    assertThat(response).isNotNull();
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
  }

  @Test
  void testSimpleResponseServiceCallAsync() {
    final var responseClientAsync =
        AiCoreOpenAiClient.forDestination(destination).async().responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();
    final var response = responseClientAsync.create(params);
    assertThat(response).isNotNull();
    response
        .thenAccept(
            r -> {
              assertThat(r).isNotNull();
              assertThat(r.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
            })
        .join();
  }
}
