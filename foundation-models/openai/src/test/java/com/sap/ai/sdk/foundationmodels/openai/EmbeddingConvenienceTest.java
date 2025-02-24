package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

@WireMockTest
class EmbeddingConvenienceTest {

  @Test
  void createEmbeddingRequestWithSingleToken() {
    var request = new OpenAiEmbeddingRequest("token1");
    var lowLevelRequest = request.createEmbeddingsCreateRequest();

    assertThat(((EmbeddingsCreateRequestInput.InnerString) lowLevelRequest.getInput()))
        .usingRecursiveComparison()
        .isEqualTo(EmbeddingsCreateRequestInput.create("token1"));
  }

  @Test
  void createEmbeddingRequestWithMultipleTokens() {
    var request = new OpenAiEmbeddingRequest(List.of("token1", "token2", "token3"));
    var lowLevelRequest = request.createEmbeddingsCreateRequest();

    assertThat(((EmbeddingsCreateRequestInput.InnerStrings) lowLevelRequest.getInput()).values())
        .containsExactly("token1", "token2", "token3");
  }

  @SneakyThrows
  @Test
  void getEmbeddings() {
    var originalResponse =
        OpenAiUtils.getOpenAiObjectMapper()
            .readValue(
                getClass().getClassLoader().getResource("__files/embeddingResponse.json"),
                EmbeddingsCreate200Response.class);

    var response = new OpenAiEmbeddingResponse(originalResponse);

    assertThat(response.getEmbeddings())
        .containsExactly(new float[] {0.0f, 3.4028235E38f, 1.4E-45f, 1.23f, -4.56f});
  }
}
