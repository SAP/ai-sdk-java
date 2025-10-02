package com.sap.ai.sdk.orchestration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.OrchestrationEmbeddingModel.TEXT_EMBEDDING_3_SMALL;
import static com.sap.ai.sdk.orchestration.model.DPIEntities.PERSON;
import static com.sap.ai.sdk.orchestration.model.EmbeddingResult.ObjectEnum.EMBEDDING;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.DOCUMENT;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.QUERY;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.TEXT;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsResponse.ObjectEnum.LIST;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams.EncodingFormatEnum;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class OrchestrationEmbeddingTest {
  private static OrchestrationClient client;

  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationClient(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testEmbeddingModel() {
    final var model =
        TEXT_EMBEDDING_3_SMALL.withVersion("v1").withDimensions(1536).withNormalize(true);

    final var lowLevelModel1 = model.createEmbeddingsModelDetails();
    assertThat(lowLevelModel1.getName()).isEqualTo("text-embedding-3-small");
    assertThat(lowLevelModel1.getVersion()).isEqualTo("v1");
    assertThat(lowLevelModel1.getParams().getDimensions()).isEqualTo(1536);
    assertThat(lowLevelModel1.getParams().isNormalize()).isTrue();
    assertThat(lowLevelModel1.getParams().getEncodingFormat()).isEqualTo(EncodingFormatEnum.FLOAT);

    final var model2 = new OrchestrationEmbeddingModel("custom-model");
    final var lowLevelModel2 = model2.createEmbeddingsModelDetails();
    assertThat(lowLevelModel2.getName()).isEqualTo("custom-model");
  }

  @Test
  void testEmbeddingRequestTokenTypes() {
    var request =
        OrchestrationEmbeddingRequest.forModel(TEXT_EMBEDDING_3_SMALL).forInputs("Hello World");

    request = request.asText();
    var lowLevelRequest = request.createEmbeddingsPostRequest();
    assertThat(lowLevelRequest.getInput().getType()).isEqualTo(TEXT);

    request = request.asDocument();
    lowLevelRequest = request.createEmbeddingsPostRequest();
    assertThat(lowLevelRequest.getInput().getType()).isEqualTo(DOCUMENT);

    request = request.asQuery();
    lowLevelRequest = request.createEmbeddingsPostRequest();
    assertThat(lowLevelRequest.getInput().getType()).isEqualTo(QUERY);
  }

  @SneakyThrows
  @Test
  void testEmbeddingRequest() {
    stubFor(
        post(urlPathEqualTo("/v2/embeddings"))
            .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
            .willReturn(
                aResponse()
                    .withBodyFile("embeddingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var masking =
        DpiMasking.anonymization().withEntities(PERSON).withAllowList(List.of("SAP", "Joule"));
    final var request =
        OrchestrationEmbeddingRequest.forModel(TEXT_EMBEDDING_3_SMALL)
            .forInputs(List.of("Hi SAP Orchestration Service", "I am John Doe"))
            .withMasking(masking);

    final var response = client.embed(request);

    assertThat(response.getEmbeddingVectors())
        .isNotNull()
        .hasSize(2)
        .isInstanceOf(List.class)
        .contains(
            new float[] {-0.09068491f, -0.3462946f, 0.88297224f, -0.29537824f, 0.0704844f},
            new float[] {0.18703203f, -0.10362422f, -0.65176725f, 0.6386932f, -0.34864223f});

    assertThat(response.getOriginalResponse().getRequestId())
        .isEqualTo("62935941-7c2d-4c16-8a35-0b9dce7c8c9e");
    assertThat(response.getOriginalResponse().getIntermediateResults().getInputMasking().getData())
        .isEqualTo(
            Map.of("masked_input", List.of("Hi SAP Orchestration Service", "I am MASKED_PERSON")));

    final var finalResult = response.getOriginalResponse().getFinalResult();
    assertThat(finalResult.getModel()).isEqualTo("text-embedding-3-small");
    assertThat(finalResult.getUsage().getPromptTokens()).isEqualTo(11);
    assertThat(finalResult.getUsage().getTotalTokens()).isEqualTo(11);
    assertThat(finalResult.getObject()).isEqualTo(LIST);
    assertThat(finalResult.getData())
        .hasSize(2)
        .allSatisfy(
            embeddingData -> {
              assertThat(embeddingData.getObject()).isEqualTo(EMBEDDING);
              assertThat(embeddingData.getEmbedding()).isNotNull();
              assertThat(embeddingData.getIndex()).isIn(0, 1);
            });

    final var moduleResults = response.getOriginalResponse().getIntermediateResults();
    assertThat(moduleResults).isNotNull();
    assertThat(moduleResults.getInputMasking()).isNotNull();
    assertThat(moduleResults.getInputMasking().getMessage())
        .isEqualTo("Embedding input is masked successfully.");
    assertThat(moduleResults.getInputMasking().getData()).isNotNull();
    assertThat(moduleResults.getInputMasking().getData())
        .isEqualTo(
            Map.of("masked_input", List.of("Hi SAP Orchestration Service", "I am MASKED_PERSON")));

    try (var inputStream = fileLoader.apply("embeddingRequest.json")) {
      var requestJson = new String(inputStream.readAllBytes());
      verify(
          postRequestedFor(urlEqualTo("/v2/embeddings")).withRequestBody(equalToJson(requestJson)));
    }
  }
}
