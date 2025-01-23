/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

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

package openapi.grounding.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.SearchFilter;
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

/** TextSearchRequest */
// CHECKSTYLE:OFF
public class TextSearchRequest
// CHECKSTYLE:ON
{
  @JsonProperty("query")
  private String query;

  @JsonProperty("filters")
  private List<SearchFilter> filters = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TextSearchRequest. */
  private TextSearchRequest() {}

  /**
   * Set the query of this {@link TextSearchRequest} instance and return the same instance.
   *
   * @param query Query string
   * @return The same instance of this {@link TextSearchRequest} class
   */
  @Nonnull
  public TextSearchRequest query(@Nonnull final String query) {
    this.query = query;
    return this;
  }

  /**
   * Query string
   *
   * @return query The query of this {@link TextSearchRequest} instance.
   */
  @Nonnull
  public String getQuery() {
    return query;
  }

  /**
   * Set the query of this {@link TextSearchRequest} instance.
   *
   * @param query Query string
   */
  public void setQuery(@Nonnull final String query) {
    this.query = query;
  }

  /**
   * Set the filters of this {@link TextSearchRequest} instance and return the same instance.
   *
   * @param filters The filters of this {@link TextSearchRequest}
   * @return The same instance of this {@link TextSearchRequest} class
   */
  @Nonnull
  public TextSearchRequest filters(@Nonnull final List<SearchFilter> filters) {
    this.filters = filters;
    return this;
  }

  /**
   * Add one filters instance to this {@link TextSearchRequest}.
   *
   * @param filtersItem The filters that should be added
   * @return The same instance of type {@link TextSearchRequest}
   */
  @Nonnull
  public TextSearchRequest addFiltersItem(@Nonnull final SearchFilter filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   *
   * @return filters The filters of this {@link TextSearchRequest} instance.
   */
  @Nonnull
  public List<SearchFilter> getFilters() {
    return filters;
  }

  /**
   * Set the filters of this {@link TextSearchRequest} instance.
   *
   * @param filters The filters of this {@link TextSearchRequest}
   */
  public void setFilters(@Nonnull final List<SearchFilter> filters) {
    this.filters = filters;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TextSearchRequest}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TextSearchRequest} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TextSearchRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TextSearchRequest} instance. If the map
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final TextSearchRequest textSearchRequest = (TextSearchRequest) o;
    return Objects.equals(this.cloudSdkCustomFields, textSearchRequest.cloudSdkCustomFields)
        && Objects.equals(this.query, textSearchRequest.query)
        && Objects.equals(this.filters, textSearchRequest.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(query, filters, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TextSearchRequest {\n");
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
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link TextSearchRequest}
   * instance with all required arguments.
   */
  public static Builder builder() {
    return (query) -> (filters) -> () -> new TextSearchRequest().query(query).filters(filters);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the query of this {@link TextSearchRequest} instance.
     *
     * @param query Query string
     * @return The TextSearchRequest builder.
     */
    Builder1 query(@Nonnull final String query);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the filters of this {@link TextSearchRequest} instance.
     *
     * @param filters The filters of this {@link TextSearchRequest}
     * @return The TextSearchRequest instance.
     */
    Builder2 filters(@Nonnull final List<SearchFilter> filters);

    /**
     * Set the filters of this {@link TextSearchRequest} instance.
     *
     * @param filters The filters of this {@link TextSearchRequest}
     * @return The TextSearchRequest instance.
     */
    default Builder2 filters(@Nonnull final SearchFilter... filters) {
      return filters(Arrays.asList(filters));
    }
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Finalize the builder for new {@link TextSearchRequest} instance.
     *
     * @return The TextSearchRequest instance.
     */
    TextSearchRequest build();
  }
}
