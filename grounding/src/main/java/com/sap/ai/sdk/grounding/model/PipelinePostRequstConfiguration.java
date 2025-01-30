/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 * The version of the OpenAPI document: 0.1.0
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
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** PipelinePostRequstConfiguration */
@Beta // CHECKSTYLE:OFF
public class PipelinePostRequstConfiguration
// CHECKSTYLE:ON
{
  @JsonProperty("destination")
  private String destination;

  @JsonProperty("sharePoint")
  private PipelinePostRequstConfigurationSharePoint sharePoint;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PipelinePostRequstConfiguration. */
  protected PipelinePostRequstConfiguration() {
    super();
  }

  /**
   * Set the destination of this {@link PipelinePostRequstConfiguration} instance and return the
   * same instance.
   *
   * @param destination The destination of this {@link PipelinePostRequstConfiguration}
   * @return The same instance of this {@link PipelinePostRequstConfiguration} class
   */
  @Nonnull
  public PipelinePostRequstConfiguration destination(@Nonnull final String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   *
   * @return destination The destination of this {@link PipelinePostRequstConfiguration} instance.
   */
  @Nonnull
  public String getDestination() {
    return destination;
  }

  /**
   * Set the destination of this {@link PipelinePostRequstConfiguration} instance.
   *
   * @param destination The destination of this {@link PipelinePostRequstConfiguration}
   */
  public void setDestination(@Nonnull final String destination) {
    this.destination = destination;
  }

  /**
   * Set the sharePoint of this {@link PipelinePostRequstConfiguration} instance and return the same
   * instance.
   *
   * @param sharePoint The sharePoint of this {@link PipelinePostRequstConfiguration}
   * @return The same instance of this {@link PipelinePostRequstConfiguration} class
   */
  @Nonnull
  public PipelinePostRequstConfiguration sharePoint(
      @Nullable final PipelinePostRequstConfigurationSharePoint sharePoint) {
    this.sharePoint = sharePoint;
    return this;
  }

  /**
   * Get sharePoint
   *
   * @return sharePoint The sharePoint of this {@link PipelinePostRequstConfiguration} instance.
   */
  @Nonnull
  public PipelinePostRequstConfigurationSharePoint getSharePoint() {
    return sharePoint;
  }

  /**
   * Set the sharePoint of this {@link PipelinePostRequstConfiguration} instance.
   *
   * @param sharePoint The sharePoint of this {@link PipelinePostRequstConfiguration}
   */
  public void setSharePoint(@Nullable final PipelinePostRequstConfigurationSharePoint sharePoint) {
    this.sharePoint = sharePoint;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PipelinePostRequstConfiguration}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PipelinePostRequstConfiguration}
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
          "PipelinePostRequstConfiguration has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link PipelinePostRequstConfiguration} instance. If the
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
    final PipelinePostRequstConfiguration pipelinePostRequstConfiguration =
        (PipelinePostRequstConfiguration) o;
    return Objects.equals(
            this.cloudSdkCustomFields, pipelinePostRequstConfiguration.cloudSdkCustomFields)
        && Objects.equals(this.destination, pipelinePostRequstConfiguration.destination)
        && Objects.equals(this.sharePoint, pipelinePostRequstConfiguration.sharePoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destination, sharePoint, cloudSdkCustomFields, super.hashCode());
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PipelinePostRequstConfiguration {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * PipelinePostRequstConfiguration} instance with all required arguments.
   */
  public static Builder create() {
    return (destination) -> new PipelinePostRequstConfiguration().destination(destination);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the destination of this {@link PipelinePostRequstConfiguration} instance.
     *
     * @param destination The destination of this {@link PipelinePostRequstConfiguration}
     * @return The PipelinePostRequstConfiguration instance.
     */
    PipelinePostRequstConfiguration destination(@Nonnull final String destination);
  }
}
