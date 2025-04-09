/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

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

/** PipelineConfiguration */
// CHECKSTYLE:OFF
public class PipelineConfiguration
// CHECKSTYLE:ON
{
  @JsonProperty("destination")
  private String destination;

  @JsonProperty("sharePoint")
  private PipelineConfigurationSharePoint sharePoint;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PipelineConfiguration. */
  protected PipelineConfiguration() {}

  /**
   * Set the destination of this {@link PipelineConfiguration} instance and return the same
   * instance.
   *
   * @param destination The destination of this {@link PipelineConfiguration}
   * @return The same instance of this {@link PipelineConfiguration} class
   */
  @Nonnull
  public PipelineConfiguration destination(@Nullable final String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   *
   * @return destination The destination of this {@link PipelineConfiguration} instance.
   */
  @Nonnull
  public String getDestination() {
    return destination;
  }

  /**
   * Set the destination of this {@link PipelineConfiguration} instance.
   *
   * @param destination The destination of this {@link PipelineConfiguration}
   */
  public void setDestination(@Nullable final String destination) {
    this.destination = destination;
  }

  /**
   * Set the sharePoint of this {@link PipelineConfiguration} instance and return the same instance.
   *
   * @param sharePoint The sharePoint of this {@link PipelineConfiguration}
   * @return The same instance of this {@link PipelineConfiguration} class
   */
  @Nonnull
  public PipelineConfiguration sharePoint(
      @Nullable final PipelineConfigurationSharePoint sharePoint) {
    this.sharePoint = sharePoint;
    return this;
  }

  /**
   * Get sharePoint
   *
   * @return sharePoint The sharePoint of this {@link PipelineConfiguration} instance.
   */
  @Nonnull
  public PipelineConfigurationSharePoint getSharePoint() {
    return sharePoint;
  }

  /**
   * Set the sharePoint of this {@link PipelineConfiguration} instance.
   *
   * @param sharePoint The sharePoint of this {@link PipelineConfiguration}
   */
  public void setSharePoint(@Nullable final PipelineConfigurationSharePoint sharePoint) {
    this.sharePoint = sharePoint;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PipelineConfiguration}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PipelineConfiguration} instance.
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
          "PipelineConfiguration has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link PipelineConfiguration} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (destination != null) declaredFields.put("destination", destination);
    if (sharePoint != null) declaredFields.put("sharePoint", sharePoint);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link PipelineConfiguration} instance. If the map
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
    final PipelineConfiguration pipelineConfiguration = (PipelineConfiguration) o;
    return Objects.equals(this.cloudSdkCustomFields, pipelineConfiguration.cloudSdkCustomFields)
        && Objects.equals(this.destination, pipelineConfiguration.destination)
        && Objects.equals(this.sharePoint, pipelineConfiguration.sharePoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destination, sharePoint, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PipelineConfiguration {\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    sharePoint: ").append(toIndentedString(sharePoint)).append("\n");
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

  /** Create a new {@link PipelineConfiguration} instance. No arguments are required. */
  public static PipelineConfiguration create() {
    return new PipelineConfiguration();
  }
}
