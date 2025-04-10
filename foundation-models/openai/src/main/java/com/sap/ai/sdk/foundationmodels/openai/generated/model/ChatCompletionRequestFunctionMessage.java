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

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ChatCompletionRequestFunctionMessage
 *
 * @deprecated
 */
@Deprecated
// CHECKSTYLE:OFF
public class ChatCompletionRequestFunctionMessage implements ChatCompletionRequestMessage
// CHECKSTYLE:ON
{
  /** The role of the messages author, in this case &#x60;function&#x60;. */
  public enum RoleEnum {
    /** The FUNCTION option of this ChatCompletionRequestFunctionMessage */
    FUNCTION("function"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this ChatCompletionRequestFunctionMessage */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type ChatCompletionRequestFunctionMessage
     */
    @JsonCreator
    @Nonnull
    public static RoleEnum fromValue(@Nonnull final String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("role")
  private RoleEnum role;

  @JsonProperty("content")
  private String content;

  @JsonProperty("name")
  private String name;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the role of this {@link ChatCompletionRequestFunctionMessage} instance and return the same
   * instance.
   *
   * @param role The role of the messages author, in this case &#x60;function&#x60;.
   * @return The same instance of this {@link ChatCompletionRequestFunctionMessage} class
   */
  @Nonnull
  public ChatCompletionRequestFunctionMessage role(@Nonnull final RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the messages author, in this case &#x60;function&#x60;.
   *
   * @return role The role of this {@link ChatCompletionRequestFunctionMessage} instance.
   */
  @Nonnull
  public RoleEnum getRole() {
    return role;
  }

  /**
   * Set the role of this {@link ChatCompletionRequestFunctionMessage} instance.
   *
   * @param role The role of the messages author, in this case &#x60;function&#x60;.
   */
  public void setRole(@Nonnull final RoleEnum role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link ChatCompletionRequestFunctionMessage} instance and return the
   * same instance.
   *
   * @param content The contents of the function message.
   * @return The same instance of this {@link ChatCompletionRequestFunctionMessage} class
   */
  @Nonnull
  public ChatCompletionRequestFunctionMessage content(@Nullable final String content) {
    this.content = content;
    return this;
  }

  /**
   * The contents of the function message.
   *
   * @return content The content of this {@link ChatCompletionRequestFunctionMessage} instance.
   */
  @Nullable
  public String getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatCompletionRequestFunctionMessage} instance.
   *
   * @param content The contents of the function message.
   */
  public void setContent(@Nullable final String content) {
    this.content = content;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestFunctionMessage} instance and return the same
   * instance.
   *
   * @param name The name of the function to call.
   * @return The same instance of this {@link ChatCompletionRequestFunctionMessage} class
   */
  @Nonnull
  public ChatCompletionRequestFunctionMessage name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the function to call.
   *
   * @return name The name of this {@link ChatCompletionRequestFunctionMessage} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestFunctionMessage} instance.
   *
   * @param name The name of the function to call.
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionRequestFunctionMessage}.
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
   * ChatCompletionRequestFunctionMessage} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionRequestFunctionMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionRequestFunctionMessage} instance.
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChatCompletionRequestFunctionMessage chatCompletionRequestFunctionMessage =
        (ChatCompletionRequestFunctionMessage) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionRequestFunctionMessage.cloudSdkCustomFields)
        && Objects.equals(this.role, chatCompletionRequestFunctionMessage.role)
        && Objects.equals(this.content, chatCompletionRequestFunctionMessage.content)
        && Objects.equals(this.name, chatCompletionRequestFunctionMessage.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, name, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionRequestFunctionMessage {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
