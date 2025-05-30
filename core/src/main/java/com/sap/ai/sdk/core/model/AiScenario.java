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

/**
 * An ML Scenario consists of a number of executables. E.g., there can be one or several training
 * executables, an inference (deployment) executable. An ML Scenario is versioned.
 */
// CHECKSTYLE:OFF
public class AiScenario
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("labels")
  private List<AiScenarioLabel> labels = new ArrayList<>();

  @JsonProperty("id")
  private String id;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiScenario. */
  protected AiScenario() {}

  /**
   * Set the name of this {@link AiScenario} instance and return the same instance.
   *
   * @param name Name of the scenario
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the scenario
   *
   * @return name The name of this {@link AiScenario} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link AiScenario} instance.
   *
   * @param name Name of the scenario
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the description of this {@link AiScenario} instance and return the same instance.
   *
   * @param description Description of the scenario
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the scenario
   *
   * @return description The description of this {@link AiScenario} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link AiScenario} instance.
   *
   * @param description Description of the scenario
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the labels of this {@link AiScenario} instance and return the same instance.
   *
   * @param labels Arbitrary labels as meta information
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario labels(@Nullable final List<AiScenarioLabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link AiScenario}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link AiScenario}
   */
  @Nonnull
  public AiScenario addLabelsItem(@Nonnull final AiScenarioLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Arbitrary labels as meta information
   *
   * @return labels The labels of this {@link AiScenario} instance.
   */
  @Nonnull
  public List<AiScenarioLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link AiScenario} instance.
   *
   * @param labels Arbitrary labels as meta information
   */
  public void setLabels(@Nullable final List<AiScenarioLabel> labels) {
    this.labels = labels;
  }

  /**
   * Set the id of this {@link AiScenario} instance and return the same instance.
   *
   * @param id ID of the scenario
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return id The id of this {@link AiScenario} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiScenario} instance.
   *
   * @param id ID of the scenario
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the createdAt of this {@link AiScenario} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link AiScenario} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiScenario} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link AiScenario} instance and return the same instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   * @return The same instance of this {@link AiScenario} class
   */
  @Nonnull
  public AiScenario modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   *
   * @return modifiedAt The modifiedAt of this {@link AiScenario} instance.
   */
  @Nonnull
  public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link AiScenario} instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   */
  public void setModifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiScenario}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiScenario} instance.
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
      throw new NoSuchElementException("AiScenario has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AiScenario} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (description != null) declaredFields.put("description", description);
    if (labels != null) declaredFields.put("labels", labels);
    if (id != null) declaredFields.put("id", id);
    if (createdAt != null) declaredFields.put("createdAt", createdAt);
    if (modifiedAt != null) declaredFields.put("modifiedAt", modifiedAt);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AiScenario} instance. If the map previously
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
    final AiScenario aiScenario = (AiScenario) o;
    return Objects.equals(this.cloudSdkCustomFields, aiScenario.cloudSdkCustomFields)
        && Objects.equals(this.name, aiScenario.name)
        && Objects.equals(this.description, aiScenario.description)
        && Objects.equals(this.labels, aiScenario.labels)
        && Objects.equals(this.id, aiScenario.id)
        && Objects.equals(this.createdAt, aiScenario.createdAt)
        && Objects.equals(this.modifiedAt, aiScenario.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, labels, id, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiScenario {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    modifiedAt: ").append(toIndentedString(modifiedAt)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AiScenario} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (name) ->
        (id) ->
            (createdAt) ->
                (modifiedAt) ->
                    new AiScenario().name(name).id(id).createdAt(createdAt).modifiedAt(modifiedAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link AiScenario} instance.
     *
     * @param name Name of the scenario
     * @return The AiScenario builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the id of this {@link AiScenario} instance.
     *
     * @param id ID of the scenario
     * @return The AiScenario builder.
     */
    Builder2 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the createdAt of this {@link AiScenario} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The AiScenario builder.
     */
    Builder3 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the modifiedAt of this {@link AiScenario} instance.
     *
     * @param modifiedAt Timestamp of latest resource modification
     * @return The AiScenario instance.
     */
    AiScenario modifiedAt(@Nonnull final OffsetDateTime modifiedAt);
  }
}
