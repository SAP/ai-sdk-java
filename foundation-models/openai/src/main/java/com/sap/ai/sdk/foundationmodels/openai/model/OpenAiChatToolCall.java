package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI tool call by AI. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiChatToolCall {
  /** The ID of the tool call. */
  @JsonProperty("id")
  @Getter(onMethod_ = @Nonnull)
  private String id;

  /** The type of the tool. */
  @JsonProperty("type")
  @Getter(onMethod_ = @Nonnull)
  private String type; // equals "function"

  /** The function that the model called. */
  @JsonProperty("function")
  @Getter(onMethod_ = @Nonnull)
  private OpenAiChatFunctionCall function;
}
