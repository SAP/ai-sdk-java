

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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationStatusSource;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationStatusSyncResourcesStatusInner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ArgoCD application definition and status
 */

// CHECKSTYLE:OFF
public class BckndArgoCDApplicationStatus 
// CHECKSTYLE:ON
{
  @JsonProperty("healthStatus")
  private String healthStatus;

  @JsonProperty("syncStatus")
  private String syncStatus;

  @JsonProperty("message")
  private String message;

  @JsonProperty("source")
  private BckndArgoCDApplicationStatusSource source;

  @JsonProperty("syncFinishedAt")
  private String syncFinishedAt;

  @JsonProperty("syncStartedAt")
  private String syncStartedAt;

  @JsonProperty("reconciledAt")
  private String reconciledAt;

  @JsonProperty("syncResourcesStatus")
  private List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncResourcesStatus = new ArrayList<>();

  @JsonProperty("syncRessourcesStatus")
  private List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncRessourcesStatus = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the healthStatus of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param healthStatus  ArgoCD application health status
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus healthStatus(@Nonnull final String healthStatus) {
    this.healthStatus = healthStatus;
    return this;
  }

   /**
   * ArgoCD application health status
   * @return healthStatus  The healthStatus of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getHealthStatus() {
    return healthStatus;
  }

  /**
  * Set the healthStatus of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param healthStatus  ArgoCD application health status
  */
  public void setHealthStatus( @Nonnull final String healthStatus) {
    this.healthStatus = healthStatus;
  }

   /**
   * Set the syncStatus of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param syncStatus  ArgoCD application sync status
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus syncStatus(@Nonnull final String syncStatus) {
    this.syncStatus = syncStatus;
    return this;
  }

   /**
   * ArgoCD application sync status
   * @return syncStatus  The syncStatus of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getSyncStatus() {
    return syncStatus;
  }

  /**
  * Set the syncStatus of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param syncStatus  ArgoCD application sync status
  */
  public void setSyncStatus( @Nonnull final String syncStatus) {
    this.syncStatus = syncStatus;
  }

   /**
   * Set the message of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param message  ArgoCD application health status message
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

   /**
   * ArgoCD application health status message
   * @return message  The message of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getMessage() {
    return message;
  }

  /**
  * Set the message of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param message  ArgoCD application health status message
  */
  public void setMessage( @Nonnull final String message) {
    this.message = message;
  }

   /**
   * Set the source of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param source  The source of this {@link BckndArgoCDApplicationStatus}
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus source(@Nonnull final BckndArgoCDApplicationStatusSource source) {
    this.source = source;
    return this;
  }

   /**
   * Get source
   * @return source  The source of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public BckndArgoCDApplicationStatusSource getSource() {
    return source;
  }

  /**
  * Set the source of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param source  The source of this {@link BckndArgoCDApplicationStatus}
  */
  public void setSource( @Nonnull final BckndArgoCDApplicationStatusSource source) {
    this.source = source;
  }

   /**
   * Set the syncFinishedAt of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param syncFinishedAt  Gets the timestamp information related to the sync state of the ArgoCD application
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus syncFinishedAt(@Nonnull final String syncFinishedAt) {
    this.syncFinishedAt = syncFinishedAt;
    return this;
  }

   /**
   * Gets the timestamp information related to the sync state of the ArgoCD application
   * @return syncFinishedAt  The syncFinishedAt of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getSyncFinishedAt() {
    return syncFinishedAt;
  }

  /**
  * Set the syncFinishedAt of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param syncFinishedAt  Gets the timestamp information related to the sync state of the ArgoCD application
  */
  public void setSyncFinishedAt( @Nonnull final String syncFinishedAt) {
    this.syncFinishedAt = syncFinishedAt;
  }

   /**
   * Set the syncStartedAt of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param syncStartedAt  Get timestamp information related to the sync state of the ArgoCD application
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus syncStartedAt(@Nonnull final String syncStartedAt) {
    this.syncStartedAt = syncStartedAt;
    return this;
  }

   /**
   * Get timestamp information related to the sync state of the ArgoCD application
   * @return syncStartedAt  The syncStartedAt of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getSyncStartedAt() {
    return syncStartedAt;
  }

  /**
  * Set the syncStartedAt of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param syncStartedAt  Get timestamp information related to the sync state of the ArgoCD application
  */
  public void setSyncStartedAt( @Nonnull final String syncStartedAt) {
    this.syncStartedAt = syncStartedAt;
  }

   /**
   * Set the reconciledAt of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param reconciledAt  Get timestamp information related to the sync state of the ArgoCD application
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus reconciledAt(@Nonnull final String reconciledAt) {
    this.reconciledAt = reconciledAt;
    return this;
  }

   /**
   * Get timestamp information related to the sync state of the ArgoCD application
   * @return reconciledAt  The reconciledAt of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public String getReconciledAt() {
    return reconciledAt;
  }

  /**
  * Set the reconciledAt of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param reconciledAt  Get timestamp information related to the sync state of the ArgoCD application
  */
  public void setReconciledAt( @Nonnull final String reconciledAt) {
    this.reconciledAt = reconciledAt;
  }

   /**
   * Set the syncResourcesStatus of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param syncResourcesStatus  Status of all resources that need to be synchronized with the gitops repo
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus syncResourcesStatus(@Nonnull final List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncResourcesStatus) {
    this.syncResourcesStatus = syncResourcesStatus;
    return this;
  }
  /**
  * Add one syncResourcesStatus instance to this {@link BckndArgoCDApplicationStatus}.
  * @param syncResourcesStatusItem The syncResourcesStatus that should be added
  * @return The same instance of type {@link BckndArgoCDApplicationStatus}
  */
  @Nonnull public BckndArgoCDApplicationStatus addsyncResourcesStatusItem( @Nonnull final BckndArgoCDApplicationStatusSyncResourcesStatusInner syncResourcesStatusItem) {
    if (this.syncResourcesStatus == null) {
      this.syncResourcesStatus = new ArrayList<>();
    }
    this.syncResourcesStatus.add(syncResourcesStatusItem);
    return this;
  }

   /**
   * Status of all resources that need to be synchronized with the gitops repo
   * @return syncResourcesStatus  The syncResourcesStatus of this {@link BckndArgoCDApplicationStatus} instance.
  **/
  @Nonnull public List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> getSyncResourcesStatus() {
    return syncResourcesStatus;
  }

  /**
  * Set the syncResourcesStatus of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param syncResourcesStatus  Status of all resources that need to be synchronized with the gitops repo
  */
  public void setSyncResourcesStatus( @Nonnull final List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncResourcesStatus) {
    this.syncResourcesStatus = syncResourcesStatus;
  }

   /**
   * Set the syncRessourcesStatus of this {@link BckndArgoCDApplicationStatus} instance and return the same instance.
   *
   * @param syncRessourcesStatus  Status of all resources that need to be synchronized with the gitops repo. Misspelled and deprecated, use syncResourcesStatus instead.
   * @return The same instance of this {@link BckndArgoCDApplicationStatus} class
   */
   @Nonnull public BckndArgoCDApplicationStatus syncRessourcesStatus(@Nonnull final List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncRessourcesStatus) {
    this.syncRessourcesStatus = syncRessourcesStatus;
    return this;
  }
  /**
  * Add one syncRessourcesStatus instance to this {@link BckndArgoCDApplicationStatus}.
  * @param syncRessourcesStatusItem The syncRessourcesStatus that should be added
  * @return The same instance of type {@link BckndArgoCDApplicationStatus}
  */
  @Nonnull public BckndArgoCDApplicationStatus addsyncRessourcesStatusItem( @Nonnull final BckndArgoCDApplicationStatusSyncResourcesStatusInner syncRessourcesStatusItem) {
    if (this.syncRessourcesStatus == null) {
      this.syncRessourcesStatus = new ArrayList<>();
    }
    this.syncRessourcesStatus.add(syncRessourcesStatusItem);
    return this;
  }

   /**
   * Status of all resources that need to be synchronized with the gitops repo. Misspelled and deprecated, use syncResourcesStatus instead.
   * @return syncRessourcesStatus  The syncRessourcesStatus of this {@link BckndArgoCDApplicationStatus} instance.
   * @deprecated
  **/
   @Deprecated
  @Nonnull public List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> getSyncRessourcesStatus() {
    return syncRessourcesStatus;
  }

  /**
  * Set the syncRessourcesStatus of this {@link BckndArgoCDApplicationStatus} instance.
  *
  * @param syncRessourcesStatus  Status of all resources that need to be synchronized with the gitops repo. Misspelled and deprecated, use syncResourcesStatus instead.
  */
  public void setSyncRessourcesStatus( @Nonnull final List<BckndArgoCDApplicationStatusSyncResourcesStatusInner> syncRessourcesStatus) {
    this.syncRessourcesStatus = syncRessourcesStatus;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndArgoCDApplicationStatus}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndArgoCDApplicationStatus} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndArgoCDApplicationStatus has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDApplicationStatus} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
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
    final BckndArgoCDApplicationStatus bckndArgoCDApplicationStatus = (BckndArgoCDApplicationStatus) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndArgoCDApplicationStatus.cloudSdkCustomFields) &&
        Objects.equals(this.healthStatus, bckndArgoCDApplicationStatus.healthStatus) &&
        Objects.equals(this.syncStatus, bckndArgoCDApplicationStatus.syncStatus) &&
        Objects.equals(this.message, bckndArgoCDApplicationStatus.message) &&
        Objects.equals(this.source, bckndArgoCDApplicationStatus.source) &&
        Objects.equals(this.syncFinishedAt, bckndArgoCDApplicationStatus.syncFinishedAt) &&
        Objects.equals(this.syncStartedAt, bckndArgoCDApplicationStatus.syncStartedAt) &&
        Objects.equals(this.reconciledAt, bckndArgoCDApplicationStatus.reconciledAt) &&
        Objects.equals(this.syncResourcesStatus, bckndArgoCDApplicationStatus.syncResourcesStatus) &&
        Objects.equals(this.syncRessourcesStatus, bckndArgoCDApplicationStatus.syncRessourcesStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(healthStatus, syncStatus, message, source, syncFinishedAt, syncStartedAt, reconciledAt, syncResourcesStatus, syncRessourcesStatus, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDApplicationStatus {\n");
    sb.append("    healthStatus: ").append(toIndentedString(healthStatus)).append("\n");
    sb.append("    syncStatus: ").append(toIndentedString(syncStatus)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    syncFinishedAt: ").append(toIndentedString(syncFinishedAt)).append("\n");
    sb.append("    syncStartedAt: ").append(toIndentedString(syncStartedAt)).append("\n");
    sb.append("    reconciledAt: ").append(toIndentedString(reconciledAt)).append("\n");
    sb.append("    syncResourcesStatus: ").append(toIndentedString(syncResourcesStatus)).append("\n");
    sb.append("    syncRessourcesStatus: ").append(toIndentedString(syncRessourcesStatus)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

