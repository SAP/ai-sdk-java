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

/** Generic module result */
@JsonPropertyOrder({
  GenericModuleResult.JSON_PROPERTY_MESSAGE,
  GenericModuleResult.JSON_PROPERTY_DATA
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T18:02:22.585601+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class GenericModuleResult {
  public static final String JSON_PROPERTY_MESSAGE = "message";
  public static final String JSON_PROPERTY_DATA = "data";
  private String message;
  private Object data;

  public GenericModuleResult() {}

  /** Create a builder with no initialized field. */
  public static GenericModuleResult.Builder builder() {
    return new GenericModuleResult.Builder();
  }

  public GenericModuleResult message(String message) {

    this.message = message;
    return this;
  }

  /**
   * Some message created from the module
   *
   * @return message
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getMessage() {
    return message;
  }

  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMessage(String message) {
    this.message = message;
  }

  public GenericModuleResult data(Object data) {

    this.data = data;
    return this;
  }

  /**
   * Additional data object from the module
   *
   * @return data
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Object getData() {
    return data;
  }

  @JsonProperty(JSON_PROPERTY_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenericModuleResult genericModuleResult = (GenericModuleResult) o;
    return Objects.equals(this.message, genericModuleResult.message)
        && Objects.equals(this.data, genericModuleResult.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenericModuleResult {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
  public GenericModuleResult.Builder toBuilder() {
    return new GenericModuleResult.Builder().message(getMessage()).data(getData());
  }

  public static class Builder {

    private GenericModuleResult instance;

    public Builder() {
      this(new GenericModuleResult());
    }

    protected Builder(GenericModuleResult instance) {
      this.instance = instance;
    }

    public GenericModuleResult.Builder message(String message) {
      this.instance.message = message;
      return this;
    }

    public GenericModuleResult.Builder data(Object data) {
      this.instance.data = data;
      return this;
    }

    /**
     * returns a built GenericModuleResult instance.
     *
     * <p>The builder is not reusable.
     */
    public GenericModuleResult build() {
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
