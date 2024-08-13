

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
import com.sap.ai.sdk.core.client.model.BckndResourceGetResourcePlansValue;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BckndResourceGetResponse
 */
// CHECKSTYLE:OFF
public class BckndResourceGetResponse 
// CHECKSTYLE:ON
{
  @JsonProperty("resourcePlans")
  private Map<String, BckndResourceGetResourcePlansValue> resourcePlans = new HashMap<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the resourcePlans of this {@link BckndResourceGetResponse} instance and return the same instance.
    *
    * @param resourcePlans  The resourcePlans of this {@link BckndResourceGetResponse}
    * @return The same instance of this {@link BckndResourceGetResponse} class
    */
   @Nonnull public BckndResourceGetResponse resourcePlans(@Nonnull final Map<String, BckndResourceGetResourcePlansValue> resourcePlans) {
    this.resourcePlans = resourcePlans;
    return this;
  }

   /**
    * Put one resourcePlans instance to this {@link BckndResourceGetResponse} instance.
    * @param key The String key of this resourcePlans instance
    * @param resourcePlansItem The resourcePlans that should be added under the given key
    * @return The same instance of type {@link BckndResourceGetResponse}
    */
   @Nonnull public BckndResourceGetResponse putresourcePlansItem(@Nonnull final String key, @Nonnull final BckndResourceGetResourcePlansValue resourcePlansItem) {
      this.resourcePlans = new HashMap<>();
    this.resourcePlans.put(key, resourcePlansItem);
    return this;
  }

   /**
    * Get resourcePlans
    * @return resourcePlans  The resourcePlans of this {@link BckndResourceGetResponse} instance.
    */
  @Nonnull public Map<String, BckndResourceGetResourcePlansValue> getResourcePlans() {
    return resourcePlans;
  }

  /**
   * Set the resourcePlans of this {@link BckndResourceGetResponse} instance.
   *
   * @param resourcePlans  The resourcePlans of this {@link BckndResourceGetResponse}
   */
  public void setResourcePlans( @Nonnull final Map<String, BckndResourceGetResourcePlansValue> resourcePlans) {
    this.resourcePlans = resourcePlans;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndResourceGetResponse}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndResourceGetResponse} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndResourceGetResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndResourceGetResponse} instance. If the map previously contained a mapping
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
    final BckndResourceGetResponse bckndResourceGetResponse = (BckndResourceGetResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndResourceGetResponse.cloudSdkCustomFields) &&
        Objects.equals(this.resourcePlans, bckndResourceGetResponse.resourcePlans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourcePlans, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndResourceGetResponse {\n");
    sb.append("    resourcePlans: ").append(toIndentedString(resourcePlans)).append("\n");
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

