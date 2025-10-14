package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.Embedding.ArrayOfFloats;
import com.sap.ai.sdk.orchestration.model.EmbeddingResult;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Response wrapper for orchestration embedding operations.
 *
 * <p>Wraps {@link EmbeddingsPostResponse} and provides convenient access to embedding vectors.
 *
 * @since 1.12.0
 */
@Beta
@Value
@AllArgsConstructor(access = PACKAGE)
public class OrchestrationEmbeddingResponse {

  /** The original embedding response from the orchestration API. */
  @Nonnull EmbeddingsPostResponse originalResponse;

  /**
   * Extracts embedding vectors as float arrays.
   *
   * @return list of embedding vectors, never {@code null}
   */
  @Nonnull
  public List<float[]> getEmbeddingVectors() {
    return originalResponse.getFinalResult().getData().stream()
        .map(EmbeddingResult::getEmbedding)
        .filter(ArrayOfFloats.class::isInstance)
        .map(ArrayOfFloats.class::cast)
        .map(ArrayOfFloats::values)
        .toList();
  }
}
