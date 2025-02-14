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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ChatCompletionTokenLogprob */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class ChatCompletionTokenLogprob
// CHECKSTYLE:ON
{
  @JsonProperty("token")
  private String token;

  @JsonProperty("logprob")
  private BigDecimal logprob;

  @JsonProperty("bytes")
  private List<Integer> bytes;

  @JsonProperty("top_logprobs")
  private List<ChatCompletionTokenLogprobTopLogprobsInner> topLogprobs = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the token of this {@link ChatCompletionTokenLogprob} instance and return the same instance.
   *
   * @param token The token.
   * @return The same instance of this {@link ChatCompletionTokenLogprob} class
   */
  @Nonnull
  public ChatCompletionTokenLogprob token(@Nonnull final String token) {
    this.token = token;
    return this;
  }

  /**
   * The token.
   *
   * @return token The token of this {@link ChatCompletionTokenLogprob} instance.
   */
  @Nonnull
  public String getToken() {
    return token;
  }

  /**
   * Set the token of this {@link ChatCompletionTokenLogprob} instance.
   *
   * @param token The token.
   */
  public void setToken(@Nonnull final String token) {
    this.token = token;
  }

  /**
   * Set the logprob of this {@link ChatCompletionTokenLogprob} instance and return the same
   * instance.
   *
   * @param logprob The log probability of this token.
   * @return The same instance of this {@link ChatCompletionTokenLogprob} class
   */
  @Nonnull
  public ChatCompletionTokenLogprob logprob(@Nonnull final BigDecimal logprob) {
    this.logprob = logprob;
    return this;
  }

  /**
   * The log probability of this token.
   *
   * @return logprob The logprob of this {@link ChatCompletionTokenLogprob} instance.
   */
  @Nonnull
  public BigDecimal getLogprob() {
    return logprob;
  }

  /**
   * Set the logprob of this {@link ChatCompletionTokenLogprob} instance.
   *
   * @param logprob The log probability of this token.
   */
  public void setLogprob(@Nonnull final BigDecimal logprob) {
    this.logprob = logprob;
  }

  /**
   * Set the bytes of this {@link ChatCompletionTokenLogprob} instance and return the same instance.
   *
   * @param bytes A list of integers representing the UTF-8 bytes representation of the token.
   *     Useful in instances where characters are represented by multiple tokens and their byte
   *     representations must be combined to generate the correct text representation. Can be
   *     &#x60;null&#x60; if there is no bytes representation for the token.
   * @return The same instance of this {@link ChatCompletionTokenLogprob} class
   */
  @Nonnull
  public ChatCompletionTokenLogprob bytes(@Nullable final List<Integer> bytes) {
    this.bytes = bytes;
    return this;
  }

  /**
   * Add one bytes instance to this {@link ChatCompletionTokenLogprob}.
   *
   * @param bytesItem The bytes that should be added
   * @return The same instance of type {@link ChatCompletionTokenLogprob}
   */
  @Nonnull
  public ChatCompletionTokenLogprob addBytesItem(@Nonnull final Integer bytesItem) {
    if (this.bytes == null) {
      this.bytes = new ArrayList<>();
    }
    this.bytes.add(bytesItem);
    return this;
  }

  /**
   * A list of integers representing the UTF-8 bytes representation of the token. Useful in
   * instances where characters are represented by multiple tokens and their byte representations
   * must be combined to generate the correct text representation. Can be &#x60;null&#x60; if there
   * is no bytes representation for the token.
   *
   * @return bytes The bytes of this {@link ChatCompletionTokenLogprob} instance.
   */
  @Nullable
  public List<Integer> getBytes() {
    return bytes;
  }

  /**
   * Set the bytes of this {@link ChatCompletionTokenLogprob} instance.
   *
   * @param bytes A list of integers representing the UTF-8 bytes representation of the token.
   *     Useful in instances where characters are represented by multiple tokens and their byte
   *     representations must be combined to generate the correct text representation. Can be
   *     &#x60;null&#x60; if there is no bytes representation for the token.
   */
  public void setBytes(@Nullable final List<Integer> bytes) {
    this.bytes = bytes;
  }

  /**
   * Set the topLogprobs of this {@link ChatCompletionTokenLogprob} instance and return the same
   * instance.
   *
   * @param topLogprobs List of the most likely tokens and their log probability, at this token
   *     position. In rare cases, there may be fewer than the number of requested
   *     &#x60;top_logprobs&#x60; returned.
   * @return The same instance of this {@link ChatCompletionTokenLogprob} class
   */
  @Nonnull
  public ChatCompletionTokenLogprob topLogprobs(
      @Nonnull final List<ChatCompletionTokenLogprobTopLogprobsInner> topLogprobs) {
    this.topLogprobs = topLogprobs;
    return this;
  }

  /**
   * Add one topLogprobs instance to this {@link ChatCompletionTokenLogprob}.
   *
   * @param topLogprobsItem The topLogprobs that should be added
   * @return The same instance of type {@link ChatCompletionTokenLogprob}
   */
  @Nonnull
  public ChatCompletionTokenLogprob addTopLogprobsItem(
      @Nonnull final ChatCompletionTokenLogprobTopLogprobsInner topLogprobsItem) {
    if (this.topLogprobs == null) {
      this.topLogprobs = new ArrayList<>();
    }
    this.topLogprobs.add(topLogprobsItem);
    return this;
  }

  /**
   * List of the most likely tokens and their log probability, at this token position. In rare
   * cases, there may be fewer than the number of requested &#x60;top_logprobs&#x60; returned.
   *
   * @return topLogprobs The topLogprobs of this {@link ChatCompletionTokenLogprob} instance.
   */
  @Nonnull
  public List<ChatCompletionTokenLogprobTopLogprobsInner> getTopLogprobs() {
    return topLogprobs;
  }

  /**
   * Set the topLogprobs of this {@link ChatCompletionTokenLogprob} instance.
   *
   * @param topLogprobs List of the most likely tokens and their log probability, at this token
   *     position. In rare cases, there may be fewer than the number of requested
   *     &#x60;top_logprobs&#x60; returned.
   */
  public void setTopLogprobs(
      @Nonnull final List<ChatCompletionTokenLogprobTopLogprobsInner> topLogprobs) {
    this.topLogprobs = topLogprobs;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionTokenLogprob}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionTokenLogprob}
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
          "ChatCompletionTokenLogprob has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionTokenLogprob} instance. If the map
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
    final ChatCompletionTokenLogprob chatCompletionTokenLogprob = (ChatCompletionTokenLogprob) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionTokenLogprob.cloudSdkCustomFields)
        && Objects.equals(this.token, chatCompletionTokenLogprob.token)
        && Objects.equals(this.logprob, chatCompletionTokenLogprob.logprob)
        && Objects.equals(this.bytes, chatCompletionTokenLogprob.bytes)
        && Objects.equals(this.topLogprobs, chatCompletionTokenLogprob.topLogprobs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, logprob, bytes, topLogprobs, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionTokenLogprob {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    logprob: ").append(toIndentedString(logprob)).append("\n");
    sb.append("    bytes: ").append(toIndentedString(bytes)).append("\n");
    sb.append("    topLogprobs: ").append(toIndentedString(topLogprobs)).append("\n");
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
