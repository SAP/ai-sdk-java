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
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DataRepository;
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

/** DataRepositories */
// CHECKSTYLE:OFF
public class DataRepositories
// CHECKSTYLE:ON
{
  @JsonProperty("resources")
  private List<DataRepository> resources = new ArrayList<>();

  @JsonProperty("count")
  private Integer count;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DataRepositories. */
  private DataRepositories() {}

  /**
   * Set the resources of this {@link DataRepositories} instance and return the same instance.
   *
   * @param resources The resources of this {@link DataRepositories}
   * @return The same instance of this {@link DataRepositories} class
   */
  @Nonnull
  public DataRepositories resources(@Nonnull final List<DataRepository> resources) {
    this.resources = resources;
    return this;
  }

  /**
   * Add one resources instance to this {@link DataRepositories}.
   *
   * @param resourcesItem The resources that should be added
   * @return The same instance of type {@link DataRepositories}
   */
  @Nonnull
  public DataRepositories addResourcesItem(@Nonnull final DataRepository resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

  /**
   * Get resources
   *
   * @return resources The resources of this {@link DataRepositories} instance.
   */
  @Nonnull
  public List<DataRepository> getResources() {
    return resources;
  }

  /**
   * Set the resources of this {@link DataRepositories} instance.
   *
   * @param resources The resources of this {@link DataRepositories}
   */
  public void setResources(@Nonnull final List<DataRepository> resources) {
    this.resources = resources;
  }

  /**
   * Set the count of this {@link DataRepositories} instance and return the same instance.
   *
   * @param count The count of this {@link DataRepositories}
   * @return The same instance of this {@link DataRepositories} class
   */
  @Nonnull
  public DataRepositories count(@Nullable final Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   *
   * @return count The count of this {@link DataRepositories} instance.
   */
  @Nonnull
  public Integer getCount() {
    return count;
  }

  /**
   * Set the count of this {@link DataRepositories} instance.
   *
   * @param count The count of this {@link DataRepositories}
   */
  public void setCount(@Nullable final Integer count) {
    this.count = count;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DataRepositories}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DataRepositories} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("DataRepositories has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DataRepositories} instance. If the map previously
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final DataRepositories dataRepositories = (DataRepositories) o;
    return Objects.equals(this.cloudSdkCustomFields, dataRepositories.cloudSdkCustomFields)
        && Objects.equals(this.resources, dataRepositories.resources)
        && Objects.equals(this.count, dataRepositories.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resources, count, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DataRepositories {\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DataRepositories}
   * instance with all required arguments.
   */
  public static Builder builder() {
    return (resources) -> () -> new DataRepositories().resources(resources);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the resources of this {@link DataRepositories} instance.
     *
     * @param resources The resources of this {@link DataRepositories}
     * @return The DataRepositories instance.
     */
    Builder1 resources(@Nonnull final List<DataRepository> resources);

    /**
     * Set the resources of this {@link DataRepositories} instance.
     *
     * @param resources The resources of this {@link DataRepositories}
     * @return The DataRepositories instance.
     */
    default Builder1 resources(@Nonnull final DataRepository... resources) {
      return resources(Arrays.asList(resources));
    }
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Finalize the builder for new {@link DataRepositories} instance.
     *
     * @return The DataRepositories instance.
     */
    DataRepositories build();
  }
}
