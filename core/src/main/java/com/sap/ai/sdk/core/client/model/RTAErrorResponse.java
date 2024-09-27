/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.34.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

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

/** RTAErrorResponse */
// CHECKSTYLE:OFF
public class RTAErrorResponse
// CHECKSTYLE:ON
{
  @JsonProperty("error")
  private RTAError error;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected RTAErrorResponse() {}

  /**
   * Set the error of this {@link RTAErrorResponse} instance and return the same instance.
   *
   * @param error The error of this {@link RTAErrorResponse}
   * @return The same instance of this {@link RTAErrorResponse} class
   */
  @Nonnull
  public RTAErrorResponse error(@Nullable final RTAError error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   *
   * @return error The error of this {@link RTAErrorResponse} instance.
   */
  @Nonnull
  public RTAError getError() {
    return error;
  }

  /**
   * Set the error of this {@link RTAErrorResponse} instance.
   *
   * @param error The error of this {@link RTAErrorResponse}
   */
  public void setError(@Nullable final RTAError error) {
    this.error = error;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAErrorResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAErrorResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("RTAErrorResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAErrorResponse} instance. If the map previously
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
    final RTAErrorResponse rtAErrorResponse = (RTAErrorResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAErrorResponse.cloudSdkCustomFields)
        && Objects.equals(this.error, rtAErrorResponse.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

  /** Create a new {@link RTAErrorResponse} instance. No arguments are required. */
  public static RTAErrorResponse create() {
    return new RTAErrorResponse();
  }
}
