/*
 * Grounding
 * Grounding is a service designed to handle data-related tasks, such as grounding and retrieval, using vector databases. It provides specialized data retrieval through these databases, grounding the retrieval process with your own external and context-relevant data. Grounding combines generative AI capabilities with the ability to use real-time, precise data to improve decision-making and business operations for specific AI-driven business solutions.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** GetPipeline */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = MSSharePointPipelineGetResponse.class, name = "MSSharePoint"),
  @JsonSubTypes.Type(value = S3PipelineGetResponse.class, name = "S3"),
  @JsonSubTypes.Type(value = SFTPPipelineGetResponse.class, name = "SFTP"),
  @JsonSubTypes.Type(
      value = MSSharePointPipelineGetResponse.class,
      name = "MSSharePointPipelineGetResponse"),
  @JsonSubTypes.Type(value = S3PipelineGetResponse.class, name = "S3PipelineGetResponse"),
  @JsonSubTypes.Type(value = SFTPPipelineGetResponse.class, name = "SFTPPipelineGetResponse"),
})
public interface GetPipeline {
  Object getType();
}
