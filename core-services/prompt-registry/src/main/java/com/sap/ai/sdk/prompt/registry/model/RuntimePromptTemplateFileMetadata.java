/*
 * Prompt Registry API
 * Prompt Storage service for Design time & Runtime prompt templates.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.prompt.registry.model;

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

/** RuntimePromptTemplateFileMetadata */
// CHECKSTYLE:OFF
public class RuntimePromptTemplateFileMetadata
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("version")
  private String version;

  @JsonProperty("scenario")
  private String scenario;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RuntimePromptTemplateFileMetadata. */
  protected RuntimePromptTemplateFileMetadata() {}

  /**
   * Set the name of this {@link RuntimePromptTemplateFileMetadata} instance and return the same
   * instance.
   *
   * @param name The name of this {@link RuntimePromptTemplateFileMetadata}
   * @return The same instance of this {@link RuntimePromptTemplateFileMetadata} class
   */
  @Nonnull
  public RuntimePromptTemplateFileMetadata name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name The name of this {@link RuntimePromptTemplateFileMetadata} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link RuntimePromptTemplateFileMetadata} instance.
   *
   * @param name The name of this {@link RuntimePromptTemplateFileMetadata}
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the version of this {@link RuntimePromptTemplateFileMetadata} instance and return the same
   * instance.
   *
   * @param version The version of this {@link RuntimePromptTemplateFileMetadata}
   * @return The same instance of this {@link RuntimePromptTemplateFileMetadata} class
   */
  @Nonnull
  public RuntimePromptTemplateFileMetadata version(@Nullable final String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   *
   * @return version The version of this {@link RuntimePromptTemplateFileMetadata} instance.
   */
  @Nonnull
  public String getVersion() {
    return version;
  }

  /**
   * Set the version of this {@link RuntimePromptTemplateFileMetadata} instance.
   *
   * @param version The version of this {@link RuntimePromptTemplateFileMetadata}
   */
  public void setVersion(@Nullable final String version) {
    this.version = version;
  }

  /**
   * Set the scenario of this {@link RuntimePromptTemplateFileMetadata} instance and return the same
   * instance.
   *
   * @param scenario The scenario of this {@link RuntimePromptTemplateFileMetadata}
   * @return The same instance of this {@link RuntimePromptTemplateFileMetadata} class
   */
  @Nonnull
  public RuntimePromptTemplateFileMetadata scenario(@Nullable final String scenario) {
    this.scenario = scenario;
    return this;
  }

  /**
   * Get scenario
   *
   * @return scenario The scenario of this {@link RuntimePromptTemplateFileMetadata} instance.
   */
  @Nonnull
  public String getScenario() {
    return scenario;
  }

  /**
   * Set the scenario of this {@link RuntimePromptTemplateFileMetadata} instance.
   *
   * @param scenario The scenario of this {@link RuntimePromptTemplateFileMetadata}
   */
  public void setScenario(@Nullable final String scenario) {
    this.scenario = scenario;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * RuntimePromptTemplateFileMetadata}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RuntimePromptTemplateFileMetadata}
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
          "RuntimePromptTemplateFileMetadata has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link RuntimePromptTemplateFileMetadata} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (version != null) declaredFields.put("version", version);
    if (scenario != null) declaredFields.put("scenario", scenario);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link RuntimePromptTemplateFileMetadata} instance. If
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
    final RuntimePromptTemplateFileMetadata runtimePromptTemplateFileMetadata =
        (RuntimePromptTemplateFileMetadata) o;
    return Objects.equals(
            this.cloudSdkCustomFields, runtimePromptTemplateFileMetadata.cloudSdkCustomFields)
        && Objects.equals(this.name, runtimePromptTemplateFileMetadata.name)
        && Objects.equals(this.version, runtimePromptTemplateFileMetadata.version)
        && Objects.equals(this.scenario, runtimePromptTemplateFileMetadata.scenario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, scenario, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RuntimePromptTemplateFileMetadata {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    scenario: ").append(toIndentedString(scenario)).append("\n");
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

  /** Create a new {@link RuntimePromptTemplateFileMetadata} instance. No arguments are required. */
  public static RuntimePromptTemplateFileMetadata create() {
    return new RuntimePromptTemplateFileMetadata();
  }
}
