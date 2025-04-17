/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** EmbeddingsCreate200Response */
@Beta // CHECKSTYLE:OFF
public class EmbeddingsCreate200Response
// CHECKSTYLE:ON
{
  @JsonProperty("object")
  private String _object;

  @JsonProperty("model")
  private String model;

  @JsonProperty("data")
  private List<EmbeddingsCreate200ResponseDataInner> data = new ArrayList<>();

  @JsonProperty("usage")
  private EmbeddingsCreate200ResponseUsage usage;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the _object of this {@link EmbeddingsCreate200Response} instance and return the same
   * instance.
   *
   * @param _object The _object of this {@link EmbeddingsCreate200Response}
   * @return The same instance of this {@link EmbeddingsCreate200Response} class
   */
  @Nonnull
  public EmbeddingsCreate200Response _object(@Nonnull final String _object) {
    this._object = _object;
    return this;
  }

  /**
   * Get _object
   *
   * @return _object The _object of this {@link EmbeddingsCreate200Response} instance.
   */
  @Nonnull
  public String getObject() {
    return _object;
  }

  /**
   * Set the _object of this {@link EmbeddingsCreate200Response} instance.
   *
   * @param _object The _object of this {@link EmbeddingsCreate200Response}
   */
  public void setObject(@Nonnull final String _object) {
    this._object = _object;
  }

  /**
   * Set the model of this {@link EmbeddingsCreate200Response} instance and return the same
   * instance.
   *
   * @param model The model of this {@link EmbeddingsCreate200Response}
   * @return The same instance of this {@link EmbeddingsCreate200Response} class
   */
  @Nonnull
  public EmbeddingsCreate200Response model(@Nonnull final String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   *
   * @return model The model of this {@link EmbeddingsCreate200Response} instance.
   */
  @Nonnull
  public String getModel() {
    return model;
  }

  /**
   * Set the model of this {@link EmbeddingsCreate200Response} instance.
   *
   * @param model The model of this {@link EmbeddingsCreate200Response}
   */
  public void setModel(@Nonnull final String model) {
    this.model = model;
  }

  /**
   * Set the data of this {@link EmbeddingsCreate200Response} instance and return the same instance.
   *
   * @param data The data of this {@link EmbeddingsCreate200Response}
   * @return The same instance of this {@link EmbeddingsCreate200Response} class
   */
  @Nonnull
  public EmbeddingsCreate200Response data(
      @Nonnull final List<EmbeddingsCreate200ResponseDataInner> data) {
    this.data = data;
    return this;
  }

  /**
   * Add one data instance to this {@link EmbeddingsCreate200Response}.
   *
   * @param dataItem The data that should be added
   * @return The same instance of type {@link EmbeddingsCreate200Response}
   */
  @Nonnull
  public EmbeddingsCreate200Response addDataItem(
      @Nonnull final EmbeddingsCreate200ResponseDataInner dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   *
   * @return data The data of this {@link EmbeddingsCreate200Response} instance.
   */
  @Nonnull
  public List<EmbeddingsCreate200ResponseDataInner> getData() {
    return data;
  }

  /**
   * Set the data of this {@link EmbeddingsCreate200Response} instance.
   *
   * @param data The data of this {@link EmbeddingsCreate200Response}
   */
  public void setData(@Nonnull final List<EmbeddingsCreate200ResponseDataInner> data) {
    this.data = data;
  }

  /**
   * Set the usage of this {@link EmbeddingsCreate200Response} instance and return the same
   * instance.
   *
   * @param usage The usage of this {@link EmbeddingsCreate200Response}
   * @return The same instance of this {@link EmbeddingsCreate200Response} class
   */
  @Nonnull
  public EmbeddingsCreate200Response usage(@Nonnull final EmbeddingsCreate200ResponseUsage usage) {
    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   *
   * @return usage The usage of this {@link EmbeddingsCreate200Response} instance.
   */
  @Nonnull
  public EmbeddingsCreate200ResponseUsage getUsage() {
    return usage;
  }

  /**
   * Set the usage of this {@link EmbeddingsCreate200Response} instance.
   *
   * @param usage The usage of this {@link EmbeddingsCreate200Response}
   */
  public void setUsage(@Nonnull final EmbeddingsCreate200ResponseUsage usage) {
    this.usage = usage;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link EmbeddingsCreate200Response}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link EmbeddingsCreate200Response}
   * instance.
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
          "EmbeddingsCreate200Response has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link EmbeddingsCreate200Response} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (_object != null) declaredFields.put("_object", _object);
    if (model != null) declaredFields.put("model", model);
    if (data != null) declaredFields.put("data", data);
    if (usage != null) declaredFields.put("usage", usage);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link EmbeddingsCreate200Response} instance. If the map
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
    final EmbeddingsCreate200Response embeddingsCreate200Response = (EmbeddingsCreate200Response) o;
    return Objects.equals(
            this.cloudSdkCustomFields, embeddingsCreate200Response.cloudSdkCustomFields)
        && Objects.equals(this._object, embeddingsCreate200Response._object)
        && Objects.equals(this.model, embeddingsCreate200Response.model)
        && Objects.equals(this.data, embeddingsCreate200Response.data)
        && Objects.equals(this.usage, embeddingsCreate200Response.usage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_object, model, data, usage, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class EmbeddingsCreate200Response {\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
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
}
