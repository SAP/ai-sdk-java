/*
 * Internal Orchestration Service API
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** EmbeddingsOrchestrationConfig */
// CHECKSTYLE:OFF
public class EmbeddingsOrchestrationConfig
// CHECKSTYLE:ON
{
  @JsonProperty("modules")
  private EmbeddingsModuleConfigs modules;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for EmbeddingsOrchestrationConfig. */
  protected EmbeddingsOrchestrationConfig() {}

  /**
   * Set the modules of this {@link EmbeddingsOrchestrationConfig} instance and return the same
   * instance.
   *
   * @param modules The modules of this {@link EmbeddingsOrchestrationConfig}
   * @return The same instance of this {@link EmbeddingsOrchestrationConfig} class
   */
  @Nonnull
  public EmbeddingsOrchestrationConfig modules(@Nonnull final EmbeddingsModuleConfigs modules) {
    this.modules = modules;
    return this;
  }

  /**
   * Get modules
   *
   * @return modules The modules of this {@link EmbeddingsOrchestrationConfig} instance.
   */
  @Nonnull
  public EmbeddingsModuleConfigs getModules() {
    return modules;
  }

  /**
   * Set the modules of this {@link EmbeddingsOrchestrationConfig} instance.
   *
   * @param modules The modules of this {@link EmbeddingsOrchestrationConfig}
   */
  public void setModules(@Nonnull final EmbeddingsModuleConfigs modules) {
    this.modules = modules;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link EmbeddingsOrchestrationConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link EmbeddingsOrchestrationConfig}
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
          "EmbeddingsOrchestrationConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link EmbeddingsOrchestrationConfig} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (modules != null) declaredFields.put("modules", modules);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link EmbeddingsOrchestrationConfig} instance. If the
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
    final EmbeddingsOrchestrationConfig embeddingsOrchestrationConfig =
        (EmbeddingsOrchestrationConfig) o;
    return Objects.equals(
            this.cloudSdkCustomFields, embeddingsOrchestrationConfig.cloudSdkCustomFields)
        && Objects.equals(this.modules, embeddingsOrchestrationConfig.modules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modules, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class EmbeddingsOrchestrationConfig {\n");
    sb.append("    modules: ").append(toIndentedString(modules)).append("\n");
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
   * EmbeddingsOrchestrationConfig} instance with all required arguments.
   */
  public static Builder create() {
    return (modules) -> new EmbeddingsOrchestrationConfig().modules(modules);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the modules of this {@link EmbeddingsOrchestrationConfig} instance.
     *
     * @param modules The modules of this {@link EmbeddingsOrchestrationConfig}
     * @return The EmbeddingsOrchestrationConfig instance.
     */
    EmbeddingsOrchestrationConfig modules(@Nonnull final EmbeddingsModuleConfigs modules);
  }
}
