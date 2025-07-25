/*
 * Grounding
 * Grounding is a service designed to handle data-related tasks, such as grounding and retrieval, using vector databases. It provides specialized data retrieval through these databases, grounding the retrieval process with your own external and context-relevant data. Grounding combines generative AI capabilities with the ability to use real-time, precise data to improve decision-making and business operations for specific AI-driven business solutions.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ApiError */
// CHECKSTYLE:OFF
public class ApiError
// CHECKSTYLE:ON
{
  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("target")
  private String target;

  @JsonProperty("details")
  private List<DetailsErrorResponse> details = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ApiError. */
  protected ApiError() {}

  /**
   * Set the code of this {@link ApiError} instance and return the same instance.
   *
   * @param code Descriptive error code (not http status code).
   * @return The same instance of this {@link ApiError} class
   */
  @Nonnull
  public ApiError code(@Nonnull final String code) {
    this.code = code;
    return this;
  }

  /**
   * Descriptive error code (not http status code).
   *
   * @return code The code of this {@link ApiError} instance.
   */
  @Nonnull
  public String getCode() {
    return code;
  }

  /**
   * Set the code of this {@link ApiError} instance.
   *
   * @param code Descriptive error code (not http status code).
   */
  public void setCode(@Nonnull final String code) {
    this.code = code;
  }

  /**
   * Set the message of this {@link ApiError} instance and return the same instance.
   *
   * @param message plaintext error description
   * @return The same instance of this {@link ApiError} class
   */
  @Nonnull
  public ApiError message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * plaintext error description
   *
   * @return message The message of this {@link ApiError} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link ApiError} instance.
   *
   * @param message plaintext error description
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Set the requestId of this {@link ApiError} instance and return the same instance.
   *
   * @param requestId id of individual request
   * @return The same instance of this {@link ApiError} class
   */
  @Nonnull
  public ApiError requestId(@Nullable final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * id of individual request
   *
   * @return requestId The requestId of this {@link ApiError} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link ApiError} instance.
   *
   * @param requestId id of individual request
   */
  public void setRequestId(@Nullable final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the target of this {@link ApiError} instance and return the same instance.
   *
   * @param target url that has been called
   * @return The same instance of this {@link ApiError} class
   */
  @Nonnull
  public ApiError target(@Nullable final String target) {
    this.target = target;
    return this;
  }

  /**
   * url that has been called
   *
   * @return target The target of this {@link ApiError} instance.
   */
  @Nonnull
  public String getTarget() {
    return target;
  }

  /**
   * Set the target of this {@link ApiError} instance.
   *
   * @param target url that has been called
   */
  public void setTarget(@Nullable final String target) {
    this.target = target;
  }

  /**
   * Set the details of this {@link ApiError} instance and return the same instance.
   *
   * @param details The details of this {@link ApiError}
   * @return The same instance of this {@link ApiError} class
   */
  @Nonnull
  public ApiError details(@Nullable final List<DetailsErrorResponse> details) {
    this.details = details;
    return this;
  }

  /**
   * Add one details instance to this {@link ApiError}.
   *
   * @param detailsItem The details that should be added
   * @return The same instance of type {@link ApiError}
   */
  @Nonnull
  public ApiError addDetailsItem(@Nonnull final DetailsErrorResponse detailsItem) {
    if (this.details == null) {
      this.details = new ArrayList<>();
    }
    this.details.add(detailsItem);
    return this;
  }

  /**
   * Get details
   *
   * @return details The details of this {@link ApiError} instance.
   */
  @Nonnull
  public List<DetailsErrorResponse> getDetails() {
    return details;
  }

  /**
   * Set the details of this {@link ApiError} instance.
   *
   * @param details The details of this {@link ApiError}
   */
  public void setDetails(@Nullable final List<DetailsErrorResponse> details) {
    this.details = details;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ApiError}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ApiError} instance.
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
      throw new NoSuchElementException("ApiError has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ApiError} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (code != null) declaredFields.put("code", code);
    if (message != null) declaredFields.put("message", message);
    if (requestId != null) declaredFields.put("requestId", requestId);
    if (target != null) declaredFields.put("target", target);
    if (details != null) declaredFields.put("details", details);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ApiError} instance. If the map previously
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
    final ApiError apiError = (ApiError) o;
    return Objects.equals(this.cloudSdkCustomFields, apiError.cloudSdkCustomFields)
        && Objects.equals(this.code, apiError.code)
        && Objects.equals(this.message, apiError.message)
        && Objects.equals(this.requestId, apiError.requestId)
        && Objects.equals(this.target, apiError.target)
        && Objects.equals(this.details, apiError.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, requestId, target, details, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ApiError {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ApiError} instance with
   * all required arguments.
   */
  public static Builder create() {
    return (code) -> (message) -> new ApiError().code(code).message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the code of this {@link ApiError} instance.
     *
     * @param code Descriptive error code (not http status code).
     * @return The ApiError builder.
     */
    Builder1 code(@Nonnull final String code);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the message of this {@link ApiError} instance.
     *
     * @param message plaintext error description
     * @return The ApiError instance.
     */
    ApiError message(@Nonnull final String message);
  }
}
