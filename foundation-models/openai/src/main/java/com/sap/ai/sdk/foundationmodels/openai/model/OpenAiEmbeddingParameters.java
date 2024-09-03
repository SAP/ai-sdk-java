package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI embedding input parameters. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiEmbeddingParameters {
  /**
   * Input text to get embeddings for, encoded as a string. The number of input tokens varies
   * depending on what model you are using. Unless you're embedding code, we suggest replacing
   * newlines (\n) in your input with a single space, as we have observed inferior results when
   * newlines are present.
   */
  @JsonProperty("input")
  @Setter(AccessLevel.NONE)
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<String> input;

  /**
   * A unique identifier representing your end-user. This will help Azure OpenAI monitor and detect
   * abuse. Do not pass PII identifiers instead use pseudo anonymized values such as GUIDs.
   */
  @JsonProperty("user")
  @Setter(onParam_ = @Nullable)
  private String user;

  /** input type of embedding search to use, e.g. "query". */
  @JsonProperty("input_type")
  @Setter(onParam_ = @Nullable)
  private String inputType;

  /**
   * Input text to get embeddings for, encoded as a string. The number of input tokens varies
   * depending on what model you are using. Unless you're embedding code, we suggest replacing
   * newlines (\n) in your input with a single space, as we have observed inferior results when
   * newlines are present.
   *
   * @param input Input text to get embeddings for, encoded as a string.
   * @return ${code this} instance for chaining.
   */
  @Nonnull
  public OpenAiEmbeddingParameters setInput(@Nonnull final String... input) {
    this.input = List.of(input);
    return this;
  }
}
