/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.29.3
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

/** OrchestrationConfig */
@JsonPropertyOrder({
  OrchestrationConfig.JSON_PROPERTY_MODULE_CONFIGURATIONS,
  OrchestrationConfig.JSON_PROPERTY_STREAM,
  OrchestrationConfig.JSON_PROPERTY_STREAM_OPTIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T18:02:22.585601+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class OrchestrationConfig {
  public static final String JSON_PROPERTY_MODULE_CONFIGURATIONS = "module_configurations";
  private ModuleConfigs moduleConfigurations;

  public static final String JSON_PROPERTY_STREAM = "stream";
  private Boolean stream = false;

  public static final String JSON_PROPERTY_STREAM_OPTIONS = "stream_options";
  private GlobalStreamOptions streamOptions;

  public OrchestrationConfig() {}

  public OrchestrationConfig moduleConfigurations(ModuleConfigs moduleConfigurations) {

    this.moduleConfigurations = moduleConfigurations;
    return this;
  }

  /**
   * Get moduleConfigurations
   *
   * @return moduleConfigurations
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MODULE_CONFIGURATIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public ModuleConfigs getModuleConfigurations() {
    return moduleConfigurations;
  }

  @JsonProperty(JSON_PROPERTY_MODULE_CONFIGURATIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setModuleConfigurations(ModuleConfigs moduleConfigurations) {
    this.moduleConfigurations = moduleConfigurations;
  }

  public OrchestrationConfig stream(Boolean stream) {

    this.stream = stream;
    return this;
  }

  /**
   * If true, the response will be streamed back to the client
   *
   * @return stream
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STREAM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getStream() {
    return stream;
  }

  @JsonProperty(JSON_PROPERTY_STREAM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStream(Boolean stream) {
    this.stream = stream;
  }

  public OrchestrationConfig streamOptions(GlobalStreamOptions streamOptions) {

    this.streamOptions = streamOptions;
    return this;
  }

  /**
   * Get streamOptions
   *
   * @return streamOptions
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STREAM_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public GlobalStreamOptions getStreamOptions() {
    return streamOptions;
  }

  @JsonProperty(JSON_PROPERTY_STREAM_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStreamOptions(GlobalStreamOptions streamOptions) {
    this.streamOptions = streamOptions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrchestrationConfig orchestrationConfig = (OrchestrationConfig) o;
    return Objects.equals(this.moduleConfigurations, orchestrationConfig.moduleConfigurations)
        && Objects.equals(this.stream, orchestrationConfig.stream)
        && Objects.equals(this.streamOptions, orchestrationConfig.streamOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(moduleConfigurations, stream, streamOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrchestrationConfig {\n");
    sb.append("    moduleConfigurations: ")
        .append(toIndentedString(moduleConfigurations))
        .append("\n");
    sb.append("    stream: ").append(toIndentedString(stream)).append("\n");
    sb.append("    streamOptions: ").append(toIndentedString(streamOptions)).append("\n");
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

  public static class Builder {

    private OrchestrationConfig instance;

    public Builder() {
      this(new OrchestrationConfig());
    }

    protected Builder(OrchestrationConfig instance) {
      this.instance = instance;
    }

    public OrchestrationConfig.Builder moduleConfigurations(ModuleConfigs moduleConfigurations) {
      this.instance.moduleConfigurations = moduleConfigurations;
      return this;
    }

    public OrchestrationConfig.Builder stream(Boolean stream) {
      this.instance.stream = stream;
      return this;
    }

    public OrchestrationConfig.Builder streamOptions(GlobalStreamOptions streamOptions) {
      this.instance.streamOptions = streamOptions;
      return this;
    }

    /**
     * returns a built OrchestrationConfig instance.
     *
     * <p>The builder is not reusable.
     */
    public OrchestrationConfig build() {
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

  /** Create a builder with no initialized field. */
  public static OrchestrationConfig.Builder builder() {
    return new OrchestrationConfig.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public OrchestrationConfig.Builder toBuilder() {
    return new OrchestrationConfig.Builder()
        .moduleConfigurations(getModuleConfigurations()).stream(getStream())
            .streamOptions(getStreamOptions());
  }
}
