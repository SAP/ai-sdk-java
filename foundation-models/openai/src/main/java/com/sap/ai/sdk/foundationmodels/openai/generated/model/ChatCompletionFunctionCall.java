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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Deprecated and replaced by &#x60;tool_calls&#x60;. The name and arguments of a function that
 * should be called, as generated by the model.
 */
@Beta // CHECKSTYLE:OFF
public class ChatCompletionFunctionCall
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("arguments")
  private String arguments;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the name of this {@link ChatCompletionFunctionCall} instance and return the same instance.
   *
   * @param name The name of the function to call.
   * @return The same instance of this {@link ChatCompletionFunctionCall} class
   */
  @Nonnull
  public ChatCompletionFunctionCall name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the function to call.
   *
   * @return name The name of this {@link ChatCompletionFunctionCall} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ChatCompletionFunctionCall} instance.
   *
   * @param name The name of the function to call.
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the arguments of this {@link ChatCompletionFunctionCall} instance and return the same
   * instance.
   *
   * @param arguments The arguments to call the function with, as generated by the model in JSON
   *     format. Note that the model does not always generate valid JSON, and may hallucinate
   *     parameters not defined by your function schema. Validate the arguments in your code before
   *     calling your function.
   * @return The same instance of this {@link ChatCompletionFunctionCall} class
   */
  @Nonnull
  public ChatCompletionFunctionCall arguments(@Nonnull final String arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * The arguments to call the function with, as generated by the model in JSON format. Note that
   * the model does not always generate valid JSON, and may hallucinate parameters not defined by
   * your function schema. Validate the arguments in your code before calling your function.
   *
   * @return arguments The arguments of this {@link ChatCompletionFunctionCall} instance.
   */
  @Nonnull
  public String getArguments() {
    return arguments;
  }

  /**
   * Set the arguments of this {@link ChatCompletionFunctionCall} instance.
   *
   * @param arguments The arguments to call the function with, as generated by the model in JSON
   *     format. Note that the model does not always generate valid JSON, and may hallucinate
   *     parameters not defined by your function schema. Validate the arguments in your code before
   *     calling your function.
   */
  public void setArguments(@Nonnull final String arguments) {
    this.arguments = arguments;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionFunctionCall}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionFunctionCall}
   * instance.
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
          "ChatCompletionFunctionCall has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ChatCompletionFunctionCall} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (arguments != null) declaredFields.put("arguments", arguments);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionFunctionCall} instance. If the map
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
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChatCompletionFunctionCall chatCompletionFunctionCall = (ChatCompletionFunctionCall) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionFunctionCall.cloudSdkCustomFields)
        && Objects.equals(this.name, chatCompletionFunctionCall.name)
        && Objects.equals(this.arguments, chatCompletionFunctionCall.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, arguments, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionFunctionCall {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
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
