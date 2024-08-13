

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
 * BckndArgoCDApplicationDataRepoName
 */
// CHECKSTYLE:OFF
public class BckndArgoCDApplicationDataRepoName 
// CHECKSTYLE:ON
{
  @JsonProperty("repositoryName")
  private String repositoryName;

  @JsonProperty("revision")
  private String revision;

  @JsonProperty("path")
  private String path;

  @JsonProperty("applicationName")
  private String applicationName;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the repositoryName of this {@link BckndArgoCDApplicationDataRepoName} instance and return the same instance.
    *
    * @param repositoryName  Name of the repository to synchronise
    * @return The same instance of this {@link BckndArgoCDApplicationDataRepoName} class
    */
   @Nonnull public BckndArgoCDApplicationDataRepoName repositoryName(@Nonnull final String repositoryName) {
    this.repositoryName = repositoryName;
    return this;
  }

   /**
    * Name of the repository to synchronise
    * @return repositoryName  The repositoryName of this {@link BckndArgoCDApplicationDataRepoName} instance.
    */
  @Nonnull public String getRepositoryName() {
    return repositoryName;
  }

  /**
   * Set the repositoryName of this {@link BckndArgoCDApplicationDataRepoName} instance.
   *
   * @param repositoryName  Name of the repository to synchronise
   */
  public void setRepositoryName( @Nonnull final String repositoryName) {
    this.repositoryName = repositoryName;
  }

   /**
    * Set the revision of this {@link BckndArgoCDApplicationDataRepoName} instance and return the same instance.
    *
    * @param revision  revision to synchronise
    * @return The same instance of this {@link BckndArgoCDApplicationDataRepoName} class
    */
   @Nonnull public BckndArgoCDApplicationDataRepoName revision(@Nonnull final String revision) {
    this.revision = revision;
    return this;
  }

   /**
    * revision to synchronise
    * @return revision  The revision of this {@link BckndArgoCDApplicationDataRepoName} instance.
    */
  @Nonnull public String getRevision() {
    return revision;
  }

  /**
   * Set the revision of this {@link BckndArgoCDApplicationDataRepoName} instance.
   *
   * @param revision  revision to synchronise
   */
  public void setRevision( @Nonnull final String revision) {
    this.revision = revision;
  }

   /**
    * Set the path of this {@link BckndArgoCDApplicationDataRepoName} instance and return the same instance.
    *
    * @param path  path within the repository to synchronise
    * @return The same instance of this {@link BckndArgoCDApplicationDataRepoName} class
    */
   @Nonnull public BckndArgoCDApplicationDataRepoName path(@Nonnull final String path) {
    this.path = path;
    return this;
  }

   /**
    * path within the repository to synchronise
    * @return path  The path of this {@link BckndArgoCDApplicationDataRepoName} instance.
    */
  @Nonnull public String getPath() {
    return path;
  }

  /**
   * Set the path of this {@link BckndArgoCDApplicationDataRepoName} instance.
   *
   * @param path  path within the repository to synchronise
   */
  public void setPath( @Nonnull final String path) {
    this.path = path;
  }

   /**
    * Set the applicationName of this {@link BckndArgoCDApplicationDataRepoName} instance and return the same instance.
    *
    * @param applicationName  ArgoCD application name
    * @return The same instance of this {@link BckndArgoCDApplicationDataRepoName} class
    */
   @Nonnull public BckndArgoCDApplicationDataRepoName applicationName(@Nonnull final String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

   /**
    * ArgoCD application name
    * @return applicationName  The applicationName of this {@link BckndArgoCDApplicationDataRepoName} instance.
    */
  @Nonnull public String getApplicationName() {
    return applicationName;
  }

  /**
   * Set the applicationName of this {@link BckndArgoCDApplicationDataRepoName} instance.
   *
   * @param applicationName  ArgoCD application name
   */
  public void setApplicationName( @Nonnull final String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndArgoCDApplicationDataRepoName}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndArgoCDApplicationDataRepoName} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndArgoCDApplicationDataRepoName has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDApplicationDataRepoName} instance. If the map previously contained a mapping
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
    final BckndArgoCDApplicationDataRepoName bckndArgoCDApplicationDataRepoName = (BckndArgoCDApplicationDataRepoName) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndArgoCDApplicationDataRepoName.cloudSdkCustomFields) &&
        Objects.equals(this.repositoryName, bckndArgoCDApplicationDataRepoName.repositoryName) &&
        Objects.equals(this.revision, bckndArgoCDApplicationDataRepoName.revision) &&
        Objects.equals(this.path, bckndArgoCDApplicationDataRepoName.path) &&
        Objects.equals(this.applicationName, bckndArgoCDApplicationDataRepoName.applicationName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repositoryName, revision, path, applicationName, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDApplicationDataRepoName {\n");
    sb.append("    repositoryName: ").append(toIndentedString(repositoryName)).append("\n");
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
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

