/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.model2;

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

/** ContentFilterDetectedResult */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class ContentFilterDetectedResult
// CHECKSTYLE:ON
{
  @JsonProperty("filtered")
  private Boolean filtered;

  @JsonProperty("detected")
  private Boolean detected;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the filtered of this {@link ContentFilterDetectedResult} instance and return the same
   * instance.
   *
   * @param filtered The filtered of this {@link ContentFilterDetectedResult}
   * @return The same instance of this {@link ContentFilterDetectedResult} class
   */
  @Nonnull
  public ContentFilterDetectedResult filtered(@Nonnull final Boolean filtered) {
    this.filtered = filtered;
    return this;
  }

  /**
   * Get filtered
   *
   * @return filtered The filtered of this {@link ContentFilterDetectedResult} instance.
   */
  @Nonnull
  public Boolean isFiltered() {
    return filtered;
  }

  /**
   * Set the filtered of this {@link ContentFilterDetectedResult} instance.
   *
   * @param filtered The filtered of this {@link ContentFilterDetectedResult}
   */
  public void setFiltered(@Nonnull final Boolean filtered) {
    this.filtered = filtered;
  }

  /**
   * Set the detected of this {@link ContentFilterDetectedResult} instance and return the same
   * instance.
   *
   * @param detected The detected of this {@link ContentFilterDetectedResult}
   * @return The same instance of this {@link ContentFilterDetectedResult} class
   */
  @Nonnull
  public ContentFilterDetectedResult detected(@Nonnull final Boolean detected) {
    this.detected = detected;
    return this;
  }

  /**
   * Get detected
   *
   * @return detected The detected of this {@link ContentFilterDetectedResult} instance.
   */
  @Nonnull
  public Boolean isDetected() {
    return detected;
  }

  /**
   * Set the detected of this {@link ContentFilterDetectedResult} instance.
   *
   * @param detected The detected of this {@link ContentFilterDetectedResult}
   */
  public void setDetected(@Nonnull final Boolean detected) {
    this.detected = detected;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ContentFilterDetectedResult}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ContentFilterDetectedResult}
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
          "ContentFilterDetectedResult has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ContentFilterDetectedResult} instance. If the map
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
    final ContentFilterDetectedResult contentFilterDetectedResult = (ContentFilterDetectedResult) o;
    return Objects.equals(
            this.cloudSdkCustomFields, contentFilterDetectedResult.cloudSdkCustomFields)
        && Objects.equals(this.filtered, contentFilterDetectedResult.filtered)
        && Objects.equals(this.detected, contentFilterDetectedResult.detected);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filtered, detected, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ContentFilterDetectedResult {\n");
    sb.append("    filtered: ").append(toIndentedString(filtered)).append("\n");
    sb.append("    detected: ").append(toIndentedString(detected)).append("\n");
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
}
