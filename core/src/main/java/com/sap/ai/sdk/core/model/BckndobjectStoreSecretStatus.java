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

/**
 * This represents the meta-data of a stored secret. The &#39;data&#39; field of the secret is never
 * retrieved.
 */
@Beta // CHECKSTYLE:OFF
public class BckndobjectStoreSecretStatus
// CHECKSTYLE:ON
{
  @JsonProperty("metadata")
  private BckndobjectStoreSecretStatusMetadata metadata;

  @JsonProperty("name")
  private String name;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndobjectStoreSecretStatus. */
  protected BckndobjectStoreSecretStatus() {}

  /**
   * Set the metadata of this {@link BckndobjectStoreSecretStatus} instance and return the same
   * instance.
   *
   * @param metadata The metadata of this {@link BckndobjectStoreSecretStatus}
   * @return The same instance of this {@link BckndobjectStoreSecretStatus} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatus metadata(
      @Nullable final BckndobjectStoreSecretStatusMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link BckndobjectStoreSecretStatus} instance.
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link BckndobjectStoreSecretStatus} instance.
   *
   * @param metadata The metadata of this {@link BckndobjectStoreSecretStatus}
   */
  public void setMetadata(@Nullable final BckndobjectStoreSecretStatusMetadata metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the name of this {@link BckndobjectStoreSecretStatus} instance and return the same
   * instance.
   *
   * @param name Name of objectstore
   * @return The same instance of this {@link BckndobjectStoreSecretStatus} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatus name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of objectstore
   *
   * @return name The name of this {@link BckndobjectStoreSecretStatus} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndobjectStoreSecretStatus} instance.
   *
   * @param name Name of objectstore
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndobjectStoreSecretStatus}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndobjectStoreSecretStatus}
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
          "BckndobjectStoreSecretStatus has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndobjectStoreSecretStatus} instance. If the
   * map previously contained a mapping for the key, the old value is replaced by the specified
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
    final BckndobjectStoreSecretStatus bckndobjectStoreSecretStatus =
        (BckndobjectStoreSecretStatus) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndobjectStoreSecretStatus.cloudSdkCustomFields)
        && Objects.equals(this.metadata, bckndobjectStoreSecretStatus.metadata)
        && Objects.equals(this.name, bckndobjectStoreSecretStatus.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, name, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndobjectStoreSecretStatus {\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

  /** Create a new {@link BckndobjectStoreSecretStatus} instance. No arguments are required. */
  public static BckndobjectStoreSecretStatus create() {
    return new BckndobjectStoreSecretStatus();
  }
}
