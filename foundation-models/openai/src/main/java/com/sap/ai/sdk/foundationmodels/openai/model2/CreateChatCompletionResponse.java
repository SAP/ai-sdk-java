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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Represents a chat completion response returned by model, based on the provided input. */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class CreateChatCompletionResponse implements ChatCompletionsCreate200Response
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("prompt_filter_results")
  private List<PromptFilterResult> promptFilterResults = new ArrayList<>();

  @JsonProperty("choices")
  private List<CreateChatCompletionResponseChoicesInner> choices = new ArrayList<>();

  @JsonProperty("created")
  private Integer created;

  @JsonProperty("model")
  private String model;

  @JsonProperty("system_fingerprint")
  private String systemFingerprint;

  /** The object type, which is always &#x60;chat.completion&#x60;. */
  public enum ObjectEnum {
    /** The CHAT_COMPLETION option of this CreateChatCompletionResponse */
    CHAT_COMPLETION("chat.completion"),
    /** The UNKNOWN_DEFAULT_OPEN_API option of this CreateChatCompletionResponse */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");
    ;

    private String value;

    ObjectEnum(String value) {
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
     * @return The enum value of type CreateChatCompletionResponse
     */
    @JsonCreator
    @Nonnull
    public static ObjectEnum fromValue(@Nonnull final String value) {
      for (ObjectEnum b : ObjectEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("object")
  private ObjectEnum _object;

  @JsonProperty("usage")
  private CompletionUsage usage;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the id of this {@link CreateChatCompletionResponse} instance and return the same instance.
   *
   * @param id A unique identifier for the chat completion.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * A unique identifier for the chat completion.
   *
   * @return id The id of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link CreateChatCompletionResponse} instance.
   *
   * @param id A unique identifier for the chat completion.
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the promptFilterResults of this {@link CreateChatCompletionResponse} instance and return
   * the same instance.
   *
   * @param promptFilterResults Content filtering results for zero or more prompts in the request.
   *     In a streaming request, results for different prompts may arrive at different times or in
   *     different orders.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse promptFilterResults(
      @Nullable final List<PromptFilterResult> promptFilterResults) {
    this.promptFilterResults = promptFilterResults;
    return this;
  }

  /**
   * Add one promptFilterResults instance to this {@link CreateChatCompletionResponse}.
   *
   * @param promptFilterResultsItem The promptFilterResults that should be added
   * @return The same instance of type {@link CreateChatCompletionResponse}
   */
  @Nonnull
  public CreateChatCompletionResponse addPromptFilterResultsItem(
      @Nonnull final PromptFilterResult promptFilterResultsItem) {
    if (this.promptFilterResults == null) {
      this.promptFilterResults = new ArrayList<>();
    }
    this.promptFilterResults.add(promptFilterResultsItem);
    return this;
  }

  /**
   * Content filtering results for zero or more prompts in the request. In a streaming request,
   * results for different prompts may arrive at different times or in different orders.
   *
   * @return promptFilterResults The promptFilterResults of this {@link
   *     CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public List<PromptFilterResult> getPromptFilterResults() {
    return promptFilterResults;
  }

  /**
   * Set the promptFilterResults of this {@link CreateChatCompletionResponse} instance.
   *
   * @param promptFilterResults Content filtering results for zero or more prompts in the request.
   *     In a streaming request, results for different prompts may arrive at different times or in
   *     different orders.
   */
  public void setPromptFilterResults(@Nullable final List<PromptFilterResult> promptFilterResults) {
    this.promptFilterResults = promptFilterResults;
  }

  /**
   * Set the choices of this {@link CreateChatCompletionResponse} instance and return the same
   * instance.
   *
   * @param choices A list of chat completion choices. Can be more than one if &#x60;n&#x60; is
   *     greater than 1.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse choices(
      @Nonnull final List<CreateChatCompletionResponseChoicesInner> choices) {
    this.choices = choices;
    return this;
  }

  /**
   * Add one choices instance to this {@link CreateChatCompletionResponse}.
   *
   * @param choicesItem The choices that should be added
   * @return The same instance of type {@link CreateChatCompletionResponse}
   */
  @Nonnull
  public CreateChatCompletionResponse addChoicesItem(
      @Nonnull final CreateChatCompletionResponseChoicesInner choicesItem) {
    if (this.choices == null) {
      this.choices = new ArrayList<>();
    }
    this.choices.add(choicesItem);
    return this;
  }

  /**
   * A list of chat completion choices. Can be more than one if &#x60;n&#x60; is greater than 1.
   *
   * @return choices The choices of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public List<CreateChatCompletionResponseChoicesInner> getChoices() {
    return choices;
  }

  /**
   * Set the choices of this {@link CreateChatCompletionResponse} instance.
   *
   * @param choices A list of chat completion choices. Can be more than one if &#x60;n&#x60; is
   *     greater than 1.
   */
  public void setChoices(@Nonnull final List<CreateChatCompletionResponseChoicesInner> choices) {
    this.choices = choices;
  }

  /**
   * Set the created of this {@link CreateChatCompletionResponse} instance and return the same
   * instance.
   *
   * @param created The Unix timestamp (in seconds) of when the chat completion was created.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse created(@Nonnull final Integer created) {
    this.created = created;
    return this;
  }

  /**
   * The Unix timestamp (in seconds) of when the chat completion was created.
   *
   * @return created The created of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public Integer getCreated() {
    return created;
  }

  /**
   * Set the created of this {@link CreateChatCompletionResponse} instance.
   *
   * @param created The Unix timestamp (in seconds) of when the chat completion was created.
   */
  public void setCreated(@Nonnull final Integer created) {
    this.created = created;
  }

  /**
   * Set the model of this {@link CreateChatCompletionResponse} instance and return the same
   * instance.
   *
   * @param model The model used for the chat completion.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse model(@Nonnull final String model) {
    this.model = model;
    return this;
  }

  /**
   * The model used for the chat completion.
   *
   * @return model The model of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public String getModel() {
    return model;
  }

  /**
   * Set the model of this {@link CreateChatCompletionResponse} instance.
   *
   * @param model The model used for the chat completion.
   */
  public void setModel(@Nonnull final String model) {
    this.model = model;
  }

  /**
   * Set the systemFingerprint of this {@link CreateChatCompletionResponse} instance and return the
   * same instance.
   *
   * @param systemFingerprint This fingerprint represents the backend configuration that the model
   *     runs with. Can be used in conjunction with the &#x60;seed&#x60; request parameter to
   *     understand when backend changes have been made that might impact determinism.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse systemFingerprint(@Nullable final String systemFingerprint) {
    this.systemFingerprint = systemFingerprint;
    return this;
  }

  /**
   * This fingerprint represents the backend configuration that the model runs with. Can be used in
   * conjunction with the &#x60;seed&#x60; request parameter to understand when backend changes have
   * been made that might impact determinism.
   *
   * @return systemFingerprint The systemFingerprint of this {@link CreateChatCompletionResponse}
   *     instance.
   */
  @Nonnull
  public String getSystemFingerprint() {
    return systemFingerprint;
  }

  /**
   * Set the systemFingerprint of this {@link CreateChatCompletionResponse} instance.
   *
   * @param systemFingerprint This fingerprint represents the backend configuration that the model
   *     runs with. Can be used in conjunction with the &#x60;seed&#x60; request parameter to
   *     understand when backend changes have been made that might impact determinism.
   */
  public void setSystemFingerprint(@Nullable final String systemFingerprint) {
    this.systemFingerprint = systemFingerprint;
  }

  /**
   * Set the _object of this {@link CreateChatCompletionResponse} instance and return the same
   * instance.
   *
   * @param _object The object type, which is always &#x60;chat.completion&#x60;.
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse _object(@Nonnull final ObjectEnum _object) {
    this._object = _object;
    return this;
  }

  /**
   * The object type, which is always &#x60;chat.completion&#x60;.
   *
   * @return _object The _object of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public ObjectEnum getObject() {
    return _object;
  }

  /**
   * Set the _object of this {@link CreateChatCompletionResponse} instance.
   *
   * @param _object The object type, which is always &#x60;chat.completion&#x60;.
   */
  public void setObject(@Nonnull final ObjectEnum _object) {
    this._object = _object;
  }

  /**
   * Set the usage of this {@link CreateChatCompletionResponse} instance and return the same
   * instance.
   *
   * @param usage The usage of this {@link CreateChatCompletionResponse}
   * @return The same instance of this {@link CreateChatCompletionResponse} class
   */
  @Nonnull
  public CreateChatCompletionResponse usage(@Nullable final CompletionUsage usage) {
    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   *
   * @return usage The usage of this {@link CreateChatCompletionResponse} instance.
   */
  @Nonnull
  public CompletionUsage getUsage() {
    return usage;
  }

  /**
   * Set the usage of this {@link CreateChatCompletionResponse} instance.
   *
   * @param usage The usage of this {@link CreateChatCompletionResponse}
   */
  public void setUsage(@Nullable final CompletionUsage usage) {
    this.usage = usage;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link CreateChatCompletionResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link CreateChatCompletionResponse}
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
          "CreateChatCompletionResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link CreateChatCompletionResponse} instance. If the
   * map previously contained a mapping for the key, the old value is replaced by the specified
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
    final CreateChatCompletionResponse createChatCompletionResponse =
        (CreateChatCompletionResponse) o;
    return Objects.equals(
            this.cloudSdkCustomFields, createChatCompletionResponse.cloudSdkCustomFields)
        && Objects.equals(this.id, createChatCompletionResponse.id)
        && Objects.equals(
            this.promptFilterResults, createChatCompletionResponse.promptFilterResults)
        && Objects.equals(this.choices, createChatCompletionResponse.choices)
        && Objects.equals(this.created, createChatCompletionResponse.created)
        && Objects.equals(this.model, createChatCompletionResponse.model)
        && Objects.equals(this.systemFingerprint, createChatCompletionResponse.systemFingerprint)
        && Objects.equals(this._object, createChatCompletionResponse._object)
        && Objects.equals(this.usage, createChatCompletionResponse.usage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        promptFilterResults,
        choices,
        created,
        model,
        systemFingerprint,
        _object,
        usage,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CreateChatCompletionResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    promptFilterResults: ")
        .append(toIndentedString(promptFilterResults))
        .append("\n");
    sb.append("    choices: ").append(toIndentedString(choices)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    systemFingerprint: ").append(toIndentedString(systemFingerprint)).append("\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
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
