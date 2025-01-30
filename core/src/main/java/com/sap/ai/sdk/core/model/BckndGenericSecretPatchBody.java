/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndGenericSecretPatchBody */
@Beta // CHECKSTYLE:OFF
public class BckndGenericSecretPatchBody
// CHECKSTYLE:ON
{
  @JsonProperty("data")
  private Map<String, String> data = new HashMap<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndGenericSecretPatchBody. */
  protected BckndGenericSecretPatchBody() {}

  /**
   * Set the data of this {@link BckndGenericSecretPatchBody} instance and return the same instance.
   *
   * @param data Base64 encoded secret data
   * @return The same instance of this {@link BckndGenericSecretPatchBody} class
   */
  @Nonnull
  public BckndGenericSecretPatchBody data(@Nonnull final Map<String, String> data) {
    this.data = data;
    return this;
  }

  /**
   * Put one data instance to this {@link BckndGenericSecretPatchBody} instance.
   *
   * @param key The String key of this data instance
   * @param dataItem The data that should be added under the given key
   * @return The same instance of type {@link BckndGenericSecretPatchBody}
   */
  @Nonnull
  public BckndGenericSecretPatchBody putdataItem(
      @Nonnull final String key, @Nonnull final String dataItem) {
    this.data.put(key, dataItem);
    return this;
  }

  /**
   * Base64 encoded secret data
   *
   * @return data The data of this {@link BckndGenericSecretPatchBody} instance.
   */
  @Nonnull
  public Map<String, String> getData() {
    return data;
  }

  /**
   * Set the data of this {@link BckndGenericSecretPatchBody} instance.
   *
   * @param data Base64 encoded secret data
   */
  public void setData(@Nonnull final Map<String, String> data) {
    this.data = data;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndGenericSecretPatchBody}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndGenericSecretPatchBody}
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
          "BckndGenericSecretPatchBody has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndGenericSecretPatchBody} instance. If the map
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
    final BckndGenericSecretPatchBody bckndGenericSecretPatchBody = (BckndGenericSecretPatchBody) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndGenericSecretPatchBody.cloudSdkCustomFields)
        && Objects.equals(this.data, bckndGenericSecretPatchBody.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndGenericSecretPatchBody {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
   * BckndGenericSecretPatchBody} instance with all required arguments.
   */
  public static Builder create() {
    return (data) -> new BckndGenericSecretPatchBody().data(data);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the data of this {@link BckndGenericSecretPatchBody} instance.
     *
     * @param data Base64 encoded secret data
     * @return The BckndGenericSecretPatchBody instance.
     */
    BckndGenericSecretPatchBody data(@Nonnull final Map<String, String> data);
  }
}
