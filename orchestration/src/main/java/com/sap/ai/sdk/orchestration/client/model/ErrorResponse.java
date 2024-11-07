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

/** ErrorResponse */
@JsonPropertyOrder({
  ErrorResponse.JSON_PROPERTY_REQUEST_ID,
  ErrorResponse.JSON_PROPERTY_CODE,
  ErrorResponse.JSON_PROPERTY_MESSAGE,
  ErrorResponse.JSON_PROPERTY_LOCATION,
  ErrorResponse.JSON_PROPERTY_MODULE_RESULTS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class ErrorResponse {
  public static final String JSON_PROPERTY_REQUEST_ID = "request_id";
  private String requestId;

  public static final String JSON_PROPERTY_CODE = "code";
  private Integer code;

  public static final String JSON_PROPERTY_MESSAGE = "message";
  private String message;

  public static final String JSON_PROPERTY_LOCATION = "location";
  private String location;

  public static final String JSON_PROPERTY_MODULE_RESULTS = "module_results";
  private ModuleResults moduleResults;

  public ErrorResponse() {}

  public ErrorResponse requestId(String requestId) {

    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   *
   * @return requestId
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getRequestId() {
    return requestId;
  }

  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public ErrorResponse code(Integer code) {

    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getCode() {
    return code;
  }

  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCode(Integer code) {
    this.code = code;
  }

  public ErrorResponse message(String message) {

    this.message = message;
    return this;
  }

  /**
   * Get message
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

  public ErrorResponse location(String location) {

    this.location = location;
    return this;
  }

  /**
   * Where the error occurred
   *
   * @return location
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getLocation() {
    return location;
  }

  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLocation(String location) {
    this.location = location;
  }

  public ErrorResponse moduleResults(ModuleResults moduleResults) {

    this.moduleResults = moduleResults;
    return this;
  }

  /**
   * Get moduleResults
   *
   * @return moduleResults
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MODULE_RESULTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public ModuleResults getModuleResults() {
    return moduleResults;
  }

  @JsonProperty(JSON_PROPERTY_MODULE_RESULTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setModuleResults(ModuleResults moduleResults) {
    this.moduleResults = moduleResults;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.requestId, errorResponse.requestId)
        && Objects.equals(this.code, errorResponse.code)
        && Objects.equals(this.message, errorResponse.message)
        && Objects.equals(this.location, errorResponse.location)
        && Objects.equals(this.moduleResults, errorResponse.moduleResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, code, message, location, moduleResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    moduleResults: ").append(toIndentedString(moduleResults)).append("\n");
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

    private ErrorResponse instance;

    public Builder() {
      this(new ErrorResponse());
    }

    protected Builder(ErrorResponse instance) {
      this.instance = instance;
    }

    public ErrorResponse.Builder requestId(String requestId) {
      this.instance.requestId = requestId;
      return this;
    }

    public ErrorResponse.Builder code(Integer code) {
      this.instance.code = code;
      return this;
    }

    public ErrorResponse.Builder message(String message) {
      this.instance.message = message;
      return this;
    }

    public ErrorResponse.Builder location(String location) {
      this.instance.location = location;
      return this;
    }

    public ErrorResponse.Builder moduleResults(ModuleResults moduleResults) {
      this.instance.moduleResults = moduleResults;
      return this;
    }

    /**
     * returns a built ErrorResponse instance.
     *
     * <p>The builder is not reusable.
     */
    public ErrorResponse build() {
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
  public static ErrorResponse.Builder builder() {
    return new ErrorResponse.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public ErrorResponse.Builder toBuilder() {
    return new ErrorResponse.Builder()
        .requestId(getRequestId())
        .code(getCode())
        .message(getMessage())
        .location(getLocation())
        .moduleResults(getModuleResults());
  }
}
