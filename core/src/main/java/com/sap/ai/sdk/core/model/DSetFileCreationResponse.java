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

/** Response for successful file creation */
// CHECKSTYLE:OFF
public class DSetFileCreationResponse
// CHECKSTYLE:ON
{
  @JsonProperty("message")
  private String message;

  @JsonProperty("url")
  private String url;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DSetFileCreationResponse. */
  protected DSetFileCreationResponse() {}

  /**
   * Set the message of this {@link DSetFileCreationResponse} instance and return the same instance.
   *
   * @param message File creation response message
   * @return The same instance of this {@link DSetFileCreationResponse} class
   */
  @Nonnull
  public DSetFileCreationResponse message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * File creation response message
   *
   * @return message The message of this {@link DSetFileCreationResponse} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link DSetFileCreationResponse} instance.
   *
   * @param message File creation response message
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Set the url of this {@link DSetFileCreationResponse} instance and return the same instance.
   *
   * @param url The url of this {@link DSetFileCreationResponse}
   * @return The same instance of this {@link DSetFileCreationResponse} class
   */
  @Nonnull
  public DSetFileCreationResponse url(@Nonnull final String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   *
   * @return url The url of this {@link DSetFileCreationResponse} instance.
   */
  @Nonnull
  public String getUrl() {
    return url;
  }

  /**
   * Set the url of this {@link DSetFileCreationResponse} instance.
   *
   * @param url The url of this {@link DSetFileCreationResponse}
   */
  public void setUrl(@Nonnull final String url) {
    this.url = url;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DSetFileCreationResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DSetFileCreationResponse} instance.
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
          "DSetFileCreationResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DSetFileCreationResponse} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (message != null) declaredFields.put("message", message);
    if (url != null) declaredFields.put("url", url);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DSetFileCreationResponse} instance. If the map
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
    final DSetFileCreationResponse dsetFileCreationResponse = (DSetFileCreationResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, dsetFileCreationResponse.cloudSdkCustomFields)
        && Objects.equals(this.message, dsetFileCreationResponse.message)
        && Objects.equals(this.url, dsetFileCreationResponse.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, url, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DSetFileCreationResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * DSetFileCreationResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (message) -> (url) -> new DSetFileCreationResponse().message(message).url(url);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the message of this {@link DSetFileCreationResponse} instance.
     *
     * @param message File creation response message
     * @return The DSetFileCreationResponse builder.
     */
    Builder1 message(@Nonnull final String message);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the url of this {@link DSetFileCreationResponse} instance.
     *
     * @param url The url of this {@link DSetFileCreationResponse}
     * @return The DSetFileCreationResponse instance.
     */
    DSetFileCreationResponse url(@Nonnull final String url);
  }
}
