/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
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
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** RetievalPerFilterSearchResultWithError */
@Beta // CHECKSTYLE:OFF
public class RetievalPerFilterSearchResultWithError
// CHECKSTYLE:ON
{
  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RetievalPerFilterSearchResultWithError. */
  protected RetievalPerFilterSearchResultWithError() {}

  /**
   * Set the message of this {@link RetievalPerFilterSearchResultWithError} instance and return the
   * same instance.
   *
   * @param message The message of this {@link RetievalPerFilterSearchResultWithError}
   * @return The same instance of this {@link RetievalPerFilterSearchResultWithError} class
   */
  @Nonnull
  public RetievalPerFilterSearchResultWithError message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link RetievalPerFilterSearchResultWithError} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link RetievalPerFilterSearchResultWithError} instance.
   *
   * @param message The message of this {@link RetievalPerFilterSearchResultWithError}
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * RetievalPerFilterSearchResultWithError}.
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
   * RetievalPerFilterSearchResultWithError} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "RetievalPerFilterSearchResultWithError has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RetievalPerFilterSearchResultWithError} instance.
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
    final RetievalPerFilterSearchResultWithError retievalPerFilterSearchResultWithError =
        (RetievalPerFilterSearchResultWithError) o;
    return Objects.equals(
            this.cloudSdkCustomFields, retievalPerFilterSearchResultWithError.cloudSdkCustomFields)
        && Objects.equals(this.message, retievalPerFilterSearchResultWithError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RetievalPerFilterSearchResultWithError {\n");
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
   * RetievalPerFilterSearchResultWithError} instance with all required arguments.
   */
  public static Builder create() {
    return (message) -> new RetievalPerFilterSearchResultWithError().message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the message of this {@link RetievalPerFilterSearchResultWithError} instance.
     *
     * @param message The message of this {@link RetievalPerFilterSearchResultWithError}
     * @return The RetievalPerFilterSearchResultWithError instance.
     */
    RetievalPerFilterSearchResultWithError message(@Nonnull final String message);
  }
}
