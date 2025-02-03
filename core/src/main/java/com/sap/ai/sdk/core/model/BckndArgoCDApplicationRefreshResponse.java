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
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndArgoCDApplicationRefreshResponse */
@Beta // CHECKSTYLE:OFF
public class BckndArgoCDApplicationRefreshResponse
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndArgoCDApplicationRefreshResponse. */
  protected BckndArgoCDApplicationRefreshResponse() {}

  /**
   * Set the id of this {@link BckndArgoCDApplicationRefreshResponse} instance and return the same
   * instance.
   *
   * @param id Generic ID
   * @return The same instance of this {@link BckndArgoCDApplicationRefreshResponse} class
   */
  @Nonnull
  public BckndArgoCDApplicationRefreshResponse id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * Generic ID
   *
   * @return id The id of this {@link BckndArgoCDApplicationRefreshResponse} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link BckndArgoCDApplicationRefreshResponse} instance.
   *
   * @param id Generic ID
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the message of this {@link BckndArgoCDApplicationRefreshResponse} instance and return the
   * same instance.
   *
   * @param message Message
   * @return The same instance of this {@link BckndArgoCDApplicationRefreshResponse} class
   */
  @Nonnull
  public BckndArgoCDApplicationRefreshResponse message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Message
   *
   * @return message The message of this {@link BckndArgoCDApplicationRefreshResponse} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link BckndArgoCDApplicationRefreshResponse} instance.
   *
   * @param message Message
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndArgoCDApplicationRefreshResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * BckndArgoCDApplicationRefreshResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndArgoCDApplicationRefreshResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDApplicationRefreshResponse} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
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
    final BckndArgoCDApplicationRefreshResponse bckndArgoCDApplicationRefreshResponse =
        (BckndArgoCDApplicationRefreshResponse) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndArgoCDApplicationRefreshResponse.cloudSdkCustomFields)
        && Objects.equals(this.id, bckndArgoCDApplicationRefreshResponse.id)
        && Objects.equals(this.message, bckndArgoCDApplicationRefreshResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDApplicationRefreshResponse {\n");
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

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * BckndArgoCDApplicationRefreshResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (id) -> (message) -> new BckndArgoCDApplicationRefreshResponse().id(id).message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link BckndArgoCDApplicationRefreshResponse} instance.
     *
     * @param id Generic ID
     * @return The BckndArgoCDApplicationRefreshResponse builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the message of this {@link BckndArgoCDApplicationRefreshResponse} instance.
     *
     * @param message Message
     * @return The BckndArgoCDApplicationRefreshResponse instance.
     */
    BckndArgoCDApplicationRefreshResponse message(@Nonnull final String message);
  }
}
