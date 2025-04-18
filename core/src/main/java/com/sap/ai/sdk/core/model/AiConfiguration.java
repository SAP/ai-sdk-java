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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

/** AiConfiguration */
// CHECKSTYLE:OFF
public class AiConfiguration
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("parameterBindings")
  private List<AiParameterArgumentBinding> parameterBindings = new ArrayList<>();

  @JsonProperty("inputArtifactBindings")
  private List<AiArtifactArgumentBinding> inputArtifactBindings = new ArrayList<>();

  @JsonProperty("id")
  private String id;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("scenario")
  private AiScenario scenario;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiConfiguration. */
  protected AiConfiguration() {}

  /**
   * Set the name of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param name Name of the configuration
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the configuration
   *
   * @return name The name of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link AiConfiguration} instance.
   *
   * @param name Name of the configuration
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the executableId of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param executableId ID of the executable
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration executableId(@Nonnull final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   *
   * @return executableId The executableId of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link AiConfiguration} instance.
   *
   * @param executableId ID of the executable
   */
  public void setExecutableId(@Nonnull final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the scenarioId of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param scenarioId ID of the scenario
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration scenarioId(@Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return scenarioId The scenarioId of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link AiConfiguration} instance.
   *
   * @param scenarioId ID of the scenario
   */
  public void setScenarioId(@Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Set the parameterBindings of this {@link AiConfiguration} instance and return the same
   * instance.
   *
   * @param parameterBindings The parameterBindings of this {@link AiConfiguration}
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration parameterBindings(
      @Nullable final List<AiParameterArgumentBinding> parameterBindings) {
    this.parameterBindings = parameterBindings;
    return this;
  }

  /**
   * Add one parameterBindings instance to this {@link AiConfiguration}.
   *
   * @param parameterBindingsItem The parameterBindings that should be added
   * @return The same instance of type {@link AiConfiguration}
   */
  @Nonnull
  public AiConfiguration addParameterBindingsItem(
      @Nonnull final AiParameterArgumentBinding parameterBindingsItem) {
    if (this.parameterBindings == null) {
      this.parameterBindings = new ArrayList<>();
    }
    this.parameterBindings.add(parameterBindingsItem);
    return this;
  }

  /**
   * Get parameterBindings
   *
   * @return parameterBindings The parameterBindings of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public List<AiParameterArgumentBinding> getParameterBindings() {
    return parameterBindings;
  }

  /**
   * Set the parameterBindings of this {@link AiConfiguration} instance.
   *
   * @param parameterBindings The parameterBindings of this {@link AiConfiguration}
   */
  public void setParameterBindings(
      @Nullable final List<AiParameterArgumentBinding> parameterBindings) {
    this.parameterBindings = parameterBindings;
  }

  /**
   * Set the inputArtifactBindings of this {@link AiConfiguration} instance and return the same
   * instance.
   *
   * @param inputArtifactBindings The inputArtifactBindings of this {@link AiConfiguration}
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration inputArtifactBindings(
      @Nullable final List<AiArtifactArgumentBinding> inputArtifactBindings) {
    this.inputArtifactBindings = inputArtifactBindings;
    return this;
  }

  /**
   * Add one inputArtifactBindings instance to this {@link AiConfiguration}.
   *
   * @param inputArtifactBindingsItem The inputArtifactBindings that should be added
   * @return The same instance of type {@link AiConfiguration}
   */
  @Nonnull
  public AiConfiguration addInputArtifactBindingsItem(
      @Nonnull final AiArtifactArgumentBinding inputArtifactBindingsItem) {
    if (this.inputArtifactBindings == null) {
      this.inputArtifactBindings = new ArrayList<>();
    }
    this.inputArtifactBindings.add(inputArtifactBindingsItem);
    return this;
  }

  /**
   * Get inputArtifactBindings
   *
   * @return inputArtifactBindings The inputArtifactBindings of this {@link AiConfiguration}
   *     instance.
   */
  @Nonnull
  public List<AiArtifactArgumentBinding> getInputArtifactBindings() {
    return inputArtifactBindings;
  }

  /**
   * Set the inputArtifactBindings of this {@link AiConfiguration} instance.
   *
   * @param inputArtifactBindings The inputArtifactBindings of this {@link AiConfiguration}
   */
  public void setInputArtifactBindings(
      @Nullable final List<AiArtifactArgumentBinding> inputArtifactBindings) {
    this.inputArtifactBindings = inputArtifactBindings;
  }

  /**
   * Set the id of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param id ID of the configuration
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the configuration
   *
   * @return id The id of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiConfiguration} instance.
   *
   * @param id ID of the configuration
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the createdAt of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link AiConfiguration} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiConfiguration} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the scenario of this {@link AiConfiguration} instance and return the same instance.
   *
   * @param scenario The scenario of this {@link AiConfiguration}
   * @return The same instance of this {@link AiConfiguration} class
   */
  @Nonnull
  public AiConfiguration scenario(@Nullable final AiScenario scenario) {
    this.scenario = scenario;
    return this;
  }

  /**
   * Get scenario
   *
   * @return scenario The scenario of this {@link AiConfiguration} instance.
   */
  @Nullable
  public AiScenario getScenario() {
    return scenario;
  }

  /**
   * Set the scenario of this {@link AiConfiguration} instance.
   *
   * @param scenario The scenario of this {@link AiConfiguration}
   */
  public void setScenario(@Nullable final AiScenario scenario) {
    this.scenario = scenario;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiConfiguration}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiConfiguration} instance.
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
      throw new NoSuchElementException("AiConfiguration has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AiConfiguration} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (executableId != null) declaredFields.put("executableId", executableId);
    if (scenarioId != null) declaredFields.put("scenarioId", scenarioId);
    if (parameterBindings != null) declaredFields.put("parameterBindings", parameterBindings);
    if (inputArtifactBindings != null)
      declaredFields.put("inputArtifactBindings", inputArtifactBindings);
    if (id != null) declaredFields.put("id", id);
    if (createdAt != null) declaredFields.put("createdAt", createdAt);
    if (scenario != null) declaredFields.put("scenario", scenario);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AiConfiguration} instance. If the map previously
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
    final AiConfiguration aiConfiguration = (AiConfiguration) o;
    return Objects.equals(this.cloudSdkCustomFields, aiConfiguration.cloudSdkCustomFields)
        && Objects.equals(this.name, aiConfiguration.name)
        && Objects.equals(this.executableId, aiConfiguration.executableId)
        && Objects.equals(this.scenarioId, aiConfiguration.scenarioId)
        && Objects.equals(this.parameterBindings, aiConfiguration.parameterBindings)
        && Objects.equals(this.inputArtifactBindings, aiConfiguration.inputArtifactBindings)
        && Objects.equals(this.id, aiConfiguration.id)
        && Objects.equals(this.createdAt, aiConfiguration.createdAt)
        && Objects.equals(this.scenario, aiConfiguration.scenario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        executableId,
        scenarioId,
        parameterBindings,
        inputArtifactBindings,
        id,
        createdAt,
        scenario,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiConfiguration {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    sb.append("    parameterBindings: ").append(toIndentedString(parameterBindings)).append("\n");
    sb.append("    inputArtifactBindings: ")
        .append(toIndentedString(inputArtifactBindings))
        .append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    scenario: ").append(toIndentedString(scenario)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AiConfiguration}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (name) ->
        (executableId) ->
            (scenarioId) ->
                (id) ->
                    (createdAt) ->
                        new AiConfiguration()
                            .name(name)
                            .executableId(executableId)
                            .scenarioId(scenarioId)
                            .id(id)
                            .createdAt(createdAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link AiConfiguration} instance.
     *
     * @param name Name of the configuration
     * @return The AiConfiguration builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the executableId of this {@link AiConfiguration} instance.
     *
     * @param executableId ID of the executable
     * @return The AiConfiguration builder.
     */
    Builder2 executableId(@Nonnull final String executableId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the scenarioId of this {@link AiConfiguration} instance.
     *
     * @param scenarioId ID of the scenario
     * @return The AiConfiguration builder.
     */
    Builder3 scenarioId(@Nonnull final String scenarioId);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the id of this {@link AiConfiguration} instance.
     *
     * @param id ID of the configuration
     * @return The AiConfiguration builder.
     */
    Builder4 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder4 {
    /**
     * Set the createdAt of this {@link AiConfiguration} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The AiConfiguration instance.
     */
    AiConfiguration createdAt(@Nonnull final OffsetDateTime createdAt);
  }
}
