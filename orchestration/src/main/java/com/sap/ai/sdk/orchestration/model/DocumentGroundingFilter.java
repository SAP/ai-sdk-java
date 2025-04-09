/*
 * Internal Orchestration Service API
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** DocumentGroundingFilter */
// CHECKSTYLE:OFF
public class DocumentGroundingFilter implements GroundingModuleConfigConfigFiltersInner
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private String id;

  @JsonProperty("search_config")
  private GroundingFilterSearchConfiguration searchConfig;

  @JsonProperty("data_repositories")
  private List<String> dataRepositories = new ArrayList<>(Arrays.asList("*"));

  @JsonProperty("data_repository_type")
  private DataRepositoryType dataRepositoryType;

  @JsonProperty("data_repository_metadata")
  private List<KeyValueListPair> dataRepositoryMetadata = new ArrayList<>();

  @JsonProperty("document_metadata")
  private List<SearchDocumentKeyValueListPair> documentMetadata = new ArrayList<>();

  @JsonProperty("chunk_metadata")
  private List<KeyValueListPair> chunkMetadata = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentGroundingFilter. */
  protected DocumentGroundingFilter() {}

  /**
   * Set the id of this {@link DocumentGroundingFilter} instance and return the same instance.
   *
   * @param id Identifier of this SearchFilter - unique per request.
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter id(@Nullable final String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this SearchFilter - unique per request.
   *
   * @return id The id of this {@link DocumentGroundingFilter} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link DocumentGroundingFilter} instance.
   *
   * @param id Identifier of this SearchFilter - unique per request.
   */
  public void setId(@Nullable final String id) {
    this.id = id;
  }

  /**
   * Set the searchConfig of this {@link DocumentGroundingFilter} instance and return the same
   * instance.
   *
   * @param searchConfig The searchConfig of this {@link DocumentGroundingFilter}
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter searchConfig(
      @Nullable final GroundingFilterSearchConfiguration searchConfig) {
    this.searchConfig = searchConfig;
    return this;
  }

  /**
   * Get searchConfig
   *
   * @return searchConfig The searchConfig of this {@link DocumentGroundingFilter} instance.
   */
  @Nonnull
  public GroundingFilterSearchConfiguration getSearchConfig() {
    return searchConfig;
  }

  /**
   * Set the searchConfig of this {@link DocumentGroundingFilter} instance.
   *
   * @param searchConfig The searchConfig of this {@link DocumentGroundingFilter}
   */
  public void setSearchConfig(@Nullable final GroundingFilterSearchConfiguration searchConfig) {
    this.searchConfig = searchConfig;
  }

  /**
   * Set the dataRepositories of this {@link DocumentGroundingFilter} instance and return the same
   * instance.
   *
   * @param dataRepositories Specify [&#39;*&#39;] to search across all DataRepositories or give a
   *     specific list of DataRepository ids.
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter dataRepositories(@Nullable final List<String> dataRepositories) {
    this.dataRepositories = dataRepositories;
    return this;
  }

  /**
   * Add one dataRepositories instance to this {@link DocumentGroundingFilter}.
   *
   * @param dataRepositoriesItem The dataRepositories that should be added
   * @return The same instance of type {@link DocumentGroundingFilter}
   */
  @Nonnull
  public DocumentGroundingFilter addDataRepositoriesItem(
      @Nonnull final String dataRepositoriesItem) {
    if (this.dataRepositories == null) {
      this.dataRepositories = new ArrayList<>(Arrays.asList("*"));
    }
    this.dataRepositories.add(dataRepositoriesItem);
    return this;
  }

  /**
   * Specify [&#39;*&#39;] to search across all DataRepositories or give a specific list of
   * DataRepository ids.
   *
   * @return dataRepositories The dataRepositories of this {@link DocumentGroundingFilter} instance.
   */
  @Nonnull
  public List<String> getDataRepositories() {
    return dataRepositories;
  }

  /**
   * Set the dataRepositories of this {@link DocumentGroundingFilter} instance.
   *
   * @param dataRepositories Specify [&#39;*&#39;] to search across all DataRepositories or give a
   *     specific list of DataRepository ids.
   */
  public void setDataRepositories(@Nullable final List<String> dataRepositories) {
    this.dataRepositories = dataRepositories;
  }

  /**
   * Set the dataRepositoryType of this {@link DocumentGroundingFilter} instance and return the same
   * instance.
   *
   * @param dataRepositoryType The dataRepositoryType of this {@link DocumentGroundingFilter}
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter dataRepositoryType(
      @Nonnull final DataRepositoryType dataRepositoryType) {
    this.dataRepositoryType = dataRepositoryType;
    return this;
  }

  /**
   * Get dataRepositoryType
   *
   * @return dataRepositoryType The dataRepositoryType of this {@link DocumentGroundingFilter}
   *     instance.
   */
  @Nonnull
  public DataRepositoryType getDataRepositoryType() {
    return dataRepositoryType;
  }

  /**
   * Set the dataRepositoryType of this {@link DocumentGroundingFilter} instance.
   *
   * @param dataRepositoryType The dataRepositoryType of this {@link DocumentGroundingFilter}
   */
  public void setDataRepositoryType(@Nonnull final DataRepositoryType dataRepositoryType) {
    this.dataRepositoryType = dataRepositoryType;
  }

  /**
   * Set the dataRepositoryMetadata of this {@link DocumentGroundingFilter} instance and return the
   * same instance.
   *
   * @param dataRepositoryMetadata Restrict DataRepositories considered during search to those
   *     annotated with the given metadata. Useful when combined with
   *     dataRepositories&#x3D;[&#39;*&#39;]
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter dataRepositoryMetadata(
      @Nullable final List<KeyValueListPair> dataRepositoryMetadata) {
    this.dataRepositoryMetadata = dataRepositoryMetadata;
    return this;
  }

  /**
   * Add one dataRepositoryMetadata instance to this {@link DocumentGroundingFilter}.
   *
   * @param dataRepositoryMetadataItem The dataRepositoryMetadata that should be added
   * @return The same instance of type {@link DocumentGroundingFilter}
   */
  @Nonnull
  public DocumentGroundingFilter addDataRepositoryMetadataItem(
      @Nonnull final KeyValueListPair dataRepositoryMetadataItem) {
    if (this.dataRepositoryMetadata == null) {
      this.dataRepositoryMetadata = new ArrayList<>();
    }
    this.dataRepositoryMetadata.add(dataRepositoryMetadataItem);
    return this;
  }

  /**
   * Restrict DataRepositories considered during search to those annotated with the given metadata.
   * Useful when combined with dataRepositories&#x3D;[&#39;*&#39;]
   *
   * @return dataRepositoryMetadata The dataRepositoryMetadata of this {@link
   *     DocumentGroundingFilter} instance.
   */
  @Nonnull
  public List<KeyValueListPair> getDataRepositoryMetadata() {
    return dataRepositoryMetadata;
  }

  /**
   * Set the dataRepositoryMetadata of this {@link DocumentGroundingFilter} instance.
   *
   * @param dataRepositoryMetadata Restrict DataRepositories considered during search to those
   *     annotated with the given metadata. Useful when combined with
   *     dataRepositories&#x3D;[&#39;*&#39;]
   */
  public void setDataRepositoryMetadata(
      @Nullable final List<KeyValueListPair> dataRepositoryMetadata) {
    this.dataRepositoryMetadata = dataRepositoryMetadata;
  }

  /**
   * Set the documentMetadata of this {@link DocumentGroundingFilter} instance and return the same
   * instance.
   *
   * @param documentMetadata Restrict documents considered during search to those annotated with the
   *     given metadata.
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter documentMetadata(
      @Nullable final List<SearchDocumentKeyValueListPair> documentMetadata) {
    this.documentMetadata = documentMetadata;
    return this;
  }

  /**
   * Add one documentMetadata instance to this {@link DocumentGroundingFilter}.
   *
   * @param documentMetadataItem The documentMetadata that should be added
   * @return The same instance of type {@link DocumentGroundingFilter}
   */
  @Nonnull
  public DocumentGroundingFilter addDocumentMetadataItem(
      @Nonnull final SearchDocumentKeyValueListPair documentMetadataItem) {
    if (this.documentMetadata == null) {
      this.documentMetadata = new ArrayList<>();
    }
    this.documentMetadata.add(documentMetadataItem);
    return this;
  }

  /**
   * Restrict documents considered during search to those annotated with the given metadata.
   *
   * @return documentMetadata The documentMetadata of this {@link DocumentGroundingFilter} instance.
   */
  @Nonnull
  public List<SearchDocumentKeyValueListPair> getDocumentMetadata() {
    return documentMetadata;
  }

  /**
   * Set the documentMetadata of this {@link DocumentGroundingFilter} instance.
   *
   * @param documentMetadata Restrict documents considered during search to those annotated with the
   *     given metadata.
   */
  public void setDocumentMetadata(
      @Nullable final List<SearchDocumentKeyValueListPair> documentMetadata) {
    this.documentMetadata = documentMetadata;
  }

  /**
   * Set the chunkMetadata of this {@link DocumentGroundingFilter} instance and return the same
   * instance.
   *
   * @param chunkMetadata Restrict chunks considered during search to those with the given metadata.
   * @return The same instance of this {@link DocumentGroundingFilter} class
   */
  @Nonnull
  public DocumentGroundingFilter chunkMetadata(
      @Nullable final List<KeyValueListPair> chunkMetadata) {
    this.chunkMetadata = chunkMetadata;
    return this;
  }

  /**
   * Add one chunkMetadata instance to this {@link DocumentGroundingFilter}.
   *
   * @param chunkMetadataItem The chunkMetadata that should be added
   * @return The same instance of type {@link DocumentGroundingFilter}
   */
  @Nonnull
  public DocumentGroundingFilter addChunkMetadataItem(
      @Nonnull final KeyValueListPair chunkMetadataItem) {
    if (this.chunkMetadata == null) {
      this.chunkMetadata = new ArrayList<>();
    }
    this.chunkMetadata.add(chunkMetadataItem);
    return this;
  }

  /**
   * Restrict chunks considered during search to those with the given metadata.
   *
   * @return chunkMetadata The chunkMetadata of this {@link DocumentGroundingFilter} instance.
   */
  @Nonnull
  public List<KeyValueListPair> getChunkMetadata() {
    return chunkMetadata;
  }

  /**
   * Set the chunkMetadata of this {@link DocumentGroundingFilter} instance.
   *
   * @param chunkMetadata Restrict chunks considered during search to those with the given metadata.
   */
  public void setChunkMetadata(@Nullable final List<KeyValueListPair> chunkMetadata) {
    this.chunkMetadata = chunkMetadata;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentGroundingFilter}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentGroundingFilter} instance.
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
          "DocumentGroundingFilter has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DocumentGroundingFilter} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (id != null) declaredFields.put("id", id);
    if (searchConfig != null) declaredFields.put("searchConfig", searchConfig);
    if (dataRepositories != null) declaredFields.put("dataRepositories", dataRepositories);
    if (dataRepositoryType != null) declaredFields.put("dataRepositoryType", dataRepositoryType);
    if (dataRepositoryMetadata != null)
      declaredFields.put("dataRepositoryMetadata", dataRepositoryMetadata);
    if (documentMetadata != null) declaredFields.put("documentMetadata", documentMetadata);
    if (chunkMetadata != null) declaredFields.put("chunkMetadata", chunkMetadata);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DocumentGroundingFilter} instance. If the map
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
    final DocumentGroundingFilter documentGroundingFilter = (DocumentGroundingFilter) o;
    return Objects.equals(this.cloudSdkCustomFields, documentGroundingFilter.cloudSdkCustomFields)
        && Objects.equals(this.id, documentGroundingFilter.id)
        && Objects.equals(this.searchConfig, documentGroundingFilter.searchConfig)
        && Objects.equals(this.dataRepositories, documentGroundingFilter.dataRepositories)
        && Objects.equals(this.dataRepositoryType, documentGroundingFilter.dataRepositoryType)
        && Objects.equals(
            this.dataRepositoryMetadata, documentGroundingFilter.dataRepositoryMetadata)
        && Objects.equals(this.documentMetadata, documentGroundingFilter.documentMetadata)
        && Objects.equals(this.chunkMetadata, documentGroundingFilter.chunkMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        searchConfig,
        dataRepositories,
        dataRepositoryType,
        dataRepositoryMetadata,
        documentMetadata,
        chunkMetadata,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentGroundingFilter {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    searchConfig: ").append(toIndentedString(searchConfig)).append("\n");
    sb.append("    dataRepositories: ").append(toIndentedString(dataRepositories)).append("\n");
    sb.append("    dataRepositoryType: ").append(toIndentedString(dataRepositoryType)).append("\n");
    sb.append("    dataRepositoryMetadata: ")
        .append(toIndentedString(dataRepositoryMetadata))
        .append("\n");
    sb.append("    documentMetadata: ").append(toIndentedString(documentMetadata)).append("\n");
    sb.append("    chunkMetadata: ").append(toIndentedString(chunkMetadata)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * DocumentGroundingFilter} instance with all required arguments.
   */
  public static Builder create() {
    return (dataRepositoryType) ->
        new DocumentGroundingFilter().dataRepositoryType(dataRepositoryType);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the dataRepositoryType of this {@link DocumentGroundingFilter} instance.
     *
     * @param dataRepositoryType The dataRepositoryType of this {@link DocumentGroundingFilter}
     * @return The DocumentGroundingFilter instance.
     */
    DocumentGroundingFilter dataRepositoryType(
        @Nonnull final DataRepositoryType dataRepositoryType);
  }
}
