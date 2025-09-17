package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelDetails;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams.EncodingFormatEnum;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

/**
 * Configuration for embedding models in the Orchestration service.
 *
 * @since 1.12.0
 */
@Beta
@With
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationEmbeddingModel implements AiModel {
  /** The name of the embedding model. */
  @Nonnull String name;

  /** The version of the model, defaults to latest if not specified. */
  @Nullable String version;

  /** The number of dimensions for the output embeddings. */
  @Nullable Integer dimensions;

  /** Whether to normalize the embedding vectors. */
  @Nullable Boolean normalize;

  /** Azure OpenAI Text Embedding 3 Small model */
  public static final OrchestrationEmbeddingModel TEXT_EMBEDDING_3_SMALL =
      new OrchestrationEmbeddingModel("text-embedding-3-small");

  /** Azure OpenAI Text Embedding 3 Large model */
  public static final OrchestrationEmbeddingModel TEXT_EMBEDDING_3_LARGE =
      new OrchestrationEmbeddingModel("text-embedding-3-large");

  /** Amazon Titan Embed Text model */
  public static final OrchestrationEmbeddingModel AMAZON_TITAN_EMBED_TEXT =
      new OrchestrationEmbeddingModel("amazon--titan-embed-text");

  /** NVIDIA LLaMA 3.2 7B NV EmbedQA model */
  public static final OrchestrationEmbeddingModel NVIDIA_LLAMA_32_NV_EMBEDQA_1B =
      new OrchestrationEmbeddingModel("nvidia--llama-3.2-nv-embedqa-1b");

  /**
   * Creates a new embedding model configuration with the specified name.
   *
   * @param name the model name
   */
  public OrchestrationEmbeddingModel(@Nonnull final String name) {
    this(name, null, null, null);
  }

  @Nonnull
  EmbeddingsModelDetails createEmbeddingsModelDetails() {
    final var params =
        EmbeddingsModelParams.create()
            .dimensions(dimensions)
            .normalize(normalize)
            .encodingFormat(EncodingFormatEnum.FLOAT);
    return EmbeddingsModelDetails.create().name(name).version(version).params(params);
  }
}
