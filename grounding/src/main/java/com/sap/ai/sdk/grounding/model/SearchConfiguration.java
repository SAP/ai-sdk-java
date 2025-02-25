/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

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

/** SearchConfiguration */
@Beta // CHECKSTYLE:OFF
public class SearchConfiguration
// CHECKSTYLE:ON
{
  @JsonProperty("maxChunkCount")
  private Integer maxChunkCount;

  @JsonProperty("maxDocumentCount")
  private Integer maxDocumentCount;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for SearchConfiguration. */
  protected SearchConfiguration() {}

  /**
   * Set the maxChunkCount of this {@link SearchConfiguration} instance and return the same
   * instance.
   *
   * @param maxChunkCount Maximum number of chunks to be returned. Cannot be used with
   *     &#39;maxDocumentCount&#39;. Minimum: 0 Maximum: 0
   * @return The same instance of this {@link SearchConfiguration} class
   */
  @Nonnull
  public SearchConfiguration maxChunkCount(@Nullable final Integer maxChunkCount) {
    this.maxChunkCount = maxChunkCount;
    return this;
  }

  /**
   * Maximum number of chunks to be returned. Cannot be used with &#39;maxDocumentCount&#39;.
   * minimum: 0 maximum: 0
   *
   * @return maxChunkCount The maxChunkCount of this {@link SearchConfiguration} instance.
   */
  @Nonnull
  public Integer getMaxChunkCount() {
    return maxChunkCount;
  }

  /**
   * Set the maxChunkCount of this {@link SearchConfiguration} instance.
   *
   * @param maxChunkCount Maximum number of chunks to be returned. Cannot be used with
   *     &#39;maxDocumentCount&#39;. Minimum: 0 Maximum: 0
   */
  public void setMaxChunkCount(@Nullable final Integer maxChunkCount) {
    this.maxChunkCount = maxChunkCount;
  }

  /**
   * Set the maxDocumentCount of this {@link SearchConfiguration} instance and return the same
   * instance.
   *
   * @param maxDocumentCount [Only supports &#39;vector&#39; dataRepositoryType] - Maximum number of
   *     documents to be returned. Cannot be used with &#39;maxChunkCount&#39;. If maxDocumentCount
   *     is given, then only one chunk per document is returned. Minimum: 0 Maximum: 0
   * @return The same instance of this {@link SearchConfiguration} class
   */
  @Nonnull
  public SearchConfiguration maxDocumentCount(@Nullable final Integer maxDocumentCount) {
    this.maxDocumentCount = maxDocumentCount;
    return this;
  }

  /**
   * [Only supports &#39;vector&#39; dataRepositoryType] - Maximum number of documents to be
   * returned. Cannot be used with &#39;maxChunkCount&#39;. If maxDocumentCount is given, then only
   * one chunk per document is returned. minimum: 0 maximum: 0
   *
   * @return maxDocumentCount The maxDocumentCount of this {@link SearchConfiguration} instance.
   */
  @Nonnull
  public Integer getMaxDocumentCount() {
    return maxDocumentCount;
  }

  /**
   * Set the maxDocumentCount of this {@link SearchConfiguration} instance.
   *
   * @param maxDocumentCount [Only supports &#39;vector&#39; dataRepositoryType] - Maximum number of
   *     documents to be returned. Cannot be used with &#39;maxChunkCount&#39;. If maxDocumentCount
   *     is given, then only one chunk per document is returned. Minimum: 0 Maximum: 0
   */
  public void setMaxDocumentCount(@Nullable final Integer maxDocumentCount) {
    this.maxDocumentCount = maxDocumentCount;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link SearchConfiguration}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link SearchConfiguration} instance.
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
          "SearchConfiguration has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link SearchConfiguration} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (maxChunkCount != null) declaredFields.put("maxChunkCount", maxChunkCount);
    if (maxDocumentCount != null) declaredFields.put("maxDocumentCount", maxDocumentCount);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link SearchConfiguration} instance. If the map
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
    final SearchConfiguration searchConfiguration = (SearchConfiguration) o;
    return Objects.equals(this.cloudSdkCustomFields, searchConfiguration.cloudSdkCustomFields)
        && Objects.equals(this.maxChunkCount, searchConfiguration.maxChunkCount)
        && Objects.equals(this.maxDocumentCount, searchConfiguration.maxDocumentCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxChunkCount, maxDocumentCount, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class SearchConfiguration {\n");
    sb.append("    maxChunkCount: ").append(toIndentedString(maxChunkCount)).append("\n");
    sb.append("    maxDocumentCount: ").append(toIndentedString(maxDocumentCount)).append("\n");
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

  /** Create a new {@link SearchConfiguration} instance. No arguments are required. */
  public static SearchConfiguration create() {
    return new SearchConfiguration();
  }
}
