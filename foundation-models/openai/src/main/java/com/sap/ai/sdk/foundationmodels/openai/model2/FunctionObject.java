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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** FunctionObject */
// CHECKSTYLE:OFF
public class FunctionObject
// CHECKSTYLE:ON
{
  @JsonProperty("description")
  private String description;

  @JsonProperty("name")
  private String name;

  @JsonProperty("parameters")
  private Map<String, Object> parameters = new HashMap<>();

  @JsonProperty("strict")
  private Boolean strict = false;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the description of this {@link FunctionObject} instance and return the same instance.
   *
   * @param description A description of what the function does, used by the model to choose when
   *     and how to call the function.
   * @return The same instance of this {@link FunctionObject} class
   */
  @Nonnull
  public FunctionObject description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * A description of what the function does, used by the model to choose when and how to call the
   * function.
   *
   * @return description The description of this {@link FunctionObject} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link FunctionObject} instance.
   *
   * @param description A description of what the function does, used by the model to choose when
   *     and how to call the function.
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the name of this {@link FunctionObject} instance and return the same instance.
   *
   * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or contain
   *     underscores and dashes, with a maximum length of 64.
   * @return The same instance of this {@link FunctionObject} class
   */
  @Nonnull
  public FunctionObject name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and
   * dashes, with a maximum length of 64.
   *
   * @return name The name of this {@link FunctionObject} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link FunctionObject} instance.
   *
   * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or contain
   *     underscores and dashes, with a maximum length of 64.
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the parameters of this {@link FunctionObject} instance and return the same instance.
   *
   * @param parameters The parameters the functions accepts, described as a JSON Schema object. See
   *     the
   *     guide](https://learn.microsoft.com/en-us/azure/ai-services/openai/how-to/function-calling)
   *     for examples, and the [JSON Schema
   *     reference](https://json-schema.org/understanding-json-schema/) for documentation about the
   *     format. Omitting &#x60;parameters&#x60; defines a function with an empty parameter list.
   * @return The same instance of this {@link FunctionObject} class
   */
  @Nonnull
  public FunctionObject parameters(@Nullable final Map<String, Object> parameters) {
    this.parameters = parameters;
    return this;
  }

  /**
   * Put one parameters instance to this {@link FunctionObject} instance.
   *
   * @param key The String key of this parameters instance
   * @param parametersItem The parameters that should be added under the given key
   * @return The same instance of type {@link FunctionObject}
   */
  @Nonnull
  public FunctionObject putparametersItem(
      @Nonnull final String key, @Nullable final Object parametersItem) {
    if (this.parameters == null) {
      this.parameters = new HashMap<>();
    }
    this.parameters.put(key, parametersItem);
    return this;
  }

  /**
   * The parameters the functions accepts, described as a JSON Schema object. See the
   * guide](https://learn.microsoft.com/en-us/azure/ai-services/openai/how-to/function-calling) for
   * examples, and the [JSON Schema reference](https://json-schema.org/understanding-json-schema/)
   * for documentation about the format. Omitting &#x60;parameters&#x60; defines a function with an
   * empty parameter list.
   *
   * @return parameters The parameters of this {@link FunctionObject} instance.
   */
  @Nonnull
  public Map<String, Object> getParameters() {
    return parameters;
  }

  /**
   * Set the parameters of this {@link FunctionObject} instance.
   *
   * @param parameters The parameters the functions accepts, described as a JSON Schema object. See
   *     the
   *     guide](https://learn.microsoft.com/en-us/azure/ai-services/openai/how-to/function-calling)
   *     for examples, and the [JSON Schema
   *     reference](https://json-schema.org/understanding-json-schema/) for documentation about the
   *     format. Omitting &#x60;parameters&#x60; defines a function with an empty parameter list.
   */
  public void setParameters(@Nullable final Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  /**
   * Set the strict of this {@link FunctionObject} instance and return the same instance.
   *
   * @param strict Whether to enable strict schema adherence when generating the function call. If
   *     set to true, the model will follow the exact schema defined in the &#x60;parameters&#x60;
   *     field. Only a subset of JSON Schema is supported when &#x60;strict&#x60; is
   *     &#x60;true&#x60;. Learn more about Structured Outputs in the [function calling
   *     guide](docs/guides/function-calling).
   * @return The same instance of this {@link FunctionObject} class
   */
  @Nonnull
  public FunctionObject strict(@Nullable final Boolean strict) {
    this.strict = strict;
    return this;
  }

  /**
   * Whether to enable strict schema adherence when generating the function call. If set to true,
   * the model will follow the exact schema defined in the &#x60;parameters&#x60; field. Only a
   * subset of JSON Schema is supported when &#x60;strict&#x60; is &#x60;true&#x60;. Learn more
   * about Structured Outputs in the [function calling guide](docs/guides/function-calling).
   *
   * @return strict The strict of this {@link FunctionObject} instance.
   */
  @Nullable
  public Boolean isStrict() {
    return strict;
  }

  /**
   * Set the strict of this {@link FunctionObject} instance.
   *
   * @param strict Whether to enable strict schema adherence when generating the function call. If
   *     set to true, the model will follow the exact schema defined in the &#x60;parameters&#x60;
   *     field. Only a subset of JSON Schema is supported when &#x60;strict&#x60; is
   *     &#x60;true&#x60;. Learn more about Structured Outputs in the [function calling
   *     guide](docs/guides/function-calling).
   */
  public void setStrict(@Nullable final Boolean strict) {
    this.strict = strict;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link FunctionObject}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link FunctionObject} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("FunctionObject has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link FunctionObject} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
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
    final FunctionObject functionObject = (FunctionObject) o;
    return Objects.equals(this.cloudSdkCustomFields, functionObject.cloudSdkCustomFields)
        && Objects.equals(this.description, functionObject.description)
        && Objects.equals(this.name, functionObject.name)
        && Objects.equals(this.parameters, functionObject.parameters)
        && Objects.equals(this.strict, functionObject.strict);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, name, parameters, strict, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class FunctionObject {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    strict: ").append(toIndentedString(strict)).append("\n");
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
