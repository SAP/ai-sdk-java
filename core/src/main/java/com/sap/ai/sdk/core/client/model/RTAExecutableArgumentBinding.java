

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.32.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Required for execution Result of activation 
 */
// CHECKSTYLE:OFF
public class RTAExecutableArgumentBinding 
// CHECKSTYLE:ON
{
  @JsonProperty("key")
  private String key;

  @JsonProperty("value")
  private String value;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the key of this {@link RTAExecutableArgumentBinding} instance and return the same instance.
    *
    * @param key  The key of this {@link RTAExecutableArgumentBinding}
    * @return The same instance of this {@link RTAExecutableArgumentBinding} class
    */
   @Nonnull public RTAExecutableArgumentBinding key(@Nonnull final String key) {
    this.key = key;
    return this;
  }

   /**
    * Get key
    * @return key  The key of this {@link RTAExecutableArgumentBinding} instance.
    */
  @Nonnull public String getKey() {
    return key;
  }

  /**
   * Set the key of this {@link RTAExecutableArgumentBinding} instance.
   *
   * @param key  The key of this {@link RTAExecutableArgumentBinding}
   */
  public void setKey( @Nonnull final String key) {
    this.key = key;
  }

   /**
    * Set the value of this {@link RTAExecutableArgumentBinding} instance and return the same instance.
    *
    * @param value  The value of this {@link RTAExecutableArgumentBinding}
    * @return The same instance of this {@link RTAExecutableArgumentBinding} class
    */
   @Nonnull public RTAExecutableArgumentBinding value(@Nonnull final String value) {
    this.value = value;
    return this;
  }

   /**
    * Get value
    * @return value  The value of this {@link RTAExecutableArgumentBinding} instance.
    */
  @Nonnull public String getValue() {
    return value;
  }

  /**
   * Set the value of this {@link RTAExecutableArgumentBinding} instance.
   *
   * @param value  The value of this {@link RTAExecutableArgumentBinding}
   */
  public void setValue( @Nonnull final String value) {
    this.value = value;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAExecutableArgumentBinding}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAExecutableArgumentBinding} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("RTAExecutableArgumentBinding has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAExecutableArgumentBinding} instance. If the map previously contained a mapping
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
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RTAExecutableArgumentBinding rtAExecutableArgumentBinding = (RTAExecutableArgumentBinding) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAExecutableArgumentBinding.cloudSdkCustomFields) &&
        Objects.equals(this.key, rtAExecutableArgumentBinding.key) &&
        Objects.equals(this.value, rtAExecutableArgumentBinding.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAExecutableArgumentBinding {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

