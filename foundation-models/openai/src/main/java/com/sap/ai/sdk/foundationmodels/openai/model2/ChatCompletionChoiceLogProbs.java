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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Log probability information for the choice. */
// CHECKSTYLE:OFF
public class ChatCompletionChoiceLogProbs
// CHECKSTYLE:ON
{
  @JsonProperty("content")
  private List<ChatCompletionTokenLogprob> content;

  @JsonProperty("refusal")
  private List<ChatCompletionTokenLogprob> refusal;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the content of this {@link ChatCompletionChoiceLogProbs} instance and return the same
   * instance.
   *
   * @param content A list of message content tokens with log probability information.
   * @return The same instance of this {@link ChatCompletionChoiceLogProbs} class
   */
  @Nonnull
  public ChatCompletionChoiceLogProbs content(
      @Nullable final List<ChatCompletionTokenLogprob> content) {
    this.content = content;
    return this;
  }

  /**
   * Add one content instance to this {@link ChatCompletionChoiceLogProbs}.
   *
   * @param contentItem The content that should be added
   * @return The same instance of type {@link ChatCompletionChoiceLogProbs}
   */
  @Nonnull
  public ChatCompletionChoiceLogProbs addContentItem(
      @Nonnull final ChatCompletionTokenLogprob contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * A list of message content tokens with log probability information.
   *
   * @return content The content of this {@link ChatCompletionChoiceLogProbs} instance.
   */
  @Nullable
  public List<ChatCompletionTokenLogprob> getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatCompletionChoiceLogProbs} instance.
   *
   * @param content A list of message content tokens with log probability information.
   */
  public void setContent(@Nullable final List<ChatCompletionTokenLogprob> content) {
    this.content = content;
  }

  /**
   * Set the refusal of this {@link ChatCompletionChoiceLogProbs} instance and return the same
   * instance.
   *
   * @param refusal A list of message refusal tokens with log probability information.
   * @return The same instance of this {@link ChatCompletionChoiceLogProbs} class
   */
  @Nonnull
  public ChatCompletionChoiceLogProbs refusal(
      @Nullable final List<ChatCompletionTokenLogprob> refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * Add one refusal instance to this {@link ChatCompletionChoiceLogProbs}.
   *
   * @param refusalItem The refusal that should be added
   * @return The same instance of type {@link ChatCompletionChoiceLogProbs}
   */
  @Nonnull
  public ChatCompletionChoiceLogProbs addRefusalItem(
      @Nonnull final ChatCompletionTokenLogprob refusalItem) {
    if (this.refusal == null) {
      this.refusal = new ArrayList<>();
    }
    this.refusal.add(refusalItem);
    return this;
  }

  /**
   * A list of message refusal tokens with log probability information.
   *
   * @return refusal The refusal of this {@link ChatCompletionChoiceLogProbs} instance.
   */
  @Nullable
  public List<ChatCompletionTokenLogprob> getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link ChatCompletionChoiceLogProbs} instance.
   *
   * @param refusal A list of message refusal tokens with log probability information.
   */
  public void setRefusal(@Nullable final List<ChatCompletionTokenLogprob> refusal) {
    this.refusal = refusal;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionChoiceLogProbs}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionChoiceLogProbs}
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
          "ChatCompletionChoiceLogProbs has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionChoiceLogProbs} instance. If the
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
    final ChatCompletionChoiceLogProbs chatCompletionChoiceLogProbs =
        (ChatCompletionChoiceLogProbs) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionChoiceLogProbs.cloudSdkCustomFields)
        && Objects.equals(this.content, chatCompletionChoiceLogProbs.content)
        && Objects.equals(this.refusal, chatCompletionChoiceLogProbs.refusal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, refusal, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionChoiceLogProbs {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
