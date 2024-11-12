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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/** GroundingModuleConfig */
@JsonPropertyOrder({
  GroundingModuleConfig.JSON_PROPERTY_TYPE,
  GroundingModuleConfig.JSON_PROPERTY_CONFIG
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class GroundingModuleConfig {
  public static final String JSON_PROPERTY_TYPE = "type";
  public static final String JSON_PROPERTY_CONFIG = "config";
  private TypeEnum type;
  private GroundingModuleConfigConfig config;

  public GroundingModuleConfig() {}

  /** Create a builder with no initialized field. */
  public static GroundingModuleConfig.Builder builder() {
    return new GroundingModuleConfig.Builder();
  }

  public GroundingModuleConfig type(TypeEnum type) {

    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @javax.annotation.Nullable
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

  public GroundingModuleConfig config(GroundingModuleConfigConfig config) {

    this.config = config;
    return this;
  }

  /**
   * Get config
   *
   * @return config
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public GroundingModuleConfigConfig getConfig() {
    return config;
  }

  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setConfig(GroundingModuleConfigConfig config) {
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
    GroundingModuleConfig groundingModuleConfig = (GroundingModuleConfig) o;
    return Objects.equals(this.type, groundingModuleConfig.type)
        && Objects.equals(this.config, groundingModuleConfig.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroundingModuleConfig {\n");
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

  /** Create a builder with a shallow copy of this instance. */
  public GroundingModuleConfig.Builder toBuilder() {
    return new GroundingModuleConfig.Builder().type(getType()).config(getConfig());
  }

  /** Gets or Sets type */
  public enum TypeEnum {
    DOCUMENT_GROUNDING_SERVICE("document_grounding_service"),

    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public static class Builder {

    private GroundingModuleConfig instance;

    public Builder() {
      this(new GroundingModuleConfig());
    }

    protected Builder(GroundingModuleConfig instance) {
      this.instance = instance;
    }

    public GroundingModuleConfig.Builder type(TypeEnum type) {
      this.instance.type = type;
      return this;
    }

    public GroundingModuleConfig.Builder config(GroundingModuleConfigConfig config) {
      this.instance.config = config;
      return this;
    }

    /**
     * returns a built GroundingModuleConfig instance.
     *
     * <p>The builder is not reusable.
     */
    public GroundingModuleConfig build() {
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
