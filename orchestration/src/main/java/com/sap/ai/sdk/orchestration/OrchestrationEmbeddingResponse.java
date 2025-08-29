package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.Embedding;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Response wrapper for orchestration embedding operations.
 *
 * <p>Wraps {@link EmbeddingsPostResponse} and provides convenient access to embedding vectors.
 *
 * @since 1.11.0
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
    final var embeddings = new ArrayList<float[]>();
    for (final var container : originalResponse.getFinalResult().getData()) {
      final var bigDecimals = (Embedding.InnerBigDecimals) container.getEmbedding();
      final var values = bigDecimals.values();
      final float[] arr = new float[values.size()];
      for (int i = 0; i < values.size(); i++) {
        arr[i] = values.get(i).floatValue();
      }
      embeddings.add(arr);
    }
    return embeddings;
  }
}
