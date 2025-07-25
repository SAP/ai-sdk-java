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

/** A response containing documents created or updated, retrieved from the server. */
// CHECKSTYLE:OFF
public class DocumentsListResponse
// CHECKSTYLE:ON
{
  @JsonProperty("documents")
  private List<DocumentWithoutChunks> documents = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentsListResponse. */
  protected DocumentsListResponse() {}

  /**
   * Set the documents of this {@link DocumentsListResponse} instance and return the same instance.
   *
   * @param documents The documents of this {@link DocumentsListResponse}
   * @return The same instance of this {@link DocumentsListResponse} class
   */
  @Nonnull
  public DocumentsListResponse documents(@Nonnull final List<DocumentWithoutChunks> documents) {
    this.documents = documents;
    return this;
  }

  /**
   * Add one documents instance to this {@link DocumentsListResponse}.
   *
   * @param documentsItem The documents that should be added
   * @return The same instance of type {@link DocumentsListResponse}
   */
  @Nonnull
  public DocumentsListResponse addDocumentsItem(
      @Nonnull final DocumentWithoutChunks documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<>();
    }
    this.documents.add(documentsItem);
    return this;
  }

  /**
   * Get documents
   *
   * @return documents The documents of this {@link DocumentsListResponse} instance.
   */
  @Nonnull
  public List<DocumentWithoutChunks> getDocuments() {
    return documents;
  }

  /**
   * Set the documents of this {@link DocumentsListResponse} instance.
   *
   * @param documents The documents of this {@link DocumentsListResponse}
   */
  public void setDocuments(@Nonnull final List<DocumentWithoutChunks> documents) {
    this.documents = documents;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentsListResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentsListResponse} instance.
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
          "DocumentsListResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DocumentsListResponse} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (documents != null) declaredFields.put("documents", documents);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DocumentsListResponse} instance. If the map
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
    final DocumentsListResponse documentsListResponse = (DocumentsListResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, documentsListResponse.cloudSdkCustomFields)
        && Objects.equals(this.documents, documentsListResponse.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documents, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentsListResponse {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DocumentsListResponse}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (documents) -> new DocumentsListResponse().documents(documents);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the documents of this {@link DocumentsListResponse} instance.
     *
     * @param documents The documents of this {@link DocumentsListResponse}
     * @return The DocumentsListResponse instance.
     */
    DocumentsListResponse documents(@Nonnull final List<DocumentWithoutChunks> documents);

    /**
     * Set the documents of this {@link DocumentsListResponse} instance.
     *
     * @param documents The documents of this {@link DocumentsListResponse}
     * @return The DocumentsListResponse instance.
     */
    default DocumentsListResponse documents(@Nonnull final DocumentWithoutChunks... documents) {
      return documents(Arrays.asList(documents));
    }
  }
}
