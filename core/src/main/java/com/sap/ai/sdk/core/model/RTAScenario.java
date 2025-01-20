/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.38.0
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
import com.google.common.annotations.Beta;
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

/** Entity having labels */
@Beta // CHECKSTYLE:OFF
public class RTAScenario
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("labels")
  private List<RTALabel> labels = new ArrayList<>();

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RTAScenario. */
  protected RTAScenario() {}

  /**
   * Set the id of this {@link RTAScenario} instance and return the same instance.
   *
   * @param id ID of the scenario
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return id The id of this {@link RTAScenario} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link RTAScenario} instance.
   *
   * @param id ID of the scenario
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the name of this {@link RTAScenario} instance and return the same instance.
   *
   * @param name Name of the scenario
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the scenario
   *
   * @return name The name of this {@link RTAScenario} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link RTAScenario} instance.
   *
   * @param name Name of the scenario
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the description of this {@link RTAScenario} instance and return the same instance.
   *
   * @param description Description of the scenario
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the scenario
   *
   * @return description The description of this {@link RTAScenario} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link RTAScenario} instance.
   *
   * @param description Description of the scenario
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the labels of this {@link RTAScenario} instance and return the same instance.
   *
   * @param labels Arbitrary labels as meta information
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario labels(@Nullable final List<RTALabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link RTAScenario}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link RTAScenario}
   */
  @Nonnull
  public RTAScenario addLabelsItem(@Nonnull final RTALabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Arbitrary labels as meta information
   *
   * @return labels The labels of this {@link RTAScenario} instance.
   */
  @Nonnull
  public List<RTALabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link RTAScenario} instance.
   *
   * @param labels Arbitrary labels as meta information
   */
  public void setLabels(@Nullable final List<RTALabel> labels) {
    this.labels = labels;
  }

  /**
   * Set the createdAt of this {@link RTAScenario} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link RTAScenario} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link RTAScenario} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link RTAScenario} instance and return the same instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   * @return The same instance of this {@link RTAScenario} class
   */
  @Nonnull
  public RTAScenario modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   *
   * @return modifiedAt The modifiedAt of this {@link RTAScenario} instance.
   */
  @Nonnull
  public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link RTAScenario} instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   */
  public void setModifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAScenario}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAScenario} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("RTAScenario has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAScenario} instance. If the map previously
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
    final RTAScenario rtAScenario = (RTAScenario) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAScenario.cloudSdkCustomFields)
        && Objects.equals(this.id, rtAScenario.id)
        && Objects.equals(this.name, rtAScenario.name)
        && Objects.equals(this.description, rtAScenario.description)
        && Objects.equals(this.labels, rtAScenario.labels)
        && Objects.equals(this.createdAt, rtAScenario.createdAt)
        && Objects.equals(this.modifiedAt, rtAScenario.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, labels, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAScenario {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link RTAScenario} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (id) ->
        (name) ->
            (createdAt) ->
                (modifiedAt) ->
                    new RTAScenario().id(id).name(name).createdAt(createdAt).modifiedAt(modifiedAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link RTAScenario} instance.
     *
     * @param id ID of the scenario
     * @return The RTAScenario builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the name of this {@link RTAScenario} instance.
     *
     * @param name Name of the scenario
     * @return The RTAScenario builder.
     */
    Builder2 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the createdAt of this {@link RTAScenario} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The RTAScenario builder.
     */
    Builder3 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the modifiedAt of this {@link RTAScenario} instance.
     *
     * @param modifiedAt Timestamp of latest resource modification
     * @return The RTAScenario instance.
     */
    RTAScenario modifiedAt(@Nonnull final OffsetDateTime modifiedAt);
  }
}
