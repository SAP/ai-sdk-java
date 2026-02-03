package com.sap.ai.sdk.orchestration.spring;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationEmbeddingModel;
import com.sap.ai.sdk.orchestration.OrchestrationEmbeddingRequest;
import com.sap.ai.sdk.orchestration.OrchestrationEmbeddingResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
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
 * A Spring-based implementation of the {@link EmbeddingModel} interface that integrates with the
 * Orchestration SDK to provide embedding functionality.
 *
 * @since 1.13.0
 */
@Beta
@RequiredArgsConstructor
public class OrchestrationSpringEmbeddingModel implements EmbeddingModel {

  /**
   * Default embedding options to provide model name and other parameters.
   *
   * <p>Can be overridden by options in the request.
   *
   * @see OrchestrationSpringEmbeddingModel#call(EmbeddingRequest)
   */
  @Nonnull private final EmbeddingOptions defaultOptions;

  /** Client for interacting with the Orchestration SDK. */
  @Nonnull private final OrchestrationClient client;

  /** Metadata mode to determine how document metadata is handled. */
  @Nonnull private final MetadataMode metadataMode;

  /**
   * Constructs an instance with default options, a new {@link OrchestrationClient}, and sets the
   * metadata mode to {@link MetadataMode#EMBED}.
   *
   * @param defaultOptions Default embedding options.
   */
  public OrchestrationSpringEmbeddingModel(@Nonnull final EmbeddingOptions defaultOptions) {
    this(defaultOptions, new OrchestrationClient(), MetadataMode.EMBED);
  }

  /**
   * Calls the embedding model with the given request and returns the response.
   *
   * <p>Note: The request's options takes precedence over the defaultOptions.
   *
   * @param request The embedding request containing input texts and options.
   * @return The embedding response containing results and metadata.
   */
  @Override
  @Nonnull
  public EmbeddingResponse call(@Nonnull final EmbeddingRequest request) {
    final var orchestrationRequest = createOrchestrationEmbeddingRequest(request);
    final var orchestrationResponse = client.embed(orchestrationRequest);
    return createSpringAiEmbeddingResponse(orchestrationResponse);
  }

  @Override
  @Nonnull
  public float[] embed(@Nonnull final Document document) {
    return embed(document.getFormattedContent(this.metadataMode));
  }

  @Override
  @Nonnull
  public List<float[]> embed(@Nonnull final List<String> texts) {
    // Propagate defaultOptions instead of incomplete options in default method implementation
    final var response = this.call(new EmbeddingRequest(texts, this.defaultOptions));
    return response.getResults().stream().map(Embedding::getOutput).toList();
  }

  @Nonnull
  private OrchestrationEmbeddingRequest createOrchestrationEmbeddingRequest(
      @Nonnull final EmbeddingRequest request) {
    final var options = Objects.requireNonNullElse(request.getOptions(), defaultOptions);
    final var modelName =
        Objects.requireNonNull(options.getModel(), "EmbeddingOptions must provide the model name");
    final var model =
        new OrchestrationEmbeddingModel(modelName).withDimensions(options.getDimensions());
    return OrchestrationEmbeddingRequest.forModel(model).forInputs(request.getInstructions());
  }

  @Nonnull
  private EmbeddingResponse createSpringAiEmbeddingResponse(
      @Nonnull final OrchestrationEmbeddingResponse response) {
    final var embeddings =
        IntStream.range(0, response.getEmbeddingVectors().size())
            .mapToObj(i -> new Embedding(response.getEmbeddingVectors().get(i), i))
            .toList();
    final var finalResult = response.getOriginalResponse().getFinalResult();
    final var orchUsage = finalResult.getUsage();
    final var usage =
        new DefaultUsage(orchUsage.getPromptTokens(), null, orchUsage.getTotalTokens());
    final var metadata = new EmbeddingResponseMetadata(finalResult.getModel(), usage);

    return new EmbeddingResponse(embeddings, metadata);
  }
}
