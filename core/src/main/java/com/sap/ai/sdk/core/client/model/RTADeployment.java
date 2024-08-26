

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
import java.time.OffsetDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Detailed data about an inference-pipeline deployment
 */
// CHECKSTYLE:OFF
public class RTADeployment 
// CHECKSTYLE:ON
{
  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("id")
  private String id;

  @JsonProperty("deploymentUrl")
  private String deploymentUrl;

  @JsonProperty("latestRunningTargetId")
  private String latestRunningTargetId;

  @JsonProperty("targetId")
  private String targetId;

  @JsonProperty("ttl")
  private String ttl;

  /**
   * Deployment status
   */
  public enum StatusEnum {
    /**
    * The PENDING option of this RTADeployment
    */
    PENDING("PENDING"),
    
    /**
    * The RUNNING option of this RTADeployment
    */
    RUNNING("RUNNING"),
    
    /**
    * The COMPLETED option of this RTADeployment
    */
    COMPLETED("COMPLETED"),
    
    /**
    * The DEAD option of this RTADeployment
    */
    DEAD("DEAD"),
    
    /**
    * The UNKNOWN option of this RTADeployment
    */
    UNKNOWN("UNKNOWN"),
    
    /**
    * The DELETING option of this RTADeployment
    */
    DELETING("DELETING");

    private String value;

    StatusEnum(String value) {
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
    * @return The enum value of type RTADeployment
    */
    @JsonCreator
    @Nonnull public static StatusEnum fromValue(@Nonnull final String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("statusMessage")
  private String statusMessage;

  /**
   * Reflection of user&#39;s action on deployment. The value will be CREATE after user sends POST - create deployment, UPDATE after user sends PATCH - update deployment, and DELETE after user sends DELETE - delete deployment
   */
  public enum LastOperationEnum {
    /**
    * The CREATE option of this RTADeployment
    */
    CREATE("CREATE"),
    
    /**
    * The UPDATE option of this RTADeployment
    */
    UPDATE("UPDATE"),
    
    /**
    * The CASCADE_UPDATE option of this RTADeployment
    */
    CASCADE_UPDATE("CASCADE-UPDATE"),
    
    /**
    * The DELETE option of this RTADeployment
    */
    DELETE("DELETE");

    private String value;

    LastOperationEnum(String value) {
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
    * @return The enum value of type RTADeployment
    */
    @JsonCreator
    @Nonnull public static LastOperationEnum fromValue(@Nonnull final String value) {
      for (LastOperationEnum b : LastOperationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("lastOperation")
  private LastOperationEnum lastOperation;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected RTADeployment() {  }

  /**
   * Set the scenarioId of this {@link RTADeployment} instance and return the same instance.
   *
   * @param scenarioId  ID of the scenario
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment scenarioId( @Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

  /**
   * ID of the scenario
   * @return scenarioId  The scenarioId of this {@link RTADeployment} instance.
   */
  @Nonnull public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link RTADeployment} instance.
   *
   * @param scenarioId  ID of the scenario
   */
  public void setScenarioId( @Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Set the executableId of this {@link RTADeployment} instance and return the same instance.
   *
   * @param executableId  ID of the executable
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment executableId( @Nonnull final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   * @return executableId  The executableId of this {@link RTADeployment} instance.
   */
  @Nonnull public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link RTADeployment} instance.
   *
   * @param executableId  ID of the executable
   */
  public void setExecutableId( @Nonnull final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the id of this {@link RTADeployment} instance and return the same instance.
   *
   * @param id  ID of the deployment
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment id( @Nullable final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the deployment
   * @return id  The id of this {@link RTADeployment} instance.
   */
  @Nonnull public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link RTADeployment} instance.
   *
   * @param id  ID of the deployment
   */
  public void setId( @Nullable final String id) {
    this.id = id;
  }

  /**
   * Set the deploymentUrl of this {@link RTADeployment} instance and return the same instance.
   *
   * @param deploymentUrl  Consumption URL of the pipeline deployment
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment deploymentUrl( @Nullable final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
    return this;
  }

  /**
   * Consumption URL of the pipeline deployment
   * @return deploymentUrl  The deploymentUrl of this {@link RTADeployment} instance.
   */
  @Nonnull public String getDeploymentUrl() {
    return deploymentUrl;
  }

  /**
   * Set the deploymentUrl of this {@link RTADeployment} instance.
   *
   * @param deploymentUrl  Consumption URL of the pipeline deployment
   */
  public void setDeploymentUrl( @Nullable final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
  }

  /**
   * Set the latestRunningTargetId of this {@link RTADeployment} instance and return the same instance.
   *
   * @param latestRunningTargetId  Target ID of the latest running deployment
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment latestRunningTargetId( @Nullable final String latestRunningTargetId) {
    this.latestRunningTargetId = latestRunningTargetId;
    return this;
  }

  /**
   * Target ID of the latest running deployment
   * @return latestRunningTargetId  The latestRunningTargetId of this {@link RTADeployment} instance.
   */
  @Nonnull public String getLatestRunningTargetId() {
    return latestRunningTargetId;
  }

  /**
   * Set the latestRunningTargetId of this {@link RTADeployment} instance.
   *
   * @param latestRunningTargetId  Target ID of the latest running deployment
   */
  public void setLatestRunningTargetId( @Nullable final String latestRunningTargetId) {
    this.latestRunningTargetId = latestRunningTargetId;
  }

  /**
   * Set the targetId of this {@link RTADeployment} instance and return the same instance.
   *
   * @param targetId  Client provided reference, with which the status of a PATCHed deployment can be tracked
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment targetId( @Nullable final String targetId) {
    this.targetId = targetId;
    return this;
  }

  /**
   * Client provided reference, with which the status of a PATCHed deployment can be tracked
   * @return targetId  The targetId of this {@link RTADeployment} instance.
   */
  @Nonnull public String getTargetId() {
    return targetId;
  }

  /**
   * Set the targetId of this {@link RTADeployment} instance.
   *
   * @param targetId  Client provided reference, with which the status of a PATCHed deployment can be tracked
   */
  public void setTargetId( @Nullable final String targetId) {
    this.targetId = targetId;
  }

  /**
   * Set the ttl of this {@link RTADeployment} instance and return the same instance.
   *
   * @param ttl  TTL value of deployment
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment ttl( @Nullable final String ttl) {
    this.ttl = ttl;
    return this;
  }

  /**
   * TTL value of deployment
   * @return ttl  The ttl of this {@link RTADeployment} instance.
   */
  @Nonnull public String getTtl() {
    return ttl;
  }

  /**
   * Set the ttl of this {@link RTADeployment} instance.
   *
   * @param ttl  TTL value of deployment
   */
  public void setTtl( @Nullable final String ttl) {
    this.ttl = ttl;
  }

  /**
   * Set the status of this {@link RTADeployment} instance and return the same instance.
   *
   * @param status  Deployment status
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment status( @Nullable final StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Deployment status
   * @return status  The status of this {@link RTADeployment} instance.
   */
  @Nonnull public StatusEnum getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link RTADeployment} instance.
   *
   * @param status  Deployment status
   */
  public void setStatus( @Nullable final StatusEnum status) {
    this.status = status;
  }

  /**
   * Set the statusMessage of this {@link RTADeployment} instance and return the same instance.
   *
   * @param statusMessage  Deployment status message
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment statusMessage( @Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * Deployment status message
   * @return statusMessage  The statusMessage of this {@link RTADeployment} instance.
   */
  @Nonnull public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Set the statusMessage of this {@link RTADeployment} instance.
   *
   * @param statusMessage  Deployment status message
   */
  public void setStatusMessage( @Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * Set the lastOperation of this {@link RTADeployment} instance and return the same instance.
   *
   * @param lastOperation  Reflection of user&#39;s action on deployment. The value will be CREATE after user sends POST - create deployment, UPDATE after user sends PATCH - update deployment, and DELETE after user sends DELETE - delete deployment
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment lastOperation( @Nullable final LastOperationEnum lastOperation) {
    this.lastOperation = lastOperation;
    return this;
  }

  /**
   * Reflection of user&#39;s action on deployment. The value will be CREATE after user sends POST - create deployment, UPDATE after user sends PATCH - update deployment, and DELETE after user sends DELETE - delete deployment
   * @return lastOperation  The lastOperation of this {@link RTADeployment} instance.
   */
  @Nonnull public LastOperationEnum getLastOperation() {
    return lastOperation;
  }

  /**
   * Set the lastOperation of this {@link RTADeployment} instance.
   *
   * @param lastOperation  Reflection of user&#39;s action on deployment. The value will be CREATE after user sends POST - create deployment, UPDATE after user sends PATCH - update deployment, and DELETE after user sends DELETE - delete deployment
   */
  public void setLastOperation( @Nullable final LastOperationEnum lastOperation) {
    this.lastOperation = lastOperation;
  }

  /**
   * Set the createdAt of this {@link RTADeployment} instance and return the same instance.
   *
   * @param createdAt  Timestamp of resource creation
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment createdAt( @Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   * @return createdAt  The createdAt of this {@link RTADeployment} instance.
   */
  @Nonnull public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link RTADeployment} instance.
   *
   * @param createdAt  Timestamp of resource creation
   */
  public void setCreatedAt( @Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link RTADeployment} instance and return the same instance.
   *
   * @param modifiedAt  Timestamp of latest resource modification
   * @return The same instance of this {@link RTADeployment} class
   */
  @Nonnull public RTADeployment modifiedAt( @Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   * @return modifiedAt  The modifiedAt of this {@link RTADeployment} instance.
   */
  @Nonnull public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link RTADeployment} instance.
   *
   * @param modifiedAt  Timestamp of latest resource modification
   */
  public void setModifiedAt( @Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTADeployment}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTADeployment} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("RTADeployment has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTADeployment} instance. If the map previously contained a mapping
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
    final RTADeployment rtADeployment = (RTADeployment) o;
    return Objects.equals(this.cloudSdkCustomFields, rtADeployment.cloudSdkCustomFields) &&
        Objects.equals(this.scenarioId, rtADeployment.scenarioId) &&
        Objects.equals(this.executableId, rtADeployment.executableId) &&
        Objects.equals(this.id, rtADeployment.id) &&
        Objects.equals(this.deploymentUrl, rtADeployment.deploymentUrl) &&
        Objects.equals(this.latestRunningTargetId, rtADeployment.latestRunningTargetId) &&
        Objects.equals(this.targetId, rtADeployment.targetId) &&
        Objects.equals(this.ttl, rtADeployment.ttl) &&
        Objects.equals(this.status, rtADeployment.status) &&
        Objects.equals(this.statusMessage, rtADeployment.statusMessage) &&
        Objects.equals(this.lastOperation, rtADeployment.lastOperation) &&
        Objects.equals(this.createdAt, rtADeployment.createdAt) &&
        Objects.equals(this.modifiedAt, rtADeployment.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scenarioId, executableId, id, deploymentUrl, latestRunningTargetId, targetId, ttl, status, statusMessage, lastOperation, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTADeployment {\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    deploymentUrl: ").append(toIndentedString(deploymentUrl)).append("\n");
    sb.append("    latestRunningTargetId: ").append(toIndentedString(latestRunningTargetId)).append("\n");
    sb.append("    targetId: ").append(toIndentedString(targetId)).append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    lastOperation: ").append(toIndentedString(lastOperation)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    modifiedAt: ").append(toIndentedString(modifiedAt)).append("\n");
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
    * Create a type-safe, fluent-api builder object to construct a new {@link RTADeployment} instance with all required arguments.
    */
    public static Builder create() {
        return (scenarioId) -> (executableId) -> (createdAt) -> (modifiedAt) -> new RTADeployment().scenarioId(scenarioId).executableId(executableId).createdAt(createdAt).modifiedAt(modifiedAt);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the scenarioId of this {@link RTADeployment} instance.
        *
        * @param scenarioId  ID of the scenario
        * @return The RTADeployment builder.
        */
        Builder1 scenarioId( @Nonnull final String scenarioId);
    }
    /**
    * Builder helper class.
    */
    public interface Builder1 {
        /**
        * Set the executableId of this {@link RTADeployment} instance.
        *
        * @param executableId  ID of the executable
        * @return The RTADeployment builder.
        */
        Builder2 executableId( @Nonnull final String executableId);
    }
    /**
    * Builder helper class.
    */
    public interface Builder2 {
        /**
        * Set the createdAt of this {@link RTADeployment} instance.
        *
        * @param createdAt  Timestamp of resource creation
        * @return The RTADeployment builder.
        */
        Builder3 createdAt( @Nonnull final OffsetDateTime createdAt);
    }
    /**
    * Builder helper class.
    */
    public interface Builder3 {
        /**
        * Set the modifiedAt of this {@link RTADeployment} instance.
        *
        * @param modifiedAt  Timestamp of latest resource modification
        * @return The RTADeployment instance.
        */
        RTADeployment modifiedAt( @Nonnull final OffsetDateTime modifiedAt);
    }

}

