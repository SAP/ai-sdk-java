package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.orchestration.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.model.LLMModuleResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {
  /** Mixin to enforce a specific subtype to be deserialized always. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = LLMModuleResult.class)
  interface LLMModuleResultMixIn {}

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = As.EXISTING_PROPERTY,
      property = "type",
      visible = true)
  @JsonSubTypes({
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema.class,
        name = "json_schema"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject.class,
        name = "json_object"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.ResponseFormatText.class,
        name = "text")
  })
  interface ResponseFormatSubTypesMixin {}

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = As.EXISTING_PROPERTY,
      property = "role",
      visible = true)
  @JsonSubTypes({
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.AssistantChatMessage.class,
        name = "assistant"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.DeveloperChatMessage.class,
        name = "developer"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.SystemChatMessage.class,
        name = "system"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.ToolChatMessage.class,
        name = "tool"),
    @JsonSubTypes.Type(
        value = com.sap.ai.sdk.orchestration.model.UserChatMessage.class,
        name = "user")
  })
  interface ChatMessageMixin {}

  /**
   * Mixin used for parsing response "data" field of
   * error.intermediate_results.input_filtering.data.azure_content_safety
   */
  abstract static class AzureContentSafetyCaseAgnostic {
    @JsonProperty("hate")
    @JsonAlias("Hate")
    private AzureThreshold hate;

    @JsonProperty("self_harm")
    @JsonAlias("SelfHarm")
    private AzureThreshold selfHarm;

    @JsonProperty("sexual")
    @JsonAlias("Sexual")
    private AzureThreshold sexual;

    @JsonProperty("violence")
    @JsonAlias("Violence")
    private AzureThreshold violence;
  }
}
