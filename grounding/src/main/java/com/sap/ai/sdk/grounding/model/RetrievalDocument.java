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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** RetrievalDocument */
@Beta // CHECKSTYLE:OFF
public class RetrievalDocument
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("metadata")
  private List<DocumentKeyValueListPair> metadata = new ArrayList<>();

  @JsonProperty("chunks")
  private List<Chunk> chunks = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RetrievalDocument. */
  protected RetrievalDocument() {}

  /**
   * Set the id of this {@link RetrievalDocument} instance and return the same instance.
   *
   * @param id The id of this {@link RetrievalDocument}
   * @return The same instance of this {@link RetrievalDocument} class
   */
  @Nonnull
  public RetrievalDocument id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id The id of this {@link RetrievalDocument} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link RetrievalDocument} instance.
   *
   * @param id The id of this {@link RetrievalDocument}
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the metadata of this {@link RetrievalDocument} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link RetrievalDocument}
   * @return The same instance of this {@link RetrievalDocument} class
   */
  @Nonnull
  public RetrievalDocument metadata(@Nullable final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link RetrievalDocument}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link RetrievalDocument}
   */
  @Nonnull
  public RetrievalDocument addMetadataItem(@Nonnull final DocumentKeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link RetrievalDocument} instance.
   */
  @Nonnull
  public List<DocumentKeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link RetrievalDocument} instance.
   *
   * @param metadata The metadata of this {@link RetrievalDocument}
   */
  public void setMetadata(@Nullable final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the chunks of this {@link RetrievalDocument} instance and return the same instance.
   *
   * @param chunks The chunks of this {@link RetrievalDocument}
   * @return The same instance of this {@link RetrievalDocument} class
   */
  @Nonnull
  public RetrievalDocument chunks(@Nonnull final List<Chunk> chunks) {
    this.chunks = chunks;
    return this;
  }

  /**
   * Add one chunks instance to this {@link RetrievalDocument}.
   *
   * @param chunksItem The chunks that should be added
   * @return The same instance of type {@link RetrievalDocument}
   */
  @Nonnull
  public RetrievalDocument addChunksItem(@Nonnull final Chunk chunksItem) {
    if (this.chunks == null) {
      this.chunks = new ArrayList<>();
    }
    this.chunks.add(chunksItem);
    return this;
  }

  /**
   * Get chunks
   *
   * @return chunks The chunks of this {@link RetrievalDocument} instance.
   */
  @Nonnull
  public List<Chunk> getChunks() {
    return chunks;
  }

  /**
   * Set the chunks of this {@link RetrievalDocument} instance.
   *
   * @param chunks The chunks of this {@link RetrievalDocument}
   */
  public void setChunks(@Nonnull final List<Chunk> chunks) {
    this.chunks = chunks;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RetrievalDocument}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RetrievalDocument} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("RetrievalDocument has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RetrievalDocument} instance. If the map
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
    final RetrievalDocument retrievalDocument = (RetrievalDocument) o;
    return Objects.equals(this.cloudSdkCustomFields, retrievalDocument.cloudSdkCustomFields)
        && Objects.equals(this.id, retrievalDocument.id)
        && Objects.equals(this.metadata, retrievalDocument.metadata)
        && Objects.equals(this.chunks, retrievalDocument.chunks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, metadata, chunks, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RetrievalDocument {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    chunks: ").append(toIndentedString(chunks)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link RetrievalDocument}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (id) -> (chunks) -> new RetrievalDocument().id(id).chunks(chunks);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link RetrievalDocument} instance.
     *
     * @param id The id of this {@link RetrievalDocument}
     * @return The RetrievalDocument builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the chunks of this {@link RetrievalDocument} instance.
     *
     * @param chunks The chunks of this {@link RetrievalDocument}
     * @return The RetrievalDocument instance.
     */
    RetrievalDocument chunks(@Nonnull final List<Chunk> chunks);

    /**
     * Set the chunks of this {@link RetrievalDocument} instance.
     *
     * @param chunks The chunks of this {@link RetrievalDocument}
     * @return The RetrievalDocument instance.
     */
    default RetrievalDocument chunks(@Nonnull final Chunk... chunks) {
      return chunks(Arrays.asList(chunks));
    }
  }
}
