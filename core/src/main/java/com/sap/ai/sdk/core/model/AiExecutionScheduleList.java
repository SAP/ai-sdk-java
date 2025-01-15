/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.37.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

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

/** AiExecutionScheduleList */
@Beta // CHECKSTYLE:OFF
public class AiExecutionScheduleList
// CHECKSTYLE:ON
{
  @JsonProperty("count")
  private Integer count;

  @JsonProperty("resources")
  private List<AiExecutionSchedule> resources = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiExecutionScheduleList. */
  protected AiExecutionScheduleList() {}

  /**
   * Set the count of this {@link AiExecutionScheduleList} instance and return the same instance.
   *
   * @param count Number of the resource instances in the list
   * @return The same instance of this {@link AiExecutionScheduleList} class
   */
  @Nonnull
  public AiExecutionScheduleList count(@Nonnull final Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Number of the resource instances in the list
   *
   * @return count The count of this {@link AiExecutionScheduleList} instance.
   */
  @Nonnull
  public Integer getCount() {
    return count;
  }

  /**
   * Set the count of this {@link AiExecutionScheduleList} instance.
   *
   * @param count Number of the resource instances in the list
   */
  public void setCount(@Nonnull final Integer count) {
    this.count = count;
  }

  /**
   * Set the resources of this {@link AiExecutionScheduleList} instance and return the same
   * instance.
   *
   * @param resources The resources of this {@link AiExecutionScheduleList}
   * @return The same instance of this {@link AiExecutionScheduleList} class
   */
  @Nonnull
  public AiExecutionScheduleList resources(@Nonnull final List<AiExecutionSchedule> resources) {
    this.resources = resources;
    return this;
  }

  /**
   * Add one resources instance to this {@link AiExecutionScheduleList}.
   *
   * @param resourcesItem The resources that should be added
   * @return The same instance of type {@link AiExecutionScheduleList}
   */
  @Nonnull
  public AiExecutionScheduleList addResourcesItem(
      @Nonnull final AiExecutionSchedule resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

  /**
   * Get resources
   *
   * @return resources The resources of this {@link AiExecutionScheduleList} instance.
   */
  @Nonnull
  public List<AiExecutionSchedule> getResources() {
    return resources;
  }

  /**
   * Set the resources of this {@link AiExecutionScheduleList} instance.
   *
   * @param resources The resources of this {@link AiExecutionScheduleList}
   */
  public void setResources(@Nonnull final List<AiExecutionSchedule> resources) {
    this.resources = resources;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiExecutionScheduleList}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiExecutionScheduleList} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "AiExecutionScheduleList has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiExecutionScheduleList} instance. If the map
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
    final AiExecutionScheduleList aiExecutionScheduleList = (AiExecutionScheduleList) o;
    return Objects.equals(this.cloudSdkCustomFields, aiExecutionScheduleList.cloudSdkCustomFields)
        && Objects.equals(this.count, aiExecutionScheduleList.count)
        && Objects.equals(this.resources, aiExecutionScheduleList.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, resources, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiExecutionScheduleList {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
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
   * AiExecutionScheduleList} instance with all required arguments.
   */
  public static Builder create() {
    return (count) ->
        (resources) -> new AiExecutionScheduleList().count(count).resources(resources);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the count of this {@link AiExecutionScheduleList} instance.
     *
     * @param count Number of the resource instances in the list
     * @return The AiExecutionScheduleList builder.
     */
    Builder1 count(@Nonnull final Integer count);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the resources of this {@link AiExecutionScheduleList} instance.
     *
     * @param resources The resources of this {@link AiExecutionScheduleList}
     * @return The AiExecutionScheduleList instance.
     */
    AiExecutionScheduleList resources(@Nonnull final List<AiExecutionSchedule> resources);

    /**
     * Set the resources of this {@link AiExecutionScheduleList} instance.
     *
     * @param resources The resources of this {@link AiExecutionScheduleList}
     * @return The AiExecutionScheduleList instance.
     */
    default AiExecutionScheduleList resources(@Nonnull final AiExecutionSchedule... resources) {
      return resources(Arrays.asList(resources));
    }
  }
}
