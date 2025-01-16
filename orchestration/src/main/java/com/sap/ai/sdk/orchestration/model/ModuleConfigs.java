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

/** ModuleConfigs */
@Beta // CHECKSTYLE:OFF
public class ModuleConfigs
// CHECKSTYLE:ON
{
  @JsonProperty("llm_module_config")
  private LLMModuleConfig llmModuleConfig;

  @JsonProperty("templating_module_config")
  private TemplatingModuleConfig templatingModuleConfig;

  @JsonProperty("filtering_module_config")
  private FilteringModuleConfig filteringModuleConfig;

  @JsonProperty("masking_module_config")
  private MaskingModuleConfig maskingModuleConfig;

  @JsonProperty("grounding_module_config")
  private GroundingModuleConfig groundingModuleConfig;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ModuleConfigs. */
  protected ModuleConfigs() {}

  /**
   * Set the llmModuleConfig of this {@link ModuleConfigs} instance and return the same instance.
   *
   * @param llmModuleConfig The llmModuleConfig of this {@link ModuleConfigs}
   * @return The same instance of this {@link ModuleConfigs} class
   */
  @Nonnull
  public ModuleConfigs llmModuleConfig(@Nonnull final LLMModuleConfig llmModuleConfig) {
    this.llmModuleConfig = llmModuleConfig;
    return this;
  }

  /**
   * Get llmModuleConfig
   *
   * @return llmModuleConfig The llmModuleConfig of this {@link ModuleConfigs} instance.
   */
  @Nonnull
  public LLMModuleConfig getLlmModuleConfig() {
    return llmModuleConfig;
  }

  /**
   * Set the llmModuleConfig of this {@link ModuleConfigs} instance.
   *
   * @param llmModuleConfig The llmModuleConfig of this {@link ModuleConfigs}
   */
  public void setLlmModuleConfig(@Nonnull final LLMModuleConfig llmModuleConfig) {
    this.llmModuleConfig = llmModuleConfig;
  }

  /**
   * Set the templatingModuleConfig of this {@link ModuleConfigs} instance and return the same
   * instance.
   *
   * @param templatingModuleConfig The templatingModuleConfig of this {@link ModuleConfigs}
   * @return The same instance of this {@link ModuleConfigs} class
   */
  @Nonnull
  public ModuleConfigs templatingModuleConfig(
      @Nonnull final TemplatingModuleConfig templatingModuleConfig) {
    this.templatingModuleConfig = templatingModuleConfig;
    return this;
  }

  /**
   * Get templatingModuleConfig
   *
   * @return templatingModuleConfig The templatingModuleConfig of this {@link ModuleConfigs}
   *     instance.
   */
  @Nonnull
  public TemplatingModuleConfig getTemplatingModuleConfig() {
    return templatingModuleConfig;
  }

  /**
   * Set the templatingModuleConfig of this {@link ModuleConfigs} instance.
   *
   * @param templatingModuleConfig The templatingModuleConfig of this {@link ModuleConfigs}
   */
  public void setTemplatingModuleConfig(
      @Nonnull final TemplatingModuleConfig templatingModuleConfig) {
    this.templatingModuleConfig = templatingModuleConfig;
  }

  /**
   * Set the filteringModuleConfig of this {@link ModuleConfigs} instance and return the same
   * instance.
   *
   * @param filteringModuleConfig The filteringModuleConfig of this {@link ModuleConfigs}
   * @return The same instance of this {@link ModuleConfigs} class
   */
  @Nonnull
  public ModuleConfigs filteringModuleConfig(
      @Nullable final FilteringModuleConfig filteringModuleConfig) {
    this.filteringModuleConfig = filteringModuleConfig;
    return this;
  }

  /**
   * Get filteringModuleConfig
   *
   * @return filteringModuleConfig The filteringModuleConfig of this {@link ModuleConfigs} instance.
   */
  @Nonnull
  public FilteringModuleConfig getFilteringModuleConfig() {
    return filteringModuleConfig;
  }

  /**
   * Set the filteringModuleConfig of this {@link ModuleConfigs} instance.
   *
   * @param filteringModuleConfig The filteringModuleConfig of this {@link ModuleConfigs}
   */
  public void setFilteringModuleConfig(
      @Nullable final FilteringModuleConfig filteringModuleConfig) {
    this.filteringModuleConfig = filteringModuleConfig;
  }

  /**
   * Set the maskingModuleConfig of this {@link ModuleConfigs} instance and return the same
   * instance.
   *
   * @param maskingModuleConfig The maskingModuleConfig of this {@link ModuleConfigs}
   * @return The same instance of this {@link ModuleConfigs} class
   */
  @Nonnull
  public ModuleConfigs maskingModuleConfig(
      @Nullable final MaskingModuleConfig maskingModuleConfig) {
    this.maskingModuleConfig = maskingModuleConfig;
    return this;
  }

  /**
   * Get maskingModuleConfig
   *
   * @return maskingModuleConfig The maskingModuleConfig of this {@link ModuleConfigs} instance.
   */
  @Nonnull
  public MaskingModuleConfig getMaskingModuleConfig() {
    return maskingModuleConfig;
  }

  /**
   * Set the maskingModuleConfig of this {@link ModuleConfigs} instance.
   *
   * @param maskingModuleConfig The maskingModuleConfig of this {@link ModuleConfigs}
   */
  public void setMaskingModuleConfig(@Nullable final MaskingModuleConfig maskingModuleConfig) {
    this.maskingModuleConfig = maskingModuleConfig;
  }

  /**
   * Set the groundingModuleConfig of this {@link ModuleConfigs} instance and return the same
   * instance.
   *
   * @param groundingModuleConfig The groundingModuleConfig of this {@link ModuleConfigs}
   * @return The same instance of this {@link ModuleConfigs} class
   */
  @Nonnull
  public ModuleConfigs groundingModuleConfig(
      @Nullable final GroundingModuleConfig groundingModuleConfig) {
    this.groundingModuleConfig = groundingModuleConfig;
    return this;
  }

  /**
   * Get groundingModuleConfig
   *
   * @return groundingModuleConfig The groundingModuleConfig of this {@link ModuleConfigs} instance.
   */
  @Nonnull
  public GroundingModuleConfig getGroundingModuleConfig() {
    return groundingModuleConfig;
  }

  /**
   * Set the groundingModuleConfig of this {@link ModuleConfigs} instance.
   *
   * @param groundingModuleConfig The groundingModuleConfig of this {@link ModuleConfigs}
   */
  public void setGroundingModuleConfig(
      @Nullable final GroundingModuleConfig groundingModuleConfig) {
    this.groundingModuleConfig = groundingModuleConfig;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ModuleConfigs}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ModuleConfigs} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("ModuleConfigs has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ModuleConfigs} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
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
    final ModuleConfigs moduleConfigs = (ModuleConfigs) o;
    return Objects.equals(this.cloudSdkCustomFields, moduleConfigs.cloudSdkCustomFields)
        && Objects.equals(this.llmModuleConfig, moduleConfigs.llmModuleConfig)
        && Objects.equals(this.templatingModuleConfig, moduleConfigs.templatingModuleConfig)
        && Objects.equals(this.filteringModuleConfig, moduleConfigs.filteringModuleConfig)
        && Objects.equals(this.maskingModuleConfig, moduleConfigs.maskingModuleConfig)
        && Objects.equals(this.groundingModuleConfig, moduleConfigs.groundingModuleConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        llmModuleConfig,
        templatingModuleConfig,
        filteringModuleConfig,
        maskingModuleConfig,
        groundingModuleConfig,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ModuleConfigs {\n");
    sb.append("    llmModuleConfig: ").append(toIndentedString(llmModuleConfig)).append("\n");
    sb.append("    templatingModuleConfig: ")
        .append(toIndentedString(templatingModuleConfig))
        .append("\n");
    sb.append("    filteringModuleConfig: ")
        .append(toIndentedString(filteringModuleConfig))
        .append("\n");
    sb.append("    maskingModuleConfig: ")
        .append(toIndentedString(maskingModuleConfig))
        .append("\n");
    sb.append("    groundingModuleConfig: ")
        .append(toIndentedString(groundingModuleConfig))
        .append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ModuleConfigs} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (llmModuleConfig) ->
        (templatingModuleConfig) ->
            new ModuleConfigs()
                .llmModuleConfig(llmModuleConfig)
                .templatingModuleConfig(templatingModuleConfig);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the llmModuleConfig of this {@link ModuleConfigs} instance.
     *
     * @param llmModuleConfig The llmModuleConfig of this {@link ModuleConfigs}
     * @return The ModuleConfigs builder.
     */
    Builder1 llmModuleConfig(@Nonnull final LLMModuleConfig llmModuleConfig);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the templatingModuleConfig of this {@link ModuleConfigs} instance.
     *
     * @param templatingModuleConfig The templatingModuleConfig of this {@link ModuleConfigs}
     * @return The ModuleConfigs instance.
     */
    ModuleConfigs templatingModuleConfig(
        @Nonnull final TemplatingModuleConfig templatingModuleConfig);
  }
}
