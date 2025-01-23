package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI embedding output. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiEmbeddingOutput {
  /** List object. */
  @JsonProperty("object")
  @Getter(onMethod_ = @Nonnull)
  private String object;

  /** Model used for embedding. */
  @JsonProperty("model")
  @Getter(onMethod_ = @Nonnull)
  private String model;

  /** Array of result candidates. */
  @JsonProperty("data")
  @Getter(onMethod_ = @Nonnull)
  private List<OpenAiEmbeddingData> data;

  /** Token Usage. */
  @JsonProperty("usage")
  @Getter(onMethod_ = @Nonnull)
  private OpenAiUsage usage;
}
