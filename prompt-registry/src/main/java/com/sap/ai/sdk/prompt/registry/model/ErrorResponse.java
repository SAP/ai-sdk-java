/*
 * Prompt Registry API
 * Prompt Storage service for Design time & Runtime prompt templates.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.prompt.registry.model;

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

/** ErrorResponse */
@Beta // CHECKSTYLE:OFF
public class ErrorResponse
// CHECKSTYLE:ON
{
  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ErrorResponse. */
  protected ErrorResponse() {}

  /**
   * Set the requestId of this {@link ErrorResponse} instance and return the same instance.
   *
   * @param requestId The requestId of this {@link ErrorResponse}
   * @return The same instance of this {@link ErrorResponse} class
   */
  @Nonnull
  public ErrorResponse requestId(@Nonnull final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   *
   * @return requestId The requestId of this {@link ErrorResponse} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link ErrorResponse} instance.
   *
   * @param requestId The requestId of this {@link ErrorResponse}
   */
  public void setRequestId(@Nonnull final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the message of this {@link ErrorResponse} instance and return the same instance.
   *
   * @param message The message of this {@link ErrorResponse}
   * @return The same instance of this {@link ErrorResponse} class
   */
  @Nonnull
  public ErrorResponse message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link ErrorResponse} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link ErrorResponse} instance.
   *
   * @param message The message of this {@link ErrorResponse}
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ErrorResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ErrorResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("ErrorResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ErrorResponse} instance. If the map previously
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
    final ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, errorResponse.cloudSdkCustomFields)
        && Objects.equals(this.requestId, errorResponse.requestId)
        && Objects.equals(this.message, errorResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ErrorResponse} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (requestId) -> (message) -> new ErrorResponse().requestId(requestId).message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the requestId of this {@link ErrorResponse} instance.
     *
     * @param requestId The requestId of this {@link ErrorResponse}
     * @return The ErrorResponse builder.
     */
    Builder1 requestId(@Nonnull final String requestId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the message of this {@link ErrorResponse} instance.
     *
     * @param message The message of this {@link ErrorResponse}
     * @return The ErrorResponse instance.
     */
    ErrorResponse message(@Nonnull final String message);
  }
}
