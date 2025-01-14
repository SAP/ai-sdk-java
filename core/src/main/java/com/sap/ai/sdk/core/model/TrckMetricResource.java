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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Collection of various metrics/tags/labels associated against some execution/deployment */
@Beta // CHECKSTYLE:OFF
public class TrckMetricResource
// CHECKSTYLE:ON
{
  @JsonProperty("executionId")
  private TrckExecutionId executionId;

  @JsonProperty("metrics")
  private List<TrckMetric> metrics = new ArrayList<>();

  @JsonProperty("tags")
  private List<TrckTag> tags = new ArrayList<>();

  @JsonProperty("customInfo")
  private List<TrckCustomInfoObject> customInfo = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TrckMetricResource. */
  protected TrckMetricResource() {}

  /**
   * Set the executionId of this {@link TrckMetricResource} instance and return the same instance.
   *
   * @param executionId The executionId of this {@link TrckMetricResource}
   * @return The same instance of this {@link TrckMetricResource} class
   */
  @Nonnull
  public TrckMetricResource executionId(@Nonnull final TrckExecutionId executionId) {
    this.executionId = executionId;
    return this;
  }

  /**
   * Get executionId
   *
   * @return executionId The executionId of this {@link TrckMetricResource} instance.
   */
  @Nonnull
  public TrckExecutionId getExecutionId() {
    return executionId;
  }

  /**
   * Set the executionId of this {@link TrckMetricResource} instance.
   *
   * @param executionId The executionId of this {@link TrckMetricResource}
   */
  public void setExecutionId(@Nonnull final TrckExecutionId executionId) {
    this.executionId = executionId;
  }

  /**
   * Set the metrics of this {@link TrckMetricResource} instance and return the same instance.
   *
   * @param metrics Array of Metric items
   * @return The same instance of this {@link TrckMetricResource} class
   */
  @Nonnull
  public TrckMetricResource metrics(@Nullable final List<TrckMetric> metrics) {
    this.metrics = metrics;
    return this;
  }

  /**
   * Add one metrics instance to this {@link TrckMetricResource}.
   *
   * @param metricsItem The metrics that should be added
   * @return The same instance of type {@link TrckMetricResource}
   */
  @Nonnull
  public TrckMetricResource addMetricsItem(@Nonnull final TrckMetric metricsItem) {
    if (this.metrics == null) {
      this.metrics = new ArrayList<>();
    }
    this.metrics.add(metricsItem);
    return this;
  }

  /**
   * Array of Metric items
   *
   * @return metrics The metrics of this {@link TrckMetricResource} instance.
   */
  @Nonnull
  public List<TrckMetric> getMetrics() {
    return metrics;
  }

  /**
   * Set the metrics of this {@link TrckMetricResource} instance.
   *
   * @param metrics Array of Metric items
   */
  public void setMetrics(@Nullable final List<TrckMetric> metrics) {
    this.metrics = metrics;
  }

  /**
   * Set the tags of this {@link TrckMetricResource} instance and return the same instance.
   *
   * @param tags a list of name-value object pairs associated with the execution/deployment. Tags
   *     are queryable.
   * @return The same instance of this {@link TrckMetricResource} class
   */
  @Nonnull
  public TrckMetricResource tags(@Nullable final List<TrckTag> tags) {
    this.tags = tags;
    return this;
  }

  /**
   * Add one tags instance to this {@link TrckMetricResource}.
   *
   * @param tagsItem The tags that should be added
   * @return The same instance of type {@link TrckMetricResource}
   */
  @Nonnull
  public TrckMetricResource addTagsItem(@Nonnull final TrckTag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * a list of name-value object pairs associated with the execution/deployment. Tags are queryable.
   *
   * @return tags The tags of this {@link TrckMetricResource} instance.
   */
  @Nonnull
  public List<TrckTag> getTags() {
    return tags;
  }

  /**
   * Set the tags of this {@link TrckMetricResource} instance.
   *
   * @param tags a list of name-value object pairs associated with the execution/deployment. Tags
   *     are queryable.
   */
  public void setTags(@Nullable final List<TrckTag> tags) {
    this.tags = tags;
  }

  /**
   * Set the customInfo of this {@link TrckMetricResource} instance and return the same instance.
   *
   * @param customInfo
   * @return The same instance of this {@link TrckMetricResource} class
   */
  @Nonnull
  public TrckMetricResource customInfo(@Nullable final List<TrckCustomInfoObject> customInfo) {
    this.customInfo = customInfo;
    return this;
  }

  /**
   * Add one customInfo instance to this {@link TrckMetricResource}.
   *
   * @param customInfoItem The customInfo that should be added
   * @return The same instance of type {@link TrckMetricResource}
   */
  @Nonnull
  public TrckMetricResource addCustomInfoItem(@Nonnull final TrckCustomInfoObject customInfoItem) {
    if (this.customInfo == null) {
      this.customInfo = new ArrayList<>();
    }
    this.customInfo.add(customInfoItem);
    return this;
  }

  /**
   * @return customInfo The customInfo of this {@link TrckMetricResource} instance.
   */
  @Nonnull
  public List<TrckCustomInfoObject> getCustomInfo() {
    return customInfo;
  }

  /**
   * Set the customInfo of this {@link TrckMetricResource} instance.
   *
   * @param customInfo
   */
  public void setCustomInfo(@Nullable final List<TrckCustomInfoObject> customInfo) {
    this.customInfo = customInfo;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckMetricResource}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckMetricResource} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TrckMetricResource has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckMetricResource} instance. If the map
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
    final TrckMetricResource trckMetricResource = (TrckMetricResource) o;
    return Objects.equals(this.cloudSdkCustomFields, trckMetricResource.cloudSdkCustomFields)
        && Objects.equals(this.executionId, trckMetricResource.executionId)
        && Objects.equals(this.metrics, trckMetricResource.metrics)
        && Objects.equals(this.tags, trckMetricResource.tags)
        && Objects.equals(this.customInfo, trckMetricResource.customInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionId, metrics, tags, customInfo, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckMetricResource {\n");
    sb.append("    executionId: ").append(toIndentedString(executionId)).append("\n");
    sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    customInfo: ").append(toIndentedString(customInfo)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TrckMetricResource}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (executionId) -> new TrckMetricResource().executionId(executionId);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the executionId of this {@link TrckMetricResource} instance.
     *
     * @param executionId The executionId of this {@link TrckMetricResource}
     * @return The TrckMetricResource instance.
     */
    TrckMetricResource executionId(@Nonnull final TrckExecutionId executionId);
  }
}
