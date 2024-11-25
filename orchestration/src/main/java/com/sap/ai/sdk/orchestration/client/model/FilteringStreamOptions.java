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
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Stream options for output filtering. Will be ignored if stream is false. */
@Beta // CHECKSTYLE:OFF
public class FilteringStreamOptions
// CHECKSTYLE:ON
{
  @JsonProperty("overlap")
  private Integer overlap = 0;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for FilteringStreamOptions. */
  protected FilteringStreamOptions() {}

  /**
   * Set the overlap of this {@link FilteringStreamOptions} instance and return the same instance.
   *
   * @param overlap Number of characters that should be additionally sent to content filtering
   *     services from previous chunks as additional context. Minimum: 0 Maximum: 10000
   * @return The same instance of this {@link FilteringStreamOptions} class
   */
  @Nonnull
  public FilteringStreamOptions overlap(@Nullable final Integer overlap) {
    this.overlap = overlap;
    return this;
  }

  /**
   * Number of characters that should be additionally sent to content filtering services from
   * previous chunks as additional context. minimum: 0 maximum: 10000
   *
   * @return overlap The overlap of this {@link FilteringStreamOptions} instance.
   */
  @Nonnull
  public Integer getOverlap() {
    return overlap;
  }

  /**
   * Set the overlap of this {@link FilteringStreamOptions} instance.
   *
   * @param overlap Number of characters that should be additionally sent to content filtering
   *     services from previous chunks as additional context. Minimum: 0 Maximum: 10000
   */
  public void setOverlap(@Nullable final Integer overlap) {
    this.overlap = overlap;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link FilteringStreamOptions}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link FilteringStreamOptions} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "FilteringStreamOptions has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link FilteringStreamOptions} instance. If the map
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
    final FilteringStreamOptions filteringStreamOptions = (FilteringStreamOptions) o;
    return Objects.equals(this.cloudSdkCustomFields, filteringStreamOptions.cloudSdkCustomFields)
        && Objects.equals(this.overlap, filteringStreamOptions.overlap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(overlap, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class FilteringStreamOptions {\n");
    sb.append("    overlap: ").append(toIndentedString(overlap)).append("\n");
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

  /** Create a new {@link FilteringStreamOptions} instance. No arguments are required. */
  public static FilteringStreamOptions create() {
    return new FilteringStreamOptions();
  }
}
