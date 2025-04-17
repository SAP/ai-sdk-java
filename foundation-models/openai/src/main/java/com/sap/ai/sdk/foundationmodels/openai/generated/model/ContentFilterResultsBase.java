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

/** Information about the content filtering results. */
// CHECKSTYLE:OFF
public class ContentFilterResultsBase
// CHECKSTYLE:ON
{
  @JsonProperty("sexual")
  private ContentFilterSeverityResult sexual;

  @JsonProperty("violence")
  private ContentFilterSeverityResult violence;

  @JsonProperty("hate")
  private ContentFilterSeverityResult hate;

  @JsonProperty("self_harm")
  private ContentFilterSeverityResult selfHarm;

  @JsonProperty("profanity")
  private ContentFilterDetectedResult profanity;

  @JsonProperty("error")
  private ErrorBase error;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the sexual of this {@link ContentFilterResultsBase} instance and return the same instance.
   *
   * @param sexual The sexual of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase sexual(@Nullable final ContentFilterSeverityResult sexual) {
    this.sexual = sexual;
    return this;
  }

  /**
   * Get sexual
   *
   * @return sexual The sexual of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ContentFilterSeverityResult getSexual() {
    return sexual;
  }

  /**
   * Set the sexual of this {@link ContentFilterResultsBase} instance.
   *
   * @param sexual The sexual of this {@link ContentFilterResultsBase}
   */
  public void setSexual(@Nullable final ContentFilterSeverityResult sexual) {
    this.sexual = sexual;
  }

  /**
   * Set the violence of this {@link ContentFilterResultsBase} instance and return the same
   * instance.
   *
   * @param violence The violence of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase violence(@Nullable final ContentFilterSeverityResult violence) {
    this.violence = violence;
    return this;
  }

  /**
   * Get violence
   *
   * @return violence The violence of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ContentFilterSeverityResult getViolence() {
    return violence;
  }

  /**
   * Set the violence of this {@link ContentFilterResultsBase} instance.
   *
   * @param violence The violence of this {@link ContentFilterResultsBase}
   */
  public void setViolence(@Nullable final ContentFilterSeverityResult violence) {
    this.violence = violence;
  }

  /**
   * Set the hate of this {@link ContentFilterResultsBase} instance and return the same instance.
   *
   * @param hate The hate of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase hate(@Nullable final ContentFilterSeverityResult hate) {
    this.hate = hate;
    return this;
  }

  /**
   * Get hate
   *
   * @return hate The hate of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ContentFilterSeverityResult getHate() {
    return hate;
  }

  /**
   * Set the hate of this {@link ContentFilterResultsBase} instance.
   *
   * @param hate The hate of this {@link ContentFilterResultsBase}
   */
  public void setHate(@Nullable final ContentFilterSeverityResult hate) {
    this.hate = hate;
  }

  /**
   * Set the selfHarm of this {@link ContentFilterResultsBase} instance and return the same
   * instance.
   *
   * @param selfHarm The selfHarm of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase selfHarm(@Nullable final ContentFilterSeverityResult selfHarm) {
    this.selfHarm = selfHarm;
    return this;
  }

  /**
   * Get selfHarm
   *
   * @return selfHarm The selfHarm of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ContentFilterSeverityResult getSelfHarm() {
    return selfHarm;
  }

  /**
   * Set the selfHarm of this {@link ContentFilterResultsBase} instance.
   *
   * @param selfHarm The selfHarm of this {@link ContentFilterResultsBase}
   */
  public void setSelfHarm(@Nullable final ContentFilterSeverityResult selfHarm) {
    this.selfHarm = selfHarm;
  }

  /**
   * Set the profanity of this {@link ContentFilterResultsBase} instance and return the same
   * instance.
   *
   * @param profanity The profanity of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase profanity(@Nullable final ContentFilterDetectedResult profanity) {
    this.profanity = profanity;
    return this;
  }

  /**
   * Get profanity
   *
   * @return profanity The profanity of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ContentFilterDetectedResult getProfanity() {
    return profanity;
  }

  /**
   * Set the profanity of this {@link ContentFilterResultsBase} instance.
   *
   * @param profanity The profanity of this {@link ContentFilterResultsBase}
   */
  public void setProfanity(@Nullable final ContentFilterDetectedResult profanity) {
    this.profanity = profanity;
  }

  /**
   * Set the error of this {@link ContentFilterResultsBase} instance and return the same instance.
   *
   * @param error The error of this {@link ContentFilterResultsBase}
   * @return The same instance of this {@link ContentFilterResultsBase} class
   */
  @Nonnull
  public ContentFilterResultsBase error(@Nullable final ErrorBase error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   *
   * @return error The error of this {@link ContentFilterResultsBase} instance.
   */
  @Nonnull
  public ErrorBase getError() {
    return error;
  }

  /**
   * Set the error of this {@link ContentFilterResultsBase} instance.
   *
   * @param error The error of this {@link ContentFilterResultsBase}
   */
  public void setError(@Nullable final ErrorBase error) {
    this.error = error;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ContentFilterResultsBase}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ContentFilterResultsBase} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ContentFilterResultsBase has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ContentFilterResultsBase} instance. If the map
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
    final ContentFilterResultsBase contentFilterResultsBase = (ContentFilterResultsBase) o;
    return Objects.equals(this.cloudSdkCustomFields, contentFilterResultsBase.cloudSdkCustomFields)
        && Objects.equals(this.sexual, contentFilterResultsBase.sexual)
        && Objects.equals(this.violence, contentFilterResultsBase.violence)
        && Objects.equals(this.hate, contentFilterResultsBase.hate)
        && Objects.equals(this.selfHarm, contentFilterResultsBase.selfHarm)
        && Objects.equals(this.profanity, contentFilterResultsBase.profanity)
        && Objects.equals(this.error, contentFilterResultsBase.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sexual, violence, hate, selfHarm, profanity, error, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ContentFilterResultsBase {\n");
    sb.append("    sexual: ").append(toIndentedString(sexual)).append("\n");
    sb.append("    violence: ").append(toIndentedString(violence)).append("\n");
    sb.append("    hate: ").append(toIndentedString(hate)).append("\n");
    sb.append("    selfHarm: ").append(toIndentedString(selfHarm)).append("\n");
    sb.append("    profanity: ").append(toIndentedString(profanity)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
