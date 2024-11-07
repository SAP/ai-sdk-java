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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** CompletionPostRequest */
@JsonPropertyOrder({
  CompletionPostRequest.JSON_PROPERTY_ORCHESTRATION_CONFIG,
  CompletionPostRequest.JSON_PROPERTY_INPUT_PARAMS,
  CompletionPostRequest.JSON_PROPERTY_MESSAGES_HISTORY
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class CompletionPostRequest {
  public static final String JSON_PROPERTY_ORCHESTRATION_CONFIG = "orchestration_config";
  private OrchestrationConfig orchestrationConfig;

  public static final String JSON_PROPERTY_INPUT_PARAMS = "input_params";
  private Map<String, String> inputParams = new HashMap<>();

  public static final String JSON_PROPERTY_MESSAGES_HISTORY = "messages_history";
  private List<ChatMessage> messagesHistory;

  public CompletionPostRequest() {}

  public CompletionPostRequest orchestrationConfig(OrchestrationConfig orchestrationConfig) {

    this.orchestrationConfig = orchestrationConfig;
    return this;
  }

  /**
   * Get orchestrationConfig
   *
   * @return orchestrationConfig
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ORCHESTRATION_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public OrchestrationConfig getOrchestrationConfig() {
    return orchestrationConfig;
  }

  @JsonProperty(JSON_PROPERTY_ORCHESTRATION_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOrchestrationConfig(OrchestrationConfig orchestrationConfig) {
    this.orchestrationConfig = orchestrationConfig;
  }

  public CompletionPostRequest inputParams(Map<String, String> inputParams) {

    this.inputParams = inputParams;
    return this;
  }

  public CompletionPostRequest putInputParamsItem(String key, String inputParamsItem) {
    if (this.inputParams == null) {
      this.inputParams = new HashMap<>();
    }
    this.inputParams.put(key, inputParamsItem);
    return this;
  }

  /**
   * Get inputParams
   *
   * @return inputParams
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INPUT_PARAMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getInputParams() {
    return inputParams;
  }

  @JsonProperty(JSON_PROPERTY_INPUT_PARAMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInputParams(Map<String, String> inputParams) {
    this.inputParams = inputParams;
  }

  public CompletionPostRequest messagesHistory(List<ChatMessage> messagesHistory) {

    this.messagesHistory = messagesHistory;
    return this;
  }

  public CompletionPostRequest addMessagesHistoryItem(ChatMessage messagesHistoryItem) {
    if (this.messagesHistory == null) {
      this.messagesHistory = new ArrayList<>();
    }
    this.messagesHistory.add(messagesHistoryItem);
    return this;
  }

  /**
   * History of chat messages. Can be used to provide system and assistant messages to set the
   * context of the conversation. Will be merged with the template message
   *
   * @return messagesHistory
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MESSAGES_HISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<ChatMessage> getMessagesHistory() {
    return messagesHistory;
  }

  @JsonProperty(JSON_PROPERTY_MESSAGES_HISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMessagesHistory(List<ChatMessage> messagesHistory) {
    this.messagesHistory = messagesHistory;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompletionPostRequest completionPostRequest = (CompletionPostRequest) o;
    return Objects.equals(this.orchestrationConfig, completionPostRequest.orchestrationConfig)
        && Objects.equals(this.inputParams, completionPostRequest.inputParams)
        && Objects.equals(this.messagesHistory, completionPostRequest.messagesHistory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orchestrationConfig, inputParams, messagesHistory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompletionPostRequest {\n");
    sb.append("    orchestrationConfig: ")
        .append(toIndentedString(orchestrationConfig))
        .append("\n");
    sb.append("    inputParams: ").append(toIndentedString(inputParams)).append("\n");
    sb.append("    messagesHistory: ").append(toIndentedString(messagesHistory)).append("\n");
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

    private CompletionPostRequest instance;

    public Builder() {
      this(new CompletionPostRequest());
    }

    protected Builder(CompletionPostRequest instance) {
      this.instance = instance;
    }

    public CompletionPostRequest.Builder orchestrationConfig(
        OrchestrationConfig orchestrationConfig) {
      this.instance.orchestrationConfig = orchestrationConfig;
      return this;
    }

    public CompletionPostRequest.Builder inputParams(Map<String, String> inputParams) {
      this.instance.inputParams = inputParams;
      return this;
    }

    public CompletionPostRequest.Builder messagesHistory(List<ChatMessage> messagesHistory) {
      this.instance.messagesHistory = messagesHistory;
      return this;
    }

    /**
     * returns a built CompletionPostRequest instance.
     *
     * <p>The builder is not reusable.
     */
    public CompletionPostRequest build() {
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
  public static CompletionPostRequest.Builder builder() {
    return new CompletionPostRequest.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public CompletionPostRequest.Builder toBuilder() {
    return new CompletionPostRequest.Builder()
        .orchestrationConfig(getOrchestrationConfig())
        .inputParams(getInputParams())
        .messagesHistory(getMessagesHistory());
  }
}
