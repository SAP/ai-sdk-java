

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
import com.sap.ai.sdk.core.client.model.TrckLabel;
import java.math.BigDecimal;
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
 * Key-value metrics, where the value is numeric. Metric can also have optional step and label fields.
 */
// CHECKSTYLE:OFF
public class TrckGetMetric 
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("value")
  private BigDecimal value;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp;

  @JsonProperty("step")
  private Integer step;

  @JsonProperty("labels")
  private List<TrckLabel> labels = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected TrckGetMetric() {  }

   /**
    * Set the name of this {@link TrckGetMetric} instance and return the same instance.
    *
    * @param name  Name of the metric
    * @return The same instance of this {@link TrckGetMetric} class
    */
   @Nonnull public TrckGetMetric name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

   /**
    * Name of the metric
    * @return name  The name of this {@link TrckGetMetric} instance.
    */
  @Nonnull public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link TrckGetMetric} instance.
   *
   * @param name  Name of the metric
   */
  public void setName( @Nonnull final String name) {
    this.name = name;
  }

   /**
    * Set the value of this {@link TrckGetMetric} instance and return the same instance.
    *
    * @param value  Numeric Value of the metric
    * @return The same instance of this {@link TrckGetMetric} class
    */
   @Nonnull public TrckGetMetric value(@Nonnull final BigDecimal value) {
    this.value = value;
    return this;
  }

   /**
    * Numeric Value of the metric
    * @return value  The value of this {@link TrckGetMetric} instance.
    */
  @Nonnull public BigDecimal getValue() {
    return value;
  }

  /**
   * Set the value of this {@link TrckGetMetric} instance.
   *
   * @param value  Numeric Value of the metric
   */
  public void setValue( @Nonnull final BigDecimal value) {
    this.value = value;
  }

   /**
    * Set the timestamp of this {@link TrckGetMetric} instance and return the same instance.
    *
    * @param timestamp  Time when the metric was created or logged in RFC3339 format
    * @return The same instance of this {@link TrckGetMetric} class
    */
   @Nonnull public TrckGetMetric timestamp(@Nonnull final OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
    * Time when the metric was created or logged in RFC3339 format
    * @return timestamp  The timestamp of this {@link TrckGetMetric} instance.
    */
  @Nonnull public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Set the timestamp of this {@link TrckGetMetric} instance.
   *
   * @param timestamp  Time when the metric was created or logged in RFC3339 format
   */
  public void setTimestamp( @Nonnull final OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

   /**
    * Set the step of this {@link TrckGetMetric} instance and return the same instance.
    *
    * @param step  step is an optional integer that represents any measurement of training progress (number of training iterations, number of epochs, and so on) for the metric
    * Minimum: 0
    * @return The same instance of this {@link TrckGetMetric} class
    */
   @Nonnull public TrckGetMetric step(@Nonnull final Integer step) {
    this.step = step;
    return this;
  }

   /**
    * step is an optional integer that represents any measurement of training progress (number of training iterations, number of epochs, and so on) for the metric
    * minimum: 0
    * @return step  The step of this {@link TrckGetMetric} instance.
    */
  @Nonnull public Integer getStep() {
    return step;
  }

  /**
   * Set the step of this {@link TrckGetMetric} instance.
   *
   * @param step  step is an optional integer that represents any measurement of training progress (number of training iterations, number of epochs, and so on) for the metric
   * Minimum: 0
   */
  public void setStep( @Nonnull final Integer step) {
    this.step = step;
  }

   /**
    * Set the labels of this {@link TrckGetMetric} instance and return the same instance.
    *
    * @param labels  a list of name-value object pairs associated with some metric.
    * @return The same instance of this {@link TrckGetMetric} class
    */
   @Nonnull public TrckGetMetric labels(@Nonnull final List<TrckLabel> labels) {
    this.labels = labels;
    return this;
  }
  /**
   * Add one labels instance to this {@link TrckGetMetric}.
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link TrckGetMetric}
   */
  @Nonnull public TrckGetMetric addLabelsItem( @Nonnull final TrckLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

   /**
    * a list of name-value object pairs associated with some metric.
    * @return labels  The labels of this {@link TrckGetMetric} instance.
    */
  @Nonnull public List<TrckLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link TrckGetMetric} instance.
   *
   * @param labels  a list of name-value object pairs associated with some metric.
   */
  public void setLabels( @Nonnull final List<TrckLabel> labels) {
    this.labels = labels;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckGetMetric}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckGetMetric} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("TrckGetMetric has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckGetMetric} instance. If the map previously contained a mapping
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
    final TrckGetMetric trckGetMetric = (TrckGetMetric) o;
    return Objects.equals(this.cloudSdkCustomFields, trckGetMetric.cloudSdkCustomFields) &&
        Objects.equals(this.name, trckGetMetric.name) &&
        Objects.equals(this.value, trckGetMetric.value) &&
        Objects.equals(this.timestamp, trckGetMetric.timestamp) &&
        Objects.equals(this.step, trckGetMetric.step) &&
        Objects.equals(this.labels, trckGetMetric.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, timestamp, step, labels, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckGetMetric {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    step: ").append(toIndentedString(step)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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
    * Create a type-safe, fluent-api builder object to construct a new {@link TrckGetMetric} instance with all required arguments.
    */
    public static Builder create() {
        return (name) -> (value) -> (timestamp) -> new TrckGetMetric().name(name).value(value).timestamp(timestamp);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the name of this {@link TrckGetMetric} instance.
        *
        * @param name  Name of the metric
        * @return The TrckGetMetric builder.
        */
        Builder1 name( @Nonnull final String name);
    }
    /**
    * Builder helper class.
    */
    public interface Builder1 {
        /**
        * Set the value of this {@link TrckGetMetric} instance.
        *
        * @param value  Numeric Value of the metric
        * @return The TrckGetMetric builder.
        */
        Builder2 value( @Nonnull final BigDecimal value);
    }
    /**
    * Builder helper class.
    */
    public interface Builder2 {
        /**
        * Set the timestamp of this {@link TrckGetMetric} instance.
        *
        * @param timestamp  Time when the metric was created or logged in RFC3339 format
        * @return The TrckGetMetric instance.
        */
        TrckGetMetric timestamp( @Nonnull final OffsetDateTime timestamp);
    }

}

