

/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sap.ai.sdk.orchestration.client.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.client.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.client.model.GroundingFilterSearchConfiguration;
import com.sap.ai.sdk.orchestration.client.model.KeyValueListPair;
import com.sap.ai.sdk.orchestration.client.model.SearchDocumentKeyValueListPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * GroundingModuleConfigConfigFiltersInner
 */
// CHECKSTYLE:OFF
public class GroundingModuleConfigConfigFiltersInner 
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private Object id;

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

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected GroundingModuleConfigConfigFiltersInner() {  }

  /**
   * Set the id of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param id  Identifier of this SearchFilter - unique per request.
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner id( @Nullable final Object id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of this SearchFilter - unique per request.
   * @return id  The id of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nullable public Object getId() {
    return id;
  }

  /**
   * Set the id of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param id  Identifier of this SearchFilter - unique per request.
   */
  public void setId( @Nullable final Object id) {
    this.id = id;
  }

  /**
   * Set the searchConfig of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param searchConfig  The searchConfig of this {@link GroundingModuleConfigConfigFiltersInner}
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner searchConfig( @Nullable final GroundingFilterSearchConfiguration searchConfig) {
    this.searchConfig = searchConfig;
    return this;
  }

  /**
   * Get searchConfig
   * @return searchConfig  The searchConfig of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nullable public GroundingFilterSearchConfiguration getSearchConfig() {
    return searchConfig;
  }

  /**
   * Set the searchConfig of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param searchConfig  The searchConfig of this {@link GroundingModuleConfigConfigFiltersInner}
   */
  public void setSearchConfig( @Nullable final GroundingFilterSearchConfiguration searchConfig) {
    this.searchConfig = searchConfig;
  }

  /**
   * Set the dataRepositories of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param dataRepositories  Specify [&#39;*&#39;] to search across all DataRepositories or give a specific list of DataRepository ids.
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner dataRepositories( @Nullable final List<String> dataRepositories) {
    this.dataRepositories = dataRepositories;
    return this;
  }
  /**
   * Add one dataRepositories instance to this {@link GroundingModuleConfigConfigFiltersInner}.
   * @param dataRepositoriesItem The dataRepositories that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfigFiltersInner}
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner addDataRepositoriesItem( @Nonnull final String dataRepositoriesItem) {
    if (this.dataRepositories == null) {
      this.dataRepositories = new ArrayList<>(Arrays.asList("*"));
    }
    this.dataRepositories.add(dataRepositoriesItem);
    return this;
  }

  /**
   * Specify [&#39;*&#39;] to search across all DataRepositories or give a specific list of DataRepository ids.
   * @return dataRepositories  The dataRepositories of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nonnull public List<String> getDataRepositories() {
    return dataRepositories;
  }

  /**
   * Set the dataRepositories of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param dataRepositories  Specify [&#39;*&#39;] to search across all DataRepositories or give a specific list of DataRepository ids.
   */
  public void setDataRepositories( @Nullable final List<String> dataRepositories) {
    this.dataRepositories = dataRepositories;
  }

  /**
   * Set the dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param dataRepositoryType  The dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner}
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner dataRepositoryType( @Nullable final DataRepositoryType dataRepositoryType) {
    this.dataRepositoryType = dataRepositoryType;
    return this;
  }

  /**
   * Get dataRepositoryType
   * @return dataRepositoryType  The dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nullable public DataRepositoryType getDataRepositoryType() {
    return dataRepositoryType;
  }

  /**
   * Set the dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param dataRepositoryType  The dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner}
   */
  public void setDataRepositoryType( @Nullable final DataRepositoryType dataRepositoryType) {
    this.dataRepositoryType = dataRepositoryType;
  }

  /**
   * Set the dataRepositoryMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param dataRepositoryMetadata  Restrict DataRepositories considered during search to those annotated with the given metadata. Useful when combined with dataRepositories&#x3D;[&#39;*&#39;]
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner dataRepositoryMetadata( @Nullable final List<KeyValueListPair> dataRepositoryMetadata) {
    this.dataRepositoryMetadata = dataRepositoryMetadata;
    return this;
  }
  /**
   * Add one dataRepositoryMetadata instance to this {@link GroundingModuleConfigConfigFiltersInner}.
   * @param dataRepositoryMetadataItem The dataRepositoryMetadata that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfigFiltersInner}
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner addDataRepositoryMetadataItem( @Nonnull final KeyValueListPair dataRepositoryMetadataItem) {
    if (this.dataRepositoryMetadata == null) {
      this.dataRepositoryMetadata = new ArrayList<>();
    }
    this.dataRepositoryMetadata.add(dataRepositoryMetadataItem);
    return this;
  }

  /**
   * Restrict DataRepositories considered during search to those annotated with the given metadata. Useful when combined with dataRepositories&#x3D;[&#39;*&#39;]
   * @return dataRepositoryMetadata  The dataRepositoryMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nonnull public List<KeyValueListPair> getDataRepositoryMetadata() {
    return dataRepositoryMetadata;
  }

  /**
   * Set the dataRepositoryMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param dataRepositoryMetadata  Restrict DataRepositories considered during search to those annotated with the given metadata. Useful when combined with dataRepositories&#x3D;[&#39;*&#39;]
   */
  public void setDataRepositoryMetadata( @Nullable final List<KeyValueListPair> dataRepositoryMetadata) {
    this.dataRepositoryMetadata = dataRepositoryMetadata;
  }

  /**
   * Set the documentMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param documentMetadata  Restrict documents considered during search to those annotated with the given metadata.
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner documentMetadata( @Nullable final List<SearchDocumentKeyValueListPair> documentMetadata) {
    this.documentMetadata = documentMetadata;
    return this;
  }
  /**
   * Add one documentMetadata instance to this {@link GroundingModuleConfigConfigFiltersInner}.
   * @param documentMetadataItem The documentMetadata that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfigFiltersInner}
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner addDocumentMetadataItem( @Nonnull final SearchDocumentKeyValueListPair documentMetadataItem) {
    if (this.documentMetadata == null) {
      this.documentMetadata = new ArrayList<>();
    }
    this.documentMetadata.add(documentMetadataItem);
    return this;
  }

  /**
   * Restrict documents considered during search to those annotated with the given metadata.
   * @return documentMetadata  The documentMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nonnull public List<SearchDocumentKeyValueListPair> getDocumentMetadata() {
    return documentMetadata;
  }

  /**
   * Set the documentMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param documentMetadata  Restrict documents considered during search to those annotated with the given metadata.
   */
  public void setDocumentMetadata( @Nullable final List<SearchDocumentKeyValueListPair> documentMetadata) {
    this.documentMetadata = documentMetadata;
  }

  /**
   * Set the chunkMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance and return the same instance.
   *
   * @param chunkMetadata  Restrict chunks considered during search to those with the given metadata.
   * @return The same instance of this {@link GroundingModuleConfigConfigFiltersInner} class
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner chunkMetadata( @Nullable final List<KeyValueListPair> chunkMetadata) {
    this.chunkMetadata = chunkMetadata;
    return this;
  }
  /**
   * Add one chunkMetadata instance to this {@link GroundingModuleConfigConfigFiltersInner}.
   * @param chunkMetadataItem The chunkMetadata that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfigFiltersInner}
   */
  @Nonnull public GroundingModuleConfigConfigFiltersInner addChunkMetadataItem( @Nonnull final KeyValueListPair chunkMetadataItem) {
    if (this.chunkMetadata == null) {
      this.chunkMetadata = new ArrayList<>();
    }
    this.chunkMetadata.add(chunkMetadataItem);
    return this;
  }

  /**
   * Restrict chunks considered during search to those with the given metadata.
   * @return chunkMetadata  The chunkMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   */
  @Nonnull public List<KeyValueListPair> getChunkMetadata() {
    return chunkMetadata;
  }

  /**
   * Set the chunkMetadata of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   *
   * @param chunkMetadata  Restrict chunks considered during search to those with the given metadata.
   */
  public void setChunkMetadata( @Nullable final List<KeyValueListPair> chunkMetadata) {
    this.chunkMetadata = chunkMetadata;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link GroundingModuleConfigConfigFiltersInner}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link GroundingModuleConfigConfigFiltersInner} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("GroundingModuleConfigConfigFiltersInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link GroundingModuleConfigConfigFiltersInner} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
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
    final GroundingModuleConfigConfigFiltersInner groundingModuleConfigConfigFiltersInner = (GroundingModuleConfigConfigFiltersInner) o;
    return Objects.equals(this.cloudSdkCustomFields, groundingModuleConfigConfigFiltersInner.cloudSdkCustomFields) &&
        Objects.equals(this.id, groundingModuleConfigConfigFiltersInner.id) &&
        Objects.equals(this.searchConfig, groundingModuleConfigConfigFiltersInner.searchConfig) &&
        Objects.equals(this.dataRepositories, groundingModuleConfigConfigFiltersInner.dataRepositories) &&
        Objects.equals(this.dataRepositoryType, groundingModuleConfigConfigFiltersInner.dataRepositoryType) &&
        Objects.equals(this.dataRepositoryMetadata, groundingModuleConfigConfigFiltersInner.dataRepositoryMetadata) &&
        Objects.equals(this.documentMetadata, groundingModuleConfigConfigFiltersInner.documentMetadata) &&
        Objects.equals(this.chunkMetadata, groundingModuleConfigConfigFiltersInner.chunkMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, searchConfig, dataRepositories, dataRepositoryType, dataRepositoryMetadata, documentMetadata, chunkMetadata, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class GroundingModuleConfigConfigFiltersInner {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    searchConfig: ").append(toIndentedString(searchConfig)).append("\n");
    sb.append("    dataRepositories: ").append(toIndentedString(dataRepositories)).append("\n");
    sb.append("    dataRepositoryType: ").append(toIndentedString(dataRepositoryType)).append("\n");
    sb.append("    dataRepositoryMetadata: ").append(toIndentedString(dataRepositoryMetadata)).append("\n");
    sb.append("    documentMetadata: ").append(toIndentedString(documentMetadata)).append("\n");
    sb.append("    chunkMetadata: ").append(toIndentedString(chunkMetadata)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

    /**
    * Create a type-safe, fluent-api builder object to construct a new {@link GroundingModuleConfigConfigFiltersInner} instance with all required arguments.
    */
    public static Builder create() {
        return (id) -> (dataRepositoryType) -> new GroundingModuleConfigConfigFiltersInner().id(id).dataRepositoryType(dataRepositoryType);
    }
    /**
    * Builder helper class.
    */
    public interface Builder {
        /**
        * Set the id of this {@link GroundingModuleConfigConfigFiltersInner} instance.
        *
        * @param id  Identifier of this SearchFilter - unique per request.
        * @return The GroundingModuleConfigConfigFiltersInner builder.
        */
        Builder1 id( @Nullable final Object id);
    }
    /**
    * Builder helper class.
    */
    public interface Builder1 {
        /**
        * Set the dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner} instance.
        *
        * @param dataRepositoryType  The dataRepositoryType of this {@link GroundingModuleConfigConfigFiltersInner}
        * @return The GroundingModuleConfigConfigFiltersInner instance.
        */
        GroundingModuleConfigConfigFiltersInner dataRepositoryType( @Nullable final DataRepositoryType dataRepositoryType);
    }

}

