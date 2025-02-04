/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
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
import com.google.common.annotations.Beta;

/** VectorV1VectorEndpointsGetCollectionDeletionStatus200Response */
@Beta
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = CollectionDeletedResponse.class),
  @JsonSubTypes.Type(value = CollectionPendingResponse.class),
})
public interface VectorV1VectorEndpointsGetCollectionDeletionStatus200Response {}
