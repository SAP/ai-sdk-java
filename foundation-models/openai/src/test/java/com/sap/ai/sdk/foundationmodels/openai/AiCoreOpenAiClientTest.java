package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseStatus;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.models.responses.ResponseTextConfig;
import com.openai.models.responses.ToolChoiceOptions;
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

    final var client = AiCoreOpenAiClient.forDestination(destination).responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();

    final Response response = client.create(params);

    assertThat(response).isNotNull();
    assertThat(response.isValid()).isTrue();
    assertThat(response.background().orElseThrow()).isFalse();
    assertThat(response.completedAt().orElseThrow()).isEqualTo(1774011347);
    assertThat(response.createdAt()).isEqualTo(1774011346);
    assertThat(response.error()).isEmpty();
    assertThat(response.id()).isEqualTo("resp_01a38d2783b385be0069bd43d260108193aef990678aa8a0af");
    assertThat(response.incompleteDetails()).isEmpty();
    assertThat(response.instructions()).isEmpty();
    assertThat(response.maxOutputTokens()).isEmpty();
    assertThat(response.maxToolCalls()).isEmpty();
    assertThat(response.metadata()).contains(Response.Metadata.builder().build());
    assertThat(response.model().asString()).isEqualTo("gpt-5-2025-08-07-dz-std");
    assertThat(response.parallelToolCalls()).isTrue();
    assertThat(response.previousResponseId()).isEmpty();
    assertThat(response.promptCacheKey()).isEmpty();
    assertThat(response.promptCacheRetention()).isEmpty();
    assertThat(response.safetyIdentifier()).isEmpty();
    assertThat(response.serviceTier()).contains(Response.ServiceTier.DEFAULT);
    assertThat(response.status()).contains(ResponseStatus.COMPLETED);
    assertThat(response.temperature()).contains(1.0);
    assertThat(response.toolChoice().asOptions()).isEqualTo(ToolChoiceOptions.AUTO);
    assertThat(response.tools()).isEmpty();
    assertThat(response.topLogprobs()).contains(0L);
    assertThat(response.topP()).contains(1.0);
    assertThat(response.truncation()).contains(Response.Truncation.DISABLED);
    assertThat(response.reasoning())
        .hasValueSatisfying(
            r -> {
              assertThat(r.effort()).contains(ReasoningEffort.MEDIUM);
              assertThat(r.summary()).isEmpty();
            });
    assertThat(response.text())
        .hasValueSatisfying(
            t -> {
              assertThat(t.verbosity()).contains(ResponseTextConfig.Verbosity.MEDIUM);
            });
    assertThat(response.usage())
        .hasValueSatisfying(
            u -> {
              assertThat(u.inputTokens()).isEqualTo(13L);
              assertThat(u.outputTokens()).isEqualTo(59L);
              assertThat(u.totalTokens()).isEqualTo(72L);
            });

    assertThat(response.output()).hasSize(2);
    // First item - reasoning
    final ResponseOutputItem reasoning = response.output().get(0);
    assertThat(reasoning.isReasoning()).isTrue();
    assertThat(reasoning.asReasoning().id())
        .isEqualTo("rs_01a38d2783b385be0069bd43d2de808193a34727b53738d2e1");

    // Second item - message
    final ResponseOutputItem outputItem = response.output().get(1);
    assertThat(outputItem).isInstanceOf(ResponseOutputItem.class);
    assertThat(outputItem.isMessage()).isTrue();
    final ResponseOutputMessage message = outputItem.asMessage();
    assertThat(message.id()).isEqualTo("msg_01a38d2783b385be0069bd43d354388193a870074cabde1603");
    assertThat(message.status()).isEqualTo(ResponseOutputMessage.Status.COMPLETED);
    assertThat(message.content()).hasSize(1);
    final ResponseOutputMessage.Content text = message.content().get(0);
    assertThat(text.isOutputText()).isTrue();
    assertThat(text.asOutputText().text()).isEqualTo("Paris.");
  }

  @Test
  void testSimpleResponseServiceCallAsync() {
    final var clientAsync = AiCoreOpenAiClient.forDestination(destination).async().responses();
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();

    final var response = clientAsync.create(params);

    assertThat(response).isNotNull();
    response
        .thenAccept(
            r -> {
              assertThat(r).isNotNull();
              assertThat(r.status().orElseThrow()).isEqualTo(ResponseStatus.COMPLETED);
              final var text = r.output().get(1).asMessage().content().get(0).asOutputText().text();
              assertThat(text).isEqualTo("Paris.");
            })
        .join();
  }

  @Test
  void testStreamingResponseServiceCall() {
    final var client = AiCoreOpenAiClient.forDestination(destination).responses();
    final var params =
        ResponseCreateParams.builder()
            .input("Complete the sentence - When life gives you lemon, ")
            .build();

    try (var stream = client.createStreaming(params)) {
      final var events = stream.stream().toList();

      assertThat(events).hasSize(18);
      assertThat(events).anyMatch(ResponseStreamEvent::isOutputTextDelta);
      assertThat(events).anyMatch(ResponseStreamEvent::isCompleted);
    }
  }
}
