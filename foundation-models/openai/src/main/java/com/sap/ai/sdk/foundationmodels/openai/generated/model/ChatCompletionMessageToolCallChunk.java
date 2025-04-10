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

/** ChatCompletionMessageToolCallChunk */
// CHECKSTYLE:OFF
public class ChatCompletionMessageToolCallChunk
// CHECKSTYLE:ON
{
  @JsonProperty("index")
  private Integer index;

  @JsonProperty("id")
  private String id;

  /** The type of the tool. Currently, only &#x60;function&#x60; is supported. */
  public enum TypeEnum {
    /** The FUNCTION option of this ChatCompletionMessageToolCallChunk */
    FUNCTION("function"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this ChatCompletionMessageToolCallChunk */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

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
     * @return The enum value of type ChatCompletionMessageToolCallChunk
     */
    @JsonCreator
    @Nonnull
    public static TypeEnum fromValue(@Nonnull final String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("function")
  private ChatCompletionMessageToolCallChunkFunction function;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the index of this {@link ChatCompletionMessageToolCallChunk} instance and return the same
   * instance.
   *
   * @param index The index of this {@link ChatCompletionMessageToolCallChunk}
   * @return The same instance of this {@link ChatCompletionMessageToolCallChunk} class
   */
  @Nonnull
  public ChatCompletionMessageToolCallChunk index(@Nonnull final Integer index) {
    this.index = index;
    return this;
  }

  /**
   * Get index
   *
   * @return index The index of this {@link ChatCompletionMessageToolCallChunk} instance.
   */
  @Nonnull
  public Integer getIndex() {
    return index;
  }

  /**
   * Set the index of this {@link ChatCompletionMessageToolCallChunk} instance.
   *
   * @param index The index of this {@link ChatCompletionMessageToolCallChunk}
   */
  public void setIndex(@Nonnull final Integer index) {
    this.index = index;
  }

  /**
   * Set the id of this {@link ChatCompletionMessageToolCallChunk} instance and return the same
   * instance.
   *
   * @param id The ID of the tool call.
   * @return The same instance of this {@link ChatCompletionMessageToolCallChunk} class
   */
  @Nonnull
  public ChatCompletionMessageToolCallChunk id(@Nullable final String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the tool call.
   *
   * @return id The id of this {@link ChatCompletionMessageToolCallChunk} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link ChatCompletionMessageToolCallChunk} instance.
   *
   * @param id The ID of the tool call.
   */
  public void setId(@Nullable final String id) {
    this.id = id;
  }

  /**
   * Set the type of this {@link ChatCompletionMessageToolCallChunk} instance and return the same
   * instance.
   *
   * @param type The type of the tool. Currently, only &#x60;function&#x60; is supported.
   * @return The same instance of this {@link ChatCompletionMessageToolCallChunk} class
   */
  @Nonnull
  public ChatCompletionMessageToolCallChunk type(@Nullable final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of the tool. Currently, only &#x60;function&#x60; is supported.
   *
   * @return type The type of this {@link ChatCompletionMessageToolCallChunk} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link ChatCompletionMessageToolCallChunk} instance.
   *
   * @param type The type of the tool. Currently, only &#x60;function&#x60; is supported.
   */
  public void setType(@Nullable final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the function of this {@link ChatCompletionMessageToolCallChunk} instance and return the
   * same instance.
   *
   * @param function The function of this {@link ChatCompletionMessageToolCallChunk}
   * @return The same instance of this {@link ChatCompletionMessageToolCallChunk} class
   */
  @Nonnull
  public ChatCompletionMessageToolCallChunk function(
      @Nullable final ChatCompletionMessageToolCallChunkFunction function) {
    this.function = function;
    return this;
  }

  /**
   * Get function
   *
   * @return function The function of this {@link ChatCompletionMessageToolCallChunk} instance.
   */
  @Nonnull
  public ChatCompletionMessageToolCallChunkFunction getFunction() {
    return function;
  }

  /**
   * Set the function of this {@link ChatCompletionMessageToolCallChunk} instance.
   *
   * @param function The function of this {@link ChatCompletionMessageToolCallChunk}
   */
  public void setFunction(@Nullable final ChatCompletionMessageToolCallChunkFunction function) {
    this.function = function;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionMessageToolCallChunk}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionMessageToolCallChunk}
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
          "ChatCompletionMessageToolCallChunk has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionMessageToolCallChunk} instance. If
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
    final ChatCompletionMessageToolCallChunk chatCompletionMessageToolCallChunk =
        (ChatCompletionMessageToolCallChunk) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionMessageToolCallChunk.cloudSdkCustomFields)
        && Objects.equals(this.index, chatCompletionMessageToolCallChunk.index)
        && Objects.equals(this.id, chatCompletionMessageToolCallChunk.id)
        && Objects.equals(this.type, chatCompletionMessageToolCallChunk.type)
        && Objects.equals(this.function, chatCompletionMessageToolCallChunk.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, id, type, function, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionMessageToolCallChunk {\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    function: ").append(toIndentedString(function)).append("\n");
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
