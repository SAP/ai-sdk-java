/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets DataRepositoryType */
public enum DataRepositoryType {
  VECTOR("vector"),

  HELP_SAP_COM("help.sap.com"),

  UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

  private String value;

  DataRepositoryType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static DataRepositoryType fromValue(String value) {
    for (DataRepositoryType b : DataRepositoryType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return null;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
