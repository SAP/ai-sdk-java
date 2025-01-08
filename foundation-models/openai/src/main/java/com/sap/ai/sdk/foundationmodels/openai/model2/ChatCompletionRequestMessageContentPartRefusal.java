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

/** ChatCompletionRequestMessageContentPartRefusal */
// CHECKSTYLE:OFF
public class ChatCompletionRequestMessageContentPartRefusal
    implements ChatCompletionRequestAssistantMessageContentPart
// CHECKSTYLE:ON
{
  /** The type of the content part. */
  public enum TypeEnum {
    /** The REFUSAL option of this ChatCompletionRequestMessageContentPartRefusal */
    REFUSAL("refusal");

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
     * @return The enum value of type ChatCompletionRequestMessageContentPartRefusal
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

  @JsonProperty("refusal")
  private String refusal;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the type of this {@link ChatCompletionRequestMessageContentPartRefusal} instance and return
   * the same instance.
   *
   * @param type The type of the content part.
   * @return The same instance of this {@link ChatCompletionRequestMessageContentPartRefusal} class
   */
  @Nonnull
  public ChatCompletionRequestMessageContentPartRefusal type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of the content part.
   *
   * @return type The type of this {@link ChatCompletionRequestMessageContentPartRefusal} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link ChatCompletionRequestMessageContentPartRefusal} instance.
   *
   * @param type The type of the content part.
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the refusal of this {@link ChatCompletionRequestMessageContentPartRefusal} instance and
   * return the same instance.
   *
   * @param refusal The refusal message generated by the model.
   * @return The same instance of this {@link ChatCompletionRequestMessageContentPartRefusal} class
   */
  @Nonnull
  public ChatCompletionRequestMessageContentPartRefusal refusal(@Nonnull final String refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * The refusal message generated by the model.
   *
   * @return refusal The refusal of this {@link ChatCompletionRequestMessageContentPartRefusal}
   *     instance.
   */
  @Nonnull
  public String getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link ChatCompletionRequestMessageContentPartRefusal} instance.
   *
   * @param refusal The refusal message generated by the model.
   */
  public void setRefusal(@Nonnull final String refusal) {
    this.refusal = refusal;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionRequestMessageContentPartRefusal}.
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
   * ChatCompletionRequestMessageContentPartRefusal} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionRequestMessageContentPartRefusal has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionRequestMessageContentPartRefusal}
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
    final ChatCompletionRequestMessageContentPartRefusal
        chatCompletionRequestMessageContentPartRefusal =
            (ChatCompletionRequestMessageContentPartRefusal) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            chatCompletionRequestMessageContentPartRefusal.cloudSdkCustomFields)
        && Objects.equals(this.type, chatCompletionRequestMessageContentPartRefusal.type)
        && Objects.equals(this.refusal, chatCompletionRequestMessageContentPartRefusal.refusal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, refusal, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionRequestMessageContentPartRefusal {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    refusal: ").append(toIndentedString(refusal)).append("\n");
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
