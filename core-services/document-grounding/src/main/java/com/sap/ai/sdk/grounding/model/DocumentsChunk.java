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

/** DocumentsChunk */
// CHECKSTYLE:OFF
public class DocumentsChunk
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("metadata")
  private List<KeyValueListPair> metadata = new ArrayList<>();

  @JsonProperty("documents")
  private List<DocumentOutput> documents = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentsChunk. */
  protected DocumentsChunk() {}

  /**
   * Set the id of this {@link DocumentsChunk} instance and return the same instance.
   *
   * @param id The id of this {@link DocumentsChunk}
   * @return The same instance of this {@link DocumentsChunk} class
   */
  @Nonnull
  public DocumentsChunk id(@Nonnull final UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id The id of this {@link DocumentsChunk} instance.
   */
  @Nonnull
  public UUID getId() {
    return id;
  }

  /**
   * Set the id of this {@link DocumentsChunk} instance.
   *
   * @param id The id of this {@link DocumentsChunk}
   */
  public void setId(@Nonnull final UUID id) {
    this.id = id;
  }

  /**
   * Set the title of this {@link DocumentsChunk} instance and return the same instance.
   *
   * @param title The title of this {@link DocumentsChunk}
   * @return The same instance of this {@link DocumentsChunk} class
   */
  @Nonnull
  public DocumentsChunk title(@Nonnull final String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
   * @return title The title of this {@link DocumentsChunk} instance.
   */
  @Nonnull
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of this {@link DocumentsChunk} instance.
   *
   * @param title The title of this {@link DocumentsChunk}
   */
  public void setTitle(@Nonnull final String title) {
    this.title = title;
  }

  /**
   * Set the metadata of this {@link DocumentsChunk} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link DocumentsChunk}
   * @return The same instance of this {@link DocumentsChunk} class
   */
  @Nonnull
  public DocumentsChunk metadata(@Nullable final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link DocumentsChunk}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link DocumentsChunk}
   */
  @Nonnull
  public DocumentsChunk addMetadataItem(@Nonnull final KeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link DocumentsChunk} instance.
   */
  @Nonnull
  public List<KeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link DocumentsChunk} instance.
   *
   * @param metadata The metadata of this {@link DocumentsChunk}
   */
  public void setMetadata(@Nullable final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the documents of this {@link DocumentsChunk} instance and return the same instance.
   *
   * @param documents The documents of this {@link DocumentsChunk}
   * @return The same instance of this {@link DocumentsChunk} class
   */
  @Nonnull
  public DocumentsChunk documents(@Nonnull final List<DocumentOutput> documents) {
    this.documents = documents;
    return this;
  }

  /**
   * Add one documents instance to this {@link DocumentsChunk}.
   *
   * @param documentsItem The documents that should be added
   * @return The same instance of type {@link DocumentsChunk}
   */
  @Nonnull
  public DocumentsChunk addDocumentsItem(@Nonnull final DocumentOutput documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<>();
    }
    this.documents.add(documentsItem);
    return this;
  }

  /**
   * Get documents
   *
   * @return documents The documents of this {@link DocumentsChunk} instance.
   */
  @Nonnull
  public List<DocumentOutput> getDocuments() {
    return documents;
  }

  /**
   * Set the documents of this {@link DocumentsChunk} instance.
   *
   * @param documents The documents of this {@link DocumentsChunk}
   */
  public void setDocuments(@Nonnull final List<DocumentOutput> documents) {
    this.documents = documents;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentsChunk}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentsChunk} instance.
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
      throw new NoSuchElementException("DocumentsChunk has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DocumentsChunk} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (title != null) declaredFields.put("title", title);
    if (metadata != null) declaredFields.put("metadata", metadata);
    if (documents != null) declaredFields.put("documents", documents);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DocumentsChunk} instance. If the map previously
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
    final DocumentsChunk documentsChunk = (DocumentsChunk) o;
    return Objects.equals(this.cloudSdkCustomFields, documentsChunk.cloudSdkCustomFields)
        && Objects.equals(this.id, documentsChunk.id)
        && Objects.equals(this.title, documentsChunk.title)
        && Objects.equals(this.metadata, documentsChunk.metadata)
        && Objects.equals(this.documents, documentsChunk.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, metadata, documents, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentsChunk {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DocumentsChunk}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (id) ->
        (title) -> (documents) -> new DocumentsChunk().id(id).title(title).documents(documents);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link DocumentsChunk} instance.
     *
     * @param id The id of this {@link DocumentsChunk}
     * @return The DocumentsChunk builder.
     */
    Builder1 id(@Nonnull final UUID id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the title of this {@link DocumentsChunk} instance.
     *
     * @param title The title of this {@link DocumentsChunk}
     * @return The DocumentsChunk builder.
     */
    Builder2 title(@Nonnull final String title);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the documents of this {@link DocumentsChunk} instance.
     *
     * @param documents The documents of this {@link DocumentsChunk}
     * @return The DocumentsChunk instance.
     */
    DocumentsChunk documents(@Nonnull final List<DocumentOutput> documents);

    /**
     * Set the documents of this {@link DocumentsChunk} instance.
     *
     * @param documents The documents of this {@link DocumentsChunk}
     * @return The DocumentsChunk instance.
     */
    default DocumentsChunk documents(@Nonnull final DocumentOutput... documents) {
      return documents(Arrays.asList(documents));
    }
  }
}
