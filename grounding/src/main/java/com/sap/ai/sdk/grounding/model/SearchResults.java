/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 * The version of the OpenAPI document: 0.1.0
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
import com.google.common.annotations.Beta;
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

/** SearchResults */
@Beta // CHECKSTYLE:OFF
public class SearchResults
// CHECKSTYLE:ON
{
  @JsonProperty("results")
  private List<ResultsInner> results = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for SearchResults. */
  protected SearchResults() {}

  /**
   * Set the results of this {@link SearchResults} instance and return the same instance.
   *
   * @param results List of returned results.
   * @return The same instance of this {@link SearchResults} class
   */
  @Nonnull
  public SearchResults results(@Nonnull final List<ResultsInner> results) {
    this.results = results;
    return this;
  }

  /**
   * Add one results instance to this {@link SearchResults}.
   *
   * @param resultsItem The results that should be added
   * @return The same instance of type {@link SearchResults}
   */
  @Nonnull
  public SearchResults addResultsItem(@Nonnull final ResultsInner resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * List of returned results.
   *
   * @return results The results of this {@link SearchResults} instance.
   */
  @Nonnull
  public List<ResultsInner> getResults() {
    return results;
  }

  /**
   * Set the results of this {@link SearchResults} instance.
   *
   * @param results List of returned results.
   */
  public void setResults(@Nonnull final List<ResultsInner> results) {
    this.results = results;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link SearchResults}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link SearchResults} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("SearchResults has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link SearchResults} instance. If the map previously
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
    final SearchResults searchResults = (SearchResults) o;
    return Objects.equals(this.cloudSdkCustomFields, searchResults.cloudSdkCustomFields)
        && Objects.equals(this.results, searchResults.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(results, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class SearchResults {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link SearchResults} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (results) -> new SearchResults().results(results);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the results of this {@link SearchResults} instance.
     *
     * @param results List of returned results.
     * @return The SearchResults instance.
     */
    SearchResults results(@Nonnull final List<ResultsInner> results);

    /**
     * Set the results of this {@link SearchResults} instance.
     *
     * @param results List of returned results.
     * @return The SearchResults instance.
     */
    default SearchResults results(@Nonnull final ResultsInner... results) {
      return results(Arrays.asList(results));
    }
  }
}
