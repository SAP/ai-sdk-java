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
import com.google.common.annotations.Beta;
import java.math.BigDecimal;
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
 * Key-value metrics, where the value is numeric. Metric can also have optional step and label
 * fields.
 */
@Beta // CHECKSTYLE:OFF
public class TrckMetric
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

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TrckMetric. */
  protected TrckMetric() {}

  /**
   * Set the name of this {@link TrckMetric} instance and return the same instance.
   *
   * @param name Name of the metric
   * @return The same instance of this {@link TrckMetric} class
   */
  @Nonnull
  public TrckMetric name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the metric
   *
   * @return name The name of this {@link TrckMetric} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link TrckMetric} instance.
   *
   * @param name Name of the metric
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the value of this {@link TrckMetric} instance and return the same instance.
   *
   * @param value Numeric Value of the metric
   * @return The same instance of this {@link TrckMetric} class
   */
  @Nonnull
  public TrckMetric value(@Nonnull final BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * Numeric Value of the metric
   *
   * @return value The value of this {@link TrckMetric} instance.
   */
  @Nonnull
  public BigDecimal getValue() {
    return value;
  }

  /**
   * Set the value of this {@link TrckMetric} instance.
   *
   * @param value Numeric Value of the metric
   */
  public void setValue(@Nonnull final BigDecimal value) {
    this.value = value;
  }

  /**
   * Set the timestamp of this {@link TrckMetric} instance and return the same instance.
   *
   * @param timestamp Time when the metric was created or logged in RFC3339 format
   * @return The same instance of this {@link TrckMetric} class
   */
  @Nonnull
  public TrckMetric timestamp(@Nullable final OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Time when the metric was created or logged in RFC3339 format
   *
   * @return timestamp The timestamp of this {@link TrckMetric} instance.
   */
  @Nonnull
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Set the timestamp of this {@link TrckMetric} instance.
   *
   * @param timestamp Time when the metric was created or logged in RFC3339 format
   */
  public void setTimestamp(@Nullable final OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Set the step of this {@link TrckMetric} instance and return the same instance.
   *
   * @param step step is an optional integer that represents any measurement of training progress
   *     (number of training iterations, number of epochs, and so on) for the metric Minimum: 0
   * @return The same instance of this {@link TrckMetric} class
   */
  @Nonnull
  public TrckMetric step(@Nullable final Integer step) {
    this.step = step;
    return this;
  }

  /**
   * step is an optional integer that represents any measurement of training progress (number of
   * training iterations, number of epochs, and so on) for the metric minimum: 0
   *
   * @return step The step of this {@link TrckMetric} instance.
   */
  @Nonnull
  public Integer getStep() {
    return step;
  }

  /**
   * Set the step of this {@link TrckMetric} instance.
   *
   * @param step step is an optional integer that represents any measurement of training progress
   *     (number of training iterations, number of epochs, and so on) for the metric Minimum: 0
   */
  public void setStep(@Nullable final Integer step) {
    this.step = step;
  }

  /**
   * Set the labels of this {@link TrckMetric} instance and return the same instance.
   *
   * @param labels a list of name-value object pairs associated with some metric.
   * @return The same instance of this {@link TrckMetric} class
   */
  @Nonnull
  public TrckMetric labels(@Nullable final List<TrckLabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link TrckMetric}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link TrckMetric}
   */
  @Nonnull
  public TrckMetric addLabelsItem(@Nonnull final TrckLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * a list of name-value object pairs associated with some metric.
   *
   * @return labels The labels of this {@link TrckMetric} instance.
   */
  @Nonnull
  public List<TrckLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link TrckMetric} instance.
   *
   * @param labels a list of name-value object pairs associated with some metric.
   */
  public void setLabels(@Nullable final List<TrckLabel> labels) {
    this.labels = labels;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckMetric}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckMetric} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TrckMetric has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckMetric} instance. If the map previously
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
    final TrckMetric trckMetric = (TrckMetric) o;
    return Objects.equals(this.cloudSdkCustomFields, trckMetric.cloudSdkCustomFields)
        && Objects.equals(this.name, trckMetric.name)
        && Objects.equals(this.value, trckMetric.value)
        && Objects.equals(this.timestamp, trckMetric.timestamp)
        && Objects.equals(this.step, trckMetric.step)
        && Objects.equals(this.labels, trckMetric.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, timestamp, step, labels, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckMetric {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    step: ").append(toIndentedString(step)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TrckMetric} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (name) -> (value) -> new TrckMetric().name(name).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link TrckMetric} instance.
     *
     * @param name Name of the metric
     * @return The TrckMetric builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link TrckMetric} instance.
     *
     * @param value Numeric Value of the metric
     * @return The TrckMetric instance.
     */
    TrckMetric value(@Nonnull final BigDecimal value);
  }
}
