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

/** FilteringModuleConfig */
// CHECKSTYLE:OFF
public class FilteringModuleConfig
// CHECKSTYLE:ON
{
  @JsonProperty("input")
  private FilteringConfig input;

  @JsonProperty("output")
  private FilteringConfig output;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected FilteringModuleConfig() {}

  /**
   * Set the input of this {@link FilteringModuleConfig} instance and return the same instance.
   *
   * @param input The input of this {@link FilteringModuleConfig}
   * @return The same instance of this {@link FilteringModuleConfig} class
   */
  @Nonnull
  public FilteringModuleConfig input(@Nullable final FilteringConfig input) {
    this.input = input;
    return this;
  }

  /**
   * Get input
   *
   * @return input The input of this {@link FilteringModuleConfig} instance.
   */
  @Nonnull
  public FilteringConfig getInput() {
    return input;
  }

  /**
   * Set the input of this {@link FilteringModuleConfig} instance.
   *
   * @param input The input of this {@link FilteringModuleConfig}
   */
  public void setInput(@Nullable final FilteringConfig input) {
    this.input = input;
  }

  /**
   * Set the output of this {@link FilteringModuleConfig} instance and return the same instance.
   *
   * @param output The output of this {@link FilteringModuleConfig}
   * @return The same instance of this {@link FilteringModuleConfig} class
   */
  @Nonnull
  public FilteringModuleConfig output(@Nullable final FilteringConfig output) {
    this.output = output;
    return this;
  }

  /**
   * Get output
   *
   * @return output The output of this {@link FilteringModuleConfig} instance.
   */
  @Nonnull
  public FilteringConfig getOutput() {
    return output;
  }

  /**
   * Set the output of this {@link FilteringModuleConfig} instance.
   *
   * @param output The output of this {@link FilteringModuleConfig}
   */
  public void setOutput(@Nullable final FilteringConfig output) {
    this.output = output;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link FilteringModuleConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link FilteringModuleConfig} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "FilteringModuleConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link FilteringModuleConfig} instance. If the map
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
    final FilteringModuleConfig filteringModuleConfig = (FilteringModuleConfig) o;
    return Objects.equals(this.cloudSdkCustomFields, filteringModuleConfig.cloudSdkCustomFields)
        && Objects.equals(this.input, filteringModuleConfig.input)
        && Objects.equals(this.output, filteringModuleConfig.output);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, output, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class FilteringModuleConfig {\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
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

  /** Create a new {@link FilteringModuleConfig} instance. No arguments are required. */
  public static FilteringModuleConfig create() {
    return new FilteringModuleConfig();
  }
}
