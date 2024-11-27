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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Usage of tokens in the response */
@Beta // CHECKSTYLE:OFF
public class TokenUsage
// CHECKSTYLE:ON
{
  @JsonProperty("completion_tokens")
  private Integer completionTokens;

  @JsonProperty("prompt_tokens")
  private Integer promptTokens;

  @JsonProperty("total_tokens")
  private Integer totalTokens;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TokenUsage. */
  protected TokenUsage() {}

  /**
   * Set the completionTokens of this {@link TokenUsage} instance and return the same instance.
   *
   * @param completionTokens Number of tokens used in the input
   * @return The same instance of this {@link TokenUsage} class
   */
  @Nonnull
  public TokenUsage completionTokens(@Nonnull final Integer completionTokens) {
    this.completionTokens = completionTokens;
    return this;
  }

  /**
   * Number of tokens used in the input
   *
   * @return completionTokens The completionTokens of this {@link TokenUsage} instance.
   */
  @Nonnull
  public Integer getCompletionTokens() {
    return completionTokens;
  }

  /**
   * Set the completionTokens of this {@link TokenUsage} instance.
   *
   * @param completionTokens Number of tokens used in the input
   */
  public void setCompletionTokens(@Nonnull final Integer completionTokens) {
    this.completionTokens = completionTokens;
  }

  /**
   * Set the promptTokens of this {@link TokenUsage} instance and return the same instance.
   *
   * @param promptTokens Number of tokens used in the output
   * @return The same instance of this {@link TokenUsage} class
   */
  @Nonnull
  public TokenUsage promptTokens(@Nonnull final Integer promptTokens) {
    this.promptTokens = promptTokens;
    return this;
  }

  /**
   * Number of tokens used in the output
   *
   * @return promptTokens The promptTokens of this {@link TokenUsage} instance.
   */
  @Nonnull
  public Integer getPromptTokens() {
    return promptTokens;
  }

  /**
   * Set the promptTokens of this {@link TokenUsage} instance.
   *
   * @param promptTokens Number of tokens used in the output
   */
  public void setPromptTokens(@Nonnull final Integer promptTokens) {
    this.promptTokens = promptTokens;
  }

  /**
   * Set the totalTokens of this {@link TokenUsage} instance and return the same instance.
   *
   * @param totalTokens Total number of tokens used
   * @return The same instance of this {@link TokenUsage} class
   */
  @Nonnull
  public TokenUsage totalTokens(@Nonnull final Integer totalTokens) {
    this.totalTokens = totalTokens;
    return this;
  }

  /**
   * Total number of tokens used
   *
   * @return totalTokens The totalTokens of this {@link TokenUsage} instance.
   */
  @Nonnull
  public Integer getTotalTokens() {
    return totalTokens;
  }

  /**
   * Set the totalTokens of this {@link TokenUsage} instance.
   *
   * @param totalTokens Total number of tokens used
   */
  public void setTotalTokens(@Nonnull final Integer totalTokens) {
    this.totalTokens = totalTokens;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TokenUsage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TokenUsage} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TokenUsage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TokenUsage} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
    cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }

  @Override
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final TokenUsage tokenUsage = (TokenUsage) o;
    return Objects.equals(this.cloudSdkCustomFields, tokenUsage.cloudSdkCustomFields)
        && Objects.equals(this.completionTokens, tokenUsage.completionTokens)
        && Objects.equals(this.promptTokens, tokenUsage.promptTokens)
        && Objects.equals(this.totalTokens, tokenUsage.totalTokens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(completionTokens, promptTokens, totalTokens, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TokenUsage {\n");
    sb.append("    completionTokens: ").append(toIndentedString(completionTokens)).append("\n");
    sb.append("    promptTokens: ").append(toIndentedString(promptTokens)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link TokenUsage} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (completionTokens) ->
        (promptTokens) ->
            (totalTokens) ->
                new TokenUsage()
                    .completionTokens(completionTokens)
                    .promptTokens(promptTokens)
                    .totalTokens(totalTokens);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the completionTokens of this {@link TokenUsage} instance.
     *
     * @param completionTokens Number of tokens used in the input
     * @return The TokenUsage builder.
     */
    Builder1 completionTokens(@Nonnull final Integer completionTokens);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the promptTokens of this {@link TokenUsage} instance.
     *
     * @param promptTokens Number of tokens used in the output
     * @return The TokenUsage builder.
     */
    Builder2 promptTokens(@Nonnull final Integer promptTokens);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the totalTokens of this {@link TokenUsage} instance.
     *
     * @param totalTokens Total number of tokens used
     * @return The TokenUsage instance.
     */
    TokenUsage totalTokens(@Nonnull final Integer totalTokens);
  }
}
