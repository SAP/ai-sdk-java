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

/** CreateChatCompletionStreamResponseChoicesInner */
// CHECKSTYLE:OFF
public class CreateChatCompletionStreamResponseChoicesInner
// CHECKSTYLE:ON
{
  @JsonProperty("delta")
  private ChatCompletionStreamResponseDelta delta;

  @JsonProperty("logprobs")
  private CreateChatCompletionResponseChoicesInnerLogprobs logprobs;

  /**
   * The reason the model stopped generating tokens. This will be &#x60;stop&#x60; if the model hit
   * a natural stop point or a provided stop sequence, &#x60;length&#x60; if the maximum number of
   * tokens specified in the request was reached, &#x60;content_filter&#x60; if content was omitted
   * due to a flag from our content filters, &#x60;tool_calls&#x60; if the model called a tool, or
   * &#x60;function_call&#x60; (deprecated) if the model called a function.
   */
  public enum FinishReasonEnum {
    /** The STOP option of this CreateChatCompletionStreamResponseChoicesInner */
    STOP("stop"),

    /** The LENGTH option of this CreateChatCompletionStreamResponseChoicesInner */
    LENGTH("length"),

    /** The TOOL_CALLS option of this CreateChatCompletionStreamResponseChoicesInner */
    TOOL_CALLS("tool_calls"),

    /** The CONTENT_FILTER option of this CreateChatCompletionStreamResponseChoicesInner */
    CONTENT_FILTER("content_filter"),

    /** The FUNCTION_CALL option of this CreateChatCompletionStreamResponseChoicesInner */
    FUNCTION_CALL("function_call");

    private String value;

    FinishReasonEnum(String value) {
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
     * @return The enum value of type CreateChatCompletionStreamResponseChoicesInner
     */
    @JsonCreator
    @Nonnull
    public static FinishReasonEnum fromValue(@Nonnull final String value) {
      for (FinishReasonEnum b : FinishReasonEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("finish_reason")
  private FinishReasonEnum finishReason;

  @JsonProperty("index")
  private Integer index;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the delta of this {@link CreateChatCompletionStreamResponseChoicesInner} instance and
   * return the same instance.
   *
   * @param delta The delta of this {@link CreateChatCompletionStreamResponseChoicesInner}
   * @return The same instance of this {@link CreateChatCompletionStreamResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionStreamResponseChoicesInner delta(
      @Nonnull final ChatCompletionStreamResponseDelta delta) {
    this.delta = delta;
    return this;
  }

  /**
   * Get delta
   *
   * @return delta The delta of this {@link CreateChatCompletionStreamResponseChoicesInner}
   *     instance.
   */
  @Nonnull
  public ChatCompletionStreamResponseDelta getDelta() {
    return delta;
  }

  /**
   * Set the delta of this {@link CreateChatCompletionStreamResponseChoicesInner} instance.
   *
   * @param delta The delta of this {@link CreateChatCompletionStreamResponseChoicesInner}
   */
  public void setDelta(@Nonnull final ChatCompletionStreamResponseDelta delta) {
    this.delta = delta;
  }

  /**
   * Set the logprobs of this {@link CreateChatCompletionStreamResponseChoicesInner} instance and
   * return the same instance.
   *
   * @param logprobs The logprobs of this {@link CreateChatCompletionStreamResponseChoicesInner}
   * @return The same instance of this {@link CreateChatCompletionStreamResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionStreamResponseChoicesInner logprobs(
      @Nullable final CreateChatCompletionResponseChoicesInnerLogprobs logprobs) {
    this.logprobs = logprobs;
    return this;
  }

  /**
   * Get logprobs
   *
   * @return logprobs The logprobs of this {@link CreateChatCompletionStreamResponseChoicesInner}
   *     instance.
   */
  @Nullable
  public CreateChatCompletionResponseChoicesInnerLogprobs getLogprobs() {
    return logprobs;
  }

  /**
   * Set the logprobs of this {@link CreateChatCompletionStreamResponseChoicesInner} instance.
   *
   * @param logprobs The logprobs of this {@link CreateChatCompletionStreamResponseChoicesInner}
   */
  public void setLogprobs(
      @Nullable final CreateChatCompletionResponseChoicesInnerLogprobs logprobs) {
    this.logprobs = logprobs;
  }

  /**
   * Set the finishReason of this {@link CreateChatCompletionStreamResponseChoicesInner} instance
   * and return the same instance.
   *
   * @param finishReason The reason the model stopped generating tokens. This will be
   *     &#x60;stop&#x60; if the model hit a natural stop point or a provided stop sequence,
   *     &#x60;length&#x60; if the maximum number of tokens specified in the request was reached,
   *     &#x60;content_filter&#x60; if content was omitted due to a flag from our content filters,
   *     &#x60;tool_calls&#x60; if the model called a tool, or &#x60;function_call&#x60;
   *     (deprecated) if the model called a function.
   * @return The same instance of this {@link CreateChatCompletionStreamResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionStreamResponseChoicesInner finishReason(
      @Nullable final FinishReasonEnum finishReason) {
    this.finishReason = finishReason;
    return this;
  }

  /**
   * The reason the model stopped generating tokens. This will be &#x60;stop&#x60; if the model hit
   * a natural stop point or a provided stop sequence, &#x60;length&#x60; if the maximum number of
   * tokens specified in the request was reached, &#x60;content_filter&#x60; if content was omitted
   * due to a flag from our content filters, &#x60;tool_calls&#x60; if the model called a tool, or
   * &#x60;function_call&#x60; (deprecated) if the model called a function.
   *
   * @return finishReason The finishReason of this {@link
   *     CreateChatCompletionStreamResponseChoicesInner} instance.
   */
  @Nullable
  public FinishReasonEnum getFinishReason() {
    return finishReason;
  }

  /**
   * Set the finishReason of this {@link CreateChatCompletionStreamResponseChoicesInner} instance.
   *
   * @param finishReason The reason the model stopped generating tokens. This will be
   *     &#x60;stop&#x60; if the model hit a natural stop point or a provided stop sequence,
   *     &#x60;length&#x60; if the maximum number of tokens specified in the request was reached,
   *     &#x60;content_filter&#x60; if content was omitted due to a flag from our content filters,
   *     &#x60;tool_calls&#x60; if the model called a tool, or &#x60;function_call&#x60;
   *     (deprecated) if the model called a function.
   */
  public void setFinishReason(@Nullable final FinishReasonEnum finishReason) {
    this.finishReason = finishReason;
  }

  /**
   * Set the index of this {@link CreateChatCompletionStreamResponseChoicesInner} instance and
   * return the same instance.
   *
   * @param index The index of the choice in the list of choices.
   * @return The same instance of this {@link CreateChatCompletionStreamResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionStreamResponseChoicesInner index(@Nonnull final Integer index) {
    this.index = index;
    return this;
  }

  /**
   * The index of the choice in the list of choices.
   *
   * @return index The index of this {@link CreateChatCompletionStreamResponseChoicesInner}
   *     instance.
   */
  @Nonnull
  public Integer getIndex() {
    return index;
  }

  /**
   * Set the index of this {@link CreateChatCompletionStreamResponseChoicesInner} instance.
   *
   * @param index The index of the choice in the list of choices.
   */
  public void setIndex(@Nonnull final Integer index) {
    this.index = index;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * CreateChatCompletionStreamResponseChoicesInner}.
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
   * CreateChatCompletionStreamResponseChoicesInner} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "CreateChatCompletionStreamResponseChoicesInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link CreateChatCompletionStreamResponseChoicesInner}
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
    final CreateChatCompletionStreamResponseChoicesInner
        createChatCompletionStreamResponseChoicesInner =
            (CreateChatCompletionStreamResponseChoicesInner) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            createChatCompletionStreamResponseChoicesInner.cloudSdkCustomFields)
        && Objects.equals(this.delta, createChatCompletionStreamResponseChoicesInner.delta)
        && Objects.equals(this.logprobs, createChatCompletionStreamResponseChoicesInner.logprobs)
        && Objects.equals(
            this.finishReason, createChatCompletionStreamResponseChoicesInner.finishReason)
        && Objects.equals(this.index, createChatCompletionStreamResponseChoicesInner.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delta, logprobs, finishReason, index, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CreateChatCompletionStreamResponseChoicesInner {\n");
    sb.append("    delta: ").append(toIndentedString(delta)).append("\n");
    sb.append("    logprobs: ").append(toIndentedString(logprobs)).append("\n");
    sb.append("    finishReason: ").append(toIndentedString(finishReason)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
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
