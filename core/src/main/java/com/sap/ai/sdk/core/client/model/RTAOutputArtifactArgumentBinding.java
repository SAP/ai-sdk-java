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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Result of execution */
// CHECKSTYLE:OFF
public class RTAOutputArtifactArgumentBinding
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected RTAOutputArtifactArgumentBinding() {}

  /**
   * Set the name of this {@link RTAOutputArtifactArgumentBinding} instance and return the same
   * instance.
   *
   * @param name The name of this {@link RTAOutputArtifactArgumentBinding}
   * @return The same instance of this {@link RTAOutputArtifactArgumentBinding} class
   */
  @Nonnull
  public RTAOutputArtifactArgumentBinding name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name The name of this {@link RTAOutputArtifactArgumentBinding} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link RTAOutputArtifactArgumentBinding} instance.
   *
   * @param name The name of this {@link RTAOutputArtifactArgumentBinding}
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the url of this {@link RTAOutputArtifactArgumentBinding} instance and return the same
   * instance.
   *
   * @param url Reference to the location of the artifact. Note, the credentials will be found in a
   *     secret called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be
   *     assumed.
   * @return The same instance of this {@link RTAOutputArtifactArgumentBinding} class
   */
  @Nonnull
  public RTAOutputArtifactArgumentBinding url(@Nullable final String url) {
    this.url = url;
    return this;
  }

  /**
   * Reference to the location of the artifact. Note, the credentials will be found in a secret
   * called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be assumed.
   *
   * @return url The url of this {@link RTAOutputArtifactArgumentBinding} instance.
   */
  @Nonnull
  public String getUrl() {
    return url;
  }

  /**
   * Set the url of this {@link RTAOutputArtifactArgumentBinding} instance.
   *
   * @param url Reference to the location of the artifact. Note, the credentials will be found in a
   *     secret called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be
   *     assumed.
   */
  public void setUrl(@Nullable final String url) {
    this.url = url;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAOutputArtifactArgumentBinding}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAOutputArtifactArgumentBinding}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "RTAOutputArtifactArgumentBinding has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAOutputArtifactArgumentBinding} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final RTAOutputArtifactArgumentBinding rtAOutputArtifactArgumentBinding =
        (RTAOutputArtifactArgumentBinding) o;
    return Objects.equals(
            this.cloudSdkCustomFields, rtAOutputArtifactArgumentBinding.cloudSdkCustomFields)
        && Objects.equals(this.name, rtAOutputArtifactArgumentBinding.name)
        && Objects.equals(this.url, rtAOutputArtifactArgumentBinding.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, url, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAOutputArtifactArgumentBinding {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
   * RTAOutputArtifactArgumentBinding} instance with all required arguments.
   */
  public static Builder create() {
    return (name) -> new RTAOutputArtifactArgumentBinding().name(name);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link RTAOutputArtifactArgumentBinding} instance.
     *
     * @param name The name of this {@link RTAOutputArtifactArgumentBinding}
     * @return The RTAOutputArtifactArgumentBinding instance.
     */
    RTAOutputArtifactArgumentBinding name(@Nonnull final String name);
  }
}
