

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
import com.sap.ai.sdk.core.client.model.RTALogCommonResultItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * RTALogCommonData
 */
// CHECKSTYLE:OFF
public class RTALogCommonData 
// CHECKSTYLE:ON
{
  @JsonProperty("result")
  private List<RTALogCommonResultItem> result = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the result of this {@link RTALogCommonData} instance and return the same instance.
    *
    * @param result  The result of this {@link RTALogCommonData}
    * @return The same instance of this {@link RTALogCommonData} class
    */
   @Nonnull public RTALogCommonData result(@Nonnull final List<RTALogCommonResultItem> result) {
    this.result = result;
    return this;
  }
  /**
   * Add one result instance to this {@link RTALogCommonData}.
   * @param resultItem The result that should be added
   * @return The same instance of type {@link RTALogCommonData}
   */
  @Nonnull public RTALogCommonData addResultItem( @Nonnull final RTALogCommonResultItem resultItem) {
    if (this.result == null) {
      this.result = new ArrayList<>();
    }
    this.result.add(resultItem);
    return this;
  }

   /**
    * Get result
    * @return result  The result of this {@link RTALogCommonData} instance.
    */
  @Nonnull public List<RTALogCommonResultItem> getResult() {
    return result;
  }

  /**
   * Set the result of this {@link RTALogCommonData} instance.
   *
   * @param result  The result of this {@link RTALogCommonData}
   */
  public void setResult( @Nonnull final List<RTALogCommonResultItem> result) {
    this.result = result;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTALogCommonData}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTALogCommonData} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("RTALogCommonData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTALogCommonData} instance. If the map previously contained a mapping
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
    final RTALogCommonData rtALogCommonData = (RTALogCommonData) o;
    return Objects.equals(this.cloudSdkCustomFields, rtALogCommonData.cloudSdkCustomFields) &&
        Objects.equals(this.result, rtALogCommonData.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTALogCommonData {\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

