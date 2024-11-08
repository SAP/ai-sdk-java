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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** LLMChoice */
@JsonPropertyOrder({
  LLMChoice.JSON_PROPERTY_INDEX,
  LLMChoice.JSON_PROPERTY_MESSAGE,
  LLMChoice.JSON_PROPERTY_LOGPROBS,
  LLMChoice.JSON_PROPERTY_FINISH_REASON
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T17:17:51.177015+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class LLMChoice implements ModuleResultsOutputUnmaskingInner {
  public static final String JSON_PROPERTY_INDEX = "index";
  private Integer index;

  public static final String JSON_PROPERTY_MESSAGE = "message";
  private ChatMessage message;

  public static final String JSON_PROPERTY_LOGPROBS = "logprobs";
  private Map<String, List<BigDecimal>> logprobs = new HashMap<>();

  public static final String JSON_PROPERTY_FINISH_REASON = "finish_reason";
  private String finishReason;

  public LLMChoice() {}

  public LLMChoice index(Integer index) {

    this.index = index;
    return this;
  }

  /**
   * Index of the choice
   *
   * @return index
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getIndex() {
    return index;
  }

  @JsonProperty(JSON_PROPERTY_INDEX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndex(Integer index) {
    this.index = index;
  }

  public LLMChoice message(ChatMessage message) {

    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public ChatMessage getMessage() {
    return message;
  }

  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMessage(ChatMessage message) {
    this.message = message;
  }

  public LLMChoice logprobs(Map<String, List<BigDecimal>> logprobs) {

    this.logprobs = logprobs;
    return this;
  }

  public LLMChoice putLogprobsItem(String key, List<BigDecimal> logprobsItem) {
    if (this.logprobs == null) {
      this.logprobs = new HashMap<>();
    }
    this.logprobs.put(key, logprobsItem);
    return this;
  }

  /**
   * Log probabilities
   *
   * @return logprobs
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOGPROBS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, List<BigDecimal>> getLogprobs() {
    return logprobs;
  }

  @JsonProperty(JSON_PROPERTY_LOGPROBS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLogprobs(Map<String, List<BigDecimal>> logprobs) {
    this.logprobs = logprobs;
  }

  public LLMChoice finishReason(String finishReason) {

    this.finishReason = finishReason;
    return this;
  }

  /**
   * Reason the model stopped generating tokens. &#39;stop&#39; if the model hit a natural stop
   * point or a provided stop sequence, &#39;length&#39; if the maximum token number was reached,
   * &#39;content_filter&#39; if content was omitted due to a filter enforced by the LLM model
   * provider or the content filtering module
   *
   * @return finishReason
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_FINISH_REASON)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getFinishReason() {
    return finishReason;
  }

  @JsonProperty(JSON_PROPERTY_FINISH_REASON)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFinishReason(String finishReason) {
    this.finishReason = finishReason;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LLMChoice llMChoice = (LLMChoice) o;
    return Objects.equals(this.index, llMChoice.index)
        && Objects.equals(this.message, llMChoice.message)
        && Objects.equals(this.logprobs, llMChoice.logprobs)
        && Objects.equals(this.finishReason, llMChoice.finishReason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, message, logprobs, finishReason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LLMChoice {\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    logprobs: ").append(toIndentedString(logprobs)).append("\n");
    sb.append("    finishReason: ").append(toIndentedString(finishReason)).append("\n");
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

  public static class Builder {

    private LLMChoice instance;

    public Builder() {
      this(new LLMChoice());
    }

    protected Builder(LLMChoice instance) {
      this.instance = instance;
    }

    public LLMChoice.Builder index(Integer index) {
      this.instance.index = index;
      return this;
    }

    public LLMChoice.Builder message(ChatMessage message) {
      this.instance.message = message;
      return this;
    }

    public LLMChoice.Builder logprobs(Map<String, List<BigDecimal>> logprobs) {
      this.instance.logprobs = logprobs;
      return this;
    }

    public LLMChoice.Builder finishReason(String finishReason) {
      this.instance.finishReason = finishReason;
      return this;
    }

    /**
     * returns a built LLMChoice instance.
     *
     * <p>The builder is not reusable.
     */
    public LLMChoice build() {
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

  /** Create a builder with no initialized field. */
  public static LLMChoice.Builder builder() {
    return new LLMChoice.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public LLMChoice.Builder toBuilder() {
    return new LLMChoice.Builder()
        .index(getIndex())
        .message(getMessage())
        .logprobs(getLogprobs())
        .finishReason(getFinishReason());
  }
}
