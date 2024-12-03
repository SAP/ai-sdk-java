package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI completion output . */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiCompletionOutput {
  /** Creation date Unix timestamp. */
  @JsonProperty("created")
  @Getter(onMethod_ = @Nonnull)
  private Integer created;

  /** Unique ID for completion. */
  @JsonProperty("id")
  @Getter(onMethod_ = @Nonnull)
  private String id;

  /** Name of the model used for completion. */
  @JsonProperty("model")
  @Getter(onMethod_ = @Nonnull)
  private String model;

  /** Completion object. */
  @JsonProperty("object")
  @Getter(onMethod_ = @Nonnull)
  private String object; // Expected value "chat.completion"

  /** Token usage. */
  @JsonProperty("usage")
  @Getter(onMethod_ = @Nullable)
  private OpenAiUsage usage;

  /** Content filtering results for zero or more prompts in the request. */
  @JsonProperty("prompt_filter_results")
  @Getter(onMethod_ = @Nullable)
  private List<OpenAiPromptFilterResult> promptFilterResults;

  void addDelta(@Nonnull final OpenAiChatCompletionDelta delta) {
    created = delta.getCreated();
    id = delta.getId();
    model = delta.getModel();
    object = delta.getObject();

    if (delta.getUsage() != null) {
      if (usage == null) {
        usage = new OpenAiUsage();
      }
      usage.addDelta(delta.getUsage());
    }

    if (delta.getPromptFilterResults() != null && promptFilterResults == null) {
      promptFilterResults = delta.getPromptFilterResults();
      // prompt_filter_results is overriden instead of updated because it is only present once in
      // the first delta
    }
  }
}
