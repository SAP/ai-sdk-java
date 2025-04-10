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

/** ChatCompletionRequestUserMessage */
// CHECKSTYLE:OFF
public class ChatCompletionRequestUserMessage implements ChatCompletionRequestMessage
// CHECKSTYLE:ON
{
  @JsonProperty("content")
  private ChatCompletionRequestUserMessageContent content;

  /** The role of the messages author, in this case &#x60;user&#x60;. */
  public enum RoleEnum {
    /** The USER option of this ChatCompletionRequestUserMessage */
    USER("user"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this ChatCompletionRequestUserMessage */
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
     * @return The enum value of type ChatCompletionRequestUserMessage
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

  @JsonProperty("name")
  private String name;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the content of this {@link ChatCompletionRequestUserMessage} instance and return the same
   * instance.
   *
   * @param content The content of this {@link ChatCompletionRequestUserMessage}
   * @return The same instance of this {@link ChatCompletionRequestUserMessage} class
   */
  @Nonnull
  public ChatCompletionRequestUserMessage content(
      @Nonnull final ChatCompletionRequestUserMessageContent content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link ChatCompletionRequestUserMessage} instance.
   */
  @Nonnull
  public ChatCompletionRequestUserMessageContent getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatCompletionRequestUserMessage} instance.
   *
   * @param content The content of this {@link ChatCompletionRequestUserMessage}
   */
  public void setContent(@Nonnull final ChatCompletionRequestUserMessageContent content) {
    this.content = content;
  }

  /**
   * Set the role of this {@link ChatCompletionRequestUserMessage} instance and return the same
   * instance.
   *
   * @param role The role of the messages author, in this case &#x60;user&#x60;.
   * @return The same instance of this {@link ChatCompletionRequestUserMessage} class
   */
  @Nonnull
  public ChatCompletionRequestUserMessage role(@Nonnull final RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the messages author, in this case &#x60;user&#x60;.
   *
   * @return role The role of this {@link ChatCompletionRequestUserMessage} instance.
   */
  @Nonnull
  public RoleEnum getRole() {
    return role;
  }

  /**
   * Set the role of this {@link ChatCompletionRequestUserMessage} instance.
   *
   * @param role The role of the messages author, in this case &#x60;user&#x60;.
   */
  public void setRole(@Nonnull final RoleEnum role) {
    this.role = role;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestUserMessage} instance and return the same
   * instance.
   *
   * @param name An optional name for the participant. Provides the model information to
   *     differentiate between participants of the same role.
   * @return The same instance of this {@link ChatCompletionRequestUserMessage} class
   */
  @Nonnull
  public ChatCompletionRequestUserMessage name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * An optional name for the participant. Provides the model information to differentiate between
   * participants of the same role.
   *
   * @return name The name of this {@link ChatCompletionRequestUserMessage} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestUserMessage} instance.
   *
   * @param name An optional name for the participant. Provides the model information to
   *     differentiate between participants of the same role.
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionRequestUserMessage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionRequestUserMessage}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionRequestUserMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionRequestUserMessage} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final ChatCompletionRequestUserMessage chatCompletionRequestUserMessage =
        (ChatCompletionRequestUserMessage) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionRequestUserMessage.cloudSdkCustomFields)
        && Objects.equals(this.content, chatCompletionRequestUserMessage.content)
        && Objects.equals(this.role, chatCompletionRequestUserMessage.role)
        && Objects.equals(this.name, chatCompletionRequestUserMessage.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, role, name, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionRequestUserMessage {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
