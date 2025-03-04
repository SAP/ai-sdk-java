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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ResponseFormatJsonObject */
@Beta // CHECKSTYLE:OFF
public class ResponseFormatJsonObject implements CreateChatCompletionRequestAllOfResponseFormat
// CHECKSTYLE:ON
{
  /** The type of response format being defined: &#x60;json_object&#x60; */
  public enum TypeEnum {
    /** The JSON_OBJECT option of this ResponseFormatJsonObject */
    JSON_OBJECT("json_object"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this ResponseFormatJsonObject */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type ResponseFormatJsonObject
     */
    @JsonCreator
    @Nonnull
    public static TypeEnum fromValue(@Nonnull final String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the type of this {@link ResponseFormatJsonObject} instance and return the same instance.
   *
   * @param type The type of response format being defined: &#x60;json_object&#x60;
   * @return The same instance of this {@link ResponseFormatJsonObject} class
   */
  @Nonnull
  public ResponseFormatJsonObject type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of response format being defined: &#x60;json_object&#x60;
   *
   * @return type The type of this {@link ResponseFormatJsonObject} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link ResponseFormatJsonObject} instance.
   *
   * @param type The type of response format being defined: &#x60;json_object&#x60;
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ResponseFormatJsonObject}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ResponseFormatJsonObject} instance.
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
          "ResponseFormatJsonObject has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ResponseFormatJsonObject} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (type != null) declaredFields.put("type", type);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ResponseFormatJsonObject} instance. If the map
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
    final ResponseFormatJsonObject responseFormatJsonObject = (ResponseFormatJsonObject) o;
    return Objects.equals(this.cloudSdkCustomFields, responseFormatJsonObject.cloudSdkCustomFields)
        && Objects.equals(this.type, responseFormatJsonObject.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResponseFormatJsonObject {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
