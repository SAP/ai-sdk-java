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
  void createEmbeddingRequestWithMultipleTokens() {
    var request = new OpenAiEmbeddingRequest(List.of("token1", "token2", "token3"));
    var lowLevelRequest = request.createEmbeddingsCreateRequest();

    assertThat(((EmbeddingsCreateRequestInput.ListOfStrings) lowLevelRequest.getInput()).values())
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

    var embeddings = new OpenAiEmbeddingResponse(originalResponse).getEmbeddingVectors();

    assertThat(embeddings).isInstanceOf(List.class);
    assertThat(embeddings).hasSize(1);
    assertThat(embeddings)
        .containsExactly(new float[] {0.0f, 3.4028235E38f, 1.4E-45f, 1.23f, -4.56f});
  }
}
