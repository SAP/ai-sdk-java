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

/** ChatCompletionTokenLogprobTopLogprobsInner */
// CHECKSTYLE:OFF
public class ChatCompletionTokenLogprobTopLogprobsInner
// CHECKSTYLE:ON
{
  @JsonProperty("token")
  private String token;

  @JsonProperty("logprob")
  private BigDecimal logprob;

  @JsonProperty("bytes")
  private List<Integer> bytes;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the token of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance and return
   * the same instance.
   *
   * @param token The token.
   * @return The same instance of this {@link ChatCompletionTokenLogprobTopLogprobsInner} class
   */
  @Nonnull
  public ChatCompletionTokenLogprobTopLogprobsInner token(@Nonnull final String token) {
    this.token = token;
    return this;
  }

  /**
   * The token.
   *
   * @return token The token of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance.
   */
  @Nonnull
  public String getToken() {
    return token;
  }

  /**
   * Set the token of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance.
   *
   * @param token The token.
   */
  public void setToken(@Nonnull final String token) {
    this.token = token;
  }

  /**
   * Set the logprob of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance and return
   * the same instance.
   *
   * @param logprob The log probability of this token.
   * @return The same instance of this {@link ChatCompletionTokenLogprobTopLogprobsInner} class
   */
  @Nonnull
  public ChatCompletionTokenLogprobTopLogprobsInner logprob(@Nonnull final BigDecimal logprob) {
    this.logprob = logprob;
    return this;
  }

  /**
   * The log probability of this token.
   *
   * @return logprob The logprob of this {@link ChatCompletionTokenLogprobTopLogprobsInner}
   *     instance.
   */
  @Nonnull
  public BigDecimal getLogprob() {
    return logprob;
  }

  /**
   * Set the logprob of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance.
   *
   * @param logprob The log probability of this token.
   */
  public void setLogprob(@Nonnull final BigDecimal logprob) {
    this.logprob = logprob;
  }

  /**
   * Set the bytes of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance and return
   * the same instance.
   *
   * @param bytes A list of integers representing the UTF-8 bytes representation of the token.
   *     Useful in instances where characters are represented by multiple tokens and their byte
   *     representations must be combined to generate the correct text representation. Can be
   *     &#x60;null&#x60; if there is no bytes representation for the token.
   * @return The same instance of this {@link ChatCompletionTokenLogprobTopLogprobsInner} class
   */
  @Nonnull
  public ChatCompletionTokenLogprobTopLogprobsInner bytes(@Nullable final List<Integer> bytes) {
    this.bytes = bytes;
    return this;
  }

  /**
   * Add one bytes instance to this {@link ChatCompletionTokenLogprobTopLogprobsInner}.
   *
   * @param bytesItem The bytes that should be added
   * @return The same instance of type {@link ChatCompletionTokenLogprobTopLogprobsInner}
   */
  @Nonnull
  public ChatCompletionTokenLogprobTopLogprobsInner addBytesItem(@Nonnull final Integer bytesItem) {
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
   * @return bytes The bytes of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance.
   */
  @Nullable
  public List<Integer> getBytes() {
    return bytes;
  }

  /**
   * Set the bytes of this {@link ChatCompletionTokenLogprobTopLogprobsInner} instance.
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
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionTokenLogprobTopLogprobsInner}.
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
   * ChatCompletionTokenLogprobTopLogprobsInner} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionTokenLogprobTopLogprobsInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionTokenLogprobTopLogprobsInner}
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
    final ChatCompletionTokenLogprobTopLogprobsInner chatCompletionTokenLogprobTopLogprobsInner =
        (ChatCompletionTokenLogprobTopLogprobsInner) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            chatCompletionTokenLogprobTopLogprobsInner.cloudSdkCustomFields)
        && Objects.equals(this.token, chatCompletionTokenLogprobTopLogprobsInner.token)
        && Objects.equals(this.logprob, chatCompletionTokenLogprobTopLogprobsInner.logprob)
        && Objects.equals(this.bytes, chatCompletionTokenLogprobTopLogprobsInner.bytes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, logprob, bytes, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionTokenLogprobTopLogprobsInner {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    logprob: ").append(toIndentedString(logprob)).append("\n");
    sb.append("    bytes: ").append(toIndentedString(bytes)).append("\n");
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
