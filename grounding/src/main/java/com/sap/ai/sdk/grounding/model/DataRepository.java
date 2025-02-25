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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** DataRepository schema expected by Retrieval. */
@Beta // CHECKSTYLE:OFF
public class DataRepository
// CHECKSTYLE:ON
{
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("metadata")
  private List<KeyValueListPair> metadata = new ArrayList<>();

  @JsonProperty("type")
  private DataRepositoryType type;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DataRepository. */
  protected DataRepository() {}

  /**
   * Set the id of this {@link DataRepository} instance and return the same instance.
   *
   * @param id Unique identifier of this DataRepository.
   * @return The same instance of this {@link DataRepository} class
   */
  @Nonnull
  public DataRepository id(@Nonnull final UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of this DataRepository.
   *
   * @return id The id of this {@link DataRepository} instance.
   */
  @Nonnull
  public UUID getId() {
    return id;
  }

  /**
   * Set the id of this {@link DataRepository} instance.
   *
   * @param id Unique identifier of this DataRepository.
   */
  public void setId(@Nonnull final UUID id) {
    this.id = id;
  }

  /**
   * Set the title of this {@link DataRepository} instance and return the same instance.
   *
   * @param title The title of this {@link DataRepository}
   * @return The same instance of this {@link DataRepository} class
   */
  @Nonnull
  public DataRepository title(@Nonnull final String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   *
   * @return title The title of this {@link DataRepository} instance.
   */
  @Nonnull
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of this {@link DataRepository} instance.
   *
   * @param title The title of this {@link DataRepository}
   */
  public void setTitle(@Nonnull final String title) {
    this.title = title;
  }

  /**
   * Set the metadata of this {@link DataRepository} instance and return the same instance.
   *
   * @param metadata Metadata attached to DataRepository. Useful to later limit search to a subset
   *     of DataRepositories.
   * @return The same instance of this {@link DataRepository} class
   */
  @Nonnull
  public DataRepository metadata(@Nullable final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link DataRepository}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link DataRepository}
   */
  @Nonnull
  public DataRepository addMetadataItem(@Nonnull final KeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Metadata attached to DataRepository. Useful to later limit search to a subset of
   * DataRepositories.
   *
   * @return metadata The metadata of this {@link DataRepository} instance.
   */
  @Nonnull
  public List<KeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link DataRepository} instance.
   *
   * @param metadata Metadata attached to DataRepository. Useful to later limit search to a subset
   *     of DataRepositories.
   */
  public void setMetadata(@Nullable final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the type of this {@link DataRepository} instance and return the same instance.
   *
   * @param type The type of this {@link DataRepository}
   * @return The same instance of this {@link DataRepository} class
   */
  @Nonnull
  public DataRepository type(@Nullable final DataRepositoryType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type The type of this {@link DataRepository} instance.
   */
  @Nullable
  public DataRepositoryType getType() {
    return type;
  }

  /**
   * Set the type of this {@link DataRepository} instance.
   *
   * @param type The type of this {@link DataRepository}
   */
  public void setType(@Nullable final DataRepositoryType type) {
    this.type = type;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DataRepository}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DataRepository} instance.
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
      throw new NoSuchElementException("DataRepository has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DataRepository} instance including unrecognized
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
    if (type != null) declaredFields.put("type", type);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DataRepository} instance. If the map previously
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
    final DataRepository dataRepository = (DataRepository) o;
    return Objects.equals(this.cloudSdkCustomFields, dataRepository.cloudSdkCustomFields)
        && Objects.equals(this.id, dataRepository.id)
        && Objects.equals(this.title, dataRepository.title)
        && Objects.equals(this.metadata, dataRepository.metadata)
        && Objects.equals(this.type, dataRepository.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, metadata, type, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DataRepository {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DataRepository}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (id) -> (title) -> (type) -> new DataRepository().id(id).title(title).type(type);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the id of this {@link DataRepository} instance.
     *
     * @param id Unique identifier of this DataRepository.
     * @return The DataRepository builder.
     */
    Builder1 id(@Nonnull final UUID id);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the title of this {@link DataRepository} instance.
     *
     * @param title The title of this {@link DataRepository}
     * @return The DataRepository builder.
     */
    Builder2 title(@Nonnull final String title);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the type of this {@link DataRepository} instance.
     *
     * @param type The type of this {@link DataRepository}
     * @return The DataRepository instance.
     */
    DataRepository type(@Nullable final DataRepositoryType type);
  }
}
