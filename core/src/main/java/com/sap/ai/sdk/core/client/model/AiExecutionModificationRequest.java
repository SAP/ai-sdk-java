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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request object for changing the target status of an execution (currently only STOPPED is
 * supported)
 */
// CHECKSTYLE:OFF
public class AiExecutionModificationRequest
// CHECKSTYLE:ON
{
  /** Desired target status of the execution (currently only STOPPED is supported) */
  public enum TargetStatusEnum {
    /** The STOPPED option of this AiExecutionModificationRequest */
    STOPPED("STOPPED"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AiExecutionModificationRequest */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TargetStatusEnum(String value) {
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
     * @return The enum value of type AiExecutionModificationRequest
     */
    @JsonCreator
    @Nonnull
    public static TargetStatusEnum fromValue(@Nonnull final String value) {
      for (TargetStatusEnum b : TargetStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("targetStatus")
  private TargetStatusEnum targetStatus;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected AiExecutionModificationRequest() {}

  /**
   * Set the targetStatus of this {@link AiExecutionModificationRequest} instance and return the
   * same instance.
   *
   * @param targetStatus Desired target status of the execution (currently only STOPPED is
   *     supported)
   * @return The same instance of this {@link AiExecutionModificationRequest} class
   */
  @Nonnull
  public AiExecutionModificationRequest targetStatus(@Nonnull final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
    return this;
  }

  /**
   * Desired target status of the execution (currently only STOPPED is supported)
   *
   * @return targetStatus The targetStatus of this {@link AiExecutionModificationRequest} instance.
   */
  @Nonnull
  public TargetStatusEnum getTargetStatus() {
    return targetStatus;
  }

  /**
   * Set the targetStatus of this {@link AiExecutionModificationRequest} instance.
   *
   * @param targetStatus Desired target status of the execution (currently only STOPPED is
   *     supported)
   */
  public void setTargetStatus(@Nonnull final TargetStatusEnum targetStatus) {
    this.targetStatus = targetStatus;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiExecutionModificationRequest}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiExecutionModificationRequest}
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
          "AiExecutionModificationRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiExecutionModificationRequest} instance. If the
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
    final AiExecutionModificationRequest aiExecutionModificationRequest =
        (AiExecutionModificationRequest) o;
    return Objects.equals(
            this.cloudSdkCustomFields, aiExecutionModificationRequest.cloudSdkCustomFields)
        && Objects.equals(this.targetStatus, aiExecutionModificationRequest.targetStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetStatus, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiExecutionModificationRequest {\n");
    sb.append("    targetStatus: ").append(toIndentedString(targetStatus)).append("\n");
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
   * AiExecutionModificationRequest} instance with all required arguments.
   */
  public static Builder create() {
    return (targetStatus) -> new AiExecutionModificationRequest().targetStatus(targetStatus);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the targetStatus of this {@link AiExecutionModificationRequest} instance.
     *
     * @param targetStatus Desired target status of the execution (currently only STOPPED is
     *     supported)
     * @return The AiExecutionModificationRequest instance.
     */
    AiExecutionModificationRequest targetStatus(@Nonnull final TargetStatusEnum targetStatus);
  }
}
