/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.34.0
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

/** MetaCapabilities */
// CHECKSTYLE:OFF
public class MetaCapabilities
// CHECKSTYLE:ON
{
  @JsonProperty("runtimeIdentifier")
  private String runtimeIdentifier;

  @JsonProperty("runtimeApiVersion")
  private String runtimeApiVersion;

  @JsonProperty("description")
  private String description;

  @JsonProperty("aiApi")
  private MetaAiApi aiApi;

  @JsonProperty("extensions")
  private MetaExtensions extensions;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected MetaCapabilities() {}

  /**
   * Set the runtimeIdentifier of this {@link MetaCapabilities} instance and return the same
   * instance.
   *
   * @param runtimeIdentifier The name of the runtime
   * @return The same instance of this {@link MetaCapabilities} class
   */
  @Nonnull
  public MetaCapabilities runtimeIdentifier(@Nullable final String runtimeIdentifier) {
    this.runtimeIdentifier = runtimeIdentifier;
    return this;
  }

  /**
   * The name of the runtime
   *
   * @return runtimeIdentifier The runtimeIdentifier of this {@link MetaCapabilities} instance.
   */
  @Nonnull
  public String getRuntimeIdentifier() {
    return runtimeIdentifier;
  }

  /**
   * Set the runtimeIdentifier of this {@link MetaCapabilities} instance.
   *
   * @param runtimeIdentifier The name of the runtime
   */
  public void setRuntimeIdentifier(@Nullable final String runtimeIdentifier) {
    this.runtimeIdentifier = runtimeIdentifier;
  }

  /**
   * Set the runtimeApiVersion of this {@link MetaCapabilities} instance and return the same
   * instance.
   *
   * @param runtimeApiVersion The runtimeApiVersion of this {@link MetaCapabilities}
   * @return The same instance of this {@link MetaCapabilities} class
   */
  @Nonnull
  public MetaCapabilities runtimeApiVersion(@Nullable final String runtimeApiVersion) {
    this.runtimeApiVersion = runtimeApiVersion;
    return this;
  }

  /**
   * Get runtimeApiVersion
   *
   * @return runtimeApiVersion The runtimeApiVersion of this {@link MetaCapabilities} instance.
   */
  @Nonnull
  public String getRuntimeApiVersion() {
    return runtimeApiVersion;
  }

  /**
   * Set the runtimeApiVersion of this {@link MetaCapabilities} instance.
   *
   * @param runtimeApiVersion The runtimeApiVersion of this {@link MetaCapabilities}
   */
  public void setRuntimeApiVersion(@Nullable final String runtimeApiVersion) {
    this.runtimeApiVersion = runtimeApiVersion;
  }

  /**
   * Set the description of this {@link MetaCapabilities} instance and return the same instance.
   *
   * @param description The description of this {@link MetaCapabilities}
   * @return The same instance of this {@link MetaCapabilities} class
   */
  @Nonnull
  public MetaCapabilities description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description The description of this {@link MetaCapabilities} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link MetaCapabilities} instance.
   *
   * @param description The description of this {@link MetaCapabilities}
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the aiApi of this {@link MetaCapabilities} instance and return the same instance.
   *
   * @param aiApi The aiApi of this {@link MetaCapabilities}
   * @return The same instance of this {@link MetaCapabilities} class
   */
  @Nonnull
  public MetaCapabilities aiApi(@Nonnull final MetaAiApi aiApi) {
    this.aiApi = aiApi;
    return this;
  }

  /**
   * Get aiApi
   *
   * @return aiApi The aiApi of this {@link MetaCapabilities} instance.
   */
  @Nonnull
  public MetaAiApi getAiApi() {
    return aiApi;
  }

  /**
   * Set the aiApi of this {@link MetaCapabilities} instance.
   *
   * @param aiApi The aiApi of this {@link MetaCapabilities}
   */
  public void setAiApi(@Nonnull final MetaAiApi aiApi) {
    this.aiApi = aiApi;
  }

  /**
   * Set the extensions of this {@link MetaCapabilities} instance and return the same instance.
   *
   * @param extensions The extensions of this {@link MetaCapabilities}
   * @return The same instance of this {@link MetaCapabilities} class
   */
  @Nonnull
  public MetaCapabilities extensions(@Nullable final MetaExtensions extensions) {
    this.extensions = extensions;
    return this;
  }

  /**
   * Get extensions
   *
   * @return extensions The extensions of this {@link MetaCapabilities} instance.
   */
  @Nonnull
  public MetaExtensions getExtensions() {
    return extensions;
  }

  /**
   * Set the extensions of this {@link MetaCapabilities} instance.
   *
   * @param extensions The extensions of this {@link MetaCapabilities}
   */
  public void setExtensions(@Nullable final MetaExtensions extensions) {
    this.extensions = extensions;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MetaCapabilities}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MetaCapabilities} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("MetaCapabilities has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link MetaCapabilities} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
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
    final MetaCapabilities metaCapabilities = (MetaCapabilities) o;
    return Objects.equals(this.cloudSdkCustomFields, metaCapabilities.cloudSdkCustomFields)
        && Objects.equals(this.runtimeIdentifier, metaCapabilities.runtimeIdentifier)
        && Objects.equals(this.runtimeApiVersion, metaCapabilities.runtimeApiVersion)
        && Objects.equals(this.description, metaCapabilities.description)
        && Objects.equals(this.aiApi, metaCapabilities.aiApi)
        && Objects.equals(this.extensions, metaCapabilities.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        runtimeIdentifier, runtimeApiVersion, description, aiApi, extensions, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaCapabilities {\n");
    sb.append("    runtimeIdentifier: ").append(toIndentedString(runtimeIdentifier)).append("\n");
    sb.append("    runtimeApiVersion: ").append(toIndentedString(runtimeApiVersion)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    aiApi: ").append(toIndentedString(aiApi)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link MetaCapabilities}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (aiApi) -> new MetaCapabilities().aiApi(aiApi);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the aiApi of this {@link MetaCapabilities} instance.
     *
     * @param aiApi The aiApi of this {@link MetaCapabilities}
     * @return The MetaCapabilities instance.
     */
    MetaCapabilities aiApi(@Nonnull final MetaAiApi aiApi);
  }
}
