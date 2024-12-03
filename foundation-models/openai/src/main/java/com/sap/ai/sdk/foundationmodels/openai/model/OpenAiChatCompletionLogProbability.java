package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;

/** Log probability information for the choice. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiChatCompletionLogProbability {
  /** A list of message content tokens with log probability information. */
  @JsonProperty("content")
  @Setter(onParam_ = @Nullable)
  private List<OpenAiChatCompletionLogProbabilityTop> content;
}
