package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
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
    responseClient = AiCoreOpenAiClient.responses(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testSimpleResponseServiceCall() {
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();
    final Response response = responseClient.create(params);

    assertThat(response).isNotNull();
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
  }
}
