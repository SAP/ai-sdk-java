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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** AiVersion */
// CHECKSTYLE:OFF
public class AiVersion
// CHECKSTYLE:ON
{
  @JsonProperty("description")
  private String description;

  @JsonProperty("id")
  private String id;

  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("modifiedAt")
  private OffsetDateTime modifiedAt;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiVersion. */
  protected AiVersion() {}

  /**
   * Set the description of this {@link AiVersion} instance and return the same instance.
   *
   * @param description Version description
   * @return The same instance of this {@link AiVersion} class
   */
  @Nonnull
  public AiVersion description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * Version description
   *
   * @return description The description of this {@link AiVersion} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link AiVersion} instance.
   *
   * @param description Version description
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the id of this {@link AiVersion} instance and return the same instance.
   *
   * @param id Version ID
   * @return The same instance of this {@link AiVersion} class
   */
  @Nonnull
  public AiVersion id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * Version ID
   *
   * @return id The id of this {@link AiVersion} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link AiVersion} instance.
   *
   * @param id Version ID
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the scenarioId of this {@link AiVersion} instance and return the same instance.
   *
   * @param scenarioId ID of the scenario
   * @return The same instance of this {@link AiVersion} class
   */
  @Nonnull
  public AiVersion scenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

  /**
   * ID of the scenario
   *
   * @return scenarioId The scenarioId of this {@link AiVersion} instance.
   */
  @Nonnull
  public String getScenarioId() {
    return scenarioId;
  }

  /**
   * Set the scenarioId of this {@link AiVersion} instance.
   *
   * @param scenarioId ID of the scenario
   */
  public void setScenarioId(@Nullable final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Set the createdAt of this {@link AiVersion} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link AiVersion} class
   */
  @Nonnull
  public AiVersion createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link AiVersion} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link AiVersion} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the modifiedAt of this {@link AiVersion} instance and return the same instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   * @return The same instance of this {@link AiVersion} class
   */
  @Nonnull
  public AiVersion modifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
    return this;
  }

  /**
   * Timestamp of latest resource modification
   *
   * @return modifiedAt The modifiedAt of this {@link AiVersion} instance.
   */
  @Nonnull
  public OffsetDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Set the modifiedAt of this {@link AiVersion} instance.
   *
   * @param modifiedAt Timestamp of latest resource modification
   */
  public void setModifiedAt(@Nonnull final OffsetDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiVersion}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiVersion} instance.
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
      throw new NoSuchElementException("AiVersion has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AiVersion} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (description != null) declaredFields.put("description", description);
    if (id != null) declaredFields.put("id", id);
    if (scenarioId != null) declaredFields.put("scenarioId", scenarioId);
    if (createdAt != null) declaredFields.put("createdAt", createdAt);
    if (modifiedAt != null) declaredFields.put("modifiedAt", modifiedAt);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AiVersion} instance. If the map previously
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
    final AiVersion aiVersion = (AiVersion) o;
    return Objects.equals(this.cloudSdkCustomFields, aiVersion.cloudSdkCustomFields)
        && Objects.equals(this.description, aiVersion.description)
        && Objects.equals(this.id, aiVersion.id)
        && Objects.equals(this.scenarioId, aiVersion.scenarioId)
        && Objects.equals(this.createdAt, aiVersion.createdAt)
        && Objects.equals(this.modifiedAt, aiVersion.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, scenarioId, createdAt, modifiedAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiVersion {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AiVersion} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (id) ->
        (createdAt) ->
            (modifiedAt) -> new AiVersion().id(id).createdAt(createdAt).modifiedAt(modifiedAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link AiVersion} instance.
     *
     * @param id Version ID
     * @return The AiVersion builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the createdAt of this {@link AiVersion} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The AiVersion builder.
     */
    Builder2 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the modifiedAt of this {@link AiVersion} instance.
     *
     * @param modifiedAt Timestamp of latest resource modification
     * @return The AiVersion instance.
     */
    AiVersion modifiedAt(@Nonnull final OffsetDateTime modifiedAt);
  }
}
