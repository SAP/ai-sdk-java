package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Result candidates for OpenAI embedding output. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiEmbeddingData {
  /** Embedding object. */
  @JsonProperty("object")
  @Getter(onMethod_ = @Nonnull)
  private String object;

  /** Array of size `1536` (OpenAI's embedding size) containing embedding vector. */
  @JsonProperty("embedding")
  @Getter(onMethod_ = @Nonnull)
  private List<Double> embedding;

  /** Index of choice. */
  @JsonProperty("index")
  @Getter(onMethod_ = @Nonnull)
  private Integer index;
}
