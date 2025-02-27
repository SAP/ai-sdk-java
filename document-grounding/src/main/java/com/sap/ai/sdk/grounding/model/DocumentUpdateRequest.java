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

/**
 * An update request containing one or more documents to update existing documents in a collection
 * by ID.
 */
@Beta // CHECKSTYLE:OFF
public class DocumentUpdateRequest
// CHECKSTYLE:ON
{
  @JsonProperty("documents")
  private List<DocumentInput> documents = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentUpdateRequest. */
  protected DocumentUpdateRequest() {}

  /**
   * Set the documents of this {@link DocumentUpdateRequest} instance and return the same instance.
   *
   * @param documents The documents of this {@link DocumentUpdateRequest}
   * @return The same instance of this {@link DocumentUpdateRequest} class
   */
  @Nonnull
  public DocumentUpdateRequest documents(@Nonnull final List<DocumentInput> documents) {
    this.documents = documents;
    return this;
  }

  /**
   * Add one documents instance to this {@link DocumentUpdateRequest}.
   *
   * @param documentsItem The documents that should be added
   * @return The same instance of type {@link DocumentUpdateRequest}
   */
  @Nonnull
  public DocumentUpdateRequest addDocumentsItem(@Nonnull final DocumentInput documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<>();
    }
    this.documents.add(documentsItem);
    return this;
  }

  /**
   * Get documents
   *
   * @return documents The documents of this {@link DocumentUpdateRequest} instance.
   */
  @Nonnull
  public List<DocumentInput> getDocuments() {
    return documents;
  }

  /**
   * Set the documents of this {@link DocumentUpdateRequest} instance.
   *
   * @param documents The documents of this {@link DocumentUpdateRequest}
   */
  public void setDocuments(@Nonnull final List<DocumentInput> documents) {
    this.documents = documents;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentUpdateRequest}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentUpdateRequest} instance.
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
          "DocumentUpdateRequest has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DocumentUpdateRequest} instance including
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
   * Set an unrecognizable property of this {@link DocumentUpdateRequest} instance. If the map
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
    final DocumentUpdateRequest documentUpdateRequest = (DocumentUpdateRequest) o;
    return Objects.equals(this.cloudSdkCustomFields, documentUpdateRequest.cloudSdkCustomFields)
        && Objects.equals(this.documents, documentUpdateRequest.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documents, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentUpdateRequest {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DocumentUpdateRequest}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (documents) -> new DocumentUpdateRequest().documents(documents);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the documents of this {@link DocumentUpdateRequest} instance.
     *
     * @param documents The documents of this {@link DocumentUpdateRequest}
     * @return The DocumentUpdateRequest instance.
     */
    DocumentUpdateRequest documents(@Nonnull final List<DocumentInput> documents);

    /**
     * Set the documents of this {@link DocumentUpdateRequest} instance.
     *
     * @param documents The documents of this {@link DocumentUpdateRequest}
     * @return The DocumentUpdateRequest instance.
     */
    default DocumentUpdateRequest documents(@Nonnull final DocumentInput... documents) {
      return documents(Arrays.asList(documents));
    }
  }
}
