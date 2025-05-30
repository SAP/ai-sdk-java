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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndUsageResourcePlanItem */
// CHECKSTYLE:OFF
public class BckndUsageResourcePlanItem
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("resourcePlanType")
  private String resourcePlanType;

  @JsonProperty("configuredMaxReplicas")
  private Integer configuredMaxReplicas;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndUsageResourcePlanItem. */
  protected BckndUsageResourcePlanItem() {}

  /**
   * Set the id of this {@link BckndUsageResourcePlanItem} instance and return the same instance.
   *
   * @param id The id of this {@link BckndUsageResourcePlanItem}
   * @return The same instance of this {@link BckndUsageResourcePlanItem} class
   */
  @Nonnull
  public BckndUsageResourcePlanItem id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id The id of this {@link BckndUsageResourcePlanItem} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link BckndUsageResourcePlanItem} instance.
   *
   * @param id The id of this {@link BckndUsageResourcePlanItem}
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the resourcePlanType of this {@link BckndUsageResourcePlanItem} instance and return the
   * same instance.
   *
   * @param resourcePlanType The resourcePlanType of this {@link BckndUsageResourcePlanItem}
   * @return The same instance of this {@link BckndUsageResourcePlanItem} class
   */
  @Nonnull
  public BckndUsageResourcePlanItem resourcePlanType(@Nullable final String resourcePlanType) {
    this.resourcePlanType = resourcePlanType;
    return this;
  }

  /**
   * Get resourcePlanType
   *
   * @return resourcePlanType The resourcePlanType of this {@link BckndUsageResourcePlanItem}
   *     instance.
   */
  @Nonnull
  public String getResourcePlanType() {
    return resourcePlanType;
  }

  /**
   * Set the resourcePlanType of this {@link BckndUsageResourcePlanItem} instance.
   *
   * @param resourcePlanType The resourcePlanType of this {@link BckndUsageResourcePlanItem}
   */
  public void setResourcePlanType(@Nullable final String resourcePlanType) {
    this.resourcePlanType = resourcePlanType;
  }

  /**
   * Set the configuredMaxReplicas of this {@link BckndUsageResourcePlanItem} instance and return
   * the same instance.
   *
   * @param configuredMaxReplicas The configuredMaxReplicas of this {@link
   *     BckndUsageResourcePlanItem}
   * @return The same instance of this {@link BckndUsageResourcePlanItem} class
   */
  @Nonnull
  public BckndUsageResourcePlanItem configuredMaxReplicas(
      @Nullable final Integer configuredMaxReplicas) {
    this.configuredMaxReplicas = configuredMaxReplicas;
    return this;
  }

  /**
   * Get configuredMaxReplicas
   *
   * @return configuredMaxReplicas The configuredMaxReplicas of this {@link
   *     BckndUsageResourcePlanItem} instance.
   */
  @Nonnull
  public Integer getConfiguredMaxReplicas() {
    return configuredMaxReplicas;
  }

  /**
   * Set the configuredMaxReplicas of this {@link BckndUsageResourcePlanItem} instance.
   *
   * @param configuredMaxReplicas The configuredMaxReplicas of this {@link
   *     BckndUsageResourcePlanItem}
   */
  public void setConfiguredMaxReplicas(@Nullable final Integer configuredMaxReplicas) {
    this.configuredMaxReplicas = configuredMaxReplicas;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndUsageResourcePlanItem}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndUsageResourcePlanItem}
   * instance.
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
      throw new NoSuchElementException(
          "BckndUsageResourcePlanItem has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link BckndUsageResourcePlanItem} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (resourcePlanType != null) declaredFields.put("resourcePlanType", resourcePlanType);
    if (configuredMaxReplicas != null)
      declaredFields.put("configuredMaxReplicas", configuredMaxReplicas);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link BckndUsageResourcePlanItem} instance. If the map
   * previously contained a mapping for the key, the old value is replaced by the specified value.
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
    final BckndUsageResourcePlanItem bckndUsageResourcePlanItem = (BckndUsageResourcePlanItem) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndUsageResourcePlanItem.cloudSdkCustomFields)
        && Objects.equals(this.id, bckndUsageResourcePlanItem.id)
        && Objects.equals(this.resourcePlanType, bckndUsageResourcePlanItem.resourcePlanType)
        && Objects.equals(
            this.configuredMaxReplicas, bckndUsageResourcePlanItem.configuredMaxReplicas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, resourcePlanType, configuredMaxReplicas, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndUsageResourcePlanItem {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resourcePlanType: ").append(toIndentedString(resourcePlanType)).append("\n");
    sb.append("    configuredMaxReplicas: ")
        .append(toIndentedString(configuredMaxReplicas))
        .append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * BckndUsageResourcePlanItem} instance with all required arguments.
   */
  public static Builder create() {
    return (id) -> new BckndUsageResourcePlanItem().id(id);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link BckndUsageResourcePlanItem} instance.
     *
     * @param id The id of this {@link BckndUsageResourcePlanItem}
     * @return The BckndUsageResourcePlanItem instance.
     */
    BckndUsageResourcePlanItem id(@Nonnull final String id);
  }
}
