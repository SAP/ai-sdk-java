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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ResponseFormatJsonObject
 */
// CHECKSTYLE:OFF
public class ResponseFormatJsonObject implements CreateChatCompletionRequestAllOfResponseFormat 
// CHECKSTYLE:ON
{
  /**
   * The type of response format being defined: &#x60;json_object&#x60;
   */
  public enum TypeEnum {
    /**
    * The JSON_OBJECT option of this ResponseFormatJsonObject
    */
    JSON_OBJECT("json_object");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    /**
    * Get the value of the enum
    * @return The enum value
    */
    @JsonValue
    @Nonnull public String getValue() {
      return value;
    }

    /**
    * Get the String value of the enum value.
    * @return The enum value as String
    */
    @Override
    @Nonnull public String toString() {
      return String.valueOf(value);
    }

    /**
    * Get the enum value from a String value
    * @param value The String value
    * @return The enum value of type ResponseFormatJsonObject
    */
    @JsonCreator
    @Nonnull public static TypeEnum fromValue(@Nonnull final String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the type of this {@link ResponseFormatJsonObject} instance and return the same instance.
   *
   * @param type  The type of response format being defined: &#x60;json_object&#x60;
   * @return The same instance of this {@link ResponseFormatJsonObject} class
   */
  @Nonnull public ResponseFormatJsonObject type( @Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * The type of response format being defined: &#x60;json_object&#x60;
   * @return type  The type of this {@link ResponseFormatJsonObject} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link ResponseFormatJsonObject} instance.
   *
   * @param type  The type of response format being defined: &#x60;json_object&#x60;
   */
  public void setType( @Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ResponseFormatJsonObject}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ResponseFormatJsonObject} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("ResponseFormatJsonObject has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ResponseFormatJsonObject} instance. If the map previously contained a mapping
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
    final ResponseFormatJsonObject responseFormatJsonObject = (ResponseFormatJsonObject) o;
    return Objects.equals(this.cloudSdkCustomFields, responseFormatJsonObject.cloudSdkCustomFields) &&
        Objects.equals(this.type, responseFormatJsonObject.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResponseFormatJsonObject {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

