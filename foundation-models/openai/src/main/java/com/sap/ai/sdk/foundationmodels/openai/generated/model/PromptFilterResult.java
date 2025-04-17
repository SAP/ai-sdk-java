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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Content filtering results for a single prompt in the request. */
// CHECKSTYLE:OFF
public class PromptFilterResult
// CHECKSTYLE:ON
{
  @JsonProperty("prompt_index")
  private Integer promptIndex;

  @JsonProperty("content_filter_results")
  private ContentFilterPromptResults contentFilterResults;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the promptIndex of this {@link PromptFilterResult} instance and return the same instance.
   *
   * @param promptIndex The promptIndex of this {@link PromptFilterResult}
   * @return The same instance of this {@link PromptFilterResult} class
   */
  @Nonnull
  public PromptFilterResult promptIndex(@Nullable final Integer promptIndex) {
    this.promptIndex = promptIndex;
    return this;
  }

  /**
   * Get promptIndex
   *
   * @return promptIndex The promptIndex of this {@link PromptFilterResult} instance.
   */
  @Nonnull
  public Integer getPromptIndex() {
    return promptIndex;
  }

  /**
   * Set the promptIndex of this {@link PromptFilterResult} instance.
   *
   * @param promptIndex The promptIndex of this {@link PromptFilterResult}
   */
  public void setPromptIndex(@Nullable final Integer promptIndex) {
    this.promptIndex = promptIndex;
  }

  /**
   * Set the contentFilterResults of this {@link PromptFilterResult} instance and return the same
   * instance.
   *
   * @param contentFilterResults The contentFilterResults of this {@link PromptFilterResult}
   * @return The same instance of this {@link PromptFilterResult} class
   */
  @Nonnull
  public PromptFilterResult contentFilterResults(
      @Nullable final ContentFilterPromptResults contentFilterResults) {
    this.contentFilterResults = contentFilterResults;
    return this;
  }

  /**
   * Get contentFilterResults
   *
   * @return contentFilterResults The contentFilterResults of this {@link PromptFilterResult}
   *     instance.
   */
  @Nonnull
  public ContentFilterPromptResults getContentFilterResults() {
    return contentFilterResults;
  }

  /**
   * Set the contentFilterResults of this {@link PromptFilterResult} instance.
   *
   * @param contentFilterResults The contentFilterResults of this {@link PromptFilterResult}
   */
  public void setContentFilterResults(
      @Nullable final ContentFilterPromptResults contentFilterResults) {
    this.contentFilterResults = contentFilterResults;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PromptFilterResult}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PromptFilterResult} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("PromptFilterResult has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link PromptFilterResult} instance. If the map
   * previously contained a mapping for the key, the old value is replaced by the specified value.
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
    final PromptFilterResult promptFilterResult = (PromptFilterResult) o;
    return Objects.equals(this.cloudSdkCustomFields, promptFilterResult.cloudSdkCustomFields)
        && Objects.equals(this.promptIndex, promptFilterResult.promptIndex)
        && Objects.equals(this.contentFilterResults, promptFilterResult.contentFilterResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(promptIndex, contentFilterResults, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PromptFilterResult {\n");
    sb.append("    promptIndex: ").append(toIndentedString(promptIndex)).append("\n");
    sb.append("    contentFilterResults: ")
        .append(toIndentedString(contentFilterResults))
        .append("\n");
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
