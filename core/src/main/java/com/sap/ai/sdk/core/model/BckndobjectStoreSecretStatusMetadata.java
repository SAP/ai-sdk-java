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

/** Key value pairs of meta-data assigned to the secret when the secret was being created. */
@Beta // CHECKSTYLE:OFF
public class BckndobjectStoreSecretStatusMetadata
// CHECKSTYLE:ON
{
  @JsonProperty("serving.kubeflow.org/s3-usehttps")
  private String servingKubeflowOrgS3Usehttps;

  @JsonProperty("serving.kubeflow.org/s3-verifyssl")
  private String servingKubeflowOrgS3Verifyssl;

  @JsonProperty("serving.kubeflow.org/s3-endpoint")
  private String servingKubeflowOrgS3Endpoint;

  @JsonProperty("serving.kubeflow.org/s3-region")
  private String servingKubeflowOrgS3Region;

  @JsonProperty("storage.ai.sap.com/type")
  private String storageAiSapComType;

  @JsonProperty("storage.ai.sap.com/bucket")
  private String storageAiSapComBucket;

  @JsonProperty("storage.ai.sap.com/endpoint")
  private String storageAiSapComEndpoint;

  @JsonProperty("storage.ai.sap.com/region")
  private String storageAiSapComRegion;

  @JsonProperty("storage.ai.sap.com/pathPrefix")
  private String storageAiSapComPathPrefix;

  @JsonProperty("storage.ai.sap.com/hdfsNameNode")
  private String storageAiSapComHdfsNameNode;

  @JsonProperty("storage.ai.sap.com/headers")
  private String storageAiSapComHeaders;

  @JsonProperty("storage.ai.sap.com/containerUri")
  private String storageAiSapComContainerUri;

  @JsonProperty("storage.ai.sap.com/subscriptionId")
  private String storageAiSapComSubscriptionId;

  @JsonProperty("storage.ai.sap.com/tenantId")
  private String storageAiSapComTenantId;

  @JsonProperty("storage.ai.sap.com/projectId")
  private String storageAiSapComProjectId;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndobjectStoreSecretStatusMetadata. */
  protected BckndobjectStoreSecretStatusMetadata() {}

  /**
   * Set the servingKubeflowOrgS3Usehttps of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param servingKubeflowOrgS3Usehttps 0 and 1 values for setting the flag
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata servingKubeflowOrgS3Usehttps(
      @Nullable final String servingKubeflowOrgS3Usehttps) {
    this.servingKubeflowOrgS3Usehttps = servingKubeflowOrgS3Usehttps;
    return this;
  }

  /**
   * 0 and 1 values for setting the flag
   *
   * @return servingKubeflowOrgS3Usehttps The servingKubeflowOrgS3Usehttps of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getServingKubeflowOrgS3Usehttps() {
    return servingKubeflowOrgS3Usehttps;
  }

  /**
   * Set the servingKubeflowOrgS3Usehttps of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param servingKubeflowOrgS3Usehttps 0 and 1 values for setting the flag
   */
  public void setServingKubeflowOrgS3Usehttps(@Nullable final String servingKubeflowOrgS3Usehttps) {
    this.servingKubeflowOrgS3Usehttps = servingKubeflowOrgS3Usehttps;
  }

  /**
   * Set the servingKubeflowOrgS3Verifyssl of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param servingKubeflowOrgS3Verifyssl 0 and 1 values for setting the flag
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata servingKubeflowOrgS3Verifyssl(
      @Nullable final String servingKubeflowOrgS3Verifyssl) {
    this.servingKubeflowOrgS3Verifyssl = servingKubeflowOrgS3Verifyssl;
    return this;
  }

  /**
   * 0 and 1 values for setting the flag
   *
   * @return servingKubeflowOrgS3Verifyssl The servingKubeflowOrgS3Verifyssl of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getServingKubeflowOrgS3Verifyssl() {
    return servingKubeflowOrgS3Verifyssl;
  }

  /**
   * Set the servingKubeflowOrgS3Verifyssl of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param servingKubeflowOrgS3Verifyssl 0 and 1 values for setting the flag
   */
  public void setServingKubeflowOrgS3Verifyssl(
      @Nullable final String servingKubeflowOrgS3Verifyssl) {
    this.servingKubeflowOrgS3Verifyssl = servingKubeflowOrgS3Verifyssl;
  }

  /**
   * Set the servingKubeflowOrgS3Endpoint of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param servingKubeflowOrgS3Endpoint Annotation for endpoint required by KF_Serving
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata servingKubeflowOrgS3Endpoint(
      @Nullable final String servingKubeflowOrgS3Endpoint) {
    this.servingKubeflowOrgS3Endpoint = servingKubeflowOrgS3Endpoint;
    return this;
  }

  /**
   * Annotation for endpoint required by KF_Serving
   *
   * @return servingKubeflowOrgS3Endpoint The servingKubeflowOrgS3Endpoint of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getServingKubeflowOrgS3Endpoint() {
    return servingKubeflowOrgS3Endpoint;
  }

  /**
   * Set the servingKubeflowOrgS3Endpoint of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param servingKubeflowOrgS3Endpoint Annotation for endpoint required by KF_Serving
   */
  public void setServingKubeflowOrgS3Endpoint(@Nullable final String servingKubeflowOrgS3Endpoint) {
    this.servingKubeflowOrgS3Endpoint = servingKubeflowOrgS3Endpoint;
  }

  /**
   * Set the servingKubeflowOrgS3Region of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param servingKubeflowOrgS3Region Annotation for region required by KF_Serving
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata servingKubeflowOrgS3Region(
      @Nullable final String servingKubeflowOrgS3Region) {
    this.servingKubeflowOrgS3Region = servingKubeflowOrgS3Region;
    return this;
  }

  /**
   * Annotation for region required by KF_Serving
   *
   * @return servingKubeflowOrgS3Region The servingKubeflowOrgS3Region of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getServingKubeflowOrgS3Region() {
    return servingKubeflowOrgS3Region;
  }

  /**
   * Set the servingKubeflowOrgS3Region of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param servingKubeflowOrgS3Region Annotation for region required by KF_Serving
   */
  public void setServingKubeflowOrgS3Region(@Nullable final String servingKubeflowOrgS3Region) {
    this.servingKubeflowOrgS3Region = servingKubeflowOrgS3Region;
  }

  /**
   * Set the storageAiSapComType of this {@link BckndobjectStoreSecretStatusMetadata} instance and
   * return the same instance.
   *
   * @param storageAiSapComType Storage type of the secret
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComType(
      @Nullable final String storageAiSapComType) {
    this.storageAiSapComType = storageAiSapComType;
    return this;
  }

  /**
   * Storage type of the secret
   *
   * @return storageAiSapComType The storageAiSapComType of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComType() {
    return storageAiSapComType;
  }

  /**
   * Set the storageAiSapComType of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComType Storage type of the secret
   */
  public void setStorageAiSapComType(@Nullable final String storageAiSapComType) {
    this.storageAiSapComType = storageAiSapComType;
  }

  /**
   * Set the storageAiSapComBucket of this {@link BckndobjectStoreSecretStatusMetadata} instance and
   * return the same instance.
   *
   * @param storageAiSapComBucket bucket assigned to the secret on creation
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComBucket(
      @Nullable final String storageAiSapComBucket) {
    this.storageAiSapComBucket = storageAiSapComBucket;
    return this;
  }

  /**
   * bucket assigned to the secret on creation
   *
   * @return storageAiSapComBucket The storageAiSapComBucket of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComBucket() {
    return storageAiSapComBucket;
  }

  /**
   * Set the storageAiSapComBucket of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComBucket bucket assigned to the secret on creation
   */
  public void setStorageAiSapComBucket(@Nullable final String storageAiSapComBucket) {
    this.storageAiSapComBucket = storageAiSapComBucket;
  }

  /**
   * Set the storageAiSapComEndpoint of this {@link BckndobjectStoreSecretStatusMetadata} instance
   * and return the same instance.
   *
   * @param storageAiSapComEndpoint Endpoint assigned to the secret on creation
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComEndpoint(
      @Nullable final String storageAiSapComEndpoint) {
    this.storageAiSapComEndpoint = storageAiSapComEndpoint;
    return this;
  }

  /**
   * Endpoint assigned to the secret on creation
   *
   * @return storageAiSapComEndpoint The storageAiSapComEndpoint of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComEndpoint() {
    return storageAiSapComEndpoint;
  }

  /**
   * Set the storageAiSapComEndpoint of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComEndpoint Endpoint assigned to the secret on creation
   */
  public void setStorageAiSapComEndpoint(@Nullable final String storageAiSapComEndpoint) {
    this.storageAiSapComEndpoint = storageAiSapComEndpoint;
  }

  /**
   * Set the storageAiSapComRegion of this {@link BckndobjectStoreSecretStatusMetadata} instance and
   * return the same instance.
   *
   * @param storageAiSapComRegion Region of the storage server
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComRegion(
      @Nullable final String storageAiSapComRegion) {
    this.storageAiSapComRegion = storageAiSapComRegion;
    return this;
  }

  /**
   * Region of the storage server
   *
   * @return storageAiSapComRegion The storageAiSapComRegion of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComRegion() {
    return storageAiSapComRegion;
  }

  /**
   * Set the storageAiSapComRegion of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComRegion Region of the storage server
   */
  public void setStorageAiSapComRegion(@Nullable final String storageAiSapComRegion) {
    this.storageAiSapComRegion = storageAiSapComRegion;
  }

  /**
   * Set the storageAiSapComPathPrefix of this {@link BckndobjectStoreSecretStatusMetadata} instance
   * and return the same instance.
   *
   * @param storageAiSapComPathPrefix Pathprefix type assigned to the secret on creation.
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComPathPrefix(
      @Nullable final String storageAiSapComPathPrefix) {
    this.storageAiSapComPathPrefix = storageAiSapComPathPrefix;
    return this;
  }

  /**
   * Pathprefix type assigned to the secret on creation.
   *
   * @return storageAiSapComPathPrefix The storageAiSapComPathPrefix of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComPathPrefix() {
    return storageAiSapComPathPrefix;
  }

  /**
   * Set the storageAiSapComPathPrefix of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param storageAiSapComPathPrefix Pathprefix type assigned to the secret on creation.
   */
  public void setStorageAiSapComPathPrefix(@Nullable final String storageAiSapComPathPrefix) {
    this.storageAiSapComPathPrefix = storageAiSapComPathPrefix;
  }

  /**
   * Set the storageAiSapComHdfsNameNode of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param storageAiSapComHdfsNameNode name node of the HDFS file system
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComHdfsNameNode(
      @Nullable final String storageAiSapComHdfsNameNode) {
    this.storageAiSapComHdfsNameNode = storageAiSapComHdfsNameNode;
    return this;
  }

  /**
   * name node of the HDFS file system
   *
   * @return storageAiSapComHdfsNameNode The storageAiSapComHdfsNameNode of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComHdfsNameNode() {
    return storageAiSapComHdfsNameNode;
  }

  /**
   * Set the storageAiSapComHdfsNameNode of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param storageAiSapComHdfsNameNode name node of the HDFS file system
   */
  public void setStorageAiSapComHdfsNameNode(@Nullable final String storageAiSapComHdfsNameNode) {
    this.storageAiSapComHdfsNameNode = storageAiSapComHdfsNameNode;
  }

  /**
   * Set the storageAiSapComHeaders of this {@link BckndobjectStoreSecretStatusMetadata} instance
   * and return the same instance.
   *
   * @param storageAiSapComHeaders headers for webHDFS and other protocols
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComHeaders(
      @Nullable final String storageAiSapComHeaders) {
    this.storageAiSapComHeaders = storageAiSapComHeaders;
    return this;
  }

  /**
   * headers for webHDFS and other protocols
   *
   * @return storageAiSapComHeaders The storageAiSapComHeaders of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComHeaders() {
    return storageAiSapComHeaders;
  }

  /**
   * Set the storageAiSapComHeaders of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComHeaders headers for webHDFS and other protocols
   */
  public void setStorageAiSapComHeaders(@Nullable final String storageAiSapComHeaders) {
    this.storageAiSapComHeaders = storageAiSapComHeaders;
  }

  /**
   * Set the storageAiSapComContainerUri of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param storageAiSapComContainerUri container uri of azure storage
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComContainerUri(
      @Nullable final String storageAiSapComContainerUri) {
    this.storageAiSapComContainerUri = storageAiSapComContainerUri;
    return this;
  }

  /**
   * container uri of azure storage
   *
   * @return storageAiSapComContainerUri The storageAiSapComContainerUri of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComContainerUri() {
    return storageAiSapComContainerUri;
  }

  /**
   * Set the storageAiSapComContainerUri of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param storageAiSapComContainerUri container uri of azure storage
   */
  public void setStorageAiSapComContainerUri(@Nullable final String storageAiSapComContainerUri) {
    this.storageAiSapComContainerUri = storageAiSapComContainerUri;
  }

  /**
   * Set the storageAiSapComSubscriptionId of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance and return the same instance.
   *
   * @param storageAiSapComSubscriptionId subscription id
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComSubscriptionId(
      @Nullable final String storageAiSapComSubscriptionId) {
    this.storageAiSapComSubscriptionId = storageAiSapComSubscriptionId;
    return this;
  }

  /**
   * subscription id
   *
   * @return storageAiSapComSubscriptionId The storageAiSapComSubscriptionId of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComSubscriptionId() {
    return storageAiSapComSubscriptionId;
  }

  /**
   * Set the storageAiSapComSubscriptionId of this {@link BckndobjectStoreSecretStatusMetadata}
   * instance.
   *
   * @param storageAiSapComSubscriptionId subscription id
   */
  public void setStorageAiSapComSubscriptionId(
      @Nullable final String storageAiSapComSubscriptionId) {
    this.storageAiSapComSubscriptionId = storageAiSapComSubscriptionId;
  }

  /**
   * Set the storageAiSapComTenantId of this {@link BckndobjectStoreSecretStatusMetadata} instance
   * and return the same instance.
   *
   * @param storageAiSapComTenantId tenant id
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComTenantId(
      @Nullable final String storageAiSapComTenantId) {
    this.storageAiSapComTenantId = storageAiSapComTenantId;
    return this;
  }

  /**
   * tenant id
   *
   * @return storageAiSapComTenantId The storageAiSapComTenantId of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComTenantId() {
    return storageAiSapComTenantId;
  }

  /**
   * Set the storageAiSapComTenantId of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComTenantId tenant id
   */
  public void setStorageAiSapComTenantId(@Nullable final String storageAiSapComTenantId) {
    this.storageAiSapComTenantId = storageAiSapComTenantId;
  }

  /**
   * Set the storageAiSapComProjectId of this {@link BckndobjectStoreSecretStatusMetadata} instance
   * and return the same instance.
   *
   * @param storageAiSapComProjectId project id of google cloud platform
   * @return The same instance of this {@link BckndobjectStoreSecretStatusMetadata} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusMetadata storageAiSapComProjectId(
      @Nullable final String storageAiSapComProjectId) {
    this.storageAiSapComProjectId = storageAiSapComProjectId;
    return this;
  }

  /**
   * project id of google cloud platform
   *
   * @return storageAiSapComProjectId The storageAiSapComProjectId of this {@link
   *     BckndobjectStoreSecretStatusMetadata} instance.
   */
  @Nonnull
  public String getStorageAiSapComProjectId() {
    return storageAiSapComProjectId;
  }

  /**
   * Set the storageAiSapComProjectId of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param storageAiSapComProjectId project id of google cloud platform
   */
  public void setStorageAiSapComProjectId(@Nullable final String storageAiSapComProjectId) {
    this.storageAiSapComProjectId = storageAiSapComProjectId;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndobjectStoreSecretStatusMetadata}.
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
   * BckndobjectStoreSecretStatusMetadata} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndobjectStoreSecretStatusMetadata has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndobjectStoreSecretStatusMetadata} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
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
    final BckndobjectStoreSecretStatusMetadata bckndobjectStoreSecretStatusMetadata =
        (BckndobjectStoreSecretStatusMetadata) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndobjectStoreSecretStatusMetadata.cloudSdkCustomFields)
        && Objects.equals(
            this.servingKubeflowOrgS3Usehttps,
            bckndobjectStoreSecretStatusMetadata.servingKubeflowOrgS3Usehttps)
        && Objects.equals(
            this.servingKubeflowOrgS3Verifyssl,
            bckndobjectStoreSecretStatusMetadata.servingKubeflowOrgS3Verifyssl)
        && Objects.equals(
            this.servingKubeflowOrgS3Endpoint,
            bckndobjectStoreSecretStatusMetadata.servingKubeflowOrgS3Endpoint)
        && Objects.equals(
            this.servingKubeflowOrgS3Region,
            bckndobjectStoreSecretStatusMetadata.servingKubeflowOrgS3Region)
        && Objects.equals(
            this.storageAiSapComType, bckndobjectStoreSecretStatusMetadata.storageAiSapComType)
        && Objects.equals(
            this.storageAiSapComBucket, bckndobjectStoreSecretStatusMetadata.storageAiSapComBucket)
        && Objects.equals(
            this.storageAiSapComEndpoint,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComEndpoint)
        && Objects.equals(
            this.storageAiSapComRegion, bckndobjectStoreSecretStatusMetadata.storageAiSapComRegion)
        && Objects.equals(
            this.storageAiSapComPathPrefix,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComPathPrefix)
        && Objects.equals(
            this.storageAiSapComHdfsNameNode,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComHdfsNameNode)
        && Objects.equals(
            this.storageAiSapComHeaders,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComHeaders)
        && Objects.equals(
            this.storageAiSapComContainerUri,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComContainerUri)
        && Objects.equals(
            this.storageAiSapComSubscriptionId,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComSubscriptionId)
        && Objects.equals(
            this.storageAiSapComTenantId,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComTenantId)
        && Objects.equals(
            this.storageAiSapComProjectId,
            bckndobjectStoreSecretStatusMetadata.storageAiSapComProjectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        servingKubeflowOrgS3Usehttps,
        servingKubeflowOrgS3Verifyssl,
        servingKubeflowOrgS3Endpoint,
        servingKubeflowOrgS3Region,
        storageAiSapComType,
        storageAiSapComBucket,
        storageAiSapComEndpoint,
        storageAiSapComRegion,
        storageAiSapComPathPrefix,
        storageAiSapComHdfsNameNode,
        storageAiSapComHeaders,
        storageAiSapComContainerUri,
        storageAiSapComSubscriptionId,
        storageAiSapComTenantId,
        storageAiSapComProjectId,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndobjectStoreSecretStatusMetadata {\n");
    sb.append("    servingKubeflowOrgS3Usehttps: ")
        .append(toIndentedString(servingKubeflowOrgS3Usehttps))
        .append("\n");
    sb.append("    servingKubeflowOrgS3Verifyssl: ")
        .append(toIndentedString(servingKubeflowOrgS3Verifyssl))
        .append("\n");
    sb.append("    servingKubeflowOrgS3Endpoint: ")
        .append(toIndentedString(servingKubeflowOrgS3Endpoint))
        .append("\n");
    sb.append("    servingKubeflowOrgS3Region: ")
        .append(toIndentedString(servingKubeflowOrgS3Region))
        .append("\n");
    sb.append("    storageAiSapComType: ")
        .append(toIndentedString(storageAiSapComType))
        .append("\n");
    sb.append("    storageAiSapComBucket: ")
        .append(toIndentedString(storageAiSapComBucket))
        .append("\n");
    sb.append("    storageAiSapComEndpoint: ")
        .append(toIndentedString(storageAiSapComEndpoint))
        .append("\n");
    sb.append("    storageAiSapComRegion: ")
        .append(toIndentedString(storageAiSapComRegion))
        .append("\n");
    sb.append("    storageAiSapComPathPrefix: ")
        .append(toIndentedString(storageAiSapComPathPrefix))
        .append("\n");
    sb.append("    storageAiSapComHdfsNameNode: ")
        .append(toIndentedString(storageAiSapComHdfsNameNode))
        .append("\n");
    sb.append("    storageAiSapComHeaders: ")
        .append(toIndentedString(storageAiSapComHeaders))
        .append("\n");
    sb.append("    storageAiSapComContainerUri: ")
        .append(toIndentedString(storageAiSapComContainerUri))
        .append("\n");
    sb.append("    storageAiSapComSubscriptionId: ")
        .append(toIndentedString(storageAiSapComSubscriptionId))
        .append("\n");
    sb.append("    storageAiSapComTenantId: ")
        .append(toIndentedString(storageAiSapComTenantId))
        .append("\n");
    sb.append("    storageAiSapComProjectId: ")
        .append(toIndentedString(storageAiSapComProjectId))
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

  /**
   * Create a new {@link BckndobjectStoreSecretStatusMetadata} instance. No arguments are required.
   */
  public static BckndobjectStoreSecretStatusMetadata create() {
    return new BckndobjectStoreSecretStatusMetadata();
  }
}
