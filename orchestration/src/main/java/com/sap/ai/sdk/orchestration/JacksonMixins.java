package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {
  /** Mixin to enforce a specific subtype to be deserialized always. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = LLMModuleResultSynchronous.class)
  interface LLMModuleResultMixIn {}

  /** Mixin to enforce a specific subtype to be deserialized always. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = LLMChoice.class)
  interface ModuleResultsOutputUnmaskingInnerMixIn {}

  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  interface NoneTypeInfoMixin {}

//  JONAS: used EXISTING_PROPERTY here, did not seem to work better
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
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
}
