/*
 * Grounding
 * Grounding is a service designed to handle data-related tasks, such as grounding and retrieval, using vector databases. It provides specialized data retrieval through these databases, grounding the retrieval process with your own external and context-relevant data. Grounding combines generative AI capabilities with the ability to use real-time, precise data to improve decision-making and business operations for specific AI-driven business solutions.
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ResultsInner */
// CHECKSTYLE:OFF
public class ResultsInner
// CHECKSTYLE:ON
{
  @JsonProperty("filterId")
  private String filterId;

  @JsonProperty("results")
  private List<DocumentsChunk> results = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ResultsInner. */
  protected ResultsInner() {}

  /**
   * Set the filterId of this {@link ResultsInner} instance and return the same instance.
   *
   * @param filterId The filterId of this {@link ResultsInner}
   * @return The same instance of this {@link ResultsInner} class
   */
  @Nonnull
  public ResultsInner filterId(@Nonnull final String filterId) {
    this.filterId = filterId;
    return this;
  }

  /**
   * Get filterId
   *
   * @return filterId The filterId of this {@link ResultsInner} instance.
   */
  @Nonnull
  public String getFilterId() {
    return filterId;
  }

  /**
   * Set the filterId of this {@link ResultsInner} instance.
   *
   * @param filterId The filterId of this {@link ResultsInner}
   */
  public void setFilterId(@Nonnull final String filterId) {
    this.filterId = filterId;
  }

  /**
   * Set the results of this {@link ResultsInner} instance and return the same instance.
   *
   * @param results The results of this {@link ResultsInner}
   * @return The same instance of this {@link ResultsInner} class
   */
  @Nonnull
  public ResultsInner results(@Nonnull final List<DocumentsChunk> results) {
    this.results = results;
    return this;
  }

  /**
   * Add one results instance to this {@link ResultsInner}.
   *
   * @param resultsItem The results that should be added
   * @return The same instance of type {@link ResultsInner}
   */
  @Nonnull
  public ResultsInner addResultsItem(@Nonnull final DocumentsChunk resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   *
   * @return results The results of this {@link ResultsInner} instance.
   */
  @Nonnull
  public List<DocumentsChunk> getResults() {
    return results;
  }

  /**
   * Set the results of this {@link ResultsInner} instance.
   *
   * @param results The results of this {@link ResultsInner}
   */
  public void setResults(@Nonnull final List<DocumentsChunk> results) {
    this.results = results;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ResultsInner}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ResultsInner} instance.
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
      throw new NoSuchElementException("ResultsInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ResultsInner} instance including unrecognized
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
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ResultsInner} instance. If the map previously
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
    final ResultsInner resultsInner = (ResultsInner) o;
    return Objects.equals(this.cloudSdkCustomFields, resultsInner.cloudSdkCustomFields)
        && Objects.equals(this.filterId, resultsInner.filterId)
        && Objects.equals(this.results, resultsInner.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filterId, results, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResultsInner {\n");
    sb.append("    filterId: ").append(toIndentedString(filterId)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ResultsInner} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (filterId) -> (results) -> new ResultsInner().filterId(filterId).results(results);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the filterId of this {@link ResultsInner} instance.
     *
     * @param filterId The filterId of this {@link ResultsInner}
     * @return The ResultsInner builder.
     */
    Builder1 filterId(@Nonnull final String filterId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the results of this {@link ResultsInner} instance.
     *
     * @param results The results of this {@link ResultsInner}
     * @return The ResultsInner instance.
     */
    ResultsInner results(@Nonnull final List<DocumentsChunk> results);

    /**
     * Set the results of this {@link ResultsInner} instance.
     *
     * @param results The results of this {@link ResultsInner}
     * @return The ResultsInner instance.
     */
    default ResultsInner results(@Nonnull final DocumentsChunk... results) {
      return results(Arrays.asList(results));
    }
  }
}
