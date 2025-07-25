/*
 * Internal Orchestration Service API
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ErrorResponseStreaming */
// CHECKSTYLE:OFF
public class ErrorResponseStreaming
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
  private ModuleResultsStreaming moduleResults;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ErrorResponseStreaming. */
  protected ErrorResponseStreaming() {}

  /**
   * Set the requestId of this {@link ErrorResponseStreaming} instance and return the same instance.
   *
   * @param requestId The requestId of this {@link ErrorResponseStreaming}
   * @return The same instance of this {@link ErrorResponseStreaming} class
   */
  @Nonnull
  public ErrorResponseStreaming requestId(@Nonnull final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   *
   * @return requestId The requestId of this {@link ErrorResponseStreaming} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link ErrorResponseStreaming} instance.
   *
   * @param requestId The requestId of this {@link ErrorResponseStreaming}
   */
  public void setRequestId(@Nonnull final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the code of this {@link ErrorResponseStreaming} instance and return the same instance.
   *
   * @param code The code of this {@link ErrorResponseStreaming}
   * @return The same instance of this {@link ErrorResponseStreaming} class
   */
  @Nonnull
  public ErrorResponseStreaming code(@Nonnull final Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code The code of this {@link ErrorResponseStreaming} instance.
   */
  @Nonnull
  public Integer getCode() {
    return code;
  }

  /**
   * Set the code of this {@link ErrorResponseStreaming} instance.
   *
   * @param code The code of this {@link ErrorResponseStreaming}
   */
  public void setCode(@Nonnull final Integer code) {
    this.code = code;
  }

  /**
   * Set the message of this {@link ErrorResponseStreaming} instance and return the same instance.
   *
   * @param message The message of this {@link ErrorResponseStreaming}
   * @return The same instance of this {@link ErrorResponseStreaming} class
   */
  @Nonnull
  public ErrorResponseStreaming message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link ErrorResponseStreaming} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link ErrorResponseStreaming} instance.
   *
   * @param message The message of this {@link ErrorResponseStreaming}
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Set the location of this {@link ErrorResponseStreaming} instance and return the same instance.
   *
   * @param location Where the error occurred
   * @return The same instance of this {@link ErrorResponseStreaming} class
   */
  @Nonnull
  public ErrorResponseStreaming location(@Nonnull final String location) {
    this.location = location;
    return this;
  }

  /**
   * Where the error occurred
   *
   * @return location The location of this {@link ErrorResponseStreaming} instance.
   */
  @Nonnull
  public String getLocation() {
    return location;
  }

  /**
   * Set the location of this {@link ErrorResponseStreaming} instance.
   *
   * @param location Where the error occurred
   */
  public void setLocation(@Nonnull final String location) {
    this.location = location;
  }

  /**
   * Set the moduleResults of this {@link ErrorResponseStreaming} instance and return the same
   * instance.
   *
   * @param moduleResults The moduleResults of this {@link ErrorResponseStreaming}
   * @return The same instance of this {@link ErrorResponseStreaming} class
   */
  @Nonnull
  public ErrorResponseStreaming moduleResults(
      @Nullable final ModuleResultsStreaming moduleResults) {
    this.moduleResults = moduleResults;
    return this;
  }

  /**
   * Get moduleResults
   *
   * @return moduleResults The moduleResults of this {@link ErrorResponseStreaming} instance.
   */
  @Nonnull
  public ModuleResultsStreaming getModuleResults() {
    return moduleResults;
  }

  /**
   * Set the moduleResults of this {@link ErrorResponseStreaming} instance.
   *
   * @param moduleResults The moduleResults of this {@link ErrorResponseStreaming}
   */
  public void setModuleResults(@Nullable final ModuleResultsStreaming moduleResults) {
    this.moduleResults = moduleResults;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ErrorResponseStreaming}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ErrorResponseStreaming} instance.
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
          "ErrorResponseStreaming has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ErrorResponseStreaming} instance including
   * unrecognized properties.
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
   * Set an unrecognizable property of this {@link ErrorResponseStreaming} instance. If the map
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
    final ErrorResponseStreaming errorResponseStreaming = (ErrorResponseStreaming) o;
    return Objects.equals(this.cloudSdkCustomFields, errorResponseStreaming.cloudSdkCustomFields)
        && Objects.equals(this.requestId, errorResponseStreaming.requestId)
        && Objects.equals(this.code, errorResponseStreaming.code)
        && Objects.equals(this.message, errorResponseStreaming.message)
        && Objects.equals(this.location, errorResponseStreaming.location)
        && Objects.equals(this.moduleResults, errorResponseStreaming.moduleResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, code, message, location, moduleResults, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponseStreaming {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ErrorResponseStreaming}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (requestId) ->
        (code) ->
            (message) ->
                (location) ->
                    new ErrorResponseStreaming()
                        .requestId(requestId)
                        .code(code)
                        .message(message)
                        .location(location);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the requestId of this {@link ErrorResponseStreaming} instance.
     *
     * @param requestId The requestId of this {@link ErrorResponseStreaming}
     * @return The ErrorResponseStreaming builder.
     */
    Builder1 requestId(@Nonnull final String requestId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the code of this {@link ErrorResponseStreaming} instance.
     *
     * @param code The code of this {@link ErrorResponseStreaming}
     * @return The ErrorResponseStreaming builder.
     */
    Builder2 code(@Nonnull final Integer code);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the message of this {@link ErrorResponseStreaming} instance.
     *
     * @param message The message of this {@link ErrorResponseStreaming}
     * @return The ErrorResponseStreaming builder.
     */
    Builder3 message(@Nonnull final String message);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the location of this {@link ErrorResponseStreaming} instance.
     *
     * @param location Where the error occurred
     * @return The ErrorResponseStreaming instance.
     */
    ErrorResponseStreaming location(@Nonnull final String location);
  }
}
