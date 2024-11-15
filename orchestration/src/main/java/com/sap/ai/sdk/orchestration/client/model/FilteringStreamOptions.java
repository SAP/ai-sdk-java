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

/** Stream options for output filtering. Will be ignored if stream is false. */
@JsonPropertyOrder({FilteringStreamOptions.JSON_PROPERTY_OVERLAP})
@com.google.common.annotations.Beta
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class FilteringStreamOptions {
  public static final String JSON_PROPERTY_OVERLAP = "overlap";
  private Integer overlap = 0;

  public FilteringStreamOptions() {}

  public FilteringStreamOptions overlap(Integer overlap) {

    this.overlap = overlap;
    return this;
  }

  /**
   * Number of characters that should be additionally sent to content filtering services from
   * previous chunks as additional context. minimum: 0 maximum: 10000
   *
   * @return overlap
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OVERLAP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getOverlap() {
    return overlap;
  }

  @JsonProperty(JSON_PROPERTY_OVERLAP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOverlap(Integer overlap) {
    this.overlap = overlap;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilteringStreamOptions filteringStreamOptions = (FilteringStreamOptions) o;
    return Objects.equals(this.overlap, filteringStreamOptions.overlap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(overlap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FilteringStreamOptions {\n");
    sb.append("    overlap: ").append(toIndentedString(overlap)).append("\n");
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

    private FilteringStreamOptions instance;

    public Builder() {
      this(new FilteringStreamOptions());
    }

    protected Builder(FilteringStreamOptions instance) {
      this.instance = instance;
    }

    public FilteringStreamOptions.Builder overlap(Integer overlap) {
      this.instance.overlap = overlap;
      return this;
    }

    /**
     * returns a built FilteringStreamOptions instance.
     *
     * <p>The builder is not reusable.
     */
    public FilteringStreamOptions build() {
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
  public static FilteringStreamOptions.Builder builder() {
    return new FilteringStreamOptions.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public FilteringStreamOptions.Builder toBuilder() {
    return new FilteringStreamOptions.Builder().overlap(getOverlap());
  }
}
