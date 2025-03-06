package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiSpringEmbeddingModel;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

/** Service class for Spring AI integration with OpenAI */
@Service
public class SpringAiOpenAiService {

  private final OpenAiClient client = OpenAiClient.forModel(OpenAiModel.TEXT_EMBEDDING_3_SMALL);

  /**
   * Embeds a list of strings using the OpenAI embedding model.
   *
   * @return an {@code EmbeddingResponse} containing the embeddings and metadata
   */
  @Nonnull
  public EmbeddingResponse embedStrings() {
    final var options = EmbeddingOptionsBuilder.builder().withDimensions(128).build();
    final var springAiRequest =
        new EmbeddingRequest(List.of("The quick brown fox jumps over the lazy dog."), options);

    return new OpenAiSpringEmbeddingModel(client).call(springAiRequest);
  }

  /**
   * Embeds the content of a document using the OpenAI embedding model.
   *
   * @return a float array representing the embedding of the document's content
   */
  @Nonnull
  public float[] embedDocument() {
    final var document = new Document("The quick brown fox jumps over the lazy dog.");
    return new OpenAiSpringEmbeddingModel(client).embed(document);
  }
}
