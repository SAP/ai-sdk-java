package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI tool signature. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiChatCompletionTool {
  /** Specifies a tool the model should use. Use to force the model to call a specific function. */
  @JsonProperty("type")
  @Setter(onParam_ = @Nonnull)
  private ToolType type;

  /** Use to force the model to call a specific function. */
  @JsonProperty("function")
  @Setter(onParam_ = @Nonnull)
  private OpenAiChatCompletionFunction function;

  /** The type of the tool. Currently, only `function` is supported. */
  // JSON payload sent: { "type": "function" }
  @RequiredArgsConstructor
  public enum ToolType {
    /**
     * Specifies a tool the model should use. Use to force the model to call a specific function.
     */
    FUNCTION("function");

    @JsonValue @Nonnull private final String value;
  }
}
