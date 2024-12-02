/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.36.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** CompletionPostResponse */
@Beta // CHECKSTYLE:OFF
public class CompletionPostResponse
// CHECKSTYLE:ON
{
  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("module_results")
  private ModuleResults moduleResults;

  @JsonProperty("orchestration_result")
  private LLMModuleResult orchestrationResult;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for CompletionPostResponse. */
  protected CompletionPostResponse() {}

  /**
   * Set the requestId of this {@link CompletionPostResponse} instance and return the same instance.
   *
   * @param requestId ID of the request
   * @return The same instance of this {@link CompletionPostResponse} class
   */
  @Nonnull
  public CompletionPostResponse requestId(@Nonnull final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * ID of the request
   *
   * @return requestId The requestId of this {@link CompletionPostResponse} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link CompletionPostResponse} instance.
   *
   * @param requestId ID of the request
   */
  public void setRequestId(@Nonnull final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the moduleResults of this {@link CompletionPostResponse} instance and return the same
   * instance.
   *
   * @param moduleResults The moduleResults of this {@link CompletionPostResponse}
   * @return The same instance of this {@link CompletionPostResponse} class
   */
  @Nonnull
  public CompletionPostResponse moduleResults(@Nonnull final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
    return this;
  }

  /**
   * Get moduleResults
   *
   * @return moduleResults The moduleResults of this {@link CompletionPostResponse} instance.
   */
  @Nonnull
  public ModuleResults getModuleResults() {
    return moduleResults;
  }

  /**
   * Set the moduleResults of this {@link CompletionPostResponse} instance.
   *
   * @param moduleResults The moduleResults of this {@link CompletionPostResponse}
   */
  public void setModuleResults(@Nonnull final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
  }

  /**
   * Set the orchestrationResult of this {@link CompletionPostResponse} instance and return the same
   * instance.
   *
   * @param orchestrationResult The orchestrationResult of this {@link CompletionPostResponse}
   * @return The same instance of this {@link CompletionPostResponse} class
   */
  @Nonnull
  public CompletionPostResponse orchestrationResult(
      @Nonnull final LLMModuleResult orchestrationResult) {
    this.orchestrationResult = orchestrationResult;
    return this;
  }

  /**
   * Get orchestrationResult
   *
   * @return orchestrationResult The orchestrationResult of this {@link CompletionPostResponse}
   *     instance.
   */
  @Nonnull
  public LLMModuleResult getOrchestrationResult() {
    return orchestrationResult;
  }

  /**
   * Set the orchestrationResult of this {@link CompletionPostResponse} instance.
   *
   * @param orchestrationResult The orchestrationResult of this {@link CompletionPostResponse}
   */
  public void setOrchestrationResult(@Nonnull final LLMModuleResult orchestrationResult) {
    this.orchestrationResult = orchestrationResult;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link CompletionPostResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link CompletionPostResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "CompletionPostResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link CompletionPostResponse} instance. If the map
   * previously contained a mapping for the key, the old value is replaced by the specified value.
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
    final CompletionPostResponse completionPostResponse = (CompletionPostResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, completionPostResponse.cloudSdkCustomFields)
        && Objects.equals(this.requestId, completionPostResponse.requestId)
        && Objects.equals(this.moduleResults, completionPostResponse.moduleResults)
        && Objects.equals(this.orchestrationResult, completionPostResponse.orchestrationResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, moduleResults, orchestrationResult, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CompletionPostResponse {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link CompletionPostResponse}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (requestId) ->
        (moduleResults) ->
            (orchestrationResult) ->
                new CompletionPostResponse()
                    .requestId(requestId)
                    .moduleResults(moduleResults)
                    .orchestrationResult(orchestrationResult);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the requestId of this {@link CompletionPostResponse} instance.
     *
     * @param requestId ID of the request
     * @return The CompletionPostResponse builder.
     */
    Builder1 requestId(@Nonnull final String requestId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the moduleResults of this {@link CompletionPostResponse} instance.
     *
     * @param moduleResults The moduleResults of this {@link CompletionPostResponse}
     * @return The CompletionPostResponse builder.
     */
    Builder2 moduleResults(@Nonnull final ModuleResults moduleResults);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the orchestrationResult of this {@link CompletionPostResponse} instance.
     *
     * @param orchestrationResult The orchestrationResult of this {@link CompletionPostResponse}
     * @return The CompletionPostResponse instance.
     */
    CompletionPostResponse orchestrationResult(@Nonnull final LLMModuleResult orchestrationResult);
  }
}