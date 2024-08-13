

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
import com.sap.ai.sdk.core.client.model.BckndUsageResourcePlanItem;
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
 * BckndDeploymentUsage
 */
// CHECKSTYLE:OFF
public class BckndDeploymentUsage 
// CHECKSTYLE:ON
{
  @JsonProperty("count")
  private Integer count;

  @JsonProperty("resourcePlans")
  private List<BckndUsageResourcePlanItem> resourcePlans = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the count of this {@link BckndDeploymentUsage} instance and return the same instance.
    *
    * @param count  The count of this {@link BckndDeploymentUsage}
    * @return The same instance of this {@link BckndDeploymentUsage} class
    */
   @Nonnull public BckndDeploymentUsage count(@Nonnull final Integer count) {
    this.count = count;
    return this;
  }

   /**
    * Get count
    * @return count  The count of this {@link BckndDeploymentUsage} instance.
    */
  @Nonnull public Integer getCount() {
    return count;
  }

  /**
   * Set the count of this {@link BckndDeploymentUsage} instance.
   *
   * @param count  The count of this {@link BckndDeploymentUsage}
   */
  public void setCount( @Nonnull final Integer count) {
    this.count = count;
  }

   /**
    * Set the resourcePlans of this {@link BckndDeploymentUsage} instance and return the same instance.
    *
    * @param resourcePlans  The resourcePlans of this {@link BckndDeploymentUsage}
    * @return The same instance of this {@link BckndDeploymentUsage} class
    */
   @Nonnull public BckndDeploymentUsage resourcePlans(@Nonnull final List<BckndUsageResourcePlanItem> resourcePlans) {
    this.resourcePlans = resourcePlans;
    return this;
  }
  /**
   * Add one resourcePlans instance to this {@link BckndDeploymentUsage}.
   * @param resourcePlansItem The resourcePlans that should be added
   * @return The same instance of type {@link BckndDeploymentUsage}
   */
  @Nonnull public BckndDeploymentUsage addResourcePlansItem( @Nonnull final BckndUsageResourcePlanItem resourcePlansItem) {
    if (this.resourcePlans == null) {
      this.resourcePlans = new ArrayList<>();
    }
    this.resourcePlans.add(resourcePlansItem);
    return this;
  }

   /**
    * Get resourcePlans
    * @return resourcePlans  The resourcePlans of this {@link BckndDeploymentUsage} instance.
    */
  @Nonnull public List<BckndUsageResourcePlanItem> getResourcePlans() {
    return resourcePlans;
  }

  /**
   * Set the resourcePlans of this {@link BckndDeploymentUsage} instance.
   *
   * @param resourcePlans  The resourcePlans of this {@link BckndDeploymentUsage}
   */
  public void setResourcePlans( @Nonnull final List<BckndUsageResourcePlanItem> resourcePlans) {
    this.resourcePlans = resourcePlans;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndDeploymentUsage}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndDeploymentUsage} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndDeploymentUsage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndDeploymentUsage} instance. If the map previously contained a mapping
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
    final BckndDeploymentUsage bckndDeploymentUsage = (BckndDeploymentUsage) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndDeploymentUsage.cloudSdkCustomFields) &&
        Objects.equals(this.count, bckndDeploymentUsage.count) &&
        Objects.equals(this.resourcePlans, bckndDeploymentUsage.resourcePlans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, resourcePlans, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndDeploymentUsage {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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

