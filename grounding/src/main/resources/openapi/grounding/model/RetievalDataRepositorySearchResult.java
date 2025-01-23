/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

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

package openapi.grounding.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DataRepositoryWithDocuments;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** RetievalDataRepositorySearchResult */
// CHECKSTYLE:OFF
public class RetievalDataRepositorySearchResult
// CHECKSTYLE:ON
{
  @JsonProperty("dataRepository")
  private DataRepositoryWithDocuments dataRepository;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RetievalDataRepositorySearchResult. */
  private RetievalDataRepositorySearchResult() {}

  /**
   * Set the dataRepository of this {@link RetievalDataRepositorySearchResult} instance and return
   * the same instance.
   *
   * @param dataRepository The dataRepository of this {@link RetievalDataRepositorySearchResult}
   * @return The same instance of this {@link RetievalDataRepositorySearchResult} class
   */
  @Nonnull
  public RetievalDataRepositorySearchResult dataRepository(
      @Nonnull final DataRepositoryWithDocuments dataRepository) {
    this.dataRepository = dataRepository;
    return this;
  }

  /**
   * Get dataRepository
   *
   * @return dataRepository The dataRepository of this {@link RetievalDataRepositorySearchResult}
   *     instance.
   */
  @Nonnull
  public DataRepositoryWithDocuments getDataRepository() {
    return dataRepository;
  }

  /**
   * Set the dataRepository of this {@link RetievalDataRepositorySearchResult} instance.
   *
   * @param dataRepository The dataRepository of this {@link RetievalDataRepositorySearchResult}
   */
  public void setDataRepository(@Nonnull final DataRepositoryWithDocuments dataRepository) {
    this.dataRepository = dataRepository;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * RetievalDataRepositorySearchResult}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RetievalDataRepositorySearchResult}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "RetievalDataRepositorySearchResult has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RetievalDataRepositorySearchResult} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
    cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RetievalDataRepositorySearchResult retievalDataRepositorySearchResult =
        (RetievalDataRepositorySearchResult) o;
    return Objects.equals(
            this.cloudSdkCustomFields, retievalDataRepositorySearchResult.cloudSdkCustomFields)
        && Objects.equals(this.dataRepository, retievalDataRepositorySearchResult.dataRepository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataRepository, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RetievalDataRepositorySearchResult {\n");
    sb.append("    dataRepository: ").append(toIndentedString(dataRepository)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * RetievalDataRepositorySearchResult} instance with all required arguments.
   */
  public static Builder builder() {
    return (dataRepository) ->
        () -> new RetievalDataRepositorySearchResult().dataRepository(dataRepository);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the dataRepository of this {@link RetievalDataRepositorySearchResult} instance.
     *
     * @param dataRepository The dataRepository of this {@link RetievalDataRepositorySearchResult}
     * @return The RetievalDataRepositorySearchResult instance.
     */
    Builder1 dataRepository(@Nonnull final DataRepositoryWithDocuments dataRepository);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Finalize the builder for new {@link RetievalDataRepositorySearchResult} instance.
     *
     * @return The RetievalDataRepositorySearchResult instance.
     */
    RetievalDataRepositorySearchResult build();
  }
}
