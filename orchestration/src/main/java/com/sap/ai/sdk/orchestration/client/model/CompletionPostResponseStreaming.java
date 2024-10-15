/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** CompletionPostResponseStreaming */
// CHECKSTYLE:OFF
public class CompletionPostResponseStreaming
// CHECKSTYLE:ON
{
  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("module_results")
  private ModuleResults moduleResults;

  @JsonProperty("orchestration_result")
  private LLMModuleResultStreaming orchestrationResult;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected CompletionPostResponseStreaming() {}

  /**
   * Set the requestId of this {@link CompletionPostResponseStreaming} instance and return the same
   * instance.
   *
   * @param requestId ID of the request
   * @return The same instance of this {@link CompletionPostResponseStreaming} class
   */
  @Nonnull
  public CompletionPostResponseStreaming requestId(@Nonnull final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * ID of the request
   *
   * @return requestId The requestId of this {@link CompletionPostResponseStreaming} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link CompletionPostResponseStreaming} instance.
   *
   * @param requestId ID of the request
   */
  public void setRequestId(@Nonnull final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the moduleResults of this {@link CompletionPostResponseStreaming} instance and return the
   * same instance.
   *
   * @param moduleResults The moduleResults of this {@link CompletionPostResponseStreaming}
   * @return The same instance of this {@link CompletionPostResponseStreaming} class
   */
  @Nonnull
  public CompletionPostResponseStreaming moduleResults(
      @Nullable final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
    return this;
  }

  /**
   * Get moduleResults
   *
   * @return moduleResults The moduleResults of this {@link CompletionPostResponseStreaming}
   *     instance.
   */
  @Nonnull
  public ModuleResults getModuleResults() {
    return moduleResults;
  }

  /**
   * Set the moduleResults of this {@link CompletionPostResponseStreaming} instance.
   *
   * @param moduleResults The moduleResults of this {@link CompletionPostResponseStreaming}
   */
  public void setModuleResults(@Nullable final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
  }

  /**
   * Set the orchestrationResult of this {@link CompletionPostResponseStreaming} instance and return
   * the same instance.
   *
   * @param orchestrationResult The orchestrationResult of this {@link
   *     CompletionPostResponseStreaming}
   * @return The same instance of this {@link CompletionPostResponseStreaming} class
   */
  @Nonnull
  public CompletionPostResponseStreaming orchestrationResult(
      @Nullable final LLMModuleResultStreaming orchestrationResult) {
    this.orchestrationResult = orchestrationResult;
    return this;
  }

  /**
   * Get orchestrationResult
   *
   * @return orchestrationResult The orchestrationResult of this {@link
   *     CompletionPostResponseStreaming} instance.
   */
  @Nonnull
  public LLMModuleResultStreaming getOrchestrationResult() {
    return orchestrationResult;
  }

  /**
   * Set the orchestrationResult of this {@link CompletionPostResponseStreaming} instance.
   *
   * @param orchestrationResult The orchestrationResult of this {@link
   *     CompletionPostResponseStreaming}
   */
  public void setOrchestrationResult(@Nullable final LLMModuleResultStreaming orchestrationResult) {
    this.orchestrationResult = orchestrationResult;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link CompletionPostResponseStreaming}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link CompletionPostResponseStreaming}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "CompletionPostResponseStreaming has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link CompletionPostResponseStreaming} instance. If the
   * map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final CompletionPostResponseStreaming completionPostResponseStreaming =
        (CompletionPostResponseStreaming) o;
    return Objects.equals(
            this.cloudSdkCustomFields, completionPostResponseStreaming.cloudSdkCustomFields)
        && Objects.equals(this.requestId, completionPostResponseStreaming.requestId)
        && Objects.equals(this.moduleResults, completionPostResponseStreaming.moduleResults)
        && Objects.equals(
            this.orchestrationResult, completionPostResponseStreaming.orchestrationResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, moduleResults, orchestrationResult, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CompletionPostResponseStreaming {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    moduleResults: ").append(toIndentedString(moduleResults)).append("\n");
    sb.append("    orchestrationResult: ")
        .append(toIndentedString(orchestrationResult))
        .append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * CompletionPostResponseStreaming} instance with all required arguments.
   */
  public static Builder create() {
    return (requestId) -> new CompletionPostResponseStreaming().requestId(requestId);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the requestId of this {@link CompletionPostResponseStreaming} instance.
     *
     * @param requestId ID of the request
     * @return The CompletionPostResponseStreaming instance.
     */
    CompletionPostResponseStreaming requestId(@Nonnull final String requestId);
  }
}
