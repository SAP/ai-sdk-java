/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ResultsInner1 */
// CHECKSTYLE:OFF
public class ResultsInner1
// CHECKSTYLE:ON
{
  @JsonProperty("filterId")
  private String filterId;

  @JsonProperty("results")
  private List<RetievalDataRepositorySearchResult> results = new ArrayList<>();

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ResultsInner1. */
  protected ResultsInner1() {}

  /**
   * Set the filterId of this {@link ResultsInner1} instance and return the same instance.
   *
   * @param filterId The filterId of this {@link ResultsInner1}
   * @return The same instance of this {@link ResultsInner1} class
   */
  @Nonnull
  public ResultsInner1 filterId(@Nonnull final String filterId) {
    this.filterId = filterId;
    return this;
  }

  /**
   * Get filterId
   *
   * @return filterId The filterId of this {@link ResultsInner1} instance.
   */
  @Nonnull
  public String getFilterId() {
    return filterId;
  }

  /**
   * Set the filterId of this {@link ResultsInner1} instance.
   *
   * @param filterId The filterId of this {@link ResultsInner1}
   */
  public void setFilterId(@Nonnull final String filterId) {
    this.filterId = filterId;
  }

  /**
   * Set the results of this {@link ResultsInner1} instance and return the same instance.
   *
   * @param results List of returned results.
   * @return The same instance of this {@link ResultsInner1} class
   */
  @Nonnull
  public ResultsInner1 results(@Nullable final List<RetievalDataRepositorySearchResult> results) {
    this.results = results;
    return this;
  }

  /**
   * Add one results instance to this {@link ResultsInner1}.
   *
   * @param resultsItem The results that should be added
   * @return The same instance of type {@link ResultsInner1}
   */
  @Nonnull
  public ResultsInner1 addResultsItem(
      @Nonnull final RetievalDataRepositorySearchResult resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * List of returned results.
   *
   * @return results The results of this {@link ResultsInner1} instance.
   */
  @Nonnull
  public List<RetievalDataRepositorySearchResult> getResults() {
    return results;
  }

  /**
   * Set the results of this {@link ResultsInner1} instance.
   *
   * @param results List of returned results.
   */
  public void setResults(@Nullable final List<RetievalDataRepositorySearchResult> results) {
    this.results = results;
  }

  /**
   * Set the message of this {@link ResultsInner1} instance and return the same instance.
   *
   * @param message The message of this {@link ResultsInner1}
   * @return The same instance of this {@link ResultsInner1} class
   */
  @Nonnull
  public ResultsInner1 message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link ResultsInner1} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link ResultsInner1} instance.
   *
   * @param message The message of this {@link ResultsInner1}
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ResultsInner1}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ResultsInner1} instance.
   *
   * @deprecated Use {@link #toMap()} instead.
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  @Deprecated
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("ResultsInner1 has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ResultsInner1} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (filterId != null) declaredFields.put("filterId", filterId);
    if (results != null) declaredFields.put("results", results);
    if (message != null) declaredFields.put("message", message);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ResultsInner1} instance. If the map previously
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
    final ResultsInner1 resultsInner1 = (ResultsInner1) o;
    return Objects.equals(this.cloudSdkCustomFields, resultsInner1.cloudSdkCustomFields)
        && Objects.equals(this.filterId, resultsInner1.filterId)
        && Objects.equals(this.results, resultsInner1.results)
        && Objects.equals(this.message, resultsInner1.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filterId, results, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResultsInner1 {\n");
    sb.append("    filterId: ").append(toIndentedString(filterId)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ResultsInner1} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (filterId) -> (message) -> new ResultsInner1().filterId(filterId).message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the filterId of this {@link ResultsInner1} instance.
     *
     * @param filterId The filterId of this {@link ResultsInner1}
     * @return The ResultsInner1 builder.
     */
    Builder1 filterId(@Nonnull final String filterId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the message of this {@link ResultsInner1} instance.
     *
     * @param message The message of this {@link ResultsInner1}
     * @return The ResultsInner1 instance.
     */
    ResultsInner1 message(@Nonnull final String message);
  }
}
