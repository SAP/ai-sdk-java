package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.orchestration.model.LLMChoiceSynchronous;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultStreaming;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {

  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  interface NoneTypeInfoMixin {}

  /** Mixin to enforce a specific subtype to be deserialized always. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = LLMModuleResultSynchronous.class)
  interface LLMModuleResultMixIn {}

  /** Mixin to enforce a specific subtype to be deserialized always. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = LLMChoiceSynchronous.class)
  interface ModuleResultsOutputUnmaskingInnerMixIn {}

  /** Mixin to enforce deserialization into correct classes in the synchronous case. */
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.PROPERTY,
      property = "type",
      defaultImpl = LLMModuleResultSynchronous.class)
  @JsonSubTypes({
    @JsonSubTypes.Type(value = LLMModuleResultSynchronous.class, name = "synch"),
    @JsonSubTypes.Type(value = LLMModuleResultStreaming.class, name = "streaming")
  })
  public interface DefaultToSynchronousMixin {}

  /** Mixin to enforce deserialization into correct classes in the streaming case. */
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.PROPERTY,
      property = "type",
      defaultImpl = LLMModuleResultStreaming.class)
  @JsonSubTypes({
    @JsonSubTypes.Type(value = LLMModuleResultSynchronous.class, name = "synch"),
    @JsonSubTypes.Type(value = LLMModuleResultStreaming.class, name = "streaming")
  })
  public interface DefaultToStreamingMixin {}

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.PROPERTY,
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
      include = JsonTypeInfo.As.PROPERTY,
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
}
