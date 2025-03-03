package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;

/**
 * Represents a request to create embeddings using OpenAI's API.
 *
 * <p>A high-level wrapper over the generated model class {@code EmbeddingsCreateRequest}, *
 * improving API usability for common use cases such as creation from a list of tokens.
 *
 * @since 1.4.0
 */
@Beta
@Value
@With
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OpenAiEmbeddingRequest {
  /** List of tokens to be embedded. */
  @Nonnull private final List<String> tokens;

  /** Number of dimensions of the embedding. */
  @Nullable private final Integer dimensions;

  /**
   * Constructs an OpenAiEmbeddingRequest from a list of strings.
   *
   * @param tokens a list of tokens to be embedded
   */
  @Tolerate
  public OpenAiEmbeddingRequest(@Nonnull final List<String> tokens) {
    this(Collections.unmodifiableList(tokens), null);
  }

  /**
   * Converts this request to an EmbeddingsCreateRequest.
   *
   * @return an EmbeddingsCreateRequest with the tokens to be embedded
   */
  @Nonnull
  EmbeddingsCreateRequest createEmbeddingsCreateRequest() {
    if (tokens.size() == 1) {
      return new EmbeddingsCreateRequest()
          .input(EmbeddingsCreateRequestInput.create(tokens.get(0)));
    }

    return new EmbeddingsCreateRequest().input(EmbeddingsCreateRequestInput.create(tokens));
  }
}
