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

/** RetrievalSearchInput */
@Beta // CHECKSTYLE:OFF
public class RetrievalSearchInput
// CHECKSTYLE:ON
{
  @JsonProperty("query")
  private String query;

  @JsonProperty("filters")
  private List<RetrievalSearchFilter> filters = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RetrievalSearchInput. */
  protected RetrievalSearchInput() {}

  /**
   * Set the query of this {@link RetrievalSearchInput} instance and return the same instance.
   *
   * @param query Query string
   * @return The same instance of this {@link RetrievalSearchInput} class
   */
  @Nonnull
  public RetrievalSearchInput query(@Nonnull final String query) {
    this.query = query;
    return this;
  }

  /**
   * Query string
   *
   * @return query The query of this {@link RetrievalSearchInput} instance.
   */
  @Nonnull
  public String getQuery() {
    return query;
  }

  /**
   * Set the query of this {@link RetrievalSearchInput} instance.
   *
   * @param query Query string
   */
  public void setQuery(@Nonnull final String query) {
    this.query = query;
  }

  /**
   * Set the filters of this {@link RetrievalSearchInput} instance and return the same instance.
   *
   * @param filters The filters of this {@link RetrievalSearchInput}
   * @return The same instance of this {@link RetrievalSearchInput} class
   */
  @Nonnull
  public RetrievalSearchInput filters(@Nonnull final List<RetrievalSearchFilter> filters) {
    this.filters = filters;
    return this;
  }

  /**
   * Add one filters instance to this {@link RetrievalSearchInput}.
   *
   * @param filtersItem The filters that should be added
   * @return The same instance of type {@link RetrievalSearchInput}
   */
  @Nonnull
  public RetrievalSearchInput addFiltersItem(@Nonnull final RetrievalSearchFilter filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   *
   * @return filters The filters of this {@link RetrievalSearchInput} instance.
   */
  @Nonnull
  public List<RetrievalSearchFilter> getFilters() {
    return filters;
  }

  /**
   * Set the filters of this {@link RetrievalSearchInput} instance.
   *
   * @param filters The filters of this {@link RetrievalSearchInput}
   */
  public void setFilters(@Nonnull final List<RetrievalSearchFilter> filters) {
    this.filters = filters;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RetrievalSearchInput}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RetrievalSearchInput} instance.
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
      throw new NoSuchElementException(
          "RetrievalSearchInput has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link RetrievalSearchInput} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (query != null) declaredFields.put("query", query);
    if (filters != null) declaredFields.put("filters", filters);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link RetrievalSearchInput} instance. If the map
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
    final RetrievalSearchInput retrievalSearchInput = (RetrievalSearchInput) o;
    return Objects.equals(this.cloudSdkCustomFields, retrievalSearchInput.cloudSdkCustomFields)
        && Objects.equals(this.query, retrievalSearchInput.query)
        && Objects.equals(this.filters, retrievalSearchInput.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(query, filters, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RetrievalSearchInput {\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link RetrievalSearchInput}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (query) -> (filters) -> new RetrievalSearchInput().query(query).filters(filters);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the query of this {@link RetrievalSearchInput} instance.
     *
     * @param query Query string
     * @return The RetrievalSearchInput builder.
     */
    Builder1 query(@Nonnull final String query);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the filters of this {@link RetrievalSearchInput} instance.
     *
     * @param filters The filters of this {@link RetrievalSearchInput}
     * @return The RetrievalSearchInput instance.
     */
    RetrievalSearchInput filters(@Nonnull final List<RetrievalSearchFilter> filters);

    /**
     * Set the filters of this {@link RetrievalSearchInput} instance.
     *
     * @param filters The filters of this {@link RetrievalSearchInput}
     * @return The RetrievalSearchInput instance.
     */
    default RetrievalSearchInput filters(@Nonnull final RetrievalSearchFilter... filters) {
      return filters(Arrays.asList(filters));
    }
  }
}
