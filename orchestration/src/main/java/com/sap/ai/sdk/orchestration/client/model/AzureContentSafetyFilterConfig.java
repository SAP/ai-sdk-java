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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/** AzureContentSafetyFilterConfig */
@JsonPropertyOrder({
  AzureContentSafetyFilterConfig.JSON_PROPERTY_X_DISCRIMINATOR_VALUE,
  AzureContentSafetyFilterConfig.JSON_PROPERTY_TYPE,
  AzureContentSafetyFilterConfig.JSON_PROPERTY_CONFIG
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class AzureContentSafetyFilterConfig implements FilterConfig {
  public static final String JSON_PROPERTY_X_DISCRIMINATOR_VALUE = "x-discriminator-value";
  private String xDiscriminatorValue;

  /** String represents name of the filter provider */
  public enum TypeEnum {
    AZURE_CONTENT_SAFETY("azure_content_safety"),

    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  private TypeEnum type;

  public static final String JSON_PROPERTY_CONFIG = "config";
  private AzureContentSafety config;

  public AzureContentSafetyFilterConfig() {}

  public AzureContentSafetyFilterConfig xDiscriminatorValue(String xDiscriminatorValue) {

    this.xDiscriminatorValue = xDiscriminatorValue;
    return this;
  }

  /**
   * Discriminator for the type of the object
   *
   * @return xDiscriminatorValue
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_X_DISCRIMINATOR_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getxDiscriminatorValue() {
    return xDiscriminatorValue;
  }

  @JsonProperty(JSON_PROPERTY_X_DISCRIMINATOR_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setxDiscriminatorValue(String xDiscriminatorValue) {
    this.xDiscriminatorValue = xDiscriminatorValue;
  }

  public AzureContentSafetyFilterConfig type(TypeEnum type) {

    this.type = type;
    return this;
  }

  /**
   * String represents name of the filter provider
   *
   * @return type
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public TypeEnum getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(TypeEnum type) {
    this.type = type;
  }

  public AzureContentSafetyFilterConfig config(AzureContentSafety config) {

    this.config = config;
    return this;
  }

  /**
   * Get config
   *
   * @return config
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public AzureContentSafety getConfig() {
    return config;
  }

  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConfig(AzureContentSafety config) {
    this.config = config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AzureContentSafetyFilterConfig azureContentSafetyFilterConfig =
        (AzureContentSafetyFilterConfig) o;
    return Objects.equals(
            this.xDiscriminatorValue, azureContentSafetyFilterConfig.xDiscriminatorValue)
        && Objects.equals(this.type, azureContentSafetyFilterConfig.type)
        && Objects.equals(this.config, azureContentSafetyFilterConfig.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(xDiscriminatorValue, type, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AzureContentSafetyFilterConfig {\n");
    sb.append("    xDiscriminatorValue: ")
        .append(toIndentedString(xDiscriminatorValue))
        .append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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

    private AzureContentSafetyFilterConfig instance;

    public Builder() {
      this(new AzureContentSafetyFilterConfig());
    }

    protected Builder(AzureContentSafetyFilterConfig instance) {
      this.instance = instance;
    }

    public AzureContentSafetyFilterConfig.Builder xDiscriminatorValue(String xDiscriminatorValue) {
      this.instance.xDiscriminatorValue = xDiscriminatorValue;
      return this;
    }

    public AzureContentSafetyFilterConfig.Builder type(TypeEnum type) {
      this.instance.type = type;
      return this;
    }

    public AzureContentSafetyFilterConfig.Builder config(AzureContentSafety config) {
      this.instance.config = config;
      return this;
    }

    /**
     * returns a built AzureContentSafetyFilterConfig instance.
     *
     * <p>The builder is not reusable.
     */
    public AzureContentSafetyFilterConfig build() {
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
  public static AzureContentSafetyFilterConfig.Builder builder() {
    return new AzureContentSafetyFilterConfig.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public AzureContentSafetyFilterConfig.Builder toBuilder() {
    return new AzureContentSafetyFilterConfig.Builder()
        .xDiscriminatorValue(getxDiscriminatorValue())
        .type(getType())
        .config(getConfig());
  }
}
