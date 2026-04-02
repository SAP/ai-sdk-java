package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStatus;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@WireMockTest
class AiCoreOpenAiClientTest {

  private OpenAIClient client;

  @BeforeEach
  void setup(@Nonnull final WireMockRuntimeInfo server) {
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = AiCoreOpenAiClient.fromDestination(destination, OpenAiModel.GPT_5);

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
  void testResponseServiceSuccessWithoutModel() {
    final var params =
        ResponseCreateParams.builder().input("What is the capital of France?").build();
    final Response response = client.responses().create(params);

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

    assertThatThrownBy(() -> client.responses().create(params))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Model mismatch")
        .hasMessageContaining("gpt-5")
        .hasMessageContaining("gpt-4");
  }

  @Test
  void testResponseServiceWithOptions() {
    final var service = client.responses();

    final var modifiedService =
        service.withOptions(
            builder -> {
              // Modify some option
              builder.putHeader("X-Custom-Header", "test-value");
            });

    assertThat(modifiedService).isInstanceOf(AiCoreResponseService.class);
  }

  @Test
  void testChatCompletionServiceSuccessWithMatchingModel() {
    final var params =
        ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_5)
            .addUserMessage("Say this is a test")
            .build();

    final ChatCompletion response = client.chat().completions().create(params);
    assertThat(response).isNotNull();
  }

  @Test
  @Disabled("Fails as Async client needs additional wrappers. Maintenance wall.")
  void testAsyncChatCompletion() {
    final var params =
        ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_5)
            .addUserMessage("Say this is a test")
            .build();

    final CompletableFuture<ChatCompletion> future =
        client.async().chat().completions().create(params);

    final ChatCompletion response = future.join();

    assertThat(response).isNotNull();
    assertThat(response.choices()).isNotEmpty();
  }
}
