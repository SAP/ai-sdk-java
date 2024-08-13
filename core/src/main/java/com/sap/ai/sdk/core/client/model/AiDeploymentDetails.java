

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.33.0
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
import com.sap.ai.sdk.core.client.model.AiResourcesDetails;
import com.sap.ai.sdk.core.client.model.AiScalingDetails;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Detail information about a deployment (including predefined sections: &#x60;scaling&#x60; and &#x60;resources&#x60;). JSON String representation of this object is limited to 5000 characters 
 */

// CHECKSTYLE:OFF
public class AiDeploymentDetails 
// CHECKSTYLE:ON
{
  @JsonProperty("scaling")
  private AiScalingDetails scaling;

  @JsonProperty("resources")
  private AiResourcesDetails resources;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the scaling of this {@link AiDeploymentDetails} instance and return the same instance.
   *
   * @param scaling  The scaling of this {@link AiDeploymentDetails}
   * @return The same instance of this {@link AiDeploymentDetails} class
   */
   @Nonnull public AiDeploymentDetails scaling(@Nonnull final AiScalingDetails scaling) {
    this.scaling = scaling;
    return this;
  }

   /**
   * Get scaling
   * @return scaling  The scaling of this {@link AiDeploymentDetails} instance.
  **/
  @Nonnull public AiScalingDetails getScaling() {
    return scaling;
  }

  /**
  * Set the scaling of this {@link AiDeploymentDetails} instance.
  *
  * @param scaling  The scaling of this {@link AiDeploymentDetails}
  */
  public void setScaling( @Nonnull final AiScalingDetails scaling) {
    this.scaling = scaling;
  }

   /**
   * Set the resources of this {@link AiDeploymentDetails} instance and return the same instance.
   *
   * @param resources  The resources of this {@link AiDeploymentDetails}
   * @return The same instance of this {@link AiDeploymentDetails} class
   */
   @Nonnull public AiDeploymentDetails resources(@Nonnull final AiResourcesDetails resources) {
    this.resources = resources;
    return this;
  }

   /**
   * Get resources
   * @return resources  The resources of this {@link AiDeploymentDetails} instance.
  **/
  @Nonnull public AiResourcesDetails getResources() {
    return resources;
  }

  /**
  * Set the resources of this {@link AiDeploymentDetails} instance.
  *
  * @param resources  The resources of this {@link AiDeploymentDetails}
  */
  public void setResources( @Nonnull final AiResourcesDetails resources) {
    this.resources = resources;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentDetails}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentDetails} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiDeploymentDetails has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentDetails} instance. If the map previously contained a mapping
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
    final AiDeploymentDetails aiDeploymentDetails = (AiDeploymentDetails) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeploymentDetails.cloudSdkCustomFields) &&
        Objects.equals(this.scaling, aiDeploymentDetails.scaling) &&
        Objects.equals(this.resources, aiDeploymentDetails.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scaling, resources, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentDetails {\n");
    sb.append("    scaling: ").append(toIndentedString(scaling)).append("\n");
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

