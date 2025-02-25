/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

  @JsonProperty("code")
  private Integer code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("location")
  private String location;

  @JsonProperty("module_results")
  private ModuleResults moduleResults;

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
   * Set the code of this {@link ErrorResponse} instance and return the same instance.
   *
   * @param code The code of this {@link ErrorResponse}
   * @return The same instance of this {@link ErrorResponse} class
   */
  @Nonnull
  public ErrorResponse code(@Nonnull final Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code The code of this {@link ErrorResponse} instance.
   */
  @Nonnull
  public Integer getCode() {
    return code;
  }

  /**
   * Set the code of this {@link ErrorResponse} instance.
   *
   * @param code The code of this {@link ErrorResponse}
   */
  public void setCode(@Nonnull final Integer code) {
    this.code = code;
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
   * Set the location of this {@link ErrorResponse} instance and return the same instance.
   *
   * @param location Where the error occurred
   * @return The same instance of this {@link ErrorResponse} class
   */
  @Nonnull
  public ErrorResponse location(@Nonnull final String location) {
    this.location = location;
    return this;
  }

  /**
   * Where the error occurred
   *
   * @return location The location of this {@link ErrorResponse} instance.
   */
  @Nonnull
  public String getLocation() {
    return location;
  }

  /**
   * Set the location of this {@link ErrorResponse} instance.
   *
   * @param location Where the error occurred
   */
  public void setLocation(@Nonnull final String location) {
    this.location = location;
  }

  /**
   * Set the moduleResults of this {@link ErrorResponse} instance and return the same instance.
   *
   * @param moduleResults The moduleResults of this {@link ErrorResponse}
   * @return The same instance of this {@link ErrorResponse} class
   */
  @Nonnull
  public ErrorResponse moduleResults(@Nullable final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
    return this;
  }

  /**
   * Get moduleResults
   *
   * @return moduleResults The moduleResults of this {@link ErrorResponse} instance.
   */
  @Nonnull
  public ModuleResults getModuleResults() {
    return moduleResults;
  }

  /**
   * Set the moduleResults of this {@link ErrorResponse} instance.
   *
   * @param moduleResults The moduleResults of this {@link ErrorResponse}
   */
  public void setModuleResults(@Nullable final ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
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
   * @deprecated Use {@link #toMap()} instead.
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  @Deprecated
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("ErrorResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ErrorResponse} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (requestId != null) declaredFields.put("requestId", requestId);
    if (code != null) declaredFields.put("code", code);
    if (message != null) declaredFields.put("message", message);
    if (location != null) declaredFields.put("location", location);
    if (moduleResults != null) declaredFields.put("moduleResults", moduleResults);
    return declaredFields;
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
        && Objects.equals(this.code, errorResponse.code)
        && Objects.equals(this.message, errorResponse.message)
        && Objects.equals(this.location, errorResponse.location)
        && Objects.equals(this.moduleResults, errorResponse.moduleResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, code, message, location, moduleResults, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    moduleResults: ").append(toIndentedString(moduleResults)).append("\n");
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
    return (requestId) ->
        (code) ->
            (message) ->
                (location) ->
                    new ErrorResponse()
                        .requestId(requestId)
                        .code(code)
                        .message(message)
                        .location(location);
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
     * Set the code of this {@link ErrorResponse} instance.
     *
     * @param code The code of this {@link ErrorResponse}
     * @return The ErrorResponse builder.
     */
    Builder2 code(@Nonnull final Integer code);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the message of this {@link ErrorResponse} instance.
     *
     * @param message The message of this {@link ErrorResponse}
     * @return The ErrorResponse builder.
     */
    Builder3 message(@Nonnull final String message);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the location of this {@link ErrorResponse} instance.
     *
     * @param location Where the error occurred
     * @return The ErrorResponse instance.
     */
    ErrorResponse location(@Nonnull final String location);
  }
}
