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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T10:43:10.628772+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
@JsonIgnoreProperties(
    value = "", // ignore manually set , it will be automatically generated by Jackson during
    // serialization
    allowSetters = true // allows the  to be set during deserialization
    )
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = LLMChoice.class, name = "LLMChoice"),
  @JsonSubTypes.Type(value = LLMChoiceStreaming.class, name = "LLMChoiceStreaming")
})
public interface ModuleResultsOutputUnmaskingInner {}
