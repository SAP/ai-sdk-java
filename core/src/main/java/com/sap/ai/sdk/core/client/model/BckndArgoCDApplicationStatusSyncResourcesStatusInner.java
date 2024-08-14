

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
 * BckndArgoCDApplicationStatusSyncResourcesStatusInner
 */
// CHECKSTYLE:OFF
public class BckndArgoCDApplicationStatusSyncResourcesStatusInner 
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("kind")
  private String kind;

  @JsonProperty("status")
  private String status;

  @JsonProperty("message")
  private String message;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the name of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance and return the same instance.
    *
    * @param name  ArgoCD application object name
    * @return The same instance of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} class
    */
   @Nonnull public BckndArgoCDApplicationStatusSyncResourcesStatusInner name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

   /**
    * ArgoCD application object name
    * @return name  The name of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
    */
  @Nonnull public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
   *
   * @param name  ArgoCD application object name
   */
  public void setName( @Nonnull final String name) {
    this.name = name;
  }

   /**
    * Set the kind of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance and return the same instance.
    *
    * @param kind  ArgoCD application object kind
    * @return The same instance of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} class
    */
   @Nonnull public BckndArgoCDApplicationStatusSyncResourcesStatusInner kind(@Nonnull final String kind) {
    this.kind = kind;
    return this;
  }

   /**
    * ArgoCD application object kind
    * @return kind  The kind of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
    */
  @Nonnull public String getKind() {
    return kind;
  }

  /**
   * Set the kind of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
   *
   * @param kind  ArgoCD application object kind
   */
  public void setKind( @Nonnull final String kind) {
    this.kind = kind;
  }

   /**
    * Set the status of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance and return the same instance.
    *
    * @param status  ArgoCD application object sync status
    * @return The same instance of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} class
    */
   @Nonnull public BckndArgoCDApplicationStatusSyncResourcesStatusInner status(@Nonnull final String status) {
    this.status = status;
    return this;
  }

   /**
    * ArgoCD application object sync status
    * @return status  The status of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
    */
  @Nonnull public String getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
   *
   * @param status  ArgoCD application object sync status
   */
  public void setStatus( @Nonnull final String status) {
    this.status = status;
  }

   /**
    * Set the message of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance and return the same instance.
    *
    * @param message  ArgoCD application object message
    * @return The same instance of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} class
    */
   @Nonnull public BckndArgoCDApplicationStatusSyncResourcesStatusInner message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

   /**
    * ArgoCD application object message
    * @return message  The message of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
    */
  @Nonnull public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
   *
   * @param message  ArgoCD application object message
   */
  public void setMessage( @Nonnull final String message) {
    this.message = message;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndArgoCDApplicationStatusSyncResourcesStatusInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDApplicationStatusSyncResourcesStatusInner} instance. If the map previously contained a mapping
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
    final BckndArgoCDApplicationStatusSyncResourcesStatusInner bckndArgoCDApplicationStatusSyncResourcesStatusInner = (BckndArgoCDApplicationStatusSyncResourcesStatusInner) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndArgoCDApplicationStatusSyncResourcesStatusInner.cloudSdkCustomFields) &&
        Objects.equals(this.name, bckndArgoCDApplicationStatusSyncResourcesStatusInner.name) &&
        Objects.equals(this.kind, bckndArgoCDApplicationStatusSyncResourcesStatusInner.kind) &&
        Objects.equals(this.status, bckndArgoCDApplicationStatusSyncResourcesStatusInner.status) &&
        Objects.equals(this.message, bckndArgoCDApplicationStatusSyncResourcesStatusInner.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, kind, status, message, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDApplicationStatusSyncResourcesStatusInner {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

