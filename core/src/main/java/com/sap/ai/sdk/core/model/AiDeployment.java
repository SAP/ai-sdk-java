/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.37.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Detailed data about a deployment */
@Beta // CHECKSTYLE:OFF
public class AiDeployment
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("deploymentUrl")
  private String deploymentUrl;

  @JsonProperty("configurationId")
  private String configurationId;

  @JsonProperty("configurationName")
  private String configurationName;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("status")
  private AiDeploymentStatus status;

  @JsonProperty("statusMessage")
  private String statusMessage;

  /** Deployment target status */
  public enum TargetStatusEnum {
    /** The RUNNING option of this AiDeployment */
    RUNNING("RUNNING"),

    /** The STOPPED option of this AiDeployment */
    STOPPED("STOPPED"),

    /** The DELETED option of this AiDeployment */
    DELETED("DELETED"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AiDeployment */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TargetStatusEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type AiDeployment
     */
    @JsonCreator
    @Nonnull
    public static TargetStatusEnum fromValue(@Nonnull final String value) {
      for (TargetStatusEnum b : TargetStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("targetStatus")
  private TargetStatusEnum targetStatus;

  /** Gets or Sets lastOperation */
  public enum LastOperationEnum {
    /** The CREATE option of this AiDeployment */
    CREATE("CREATE"),

    /** The UPDATE option of this AiDeployment */
    UPDATE("UPDATE"),

    /** The DELETE option of this AiDeployment */
    DELETE("DELETE"),

    /** The CASCADE_UPDATE option of this AiDeployment */
    CASCADE_UPDATE("CASCADE-UPDATE"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AiDeployment */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    LastOperationEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type AiDeployment
     */
    @JsonCreator
    @Nonnull
    public static LastOperationEnum fromValue(@Nonnull final String value) {
      for (LastOperationEnum b : LastOperationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("lastOperation")
  private LastOperationEnum lastOperation;

  @JsonProperty("latestRunningConfigurationId")
  private String latestRunningConfigurationId;

  @JsonProperty("ttl")
  private String ttl;

  @JsonProperty("details")
  private AiDeploymentDetails details;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonProperty("submissionTime")
  private OffsetDateTime submissionTime;

  @JsonProperty("startTime")
  private OffsetDateTime startTime;

  @JsonProperty("completionTime")
  private OffsetDateTime completionTime;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiDeployment. */
  protected AiDeployment() {}

  /**
   * Set the id of this {@link AiDeployment} instance and return the same instance.
   *
   * @param id ID of the deployment
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the deployment
   *
   * @return id The id of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiDeployment} instance.
   *
   * @param id ID of the deployment
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the deploymentUrl of this {@link AiDeployment} instance and return the same instance.
   *
   * @param deploymentUrl Consumption URL of the deployment
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment deploymentUrl(@Nullable final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
    return this;
  }

  /**
   * Consumption URL of the deployment
   *
   * @return deploymentUrl The deploymentUrl of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getDeploymentUrl() {
    return deploymentUrl;
  }

  /**
   * Set the deploymentUrl of this {@link AiDeployment} instance.
   *
   * @param deploymentUrl Consumption URL of the deployment
   */
  public void setDeploymentUrl(@Nullable final String deploymentUrl) {
    this.deploymentUrl = deploymentUrl;
  }

  /**
   * Set the configurationId of this {@link AiDeployment} instance and return the same instance.
   *
   * @param configurationId ID of the configuration
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment configurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
    return this;
  }

  /**
   * ID of the configuration
   *
   * @return configurationId The configurationId of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getConfigurationId() {
    return configurationId;
  }

  /**
   * Set the configurationId of this {@link AiDeployment} instance.
   *
   * @param configurationId ID of the configuration
   */
  public void setConfigurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
  }

  /**
   * Set the configurationName of this {@link AiDeployment} instance and return the same instance.
   *
   * @param configurationName Name of the configuration
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment configurationName(@Nullable final String configurationName) {
    this.configurationName = configurationName;
    return this;
  }

  /**
   * Name of the configuration
   *
   * @return configurationName The configurationName of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getConfigurationName() {
    return configurationName;
  }

  /**
   * Set the configurationName of this {@link AiDeployment} instance.
   *
   * @param configurationName Name of the configuration
   */
  public void setConfigurationName(@Nullable final String configurationName) {
    this.configurationName = configurationName;
  }

  /**
   * Set the executableId of this {@link AiDeployment} instance and return the same instance.
   *
   * @param executableId ID of the executable
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment executableId(@Nullable final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   *
   * @return executableId The executableId of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link AiDeployment} instance.
   *
   * @param executableId ID of the executable
   */
  public void setExecutableId(@Nullable final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the scenarioId of this {@link AiDeployment} instance and return the same instance.
   *
   * @param scenarioId ID of the scenario
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment scenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return scenarioId The scenarioId of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link AiDeployment} instance.
   *
   * @param scenarioId ID of the scenario
   */
  public void setScenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Set the status of this {@link AiDeployment} instance and return the same instance.
   *
   * @param status The status of this {@link AiDeployment}
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment status(@Nonnull final AiDeploymentStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status The status of this {@link AiDeployment} instance.
   */
  @Nonnull
  public AiDeploymentStatus getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link AiDeployment} instance.
   *
   * @param status The status of this {@link AiDeployment}
   */
  public void setStatus(@Nonnull final AiDeploymentStatus status) {
    this.status = status;
  }

  /**
   * Set the statusMessage of this {@link AiDeployment} instance and return the same instance.
   *
   * @param statusMessage Deployment status message
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment statusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * Deployment status message
   *
   * @return statusMessage The statusMessage of this {@link AiDeployment} instance.
   */
  @Nonnull
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Set the statusMessage of this {@link AiDeployment} instance.
   *
   * @param statusMessage Deployment status message
   */
  public void setStatusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * Set the targetStatus of this {@link AiDeployment} instance and return the same instance.
   *
   * @param targetStatus Deployment target status
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment targetStatus(@Nullable final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
    return this;
  }

  /**
   * Deployment target status
   *
   * @return targetStatus The targetStatus of this {@link AiDeployment} instance.
   */
  @Nonnull
  public TargetStatusEnum getTargetStatus() {
    return targetStatus;
  }

  /**
   * Set the targetStatus of this {@link AiDeployment} instance.
   *
   * @param targetStatus Deployment target status
   */
  public void setTargetStatus(@Nullable final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
  }

  /**
   * Set the lastOperation of this {@link AiDeployment} instance and return the same instance.
   *
   * @param lastOperation The lastOperation of this {@link AiDeployment}
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment lastOperation(@Nullable final LastOperationEnum lastOperation) {
    this.lastOperation = lastOperation;
    return this;
  }

  /**
   * Get lastOperation
   *
   * @return lastOperation The lastOperation of this {@link AiDeployment} instance.
   */
  @Nullable
  public LastOperationEnum getLastOperation() {
    return lastOperation;
  }

  /**
   * Set the lastOperation of this {@link AiDeployment} instance.
   *
   * @param lastOperation The lastOperation of this {@link AiDeployment}
   */
  public void setLastOperation(@Nullable final LastOperationEnum lastOperation) {
    this.lastOperation = lastOperation;
  }

  /**
   * Set the latestRunningConfigurationId of this {@link AiDeployment} instance and return the same
   * instance.
   *
   * @param latestRunningConfigurationId configurationId that was running before a PATCH operation
   *     has modified the configurationId of the deployment. This can be used for a manual rollback
   *     in case the new configurationId results in a DEAD deployment
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment latestRunningConfigurationId(
      @Nullable final String latestRunningConfigurationId) {
    this.latestRunningConfigurationId = latestRunningConfigurationId;
    return this;
  }

  /**
   * configurationId that was running before a PATCH operation has modified the configurationId of
   * the deployment. This can be used for a manual rollback in case the new configurationId results
   * in a DEAD deployment
   *
   * @return latestRunningConfigurationId The latestRunningConfigurationId of this {@link
   *     AiDeployment} instance.
   */
  @Nonnull
  public String getLatestRunningConfigurationId() {
    return latestRunningConfigurationId;
  }

  /**
   * Set the latestRunningConfigurationId of this {@link AiDeployment} instance.
   *
   * @param latestRunningConfigurationId configurationId that was running before a PATCH operation
   *     has modified the configurationId of the deployment. This can be used for a manual rollback
   *     in case the new configurationId results in a DEAD deployment
   */
  public void setLatestRunningConfigurationId(@Nullable final String latestRunningConfigurationId) {
    this.latestRunningConfigurationId = latestRunningConfigurationId;
  }

  /**
   * Set the ttl of this {@link AiDeployment} instance and return the same instance.
   *
   * @param ttl Time to live for a deployment. Its value can be either null or a number followed by
   *     the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment ttl(@Nullable final String ttl) {
    this.ttl = ttl;
    return this;
  }

  /**
   * Time to live for a deployment. Its value can be either null or a number followed by the unit
   * (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   *
   * @return ttl The ttl of this {@link AiDeployment} instance.
   */
  @Nullable
  public String getTtl() {
    return ttl;
  }

  /**
   * Set the ttl of this {@link AiDeployment} instance.
   *
   * @param ttl Time to live for a deployment. Its value can be either null or a number followed by
   *     the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   */
  public void setTtl(@Nullable final String ttl) {
    this.ttl = ttl;
  }

  /**
   * Set the details of this {@link AiDeployment} instance and return the same instance.
   *
   * @param details The details of this {@link AiDeployment}
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment details(@Nullable final AiDeploymentDetails details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   *
   * @return details The details of this {@link AiDeployment} instance.
   */
  @Nonnull
  public AiDeploymentDetails getDetails() {
    return details;
  }

  /**
   * Set the details of this {@link AiDeployment} instance.
   *
   * @param details The details of this {@link AiDeployment}
   */
  public void setDetails(@Nullable final AiDeploymentDetails details) {
    this.details = details;
  }

  /**
   * Set the createdAt of this {@link AiDeployment} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link AiDeployment} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiDeployment} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link AiDeployment} instance and return the same instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   *
   * @return modifiedAt The modifiedAt of this {@link AiDeployment} instance.
   */
  @Nonnull
  public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link AiDeployment} instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   */
  public void setModifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Set the submissionTime of this {@link AiDeployment} instance and return the same instance.
   *
   * @param submissionTime Timestamp of job submitted
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment submissionTime(@Nullable final OffsetDateTime submissionTime) {
    this.submissionTime = submissionTime;
    return this;
  }

  /**
   * Timestamp of job submitted
   *
   * @return submissionTime The submissionTime of this {@link AiDeployment} instance.
   */
  @Nonnull
  public OffsetDateTime getSubmissionTime() {
    return submissionTime;
  }

  /**
   * Set the submissionTime of this {@link AiDeployment} instance.
   *
   * @param submissionTime Timestamp of job submitted
   */
  public void setSubmissionTime(@Nullable final OffsetDateTime submissionTime) {
    this.submissionTime = submissionTime;
  }

  /**
   * Set the startTime of this {@link AiDeployment} instance and return the same instance.
   *
   * @param startTime Timestamp of job status changed to RUNNING
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment startTime(@Nullable final OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Timestamp of job status changed to RUNNING
   *
   * @return startTime The startTime of this {@link AiDeployment} instance.
   */
  @Nonnull
  public OffsetDateTime getStartTime() {
    return startTime;
  }

  /**
   * Set the startTime of this {@link AiDeployment} instance.
   *
   * @param startTime Timestamp of job status changed to RUNNING
   */
  public void setStartTime(@Nullable final OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  /**
   * Set the completionTime of this {@link AiDeployment} instance and return the same instance.
   *
   * @param completionTime Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   * @return The same instance of this {@link AiDeployment} class
   */
  @Nonnull
  public AiDeployment completionTime(@Nullable final OffsetDateTime completionTime) {
    this.completionTime = completionTime;
    return this;
  }

  /**
   * Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   *
   * @return completionTime The completionTime of this {@link AiDeployment} instance.
   */
  @Nonnull
  public OffsetDateTime getCompletionTime() {
    return completionTime;
  }

  /**
   * Set the completionTime of this {@link AiDeployment} instance.
   *
   * @param completionTime Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   */
  public void setCompletionTime(@Nullable final OffsetDateTime completionTime) {
    this.completionTime = completionTime;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeployment}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeployment} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("AiDeployment has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeployment} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
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
    final AiDeployment aiDeployment = (AiDeployment) o;
    return Objects.equals(this.cloudSdkCustomFields, aiDeployment.cloudSdkCustomFields)
        && Objects.equals(this.id, aiDeployment.id)
        && Objects.equals(this.deploymentUrl, aiDeployment.deploymentUrl)
        && Objects.equals(this.configurationId, aiDeployment.configurationId)
        && Objects.equals(this.configurationName, aiDeployment.configurationName)
        && Objects.equals(this.executableId, aiDeployment.executableId)
        && Objects.equals(this.scenarioId, aiDeployment.scenarioId)
        && Objects.equals(this.status, aiDeployment.status)
        && Objects.equals(this.statusMessage, aiDeployment.statusMessage)
        && Objects.equals(this.targetStatus, aiDeployment.targetStatus)
        && Objects.equals(this.lastOperation, aiDeployment.lastOperation)
        && Objects.equals(
            this.latestRunningConfigurationId, aiDeployment.latestRunningConfigurationId)
        && Objects.equals(this.ttl, aiDeployment.ttl)
        && Objects.equals(this.details, aiDeployment.details)
        && Objects.equals(this.createdAt, aiDeployment.createdAt)
        && Objects.equals(this.modifiedAt, aiDeployment.modifiedAt)
        && Objects.equals(this.submissionTime, aiDeployment.submissionTime)
        && Objects.equals(this.startTime, aiDeployment.startTime)
        && Objects.equals(this.completionTime, aiDeployment.completionTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        deploymentUrl,
        configurationId,
        configurationName,
        executableId,
        scenarioId,
        status,
        statusMessage,
        targetStatus,
        lastOperation,
        latestRunningConfigurationId,
        ttl,
        details,
        createdAt,
        modifiedAt,
        submissionTime,
        startTime,
        completionTime,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeployment {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    deploymentUrl: ").append(toIndentedString(deploymentUrl)).append("\n");
    sb.append("    configurationId: ").append(toIndentedString(configurationId)).append("\n");
    sb.append("    configurationName: ").append(toIndentedString(configurationName)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    targetStatus: ").append(toIndentedString(targetStatus)).append("\n");
    sb.append("    lastOperation: ").append(toIndentedString(lastOperation)).append("\n");
    sb.append("    latestRunningConfigurationId: ")
        .append(toIndentedString(latestRunningConfigurationId))
        .append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    modifiedAt: ").append(toIndentedString(modifiedAt)).append("\n");
    sb.append("    submissionTime: ").append(toIndentedString(submissionTime)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    completionTime: ").append(toIndentedString(completionTime)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link AiDeployment} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (id) ->
        (configurationId) ->
            (status) ->
                (createdAt) ->
                    (modifiedAt) ->
                        new AiDeployment()
                            .id(id)
                            .configurationId(configurationId)
                            .status(status)
                            .createdAt(createdAt)
                            .modifiedAt(modifiedAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link AiDeployment} instance.
     *
     * @param id ID of the deployment
     * @return The AiDeployment builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the configurationId of this {@link AiDeployment} instance.
     *
     * @param configurationId ID of the configuration
     * @return The AiDeployment builder.
     */
    Builder2 configurationId(@Nonnull final String configurationId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the status of this {@link AiDeployment} instance.
     *
     * @param status The status of this {@link AiDeployment}
     * @return The AiDeployment builder.
     */
    Builder3 status(@Nonnull final AiDeploymentStatus status);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the createdAt of this {@link AiDeployment} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The AiDeployment builder.
     */
    Builder4 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder4 {
    /**
     * Set the modifiedAt of this {@link AiDeployment} instance.
     *
     * @param modifiedAt Timestamp of latest resource modification
     * @return The AiDeployment instance.
     */
    AiDeployment modifiedAt(@Nonnull final OffsetDateTime modifiedAt);
  }
}
