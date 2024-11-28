package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.orchestration.generated.LLMChoice;
import com.sap.ai.sdk.orchestration.generated.LLMModuleResultSynchronous;
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

  /** Mixin to suppress @JsonTypeInfo for oneOf interfaces. */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  interface NoTypeInfoMixin {}
}
