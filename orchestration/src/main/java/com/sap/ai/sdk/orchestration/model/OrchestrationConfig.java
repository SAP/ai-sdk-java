/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
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

/** OrchestrationConfig */
@Beta // CHECKSTYLE:OFF
public class OrchestrationConfig
// CHECKSTYLE:ON
{
  @JsonProperty("module_configurations")
  private ModuleConfigs moduleConfigurations;

  @JsonProperty("stream")
  private Boolean stream = false;

  @JsonProperty("stream_options")
  private GlobalStreamOptions streamOptions;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for OrchestrationConfig. */
  protected OrchestrationConfig() {}

  /**
   * Set the moduleConfigurations of this {@link OrchestrationConfig} instance and return the same
   * instance.
   *
   * @param moduleConfigurations The moduleConfigurations of this {@link OrchestrationConfig}
   * @return The same instance of this {@link OrchestrationConfig} class
   */
  @Nonnull
  public OrchestrationConfig moduleConfigurations(
      @Nonnull final ModuleConfigs moduleConfigurations) {
    this.moduleConfigurations = moduleConfigurations;
    return this;
  }

  /**
   * Get moduleConfigurations
   *
   * @return moduleConfigurations The moduleConfigurations of this {@link OrchestrationConfig}
   *     instance.
   */
  @Nonnull
  public ModuleConfigs getModuleConfigurations() {
    return moduleConfigurations;
  }

  /**
   * Set the moduleConfigurations of this {@link OrchestrationConfig} instance.
   *
   * @param moduleConfigurations The moduleConfigurations of this {@link OrchestrationConfig}
   */
  public void setModuleConfigurations(@Nonnull final ModuleConfigs moduleConfigurations) {
    this.moduleConfigurations = moduleConfigurations;
  }

  /**
   * Set the stream of this {@link OrchestrationConfig} instance and return the same instance.
   *
   * @param stream If true, the response will be streamed back to the client
   * @return The same instance of this {@link OrchestrationConfig} class
   */
  @Nonnull
  public OrchestrationConfig stream(@Nullable final Boolean stream) {
    this.stream = stream;
    return this;
  }

  /**
   * If true, the response will be streamed back to the client
   *
   * @return stream The stream of this {@link OrchestrationConfig} instance.
   */
  @Nonnull
  public Boolean isStream() {
    return stream;
  }

  /**
   * Set the stream of this {@link OrchestrationConfig} instance.
   *
   * @param stream If true, the response will be streamed back to the client
   */
  public void setStream(@Nullable final Boolean stream) {
    this.stream = stream;
  }

  /**
   * Set the streamOptions of this {@link OrchestrationConfig} instance and return the same
   * instance.
   *
   * @param streamOptions The streamOptions of this {@link OrchestrationConfig}
   * @return The same instance of this {@link OrchestrationConfig} class
   */
  @Nonnull
  public OrchestrationConfig streamOptions(@Nullable final GlobalStreamOptions streamOptions) {
    this.streamOptions = streamOptions;
    return this;
  }

  /**
   * Get streamOptions
   *
   * @return streamOptions The streamOptions of this {@link OrchestrationConfig} instance.
   */
  @Nonnull
  public GlobalStreamOptions getStreamOptions() {
    return streamOptions;
  }

  /**
   * Set the streamOptions of this {@link OrchestrationConfig} instance.
   *
   * @param streamOptions The streamOptions of this {@link OrchestrationConfig}
   */
  public void setStreamOptions(@Nullable final GlobalStreamOptions streamOptions) {
    this.streamOptions = streamOptions;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link OrchestrationConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link OrchestrationConfig} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "OrchestrationConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link OrchestrationConfig} instance. If the map
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
    final OrchestrationConfig orchestrationConfig = (OrchestrationConfig) o;
    return Objects.equals(this.cloudSdkCustomFields, orchestrationConfig.cloudSdkCustomFields)
        && Objects.equals(this.moduleConfigurations, orchestrationConfig.moduleConfigurations)
        && Objects.equals(this.stream, orchestrationConfig.stream)
        && Objects.equals(this.streamOptions, orchestrationConfig.streamOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(moduleConfigurations, stream, streamOptions, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class OrchestrationConfig {\n");
    sb.append("    moduleConfigurations: ")
        .append(toIndentedString(moduleConfigurations))
        .append("\n");
    sb.append("    stream: ").append(toIndentedString(stream)).append("\n");
    sb.append("    streamOptions: ").append(toIndentedString(streamOptions)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link OrchestrationConfig}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (moduleConfigurations) ->
        new OrchestrationConfig().moduleConfigurations(moduleConfigurations);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the moduleConfigurations of this {@link OrchestrationConfig} instance.
     *
     * @param moduleConfigurations The moduleConfigurations of this {@link OrchestrationConfig}
     * @return The OrchestrationConfig instance.
     */
    OrchestrationConfig moduleConfigurations(@Nonnull final ModuleConfigs moduleConfigurations);
  }
}
