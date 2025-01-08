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

import java.util.Objects;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Deprecated and replaced by &#x60;tool_calls&#x60;. The name and arguments of a function that should be called, as generated by the model.
 * @deprecated
 */
@Deprecated
// CHECKSTYLE:OFF
public class ChatCompletionStreamResponseDeltaFunctionCall 
// CHECKSTYLE:ON
{
  @JsonProperty("arguments")
  private String arguments;

  @JsonProperty("name")
  private String name;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the arguments of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance and return the same instance.
   *
   * @param arguments  The arguments to call the function with, as generated by the model in JSON format. Note that the model does not always generate valid JSON, and may hallucinate parameters not defined by your function schema. Validate the arguments in your code before calling your function.
   * @return The same instance of this {@link ChatCompletionStreamResponseDeltaFunctionCall} class
   */
  @Nonnull public ChatCompletionStreamResponseDeltaFunctionCall arguments( @Nullable final String arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * The arguments to call the function with, as generated by the model in JSON format. Note that the model does not always generate valid JSON, and may hallucinate parameters not defined by your function schema. Validate the arguments in your code before calling your function.
   * @return arguments  The arguments of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance.
   */
  @Nonnull
  public String getArguments() {
    return arguments;
  }

  /**
   * Set the arguments of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance.
   *
   * @param arguments  The arguments to call the function with, as generated by the model in JSON format. Note that the model does not always generate valid JSON, and may hallucinate parameters not defined by your function schema. Validate the arguments in your code before calling your function.
   */
  public void setArguments( @Nullable final String arguments) {
    this.arguments = arguments;
  }

  /**
   * Set the name of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance and return the same instance.
   *
   * @param name  The name of the function to call.
   * @return The same instance of this {@link ChatCompletionStreamResponseDeltaFunctionCall} class
   */
  @Nonnull public ChatCompletionStreamResponseDeltaFunctionCall name( @Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the function to call.
   * @return name  The name of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance.
   *
   * @param name  The name of the function to call.
   */
  public void setName( @Nullable final String name) {
    this.name = name;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionStreamResponseDeltaFunctionCall}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("ChatCompletionStreamResponseDeltaFunctionCall has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionStreamResponseDeltaFunctionCall} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
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
    final ChatCompletionStreamResponseDeltaFunctionCall chatCompletionStreamResponseDeltaFunctionCall = (ChatCompletionStreamResponseDeltaFunctionCall) o;
    return Objects.equals(this.cloudSdkCustomFields, chatCompletionStreamResponseDeltaFunctionCall.cloudSdkCustomFields) &&
        Objects.equals(this.arguments, chatCompletionStreamResponseDeltaFunctionCall.arguments) &&
        Objects.equals(this.name, chatCompletionStreamResponseDeltaFunctionCall.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arguments, name, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionStreamResponseDeltaFunctionCall {\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

