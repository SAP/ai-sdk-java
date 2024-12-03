/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.36.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** TemplateRefByScenarioNameVersion */
@Beta // CHECKSTYLE:OFF
public class TemplateRefByScenarioNameVersion implements TemplateRefTemplateRef
// CHECKSTYLE:ON
{
  @JsonProperty("scenario")
  private String scenario;

  @JsonProperty("name")
  private String name;

  @JsonProperty("version")
  private String version;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TemplateRefByScenarioNameVersion. */
  protected TemplateRefByScenarioNameVersion() {}

  /**
   * Set the scenario of this {@link TemplateRefByScenarioNameVersion} instance and return the same
   * instance.
   *
   * @param scenario Scenario name
   * @return The same instance of this {@link TemplateRefByScenarioNameVersion} class
   */
  @Nonnull
  public TemplateRefByScenarioNameVersion scenario(@Nonnull final String scenario) {
    this.scenario = scenario;
    return this;
  }

  /**
   * Scenario name
   *
   * @return scenario The scenario of this {@link TemplateRefByScenarioNameVersion} instance.
   */
  @Nonnull
  public String getScenario() {
    return scenario;
  }

  /**
   * Set the scenario of this {@link TemplateRefByScenarioNameVersion} instance.
   *
   * @param scenario Scenario name
   */
  public void setScenario(@Nonnull final String scenario) {
    this.scenario = scenario;
  }

  /**
   * Set the name of this {@link TemplateRefByScenarioNameVersion} instance and return the same
   * instance.
   *
   * @param name Name of the template
   * @return The same instance of this {@link TemplateRefByScenarioNameVersion} class
   */
  @Nonnull
  public TemplateRefByScenarioNameVersion name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the template
   *
   * @return name The name of this {@link TemplateRefByScenarioNameVersion} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link TemplateRefByScenarioNameVersion} instance.
   *
   * @param name Name of the template
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the version of this {@link TemplateRefByScenarioNameVersion} instance and return the same
   * instance.
   *
   * @param version Version of the template
   * @return The same instance of this {@link TemplateRefByScenarioNameVersion} class
   */
  @Nonnull
  public TemplateRefByScenarioNameVersion version(@Nonnull final String version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the template
   *
   * @return version The version of this {@link TemplateRefByScenarioNameVersion} instance.
   */
  @Nonnull
  public String getVersion() {
    return version;
  }

  /**
   * Set the version of this {@link TemplateRefByScenarioNameVersion} instance.
   *
   * @param version Version of the template
   */
  public void setVersion(@Nonnull final String version) {
    this.version = version;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TemplateRefByScenarioNameVersion}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TemplateRefByScenarioNameVersion}
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
          "TemplateRefByScenarioNameVersion has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TemplateRefByScenarioNameVersion} instance. If
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
    final TemplateRefByScenarioNameVersion templateRefByScenarioNameVersion =
        (TemplateRefByScenarioNameVersion) o;
    return Objects.equals(
            this.cloudSdkCustomFields, templateRefByScenarioNameVersion.cloudSdkCustomFields)
        && Objects.equals(this.scenario, templateRefByScenarioNameVersion.scenario)
        && Objects.equals(this.name, templateRefByScenarioNameVersion.name)
        && Objects.equals(this.version, templateRefByScenarioNameVersion.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scenario, name, version, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TemplateRefByScenarioNameVersion {\n");
    sb.append("    scenario: ").append(toIndentedString(scenario)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
   * TemplateRefByScenarioNameVersion} instance with all required arguments.
   */
  public static Builder create() {
    return (scenario) ->
        (name) ->
            (version) ->
                new TemplateRefByScenarioNameVersion()
                    .scenario(scenario)
                    .name(name)
                    .version(version);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the scenario of this {@link TemplateRefByScenarioNameVersion} instance.
     *
     * @param scenario Scenario name
     * @return The TemplateRefByScenarioNameVersion builder.
     */
    Builder1 scenario(@Nonnull final String scenario);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the name of this {@link TemplateRefByScenarioNameVersion} instance.
     *
     * @param name Name of the template
     * @return The TemplateRefByScenarioNameVersion builder.
     */
    Builder2 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the version of this {@link TemplateRefByScenarioNameVersion} instance.
     *
     * @param version Version of the template
     * @return The TemplateRefByScenarioNameVersion instance.
     */
    TemplateRefByScenarioNameVersion version(@Nonnull final String version);
  }
}
