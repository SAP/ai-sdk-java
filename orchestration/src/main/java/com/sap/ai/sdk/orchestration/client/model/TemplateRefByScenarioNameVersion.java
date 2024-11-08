/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

/** TemplateRefByScenarioNameVersion */
@JsonPropertyOrder({
  TemplateRefByScenarioNameVersion.JSON_PROPERTY_SCENARIO,
  TemplateRefByScenarioNameVersion.JSON_PROPERTY_NAME,
  TemplateRefByScenarioNameVersion.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class TemplateRefByScenarioNameVersion {
  public static final String JSON_PROPERTY_SCENARIO = "scenario";
  public static final String JSON_PROPERTY_NAME = "name";
  public static final String JSON_PROPERTY_VERSION = "version";
  private String scenario;
  private String name;
  private String version;

  public TemplateRefByScenarioNameVersion() {}

  /** Create a builder with no initialized field. */
  public static TemplateRefByScenarioNameVersion.Builder builder() {
    return new TemplateRefByScenarioNameVersion.Builder();
  }

  public TemplateRefByScenarioNameVersion scenario(String scenario) {

    this.scenario = scenario;
    return this;
  }

  /**
   * Scenario name
   *
   * @return scenario
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SCENARIO)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getScenario() {
    return scenario;
  }

  @JsonProperty(JSON_PROPERTY_SCENARIO)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setScenario(String scenario) {
    this.scenario = scenario;
  }

  public TemplateRefByScenarioNameVersion name(String name) {

    this.name = name;
    return this;
  }

  /**
   * Name of the template
   *
   * @return name
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(String name) {
    this.name = name;
  }

  public TemplateRefByScenarioNameVersion version(String version) {

    this.version = version;
    return this;
  }

  /**
   * Version of the template
   *
   * @return version
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateRefByScenarioNameVersion templateRefByScenarioNameVersion =
        (TemplateRefByScenarioNameVersion) o;
    return Objects.equals(this.scenario, templateRefByScenarioNameVersion.scenario)
        && Objects.equals(this.name, templateRefByScenarioNameVersion.name)
        && Objects.equals(this.version, templateRefByScenarioNameVersion.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scenario, name, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateRefByScenarioNameVersion {\n");
    sb.append("    scenario: ").append(toIndentedString(scenario)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /** Create a builder with a shallow copy of this instance. */
  public TemplateRefByScenarioNameVersion.Builder toBuilder() {
    return new TemplateRefByScenarioNameVersion.Builder()
        .scenario(getScenario())
        .name(getName())
        .version(getVersion());
  }

  public static class Builder {

    private TemplateRefByScenarioNameVersion instance;

    public Builder() {
      this(new TemplateRefByScenarioNameVersion());
    }

    protected Builder(TemplateRefByScenarioNameVersion instance) {
      this.instance = instance;
    }

    public TemplateRefByScenarioNameVersion.Builder scenario(String scenario) {
      this.instance.scenario = scenario;
      return this;
    }

    public TemplateRefByScenarioNameVersion.Builder name(String name) {
      this.instance.name = name;
      return this;
    }

    public TemplateRefByScenarioNameVersion.Builder version(String version) {
      this.instance.version = version;
      return this;
    }

    /**
     * returns a built TemplateRefByScenarioNameVersion instance.
     *
     * <p>The builder is not reusable.
     */
    public TemplateRefByScenarioNameVersion build() {
      try {
        return this.instance;
      } finally {
        // ensure that this.instance is not reused
        this.instance = null;
      }
    }

    @Override
    public String toString() {
      return getClass() + "=(" + instance + ")";
    }
  }
}
