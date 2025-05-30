/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Execution that may generate artifacts */
// CHECKSTYLE:OFF
public class AiExecution
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("configurationId")
  private String configurationId;

  @JsonProperty("configurationName")
  private String configurationName;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("scenarioId")
  private String scenarioId;

  /** Target status of the execution */
  public enum TargetStatusEnum {
    /** The COMPLETED option of this AiExecution */
    COMPLETED("COMPLETED"),

    /** The RUNNING option of this AiExecution */
    RUNNING("RUNNING"),

    /** The STOPPED option of this AiExecution */
    STOPPED("STOPPED"),

    /** The DELETED option of this AiExecution */
    DELETED("DELETED"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AiExecution */
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
     * @return The enum value of type AiExecution
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

  @JsonProperty("status")
  private AiExecutionStatus status;

  @JsonProperty("statusMessage")
  private String statusMessage;

  @JsonProperty("outputArtifacts")
  private List<AiArtifact> outputArtifacts = new ArrayList<>();

  @JsonProperty("executionScheduleId")
  private String executionScheduleId;

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

  /** Default constructor for AiExecution. */
  protected AiExecution() {}

  /**
   * Set the id of this {@link AiExecution} instance and return the same instance.
   *
   * @param id ID of the execution
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the execution
   *
   * @return id The id of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiExecution} instance.
   *
   * @param id ID of the execution
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the configurationId of this {@link AiExecution} instance and return the same instance.
   *
   * @param configurationId ID of the configuration
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution configurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
    return this;
  }

  /**
   * ID of the configuration
   *
   * @return configurationId The configurationId of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getConfigurationId() {
    return configurationId;
  }

  /**
   * Set the configurationId of this {@link AiExecution} instance.
   *
   * @param configurationId ID of the configuration
   */
  public void setConfigurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
  }

  /**
   * Set the configurationName of this {@link AiExecution} instance and return the same instance.
   *
   * @param configurationName Name of the configuration
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution configurationName(@Nullable final String configurationName) {
    this.configurationName = configurationName;
    return this;
  }

  /**
   * Name of the configuration
   *
   * @return configurationName The configurationName of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getConfigurationName() {
    return configurationName;
  }

  /**
   * Set the configurationName of this {@link AiExecution} instance.
   *
   * @param configurationName Name of the configuration
   */
  public void setConfigurationName(@Nullable final String configurationName) {
    this.configurationName = configurationName;
  }

  /**
   * Set the executableId of this {@link AiExecution} instance and return the same instance.
   *
   * @param executableId ID of the executable
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution executableId(@Nullable final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   *
   * @return executableId The executableId of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link AiExecution} instance.
   *
   * @param executableId ID of the executable
   */
  public void setExecutableId(@Nullable final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the scenarioId of this {@link AiExecution} instance and return the same instance.
   *
   * @param scenarioId ID of the scenario
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution scenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return scenarioId The scenarioId of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link AiExecution} instance.
   *
   * @param scenarioId ID of the scenario
   */
  public void setScenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Set the targetStatus of this {@link AiExecution} instance and return the same instance.
   *
   * @param targetStatus Target status of the execution
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution targetStatus(@Nullable final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
    return this;
  }

  /**
   * Target status of the execution
   *
   * @return targetStatus The targetStatus of this {@link AiExecution} instance.
   */
  @Nonnull
  public TargetStatusEnum getTargetStatus() {
    return targetStatus;
  }

  /**
   * Set the targetStatus of this {@link AiExecution} instance.
   *
   * @param targetStatus Target status of the execution
   */
  public void setTargetStatus(@Nullable final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
  }

  /**
   * Set the status of this {@link AiExecution} instance and return the same instance.
   *
   * @param status The status of this {@link AiExecution}
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution status(@Nonnull final AiExecutionStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status The status of this {@link AiExecution} instance.
   */
  @Nonnull
  public AiExecutionStatus getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link AiExecution} instance.
   *
   * @param status The status of this {@link AiExecution}
   */
  public void setStatus(@Nonnull final AiExecutionStatus status) {
    this.status = status;
  }

  /**
   * Set the statusMessage of this {@link AiExecution} instance and return the same instance.
   *
   * @param statusMessage Execution status message
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution statusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * Execution status message
   *
   * @return statusMessage The statusMessage of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Set the statusMessage of this {@link AiExecution} instance.
   *
   * @param statusMessage Execution status message
   */
  public void setStatusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * Set the outputArtifacts of this {@link AiExecution} instance and return the same instance.
   *
   * @param outputArtifacts The outputArtifacts of this {@link AiExecution}
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution outputArtifacts(@Nullable final List<AiArtifact> outputArtifacts) {
    this.outputArtifacts = outputArtifacts;
    return this;
  }

  /**
   * Add one outputArtifacts instance to this {@link AiExecution}.
   *
   * @param outputArtifactsItem The outputArtifacts that should be added
   * @return The same instance of type {@link AiExecution}
   */
  @Nonnull
  public AiExecution addOutputArtifactsItem(@Nonnull final AiArtifact outputArtifactsItem) {
    if (this.outputArtifacts == null) {
      this.outputArtifacts = new ArrayList<>();
    }
    this.outputArtifacts.add(outputArtifactsItem);
    return this;
  }

  /**
   * Get outputArtifacts
   *
   * @return outputArtifacts The outputArtifacts of this {@link AiExecution} instance.
   */
  @Nonnull
  public List<AiArtifact> getOutputArtifacts() {
    return outputArtifacts;
  }

  /**
   * Set the outputArtifacts of this {@link AiExecution} instance.
   *
   * @param outputArtifacts The outputArtifacts of this {@link AiExecution}
   */
  public void setOutputArtifacts(@Nullable final List<AiArtifact> outputArtifacts) {
    this.outputArtifacts = outputArtifacts;
  }

  /**
   * Set the executionScheduleId of this {@link AiExecution} instance and return the same instance.
   *
   * @param executionScheduleId ID of the execution schedule
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution executionScheduleId(@Nullable final String executionScheduleId) {
    this.executionScheduleId = executionScheduleId;
    return this;
  }

  /**
   * ID of the execution schedule
   *
   * @return executionScheduleId The executionScheduleId of this {@link AiExecution} instance.
   */
  @Nonnull
  public String getExecutionScheduleId() {
    return executionScheduleId;
  }

  /**
   * Set the executionScheduleId of this {@link AiExecution} instance.
   *
   * @param executionScheduleId ID of the execution schedule
   */
  public void setExecutionScheduleId(@Nullable final String executionScheduleId) {
    this.executionScheduleId = executionScheduleId;
  }

  /**
   * Set the createdAt of this {@link AiExecution} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link AiExecution} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiExecution} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link AiExecution} instance and return the same instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   *
   * @return modifiedAt The modifiedAt of this {@link AiExecution} instance.
   */
  @Nonnull
  public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link AiExecution} instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   */
  public void setModifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Set the submissionTime of this {@link AiExecution} instance and return the same instance.
   *
   * @param submissionTime Timestamp of job submitted
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution submissionTime(@Nullable final OffsetDateTime submissionTime) {
    this.submissionTime = submissionTime;
    return this;
  }

  /**
   * Timestamp of job submitted
   *
   * @return submissionTime The submissionTime of this {@link AiExecution} instance.
   */
  @Nonnull
  public OffsetDateTime getSubmissionTime() {
    return submissionTime;
  }

  /**
   * Set the submissionTime of this {@link AiExecution} instance.
   *
   * @param submissionTime Timestamp of job submitted
   */
  public void setSubmissionTime(@Nullable final OffsetDateTime submissionTime) {
    this.submissionTime = submissionTime;
  }

  /**
   * Set the startTime of this {@link AiExecution} instance and return the same instance.
   *
   * @param startTime Timestamp of job status changed to RUNNING
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution startTime(@Nullable final OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Timestamp of job status changed to RUNNING
   *
   * @return startTime The startTime of this {@link AiExecution} instance.
   */
  @Nonnull
  public OffsetDateTime getStartTime() {
    return startTime;
  }

  /**
   * Set the startTime of this {@link AiExecution} instance.
   *
   * @param startTime Timestamp of job status changed to RUNNING
   */
  public void setStartTime(@Nullable final OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  /**
   * Set the completionTime of this {@link AiExecution} instance and return the same instance.
   *
   * @param completionTime Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   * @return The same instance of this {@link AiExecution} class
   */
  @Nonnull
  public AiExecution completionTime(@Nullable final OffsetDateTime completionTime) {
    this.completionTime = completionTime;
    return this;
  }

  /**
   * Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   *
   * @return completionTime The completionTime of this {@link AiExecution} instance.
   */
  @Nonnull
  public OffsetDateTime getCompletionTime() {
    return completionTime;
  }

  /**
   * Set the completionTime of this {@link AiExecution} instance.
   *
   * @param completionTime Timestamp of job status changed to COMPLETED/DEAD/STOPPED
   */
  public void setCompletionTime(@Nullable final OffsetDateTime completionTime) {
    this.completionTime = completionTime;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiExecution}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiExecution} instance.
   *
   * @deprecated Use {@link #toMap()} instead.
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  @Deprecated
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("AiExecution has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AiExecution} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (configurationId != null) declaredFields.put("configurationId", configurationId);
    if (configurationName != null) declaredFields.put("configurationName", configurationName);
    if (executableId != null) declaredFields.put("executableId", executableId);
    if (scenarioId != null) declaredFields.put("scenarioId", scenarioId);
    if (targetStatus != null) declaredFields.put("targetStatus", targetStatus);
    if (status != null) declaredFields.put("status", status);
    if (statusMessage != null) declaredFields.put("statusMessage", statusMessage);
    if (outputArtifacts != null) declaredFields.put("outputArtifacts", outputArtifacts);
    if (executionScheduleId != null) declaredFields.put("executionScheduleId", executionScheduleId);
    if (createdAt != null) declaredFields.put("createdAt", createdAt);
    if (modifiedAt != null) declaredFields.put("modifiedAt", modifiedAt);
    if (submissionTime != null) declaredFields.put("submissionTime", submissionTime);
    if (startTime != null) declaredFields.put("startTime", startTime);
    if (completionTime != null) declaredFields.put("completionTime", completionTime);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AiExecution} instance. If the map previously
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
    final AiExecution aiExecution = (AiExecution) o;
    return Objects.equals(this.cloudSdkCustomFields, aiExecution.cloudSdkCustomFields)
        && Objects.equals(this.id, aiExecution.id)
        && Objects.equals(this.configurationId, aiExecution.configurationId)
        && Objects.equals(this.configurationName, aiExecution.configurationName)
        && Objects.equals(this.executableId, aiExecution.executableId)
        && Objects.equals(this.scenarioId, aiExecution.scenarioId)
        && Objects.equals(this.targetStatus, aiExecution.targetStatus)
        && Objects.equals(this.status, aiExecution.status)
        && Objects.equals(this.statusMessage, aiExecution.statusMessage)
        && Objects.equals(this.outputArtifacts, aiExecution.outputArtifacts)
        && Objects.equals(this.executionScheduleId, aiExecution.executionScheduleId)
        && Objects.equals(this.createdAt, aiExecution.createdAt)
        && Objects.equals(this.modifiedAt, aiExecution.modifiedAt)
        && Objects.equals(this.submissionTime, aiExecution.submissionTime)
        && Objects.equals(this.startTime, aiExecution.startTime)
        && Objects.equals(this.completionTime, aiExecution.completionTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        configurationId,
        configurationName,
        executableId,
        scenarioId,
        targetStatus,
        status,
        statusMessage,
        outputArtifacts,
        executionScheduleId,
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
    sb.append("class AiExecution {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    configurationId: ").append(toIndentedString(configurationId)).append("\n");
    sb.append("    configurationName: ").append(toIndentedString(configurationName)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    targetStatus: ").append(toIndentedString(targetStatus)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    outputArtifacts: ").append(toIndentedString(outputArtifacts)).append("\n");
    sb.append("    executionScheduleId: ")
        .append(toIndentedString(executionScheduleId))
        .append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AiExecution} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (id) ->
        (configurationId) ->
            (status) ->
                (createdAt) ->
                    (modifiedAt) ->
                        new AiExecution()
                            .id(id)
                            .configurationId(configurationId)
                            .status(status)
                            .createdAt(createdAt)
                            .modifiedAt(modifiedAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link AiExecution} instance.
     *
     * @param id ID of the execution
     * @return The AiExecution builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the configurationId of this {@link AiExecution} instance.
     *
     * @param configurationId ID of the configuration
     * @return The AiExecution builder.
     */
    Builder2 configurationId(@Nonnull final String configurationId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the status of this {@link AiExecution} instance.
     *
     * @param status The status of this {@link AiExecution}
     * @return The AiExecution builder.
     */
    Builder3 status(@Nonnull final AiExecutionStatus status);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the createdAt of this {@link AiExecution} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The AiExecution builder.
     */
    Builder4 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder4 {
    /**
     * Set the modifiedAt of this {@link AiExecution} instance.
     *
     * @param modifiedAt Timestamp of latest resource modification
     * @return The AiExecution instance.
     */
    AiExecution modifiedAt(@Nonnull final OffsetDateTime modifiedAt);
  }
}
