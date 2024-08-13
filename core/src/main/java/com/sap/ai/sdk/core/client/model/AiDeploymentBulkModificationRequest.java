

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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequestWithIdentifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request object for changing the target status of multiple deployments
 */

// CHECKSTYLE:OFF
public class AiDeploymentBulkModificationRequest 
// CHECKSTYLE:ON
{
  @JsonProperty("deployments")
  private Set<AiDeploymentModificationRequestWithIdentifier> deployments = new LinkedHashSet<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the deployments of this {@link AiDeploymentBulkModificationRequest} instance and return the same instance.
   *
   * @param deployments  The deployments of this {@link AiDeploymentBulkModificationRequest}
   * @return The same instance of this {@link AiDeploymentBulkModificationRequest} class
   */
   @Nonnull public AiDeploymentBulkModificationRequest deployments(@Nonnull final Set<AiDeploymentModificationRequestWithIdentifier> deployments) {
    this.deployments = deployments;
    return this;
  }
  /**
  * Add one deployments instance to this {@link AiDeploymentBulkModificationRequest}.
  * @param deploymentsItem The deployments that should be added
  * @return The same instance of type {@link AiDeploymentBulkModificationRequest}
  */
  @Nonnull public AiDeploymentBulkModificationRequest adddeploymentsItem( @Nonnull final AiDeploymentModificationRequestWithIdentifier deploymentsItem) {
    if (this.deployments == null) {
      this.deployments = new LinkedHashSet<>();
    }
    this.deployments.add(deploymentsItem);
    return this;
  }

   /**
   * Get deployments
   * @return deployments  The deployments of this {@link AiDeploymentBulkModificationRequest} instance.
  **/
  @Nonnull public Set<AiDeploymentModificationRequestWithIdentifier> getDeployments() {
    return deployments;
  }

  /**
  * Set the deployments of this {@link AiDeploymentBulkModificationRequest} instance.
  *
  * @param deployments  The deployments of this {@link AiDeploymentBulkModificationRequest}
  */
  public void setDeployments( @Nonnull final Set<AiDeploymentModificationRequestWithIdentifier> deployments) {
    this.deployments = deployments;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentBulkModificationRequest}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentBulkModificationRequest} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiDeploymentBulkModificationRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentBulkModificationRequest} instance. If the map previously contained a mapping
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
    final AiDeploymentBulkModificationRequest aiDeploymentBulkModificationRequest = (AiDeploymentBulkModificationRequest) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeploymentBulkModificationRequest.cloudSdkCustomFields) &&
        Objects.equals(this.deployments, aiDeploymentBulkModificationRequest.deployments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deployments, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentBulkModificationRequest {\n");
    sb.append("    deployments: ").append(toIndentedString(deployments)).append("\n");
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

