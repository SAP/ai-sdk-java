/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.29.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

/** Usage of tokens in the response */
@JsonPropertyOrder({
  TokenUsage.JSON_PROPERTY_COMPLETION_TOKENS,
  TokenUsage.JSON_PROPERTY_PROMPT_TOKENS,
  TokenUsage.JSON_PROPERTY_TOTAL_TOKENS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T18:02:22.585601+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class TokenUsage {
  public static final String JSON_PROPERTY_COMPLETION_TOKENS = "completion_tokens";
  public static final String JSON_PROPERTY_PROMPT_TOKENS = "prompt_tokens";
  public static final String JSON_PROPERTY_TOTAL_TOKENS = "total_tokens";
  private Integer completionTokens;
  private Integer promptTokens;
  private Integer totalTokens;

  public TokenUsage() {}

  /** Create a builder with no initialized field. */
  public static TokenUsage.Builder builder() {
    return new TokenUsage.Builder();
  }

  public TokenUsage completionTokens(Integer completionTokens) {

    this.completionTokens = completionTokens;
    return this;
  }

  /**
   * Number of tokens used in the input
   *
   * @return completionTokens
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COMPLETION_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getCompletionTokens() {
    return completionTokens;
  }

  @JsonProperty(JSON_PROPERTY_COMPLETION_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCompletionTokens(Integer completionTokens) {
    this.completionTokens = completionTokens;
  }

  public TokenUsage promptTokens(Integer promptTokens) {

    this.promptTokens = promptTokens;
    return this;
  }

  /**
   * Number of tokens used in the output
   *
   * @return promptTokens
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PROMPT_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getPromptTokens() {
    return promptTokens;
  }

  @JsonProperty(JSON_PROPERTY_PROMPT_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPromptTokens(Integer promptTokens) {
    this.promptTokens = promptTokens;
  }

  public TokenUsage totalTokens(Integer totalTokens) {

    this.totalTokens = totalTokens;
    return this;
  }

  /**
   * Total number of tokens used
   *
   * @return totalTokens
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TOTAL_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getTotalTokens() {
    return totalTokens;
  }

  @JsonProperty(JSON_PROPERTY_TOTAL_TOKENS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalTokens(Integer totalTokens) {
    this.totalTokens = totalTokens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenUsage tokenUsage = (TokenUsage) o;
    return Objects.equals(this.completionTokens, tokenUsage.completionTokens)
        && Objects.equals(this.promptTokens, tokenUsage.promptTokens)
        && Objects.equals(this.totalTokens, tokenUsage.totalTokens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(completionTokens, promptTokens, totalTokens);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenUsage {\n");
    sb.append("    completionTokens: ").append(toIndentedString(completionTokens)).append("\n");
    sb.append("    promptTokens: ").append(toIndentedString(promptTokens)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /** Create a builder with a shallow copy of this instance. */
  public TokenUsage.Builder toBuilder() {
    return new TokenUsage.Builder()
        .completionTokens(getCompletionTokens())
        .promptTokens(getPromptTokens())
        .totalTokens(getTotalTokens());
  }

  public static class Builder {

    private TokenUsage instance;

    public Builder() {
      this(new TokenUsage());
    }

    protected Builder(TokenUsage instance) {
      this.instance = instance;
    }

    public TokenUsage.Builder completionTokens(Integer completionTokens) {
      this.instance.completionTokens = completionTokens;
      return this;
    }

    public TokenUsage.Builder promptTokens(Integer promptTokens) {
      this.instance.promptTokens = promptTokens;
      return this;
    }

    public TokenUsage.Builder totalTokens(Integer totalTokens) {
      this.instance.totalTokens = totalTokens;
      return this;
    }

    /**
     * returns a built TokenUsage instance.
     *
     * <p>The builder is not reusable.
     */
    public TokenUsage build() {
      try {
        return this.instance;
      } finally {
        // ensure that this.instance is not reused
        this.instance = null;
      }
    }

    @Override
    public String toString() {
      return getClass() + "=(" + instance + ")";
    }
  }
}
