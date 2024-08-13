

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.33.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets KpiColumnName
 */
public enum KpiColumnName {
  
  RESOURCEGROUP("ResourceGroup"),
  
  SCENARIO("Scenario"),
  
  EXECUTABLE("Executable"),
  
  EXECUTIONS("Executions"),
  
  ARTIFACTS("Artifacts"),
  
  DEPLOYMENTS("Deployments");

  private final String value;

  KpiColumnName(String value) {
    this.value = value;
  }

  /**
   * @return The enum value.
   */
  @JsonValue
  public String getValue() {
    return value;
  }

  /**
   * @return The String representation of the enum value.
   */
  @Override
  @Nonnull public String toString() {
    return String.valueOf(value);
  }

  /**
   * Converts the given value to its enum representation.
   *
   * @param value The input value.
   *
   * @return The enum representation of the given value.
   */
  @JsonCreator
  public static KpiColumnName fromValue(@Nonnull final String value) {
    for (final KpiColumnName b : KpiColumnName.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return null;
  }
}

