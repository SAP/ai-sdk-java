/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ResponseFormatJsonSchemaJsonSchema */
@Beta // CHECKSTYLE:OFF
public class ResponseFormatJsonSchemaJsonSchema
// CHECKSTYLE:ON
{
  @JsonProperty("description")
  private String description;

  @JsonProperty("name")
  private String name;

  @JsonProperty("schema")
  private Map<String, Object> schema = new HashMap<>();

  @JsonProperty("strict")
  private Boolean strict = false;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ResponseFormatJsonSchemaJsonSchema. */
  protected ResponseFormatJsonSchemaJsonSchema() {}

  /**
   * Set the description of this {@link ResponseFormatJsonSchemaJsonSchema} instance and return the
   * same instance.
   *
   * @param description A description of what the response format is for, used by the model to
   *     determine how to respond in the format.
   * @return The same instance of this {@link ResponseFormatJsonSchemaJsonSchema} class
   */
  @Nonnull
  public ResponseFormatJsonSchemaJsonSchema description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * A description of what the response format is for, used by the model to determine how to respond
   * in the format.
   *
   * @return description The description of this {@link ResponseFormatJsonSchemaJsonSchema}
   *     instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   *
   * @param description A description of what the response format is for, used by the model to
   *     determine how to respond in the format.
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the name of this {@link ResponseFormatJsonSchemaJsonSchema} instance and return the same
   * instance.
   *
   * @param name The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores and
   *     dashes, with a maximum length of 64.
   * @return The same instance of this {@link ResponseFormatJsonSchemaJsonSchema} class
   */
  @Nonnull
  public ResponseFormatJsonSchemaJsonSchema name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with
   * a maximum length of 64.
   *
   * @return name The name of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   *
   * @param name The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores and
   *     dashes, with a maximum length of 64.
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the schema of this {@link ResponseFormatJsonSchemaJsonSchema} instance and return the same
   * instance.
   *
   * @param schema The schema for the response format, described as a JSON Schema object.
   * @return The same instance of this {@link ResponseFormatJsonSchemaJsonSchema} class
   */
  @Nonnull
  public ResponseFormatJsonSchemaJsonSchema schema(@Nullable final Map<String, Object> schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Put one schema instance to this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   *
   * @param key The String key of this schema instance
   * @param schemaItem The schema that should be added under the given key
   * @return The same instance of type {@link ResponseFormatJsonSchemaJsonSchema}
   */
  @Nonnull
  public ResponseFormatJsonSchemaJsonSchema putschemaItem(
      @Nonnull final String key, @Nullable final Object schemaItem) {
    if (this.schema == null) {
      this.schema = new HashMap<>();
    }
    this.schema.put(key, schemaItem);
    return this;
  }

  /**
   * The schema for the response format, described as a JSON Schema object.
   *
   * @return schema The schema of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   */
  @Nonnull
  public Map<String, Object> getSchema() {
    return schema;
  }

  /**
   * Set the schema of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   *
   * @param schema The schema for the response format, described as a JSON Schema object.
   */
  public void setSchema(@Nullable final Map<String, Object> schema) {
    this.schema = schema;
  }

  /**
   * Set the strict of this {@link ResponseFormatJsonSchemaJsonSchema} instance and return the same
   * instance.
   *
   * @param strict Whether to enable strict schema adherence when generating the output. If set to
   *     true, the model will always follow the exact schema defined in the &#x60;schema&#x60;
   *     field. Only a subset of JSON Schema is supported when &#x60;strict&#x60; is
   *     &#x60;true&#x60;. To learn more, read the [Structured Outputs
   *     guide](https://platform.openai.com/docs/guides/structured-outputs).
   * @return The same instance of this {@link ResponseFormatJsonSchemaJsonSchema} class
   */
  @Nonnull
  public ResponseFormatJsonSchemaJsonSchema strict(@Nullable final Boolean strict) {
    this.strict = strict;
    return this;
  }

  /**
   * Whether to enable strict schema adherence when generating the output. If set to true, the model
   * will always follow the exact schema defined in the &#x60;schema&#x60; field. Only a subset of
   * JSON Schema is supported when &#x60;strict&#x60; is &#x60;true&#x60;. To learn more, read the
   * [Structured Outputs guide](https://platform.openai.com/docs/guides/structured-outputs).
   *
   * @return strict The strict of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   */
  @Nullable
  public Boolean isStrict() {
    return strict;
  }

  /**
   * Set the strict of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
   *
   * @param strict Whether to enable strict schema adherence when generating the output. If set to
   *     true, the model will always follow the exact schema defined in the &#x60;schema&#x60;
   *     field. Only a subset of JSON Schema is supported when &#x60;strict&#x60; is
   *     &#x60;true&#x60;. To learn more, read the [Structured Outputs
   *     guide](https://platform.openai.com/docs/guides/structured-outputs).
   */
  public void setStrict(@Nullable final Boolean strict) {
    this.strict = strict;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ResponseFormatJsonSchemaJsonSchema}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ResponseFormatJsonSchemaJsonSchema}
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
          "ResponseFormatJsonSchemaJsonSchema has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ResponseFormatJsonSchemaJsonSchema} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
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
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ResponseFormatJsonSchemaJsonSchema responseFormatJsonSchemaJsonSchema =
        (ResponseFormatJsonSchemaJsonSchema) o;
    return Objects.equals(
            this.cloudSdkCustomFields, responseFormatJsonSchemaJsonSchema.cloudSdkCustomFields)
        && Objects.equals(this.description, responseFormatJsonSchemaJsonSchema.description)
        && Objects.equals(this.name, responseFormatJsonSchemaJsonSchema.name)
        && Objects.equals(this.schema, responseFormatJsonSchemaJsonSchema.schema)
        && Objects.equals(this.strict, responseFormatJsonSchemaJsonSchema.strict);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, name, schema, strict, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResponseFormatJsonSchemaJsonSchema {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
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
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * ResponseFormatJsonSchemaJsonSchema} instance with all required arguments.
   */
  public static Builder create() {
    return (name) -> new ResponseFormatJsonSchemaJsonSchema().name(name);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link ResponseFormatJsonSchemaJsonSchema} instance.
     *
     * @param name The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores
     *     and dashes, with a maximum length of 64.
     * @return The ResponseFormatJsonSchemaJsonSchema instance.
     */
    ResponseFormatJsonSchemaJsonSchema name(@Nonnull final String name);
  }
}
