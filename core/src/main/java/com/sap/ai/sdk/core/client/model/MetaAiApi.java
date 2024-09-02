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
 * There are (currently) the following types of execution engines 1) complete runtimes that offer
 * executions and deployments, 2) runtimes that do only batch inference and therefore don&#39;t
 * support deployments 3) runtimes that allow deployments, but with predefined models and therefore
 * don&#39;t need executions 4) runtimes that have fixed endpoints and therefore only need listing
 * deployments
 */
// CHECKSTYLE:OFF
public class MetaAiApi
// CHECKSTYLE:ON
{
  @JsonProperty("version")
  private String version;

  @JsonProperty("capabilities")
  private MetaAiApiCapabilities capabilities;

  @JsonProperty("limits")
  private MetaAiApiLimits limits;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected MetaAiApi() {}

  /**
   * Set the version of this {@link MetaAiApi} instance and return the same instance.
   *
   * @param version The version of this {@link MetaAiApi}
   * @return The same instance of this {@link MetaAiApi} class
   */
  @Nonnull
  public MetaAiApi version(@Nonnull final String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   *
   * @return version The version of this {@link MetaAiApi} instance.
   */
  @Nonnull
  public String getVersion() {
    return version;
  }

  /**
   * Set the version of this {@link MetaAiApi} instance.
   *
   * @param version The version of this {@link MetaAiApi}
   */
  public void setVersion(@Nonnull final String version) {
    this.version = version;
  }

  /**
   * Set the capabilities of this {@link MetaAiApi} instance and return the same instance.
   *
   * @param capabilities The capabilities of this {@link MetaAiApi}
   * @return The same instance of this {@link MetaAiApi} class
   */
  @Nonnull
  public MetaAiApi capabilities(@Nonnull final MetaAiApiCapabilities capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  /**
   * Get capabilities
   *
   * @return capabilities The capabilities of this {@link MetaAiApi} instance.
   */
  @Nonnull
  public MetaAiApiCapabilities getCapabilities() {
    return capabilities;
  }

  /**
   * Set the capabilities of this {@link MetaAiApi} instance.
   *
   * @param capabilities The capabilities of this {@link MetaAiApi}
   */
  public void setCapabilities(@Nonnull final MetaAiApiCapabilities capabilities) {
    this.capabilities = capabilities;
  }

  /**
   * Set the limits of this {@link MetaAiApi} instance and return the same instance.
   *
   * @param limits The limits of this {@link MetaAiApi}
   * @return The same instance of this {@link MetaAiApi} class
   */
  @Nonnull
  public MetaAiApi limits(@Nonnull final MetaAiApiLimits limits) {
    this.limits = limits;
    return this;
  }

  /**
   * Get limits
   *
   * @return limits The limits of this {@link MetaAiApi} instance.
   */
  @Nonnull
  public MetaAiApiLimits getLimits() {
    return limits;
  }

  /**
   * Set the limits of this {@link MetaAiApi} instance.
   *
   * @param limits The limits of this {@link MetaAiApi}
   */
  public void setLimits(@Nonnull final MetaAiApiLimits limits) {
    this.limits = limits;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MetaAiApi}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaAiApi} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("MetaAiApi has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link MetaAiApi} instance. If the map previously
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
    final MetaAiApi metaAiApi = (MetaAiApi) o;
    return Objects.equals(this.cloudSdkCustomFields, metaAiApi.cloudSdkCustomFields)
        && Objects.equals(this.version, metaAiApi.version)
        && Objects.equals(this.capabilities, metaAiApi.capabilities)
        && Objects.equals(this.limits, metaAiApi.limits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, capabilities, limits, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaAiApi {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
    sb.append("    limits: ").append(toIndentedString(limits)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link MetaAiApi} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (version) -> new MetaAiApi().version(version);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the version of this {@link MetaAiApi} instance.
     *
     * @param version The version of this {@link MetaAiApi}
     * @return The MetaAiApi instance.
     */
    MetaAiApi version(@Nonnull final String version);
  }
}
