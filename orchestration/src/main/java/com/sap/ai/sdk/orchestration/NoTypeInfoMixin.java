package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** Mixin to suppress @JsonTypeInfo for oneOf interfaces. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public interface NoTypeInfoMixin {}
