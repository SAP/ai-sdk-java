package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCancelParams;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseDeleteParams;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseRetrieveParams;
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
      stream.stream().forEach(AiCoreOpenAiClientTest::assertCreateResponseStreaming);
    }
  }

  @Test
  void testRetrieveResponseServiceCall() {
    final var responseService = client.responses();
    final var params =
        ResponseRetrieveParams.builder()
            .responseId("resp_07bcc4ce7d57e28d006a19115b9848819588503af93015f462")
            .build();

    final var response = responseService.retrieve(params);

    assertThat(response).isNotNull();
    assertRetrieveResponse(response);
  }

  @Test
  void testCancelResponseServiceCall() {
    final var responseService = client.responses();
    final var params =
        ResponseCancelParams.builder()
            .responseId("resp_0960af38c651a868006a1911b68a448194b0918d31bfa6f586")
            .build();

    final var response = responseService.cancel(params);

    assertThat(response).isNotNull();
    assertCancelResponse(response);
  }

  @Test
  void testDeleteResponseServiceCall() {
    final var responseService = client.responses();
    final var params =
        ResponseDeleteParams.builder()
            .responseId("resp_07bcc4ce7d57e28d006a19115b9848819588503af93015f462")
            .build();

    assertThatNoException().isThrownBy(() -> responseService.delete(params));
    verify(
        deleteRequestedFor(
            urlMatching("/responses/resp_07bcc4ce7d57e28d006a19115b9848819588503af93015f462")));
  }

  static void assertRetrieveResponse(@Nonnull final Response response) {
    assertThat(response.isValid()).isTrue();
    assertThat(response.id()).isEqualTo("resp_07bcc4ce7d57e28d006a19115b9848819588503af93015f462");
    assertThat(response.model().asString()).isEqualTo("gpt-5-2025-08-07-dz-std");
    assertThat(response.status()).contains(ResponseStatus.COMPLETED);
    assertThat(response.background()).contains(false);
    assertThat(response.reasoning())
        .hasValueSatisfying(r -> assertThat(r.effort()).contains(ReasoningEffort.MEDIUM));

    final var usage = response.usage().orElseThrow();
    assertThat(usage.inputTokens()).isEqualTo(9L);
    assertThat(usage.outputTokens()).isEqualTo(120L);
    assertThat(usage.totalTokens()).isEqualTo(129L);

    final var output = response.output();
    assertThat(output).isNotNull();
    final var item1 = output.get(0);
    assertThat(item1.isReasoning()).isTrue();
    final var item2 = output.get(1);
    assertThat(item2.isMessage()).isTrue();
    assertThat(item2.asMessage().content()).isNotEmpty();
    final ResponseOutputMessage.Content content = item2.asMessage().content().get(0);
    assertThat(content.isOutputText()).isTrue();
    assertThat(content.asOutputText().text()).isEqualTo("Hi! How can I help you today?");
  }

  static void assertCancelResponse(@Nonnull final Response response) {
    assertThat(response.isValid()).isTrue();
    assertThat(response.id()).isEqualTo("resp_0960af38c651a868006a1911b68a448194b0918d31bfa6f586");
    assertThat(response.model().toString()).contains("gpt-5");
    assertThat(response.status()).contains(ResponseStatus.CANCELLED);
    assertThat(response.background()).contains(true);
    assertThat(response.reasoning())
        .hasValueSatisfying(r -> assertThat(r.effort()).contains(ReasoningEffort.HIGH));

    assertThat(response.maxOutputTokens()).contains(8000L);
    assertThat(response.usage()).isEmpty();

    assertThat(response.output()).isEmpty();
  }

  static void assertCreateResponse(@Nonnull final Response response) {
    assertThat(response.isValid()).isTrue();
    assertThat(response.model().asString()).isEqualTo("gpt-5-2025-08-07-dz-std");
    assertThat(response.status()).contains(ResponseStatus.COMPLETED);
    assertThat(response.reasoning())
        .hasValueSatisfying(r -> assertThat(r.effort()).contains(ReasoningEffort.MEDIUM));

    final var usage = response.usage().orElseThrow();
    assertThat(usage.inputTokens()).isEqualTo(13L);
    assertThat(usage.outputTokens()).isEqualTo(59L);
    assertThat(usage.totalTokens()).isEqualTo(72L);

    final var output = response.output();
    assertThat(output).isNotNull();
    final var item1 = output.get(0);
    assertThat(item1.isReasoning()).isTrue();
    final var item2 = output.get(1);
    assertThat(item2.isMessage()).isTrue();
    assertThat(item2.asMessage().content()).isNotEmpty();
    final ResponseOutputMessage.Content content = item2.asMessage().content().get(0);
    assertThat(content.isOutputText()).isTrue();
    assertThat(content.asOutputText().text()).isEqualTo("Paris.");
  }

  static void assertCreateResponseStreaming(@Nonnull final ResponseStreamEvent event) {
    assertThat(event.isValid()).isTrue();

    if (event.isCreated()) {
      assertThat(event.asCreated().response()).isNotNull();
      assertThat(event.asCreated().response().model().asString())
          .isEqualTo("gpt-5-2025-08-07-dz-std");
      assertThat(event.asCreated().response().status()).contains(ResponseStatus.IN_PROGRESS);
      assertThat(event.asCreated().response().reasoning())
          .hasValueSatisfying(r -> assertThat(r.effort()).contains(ReasoningEffort.MEDIUM));
    }

    if (event.isOutputTextDelta()) {
      final var delta = event.asOutputTextDelta();
      assertThat(delta.delta()).isNotBlank();
    }

    if (event.isCompleted()) {
      final var completed = event.asCompleted().response();

      final var usage = completed.usage().orElseThrow();
      assertThat(usage.inputTokens()).isEqualTo(17L);
      assertThat(usage.outputTokens()).isEqualTo(195L);
      assertThat(usage.totalTokens()).isEqualTo(212L);

      final var output = completed.output();
      assertThat(output).isNotNull();
      final var item1 = output.get(0);
      assertThat(item1.isReasoning()).isTrue();
      final var item2 = output.get(1);
      assertThat(item2.isMessage()).isTrue();
      assertThat(item2.asMessage().content()).isNotEmpty();
      final ResponseOutputMessage.Content content = item2.asMessage().content().get(0);
      assertThat(content.isOutputText()).isTrue();
      assertThat(content.asOutputText().text()).contains("lemonade");
    }
  }
}
