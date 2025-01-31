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

/** ChatCompletionRequestMessageContentPartText */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class ChatCompletionRequestMessageContentPartText
    implements ChatCompletionRequestAssistantMessageContentPart,
        ChatCompletionRequestSystemMessageContentPart,
        ChatCompletionRequestToolMessageContentPart,
        ChatCompletionRequestUserMessageContentPart
// CHECKSTYLE:ON
{
  /** The type of the content part. */
  public enum TypeEnum {
    /** The TEXT option of this ChatCompletionRequestMessageContentPartText */
    TEXT("text");

    private String value;

    TypeEnum(String value) {
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
     * @return The enum value of type ChatCompletionRequestMessageContentPartText
     */
    @JsonCreator
    @Nonnull
    public static TypeEnum fromValue(@Nonnull final String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("text")
  private String text;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the type of this {@link ChatCompletionRequestMessageContentPartText} instance and return
   * the same instance.
   *
   * @param type The type of the content part.
   * @return The same instance of this {@link ChatCompletionRequestMessageContentPartText} class
   */
  @Nonnull
  public ChatCompletionRequestMessageContentPartText type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of the content part.
   *
   * @return type The type of this {@link ChatCompletionRequestMessageContentPartText} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link ChatCompletionRequestMessageContentPartText} instance.
   *
   * @param type The type of the content part.
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the text of this {@link ChatCompletionRequestMessageContentPartText} instance and return
   * the same instance.
   *
   * @param text The text content.
   * @return The same instance of this {@link ChatCompletionRequestMessageContentPartText} class
   */
  @Nonnull
  public ChatCompletionRequestMessageContentPartText text(@Nonnull final String text) {
    this.text = text;
    return this;
  }

  /**
   * The text content.
   *
   * @return text The text of this {@link ChatCompletionRequestMessageContentPartText} instance.
   */
  @Nonnull
  public String getText() {
    return text;
  }

  /**
   * Set the text of this {@link ChatCompletionRequestMessageContentPartText} instance.
   *
   * @param text The text content.
   */
  public void setText(@Nonnull final String text) {
    this.text = text;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionRequestMessageContentPartText}.
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
   * ChatCompletionRequestMessageContentPartText} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionRequestMessageContentPartText has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionRequestMessageContentPartText}
   * instance. If the map previously contained a mapping for the key, the old value is replaced by
   * the specified value.
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
    final ChatCompletionRequestMessageContentPartText chatCompletionRequestMessageContentPartText =
        (ChatCompletionRequestMessageContentPartText) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            chatCompletionRequestMessageContentPartText.cloudSdkCustomFields)
        && Objects.equals(this.type, chatCompletionRequestMessageContentPartText.type)
        && Objects.equals(this.text, chatCompletionRequestMessageContentPartText.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, text, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionRequestMessageContentPartText {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
