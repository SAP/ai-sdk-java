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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** MaskingModuleConfig */
@JsonPropertyOrder({MaskingModuleConfig.JSON_PROPERTY_MASKING_PROVIDERS})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T14:16:03.918111+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class MaskingModuleConfig {
  public static final String JSON_PROPERTY_MASKING_PROVIDERS = "masking_providers";
  private List<MaskingProviderConfig> maskingProviders = new ArrayList<>();

  public MaskingModuleConfig() {}

  public MaskingModuleConfig maskingProviders(List<MaskingProviderConfig> maskingProviders) {

    this.maskingProviders = maskingProviders;
    return this;
  }

  public MaskingModuleConfig addMaskingProvidersItem(MaskingProviderConfig maskingProvidersItem) {
    if (this.maskingProviders == null) {
      this.maskingProviders = new ArrayList<>();
    }
    this.maskingProviders.add(maskingProvidersItem);
    return this;
  }

  /**
   * List of masking service providers
   *
   * @return maskingProviders
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MASKING_PROVIDERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<MaskingProviderConfig> getMaskingProviders() {
    return maskingProviders;
  }

  @JsonProperty(JSON_PROPERTY_MASKING_PROVIDERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMaskingProviders(List<MaskingProviderConfig> maskingProviders) {
    this.maskingProviders = maskingProviders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaskingModuleConfig maskingModuleConfig = (MaskingModuleConfig) o;
    return Objects.equals(this.maskingProviders, maskingModuleConfig.maskingProviders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maskingProviders);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaskingModuleConfig {\n");
    sb.append("    maskingProviders: ").append(toIndentedString(maskingProviders)).append("\n");
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

    private MaskingModuleConfig instance;

    public Builder() {
      this(new MaskingModuleConfig());
    }

    protected Builder(MaskingModuleConfig instance) {
      this.instance = instance;
    }

    public MaskingModuleConfig.Builder maskingProviders(
        List<MaskingProviderConfig> maskingProviders) {
      this.instance.maskingProviders = maskingProviders;
      return this;
    }

    /**
     * returns a built MaskingModuleConfig instance.
     *
     * <p>The builder is not reusable.
     */
    public MaskingModuleConfig build() {
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
  public static MaskingModuleConfig.Builder builder() {
    return new MaskingModuleConfig.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public MaskingModuleConfig.Builder toBuilder() {
    return new MaskingModuleConfig.Builder().maskingProviders(getMaskingProviders());
  }
}
