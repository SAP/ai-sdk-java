/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
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

/** BckndArgoCDRepositoryData */
@Beta // CHECKSTYLE:OFF
public class BckndArgoCDRepositoryData
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndArgoCDRepositoryData. */
  protected BckndArgoCDRepositoryData() {}

  /**
   * Set the name of this {@link BckndArgoCDRepositoryData} instance and return the same instance.
   *
   * @param name Name of the repository
   * @return The same instance of this {@link BckndArgoCDRepositoryData} class
   */
  @Nonnull
  public BckndArgoCDRepositoryData name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the repository
   *
   * @return name The name of this {@link BckndArgoCDRepositoryData} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndArgoCDRepositoryData} instance.
   *
   * @param name Name of the repository
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the url of this {@link BckndArgoCDRepositoryData} instance and return the same instance.
   *
   * @param url URL of the repository to synchronise
   * @return The same instance of this {@link BckndArgoCDRepositoryData} class
   */
  @Nonnull
  public BckndArgoCDRepositoryData url(@Nonnull final String url) {
    this.url = url;
    return this;
  }

  /**
   * URL of the repository to synchronise
   *
   * @return url The url of this {@link BckndArgoCDRepositoryData} instance.
   */
  @Nonnull
  public String getUrl() {
    return url;
  }

  /**
   * Set the url of this {@link BckndArgoCDRepositoryData} instance.
   *
   * @param url URL of the repository to synchronise
   */
  public void setUrl(@Nonnull final String url) {
    this.url = url;
  }

  /**
   * Set the username of this {@link BckndArgoCDRepositoryData} instance and return the same
   * instance.
   *
   * @param username Username for read-access to the repository
   * @return The same instance of this {@link BckndArgoCDRepositoryData} class
   */
  @Nonnull
  public BckndArgoCDRepositoryData username(@Nonnull final String username) {
    this.username = username;
    return this;
  }

  /**
   * Username for read-access to the repository
   *
   * @return username The username of this {@link BckndArgoCDRepositoryData} instance.
   */
  @Nonnull
  public String getUsername() {
    return username;
  }

  /**
   * Set the username of this {@link BckndArgoCDRepositoryData} instance.
   *
   * @param username Username for read-access to the repository
   */
  public void setUsername(@Nonnull final String username) {
    this.username = username;
  }

  /**
   * Set the password of this {@link BckndArgoCDRepositoryData} instance and return the same
   * instance.
   *
   * @param password Password for read-access to the repository
   * @return The same instance of this {@link BckndArgoCDRepositoryData} class
   */
  @Nonnull
  public BckndArgoCDRepositoryData password(@Nonnull final String password) {
    this.password = password;
    return this;
  }

  /**
   * Password for read-access to the repository
   *
   * @return password The password of this {@link BckndArgoCDRepositoryData} instance.
   */
  @Nonnull
  public String getPassword() {
    return password;
  }

  /**
   * Set the password of this {@link BckndArgoCDRepositoryData} instance.
   *
   * @param password Password for read-access to the repository
   */
  public void setPassword(@Nonnull final String password) {
    this.password = password;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndArgoCDRepositoryData}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndArgoCDRepositoryData} instance.
   *
   * @deprecated Use {@link #toMap()} instead.
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  @Deprecated
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndArgoCDRepositoryData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link BckndArgoCDRepositoryData} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (url != null) declaredFields.put("url", url);
    if (username != null) declaredFields.put("username", username);
    if (password != null) declaredFields.put("password", password);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link BckndArgoCDRepositoryData} instance. If the map
   * previously contained a mapping for the key, the old value is replaced by the specified value.
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
    final BckndArgoCDRepositoryData bckndArgoCDRepositoryData = (BckndArgoCDRepositoryData) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndArgoCDRepositoryData.cloudSdkCustomFields)
        && Objects.equals(this.name, bckndArgoCDRepositoryData.name)
        && Objects.equals(this.url, bckndArgoCDRepositoryData.url)
        && Objects.equals(this.username, bckndArgoCDRepositoryData.username)
        && Objects.equals(this.password, bckndArgoCDRepositoryData.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, url, username, password, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndArgoCDRepositoryData {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
   * BckndArgoCDRepositoryData} instance with all required arguments.
   */
  public static Builder create() {
    return (url) ->
        (username) ->
            (password) ->
                new BckndArgoCDRepositoryData().url(url).username(username).password(password);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the url of this {@link BckndArgoCDRepositoryData} instance.
     *
     * @param url URL of the repository to synchronise
     * @return The BckndArgoCDRepositoryData builder.
     */
    Builder1 url(@Nonnull final String url);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the username of this {@link BckndArgoCDRepositoryData} instance.
     *
     * @param username Username for read-access to the repository
     * @return The BckndArgoCDRepositoryData builder.
     */
    Builder2 username(@Nonnull final String username);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the password of this {@link BckndArgoCDRepositoryData} instance.
     *
     * @param password Password for read-access to the repository
     * @return The BckndArgoCDRepositoryData instance.
     */
    BckndArgoCDRepositoryData password(@Nonnull final String password);
  }
}
