/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.37.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** KubesubmitV4ApplicationsCreateRequest */
@Beta // CHECKSTYLE:OFF
public class KubesubmitV4ApplicationsCreateRequest
// CHECKSTYLE:ON
{
  @JsonProperty("repositoryUrl")
  private String repositoryUrl;

  @JsonProperty("revision")
  private String revision;

  @JsonProperty("path")
  private String path;

  @JsonProperty("applicationName")
  private String applicationName;

  @JsonProperty("repositoryName")
  private String repositoryName;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for KubesubmitV4ApplicationsCreateRequest. */
  protected KubesubmitV4ApplicationsCreateRequest() {}

  /**
   * Set the repositoryUrl of this {@link KubesubmitV4ApplicationsCreateRequest} instance and return
   * the same instance.
   *
   * @param repositoryUrl URL of the repository to synchronise
   * @return The same instance of this {@link KubesubmitV4ApplicationsCreateRequest} class
   */
  @Nonnull
  public KubesubmitV4ApplicationsCreateRequest repositoryUrl(@Nonnull final String repositoryUrl) {
    this.repositoryUrl = repositoryUrl;
    return this;
  }

  /**
   * URL of the repository to synchronise
   *
   * @return repositoryUrl The repositoryUrl of this {@link KubesubmitV4ApplicationsCreateRequest}
   *     instance.
   */
  @Nonnull
  public String getRepositoryUrl() {
    return repositoryUrl;
  }

  /**
   * Set the repositoryUrl of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param repositoryUrl URL of the repository to synchronise
   */
  public void setRepositoryUrl(@Nonnull final String repositoryUrl) {
    this.repositoryUrl = repositoryUrl;
  }

  /**
   * Set the revision of this {@link KubesubmitV4ApplicationsCreateRequest} instance and return the
   * same instance.
   *
   * @param revision revision to synchronise
   * @return The same instance of this {@link KubesubmitV4ApplicationsCreateRequest} class
   */
  @Nonnull
  public KubesubmitV4ApplicationsCreateRequest revision(@Nonnull final String revision) {
    this.revision = revision;
    return this;
  }

  /**
   * revision to synchronise
   *
   * @return revision The revision of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   */
  @Nonnull
  public String getRevision() {
    return revision;
  }

  /**
   * Set the revision of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param revision revision to synchronise
   */
  public void setRevision(@Nonnull final String revision) {
    this.revision = revision;
  }

  /**
   * Set the path of this {@link KubesubmitV4ApplicationsCreateRequest} instance and return the same
   * instance.
   *
   * @param path path within the repository to synchronise
   * @return The same instance of this {@link KubesubmitV4ApplicationsCreateRequest} class
   */
  @Nonnull
  public KubesubmitV4ApplicationsCreateRequest path(@Nonnull final String path) {
    this.path = path;
    return this;
  }

  /**
   * path within the repository to synchronise
   *
   * @return path The path of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   */
  @Nonnull
  public String getPath() {
    return path;
  }

  /**
   * Set the path of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param path path within the repository to synchronise
   */
  public void setPath(@Nonnull final String path) {
    this.path = path;
  }

  /**
   * Set the applicationName of this {@link KubesubmitV4ApplicationsCreateRequest} instance and
   * return the same instance.
   *
   * @param applicationName ArgoCD application name
   * @return The same instance of this {@link KubesubmitV4ApplicationsCreateRequest} class
   */
  @Nonnull
  public KubesubmitV4ApplicationsCreateRequest applicationName(
      @Nullable final String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  /**
   * ArgoCD application name
   *
   * @return applicationName The applicationName of this {@link
   *     KubesubmitV4ApplicationsCreateRequest} instance.
   */
  @Nonnull
  public String getApplicationName() {
    return applicationName;
  }

  /**
   * Set the applicationName of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param applicationName ArgoCD application name
   */
  public void setApplicationName(@Nullable final String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * Set the repositoryName of this {@link KubesubmitV4ApplicationsCreateRequest} instance and
   * return the same instance.
   *
   * @param repositoryName Name of the repository to synchronise
   * @return The same instance of this {@link KubesubmitV4ApplicationsCreateRequest} class
   */
  @Nonnull
  public KubesubmitV4ApplicationsCreateRequest repositoryName(
      @Nonnull final String repositoryName) {
    this.repositoryName = repositoryName;
    return this;
  }

  /**
   * Name of the repository to synchronise
   *
   * @return repositoryName The repositoryName of this {@link KubesubmitV4ApplicationsCreateRequest}
   *     instance.
   */
  @Nonnull
  public String getRepositoryName() {
    return repositoryName;
  }

  /**
   * Set the repositoryName of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param repositoryName Name of the repository to synchronise
   */
  public void setRepositoryName(@Nonnull final String repositoryName) {
    this.repositoryName = repositoryName;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * KubesubmitV4ApplicationsCreateRequest}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * KubesubmitV4ApplicationsCreateRequest} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "KubesubmitV4ApplicationsCreateRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
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
    final KubesubmitV4ApplicationsCreateRequest kubesubmitV4ApplicationsCreateRequest =
        (KubesubmitV4ApplicationsCreateRequest) o;
    return Objects.equals(
            this.cloudSdkCustomFields, kubesubmitV4ApplicationsCreateRequest.cloudSdkCustomFields)
        && Objects.equals(this.repositoryUrl, kubesubmitV4ApplicationsCreateRequest.repositoryUrl)
        && Objects.equals(this.revision, kubesubmitV4ApplicationsCreateRequest.revision)
        && Objects.equals(this.path, kubesubmitV4ApplicationsCreateRequest.path)
        && Objects.equals(
            this.applicationName, kubesubmitV4ApplicationsCreateRequest.applicationName)
        && Objects.equals(
            this.repositoryName, kubesubmitV4ApplicationsCreateRequest.repositoryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        repositoryUrl, revision, path, applicationName, repositoryName, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class KubesubmitV4ApplicationsCreateRequest {\n");
    sb.append("    repositoryUrl: ").append(toIndentedString(repositoryUrl)).append("\n");
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    repositoryName: ").append(toIndentedString(repositoryName)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * KubesubmitV4ApplicationsCreateRequest} instance with all required arguments.
   */
  public static Builder create() {
    return (repositoryUrl) ->
        (revision) ->
            (path) ->
                (repositoryName) ->
                    new KubesubmitV4ApplicationsCreateRequest()
                        .repositoryUrl(repositoryUrl)
                        .revision(revision)
                        .path(path)
                        .repositoryName(repositoryName);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the repositoryUrl of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
     *
     * @param repositoryUrl URL of the repository to synchronise
     * @return The KubesubmitV4ApplicationsCreateRequest builder.
     */
    Builder1 repositoryUrl(@Nonnull final String repositoryUrl);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the revision of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
     *
     * @param revision revision to synchronise
     * @return The KubesubmitV4ApplicationsCreateRequest builder.
     */
    Builder2 revision(@Nonnull final String revision);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the path of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
     *
     * @param path path within the repository to synchronise
     * @return The KubesubmitV4ApplicationsCreateRequest builder.
     */
    Builder3 path(@Nonnull final String path);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the repositoryName of this {@link KubesubmitV4ApplicationsCreateRequest} instance.
     *
     * @param repositoryName Name of the repository to synchronise
     * @return The KubesubmitV4ApplicationsCreateRequest instance.
     */
    KubesubmitV4ApplicationsCreateRequest repositoryName(@Nonnull final String repositoryName);
  }
}
