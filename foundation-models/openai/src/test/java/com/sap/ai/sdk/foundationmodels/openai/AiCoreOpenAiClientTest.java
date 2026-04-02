package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.client.OpenAIClient;
import com.openai.core.http.QueryParams;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
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
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = AiCoreOpenAiClient.fromDestination(destination);

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

    final Response response = client.responses().create(params);

    assertThat(response).isNotNull();
    assertThat(response.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
  }

  @Test
  void testChatCompletionServiceSuccessWithMatchingModel() {
    final var params =
        ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_5)
            .addUserMessage("Say this is a test")
            .additionalQueryParams(QueryParams.builder().put("api-version", "2024-02-01").build())
            .build();

    final ChatCompletion response = client.chat().completions().create(params);
    assertThat(response).isNotNull();
  }
}
