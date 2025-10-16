package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.List;
import java.util.function.Consumer;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;

class EmbeddingModelTest {
  private OpenAiClient client;

  @BeforeEach
  void setUp() {
    client = mock(OpenAiClient.class);
  }

  @SneakyThrows
  @Test
  @DisplayName("Call with embedding request containing valid options")
  void testCallWithValidEmbeddingRequest() {
    val texts = List.of("Some text");
    val springAiRequest =
        new EmbeddingRequest(texts, EmbeddingOptionsBuilder.builder().withDimensions(128).build());

    val expectedOpenAiResponse =
        new ObjectMapper()
            .readValue(
                getClass().getClassLoader().getResource("__files/embeddingResponse.json"),
                EmbeddingsCreate200Response.class);

    val expectedOpenAiRequest =
        new EmbeddingsCreateRequest()
            .input(EmbeddingsCreateRequestInput.createListOfStrings(texts))
            .dimensions(128);

    when(client.embedding(assertArg(assertRecursiveEquals(expectedOpenAiRequest))))
        .thenReturn(expectedOpenAiResponse);

    val actualSpringAiResponse = new OpenAiSpringEmbeddingModel(client).call(springAiRequest);

    assertThat(actualSpringAiResponse).isNotNull();
    assertThat(actualSpringAiResponse.getResult().getOutput())
        .isEqualTo(new float[] {0.0f, 3.4028235E38f, 1.4E-45f, 1.23f, -4.56f});
    assertThat(actualSpringAiResponse.getMetadata().getUsage().getPromptTokens()).isEqualTo(2);
    assertThat(actualSpringAiResponse.getMetadata().getUsage().getTotalTokens()).isEqualTo(2);
    assertThat(actualSpringAiResponse.getMetadata().getModel()).isEqualTo("ada");
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
            "Do not set a model in EmbeddingOptions, as the OpenAiClient already defines the model.");
  }

  @SneakyThrows
  @Test
  @DisplayName("Embed document with text content")
  void testEmbedDocument() {
    Document document = new Document("Some content");

    val expectedOpenAiResponse =
        new ObjectMapper()
            .readValue(
                getClass().getClassLoader().getResource("__files/embeddingResponse.json"),
                EmbeddingsCreate200Response.class);

    val docs = List.of(document.getFormattedContent());
    val expectedOpenAiRequest =
        new EmbeddingsCreateRequest().input(EmbeddingsCreateRequestInput.createListOfStrings(docs));

    when(client.embedding(assertArg(assertRecursiveEquals(expectedOpenAiRequest))))
        .thenReturn(expectedOpenAiResponse);

    float[] result = new OpenAiSpringEmbeddingModel(client).embed(document);

    assertThat(result).isEqualTo(new float[] {0.0f, 3.4028235E38f, 1.4E-45f, 1.23f, -4.56f});
  }

  private static <T> Consumer<T> assertRecursiveEquals(T expected) {
    return (actual) -> {
      assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    };
  }
}
