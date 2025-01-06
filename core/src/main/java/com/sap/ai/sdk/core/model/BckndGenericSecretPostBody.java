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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndGenericSecretPostBody */
// CHECKSTYLE:OFF
public class BckndGenericSecretPostBody
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("data")
  private Map<String, String> data = new HashMap<>();

  @JsonProperty("labels")
  private List<BckndGenericSecretLabel> labels = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndGenericSecretPostBody. */
  protected BckndGenericSecretPostBody() {}

  /**
   * Set the name of this {@link BckndGenericSecretPostBody} instance and return the same instance.
   *
   * @param name The name of the secret
   * @return The same instance of this {@link BckndGenericSecretPostBody} class
   */
  @Nonnull
  public BckndGenericSecretPostBody name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the secret
   *
   * @return name The name of this {@link BckndGenericSecretPostBody} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndGenericSecretPostBody} instance.
   *
   * @param name The name of the secret
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the data of this {@link BckndGenericSecretPostBody} instance and return the same instance.
   *
   * @param data Base64 encoded secret data
   * @return The same instance of this {@link BckndGenericSecretPostBody} class
   */
  @Nonnull
  public BckndGenericSecretPostBody data(@Nonnull final Map<String, String> data) {
    this.data = data;
    return this;
  }

  /**
   * Put one data instance to this {@link BckndGenericSecretPostBody} instance.
   *
   * @param key The String key of this data instance
   * @param dataItem The data that should be added under the given key
   * @return The same instance of type {@link BckndGenericSecretPostBody}
   */
  @Nonnull
  public BckndGenericSecretPostBody putdataItem(
      @Nonnull final String key, @Nonnull final String dataItem) {
    this.data.put(key, dataItem);
    return this;
  }

  /**
   * Base64 encoded secret data
   *
   * @return data The data of this {@link BckndGenericSecretPostBody} instance.
   */
  @Nonnull
  public Map<String, String> getData() {
    return data;
  }

  /**
   * Set the data of this {@link BckndGenericSecretPostBody} instance.
   *
   * @param data Base64 encoded secret data
   */
  public void setData(@Nonnull final Map<String, String> data) {
    this.data = data;
  }

  /**
   * Set the labels of this {@link BckndGenericSecretPostBody} instance and return the same
   * instance.
   *
   * @param labels Arbitrary labels as meta information
   * @return The same instance of this {@link BckndGenericSecretPostBody} class
   */
  @Nonnull
  public BckndGenericSecretPostBody labels(@Nullable final List<BckndGenericSecretLabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link BckndGenericSecretPostBody}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link BckndGenericSecretPostBody}
   */
  @Nonnull
  public BckndGenericSecretPostBody addLabelsItem(
      @Nonnull final BckndGenericSecretLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Arbitrary labels as meta information
   *
   * @return labels The labels of this {@link BckndGenericSecretPostBody} instance.
   */
  @Nonnull
  public List<BckndGenericSecretLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link BckndGenericSecretPostBody} instance.
   *
   * @param labels Arbitrary labels as meta information
   */
  public void setLabels(@Nullable final List<BckndGenericSecretLabel> labels) {
    this.labels = labels;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndGenericSecretPostBody}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndGenericSecretPostBody}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndGenericSecretPostBody has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndGenericSecretPostBody} instance. If the map
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
    final BckndGenericSecretPostBody bckndGenericSecretPostBody = (BckndGenericSecretPostBody) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndGenericSecretPostBody.cloudSdkCustomFields)
        && Objects.equals(this.name, bckndGenericSecretPostBody.name)
        && Objects.equals(this.data, bckndGenericSecretPostBody.data)
        && Objects.equals(this.labels, bckndGenericSecretPostBody.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, data, labels, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndGenericSecretPostBody {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * BckndGenericSecretPostBody} instance with all required arguments.
   */
  public static Builder create() {
    return (name) -> (data) -> new BckndGenericSecretPostBody().name(name).data(data);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link BckndGenericSecretPostBody} instance.
     *
     * @param name The name of the secret
     * @return The BckndGenericSecretPostBody builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the data of this {@link BckndGenericSecretPostBody} instance.
     *
     * @param data Base64 encoded secret data
     * @return The BckndGenericSecretPostBody instance.
     */
    BckndGenericSecretPostBody data(@Nonnull final Map<String, String> data);
  }
}
