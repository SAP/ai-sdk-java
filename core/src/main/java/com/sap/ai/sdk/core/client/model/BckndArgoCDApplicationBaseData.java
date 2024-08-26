

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
 * BckndArgoCDApplicationBaseData
 */
// CHECKSTYLE:OFF
public class BckndArgoCDApplicationBaseData 
// CHECKSTYLE:ON
{
  @JsonProperty("repositoryUrl")
  private String repositoryUrl;

  @JsonProperty("revision")
  private String revision;

  @JsonProperty("path")
  private String path;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected BckndArgoCDApplicationBaseData() {  }

  /**
   * Set the repositoryUrl of this {@link BckndArgoCDApplicationBaseData} instance and return the same instance.
   *
   * @param repositoryUrl  URL of the repository to synchronise
   * @return The same instance of this {@link BckndArgoCDApplicationBaseData} class
   */
  @Nonnull public BckndArgoCDApplicationBaseData repositoryUrl( @Nonnull final String repositoryUrl) {
    this.repositoryUrl = repositoryUrl;
    return this;
  }

  /**
   * URL of the repository to synchronise
   * @return repositoryUrl  The repositoryUrl of this {@link BckndArgoCDApplicationBaseData} instance.
   */
  @Nonnull public String getRepositoryUrl() {
    return repositoryUrl;
  }

  /**
   * Set the repositoryUrl of this {@link BckndArgoCDApplicationBaseData} instance.
   *
   * @param repositoryUrl  URL of the repository to synchronise
   */
  public void setRepositoryUrl( @Nonnull final String repositoryUrl) {
    this.repositoryUrl = repositoryUrl;
  }

  /**
   * Set the revision of this {@link BckndArgoCDApplicationBaseData} instance and return the same instance.
   *
   * @param revision  revision to synchronise
   * @return The same instance of this {@link BckndArgoCDApplicationBaseData} class
   */
  @Nonnull public BckndArgoCDApplicationBaseData revision( @Nonnull final String revision) {
    this.revision = revision;
    return this;
  }

  /**
   * revision to synchronise
   * @return revision  The revision of this {@link BckndArgoCDApplicationBaseData} instance.
   */
  @Nonnull public String getRevision() {
    return revision;
  }

  /**
   * Set the revision of this {@link BckndArgoCDApplicationBaseData} instance.
   *
   * @param revision  revision to synchronise
   */
  public void setRevision( @Nonnull final String revision) {
    this.revision = revision;
  }

  /**
   * Set the path of this {@link BckndArgoCDApplicationBaseData} instance and return the same instance.
   *
   * @param path  path within the repository to synchronise
   * @return The same instance of this {@link BckndArgoCDApplicationBaseData} class
   */
  @Nonnull public BckndArgoCDApplicationBaseData path( @Nonnull final String path) {
    this.path = path;
    return this;
  }

  /**
   * path within the repository to synchronise
   * @return path  The path of this {@link BckndArgoCDApplicationBaseData} instance.
   */
  @Nonnull public String getPath() {
    return path;
  }

  /**
   * Set the path of this {@link BckndArgoCDApplicationBaseData} instance.
   *
   * @param path  path within the repository to synchronise
   */
  public void setPath( @Nonnull final String path) {
    this.path = path;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndArgoCDApplicationBaseData}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndArgoCDApplicationBaseData} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndArgoCDApplicationBaseData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDApplicationBaseData} instance. If the map previously contained a mapping
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
    final BckndArgoCDApplicationBaseData bckndArgoCDApplicationBaseData = (BckndArgoCDApplicationBaseData) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndArgoCDApplicationBaseData.cloudSdkCustomFields) &&
        Objects.equals(this.repositoryUrl, bckndArgoCDApplicationBaseData.repositoryUrl) &&
        Objects.equals(this.revision, bckndArgoCDApplicationBaseData.revision) &&
        Objects.equals(this.path, bckndArgoCDApplicationBaseData.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repositoryUrl, revision, path, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDApplicationBaseData {\n");
    sb.append("    repositoryUrl: ").append(toIndentedString(repositoryUrl)).append("\n");
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
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
    * Create a type-safe, fluent-api builder object to construct a new {@link BckndArgoCDApplicationBaseData} instance with all required arguments.
    */
    public static Builder create() {
        return (repositoryUrl) -> (revision) -> (path) -> new BckndArgoCDApplicationBaseData().repositoryUrl(repositoryUrl).revision(revision).path(path);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the repositoryUrl of this {@link BckndArgoCDApplicationBaseData} instance.
        *
        * @param repositoryUrl  URL of the repository to synchronise
        * @return The BckndArgoCDApplicationBaseData builder.
        */
        Builder1 repositoryUrl( @Nonnull final String repositoryUrl);
    }
    /**
    * Builder helper class.
    */
    public interface Builder1 {
        /**
        * Set the revision of this {@link BckndArgoCDApplicationBaseData} instance.
        *
        * @param revision  revision to synchronise
        * @return The BckndArgoCDApplicationBaseData builder.
        */
        Builder2 revision( @Nonnull final String revision);
    }
    /**
    * Builder helper class.
    */
    public interface Builder2 {
        /**
        * Set the path of this {@link BckndArgoCDApplicationBaseData} instance.
        *
        * @param path  path within the repository to synchronise
        * @return The BckndArgoCDApplicationBaseData instance.
        */
        BckndArgoCDApplicationBaseData path( @Nonnull final String path);
    }

}

