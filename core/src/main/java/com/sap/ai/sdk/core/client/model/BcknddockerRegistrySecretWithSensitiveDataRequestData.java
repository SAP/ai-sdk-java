/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.36.0
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

/** key:value pairs of data */
// CHECKSTYLE:OFF
public class BcknddockerRegistrySecretWithSensitiveDataRequestData
// CHECKSTYLE:ON
{
  @JsonProperty(".dockerconfigjson")
  private String dockerconfigjson;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BcknddockerRegistrySecretWithSensitiveDataRequestData. */
  protected BcknddockerRegistrySecretWithSensitiveDataRequestData() {}

  /**
   * Set the dockerconfigjson of this {@link BcknddockerRegistrySecretWithSensitiveDataRequestData}
   * instance and return the same instance.
   *
   * @param dockerconfigjson .dockerconfigjson data
   * @return The same instance of this {@link BcknddockerRegistrySecretWithSensitiveDataRequestData}
   *     class
   */
  @Nonnull
  public BcknddockerRegistrySecretWithSensitiveDataRequestData dockerconfigjson(
      @Nonnull final String dockerconfigjson) {
    this.dockerconfigjson = dockerconfigjson;
    return this;
  }

  /**
   * .dockerconfigjson data
   *
   * @return dockerconfigjson The dockerconfigjson of this {@link
   *     BcknddockerRegistrySecretWithSensitiveDataRequestData} instance.
   */
  @Nonnull
  public String getDockerconfigjson() {
    return dockerconfigjson;
  }

  /**
   * Set the dockerconfigjson of this {@link BcknddockerRegistrySecretWithSensitiveDataRequestData}
   * instance.
   *
   * @param dockerconfigjson .dockerconfigjson data
   */
  public void setDockerconfigjson(@Nonnull final String dockerconfigjson) {
    this.dockerconfigjson = dockerconfigjson;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BcknddockerRegistrySecretWithSensitiveDataRequestData}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * BcknddockerRegistrySecretWithSensitiveDataRequestData} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BcknddockerRegistrySecretWithSensitiveDataRequestData has no field with name '"
              + name
              + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link
   * BcknddockerRegistrySecretWithSensitiveDataRequestData} instance. If the map previously
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
    final BcknddockerRegistrySecretWithSensitiveDataRequestData
        bcknddockerRegistrySecretWithSensitiveDataRequestData =
            (BcknddockerRegistrySecretWithSensitiveDataRequestData) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            bcknddockerRegistrySecretWithSensitiveDataRequestData.cloudSdkCustomFields)
        && Objects.equals(
            this.dockerconfigjson,
            bcknddockerRegistrySecretWithSensitiveDataRequestData.dockerconfigjson);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dockerconfigjson, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BcknddockerRegistrySecretWithSensitiveDataRequestData {\n");
    sb.append("    dockerconfigjson: ").append(toIndentedString(dockerconfigjson)).append("\n");
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
   * BcknddockerRegistrySecretWithSensitiveDataRequestData} instance with all required arguments.
   */
  public static Builder create() {
    return (dockerconfigjson) ->
        new BcknddockerRegistrySecretWithSensitiveDataRequestData()
            .dockerconfigjson(dockerconfigjson);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the dockerconfigjson of this {@link
     * BcknddockerRegistrySecretWithSensitiveDataRequestData} instance.
     *
     * @param dockerconfigjson .dockerconfigjson data
     * @return The BcknddockerRegistrySecretWithSensitiveDataRequestData instance.
     */
    BcknddockerRegistrySecretWithSensitiveDataRequestData dockerconfigjson(
        @Nonnull final String dockerconfigjson);
  }
}
