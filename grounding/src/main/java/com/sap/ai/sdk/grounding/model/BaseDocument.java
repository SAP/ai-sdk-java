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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Base class for documents, document requests and responses. */
@Beta // CHECKSTYLE:OFF
public class BaseDocument
// CHECKSTYLE:ON
{
  @JsonProperty("chunks")
  private List<TextOnlyBaseChunk> chunks = new ArrayList<>();

  @JsonProperty("metadata")
  private List<DocumentKeyValueListPair> metadata = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BaseDocument. */
  protected BaseDocument() {}

  /**
   * Set the chunks of this {@link BaseDocument} instance and return the same instance.
   *
   * @param chunks The chunks of this {@link BaseDocument}
   * @return The same instance of this {@link BaseDocument} class
   */
  @Nonnull
  public BaseDocument chunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
    return this;
  }

  /**
   * Add one chunks instance to this {@link BaseDocument}.
   *
   * @param chunksItem The chunks that should be added
   * @return The same instance of type {@link BaseDocument}
   */
  @Nonnull
  public BaseDocument addChunksItem(@Nonnull final TextOnlyBaseChunk chunksItem) {
    if (this.chunks == null) {
      this.chunks = new ArrayList<>();
    }
    this.chunks.add(chunksItem);
    return this;
  }

  /**
   * Get chunks
   *
   * @return chunks The chunks of this {@link BaseDocument} instance.
   */
  @Nonnull
  public List<TextOnlyBaseChunk> getChunks() {
    return chunks;
  }

  /**
   * Set the chunks of this {@link BaseDocument} instance.
   *
   * @param chunks The chunks of this {@link BaseDocument}
   */
  public void setChunks(@Nonnull final List<TextOnlyBaseChunk> chunks) {
    this.chunks = chunks;
  }

  /**
   * Set the metadata of this {@link BaseDocument} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link BaseDocument}
   * @return The same instance of this {@link BaseDocument} class
   */
  @Nonnull
  public BaseDocument metadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link BaseDocument}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link BaseDocument}
   */
  @Nonnull
  public BaseDocument addMetadataItem(@Nonnull final DocumentKeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link BaseDocument} instance.
   */
  @Nonnull
  public List<DocumentKeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link BaseDocument} instance.
   *
   * @param metadata The metadata of this {@link BaseDocument}
   */
  public void setMetadata(@Nonnull final List<DocumentKeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BaseDocument}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BaseDocument} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("BaseDocument has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BaseDocument} instance. If the map previously
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
    final BaseDocument baseDocument = (BaseDocument) o;
    return Objects.equals(this.cloudSdkCustomFields, baseDocument.cloudSdkCustomFields)
        && Objects.equals(this.chunks, baseDocument.chunks)
        && Objects.equals(this.metadata, baseDocument.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chunks, metadata, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BaseDocument {\n");
    sb.append("    chunks: ").append(toIndentedString(chunks)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link BaseDocument} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (chunks) -> (metadata) -> new BaseDocument().chunks(chunks).metadata(metadata);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the chunks of this {@link BaseDocument} instance.
     *
     * @param chunks The chunks of this {@link BaseDocument}
     * @return The BaseDocument builder.
     */
    Builder1 chunks(@Nonnull final List<TextOnlyBaseChunk> chunks);

    /**
     * Set the chunks of this {@link BaseDocument} instance.
     *
     * @param chunks The chunks of this {@link BaseDocument}
     * @return The BaseDocument builder.
     */
    default Builder1 chunks(@Nonnull final TextOnlyBaseChunk... chunks) {
      return chunks(Arrays.asList(chunks));
    }
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the metadata of this {@link BaseDocument} instance.
     *
     * @param metadata The metadata of this {@link BaseDocument}
     * @return The BaseDocument instance.
     */
    BaseDocument metadata(@Nonnull final List<DocumentKeyValueListPair> metadata);

    /**
     * Set the metadata of this {@link BaseDocument} instance.
     *
     * @param metadata The metadata of this {@link BaseDocument}
     * @return The BaseDocument instance.
     */
    default BaseDocument metadata(@Nonnull final DocumentKeyValueListPair... metadata) {
      return metadata(Arrays.asList(metadata));
    }
  }
}
