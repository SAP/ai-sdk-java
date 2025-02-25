/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.annotations.Beta;

/** ChatMessage */
@Beta
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultiChatMessage.class),
  @JsonSubTypes.Type(value = SingleChatMessage.class),
})
public interface ChatMessage {}
