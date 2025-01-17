/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.48.2
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** ToolCallChunkFunction */
@Beta // CHECKSTYLE:OFF
public class ToolCallChunkFunction
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("arguments")
  private String arguments;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ToolCallChunkFunction. */
  protected ToolCallChunkFunction() {}

  /**
   * Set the name of this {@link ToolCallChunkFunction} instance and return the same instance.
   *
   * @param name The name of the function to call.
   * @return The same instance of this {@link ToolCallChunkFunction} class
   */
  @Nonnull
  public ToolCallChunkFunction name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the function to call.
   *
   * @return name The name of this {@link ToolCallChunkFunction} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ToolCallChunkFunction} instance.
   *
   * @param name The name of the function to call.
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the arguments of this {@link ToolCallChunkFunction} instance and return the same instance.
   *
   * @param arguments The arguments to call the function with, as generated by the model in JSON
   *     format. Note that the model does not always generate valid JSON, and may hallucinate
   *     parameters not defined by your function schema. Validate the arguments in your code before
   *     calling your function.
   * @return The same instance of this {@link ToolCallChunkFunction} class
   */
  @Nonnull
  public ToolCallChunkFunction arguments(@Nullable final String arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * The arguments to call the function with, as generated by the model in JSON format. Note that
   * the model does not always generate valid JSON, and may hallucinate parameters not defined by
   * your function schema. Validate the arguments in your code before calling your function.
   *
   * @return arguments The arguments of this {@link ToolCallChunkFunction} instance.
   */
  @Nonnull
  public String getArguments() {
    return arguments;
  }

  /**
   * Set the arguments of this {@link ToolCallChunkFunction} instance.
   *
   * @param arguments The arguments to call the function with, as generated by the model in JSON
   *     format. Note that the model does not always generate valid JSON, and may hallucinate
   *     parameters not defined by your function schema. Validate the arguments in your code before
   *     calling your function.
   */
  public void setArguments(@Nullable final String arguments) {
    this.arguments = arguments;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ToolCallChunkFunction}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ToolCallChunkFunction} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ToolCallChunkFunction has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ToolCallChunkFunction} instance. If the map
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
    final ToolCallChunkFunction toolCallChunkFunction = (ToolCallChunkFunction) o;
    return Objects.equals(this.cloudSdkCustomFields, toolCallChunkFunction.cloudSdkCustomFields)
        && Objects.equals(this.name, toolCallChunkFunction.name)
        && Objects.equals(this.arguments, toolCallChunkFunction.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, arguments, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ToolCallChunkFunction {\n");
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

  /** Create a new {@link ToolCallChunkFunction} instance. No arguments are required. */
  public static ToolCallChunkFunction create() {
    return new ToolCallChunkFunction();
  }
}
