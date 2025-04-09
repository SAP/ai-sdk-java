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

/**
 * Services that support patch on /executions and /deployments to change targetStatus of multiple
 * executions and deployments.
 */
// CHECKSTYLE:OFF
public class MetaAiApiCapabilitiesBulkUpdates
// CHECKSTYLE:ON
{
  @JsonProperty("executions")
  private Boolean executions = false;

  @JsonProperty("deployments")
  private Boolean deployments = false;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MetaAiApiCapabilitiesBulkUpdates. */
  protected MetaAiApiCapabilitiesBulkUpdates() {}

  /**
   * Set the executions of this {@link MetaAiApiCapabilitiesBulkUpdates} instance and return the
   * same instance.
   *
   * @param executions The executions of this {@link MetaAiApiCapabilitiesBulkUpdates}
   * @return The same instance of this {@link MetaAiApiCapabilitiesBulkUpdates} class
   */
  @Nonnull
  public MetaAiApiCapabilitiesBulkUpdates executions(@Nullable final Boolean executions) {
    this.executions = executions;
    return this;
  }

  /**
   * Get executions
   *
   * @return executions The executions of this {@link MetaAiApiCapabilitiesBulkUpdates} instance.
   */
  @Nonnull
  public Boolean isExecutions() {
    return executions;
  }

  /**
   * Set the executions of this {@link MetaAiApiCapabilitiesBulkUpdates} instance.
   *
   * @param executions The executions of this {@link MetaAiApiCapabilitiesBulkUpdates}
   */
  public void setExecutions(@Nullable final Boolean executions) {
    this.executions = executions;
  }

  /**
   * Set the deployments of this {@link MetaAiApiCapabilitiesBulkUpdates} instance and return the
   * same instance.
   *
   * @param deployments The deployments of this {@link MetaAiApiCapabilitiesBulkUpdates}
   * @return The same instance of this {@link MetaAiApiCapabilitiesBulkUpdates} class
   */
  @Nonnull
  public MetaAiApiCapabilitiesBulkUpdates deployments(@Nullable final Boolean deployments) {
    this.deployments = deployments;
    return this;
  }

  /**
   * Get deployments
   *
   * @return deployments The deployments of this {@link MetaAiApiCapabilitiesBulkUpdates} instance.
   */
  @Nonnull
  public Boolean isDeployments() {
    return deployments;
  }

  /**
   * Set the deployments of this {@link MetaAiApiCapabilitiesBulkUpdates} instance.
   *
   * @param deployments The deployments of this {@link MetaAiApiCapabilitiesBulkUpdates}
   */
  public void setDeployments(@Nullable final Boolean deployments) {
    this.deployments = deployments;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MetaAiApiCapabilitiesBulkUpdates}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaAiApiCapabilitiesBulkUpdates}
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
          "MetaAiApiCapabilitiesBulkUpdates has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link MetaAiApiCapabilitiesBulkUpdates} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (executions != null) declaredFields.put("executions", executions);
    if (deployments != null) declaredFields.put("deployments", deployments);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link MetaAiApiCapabilitiesBulkUpdates} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final MetaAiApiCapabilitiesBulkUpdates metaAiApiCapabilitiesBulkUpdates =
        (MetaAiApiCapabilitiesBulkUpdates) o;
    return Objects.equals(
            this.cloudSdkCustomFields, metaAiApiCapabilitiesBulkUpdates.cloudSdkCustomFields)
        && Objects.equals(this.executions, metaAiApiCapabilitiesBulkUpdates.executions)
        && Objects.equals(this.deployments, metaAiApiCapabilitiesBulkUpdates.deployments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executions, deployments, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaAiApiCapabilitiesBulkUpdates {\n");
    sb.append("    executions: ").append(toIndentedString(executions)).append("\n");
    sb.append("    deployments: ").append(toIndentedString(deployments)).append("\n");
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

  /** Create a new {@link MetaAiApiCapabilitiesBulkUpdates} instance. No arguments are required. */
  public static MetaAiApiCapabilitiesBulkUpdates create() {
    return new MetaAiApiCapabilitiesBulkUpdates();
  }
}
