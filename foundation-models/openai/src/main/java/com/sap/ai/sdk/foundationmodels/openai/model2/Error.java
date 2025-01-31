/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.model2;

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

/** Error */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class Error
// CHECKSTYLE:ON
{
  @JsonProperty("param")
  private String param;

  @JsonProperty("type")
  private String type;

  @JsonProperty("inner_error")
  private InnerError innerError;

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the param of this {@link Error} instance and return the same instance.
   *
   * @param param The param of this {@link Error}
   * @return The same instance of this {@link Error} class
   */
  @Nonnull
  public Error param(@Nullable final String param) {
    this.param = param;
    return this;
  }

  /**
   * Get param
   *
   * @return param The param of this {@link Error} instance.
   */
  @Nonnull
  public String getParam() {
    return param;
  }

  /**
   * Set the param of this {@link Error} instance.
   *
   * @param param The param of this {@link Error}
   */
  public void setParam(@Nullable final String param) {
    this.param = param;
  }

  /**
   * Set the type of this {@link Error} instance and return the same instance.
   *
   * @param type The type of this {@link Error}
   * @return The same instance of this {@link Error} class
   */
  @Nonnull
  public Error type(@Nullable final String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type The type of this {@link Error} instance.
   */
  @Nonnull
  public String getType() {
    return type;
  }

  /**
   * Set the type of this {@link Error} instance.
   *
   * @param type The type of this {@link Error}
   */
  public void setType(@Nullable final String type) {
    this.type = type;
  }

  /**
   * Set the innerError of this {@link Error} instance and return the same instance.
   *
   * @param innerError The innerError of this {@link Error}
   * @return The same instance of this {@link Error} class
   */
  @Nonnull
  public Error innerError(@Nullable final InnerError innerError) {
    this.innerError = innerError;
    return this;
  }

  /**
   * Get innerError
   *
   * @return innerError The innerError of this {@link Error} instance.
   */
  @Nonnull
  public InnerError getInnerError() {
    return innerError;
  }

  /**
   * Set the innerError of this {@link Error} instance.
   *
   * @param innerError The innerError of this {@link Error}
   */
  public void setInnerError(@Nullable final InnerError innerError) {
    this.innerError = innerError;
  }

  /**
   * Set the code of this {@link Error} instance and return the same instance.
   *
   * @param code The code of this {@link Error}
   * @return The same instance of this {@link Error} class
   */
  @Nonnull
  public Error code(@Nullable final String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code The code of this {@link Error} instance.
   */
  @Nonnull
  public String getCode() {
    return code;
  }

  /**
   * Set the code of this {@link Error} instance.
   *
   * @param code The code of this {@link Error}
   */
  public void setCode(@Nullable final String code) {
    this.code = code;
  }

  /**
   * Set the message of this {@link Error} instance and return the same instance.
   *
   * @param message The message of this {@link Error}
   * @return The same instance of this {@link Error} class
   */
  @Nonnull
  public Error message(@Nullable final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link Error} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link Error} instance.
   *
   * @param message The message of this {@link Error}
   */
  public void setMessage(@Nullable final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link Error}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link Error} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("Error has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link Error} instance. If the map previously contained
   * a mapping for the key, the old value is replaced by the specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
    cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Error error = (Error) o;
    return Objects.equals(this.cloudSdkCustomFields, error.cloudSdkCustomFields)
        && Objects.equals(this.param, error.param)
        && Objects.equals(this.type, error.type)
        && Objects.equals(this.innerError, error.innerError)
        && Objects.equals(this.code, error.code)
        && Objects.equals(this.message, error.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(param, type, innerError, code, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    param: ").append(toIndentedString(param)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    innerError: ").append(toIndentedString(innerError)).append("\n");
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
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
