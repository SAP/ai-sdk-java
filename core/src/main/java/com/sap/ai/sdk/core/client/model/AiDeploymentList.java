

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
import com.sap.ai.sdk.core.client.model.AiDeployment;
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
 * AiDeploymentList
 */

// CHECKSTYLE:OFF
public class AiDeploymentList 
// CHECKSTYLE:ON
{
  @JsonProperty("count")
  private Integer count;

  @JsonProperty("resources")
  private List<AiDeployment> resources = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the count of this {@link AiDeploymentList} instance and return the same instance.
   *
   * @param count  Number of the resource instances in the list
   * @return The same instance of this {@link AiDeploymentList} class
   */
   @Nonnull public AiDeploymentList count(@Nonnull final Integer count) {
    this.count = count;
    return this;
  }

   /**
   * Number of the resource instances in the list
   * @return count  The count of this {@link AiDeploymentList} instance.
  **/
  @Nonnull public Integer getCount() {
    return count;
  }

  /**
  * Set the count of this {@link AiDeploymentList} instance.
  *
  * @param count  Number of the resource instances in the list
  */
  public void setCount( @Nonnull final Integer count) {
    this.count = count;
  }

   /**
   * Set the resources of this {@link AiDeploymentList} instance and return the same instance.
   *
   * @param resources  The resources of this {@link AiDeploymentList}
   * @return The same instance of this {@link AiDeploymentList} class
   */
   @Nonnull public AiDeploymentList resources(@Nonnull final List<AiDeployment> resources) {
    this.resources = resources;
    return this;
  }
  /**
  * Add one resources instance to this {@link AiDeploymentList}.
  * @param resourcesItem The resources that should be added
  * @return The same instance of type {@link AiDeploymentList}
  */
  @Nonnull public AiDeploymentList addresourcesItem( @Nonnull final AiDeployment resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

   /**
   * Get resources
   * @return resources  The resources of this {@link AiDeploymentList} instance.
  **/
  @Nonnull public List<AiDeployment> getResources() {
    return resources;
  }

  /**
  * Set the resources of this {@link AiDeploymentList} instance.
  *
  * @param resources  The resources of this {@link AiDeploymentList}
  */
  public void setResources( @Nonnull final List<AiDeployment> resources) {
    this.resources = resources;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentList}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentList} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiDeploymentList has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentList} instance. If the map previously contained a mapping
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
    final AiDeploymentList aiDeploymentList = (AiDeploymentList) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeploymentList.cloudSdkCustomFields) &&
        Objects.equals(this.count, aiDeploymentList.count) &&
        Objects.equals(this.resources, aiDeploymentList.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, resources, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentList {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
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

