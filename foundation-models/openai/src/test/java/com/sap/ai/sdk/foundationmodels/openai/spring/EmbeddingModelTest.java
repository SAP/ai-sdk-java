package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200ResponseDataInner;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200ResponseUsage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.List;
import java.util.function.Consumer;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

class EmbeddingModelTest {
  private OpenAiClient client;

  @BeforeEach
  void setUp() {
    client = mock(OpenAiClient.class);
  }

  @Test
  @DisplayName("Call with embedding request containing valid options")
  void testCallWithValidEmbeddingRequest() {
    val texts = List.of("Some text");
    val springAiRequest =
        new EmbeddingRequest(texts, EmbeddingOptionsBuilder.builder().withDimensions(128).build());

    val vector = new float[] {0.0f};
    val expectedOpenAiResponse =
        new EmbeddingsCreate200Response()
            .data(List.of(new EmbeddingsCreate200ResponseDataInner().embedding(vector)))
            .usage(new EmbeddingsCreate200ResponseUsage().promptTokens(0).totalTokens(0));

    val expectedOpenAiRequest =
        new EmbeddingsCreateRequest()
            .input(EmbeddingsCreateRequestInput.create(texts))
            .dimensions(128);

    when(client.embedding(assertArg(assertRecursiveEquals(expectedOpenAiRequest))))
        .thenReturn(expectedOpenAiResponse);

    val actualSpringAiResponse = new OpenAiSpringEmbeddingModel(client).call(springAiRequest);

    val modelName = ""; // defined by client object and options not honoured
    val expectedSpringAiResponse =
        new EmbeddingResponse(
            List.of(new Embedding(vector, 0)),
            new EmbeddingResponseMetadata(modelName, new DefaultUsage(0, null, 0)));

    assertThat(expectedSpringAiResponse)
        .usingRecursiveComparison()
        .isEqualTo(actualSpringAiResponse);
  }

  @Test
  @DisplayName("Call with embedding request with model option set throws exception")
  void testCallWithModelOptionSetThrows() {
    val springAiRequest =
        new EmbeddingRequest(
            List.of("Some text"), EmbeddingOptionsBuilder.builder().withModel("model").build());

    val model = new OpenAiSpringEmbeddingModel(client);

    assertThatThrownBy(() -> model.call(springAiRequest))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "Invalid EmbeddingRequest: the model option must be null, as the client already defines the model.");
  }

  @Test
  @DisplayName("Embed document with text content")
  void testEmbedDocument() {
    Document document = new Document("Some content");

    val vector = new float[] {1, 2, 3};
    val openAiResponse =
        new EmbeddingsCreate200Response()
            .data(List.of(new EmbeddingsCreate200ResponseDataInner().embedding(vector)))
            .usage(new EmbeddingsCreate200ResponseUsage().promptTokens(0).totalTokens(0));

    val expectedOpenAiRequest =
        new EmbeddingsCreateRequest()
            .input(EmbeddingsCreateRequestInput.create(List.of(document.getText())));

    when(client.embedding(assertArg(assertRecursiveEquals(expectedOpenAiRequest))))
        .thenReturn(openAiResponse);

    float[] result = new OpenAiSpringEmbeddingModel(client).embed(document);

    assertThat(result).isEqualTo(new float[] {1, 2, 3});
  }

  @Test
  @DisplayName("Embed document with missing text content throws exception")
  void testEmbedNonTextDocumentThrows() {
    val document = mock(Document.class);
    when(document.isText()).thenReturn(false);

    val model = new OpenAiSpringEmbeddingModel(client);

    assertThatThrownBy(() -> model.embed(document))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Only text type document supported for embedding");
  }

  private static <T> Consumer<T> assertRecursiveEquals(T expected) {
    return (actual) -> {
      assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    };
  }
}
