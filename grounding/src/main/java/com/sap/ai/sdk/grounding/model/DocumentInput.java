/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 * The version of the OpenAPI document: 0.1.0
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

/** A single document stored in a collection by ID. */
@Beta // CHECKSTYLE:OFF
public class DocumentInput
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

  /** Default constructor for DocumentInput. */
  protected DocumentInput() {
    super();
  }

  /**
   * Set the chunks of this {@link DocumentInput} instance and return the same instance.
   *
   * @param chunks The chunks of this {@link DocumentInput}
   * @return The same instance of this {@link DocumentInput} class
   */
  @Nonnull
  public DocumentInput chunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
    return this;
  }

  /**
   * Add one chunks instance to this {@link DocumentInput}.
   *
   * @param chunksItem The chunks that should be added
   * @return The same instance of type {@link DocumentInput}
   */
  @Nonnull
  public DocumentInput addChunksItem(@Nonnull final TextOnlyBaseChunk chunksItem) {
    if (this.chunks == null) {
      this.chunks = new ArrayList<>();
    }
    this.chunks.add(chunksItem);
    return this;
  }

  /**
   * Get chunks
   *
   * @return chunks The chunks of this {@link DocumentInput} instance.
   */
  @Nonnull
  public List<TextOnlyBaseChunk> getChunks() {
    return chunks;
  }

  /**
   * Set the chunks of this {@link DocumentInput} instance.
   *
   * @param chunks The chunks of this {@link DocumentInput}
   */
  public void setChunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
  }

  /**
   * Set the metadata of this {@link DocumentInput} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link DocumentInput}
   * @return The same instance of this {@link DocumentInput} class
   */
  @Nonnull
  public DocumentInput metadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link DocumentInput}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link DocumentInput}
   */
  @Nonnull
  public DocumentInput addMetadataItem(@Nonnull final DocumentKeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link DocumentInput} instance.
   */
  @Nonnull
  public List<DocumentKeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link DocumentInput} instance.
   *
   * @param metadata The metadata of this {@link DocumentInput}
   */
  public void setMetadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the id of this {@link DocumentInput} instance and return the same instance.
   *
   * @param id Unique identifier of a document.
   * @return The same instance of this {@link DocumentInput} class
   */
  @Nonnull
  public DocumentInput id(@Nonnull final UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of a document.
   *
   * @return id The id of this {@link DocumentInput} instance.
   */
  @Nonnull
  public UUID getId() {
    return id;
  }

  /**
   * Set the id of this {@link DocumentInput} instance.
   *
   * @param id Unique identifier of a document.
   */
  public void setId(@Nonnull final UUID id) {
    this.id = id;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentInput}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentInput} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("DocumentInput has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DocumentInput} instance. If the map previously
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
    final DocumentInput documentInput = (DocumentInput) o;
    return Objects.equals(this.cloudSdkCustomFields, documentInput.cloudSdkCustomFields)
        && Objects.equals(this.chunks, documentInput.chunks)
        && Objects.equals(this.metadata, documentInput.metadata)
        && Objects.equals(this.id, documentInput.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chunks, metadata, id, cloudSdkCustomFields, super.hashCode());
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentInput {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DocumentInput} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (chunks) ->
        (metadata) -> (id) -> new DocumentInput().chunks(chunks).metadata(metadata).id(id);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the chunks of this {@link DocumentInput} instance.
     *
     * @param chunks The chunks of this {@link DocumentInput}
     * @return The DocumentInput builder.
     */
    Builder1 chunks(@Nonnull final List<TextOnlyBaseChunk> chunks);

    /**
     * Set the chunks of this {@link DocumentInput} instance.
     *
     * @param chunks The chunks of this {@link DocumentInput}
     * @return The DocumentInput builder.
     */
    default Builder1 chunks(@Nonnull final TextOnlyBaseChunk... chunks) {
      return chunks(Arrays.asList(chunks));
    }
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the metadata of this {@link DocumentInput} instance.
     *
     * @param metadata The metadata of this {@link DocumentInput}
     * @return The DocumentInput builder.
     */
    Builder2 metadata(@Nonnull final List<DocumentKeyValueListPair> metadata);

    /**
     * Set the metadata of this {@link DocumentInput} instance.
     *
     * @param metadata The metadata of this {@link DocumentInput}
     * @return The DocumentInput builder.
     */
    default Builder2 metadata(@Nonnull final DocumentKeyValueListPair... metadata) {
      return metadata(Arrays.asList(metadata));
    }
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the id of this {@link DocumentInput} instance.
     *
     * @param id Unique identifier of a document.
     * @return The DocumentInput instance.
     */
    DocumentInput id(@Nonnull final UUID id);
  }
}
