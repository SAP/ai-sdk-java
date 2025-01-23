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

/** PipelineConfigurationSharePoint */
@Beta // CHECKSTYLE:OFF
public class PipelineConfigurationSharePoint
// CHECKSTYLE:ON
{
  @JsonProperty("site")
  private PipelineConfigurationSharePointSite site;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PipelineConfigurationSharePoint. */
  protected PipelineConfigurationSharePoint() {}

  /**
   * Set the site of this {@link PipelineConfigurationSharePoint} instance and return the same
   * instance.
   *
   * @param site The site of this {@link PipelineConfigurationSharePoint}
   * @return The same instance of this {@link PipelineConfigurationSharePoint} class
   */
  @Nonnull
  public PipelineConfigurationSharePoint site(
      @Nullable final PipelineConfigurationSharePointSite site) {
    this.site = site;
    return this;
  }

  /**
   * Get site
   *
   * @return site The site of this {@link PipelineConfigurationSharePoint} instance.
   */
  @Nonnull
  public PipelineConfigurationSharePointSite getSite() {
    return site;
  }

  /**
   * Set the site of this {@link PipelineConfigurationSharePoint} instance.
   *
   * @param site The site of this {@link PipelineConfigurationSharePoint}
   */
  public void setSite(@Nullable final PipelineConfigurationSharePointSite site) {
    this.site = site;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PipelineConfigurationSharePoint}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PipelineConfigurationSharePoint}
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
          "PipelineConfigurationSharePoint has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link PipelineConfigurationSharePoint} instance. If the
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
    final PipelineConfigurationSharePoint pipelineConfigurationSharePoint =
        (PipelineConfigurationSharePoint) o;
    return Objects.equals(
            this.cloudSdkCustomFields, pipelineConfigurationSharePoint.cloudSdkCustomFields)
        && Objects.equals(this.site, pipelineConfigurationSharePoint.site);
  }

  @Override
  public int hashCode() {
    return Objects.hash(site, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PipelineConfigurationSharePoint {\n");
    sb.append("    site: ").append(toIndentedString(site)).append("\n");
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

  /** Create a new {@link PipelineConfigurationSharePoint} instance. No arguments are required. */
  public static PipelineConfigurationSharePoint create() {
    return new PipelineConfigurationSharePoint();
  }
}
