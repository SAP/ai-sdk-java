/*
 * Grounding
 * Grounding is a service designed to handle data-related tasks, such as grounding and retrieval, using vector databases. It provides specialized data retrieval through these databases, grounding the retrieval process with your own external and context-relevant data. Grounding combines generative AI capabilities with the ability to use real-time, precise data to improve decision-making and business operations for specific AI-driven business solutions.
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

/** Document */
// CHECKSTYLE:OFF
public class Document
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

  /** Default constructor for Document. */
  protected Document() {}

  /**
   * Set the id of this {@link Document} instance and return the same instance.
   *
   * @param id The id of this {@link Document}
   * @return The same instance of this {@link Document} class
   */
  @Nonnull
  public Document id(@Nonnull final String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id The id of this {@link Document} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link Document} instance.
   *
   * @param id The id of this {@link Document}
   */
  public void setId(@Nonnull final String id) {
    this.id = id;
  }

  /**
   * Set the metadata of this {@link Document} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link Document}
   * @return The same instance of this {@link Document} class
   */
  @Nonnull
  public Document metadata(@Nullable final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link Document}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link Document}
   */
  @Nonnull
  public Document addMetadataItem(@Nonnull final DocumentKeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link Document} instance.
   */
  @Nonnull
  public List<DocumentKeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link Document} instance.
   *
   * @param metadata The metadata of this {@link Document}
   */
  public void setMetadata(@Nullable final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the chunks of this {@link Document} instance and return the same instance.
   *
   * @param chunks The chunks of this {@link Document}
   * @return The same instance of this {@link Document} class
   */
  @Nonnull
  public Document chunks(@Nonnull final List<Chunk> chunks) {
    this.chunks = chunks;
    return this;
  }

  /**
   * Add one chunks instance to this {@link Document}.
   *
   * @param chunksItem The chunks that should be added
   * @return The same instance of type {@link Document}
   */
  @Nonnull
  public Document addChunksItem(@Nonnull final Chunk chunksItem) {
    if (this.chunks == null) {
      this.chunks = new ArrayList<>();
    }
    this.chunks.add(chunksItem);
    return this;
  }

  /**
   * Get chunks
   *
   * @return chunks The chunks of this {@link Document} instance.
   */
  @Nonnull
  public List<Chunk> getChunks() {
    return chunks;
  }

  /**
   * Set the chunks of this {@link Document} instance.
   *
   * @param chunks The chunks of this {@link Document}
   */
  public void setChunks(@Nonnull final List<Chunk> chunks) {
    this.chunks = chunks;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link Document}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link Document} instance.
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
      throw new NoSuchElementException("Document has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link Document} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (metadata != null) declaredFields.put("metadata", metadata);
    if (chunks != null) declaredFields.put("chunks", chunks);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link Document} instance. If the map previously
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
    final Document document = (Document) o;
    return Objects.equals(this.cloudSdkCustomFields, document.cloudSdkCustomFields)
        && Objects.equals(this.id, document.id)
        && Objects.equals(this.metadata, document.metadata)
        && Objects.equals(this.chunks, document.chunks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, metadata, chunks, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link Document} instance with
   * all required arguments.
   */
  public static Builder create() {
    return (id) -> (chunks) -> new Document().id(id).chunks(chunks);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link Document} instance.
     *
     * @param id The id of this {@link Document}
     * @return The Document builder.
     */
    Builder1 id(@Nonnull final String id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the chunks of this {@link Document} instance.
     *
     * @param chunks The chunks of this {@link Document}
     * @return The Document instance.
     */
    Document chunks(@Nonnull final List<Chunk> chunks);

    /**
     * Set the chunks of this {@link Document} instance.
     *
     * @param chunks The chunks of this {@link Document}
     * @return The Document instance.
     */
    default Document chunks(@Nonnull final Chunk... chunks) {
      return chunks(Arrays.asList(chunks));
    }
  }
}
