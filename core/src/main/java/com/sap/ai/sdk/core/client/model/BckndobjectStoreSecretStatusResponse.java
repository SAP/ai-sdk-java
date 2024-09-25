/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.34.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

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

/**
 * This represents a list of meta-data of a stored secret. The &#39;data&#39; field of the secret is
 * never retrieved.
 */
// CHECKSTYLE:OFF
public class BckndobjectStoreSecretStatusResponse
// CHECKSTYLE:ON
{
  @JsonProperty("count")
  private Integer count;

  @JsonProperty("resources")
  private List<BckndobjectStoreSecretStatus> resources = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected BckndobjectStoreSecretStatusResponse() {}

  /**
   * Set the count of this {@link BckndobjectStoreSecretStatusResponse} instance and return the same
   * instance.
   *
   * @param count Number of the resource instances in the list
   * @return The same instance of this {@link BckndobjectStoreSecretStatusResponse} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusResponse count(@Nonnull final Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Number of the resource instances in the list
   *
   * @return count The count of this {@link BckndobjectStoreSecretStatusResponse} instance.
   */
  @Nonnull
  public Integer getCount() {
    return count;
  }

  /**
   * Set the count of this {@link BckndobjectStoreSecretStatusResponse} instance.
   *
   * @param count Number of the resource instances in the list
   */
  public void setCount(@Nonnull final Integer count) {
    this.count = count;
  }

  /**
   * Set the resources of this {@link BckndobjectStoreSecretStatusResponse} instance and return the
   * same instance.
   *
   * @param resources The resources of this {@link BckndobjectStoreSecretStatusResponse}
   * @return The same instance of this {@link BckndobjectStoreSecretStatusResponse} class
   */
  @Nonnull
  public BckndobjectStoreSecretStatusResponse resources(
      @Nonnull final List<BckndobjectStoreSecretStatus> resources) {
    this.resources = resources;
    return this;
  }

  /**
   * Add one resources instance to this {@link BckndobjectStoreSecretStatusResponse}.
   *
   * @param resourcesItem The resources that should be added
   * @return The same instance of type {@link BckndobjectStoreSecretStatusResponse}
   */
  @Nonnull
  public BckndobjectStoreSecretStatusResponse addResourcesItem(
      @Nonnull final BckndobjectStoreSecretStatus resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

  /**
   * Get resources
   *
   * @return resources The resources of this {@link BckndobjectStoreSecretStatusResponse} instance.
   */
  @Nonnull
  public List<BckndobjectStoreSecretStatus> getResources() {
    return resources;
  }

  /**
   * Set the resources of this {@link BckndobjectStoreSecretStatusResponse} instance.
   *
   * @param resources The resources of this {@link BckndobjectStoreSecretStatusResponse}
   */
  public void setResources(@Nonnull final List<BckndobjectStoreSecretStatus> resources) {
    this.resources = resources;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndobjectStoreSecretStatusResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * BckndobjectStoreSecretStatusResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndobjectStoreSecretStatusResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndobjectStoreSecretStatusResponse} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
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
    final BckndobjectStoreSecretStatusResponse bckndobjectStoreSecretStatusResponse =
        (BckndobjectStoreSecretStatusResponse) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndobjectStoreSecretStatusResponse.cloudSdkCustomFields)
        && Objects.equals(this.count, bckndobjectStoreSecretStatusResponse.count)
        && Objects.equals(this.resources, bckndobjectStoreSecretStatusResponse.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, resources, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndobjectStoreSecretStatusResponse {\n");
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
   * BckndobjectStoreSecretStatusResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (count) ->
        (resources) -> new BckndobjectStoreSecretStatusResponse().count(count).resources(resources);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the count of this {@link BckndobjectStoreSecretStatusResponse} instance.
     *
     * @param count Number of the resource instances in the list
     * @return The BckndobjectStoreSecretStatusResponse builder.
     */
    Builder1 count(@Nonnull final Integer count);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the resources of this {@link BckndobjectStoreSecretStatusResponse} instance.
     *
     * @param resources The resources of this {@link BckndobjectStoreSecretStatusResponse}
     * @return The BckndobjectStoreSecretStatusResponse instance.
     */
    BckndobjectStoreSecretStatusResponse resources(
        @Nonnull final List<BckndobjectStoreSecretStatus> resources);

    /**
     * Set the resources of this {@link BckndobjectStoreSecretStatusResponse} instance.
     *
     * @param resources The resources of this {@link BckndobjectStoreSecretStatusResponse}
     * @return The BckndobjectStoreSecretStatusResponse instance.
     */
    default BckndobjectStoreSecretStatusResponse resources(
        @Nonnull final BckndobjectStoreSecretStatus... resources) {
      return resources(Arrays.asList(resources));
    }
  }
}
