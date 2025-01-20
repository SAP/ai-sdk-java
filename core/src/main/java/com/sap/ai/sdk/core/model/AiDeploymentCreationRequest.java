/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.38.0
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Request object for creating an execution or an deployment */
@Beta // CHECKSTYLE:OFF
public class AiDeploymentCreationRequest
// CHECKSTYLE:ON
{
  @JsonProperty("ttl")
  private String ttl;

  @JsonProperty("configurationId")
  private String configurationId;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiDeploymentCreationRequest. */
  protected AiDeploymentCreationRequest() {}

  /**
   * Set the ttl of this {@link AiDeploymentCreationRequest} instance and return the same instance.
   *
   * @param ttl Time to live for a deployment. Its value can be either null or a number followed by
   *     the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   * @return The same instance of this {@link AiDeploymentCreationRequest} class
   */
  @Nonnull
  public AiDeploymentCreationRequest ttl(@Nullable final String ttl) {
    this.ttl = ttl;
    return this;
  }

  /**
   * Time to live for a deployment. Its value can be either null or a number followed by the unit
   * (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   *
   * @return ttl The ttl of this {@link AiDeploymentCreationRequest} instance.
   */
  @Nullable
  public String getTtl() {
    return ttl;
  }

  /**
   * Set the ttl of this {@link AiDeploymentCreationRequest} instance.
   *
   * @param ttl Time to live for a deployment. Its value can be either null or a number followed by
   *     the unit (any of following values, minutes(m|M), hours(h|H) or days(d|D))
   */
  public void setTtl(@Nullable final String ttl) {
    this.ttl = ttl;
  }

  /**
   * Set the configurationId of this {@link AiDeploymentCreationRequest} instance and return the
   * same instance.
   *
   * @param configurationId ID of the configuration
   * @return The same instance of this {@link AiDeploymentCreationRequest} class
   */
  @Nonnull
  public AiDeploymentCreationRequest configurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
    return this;
  }

  /**
   * ID of the configuration
   *
   * @return configurationId The configurationId of this {@link AiDeploymentCreationRequest}
   *     instance.
   */
  @Nonnull
  public String getConfigurationId() {
    return configurationId;
  }

  /**
   * Set the configurationId of this {@link AiDeploymentCreationRequest} instance.
   *
   * @param configurationId ID of the configuration
   */
  public void setConfigurationId(@Nonnull final String configurationId) {
    this.configurationId = configurationId;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiDeploymentCreationRequest}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiDeploymentCreationRequest}
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
          "AiDeploymentCreationRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiDeploymentCreationRequest} instance. If the map
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
    final AiDeploymentCreationRequest aiDeploymentCreationRequest = (AiDeploymentCreationRequest) o;
    return Objects.equals(
            this.cloudSdkCustomFields, aiDeploymentCreationRequest.cloudSdkCustomFields)
        && Objects.equals(this.ttl, aiDeploymentCreationRequest.ttl)
        && Objects.equals(this.configurationId, aiDeploymentCreationRequest.configurationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ttl, configurationId, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiDeploymentCreationRequest {\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
    sb.append("    configurationId: ").append(toIndentedString(configurationId)).append("\n");
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
   * AiDeploymentCreationRequest} instance with all required arguments.
   */
  public static Builder create() {
    return (configurationId) -> new AiDeploymentCreationRequest().configurationId(configurationId);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the configurationId of this {@link AiDeploymentCreationRequest} instance.
     *
     * @param configurationId ID of the configuration
     * @return The AiDeploymentCreationRequest instance.
     */
    AiDeploymentCreationRequest configurationId(@Nonnull final String configurationId);
  }
}
