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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A response containing information about a newly created, single document. */
@Beta // CHECKSTYLE:OFF
public class DocumentResponse
// CHECKSTYLE:ON
{
  @JsonProperty("chunks")
  private List<TextOnlyBaseChunk> chunks = new ArrayList<>();

  @JsonProperty("metadata")
  private List<DocumentKeyValueListPair> metadata = new ArrayList<>();

  @JsonProperty("id")
  private UUID id;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentResponse. */
  protected DocumentResponse() {}

  /**
   * Set the chunks of this {@link DocumentResponse} instance and return the same instance.
   *
   * @param chunks The chunks of this {@link DocumentResponse}
   * @return The same instance of this {@link DocumentResponse} class
   */
  @Nonnull
  public DocumentResponse chunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
    return this;
  }

  /**
   * Add one chunks instance to this {@link DocumentResponse}.
   *
   * @param chunksItem The chunks that should be added
   * @return The same instance of type {@link DocumentResponse}
   */
  @Nonnull
  public DocumentResponse addChunksItem(@Nonnull final TextOnlyBaseChunk chunksItem) {
    if (this.chunks == null) {
      this.chunks = new ArrayList<>();
    }
    this.chunks.add(chunksItem);
    return this;
  }

  /**
   * Get chunks
   *
   * @return chunks The chunks of this {@link DocumentResponse} instance.
   */
  @Nonnull
  public List<TextOnlyBaseChunk> getChunks() {
    return chunks;
  }

  /**
   * Set the chunks of this {@link DocumentResponse} instance.
   *
   * @param chunks The chunks of this {@link DocumentResponse}
   */
  public void setChunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
  }

  /**
   * Set the metadata of this {@link DocumentResponse} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link DocumentResponse}
   * @return The same instance of this {@link DocumentResponse} class
   */
  @Nonnull
  public DocumentResponse metadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link DocumentResponse}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link DocumentResponse}
   */
  @Nonnull
  public DocumentResponse addMetadataItem(@Nonnull final DocumentKeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link DocumentResponse} instance.
   */
  @Nonnull
  public List<DocumentKeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link DocumentResponse} instance.
   *
   * @param metadata The metadata of this {@link DocumentResponse}
   */
  public void setMetadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the id of this {@link DocumentResponse} instance and return the same instance.
   *
   * @param id Unique identifier of a document.
   * @return The same instance of this {@link DocumentResponse} class
   */
  @Nonnull
  public DocumentResponse id(@Nonnull final UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of a document.
   *
   * @return id The id of this {@link DocumentResponse} instance.
   */
  @Nonnull
  public UUID getId() {
    return id;
  }

  /**
   * Set the id of this {@link DocumentResponse} instance.
   *
   * @param id Unique identifier of a document.
   */
  public void setId(@Nonnull final UUID id) {
    this.id = id;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentResponse} instance.
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
      throw new NoSuchElementException("DocumentResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DocumentResponse} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (chunks != null) declaredFields.put("chunks", chunks);
    if (metadata != null) declaredFields.put("metadata", metadata);
    if (id != null) declaredFields.put("id", id);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DocumentResponse} instance. If the map previously
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
    final DocumentResponse documentResponse = (DocumentResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, documentResponse.cloudSdkCustomFields)
        && Objects.equals(this.chunks, documentResponse.chunks)
        && Objects.equals(this.metadata, documentResponse.metadata)
        && Objects.equals(this.id, documentResponse.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chunks, metadata, id, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentResponse {\n");
    sb.append("    chunks: ").append(toIndentedString(chunks)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DocumentResponse}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (chunks) ->
        (metadata) -> (id) -> new DocumentResponse().chunks(chunks).metadata(metadata).id(id);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the chunks of this {@link DocumentResponse} instance.
     *
     * @param chunks The chunks of this {@link DocumentResponse}
     * @return The DocumentResponse builder.
     */
    Builder1 chunks(@Nonnull final List<TextOnlyBaseChunk> chunks);

    /**
     * Set the chunks of this {@link DocumentResponse} instance.
     *
     * @param chunks The chunks of this {@link DocumentResponse}
     * @return The DocumentResponse builder.
     */
    default Builder1 chunks(@Nonnull final TextOnlyBaseChunk... chunks) {
      return chunks(Arrays.asList(chunks));
    }
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the metadata of this {@link DocumentResponse} instance.
     *
     * @param metadata The metadata of this {@link DocumentResponse}
     * @return The DocumentResponse builder.
     */
    Builder2 metadata(@Nonnull final List<DocumentKeyValueListPair> metadata);

    /**
     * Set the metadata of this {@link DocumentResponse} instance.
     *
     * @param metadata The metadata of this {@link DocumentResponse}
     * @return The DocumentResponse builder.
     */
    default Builder2 metadata(@Nonnull final DocumentKeyValueListPair... metadata) {
      return metadata(Arrays.asList(metadata));
    }
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the id of this {@link DocumentResponse} instance.
     *
     * @param id Unique identifier of a document.
     * @return The DocumentResponse instance.
     */
    DocumentResponse id(@Nonnull final UUID id);
  }
}
