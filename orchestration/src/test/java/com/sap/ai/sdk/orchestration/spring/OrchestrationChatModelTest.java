package com.sap.ai.sdk.orchestration.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO_16K;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.Prompt;

@WireMockTest
public class OrchestrationChatModelTest {

  private static OrchestrationChatModel client;
  private static OrchestrationModuleConfig config;
  private static Prompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationChatModel(new OrchestrationClient(destination));
    config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO_16K);
    prompt =
        new Prompt(
            "Hello World! Why is this phrase so famous?", new OrchestrationChatOptions(config));
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testCompletion() {
    stubFor(
        post(urlPathEqualTo("/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var result = client.call(prompt);

    assertThat(result).isNotNull();
    assertThat(result.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testThrowsOnMissingChatOptions() {
    assertThatThrownBy(() -> client.call(new Prompt("test")))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please add OrchestrationChatOptions to the Prompt");
  }

  @Test
  void testThrowsOnMissingLlmConfig() {
    OrchestrationChatOptions emptyConfig =
        new OrchestrationChatOptions(new OrchestrationModuleConfig());

    assertThatThrownBy(() -> client.call(new Prompt("test", emptyConfig)))
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM config is required");
  }
}
