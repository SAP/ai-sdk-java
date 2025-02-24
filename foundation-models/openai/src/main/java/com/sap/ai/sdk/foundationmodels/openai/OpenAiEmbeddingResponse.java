package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;

/**
 * Represents a response from the OpenAI Embedding API.
 *
 * <p>A high-level wrapper over the generated model class {@code EmbeddingsCreate200Response},
 * improving API usability for common use cases, such as extracting embeddings.
 *
 * @since 1.4.0
 */
@Beta
@Value
@AllArgsConstructor(access = PACKAGE)
@Setter(value = NONE)
public class OpenAiEmbeddingResponse {

  /** The original response from the OpenAI Embedding API. */
  @Nonnull EmbeddingsCreate200Response originalResponse;

  /**
   * Read the embeddings from the response as a list of float arrays.
   *
   * @return a list of float arrays
   */
  @Nonnull
  public List<float[]> getEmbeddings() {
    final var embeddings = new ArrayList<float[]>();
    for (final var container : originalResponse.getData()) {

      final var embeddingDecimals = container.getEmbedding();
      final var embeddingFloats = new float[embeddingDecimals.size()];

      for (int i = 0; i < embeddingDecimals.size(); i++) {
        embeddingFloats[i] = embeddingDecimals.get(i).floatValue();
      }
      embeddings.add(embeddingFloats);
    }
    return embeddings;
  }
}
