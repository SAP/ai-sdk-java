

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.32.1
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * MetaAiApiLimitsDeployments
 */
// CHECKSTYLE:OFF
public class MetaAiApiLimitsDeployments 
// CHECKSTYLE:ON
{
  @JsonProperty("maxRunningCount")
  private Integer maxRunningCount = -1;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the maxRunningCount of this {@link MetaAiApiLimitsDeployments} instance and return the same instance.
    *
    * @param maxRunningCount  Max nr of deployments allowed by this runtime per resource group. &lt;0 means unlimited.
    * @return The same instance of this {@link MetaAiApiLimitsDeployments} class
    */
   @Nonnull public MetaAiApiLimitsDeployments maxRunningCount(@Nonnull final Integer maxRunningCount) {
    this.maxRunningCount = maxRunningCount;
    return this;
  }

   /**
    * Max nr of deployments allowed by this runtime per resource group. &lt;0 means unlimited.
    * @return maxRunningCount  The maxRunningCount of this {@link MetaAiApiLimitsDeployments} instance.
    */
  @Nonnull public Integer getMaxRunningCount() {
    return maxRunningCount;
  }

  /**
   * Set the maxRunningCount of this {@link MetaAiApiLimitsDeployments} instance.
   *
   * @param maxRunningCount  Max nr of deployments allowed by this runtime per resource group. &lt;0 means unlimited.
   */
  public void setMaxRunningCount( @Nonnull final Integer maxRunningCount) {
    this.maxRunningCount = maxRunningCount;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MetaAiApiLimitsDeployments}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaAiApiLimitsDeployments} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("MetaAiApiLimitsDeployments has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link MetaAiApiLimitsDeployments} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
      cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }


  @Override
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final MetaAiApiLimitsDeployments metaAiApiLimitsDeployments = (MetaAiApiLimitsDeployments) o;
    return Objects.equals(this.cloudSdkCustomFields, metaAiApiLimitsDeployments.cloudSdkCustomFields) &&
        Objects.equals(this.maxRunningCount, metaAiApiLimitsDeployments.maxRunningCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxRunningCount, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaAiApiLimitsDeployments {\n");
    sb.append("    maxRunningCount: ").append(toIndentedString(maxRunningCount)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

