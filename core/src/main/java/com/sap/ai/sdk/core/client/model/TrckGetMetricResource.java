

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
import com.sap.ai.sdk.core.client.model.TrckCustomInfoObject;
import com.sap.ai.sdk.core.client.model.TrckExecutionId;
import com.sap.ai.sdk.core.client.model.TrckGetMetric;
import com.sap.ai.sdk.core.client.model.TrckTag;
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
 * Collection of various metrics/tags/labels associated against some execution/deployment
 */
// CHECKSTYLE:OFF
public class TrckGetMetricResource 
// CHECKSTYLE:ON
{
  @JsonProperty("executionId")
  private TrckExecutionId executionId;

  @JsonProperty("metrics")
  private List<TrckGetMetric> metrics = new ArrayList<>();

  @JsonProperty("tags")
  private List<TrckTag> tags = new ArrayList<>();

  @JsonProperty("customInfo")
  private List<TrckCustomInfoObject> customInfo = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected TrckGetMetricResource() {  }

  /**
   * Set the executionId of this {@link TrckGetMetricResource} instance and return the same instance.
   *
   * @param executionId  The executionId of this {@link TrckGetMetricResource}
   * @return The same instance of this {@link TrckGetMetricResource} class
   */
  @Nonnull public TrckGetMetricResource executionId( @Nonnull final TrckExecutionId executionId) {
    this.executionId = executionId;
    return this;
  }

  /**
   * Get executionId
   * @return executionId  The executionId of this {@link TrckGetMetricResource} instance.
   */
  @Nonnull public TrckExecutionId getExecutionId() {
    return executionId;
  }

  /**
   * Set the executionId of this {@link TrckGetMetricResource} instance.
   *
   * @param executionId  The executionId of this {@link TrckGetMetricResource}
   */
  public void setExecutionId( @Nonnull final TrckExecutionId executionId) {
    this.executionId = executionId;
  }

  /**
   * Set the metrics of this {@link TrckGetMetricResource} instance and return the same instance.
   *
   * @param metrics  Array of Metric items
   * @return The same instance of this {@link TrckGetMetricResource} class
   */
  @Nonnull public TrckGetMetricResource metrics( @Nullable final List<TrckGetMetric> metrics) {
    this.metrics = metrics;
    return this;
  }
  /**
   * Add one metrics instance to this {@link TrckGetMetricResource}.
   * @param metricsItem The metrics that should be added
   * @return The same instance of type {@link TrckGetMetricResource}
   */
  @Nonnull public TrckGetMetricResource addMetricsItem( @Nonnull final TrckGetMetric metricsItem) {
    if (this.metrics == null) {
      this.metrics = new ArrayList<>();
    }
    this.metrics.add(metricsItem);
    return this;
  }

  /**
   * Array of Metric items
   * @return metrics  The metrics of this {@link TrckGetMetricResource} instance.
   */
  @Nonnull public List<TrckGetMetric> getMetrics() {
    return metrics;
  }

  /**
   * Set the metrics of this {@link TrckGetMetricResource} instance.
   *
   * @param metrics  Array of Metric items
   */
  public void setMetrics( @Nullable final List<TrckGetMetric> metrics) {
    this.metrics = metrics;
  }

  /**
   * Set the tags of this {@link TrckGetMetricResource} instance and return the same instance.
   *
   * @param tags  a list of name-value object pairs associated with the execution/deployment. Tags are queryable.
   * @return The same instance of this {@link TrckGetMetricResource} class
   */
  @Nonnull public TrckGetMetricResource tags( @Nullable final List<TrckTag> tags) {
    this.tags = tags;
    return this;
  }
  /**
   * Add one tags instance to this {@link TrckGetMetricResource}.
   * @param tagsItem The tags that should be added
   * @return The same instance of type {@link TrckGetMetricResource}
   */
  @Nonnull public TrckGetMetricResource addTagsItem( @Nonnull final TrckTag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * a list of name-value object pairs associated with the execution/deployment. Tags are queryable.
   * @return tags  The tags of this {@link TrckGetMetricResource} instance.
   */
  @Nonnull public List<TrckTag> getTags() {
    return tags;
  }

  /**
   * Set the tags of this {@link TrckGetMetricResource} instance.
   *
   * @param tags  a list of name-value object pairs associated with the execution/deployment. Tags are queryable.
   */
  public void setTags( @Nullable final List<TrckTag> tags) {
    this.tags = tags;
  }

  /**
   * Set the customInfo of this {@link TrckGetMetricResource} instance and return the same instance.
   *
   * @param customInfo  
   * @return The same instance of this {@link TrckGetMetricResource} class
   */
  @Nonnull public TrckGetMetricResource customInfo( @Nullable final List<TrckCustomInfoObject> customInfo) {
    this.customInfo = customInfo;
    return this;
  }
  /**
   * Add one customInfo instance to this {@link TrckGetMetricResource}.
   * @param customInfoItem The customInfo that should be added
   * @return The same instance of type {@link TrckGetMetricResource}
   */
  @Nonnull public TrckGetMetricResource addCustomInfoItem( @Nonnull final TrckCustomInfoObject customInfoItem) {
    if (this.customInfo == null) {
      this.customInfo = new ArrayList<>();
    }
    this.customInfo.add(customInfoItem);
    return this;
  }

  /**
   * 
   * @return customInfo  The customInfo of this {@link TrckGetMetricResource} instance.
   */
  @Nonnull public List<TrckCustomInfoObject> getCustomInfo() {
    return customInfo;
  }

  /**
   * Set the customInfo of this {@link TrckGetMetricResource} instance.
   *
   * @param customInfo  
   */
  public void setCustomInfo( @Nullable final List<TrckCustomInfoObject> customInfo) {
    this.customInfo = customInfo;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckGetMetricResource}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckGetMetricResource} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("TrckGetMetricResource has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckGetMetricResource} instance. If the map previously contained a mapping
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
    final TrckGetMetricResource trckGetMetricResource = (TrckGetMetricResource) o;
    return Objects.equals(this.cloudSdkCustomFields, trckGetMetricResource.cloudSdkCustomFields) &&
        Objects.equals(this.executionId, trckGetMetricResource.executionId) &&
        Objects.equals(this.metrics, trckGetMetricResource.metrics) &&
        Objects.equals(this.tags, trckGetMetricResource.tags) &&
        Objects.equals(this.customInfo, trckGetMetricResource.customInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionId, metrics, tags, customInfo, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckGetMetricResource {\n");
    sb.append("    executionId: ").append(toIndentedString(executionId)).append("\n");
    sb.append("    metrics: ").append(toIndentedString(metrics)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    customInfo: ").append(toIndentedString(customInfo)).append("\n");
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

    /**
    * Create a type-safe, fluent-api builder object to construct a new {@link TrckGetMetricResource} instance with all required arguments.
    */
    public static Builder create() {
        return (executionId) -> new TrckGetMetricResource().executionId(executionId);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the executionId of this {@link TrckGetMetricResource} instance.
        *
        * @param executionId  The executionId of this {@link TrckGetMetricResource}
        * @return The TrckGetMetricResource instance.
        */
        TrckGetMetricResource executionId( @Nonnull final TrckExecutionId executionId);
    }

}

