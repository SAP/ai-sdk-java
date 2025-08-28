package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelDetails;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

// ideally this is a record but exposes all args constructor which we want to avoid. Is it worth a
// value class?
// Currently, model list follow SAP Notes as the source of truth. But deprecated models are not
// listed there.
// Can we reuse existing enum from generated class?
@Beta
@With
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationEmbeddingModel implements AiModel {
  @Nonnull String name;
  @Nullable String version;
  @Nullable Integer dimensions;
  @Nullable Boolean normalize;
  @Nullable EmbeddingsModelParams.EncodingFormatEnum encodingFormat;

  public OrchestrationEmbeddingModel(@Nonnull final String name) {
    this(name, null, null, null, null);
  }

  public static final OrchestrationEmbeddingModel TEXT_EMBEDDING_3_SMALL =
      new OrchestrationEmbeddingModel("text-embedding-3-small");

  public static final OrchestrationEmbeddingModel TEXT_EMBEDDING_3_LARGE =
      new OrchestrationEmbeddingModel("text-embedding-3-large");

  public static final OrchestrationEmbeddingModel AMAZON_TITAN_EMBED_TEXT =
      new OrchestrationEmbeddingModel("amazon.titan-embed-text");

  public static final OrchestrationEmbeddingModel NVIDIA_LLAMA_32_NV_EMBEDQA_1B =
      new OrchestrationEmbeddingModel("nvidia--llama-3.2-nv-embedqa-1b");

  EmbeddingsModelDetails createEmbeddingsModelDetails() {

    final var params =
        EmbeddingsModelParams.create()
            .dimensions(dimensions)
            .normalize(normalize)
            .encodingFormat(encodingFormat);
    return EmbeddingsModelDetails.create().name(name).version(version).params(params);
  }
}
