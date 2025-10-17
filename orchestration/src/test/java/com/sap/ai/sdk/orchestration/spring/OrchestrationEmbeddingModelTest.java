package com.sap.ai.sdk.orchestration.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;

@WireMockTest
class OrchestrationEmbeddingModelTest {

  private static EmbeddingOptions options;
  private static OrchestrationSpringEmbeddingModel model;
  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    options = EmbeddingOptionsBuilder.builder().withModel("text-embedding-3-small").build();

    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    final var client = new OrchestrationClient(destination);
    model = new OrchestrationSpringEmbeddingModel(options, client, MetadataMode.EMBED);
  }

  @Test
  void testEmbeddingRequest() throws IOException {
    stubForEmbedding();

    final var inputText = "Hi SAP Orchestration Service";
    final var embeddingRequest = new EmbeddingRequest(List.of(inputText), null);
    final var response = model.call(embeddingRequest);

    assertThat(response.getResult().getOutput())
        .isEqualTo(
            new float[] {-0.003806071f, -0.01453408f, 0.037058588f, -0.012397106f, 0.0029582495f});
    assertThat(response.getMetadata().getModel()).isEqualTo(options.getModel());
    assertThat(response.getMetadata().getUsage().getPromptTokens()).isEqualTo(6);
    assertThat(response.getMetadata().getUsage().getTotalTokens()).isEqualTo(6);

    verifyEmbeddingRequest();
  }

  @Test
  void testEmbeddingText() throws IOException {
    stubForEmbedding();

    final var inputText = "Hi SAP Orchestration Service";
    final var response = model.embed(inputText);
    assertThat(response)
        .isEqualTo(
            new float[] {-0.003806071f, -0.01453408f, 0.037058588f, -0.012397106f, 0.0029582495f});

    verifyEmbeddingRequest();
  }

  @Test
  @Description("Tests that model must name must be set and request option precedes over default")
  void testEmbeddingWithMissingModelNameThrows() {
    final var request =
        new EmbeddingRequest(List.of("Hello World"), EmbeddingOptionsBuilder.builder().build());

    assertThatThrownBy(() -> model.call(request))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("EmbeddingOptions must provide the model name");
  }

  private void stubForEmbedding() {
    stubFor(
        post(urlEqualTo("/v2/embeddings"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("simpleEmbeddingResponse.json")));
  }

  private void verifyEmbeddingRequest() throws IOException {
    try (var inputStream = fileLoader.apply("simpleEmbeddingRequest.json")) {
      var requestJson = new String(inputStream.readAllBytes());
      verify(
          postRequestedFor(urlEqualTo("/v2/embeddings")).withRequestBody(equalToJson(requestJson)));
    }
  }
}
