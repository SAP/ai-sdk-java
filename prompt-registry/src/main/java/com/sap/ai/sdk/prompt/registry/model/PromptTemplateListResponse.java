/*
 * Prompt Registry API
 * Prompt Storage service for Design time & Runtime prompt templates.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.prompt.registry.model;

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

/** PromptTemplateListResponse */
@Beta // CHECKSTYLE:OFF
public class PromptTemplateListResponse
// CHECKSTYLE:ON
{
  @JsonProperty("count")
  private Integer count;

  @JsonProperty("resources")
  private List<PromptTemplateGetResponse> resources = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PromptTemplateListResponse. */
  protected PromptTemplateListResponse() {}

  /**
   * Set the count of this {@link PromptTemplateListResponse} instance and return the same instance.
   *
   * @param count The count of this {@link PromptTemplateListResponse}
   * @return The same instance of this {@link PromptTemplateListResponse} class
   */
  @Nonnull
  public PromptTemplateListResponse count(@Nonnull final Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   *
   * @return count The count of this {@link PromptTemplateListResponse} instance.
   */
  @Nonnull
  public Integer getCount() {
    return count;
  }

  /**
   * Set the count of this {@link PromptTemplateListResponse} instance.
   *
   * @param count The count of this {@link PromptTemplateListResponse}
   */
  public void setCount(@Nonnull final Integer count) {
    this.count = count;
  }

  /**
   * Set the resources of this {@link PromptTemplateListResponse} instance and return the same
   * instance.
   *
   * @param resources The resources of this {@link PromptTemplateListResponse}
   * @return The same instance of this {@link PromptTemplateListResponse} class
   */
  @Nonnull
  public PromptTemplateListResponse resources(
      @Nonnull final List<PromptTemplateGetResponse> resources) {
    this.resources = resources;
    return this;
  }

  /**
   * Add one resources instance to this {@link PromptTemplateListResponse}.
   *
   * @param resourcesItem The resources that should be added
   * @return The same instance of type {@link PromptTemplateListResponse}
   */
  @Nonnull
  public PromptTemplateListResponse addResourcesItem(
      @Nonnull final PromptTemplateGetResponse resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

  /**
   * Get resources
   *
   * @return resources The resources of this {@link PromptTemplateListResponse} instance.
   */
  @Nonnull
  public List<PromptTemplateGetResponse> getResources() {
    return resources;
  }

  /**
   * Set the resources of this {@link PromptTemplateListResponse} instance.
   *
   * @param resources The resources of this {@link PromptTemplateListResponse}
   */
  public void setResources(@Nonnull final List<PromptTemplateGetResponse> resources) {
    this.resources = resources;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PromptTemplateListResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PromptTemplateListResponse}
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
          "PromptTemplateListResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link PromptTemplateListResponse} instance. If the map
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
    final PromptTemplateListResponse promptTemplateListResponse = (PromptTemplateListResponse) o;
    return Objects.equals(
            this.cloudSdkCustomFields, promptTemplateListResponse.cloudSdkCustomFields)
        && Objects.equals(this.count, promptTemplateListResponse.count)
        && Objects.equals(this.resources, promptTemplateListResponse.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, resources, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PromptTemplateListResponse {\n");
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
   * PromptTemplateListResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (count) ->
        (resources) -> new PromptTemplateListResponse().count(count).resources(resources);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the count of this {@link PromptTemplateListResponse} instance.
     *
     * @param count The count of this {@link PromptTemplateListResponse}
     * @return The PromptTemplateListResponse builder.
     */
    Builder1 count(@Nonnull final Integer count);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the resources of this {@link PromptTemplateListResponse} instance.
     *
     * @param resources The resources of this {@link PromptTemplateListResponse}
     * @return The PromptTemplateListResponse instance.
     */
    PromptTemplateListResponse resources(@Nonnull final List<PromptTemplateGetResponse> resources);

    /**
     * Set the resources of this {@link PromptTemplateListResponse} instance.
     *
     * @param resources The resources of this {@link PromptTemplateListResponse}
     * @return The PromptTemplateListResponse instance.
     */
    default PromptTemplateListResponse resources(
        @Nonnull final PromptTemplateGetResponse... resources) {
      return resources(Arrays.asList(resources));
    }
  }
}
