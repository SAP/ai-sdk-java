package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI function signature. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
public class OpenAiChatCompletionFunction {
  /**
   * Name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes,
   * with a maximum length of 64.
   */
  @JsonProperty("name")
  @Setter(onParam_ = @Nullable)
  private String name;

  /** Description of the function. */
  @JsonProperty("description")
  @Setter(onParam_ = @Nullable)
  private String description;

  /**
   * JSON Schema for the function input parameters.
   *
   * <p><b>Note</b>: As mentioned by <a
   * href="https://community.openai.com/t/whitch-json-schema-version-should-function-calling-use/283535/4">OpenAI</a>,
   * it follows JSON Schema 7 (2020-12). Not all JSON Schema parameters in the specification are
   * supported by OpenAI.
   */
  @JsonProperty("parameters")
  @Setter(onParam_ = @Nullable)
  private Map<String, Object> parameters;
}
