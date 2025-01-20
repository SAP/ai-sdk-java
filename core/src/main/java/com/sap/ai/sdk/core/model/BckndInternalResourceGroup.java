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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndInternalResourceGroup */
@Beta // CHECKSTYLE:OFF
public class BckndInternalResourceGroup
// CHECKSTYLE:ON
{
  @JsonProperty("resourceGroupId")
  private String resourceGroupId;

  @JsonProperty("tenantId")
  private String tenantId;

  @JsonProperty("zoneId")
  private String zoneId;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonProperty("labels")
  private List<BckndInternalResourceGroupLabel> labels = new ArrayList<>();

  /** aggregated status of the onboarding process */
  public enum StatusEnum {
    /** The PROVISIONED option of this BckndInternalResourceGroup */
    PROVISIONED("PROVISIONED"),

    /** The ERROR option of this BckndInternalResourceGroup */
    ERROR("ERROR"),

    /** The PROVISIONING option of this BckndInternalResourceGroup */
    PROVISIONING("PROVISIONING"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this BckndInternalResourceGroup */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type BckndInternalResourceGroup
     */
    @JsonCreator
    @Nonnull
    public static StatusEnum fromValue(@Nonnull final String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("statusMessage")
  private String statusMessage;

  @JsonProperty("annotations")
  private List<BckndInternalResourceGroupAnnotation> annotations = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndInternalResourceGroup. */
  protected BckndInternalResourceGroup() {}

  /**
   * Set the resourceGroupId of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param resourceGroupId resource group id
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup resourceGroupId(@Nonnull final String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

  /**
   * resource group id
   *
   * @return resourceGroupId The resourceGroupId of this {@link BckndInternalResourceGroup}
   *     instance.
   */
  @Nonnull
  public String getResourceGroupId() {
    return resourceGroupId;
  }

  /**
   * Set the resourceGroupId of this {@link BckndInternalResourceGroup} instance.
   *
   * @param resourceGroupId resource group id
   */
  public void setResourceGroupId(@Nonnull final String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  /**
   * Set the tenantId of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param tenantId tenant id
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup tenantId(@Nullable final String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * tenant id
   *
   * @return tenantId The tenantId of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public String getTenantId() {
    return tenantId;
  }

  /**
   * Set the tenantId of this {@link BckndInternalResourceGroup} instance.
   *
   * @param tenantId tenant id
   */
  public void setTenantId(@Nullable final String tenantId) {
    this.tenantId = tenantId;
  }

  /**
   * Set the zoneId of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param zoneId zone id
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup zoneId(@Nullable final String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  /**
   * zone id
   *
   * @return zoneId The zoneId of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public String getZoneId() {
    return zoneId;
  }

  /**
   * Set the zoneId of this {@link BckndInternalResourceGroup} instance.
   *
   * @param zoneId zone id
   */
  public void setZoneId(@Nullable final String zoneId) {
    this.zoneId = zoneId;
  }

  /**
   * Set the createdAt of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param createdAt Timestamp of resource group creation
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource group creation
   *
   * @return createdAt The createdAt of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link BckndInternalResourceGroup} instance.
   *
   * @param createdAt Timestamp of resource group creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the labels of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param labels Arbitrary labels as meta information
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup labels(
      @Nullable final List<BckndInternalResourceGroupLabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link BckndInternalResourceGroup}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link BckndInternalResourceGroup}
   */
  @Nonnull
  public BckndInternalResourceGroup addLabelsItem(
      @Nonnull final BckndInternalResourceGroupLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Arbitrary labels as meta information
   *
   * @return labels The labels of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public List<BckndInternalResourceGroupLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link BckndInternalResourceGroup} instance.
   *
   * @param labels Arbitrary labels as meta information
   */
  public void setLabels(@Nullable final List<BckndInternalResourceGroupLabel> labels) {
    this.labels = labels;
  }

  /**
   * Set the status of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param status aggregated status of the onboarding process
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup status(@Nonnull final StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * aggregated status of the onboarding process
   *
   * @return status The status of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public StatusEnum getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link BckndInternalResourceGroup} instance.
   *
   * @param status aggregated status of the onboarding process
   */
  public void setStatus(@Nonnull final StatusEnum status) {
    this.status = status;
  }

  /**
   * Set the statusMessage of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param statusMessage status message
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup statusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * status message
   *
   * @return statusMessage The statusMessage of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Set the statusMessage of this {@link BckndInternalResourceGroup} instance.
   *
   * @param statusMessage status message
   */
  public void setStatusMessage(@Nullable final String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * Set the annotations of this {@link BckndInternalResourceGroup} instance and return the same
   * instance.
   *
   * @param annotations Arbitrary annotations as meta information
   * @return The same instance of this {@link BckndInternalResourceGroup} class
   */
  @Nonnull
  public BckndInternalResourceGroup annotations(
      @Nullable final List<BckndInternalResourceGroupAnnotation> annotations) {
    this.annotations = annotations;
    return this;
  }

  /**
   * Add one annotations instance to this {@link BckndInternalResourceGroup}.
   *
   * @param annotationsItem The annotations that should be added
   * @return The same instance of type {@link BckndInternalResourceGroup}
   */
  @Nonnull
  public BckndInternalResourceGroup addAnnotationsItem(
      @Nonnull final BckndInternalResourceGroupAnnotation annotationsItem) {
    if (this.annotations == null) {
      this.annotations = new ArrayList<>();
    }
    this.annotations.add(annotationsItem);
    return this;
  }

  /**
   * Arbitrary annotations as meta information
   *
   * @return annotations The annotations of this {@link BckndInternalResourceGroup} instance.
   */
  @Nonnull
  public List<BckndInternalResourceGroupAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Set the annotations of this {@link BckndInternalResourceGroup} instance.
   *
   * @param annotations Arbitrary annotations as meta information
   */
  public void setAnnotations(
      @Nullable final List<BckndInternalResourceGroupAnnotation> annotations) {
    this.annotations = annotations;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndInternalResourceGroup}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndInternalResourceGroup}
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
          "BckndInternalResourceGroup has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndInternalResourceGroup} instance. If the map
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
    final BckndInternalResourceGroup bckndInternalResourceGroup = (BckndInternalResourceGroup) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndInternalResourceGroup.cloudSdkCustomFields)
        && Objects.equals(this.resourceGroupId, bckndInternalResourceGroup.resourceGroupId)
        && Objects.equals(this.tenantId, bckndInternalResourceGroup.tenantId)
        && Objects.equals(this.zoneId, bckndInternalResourceGroup.zoneId)
        && Objects.equals(this.createdAt, bckndInternalResourceGroup.createdAt)
        && Objects.equals(this.labels, bckndInternalResourceGroup.labels)
        && Objects.equals(this.status, bckndInternalResourceGroup.status)
        && Objects.equals(this.statusMessage, bckndInternalResourceGroup.statusMessage)
        && Objects.equals(this.annotations, bckndInternalResourceGroup.annotations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        resourceGroupId,
        tenantId,
        zoneId,
        createdAt,
        labels,
        status,
        statusMessage,
        annotations,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndInternalResourceGroup {\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
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
   * BckndInternalResourceGroup} instance with all required arguments.
   */
  public static Builder create() {
    return (resourceGroupId) ->
        (createdAt) ->
            (status) ->
                new BckndInternalResourceGroup()
                    .resourceGroupId(resourceGroupId)
                    .createdAt(createdAt)
                    .status(status);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the resourceGroupId of this {@link BckndInternalResourceGroup} instance.
     *
     * @param resourceGroupId resource group id
     * @return The BckndInternalResourceGroup builder.
     */
    Builder1 resourceGroupId(@Nonnull final String resourceGroupId);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the createdAt of this {@link BckndInternalResourceGroup} instance.
     *
     * @param createdAt Timestamp of resource group creation
     * @return The BckndInternalResourceGroup builder.
     */
    Builder2 createdAt(@Nonnull final OffsetDateTime createdAt);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the status of this {@link BckndInternalResourceGroup} instance.
     *
     * @param status aggregated status of the onboarding process
     * @return The BckndInternalResourceGroup instance.
     */
    BckndInternalResourceGroup status(@Nonnull final StatusEnum status);
  }
}
