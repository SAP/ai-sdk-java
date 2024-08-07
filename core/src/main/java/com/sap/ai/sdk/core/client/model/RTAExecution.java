

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
 * Execution
 */

// CHECKSTYLE:OFF
public class RTAExecution 
// CHECKSTYLE:ON
{
  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("id")
  private String id;

  /**
   * Status of the execution
   */
  public enum StatusEnum {
    /**
    * The PENDING option of this RTAExecution
    */
    PENDING("PENDING"),
    
    /**
    * The RUNNING option of this RTAExecution
    */
    RUNNING("RUNNING"),
    
    /**
    * The COMPLETED option of this RTAExecution
    */
    COMPLETED("COMPLETED"),
    
    /**
    * The DEAD option of this RTAExecution
    */
    DEAD("DEAD"),
    
    /**
    * The STOPPING option of this RTAExecution
    */
    STOPPING("STOPPING"),
    
    /**
    * The STOPPED option of this RTAExecution
    */
    STOPPED("STOPPED"),
    
    /**
    * The UNKNOWN option of this RTAExecution
    */
    UNKNOWN("UNKNOWN");

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
    * @return The enum value of type RTAExecution
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

  @JsonProperty("submissionTimestamp")
  private OffsetDateTime submissionTimestamp;

  @JsonProperty("startTimestamp")
  private OffsetDateTime startTimestamp;

  @JsonProperty("finishTimestamp")
  private OffsetDateTime finishTimestamp;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the scenarioId of this {@link RTAExecution} instance and return the same instance.
   *
   * @param scenarioId  ID of the scenario
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution scenarioId(@Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

   /**
   * ID of the scenario
   * @return scenarioId  The scenarioId of this {@link RTAExecution} instance.
  **/
  @Nonnull public String getScenarioId() {
    return scenarioId;
  }

  /**
  * Set the scenarioId of this {@link RTAExecution} instance.
  *
  * @param scenarioId  ID of the scenario
  */
  public void setScenarioId( @Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
  }

   /**
   * Set the executableId of this {@link RTAExecution} instance and return the same instance.
   *
   * @param executableId  ID of the executable
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution executableId(@Nonnull final String executableId) {
    this.executableId = executableId;
    return this;
  }

   /**
   * ID of the executable
   * @return executableId  The executableId of this {@link RTAExecution} instance.
  **/
  @Nonnull public String getExecutableId() {
    return executableId;
  }

  /**
  * Set the executableId of this {@link RTAExecution} instance.
  *
  * @param executableId  ID of the executable
  */
  public void setExecutableId( @Nonnull final String executableId) {
    this.executableId = executableId;
  }

   /**
   * Set the id of this {@link RTAExecution} instance and return the same instance.
   *
   * @param id  ID of the execution
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

   /**
   * ID of the execution
   * @return id  The id of this {@link RTAExecution} instance.
  **/
  @Nonnull public String getId() {
    return id;
  }

  /**
  * Set the id of this {@link RTAExecution} instance.
  *
  * @param id  ID of the execution
  */
  public void setId( @Nonnull final String id) {
    this.id = id;
  }

   /**
   * Set the status of this {@link RTAExecution} instance and return the same instance.
   *
   * @param status  Status of the execution
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution status(@Nonnull final StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the execution
   * @return status  The status of this {@link RTAExecution} instance.
  **/
  @Nonnull public StatusEnum getStatus() {
    return status;
  }

  /**
  * Set the status of this {@link RTAExecution} instance.
  *
  * @param status  Status of the execution
  */
  public void setStatus( @Nonnull final StatusEnum status) {
    this.status = status;
  }

   /**
   * Set the statusMessage of this {@link RTAExecution} instance and return the same instance.
   *
   * @param statusMessage  Execution status message
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution statusMessage(@Nonnull final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

   /**
   * Execution status message
   * @return statusMessage  The statusMessage of this {@link RTAExecution} instance.
  **/
  @Nonnull public String getStatusMessage() {
    return statusMessage;
  }

  /**
  * Set the statusMessage of this {@link RTAExecution} instance.
  *
  * @param statusMessage  Execution status message
  */
  public void setStatusMessage( @Nonnull final String statusMessage) {
    this.statusMessage = statusMessage;
  }

   /**
   * Set the submissionTimestamp of this {@link RTAExecution} instance and return the same instance.
   *
   * @param submissionTimestamp  Timestamp of execution submission
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution submissionTimestamp(@Nonnull final OffsetDateTime submissionTimestamp) {
    this.submissionTimestamp = submissionTimestamp;
    return this;
  }

   /**
   * Timestamp of execution submission
   * @return submissionTimestamp  The submissionTimestamp of this {@link RTAExecution} instance.
  **/
  @Nonnull public OffsetDateTime getSubmissionTimestamp() {
    return submissionTimestamp;
  }

  /**
  * Set the submissionTimestamp of this {@link RTAExecution} instance.
  *
  * @param submissionTimestamp  Timestamp of execution submission
  */
  public void setSubmissionTimestamp( @Nonnull final OffsetDateTime submissionTimestamp) {
    this.submissionTimestamp = submissionTimestamp;
  }

   /**
   * Set the startTimestamp of this {@link RTAExecution} instance and return the same instance.
   *
   * @param startTimestamp  Timestamp of execution start
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution startTimestamp(@Nonnull final OffsetDateTime startTimestamp) {
    this.startTimestamp = startTimestamp;
    return this;
  }

   /**
   * Timestamp of execution start
   * @return startTimestamp  The startTimestamp of this {@link RTAExecution} instance.
  **/
  @Nonnull public OffsetDateTime getStartTimestamp() {
    return startTimestamp;
  }

  /**
  * Set the startTimestamp of this {@link RTAExecution} instance.
  *
  * @param startTimestamp  Timestamp of execution start
  */
  public void setStartTimestamp( @Nonnull final OffsetDateTime startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

   /**
   * Set the finishTimestamp of this {@link RTAExecution} instance and return the same instance.
   *
   * @param finishTimestamp  Timestamp of execution finish
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution finishTimestamp(@Nonnull final OffsetDateTime finishTimestamp) {
    this.finishTimestamp = finishTimestamp;
    return this;
  }

   /**
   * Timestamp of execution finish
   * @return finishTimestamp  The finishTimestamp of this {@link RTAExecution} instance.
  **/
  @Nonnull public OffsetDateTime getFinishTimestamp() {
    return finishTimestamp;
  }

  /**
  * Set the finishTimestamp of this {@link RTAExecution} instance.
  *
  * @param finishTimestamp  Timestamp of execution finish
  */
  public void setFinishTimestamp( @Nonnull final OffsetDateTime finishTimestamp) {
    this.finishTimestamp = finishTimestamp;
  }

   /**
   * Set the createdAt of this {@link RTAExecution} instance and return the same instance.
   *
   * @param createdAt  Timestamp of resource creation
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Timestamp of resource creation
   * @return createdAt  The createdAt of this {@link RTAExecution} instance.
  **/
  @Nonnull public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
  * Set the createdAt of this {@link RTAExecution} instance.
  *
  * @param createdAt  Timestamp of resource creation
  */
  public void setCreatedAt( @Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

   /**
   * Set the modifiedAt of this {@link RTAExecution} instance and return the same instance.
   *
   * @param modifiedAt  Timestamp of latest resource modification
   * @return The same instance of this {@link RTAExecution} class
   */
   @Nonnull public RTAExecution modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

   /**
   * Timestamp of latest resource modification
   * @return modifiedAt  The modifiedAt of this {@link RTAExecution} instance.
  **/
  @Nonnull public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
  * Set the modifiedAt of this {@link RTAExecution} instance.
  *
  * @param modifiedAt  Timestamp of latest resource modification
  */
  public void setModifiedAt( @Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAExecution}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAExecution} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("RTAExecution has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAExecution} instance. If the map previously contained a mapping
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
    final RTAExecution rtAExecution = (RTAExecution) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAExecution.cloudSdkCustomFields) &&
        Objects.equals(this.scenarioId, rtAExecution.scenarioId) &&
        Objects.equals(this.executableId, rtAExecution.executableId) &&
        Objects.equals(this.id, rtAExecution.id) &&
        Objects.equals(this.status, rtAExecution.status) &&
        Objects.equals(this.statusMessage, rtAExecution.statusMessage) &&
        Objects.equals(this.submissionTimestamp, rtAExecution.submissionTimestamp) &&
        Objects.equals(this.startTimestamp, rtAExecution.startTimestamp) &&
        Objects.equals(this.finishTimestamp, rtAExecution.finishTimestamp) &&
        Objects.equals(this.createdAt, rtAExecution.createdAt) &&
        Objects.equals(this.modifiedAt, rtAExecution.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scenarioId, executableId, id, status, statusMessage, submissionTimestamp, startTimestamp, finishTimestamp, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAExecution {\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    submissionTimestamp: ").append(toIndentedString(submissionTimestamp)).append("\n");
    sb.append("    startTimestamp: ").append(toIndentedString(startTimestamp)).append("\n");
    sb.append("    finishTimestamp: ").append(toIndentedString(finishTimestamp)).append("\n");
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

}

