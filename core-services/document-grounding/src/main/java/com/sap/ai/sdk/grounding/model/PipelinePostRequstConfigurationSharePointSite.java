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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** PipelinePostRequstConfigurationSharePointSite */
// CHECKSTYLE:OFF
public class PipelinePostRequstConfigurationSharePointSite
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("includePaths")
  private List<String> includePaths = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PipelinePostRequstConfigurationSharePointSite. */
  protected PipelinePostRequstConfigurationSharePointSite() {}

  /**
   * Set the name of this {@link PipelinePostRequstConfigurationSharePointSite} instance and return
   * the same instance.
   *
   * @param name The name of this {@link PipelinePostRequstConfigurationSharePointSite}
   * @return The same instance of this {@link PipelinePostRequstConfigurationSharePointSite} class
   */
  @Nonnull
  public PipelinePostRequstConfigurationSharePointSite name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name The name of this {@link PipelinePostRequstConfigurationSharePointSite} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link PipelinePostRequstConfigurationSharePointSite} instance.
   *
   * @param name The name of this {@link PipelinePostRequstConfigurationSharePointSite}
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the includePaths of this {@link PipelinePostRequstConfigurationSharePointSite} instance and
   * return the same instance.
   *
   * @param includePaths The includePaths of this {@link
   *     PipelinePostRequstConfigurationSharePointSite}
   * @return The same instance of this {@link PipelinePostRequstConfigurationSharePointSite} class
   */
  @Nonnull
  public PipelinePostRequstConfigurationSharePointSite includePaths(
      @Nullable final List<String> includePaths) {
    this.includePaths = includePaths;
    return this;
  }

  /**
   * Add one includePaths instance to this {@link PipelinePostRequstConfigurationSharePointSite}.
   *
   * @param includePathsItem The includePaths that should be added
   * @return The same instance of type {@link PipelinePostRequstConfigurationSharePointSite}
   */
  @Nonnull
  public PipelinePostRequstConfigurationSharePointSite addIncludePathsItem(
      @Nonnull final String includePathsItem) {
    if (this.includePaths == null) {
      this.includePaths = new ArrayList<>();
    }
    this.includePaths.add(includePathsItem);
    return this;
  }

  /**
   * Get includePaths
   *
   * @return includePaths The includePaths of this {@link
   *     PipelinePostRequstConfigurationSharePointSite} instance.
   */
  @Nonnull
  public List<String> getIncludePaths() {
    return includePaths;
  }

  /**
   * Set the includePaths of this {@link PipelinePostRequstConfigurationSharePointSite} instance.
   *
   * @param includePaths The includePaths of this {@link
   *     PipelinePostRequstConfigurationSharePointSite}
   */
  public void setIncludePaths(@Nullable final List<String> includePaths) {
    this.includePaths = includePaths;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * PipelinePostRequstConfigurationSharePointSite}.
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
   * PipelinePostRequstConfigurationSharePointSite} instance.
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
          "PipelinePostRequstConfigurationSharePointSite has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link PipelinePostRequstConfigurationSharePointSite}
   * instance including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (includePaths != null) declaredFields.put("includePaths", includePaths);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link PipelinePostRequstConfigurationSharePointSite}
   * instance. If the map previously contained a mapping for the key, the old value is replaced by
   * the specified value.
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
    final PipelinePostRequstConfigurationSharePointSite
        pipelinePostRequstConfigurationSharePointSite =
            (PipelinePostRequstConfigurationSharePointSite) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            pipelinePostRequstConfigurationSharePointSite.cloudSdkCustomFields)
        && Objects.equals(this.name, pipelinePostRequstConfigurationSharePointSite.name)
        && Objects.equals(
            this.includePaths, pipelinePostRequstConfigurationSharePointSite.includePaths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, includePaths, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PipelinePostRequstConfigurationSharePointSite {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    includePaths: ").append(toIndentedString(includePaths)).append("\n");
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
   * Create a new {@link PipelinePostRequstConfigurationSharePointSite} instance. No arguments are
   * required.
   */
  public static PipelinePostRequstConfigurationSharePointSite create() {
    return new PipelinePostRequstConfigurationSharePointSite();
  }
}
