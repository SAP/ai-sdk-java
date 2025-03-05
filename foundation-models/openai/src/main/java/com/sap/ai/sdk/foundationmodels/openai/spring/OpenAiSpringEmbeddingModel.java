package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

/**
 * SpringAI integration with {@link OpenAiClient} to generate embeddings.
 *
 * <p>This model transforms an {@link EmbeddingRequest} into an Azure OpenAI request, processes the
 * response, and returns a Spring AI {@link EmbeddingResponse}.
 *
 * @since 1.5.0
 */
public class OpenAiSpringEmbeddingModel implements EmbeddingModel {

  private final OpenAiClient client;

  /**
   * Constructs an {@code OpenAiSpringEmbeddingModel} with the specified {@link OpenAiClient} of
   * some model.
   *
   * @param client the OpenAI client
   */
  public OpenAiSpringEmbeddingModel(@Nonnull final OpenAiClient client) {
    this.client = client;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalArgumentException if {@code request.getOptions().getModel()} is not null.
   */
  @Override
  @Nonnull
  public EmbeddingResponse call(@Nonnull final EmbeddingRequest request)
      throws IllegalArgumentException {

    if (request.getOptions().getModel() != null) {
      throw new IllegalArgumentException(
          "Invalid EmbeddingRequest: the model option must be null, as the client already defines the model.");
    }

    final var openAiRequest = createEmbeddingCreateRequest(request);
    final var openAiResponse = client.embedding(openAiRequest);

    return createSpringAiEmbeddingResponse(openAiResponse);
  }

  @Override
  @Nonnull
  public float[] embed(@Nonnull final Document document) throws UnsupportedOperationException {
    if (document.isText()) {
      return embed(Objects.requireNonNull(document.getText(), "Document is missing text content"));
    }
    throw new UnsupportedOperationException("Only text type document supported for embedding");
  }

  private EmbeddingsCreateRequest createEmbeddingCreateRequest(
      @Nonnull final EmbeddingRequest request) {
    return new EmbeddingsCreateRequest()
        .dimensions(request.getOptions().getDimensions())
        .input(EmbeddingsCreateRequestInput.create(request.getInstructions()));
  }

  private EmbeddingResponse createSpringAiEmbeddingResponse(
      @Nonnull final EmbeddingsCreate200Response response) {
    final var embeddings =
        IntStream.range(0, response.getData().size())
            .mapToObj(i -> new Embedding(response.getData().get(i).getEmbedding(), i))
            .toList();

    final var openAiUsage = response.getUsage();
    final var usage =
        new DefaultUsage(openAiUsage.getPromptTokens(), null, openAiUsage.getTotalTokens());
    final var metadata = new EmbeddingResponseMetadata(response.getModel(), usage);

    return new EmbeddingResponse(embeddings, metadata);
  }
}
