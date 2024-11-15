package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;

/** Mixin to enforce a specific subtype to be deserialized always. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonDeserialize(as = LLMModuleResultSynchronous.class)
interface LLMModuleResultMixIn {}
