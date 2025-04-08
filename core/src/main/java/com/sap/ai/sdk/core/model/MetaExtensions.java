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

/** MetaExtensions */
// CHECKSTYLE:OFF
public class MetaExtensions
// CHECKSTYLE:ON
{
  @JsonProperty("analytics")
  private MetaExtensionsAnalytics analytics;

  @JsonProperty("resourceGroups")
  private MetaExtensionsAnalytics resourceGroups;

  @JsonProperty("dataset")
  private MetaExtensionsDataset dataset;

  @JsonProperty("metrics")
  private MetaExtensionsMetrics metrics;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MetaExtensions. */
  protected MetaExtensions() {}

  /**
   * Set the analytics of this {@link MetaExtensions} instance and return the same instance.
   *
   * @param analytics The analytics of this {@link MetaExtensions}
   * @return The same instance of this {@link MetaExtensions} class
   */
  @Nonnull
  public MetaExtensions analytics(@Nullable final MetaExtensionsAnalytics analytics) {
    this.analytics = analytics;
    return this;
  }

  /**
   * Get analytics
   *
   * @return analytics The analytics of this {@link MetaExtensions} instance.
   */
  @Nonnull
  public MetaExtensionsAnalytics getAnalytics() {
    return analytics;
  }

  /**
   * Set the analytics of this {@link MetaExtensions} instance.
   *
   * @param analytics The analytics of this {@link MetaExtensions}
   */
  public void setAnalytics(@Nullable final MetaExtensionsAnalytics analytics) {
    this.analytics = analytics;
  }

  /**
   * Set the resourceGroups of this {@link MetaExtensions} instance and return the same instance.
   *
   * @param resourceGroups The resourceGroups of this {@link MetaExtensions}
   * @return The same instance of this {@link MetaExtensions} class
   */
  @Nonnull
  public MetaExtensions resourceGroups(@Nullable final MetaExtensionsAnalytics resourceGroups) {
    this.resourceGroups = resourceGroups;
    return this;
  }

  /**
   * Get resourceGroups
   *
   * @return resourceGroups The resourceGroups of this {@link MetaExtensions} instance.
   */
  @Nonnull
  public MetaExtensionsAnalytics getResourceGroups() {
    return resourceGroups;
  }

  /**
   * Set the resourceGroups of this {@link MetaExtensions} instance.
   *
   * @param resourceGroups The resourceGroups of this {@link MetaExtensions}
   */
  public void setResourceGroups(@Nullable final MetaExtensionsAnalytics resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  /**
   * Set the dataset of this {@link MetaExtensions} instance and return the same instance.
   *
   * @param dataset The dataset of this {@link MetaExtensions}
   * @return The same instance of this {@link MetaExtensions} class
   */
  @Nonnull
  public MetaExtensions dataset(@Nullable final MetaExtensionsDataset dataset) {
    this.dataset = dataset;
    return this;
  }

  /**
   * Get dataset
   *
   * @return dataset The dataset of this {@link MetaExtensions} instance.
   */
  @Nonnull
  public MetaExtensionsDataset getDataset() {
    return dataset;
  }

  /**
   * Set the dataset of this {@link MetaExtensions} instance.
   *
   * @param dataset The dataset of this {@link MetaExtensions}
   */
  public void setDataset(@Nullable final MetaExtensionsDataset dataset) {
    this.dataset = dataset;
  }

  /**
   * Set the metrics of this {@link MetaExtensions} instance and return the same instance.
   *
   * @param metrics The metrics of this {@link MetaExtensions}
   * @return The same instance of this {@link MetaExtensions} class
   */
  @Nonnull
  public MetaExtensions metrics(@Nullable final MetaExtensionsMetrics metrics) {
    this.metrics = metrics;
    return this;
  }

  /**
   * Get metrics
   *
   * @return metrics The metrics of this {@link MetaExtensions} instance.
   */
  @Nonnull
  public MetaExtensionsMetrics getMetrics() {
    return metrics;
  }

  /**
   * Set the metrics of this {@link MetaExtensions} instance.
   *
   * @param metrics The metrics of this {@link MetaExtensions}
   */
  public void setMetrics(@Nullable final MetaExtensionsMetrics metrics) {
    this.metrics = metrics;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MetaExtensions}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaExtensions} instance.
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
      throw new NoSuchElementException("MetaExtensions has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link MetaExtensions} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (analytics != null) declaredFields.put("analytics", analytics);
    if (resourceGroups != null) declaredFields.put("resourceGroups", resourceGroups);
    if (dataset != null) declaredFields.put("dataset", dataset);
    if (metrics != null) declaredFields.put("metrics", metrics);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link MetaExtensions} instance. If the map previously
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
    final MetaExtensions metaExtensions = (MetaExtensions) o;
    return Objects.equals(this.cloudSdkCustomFields, metaExtensions.cloudSdkCustomFields)
        && Objects.equals(this.analytics, metaExtensions.analytics)
        && Objects.equals(this.resourceGroups, metaExtensions.resourceGroups)
        && Objects.equals(this.dataset, metaExtensions.dataset)
        && Objects.equals(this.metrics, metaExtensions.metrics);
  }

  @Override
  public int hashCode() {
    return Objects.hash(analytics, resourceGroups, dataset, metrics, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaExtensions {\n");
    sb.append("    analytics: ").append(toIndentedString(analytics)).append("\n");
    sb.append("    resourceGroups: ").append(toIndentedString(resourceGroups)).append("\n");
    sb.append("    dataset: ").append(toIndentedString(dataset)).append("\n");
    sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
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

  /** Create a new {@link MetaExtensions} instance. No arguments are required. */
  public static MetaExtensions create() {
    return new MetaExtensions();
  }
}
