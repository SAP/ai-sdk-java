/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
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

/** CreateChatCompletionResponseChoicesInner */
// CHECKSTYLE:OFF
public class CreateChatCompletionResponseChoicesInner
// CHECKSTYLE:ON
{
  /**
   * The reason the model stopped generating tokens. This will be &#x60;stop&#x60; if the model hit
   * a natural stop point or a provided stop sequence, &#x60;length&#x60; if the maximum number of
   * tokens specified in the request was reached, &#x60;content_filter&#x60; if content was omitted
   * due to a flag from our content filters, &#x60;tool_calls&#x60; if the model called a tool, or
   * &#x60;function_call&#x60; (deprecated) if the model called a function.
   */
  public enum FinishReasonEnum {
    /** The STOP option of this CreateChatCompletionResponseChoicesInner */
    STOP("stop"),

    /** The LENGTH option of this CreateChatCompletionResponseChoicesInner */
    LENGTH("length"),

    /** The TOOL_CALLS option of this CreateChatCompletionResponseChoicesInner */
    TOOL_CALLS("tool_calls"),

    /** The CONTENT_FILTER option of this CreateChatCompletionResponseChoicesInner */
    CONTENT_FILTER("content_filter"),

    /** The FUNCTION_CALL option of this CreateChatCompletionResponseChoicesInner */
    FUNCTION_CALL("function_call"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this CreateChatCompletionResponseChoicesInner */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

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
     * @return The enum value of type CreateChatCompletionResponseChoicesInner
     */
    @JsonCreator
    @Nonnull
    public static FinishReasonEnum fromValue(@Nonnull final String value) {
      for (FinishReasonEnum b : FinishReasonEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("finish_reason")
  private FinishReasonEnum finishReason;

  @JsonProperty("index")
  private Integer index;

  @JsonProperty("message")
  private ChatCompletionResponseMessage message;

  @JsonProperty("content_filter_results")
  private ContentFilterChoiceResults contentFilterResults;

  @JsonProperty("logprobs")
  private CreateChatCompletionResponseChoicesInnerLogprobs logprobs;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the finishReason of this {@link CreateChatCompletionResponseChoicesInner} instance and
   * return the same instance.
   *
   * @param finishReason The reason the model stopped generating tokens. This will be
   *     &#x60;stop&#x60; if the model hit a natural stop point or a provided stop sequence,
   *     &#x60;length&#x60; if the maximum number of tokens specified in the request was reached,
   *     &#x60;content_filter&#x60; if content was omitted due to a flag from our content filters,
   *     &#x60;tool_calls&#x60; if the model called a tool, or &#x60;function_call&#x60;
   *     (deprecated) if the model called a function.
   * @return The same instance of this {@link CreateChatCompletionResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner finishReason(
      @Nonnull final FinishReasonEnum finishReason) {
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
   * @return finishReason The finishReason of this {@link CreateChatCompletionResponseChoicesInner}
   *     instance.
   */
  @Nonnull
  public FinishReasonEnum getFinishReason() {
    return finishReason;
  }

  /**
   * Set the finishReason of this {@link CreateChatCompletionResponseChoicesInner} instance.
   *
   * @param finishReason The reason the model stopped generating tokens. This will be
   *     &#x60;stop&#x60; if the model hit a natural stop point or a provided stop sequence,
   *     &#x60;length&#x60; if the maximum number of tokens specified in the request was reached,
   *     &#x60;content_filter&#x60; if content was omitted due to a flag from our content filters,
   *     &#x60;tool_calls&#x60; if the model called a tool, or &#x60;function_call&#x60;
   *     (deprecated) if the model called a function.
   */
  public void setFinishReason(@Nonnull final FinishReasonEnum finishReason) {
    this.finishReason = finishReason;
  }

  /**
   * Set the index of this {@link CreateChatCompletionResponseChoicesInner} instance and return the
   * same instance.
   *
   * @param index The index of the choice in the list of choices.
   * @return The same instance of this {@link CreateChatCompletionResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner index(@Nonnull final Integer index) {
    this.index = index;
    return this;
  }

  /**
   * The index of the choice in the list of choices.
   *
   * @return index The index of this {@link CreateChatCompletionResponseChoicesInner} instance.
   */
  @Nonnull
  public Integer getIndex() {
    return index;
  }

  /**
   * Set the index of this {@link CreateChatCompletionResponseChoicesInner} instance.
   *
   * @param index The index of the choice in the list of choices.
   */
  public void setIndex(@Nonnull final Integer index) {
    this.index = index;
  }

  /**
   * Set the message of this {@link CreateChatCompletionResponseChoicesInner} instance and return
   * the same instance.
   *
   * @param message The message of this {@link CreateChatCompletionResponseChoicesInner}
   * @return The same instance of this {@link CreateChatCompletionResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner message(
      @Nonnull final ChatCompletionResponseMessage message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link CreateChatCompletionResponseChoicesInner} instance.
   */
  @Nonnull
  public ChatCompletionResponseMessage getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link CreateChatCompletionResponseChoicesInner} instance.
   *
   * @param message The message of this {@link CreateChatCompletionResponseChoicesInner}
   */
  public void setMessage(@Nonnull final ChatCompletionResponseMessage message) {
    this.message = message;
  }

  /**
   * Set the contentFilterResults of this {@link CreateChatCompletionResponseChoicesInner} instance
   * and return the same instance.
   *
   * @param contentFilterResults The contentFilterResults of this {@link
   *     CreateChatCompletionResponseChoicesInner}
   * @return The same instance of this {@link CreateChatCompletionResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner contentFilterResults(
      @Nullable final ContentFilterChoiceResults contentFilterResults) {
    this.contentFilterResults = contentFilterResults;
    return this;
  }

  /**
   * Get contentFilterResults
   *
   * @return contentFilterResults The contentFilterResults of this {@link
   *     CreateChatCompletionResponseChoicesInner} instance.
   */
  @Nonnull
  public ContentFilterChoiceResults getContentFilterResults() {
    return contentFilterResults;
  }

  /**
   * Set the contentFilterResults of this {@link CreateChatCompletionResponseChoicesInner} instance.
   *
   * @param contentFilterResults The contentFilterResults of this {@link
   *     CreateChatCompletionResponseChoicesInner}
   */
  public void setContentFilterResults(
      @Nullable final ContentFilterChoiceResults contentFilterResults) {
    this.contentFilterResults = contentFilterResults;
  }

  /**
   * Set the logprobs of this {@link CreateChatCompletionResponseChoicesInner} instance and return
   * the same instance.
   *
   * @param logprobs The logprobs of this {@link CreateChatCompletionResponseChoicesInner}
   * @return The same instance of this {@link CreateChatCompletionResponseChoicesInner} class
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner logprobs(
      @Nullable final CreateChatCompletionResponseChoicesInnerLogprobs logprobs) {
    this.logprobs = logprobs;
    return this;
  }

  /**
   * Get logprobs
   *
   * @return logprobs The logprobs of this {@link CreateChatCompletionResponseChoicesInner}
   *     instance.
   */
  @Nullable
  public CreateChatCompletionResponseChoicesInnerLogprobs getLogprobs() {
    return logprobs;
  }

  /**
   * Set the logprobs of this {@link CreateChatCompletionResponseChoicesInner} instance.
   *
   * @param logprobs The logprobs of this {@link CreateChatCompletionResponseChoicesInner}
   */
  public void setLogprobs(
      @Nullable final CreateChatCompletionResponseChoicesInnerLogprobs logprobs) {
    this.logprobs = logprobs;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * CreateChatCompletionResponseChoicesInner}.
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
   * CreateChatCompletionResponseChoicesInner} instance.
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
      throw new NoSuchElementException(
          "CreateChatCompletionResponseChoicesInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link CreateChatCompletionResponseChoicesInner}
   * instance including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (finishReason != null) declaredFields.put("finishReason", finishReason);
    if (index != null) declaredFields.put("index", index);
    if (message != null) declaredFields.put("message", message);
    if (contentFilterResults != null)
      declaredFields.put("contentFilterResults", contentFilterResults);
    if (logprobs != null) declaredFields.put("logprobs", logprobs);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link CreateChatCompletionResponseChoicesInner}
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
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CreateChatCompletionResponseChoicesInner createChatCompletionResponseChoicesInner =
        (CreateChatCompletionResponseChoicesInner) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            createChatCompletionResponseChoicesInner.cloudSdkCustomFields)
        && Objects.equals(this.finishReason, createChatCompletionResponseChoicesInner.finishReason)
        && Objects.equals(this.index, createChatCompletionResponseChoicesInner.index)
        && Objects.equals(this.message, createChatCompletionResponseChoicesInner.message)
        && Objects.equals(
            this.contentFilterResults,
            createChatCompletionResponseChoicesInner.contentFilterResults)
        && Objects.equals(this.logprobs, createChatCompletionResponseChoicesInner.logprobs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        finishReason, index, message, contentFilterResults, logprobs, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CreateChatCompletionResponseChoicesInner {\n");
    sb.append("    finishReason: ").append(toIndentedString(finishReason)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    contentFilterResults: ")
        .append(toIndentedString(contentFilterResults))
        .append("\n");
    sb.append("    logprobs: ").append(toIndentedString(logprobs)).append("\n");
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
}
