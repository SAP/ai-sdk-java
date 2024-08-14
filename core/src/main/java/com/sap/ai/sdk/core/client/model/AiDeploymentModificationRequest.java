

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
import com.sap.ai.sdk.core.client.model.AiDeploymentTargetStatus;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request object for changing the target status of a deployment (currently only STOPPED is supported)
 */
// CHECKSTYLE:OFF
public class AiDeploymentModificationRequest 
// CHECKSTYLE:ON
{
  @JsonProperty("targetStatus")
  private AiDeploymentTargetStatus targetStatus;

  @JsonProperty("configurationId")
  private String configurationId;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected AiDeploymentModificationRequest() {  }

   /**
    * Set the targetStatus of this {@link AiDeploymentModificationRequest} instance and return the same instance.
    *
    * @param targetStatus  The targetStatus of this {@link AiDeploymentModificationRequest}
    * @return The same instance of this {@link AiDeploymentModificationRequest} class
    */
   @Nonnull public AiDeploymentModificationRequest targetStatus(@Nonnull final AiDeploymentTargetStatus targetStatus) {
    this.targetStatus = targetStatus;
    return this;
  }

   /**
    * Get targetStatus
    * @return targetStatus  The targetStatus of this {@link AiDeploymentModificationRequest} instance.
    */
  @Nonnull public AiDeploymentTargetStatus getTargetStatus() {
    return targetStatus;
  }

  /**
   * Set the targetStatus of this {@link AiDeploymentModificationRequest} instance.
   *
   * @param targetStatus  The targetStatus of this {@link AiDeploymentModificationRequest}
   */
  public void setTargetStatus( @Nonnull final AiDeploymentTargetStatus targetStatus) {
    this.targetStatus = targetStatus;
  }

   /**
    * Set the configurationId of this {@link AiDeploymentModificationRequest} instance and return the same instance.
    *
    * @param configurationId  ID of the configuration
    * @return The same instance of this {@link AiDeploymentModificationRequest} class
    */
   @Nonnull public AiDeploymentModificationRequest configurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
    return this;
  }

   /**
    * ID of the configuration
    * @return configurationId  The configurationId of this {@link AiDeploymentModificationRequest} instance.
    */
  @Nonnull public String getConfigurationId() {
    return configurationId;
  }

  /**
   * Set the configurationId of this {@link AiDeploymentModificationRequest} instance.
   *
   * @param configurationId  ID of the configuration
   */
  public void setConfigurationId( @Nonnull final String configurationId) {
    this.configurationId = configurationId;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentModificationRequest}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentModificationRequest} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiDeploymentModificationRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentModificationRequest} instance. If the map previously contained a mapping
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
    final AiDeploymentModificationRequest aiDeploymentModificationRequest = (AiDeploymentModificationRequest) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeploymentModificationRequest.cloudSdkCustomFields) &&
        Objects.equals(this.targetStatus, aiDeploymentModificationRequest.targetStatus) &&
        Objects.equals(this.configurationId, aiDeploymentModificationRequest.configurationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetStatus, configurationId, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentModificationRequest {\n");
    sb.append("    targetStatus: ").append(toIndentedString(targetStatus)).append("\n");
    sb.append("    configurationId: ").append(toIndentedString(configurationId)).append("\n");
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

    /**
    * Create a new {@link AiDeploymentModificationRequest} instance. No arguments are required.
    */
    public static AiDeploymentModificationRequest create() {
        return new AiDeploymentModificationRequest();
    }

}

