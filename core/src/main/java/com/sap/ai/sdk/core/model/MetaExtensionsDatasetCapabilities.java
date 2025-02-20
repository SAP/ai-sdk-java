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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** List of Dataset extension capabilities */
@Beta // CHECKSTYLE:OFF
public class MetaExtensionsDatasetCapabilities
// CHECKSTYLE:ON
{
  @JsonProperty("upload")
  private Boolean upload = true;

  @JsonProperty("download")
  private Boolean download = true;

  @JsonProperty("delete")
  private Boolean delete = true;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MetaExtensionsDatasetCapabilities. */
  protected MetaExtensionsDatasetCapabilities() {}

  /**
   * Set the upload of this {@link MetaExtensionsDatasetCapabilities} instance and return the same
   * instance.
   *
   * @param upload Support for uploading of files
   * @return The same instance of this {@link MetaExtensionsDatasetCapabilities} class
   */
  @Nonnull
  public MetaExtensionsDatasetCapabilities upload(@Nullable final Boolean upload) {
    this.upload = upload;
    return this;
  }

  /**
   * Support for uploading of files
   *
   * @return upload The upload of this {@link MetaExtensionsDatasetCapabilities} instance.
   */
  @Nonnull
  public Boolean isUpload() {
    return upload;
  }

  /**
   * Set the upload of this {@link MetaExtensionsDatasetCapabilities} instance.
   *
   * @param upload Support for uploading of files
   */
  public void setUpload(@Nullable final Boolean upload) {
    this.upload = upload;
  }

  /**
   * Set the download of this {@link MetaExtensionsDatasetCapabilities} instance and return the same
   * instance.
   *
   * @param download Support for downloading of files
   * @return The same instance of this {@link MetaExtensionsDatasetCapabilities} class
   */
  @Nonnull
  public MetaExtensionsDatasetCapabilities download(@Nullable final Boolean download) {
    this.download = download;
    return this;
  }

  /**
   * Support for downloading of files
   *
   * @return download The download of this {@link MetaExtensionsDatasetCapabilities} instance.
   */
  @Nonnull
  public Boolean isDownload() {
    return download;
  }

  /**
   * Set the download of this {@link MetaExtensionsDatasetCapabilities} instance.
   *
   * @param download Support for downloading of files
   */
  public void setDownload(@Nullable final Boolean download) {
    this.download = download;
  }

  /**
   * Set the delete of this {@link MetaExtensionsDatasetCapabilities} instance and return the same
   * instance.
   *
   * @param delete Support for deletion of files
   * @return The same instance of this {@link MetaExtensionsDatasetCapabilities} class
   */
  @Nonnull
  public MetaExtensionsDatasetCapabilities delete(@Nullable final Boolean delete) {
    this.delete = delete;
    return this;
  }

  /**
   * Support for deletion of files
   *
   * @return delete The delete of this {@link MetaExtensionsDatasetCapabilities} instance.
   */
  @Nonnull
  public Boolean isDelete() {
    return delete;
  }

  /**
   * Set the delete of this {@link MetaExtensionsDatasetCapabilities} instance.
   *
   * @param delete Support for deletion of files
   */
  public void setDelete(@Nullable final Boolean delete) {
    this.delete = delete;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * MetaExtensionsDatasetCapabilities}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaExtensionsDatasetCapabilities}
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
          "MetaExtensionsDatasetCapabilities has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link MetaExtensionsDatasetCapabilities} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (upload != null) declaredFields.put("upload", upload);
    if (download != null) declaredFields.put("download", download);
    if (delete != null) declaredFields.put("delete", delete);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link MetaExtensionsDatasetCapabilities} instance. If
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
    final MetaExtensionsDatasetCapabilities metaExtensionsDatasetCapabilities =
        (MetaExtensionsDatasetCapabilities) o;
    return Objects.equals(
            this.cloudSdkCustomFields, metaExtensionsDatasetCapabilities.cloudSdkCustomFields)
        && Objects.equals(this.upload, metaExtensionsDatasetCapabilities.upload)
        && Objects.equals(this.download, metaExtensionsDatasetCapabilities.download)
        && Objects.equals(this.delete, metaExtensionsDatasetCapabilities.delete);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upload, download, delete, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaExtensionsDatasetCapabilities {\n");
    sb.append("    upload: ").append(toIndentedString(upload)).append("\n");
    sb.append("    download: ").append(toIndentedString(download)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
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

  /** Create a new {@link MetaExtensionsDatasetCapabilities} instance. No arguments are required. */
  public static MetaExtensionsDatasetCapabilities create() {
    return new MetaExtensionsDatasetCapabilities();
  }
}
