

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
import com.sap.ai.sdk.core.client.model.AiExecutableArtifact;
import com.sap.ai.sdk.core.client.model.AiExecutableParameter;
import com.sap.ai.sdk.core.client.model.AiLabel;
import java.time.OffsetDateTime;
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
 * An ML executable consists of a set of ML tasks, flows between tasks, dependencies between tasks, models (or model versions?). 
 */
// CHECKSTYLE:OFF
public class AiExecutable 
// CHECKSTYLE:ON
{
  @JsonProperty("labels")
  private List<AiLabel> labels = new ArrayList<>();

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("id")
  private String id;

  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("versionId")
  private String versionId;

  @JsonProperty("parameters")
  private List<AiExecutableParameter> parameters = new ArrayList<>();

  @JsonProperty("inputArtifacts")
  private List<AiExecutableArtifact> inputArtifacts = new ArrayList<>();

  @JsonProperty("outputArtifacts")
  private List<AiExecutableArtifact> outputArtifacts = new ArrayList<>();

  @JsonProperty("deployable")
  private Boolean deployable;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the labels of this {@link AiExecutable} instance and return the same instance.
    *
    * @param labels  Arbitrary labels as meta information
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable labels(@Nonnull final List<AiLabel> labels) {
    this.labels = labels;
    return this;
  }
  /**
   * Add one labels instance to this {@link AiExecutable}.
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link AiExecutable}
   */
  @Nonnull public AiExecutable addLabelsItem( @Nonnull final AiLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

   /**
    * Arbitrary labels as meta information
    * @return labels  The labels of this {@link AiExecutable} instance.
    */
  @Nonnull public List<AiLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link AiExecutable} instance.
   *
   * @param labels  Arbitrary labels as meta information
   */
  public void setLabels( @Nonnull final List<AiLabel> labels) {
    this.labels = labels;
  }

   /**
    * Set the name of this {@link AiExecutable} instance and return the same instance.
    *
    * @param name  Name of the executable
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

   /**
    * Name of the executable
    * @return name  The name of this {@link AiExecutable} instance.
    */
  @Nonnull public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link AiExecutable} instance.
   *
   * @param name  Name of the executable
   */
  public void setName( @Nonnull final String name) {
    this.name = name;
  }

   /**
    * Set the description of this {@link AiExecutable} instance and return the same instance.
    *
    * @param description  Description of the executable
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

   /**
    * Description of the executable
    * @return description  The description of this {@link AiExecutable} instance.
    */
  @Nonnull public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link AiExecutable} instance.
   *
   * @param description  Description of the executable
   */
  public void setDescription( @Nonnull final String description) {
    this.description = description;
  }

   /**
    * Set the id of this {@link AiExecutable} instance and return the same instance.
    *
    * @param id  ID of the executable
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

   /**
    * ID of the executable
    * @return id  The id of this {@link AiExecutable} instance.
    */
  @Nonnull public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiExecutable} instance.
   *
   * @param id  ID of the executable
   */
  public void setId( @Nonnull final String id) {
    this.id = id;
  }

   /**
    * Set the scenarioId of this {@link AiExecutable} instance and return the same instance.
    *
    * @param scenarioId  ID of the scenario
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable scenarioId(@Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

   /**
    * ID of the scenario
    * @return scenarioId  The scenarioId of this {@link AiExecutable} instance.
    */
  @Nonnull public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link AiExecutable} instance.
   *
   * @param scenarioId  ID of the scenario
   */
  public void setScenarioId( @Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
  }

   /**
    * Set the versionId of this {@link AiExecutable} instance and return the same instance.
    *
    * @param versionId  Version ID
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable versionId(@Nonnull final String versionId) {
    this.versionId = versionId;
    return this;
  }

   /**
    * Version ID
    * @return versionId  The versionId of this {@link AiExecutable} instance.
    */
  @Nonnull public String getVersionId() {
    return versionId;
  }

  /**
   * Set the versionId of this {@link AiExecutable} instance.
   *
   * @param versionId  Version ID
   */
  public void setVersionId( @Nonnull final String versionId) {
    this.versionId = versionId;
  }

   /**
    * Set the parameters of this {@link AiExecutable} instance and return the same instance.
    *
    * @param parameters  Executable parameters
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable parameters(@Nonnull final List<AiExecutableParameter> parameters) {
    this.parameters = parameters;
    return this;
  }
  /**
   * Add one parameters instance to this {@link AiExecutable}.
   * @param parametersItem The parameters that should be added
   * @return The same instance of type {@link AiExecutable}
   */
  @Nonnull public AiExecutable addParametersItem( @Nonnull final AiExecutableParameter parametersItem) {
    if (this.parameters == null) {
      this.parameters = new ArrayList<>();
    }
    this.parameters.add(parametersItem);
    return this;
  }

   /**
    * Executable parameters
    * @return parameters  The parameters of this {@link AiExecutable} instance.
    */
  @Nonnull public List<AiExecutableParameter> getParameters() {
    return parameters;
  }

  /**
   * Set the parameters of this {@link AiExecutable} instance.
   *
   * @param parameters  Executable parameters
   */
  public void setParameters( @Nonnull final List<AiExecutableParameter> parameters) {
    this.parameters = parameters;
  }

   /**
    * Set the inputArtifacts of this {@link AiExecutable} instance and return the same instance.
    *
    * @param inputArtifacts  Executable parameters
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable inputArtifacts(@Nonnull final List<AiExecutableArtifact> inputArtifacts) {
    this.inputArtifacts = inputArtifacts;
    return this;
  }
  /**
   * Add one inputArtifacts instance to this {@link AiExecutable}.
   * @param inputArtifactsItem The inputArtifacts that should be added
   * @return The same instance of type {@link AiExecutable}
   */
  @Nonnull public AiExecutable addInputArtifactsItem( @Nonnull final AiExecutableArtifact inputArtifactsItem) {
    if (this.inputArtifacts == null) {
      this.inputArtifacts = new ArrayList<>();
    }
    this.inputArtifacts.add(inputArtifactsItem);
    return this;
  }

   /**
    * Executable parameters
    * @return inputArtifacts  The inputArtifacts of this {@link AiExecutable} instance.
    */
  @Nonnull public List<AiExecutableArtifact> getInputArtifacts() {
    return inputArtifacts;
  }

  /**
   * Set the inputArtifacts of this {@link AiExecutable} instance.
   *
   * @param inputArtifacts  Executable parameters
   */
  public void setInputArtifacts( @Nonnull final List<AiExecutableArtifact> inputArtifacts) {
    this.inputArtifacts = inputArtifacts;
  }

   /**
    * Set the outputArtifacts of this {@link AiExecutable} instance and return the same instance.
    *
    * @param outputArtifacts  Executable parameters
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable outputArtifacts(@Nonnull final List<AiExecutableArtifact> outputArtifacts) {
    this.outputArtifacts = outputArtifacts;
    return this;
  }
  /**
   * Add one outputArtifacts instance to this {@link AiExecutable}.
   * @param outputArtifactsItem The outputArtifacts that should be added
   * @return The same instance of type {@link AiExecutable}
   */
  @Nonnull public AiExecutable addOutputArtifactsItem( @Nonnull final AiExecutableArtifact outputArtifactsItem) {
    if (this.outputArtifacts == null) {
      this.outputArtifacts = new ArrayList<>();
    }
    this.outputArtifacts.add(outputArtifactsItem);
    return this;
  }

   /**
    * Executable parameters
    * @return outputArtifacts  The outputArtifacts of this {@link AiExecutable} instance.
    */
  @Nonnull public List<AiExecutableArtifact> getOutputArtifacts() {
    return outputArtifacts;
  }

  /**
   * Set the outputArtifacts of this {@link AiExecutable} instance.
   *
   * @param outputArtifacts  Executable parameters
   */
  public void setOutputArtifacts( @Nonnull final List<AiExecutableArtifact> outputArtifacts) {
    this.outputArtifacts = outputArtifacts;
  }

   /**
    * Set the deployable of this {@link AiExecutable} instance and return the same instance.
    *
    * @param deployable  Whether this executable is deployable
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable deployable(@Nonnull final Boolean deployable) {
    this.deployable = deployable;
    return this;
  }

   /**
    * Whether this executable is deployable
    * @return deployable  The deployable of this {@link AiExecutable} instance.
    */
  @Nonnull public Boolean isDeployable() {
    return deployable;
  }

  /**
   * Set the deployable of this {@link AiExecutable} instance.
   *
   * @param deployable  Whether this executable is deployable
   */
  public void setDeployable( @Nonnull final Boolean deployable) {
    this.deployable = deployable;
  }

   /**
    * Set the createdAt of this {@link AiExecutable} instance and return the same instance.
    *
    * @param createdAt  Timestamp of resource creation
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
    * Timestamp of resource creation
    * @return createdAt  The createdAt of this {@link AiExecutable} instance.
    */
  @Nonnull public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiExecutable} instance.
   *
   * @param createdAt  Timestamp of resource creation
   */
  public void setCreatedAt( @Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

   /**
    * Set the modifiedAt of this {@link AiExecutable} instance and return the same instance.
    *
    * @param modifiedAt  Timestamp of latest resource modification
    * @return The same instance of this {@link AiExecutable} class
    */
   @Nonnull public AiExecutable modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

   /**
    * Timestamp of latest resource modification
    * @return modifiedAt  The modifiedAt of this {@link AiExecutable} instance.
    */
  @Nonnull public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link AiExecutable} instance.
   *
   * @param modifiedAt  Timestamp of latest resource modification
   */
  public void setModifiedAt( @Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiExecutable}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiExecutable} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiExecutable has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiExecutable} instance. If the map previously contained a mapping
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
    final AiExecutable aiExecutable = (AiExecutable) o;
    return Objects.equals(this.cloudSdkCustomFields, aiExecutable.cloudSdkCustomFields) &&
        Objects.equals(this.labels, aiExecutable.labels) &&
        Objects.equals(this.name, aiExecutable.name) &&
        Objects.equals(this.description, aiExecutable.description) &&
        Objects.equals(this.id, aiExecutable.id) &&
        Objects.equals(this.scenarioId, aiExecutable.scenarioId) &&
        Objects.equals(this.versionId, aiExecutable.versionId) &&
        Objects.equals(this.parameters, aiExecutable.parameters) &&
        Objects.equals(this.inputArtifacts, aiExecutable.inputArtifacts) &&
        Objects.equals(this.outputArtifacts, aiExecutable.outputArtifacts) &&
        Objects.equals(this.deployable, aiExecutable.deployable) &&
        Objects.equals(this.createdAt, aiExecutable.createdAt) &&
        Objects.equals(this.modifiedAt, aiExecutable.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labels, name, description, id, scenarioId, versionId, parameters, inputArtifacts, outputArtifacts, deployable, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiExecutable {\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    versionId: ").append(toIndentedString(versionId)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    inputArtifacts: ").append(toIndentedString(inputArtifacts)).append("\n");
    sb.append("    outputArtifacts: ").append(toIndentedString(outputArtifacts)).append("\n");
    sb.append("    deployable: ").append(toIndentedString(deployable)).append("\n");
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

