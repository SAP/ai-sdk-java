/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

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

/** TrckDeleteMetricsResponse */
// CHECKSTYLE:OFF
public class TrckDeleteMetricsResponse
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private TrckExecutionId id;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TrckDeleteMetricsResponse. */
  protected TrckDeleteMetricsResponse() {}

  /**
   * Set the id of this {@link TrckDeleteMetricsResponse} instance and return the same instance.
   *
   * @param id The id of this {@link TrckDeleteMetricsResponse}
   * @return The same instance of this {@link TrckDeleteMetricsResponse} class
   */
  @Nonnull
  public TrckDeleteMetricsResponse id(@Nullable final TrckExecutionId id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id The id of this {@link TrckDeleteMetricsResponse} instance.
   */
  @Nonnull
  public TrckExecutionId getId() {
    return id;
  }

  /**
   * Set the id of this {@link TrckDeleteMetricsResponse} instance.
   *
   * @param id The id of this {@link TrckDeleteMetricsResponse}
   */
  public void setId(@Nullable final TrckExecutionId id) {
    this.id = id;
  }

  /**
   * Set the message of this {@link TrckDeleteMetricsResponse} instance and return the same
   * instance.
   *
   * @param message The message of this {@link TrckDeleteMetricsResponse}
   * @return The same instance of this {@link TrckDeleteMetricsResponse} class
   */
  @Nonnull
  public TrckDeleteMetricsResponse message(@Nullable final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link TrckDeleteMetricsResponse} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link TrckDeleteMetricsResponse} instance.
   *
   * @param message The message of this {@link TrckDeleteMetricsResponse}
   */
  public void setMessage(@Nullable final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckDeleteMetricsResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckDeleteMetricsResponse} instance.
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
          "TrckDeleteMetricsResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link TrckDeleteMetricsResponse} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (message != null) declaredFields.put("message", message);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link TrckDeleteMetricsResponse} instance. If the map
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
    final TrckDeleteMetricsResponse trckDeleteMetricsResponse = (TrckDeleteMetricsResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, trckDeleteMetricsResponse.cloudSdkCustomFields)
        && Objects.equals(this.id, trckDeleteMetricsResponse.id)
        && Objects.equals(this.message, trckDeleteMetricsResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckDeleteMetricsResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

  /** Create a new {@link TrckDeleteMetricsResponse} instance. No arguments are required. */
  public static TrckDeleteMetricsResponse create() {
    return new TrckDeleteMetricsResponse();
  }
}
