

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
 * BckndServiceBrokerSecret
 */
// CHECKSTYLE:OFF
public class BckndServiceBrokerSecret 
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("passwordKeyRef")
  private String passwordKeyRef;

  @JsonProperty("usernameKeyRef")
  private String usernameKeyRef;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected BckndServiceBrokerSecret() {  }

  /**
   * Set the name of this {@link BckndServiceBrokerSecret} instance and return the same instance.
   *
   * @param name  broker secret name
   * @return The same instance of this {@link BckndServiceBrokerSecret} class
   */
  @Nonnull public BckndServiceBrokerSecret name( @Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * broker secret name
   * @return name  The name of this {@link BckndServiceBrokerSecret} instance.
   */
  @Nonnull public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndServiceBrokerSecret} instance.
   *
   * @param name  broker secret name
   */
  public void setName( @Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the passwordKeyRef of this {@link BckndServiceBrokerSecret} instance and return the same instance.
   *
   * @param passwordKeyRef  username key reference in broker secret
   * @return The same instance of this {@link BckndServiceBrokerSecret} class
   */
  @Nonnull public BckndServiceBrokerSecret passwordKeyRef( @Nullable final String passwordKeyRef) {
    this.passwordKeyRef = passwordKeyRef;
    return this;
  }

  /**
   * username key reference in broker secret
   * @return passwordKeyRef  The passwordKeyRef of this {@link BckndServiceBrokerSecret} instance.
   */
  @Nonnull public String getPasswordKeyRef() {
    return passwordKeyRef;
  }

  /**
   * Set the passwordKeyRef of this {@link BckndServiceBrokerSecret} instance.
   *
   * @param passwordKeyRef  username key reference in broker secret
   */
  public void setPasswordKeyRef( @Nullable final String passwordKeyRef) {
    this.passwordKeyRef = passwordKeyRef;
  }

  /**
   * Set the usernameKeyRef of this {@link BckndServiceBrokerSecret} instance and return the same instance.
   *
   * @param usernameKeyRef  password key reference in broker secret
   * @return The same instance of this {@link BckndServiceBrokerSecret} class
   */
  @Nonnull public BckndServiceBrokerSecret usernameKeyRef( @Nullable final String usernameKeyRef) {
    this.usernameKeyRef = usernameKeyRef;
    return this;
  }

  /**
   * password key reference in broker secret
   * @return usernameKeyRef  The usernameKeyRef of this {@link BckndServiceBrokerSecret} instance.
   */
  @Nonnull public String getUsernameKeyRef() {
    return usernameKeyRef;
  }

  /**
   * Set the usernameKeyRef of this {@link BckndServiceBrokerSecret} instance.
   *
   * @param usernameKeyRef  password key reference in broker secret
   */
  public void setUsernameKeyRef( @Nullable final String usernameKeyRef) {
    this.usernameKeyRef = usernameKeyRef;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndServiceBrokerSecret}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndServiceBrokerSecret} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndServiceBrokerSecret has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndServiceBrokerSecret} instance. If the map previously contained a mapping
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
    final BckndServiceBrokerSecret bckndServiceBrokerSecret = (BckndServiceBrokerSecret) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndServiceBrokerSecret.cloudSdkCustomFields) &&
        Objects.equals(this.name, bckndServiceBrokerSecret.name) &&
        Objects.equals(this.passwordKeyRef, bckndServiceBrokerSecret.passwordKeyRef) &&
        Objects.equals(this.usernameKeyRef, bckndServiceBrokerSecret.usernameKeyRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, passwordKeyRef, usernameKeyRef, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndServiceBrokerSecret {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    passwordKeyRef: ").append(toIndentedString(passwordKeyRef)).append("\n");
    sb.append("    usernameKeyRef: ").append(toIndentedString(usernameKeyRef)).append("\n");
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

    /**
    * Create a new {@link BckndServiceBrokerSecret} instance. No arguments are required.
    */
    public static BckndServiceBrokerSecret create() {
        return new BckndServiceBrokerSecret();
    }

}

