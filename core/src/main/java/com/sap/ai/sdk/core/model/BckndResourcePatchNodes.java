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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndResourcePatchNodes */
// CHECKSTYLE:OFF
public class BckndResourcePatchNodes
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("request")
  private Integer request;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndResourcePatchNodes. */
  protected BckndResourcePatchNodes() {}

  /**
   * Set the name of this {@link BckndResourcePatchNodes} instance and return the same instance.
   *
   * @param name The name of this {@link BckndResourcePatchNodes}
   * @return The same instance of this {@link BckndResourcePatchNodes} class
   */
  @Nonnull
  public BckndResourcePatchNodes name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name The name of this {@link BckndResourcePatchNodes} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndResourcePatchNodes} instance.
   *
   * @param name The name of this {@link BckndResourcePatchNodes}
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the request of this {@link BckndResourcePatchNodes} instance and return the same instance.
   *
   * @param request The request of this {@link BckndResourcePatchNodes}
   * @return The same instance of this {@link BckndResourcePatchNodes} class
   */
  @Nonnull
  public BckndResourcePatchNodes request(@Nonnull final Integer request) {
    this.request = request;
    return this;
  }

  /**
   * Get request
   *
   * @return request The request of this {@link BckndResourcePatchNodes} instance.
   */
  @Nonnull
  public Integer getRequest() {
    return request;
  }

  /**
   * Set the request of this {@link BckndResourcePatchNodes} instance.
   *
   * @param request The request of this {@link BckndResourcePatchNodes}
   */
  public void setRequest(@Nonnull final Integer request) {
    this.request = request;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndResourcePatchNodes}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndResourcePatchNodes} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndResourcePatchNodes has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndResourcePatchNodes} instance. If the map
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
    final BckndResourcePatchNodes bckndResourcePatchNodes = (BckndResourcePatchNodes) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndResourcePatchNodes.cloudSdkCustomFields)
        && Objects.equals(this.name, bckndResourcePatchNodes.name)
        && Objects.equals(this.request, bckndResourcePatchNodes.request);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, request, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndResourcePatchNodes {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
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
   * BckndResourcePatchNodes} instance with all required arguments.
   */
  public static Builder create() {
    return (name) -> (request) -> new BckndResourcePatchNodes().name(name).request(request);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link BckndResourcePatchNodes} instance.
     *
     * @param name The name of this {@link BckndResourcePatchNodes}
     * @return The BckndResourcePatchNodes builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the request of this {@link BckndResourcePatchNodes} instance.
     *
     * @param request The request of this {@link BckndResourcePatchNodes}
     * @return The BckndResourcePatchNodes instance.
     */
    BckndResourcePatchNodes request(@Nonnull final Integer request);
  }
}
