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

/** OutputFilteringConfig */
@Beta // CHECKSTYLE:OFF
public class OutputFilteringConfig
// CHECKSTYLE:ON
{
  @JsonProperty("filters")
  private List<FilterConfig> filters = new ArrayList<>();

  @JsonProperty("stream_options")
  private FilteringStreamOptions streamOptions;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for OutputFilteringConfig. */
  protected OutputFilteringConfig() {}

  /**
   * Set the filters of this {@link OutputFilteringConfig} instance and return the same instance.
   *
   * @param filters Configuration for content filtering services that should be used for the given
   *     filtering step (input filtering or output filtering).
   * @return The same instance of this {@link OutputFilteringConfig} class
   */
  @Nonnull
  public OutputFilteringConfig filters(@Nonnull final List<FilterConfig> filters) {
    this.filters = filters;
    return this;
  }

  /**
   * Add one filters instance to this {@link OutputFilteringConfig}.
   *
   * @param filtersItem The filters that should be added
   * @return The same instance of type {@link OutputFilteringConfig}
   */
  @Nonnull
  public OutputFilteringConfig addFiltersItem(@Nonnull final FilterConfig filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Configuration for content filtering services that should be used for the given filtering step
   * (input filtering or output filtering).
   *
   * @return filters The filters of this {@link OutputFilteringConfig} instance.
   */
  @Nonnull
  public List<FilterConfig> getFilters() {
    return filters;
  }

  /**
   * Set the filters of this {@link OutputFilteringConfig} instance.
   *
   * @param filters Configuration for content filtering services that should be used for the given
   *     filtering step (input filtering or output filtering).
   */
  public void setFilters(@Nonnull final List<FilterConfig> filters) {
    this.filters = filters;
  }

  /**
   * Set the streamOptions of this {@link OutputFilteringConfig} instance and return the same
   * instance.
   *
   * @param streamOptions The streamOptions of this {@link OutputFilteringConfig}
   * @return The same instance of this {@link OutputFilteringConfig} class
   */
  @Nonnull
  public OutputFilteringConfig streamOptions(@Nullable final FilteringStreamOptions streamOptions) {
    this.streamOptions = streamOptions;
    return this;
  }

  /**
   * Get streamOptions
   *
   * @return streamOptions The streamOptions of this {@link OutputFilteringConfig} instance.
   */
  @Nonnull
  public FilteringStreamOptions getStreamOptions() {
    return streamOptions;
  }

  /**
   * Set the streamOptions of this {@link OutputFilteringConfig} instance.
   *
   * @param streamOptions The streamOptions of this {@link OutputFilteringConfig}
   */
  public void setStreamOptions(@Nullable final FilteringStreamOptions streamOptions) {
    this.streamOptions = streamOptions;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link OutputFilteringConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link OutputFilteringConfig} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "OutputFilteringConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link OutputFilteringConfig} instance. If the map
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
    final OutputFilteringConfig outputFilteringConfig = (OutputFilteringConfig) o;
    return Objects.equals(this.cloudSdkCustomFields, outputFilteringConfig.cloudSdkCustomFields)
        && Objects.equals(this.filters, outputFilteringConfig.filters)
        && Objects.equals(this.streamOptions, outputFilteringConfig.streamOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filters, streamOptions, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class OutputFilteringConfig {\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    streamOptions: ").append(toIndentedString(streamOptions)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link OutputFilteringConfig}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (filters) -> new OutputFilteringConfig().filters(filters);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the filters of this {@link OutputFilteringConfig} instance.
     *
     * @param filters Configuration for content filtering services that should be used for the given
     *     filtering step (input filtering or output filtering).
     * @return The OutputFilteringConfig instance.
     */
    OutputFilteringConfig filters(@Nonnull final List<FilterConfig> filters);

    /**
     * Set the filters of this {@link OutputFilteringConfig} instance.
     *
     * @param filters Configuration for content filtering services that should be used for the given
     *     filtering step (input filtering or output filtering).
     * @return The OutputFilteringConfig instance.
     */
    default OutputFilteringConfig filters(@Nonnull final FilterConfig... filters) {
      return filters(Arrays.asList(filters));
    }
  }
}
