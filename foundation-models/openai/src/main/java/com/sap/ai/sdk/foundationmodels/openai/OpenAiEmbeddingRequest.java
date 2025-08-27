package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Value;

/**
 * Represents a request to create embeddings using OpenAI's API.
 *
 * <p>A high-level wrapper over the generated model class {@code EmbeddingsCreateRequest}, *
 * improving API usability for common use cases such as creation from a list of tokens.
 *
 * @since 1.4.0
 */
@Value
public class OpenAiEmbeddingRequest {
  /** List of tokens to be embedded. */
  @Nonnull List<String> tokens;

  /**
   * Constructs an OpenAiEmbeddingRequest from a list of strings.
   *
   * @param tokens a list of tokens to be embedded
   */
  public OpenAiEmbeddingRequest(@Nonnull final List<String> tokens) {
    this.tokens = Collections.unmodifiableList(tokens);
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
