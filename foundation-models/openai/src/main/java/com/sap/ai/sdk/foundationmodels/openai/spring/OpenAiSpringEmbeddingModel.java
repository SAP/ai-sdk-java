package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
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

  @Nonnull private final OpenAiClient client;
  @Nonnull private final MetadataMode metadataMode;

  /**
   * Constructs an {@code OpenAiSpringEmbeddingModel} with the specified {@link OpenAiClient} of
   * some model.
   *
   * @param client the OpenAI client
   */
  public OpenAiSpringEmbeddingModel(@Nonnull final OpenAiClient client) {
    this(client, MetadataMode.EMBED);
  }

  /**
   * Constructs an {@code OpenAiSpringEmbeddingModel} with the specified {@link OpenAiClient} of
   * some model and metadata mode.
   *
   * <p>The metadata mode is used by content formatter to determine which metadata to include in the
   * resulting content. Currently, the formatter is only effective for calls to {@link
   * #embed(Document)}.
   *
   * @param client the OpenAI client
   * @param metadataMode the metadata mode
   */
  public OpenAiSpringEmbeddingModel(
      @Nonnull final OpenAiClient client, @Nonnull final MetadataMode metadataMode) {
    this.client = client;
    this.metadataMode = metadataMode;
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
    final var openAiRequest = createEmbeddingsCreateRequest(request);
    final var openAiResponse = client.embedding(openAiRequest);

    return createSpringAiEmbeddingResponse(openAiResponse);
  }

  @Override
  @Nonnull
  public float[] embed(@Nonnull final Document document) throws UnsupportedOperationException {
    return embed(
        Objects.requireNonNull(
            document.getFormattedContent(this.metadataMode),
            "Formatted content of the document should not be null."));
  }

  private EmbeddingsCreateRequest createEmbeddingsCreateRequest(
      @Nonnull final EmbeddingRequest request) {

    final var options = Optional.ofNullable(request.getOptions());
    if (options.map(EmbeddingOptions::getModel).isPresent()) {
      throw new IllegalArgumentException(
          "Do not set a model in EmbeddingOptions, as the OpenAiClient already defines the model.");
    }

    return new EmbeddingsCreateRequest()
        .dimensions(options.map(EmbeddingOptions::getDimensions).orElse(null))
        .input(EmbeddingsCreateRequestInput.createListOfStrings(request.getInstructions()));
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
