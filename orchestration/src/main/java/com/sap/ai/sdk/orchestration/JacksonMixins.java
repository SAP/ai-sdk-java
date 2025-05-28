package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {

  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  interface NoneTypeInfoMixin {}
}
