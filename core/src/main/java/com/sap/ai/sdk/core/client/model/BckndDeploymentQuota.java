/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.34.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

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

/** BckndDeploymentQuota */
// CHECKSTYLE:OFF
public class BckndDeploymentQuota
// CHECKSTYLE:ON
{
  @JsonProperty("maxCount")
  private Integer maxCount;

  @JsonProperty("maxReplicaPerDeployment")
  private Integer maxReplicaPerDeployment;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected BckndDeploymentQuota() {}

  /**
   * Set the maxCount of this {@link BckndDeploymentQuota} instance and return the same instance.
   *
   * @param maxCount The value can be 0(disabled) or a positive integer defining the maximum allowed
   *     number
   * @return The same instance of this {@link BckndDeploymentQuota} class
   */
  @Nonnull
  public BckndDeploymentQuota maxCount(@Nullable final Integer maxCount) {
    this.maxCount = maxCount;
    return this;
  }

  /**
   * The value can be 0(disabled) or a positive integer defining the maximum allowed number
   *
   * @return maxCount The maxCount of this {@link BckndDeploymentQuota} instance.
   */
  @Nonnull
  public Integer getMaxCount() {
    return maxCount;
  }

  /**
   * Set the maxCount of this {@link BckndDeploymentQuota} instance.
   *
   * @param maxCount The value can be 0(disabled) or a positive integer defining the maximum allowed
   *     number
   */
  public void setMaxCount(@Nullable final Integer maxCount) {
    this.maxCount = maxCount;
  }

  /**
   * Set the maxReplicaPerDeployment of this {@link BckndDeploymentQuota} instance and return the
   * same instance.
   *
   * @param maxReplicaPerDeployment The maxReplicaPerDeployment of this {@link BckndDeploymentQuota}
   * @return The same instance of this {@link BckndDeploymentQuota} class
   */
  @Nonnull
  public BckndDeploymentQuota maxReplicaPerDeployment(
      @Nullable final Integer maxReplicaPerDeployment) {
    this.maxReplicaPerDeployment = maxReplicaPerDeployment;
    return this;
  }

  /**
   * Get maxReplicaPerDeployment
   *
   * @return maxReplicaPerDeployment The maxReplicaPerDeployment of this {@link
   *     BckndDeploymentQuota} instance.
   */
  @Nonnull
  public Integer getMaxReplicaPerDeployment() {
    return maxReplicaPerDeployment;
  }

  /**
   * Set the maxReplicaPerDeployment of this {@link BckndDeploymentQuota} instance.
   *
   * @param maxReplicaPerDeployment The maxReplicaPerDeployment of this {@link BckndDeploymentQuota}
   */
  public void setMaxReplicaPerDeployment(@Nullable final Integer maxReplicaPerDeployment) {
    this.maxReplicaPerDeployment = maxReplicaPerDeployment;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndDeploymentQuota}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndDeploymentQuota} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndDeploymentQuota has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndDeploymentQuota} instance. If the map
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
    final BckndDeploymentQuota bckndDeploymentQuota = (BckndDeploymentQuota) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndDeploymentQuota.cloudSdkCustomFields)
        && Objects.equals(this.maxCount, bckndDeploymentQuota.maxCount)
        && Objects.equals(
            this.maxReplicaPerDeployment, bckndDeploymentQuota.maxReplicaPerDeployment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxCount, maxReplicaPerDeployment, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndDeploymentQuota {\n");
    sb.append("    maxCount: ").append(toIndentedString(maxCount)).append("\n");
    sb.append("    maxReplicaPerDeployment: ")
        .append(toIndentedString(maxReplicaPerDeployment))
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

  /** Create a new {@link BckndDeploymentQuota} instance. No arguments are required. */
  public static BckndDeploymentQuota create() {
    return new BckndDeploymentQuota();
  }
}
