

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
import com.sap.ai.sdk.core.client.model.AiExecutionStatus;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * AiDeploymentCreationResponse
 */
// CHECKSTYLE:OFF
public class AiDeploymentCreationResponse 
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("message")
  private String message;

  @JsonProperty("deploymentUrl")
  private String deploymentUrl;

  @JsonProperty("status")
  private AiExecutionStatus status;

  @JsonProperty("ttl")
  private String ttl;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected AiDeploymentCreationResponse() {  }

   /**
    * Set the id of this {@link AiDeploymentCreationResponse} instance and return the same instance.
    *
    * @param id  Generic ID
    * @return The same instance of this {@link AiDeploymentCreationResponse} class
    */
   @Nonnull public AiDeploymentCreationResponse id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

   /**
    * Generic ID
    * @return id  The id of this {@link AiDeploymentCreationResponse} instance.
    */
  @Nonnull public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiDeploymentCreationResponse} instance.
   *
   * @param id  Generic ID
   */
  public void setId( @Nonnull final String id) {
    this.id = id;
  }

   /**
    * Set the message of this {@link AiDeploymentCreationResponse} instance and return the same instance.
    *
    * @param message  Message
    * @return The same instance of this {@link AiDeploymentCreationResponse} class
    */
   @Nonnull public AiDeploymentCreationResponse message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

   /**
    * Message
    * @return message  The message of this {@link AiDeploymentCreationResponse} instance.
    */
  @Nonnull public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link AiDeploymentCreationResponse} instance.
   *
   * @param message  Message
   */
  public void setMessage( @Nonnull final String message) {
    this.message = message;
  }

   /**
    * Set the deploymentUrl of this {@link AiDeploymentCreationResponse} instance and return the same instance.
    *
    * @param deploymentUrl  Consumption URL of the deployment
    * @return The same instance of this {@link AiDeploymentCreationResponse} class
    */
   @Nonnull public AiDeploymentCreationResponse deploymentUrl(@Nonnull final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
    return this;
  }

   /**
    * Consumption URL of the deployment
    * @return deploymentUrl  The deploymentUrl of this {@link AiDeploymentCreationResponse} instance.
    */
  @Nonnull public String getDeploymentUrl() {
    return deploymentUrl;
  }

  /**
   * Set the deploymentUrl of this {@link AiDeploymentCreationResponse} instance.
   *
   * @param deploymentUrl  Consumption URL of the deployment
   */
  public void setDeploymentUrl( @Nonnull final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
  }

   /**
    * Set the status of this {@link AiDeploymentCreationResponse} instance and return the same instance.
    *
    * @param status  The status of this {@link AiDeploymentCreationResponse}
    * @return The same instance of this {@link AiDeploymentCreationResponse} class
    */
   @Nonnull public AiDeploymentCreationResponse status(@Nonnull final AiExecutionStatus status) {
    this.status = status;
    return this;
  }

   /**
    * Get status
    * @return status  The status of this {@link AiDeploymentCreationResponse} instance.
    */
  @Nonnull public AiExecutionStatus getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link AiDeploymentCreationResponse} instance.
   *
   * @param status  The status of this {@link AiDeploymentCreationResponse}
   */
  public void setStatus( @Nonnull final AiExecutionStatus status) {
    this.status = status;
  }

   /**
    * Set the ttl of this {@link AiDeploymentCreationResponse} instance and return the same instance.
    *
    * @param ttl  Time to live for a deployment. Its value can be either null or a number followed by the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
    * @return The same instance of this {@link AiDeploymentCreationResponse} class
    */
   @Nonnull public AiDeploymentCreationResponse ttl(@Nonnull final String ttl) {
    this.ttl = ttl;
    return this;
  }

   /**
    * Time to live for a deployment. Its value can be either null or a number followed by the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
    * @return ttl  The ttl of this {@link AiDeploymentCreationResponse} instance.
    */
  @Nonnull public String getTtl() {
    return ttl;
  }

  /**
   * Set the ttl of this {@link AiDeploymentCreationResponse} instance.
   *
   * @param ttl  Time to live for a deployment. Its value can be either null or a number followed by the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   */
  public void setTtl( @Nonnull final String ttl) {
    this.ttl = ttl;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentCreationResponse}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentCreationResponse} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiDeploymentCreationResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentCreationResponse} instance. If the map previously contained a mapping
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
    final AiDeploymentCreationResponse aiDeploymentCreationResponse = (AiDeploymentCreationResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeploymentCreationResponse.cloudSdkCustomFields) &&
        Objects.equals(this.id, aiDeploymentCreationResponse.id) &&
        Objects.equals(this.message, aiDeploymentCreationResponse.message) &&
        Objects.equals(this.deploymentUrl, aiDeploymentCreationResponse.deploymentUrl) &&
        Objects.equals(this.status, aiDeploymentCreationResponse.status) &&
        Objects.equals(this.ttl, aiDeploymentCreationResponse.ttl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message, deploymentUrl, status, ttl, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentCreationResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    deploymentUrl: ").append(toIndentedString(deploymentUrl)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
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
    * Create a type-safe, fluent-api builder object to construct a new {@link AiDeploymentCreationResponse} instance with all required arguments.
    */
    public static Builder create() {
        return (id) -> (message) -> new AiDeploymentCreationResponse().id(id).message(message);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the id of this {@link AiDeploymentCreationResponse} instance.
        *
        * @param id  Generic ID
        * @return The AiDeploymentCreationResponse builder.
        */
        Builder1 id( @Nonnull final String id);
    }
    /**
    * Builder helper class.
    */
    public interface Builder1 {
        /**
        * Set the message of this {@link AiDeploymentCreationResponse} instance.
        *
        * @param message  Message
        * @return The AiDeploymentCreationResponse instance.
        */
        AiDeploymentCreationResponse message( @Nonnull final String message);
    }

}

