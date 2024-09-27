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

/** TrckDetailsErrorResponse */
// CHECKSTYLE:OFF
public class TrckDetailsErrorResponse
// CHECKSTYLE:ON
{
  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected TrckDetailsErrorResponse() {}

  /**
   * Set the code of this {@link TrckDetailsErrorResponse} instance and return the same instance.
   *
   * @param code Descriptive error code (not http status code)
   * @return The same instance of this {@link TrckDetailsErrorResponse} class
   */
  @Nonnull
  public TrckDetailsErrorResponse code(@Nullable final String code) {
    this.code = code;
    return this;
  }

  /**
   * Descriptive error code (not http status code)
   *
   * @return code The code of this {@link TrckDetailsErrorResponse} instance.
   */
  @Nonnull
  public String getCode() {
    return code;
  }

  /**
   * Set the code of this {@link TrckDetailsErrorResponse} instance.
   *
   * @param code Descriptive error code (not http status code)
   */
  public void setCode(@Nullable final String code) {
    this.code = code;
  }

  /**
   * Set the message of this {@link TrckDetailsErrorResponse} instance and return the same instance.
   *
   * @param message Plaintext error description
   * @return The same instance of this {@link TrckDetailsErrorResponse} class
   */
  @Nonnull
  public TrckDetailsErrorResponse message(@Nullable final String message) {
    this.message = message;
    return this;
  }

  /**
   * Plaintext error description
   *
   * @return message The message of this {@link TrckDetailsErrorResponse} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link TrckDetailsErrorResponse} instance.
   *
   * @param message Plaintext error description
   */
  public void setMessage(@Nullable final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckDetailsErrorResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckDetailsErrorResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "TrckDetailsErrorResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckDetailsErrorResponse} instance. If the map
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
    final TrckDetailsErrorResponse trckDetailsErrorResponse = (TrckDetailsErrorResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, trckDetailsErrorResponse.cloudSdkCustomFields)
        && Objects.equals(this.code, trckDetailsErrorResponse.code)
        && Objects.equals(this.message, trckDetailsErrorResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckDetailsErrorResponse {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

  /** Create a new {@link TrckDetailsErrorResponse} instance. No arguments are required. */
  public static TrckDetailsErrorResponse create() {
    return new TrckDetailsErrorResponse();
  }
}
