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

/** TemplateRefByID */
@JsonPropertyOrder({TemplateRefByID.JSON_PROPERTY_ID})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class TemplateRefByID implements TemplateRefTemplateRef {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public TemplateRefByID() {}

  public TemplateRefByID id(String id) {

    this.id = id;
    return this;
  }

  /**
   * ID of the template in prompt registry
   *
   * @return id
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateRefByID templateRefByID = (TemplateRefByID) o;
    return Objects.equals(this.id, templateRefByID.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateRefByID {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

    private TemplateRefByID instance;

    public Builder() {
      this(new TemplateRefByID());
    }

    protected Builder(TemplateRefByID instance) {
      this.instance = instance;
    }

    public TemplateRefByID.Builder id(String id) {
      this.instance.id = id;
      return this;
    }

    /**
     * returns a built TemplateRefByID instance.
     *
     * <p>The builder is not reusable.
     */
    public TemplateRefByID build() {
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
  public static TemplateRefByID.Builder builder() {
    return new TemplateRefByID.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public TemplateRefByID.Builder toBuilder() {
    return new TemplateRefByID.Builder().id(getId());
  }
}
