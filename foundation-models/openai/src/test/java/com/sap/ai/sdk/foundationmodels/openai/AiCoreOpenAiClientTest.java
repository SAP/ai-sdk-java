package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseStatus;
import com.openai.models.responses.ResponseStreamEvent;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class AiCoreOpenAiClientTest {
  private AiCoreOpenAiClient client;

  @BeforeEach
  void setup(@Nonnull final WireMockRuntimeInfo server) {
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = AiCoreOpenAiClient.forDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testSimpleResponseServiceCall() {
    final var responseService = client.responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();

    final Response response = responseService.create(params);

    assertThat(response).isNotNull();
    assertCreateResponse(response);
  }

  @Test
  void testSimpleResponseServiceCallAsync() {
    final var responseClientAsync = client.async().responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();

    final var response = responseClientAsync.create(params).join();

    assertThat(response).isNotNull();
    assertCreateResponse(response);
  }

  @Test
  void testStreamingResponseServiceCall() {
    final var responseService = client.responses();
    final var params =
        ResponseCreateParams.builder()
            .input("Complete the sentence - When life gives you lemon, ")
            .build();

    try (var stream = responseService.createStreaming(params)) {
      final var events = stream.stream().toList();
      assertThat(events).hasSize(18);
      assertThat(events).anyMatch(ResponseStreamEvent::isOutputTextDelta);
      assertThat(events).anyMatch(ResponseStreamEvent::isOutputTextDone);
    }
  }

  public static void assertCreateResponse(@Nonnull final Response response) {
    assertThat(response.isValid()).isTrue();
    assertThat(response.model().asString()).isEqualTo("gpt-5-2025-08-07-dz-std");
    assertThat(response.status()).contains(ResponseStatus.COMPLETED);
    assertThat(response.reasoning())
        .hasValueSatisfying(r -> assertThat(r.effort()).contains(ReasoningEffort.MEDIUM));
    assertThat(response.usage())
        .hasValueSatisfying(
            u -> {
              assertThat(u.inputTokens()).isEqualTo(13L);
              assertThat(u.outputTokens()).isEqualTo(59L);
              assertThat(u.totalTokens()).isEqualTo(72L);
            });
    assertThat(response.output())
        .satisfiesExactly(
            item -> assertThat(item.isReasoning()).isTrue(),
            item -> {
              assertThat(item.isMessage()).isTrue();
              assertThat(item.asMessage().content()).isNotEmpty();
              final ResponseOutputMessage.Content content = item.asMessage().content().get(0);
              assertThat(content.isOutputText()).isTrue();
              assertThat(content.asOutputText().text()).isEqualTo("Paris.");
            });
  }
}
