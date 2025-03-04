package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200ResponseDataInner;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200ResponseUsage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

class EmbeddingModelTest {
  private OpenAiSpringEmbeddingModel model;
  private OpenAiClient client;

  @BeforeEach
  void setUp() {
    client = mock(OpenAiClient.class);
    model = new OpenAiSpringEmbeddingModel();
  }

  @Test
  void testCall() {
    var request =
        new EmbeddingRequest(
            List.of("instructions"),
            EmbeddingOptionsBuilder.builder().withModel("model").withDimensions(128).build());

    var vector = new float[] {0.0f};
    var expectedResponse =
        new EmbeddingResponse(
            List.of(new Embedding(vector, 0)),
            new EmbeddingResponseMetadata("model", new DefaultUsage(0, null, 0)));

    var openAiResponse =
        new EmbeddingsCreate200Response()
            .data(List.of(new EmbeddingsCreate200ResponseDataInner().embedding(vector)))
            .model("model")
            .usage(new EmbeddingsCreate200ResponseUsage().promptTokens(0).totalTokens(0));

    when(client.embedding(any(EmbeddingsCreateRequest.class))).thenReturn(openAiResponse);

    try (var mockedStatic = mockStatic(OpenAiClient.class)) {
      mockedStatic.when(() -> OpenAiClient.forModel(any())).thenReturn(client);

      var actualResponse = model.call(request);

      assertThat(expectedResponse).usingRecursiveComparison().isEqualTo(actualResponse);
    }
  }
}
