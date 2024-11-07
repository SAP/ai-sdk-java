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

/** ModuleConfigs */
@JsonPropertyOrder({
  ModuleConfigs.JSON_PROPERTY_LLM_MODULE_CONFIG,
  ModuleConfigs.JSON_PROPERTY_TEMPLATING_MODULE_CONFIG,
  ModuleConfigs.JSON_PROPERTY_FILTERING_MODULE_CONFIG,
  ModuleConfigs.JSON_PROPERTY_MASKING_MODULE_CONFIG,
  ModuleConfigs.JSON_PROPERTY_GROUNDING_MODULE_CONFIG
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class ModuleConfigs {
  public static final String JSON_PROPERTY_LLM_MODULE_CONFIG = "llm_module_config";
  private LLMModuleConfig llmModuleConfig;

  public static final String JSON_PROPERTY_TEMPLATING_MODULE_CONFIG = "templating_module_config";
  private TemplatingModuleConfig templatingModuleConfig;

  public static final String JSON_PROPERTY_FILTERING_MODULE_CONFIG = "filtering_module_config";
  private FilteringModuleConfig filteringModuleConfig;

  public static final String JSON_PROPERTY_MASKING_MODULE_CONFIG = "masking_module_config";
  private MaskingModuleConfig maskingModuleConfig;

  public static final String JSON_PROPERTY_GROUNDING_MODULE_CONFIG = "grounding_module_config";
  private GroundingModuleConfig groundingModuleConfig;

  public ModuleConfigs() {}

  public ModuleConfigs llmModuleConfig(LLMModuleConfig llmModuleConfig) {

    this.llmModuleConfig = llmModuleConfig;
    return this;
  }

  /**
   * Get llmModuleConfig
   *
   * @return llmModuleConfig
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LLM_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public LLMModuleConfig getLlmModuleConfig() {
    return llmModuleConfig;
  }

  @JsonProperty(JSON_PROPERTY_LLM_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLlmModuleConfig(LLMModuleConfig llmModuleConfig) {
    this.llmModuleConfig = llmModuleConfig;
  }

  public ModuleConfigs templatingModuleConfig(TemplatingModuleConfig templatingModuleConfig) {

    this.templatingModuleConfig = templatingModuleConfig;
    return this;
  }

  /**
   * Get templatingModuleConfig
   *
   * @return templatingModuleConfig
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TEMPLATING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public TemplatingModuleConfig getTemplatingModuleConfig() {
    return templatingModuleConfig;
  }

  @JsonProperty(JSON_PROPERTY_TEMPLATING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTemplatingModuleConfig(TemplatingModuleConfig templatingModuleConfig) {
    this.templatingModuleConfig = templatingModuleConfig;
  }

  public ModuleConfigs filteringModuleConfig(FilteringModuleConfig filteringModuleConfig) {

    this.filteringModuleConfig = filteringModuleConfig;
    return this;
  }

  /**
   * Get filteringModuleConfig
   *
   * @return filteringModuleConfig
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FILTERING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public FilteringModuleConfig getFilteringModuleConfig() {
    return filteringModuleConfig;
  }

  @JsonProperty(JSON_PROPERTY_FILTERING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFilteringModuleConfig(FilteringModuleConfig filteringModuleConfig) {
    this.filteringModuleConfig = filteringModuleConfig;
  }

  public ModuleConfigs maskingModuleConfig(MaskingModuleConfig maskingModuleConfig) {

    this.maskingModuleConfig = maskingModuleConfig;
    return this;
  }

  /**
   * Get maskingModuleConfig
   *
   * @return maskingModuleConfig
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MASKING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public MaskingModuleConfig getMaskingModuleConfig() {
    return maskingModuleConfig;
  }

  @JsonProperty(JSON_PROPERTY_MASKING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMaskingModuleConfig(MaskingModuleConfig maskingModuleConfig) {
    this.maskingModuleConfig = maskingModuleConfig;
  }

  public ModuleConfigs groundingModuleConfig(GroundingModuleConfig groundingModuleConfig) {

    this.groundingModuleConfig = groundingModuleConfig;
    return this;
  }

  /**
   * Get groundingModuleConfig
   *
   * @return groundingModuleConfig
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GROUNDING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public GroundingModuleConfig getGroundingModuleConfig() {
    return groundingModuleConfig;
  }

  @JsonProperty(JSON_PROPERTY_GROUNDING_MODULE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGroundingModuleConfig(GroundingModuleConfig groundingModuleConfig) {
    this.groundingModuleConfig = groundingModuleConfig;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModuleConfigs moduleConfigs = (ModuleConfigs) o;
    return Objects.equals(this.llmModuleConfig, moduleConfigs.llmModuleConfig)
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
        groundingModuleConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
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

    private ModuleConfigs instance;

    public Builder() {
      this(new ModuleConfigs());
    }

    protected Builder(ModuleConfigs instance) {
      this.instance = instance;
    }

    public ModuleConfigs.Builder llmModuleConfig(LLMModuleConfig llmModuleConfig) {
      this.instance.llmModuleConfig = llmModuleConfig;
      return this;
    }

    public ModuleConfigs.Builder templatingModuleConfig(
        TemplatingModuleConfig templatingModuleConfig) {
      this.instance.templatingModuleConfig = templatingModuleConfig;
      return this;
    }

    public ModuleConfigs.Builder filteringModuleConfig(
        FilteringModuleConfig filteringModuleConfig) {
      this.instance.filteringModuleConfig = filteringModuleConfig;
      return this;
    }

    public ModuleConfigs.Builder maskingModuleConfig(MaskingModuleConfig maskingModuleConfig) {
      this.instance.maskingModuleConfig = maskingModuleConfig;
      return this;
    }

    public ModuleConfigs.Builder groundingModuleConfig(
        GroundingModuleConfig groundingModuleConfig) {
      this.instance.groundingModuleConfig = groundingModuleConfig;
      return this;
    }

    /**
     * returns a built ModuleConfigs instance.
     *
     * <p>The builder is not reusable.
     */
    public ModuleConfigs build() {
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
  public static ModuleConfigs.Builder builder() {
    return new ModuleConfigs.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public ModuleConfigs.Builder toBuilder() {
    return new ModuleConfigs.Builder()
        .llmModuleConfig(getLlmModuleConfig())
        .templatingModuleConfig(getTemplatingModuleConfig())
        .filteringModuleConfig(getFilteringModuleConfig())
        .maskingModuleConfig(getMaskingModuleConfig())
        .groundingModuleConfig(getGroundingModuleConfig());
  }
}
