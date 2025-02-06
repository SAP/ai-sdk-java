/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.model2;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** EmbeddingsCreate200ResponseDataInner */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class EmbeddingsCreate200ResponseDataInner
// CHECKSTYLE:ON
{
  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  @JsonProperty("index")
  private Integer index;
  @JsonProperty("object")
  private String _object;
  @JsonProperty("embedding")
  private List<BigDecimal> embedding = new ArrayList<>(); //

  /**
   * Set the index of this {@link EmbeddingsCreate200ResponseDataInner} instance and return the same
   * instance.
   *
   * @param index The index of this {@link EmbeddingsCreate200ResponseDataInner}
   * @return The same instance of this {@link EmbeddingsCreate200ResponseDataInner} class
   */
  @Nonnull
  public EmbeddingsCreate200ResponseDataInner index(@Nonnull final Integer index) {
    this.index = index;
    return this;
  }

  /**
   * Get index
   *
   * @return index The index of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   */
  @Nonnull
  public Integer getIndex() {
    return index;
  }

  /**
   * Set the index of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   *
   * @param index The index of this {@link EmbeddingsCreate200ResponseDataInner}
   */
  public void setIndex(@Nonnull final Integer index) {
    this.index = index;
  }

  /**
   * Set the _object of this {@link EmbeddingsCreate200ResponseDataInner} instance and return the
   * same instance.
   *
   * @param _object The _object of this {@link EmbeddingsCreate200ResponseDataInner}
   * @return The same instance of this {@link EmbeddingsCreate200ResponseDataInner} class
   */
  @Nonnull
  public EmbeddingsCreate200ResponseDataInner _object(@Nonnull final String _object) {
    this._object = _object;
    return this;
  }

  /**
   * Get _object
   *
   * @return _object The _object of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   */
  @Nonnull
  public String getObject() {
    return _object;
  }

  /**
   * Set the _object of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   *
   * @param _object The _object of this {@link EmbeddingsCreate200ResponseDataInner}
   */
  public void setObject(@Nonnull final String _object) {
    this._object = _object;
  }

  /**
   * Set the embedding of this {@link EmbeddingsCreate200ResponseDataInner} instance and return the
   * same instance.
   *
   * @param embedding The embedding of this {@link EmbeddingsCreate200ResponseDataInner}
   * @return The same instance of this {@link EmbeddingsCreate200ResponseDataInner} class
   */
  @Nonnull
  public EmbeddingsCreate200ResponseDataInner embedding(@Nonnull final List<BigDecimal> embedding) {
    this.embedding = embedding;
    return this;
  }

  /**
   * Add one embedding instance to this {@link EmbeddingsCreate200ResponseDataInner}.
   *
   * @param embeddingItem The embedding that should be added
   * @return The same instance of type {@link EmbeddingsCreate200ResponseDataInner}
   */
  @Nonnull
  public EmbeddingsCreate200ResponseDataInner addEmbeddingItem(
      @Nonnull final BigDecimal embeddingItem) {
    if (this.embedding == null) {
      this.embedding = new ArrayList<>();
    }
    this.embedding.add(embeddingItem);
    return this;
  }

  /**
   * Get embedding
   *
   * @return embedding The embedding of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   */
  @Nonnull
  public List<BigDecimal> getEmbedding() {
    return embedding;
  }

  /**
   * Set the embedding of this {@link EmbeddingsCreate200ResponseDataInner} instance.
   *
   * @param embedding The embedding of this {@link EmbeddingsCreate200ResponseDataInner}
   */
  public void setEmbedding(@Nonnull final List<BigDecimal> embedding) {
    this.embedding = embedding;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * EmbeddingsCreate200ResponseDataInner}.
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
   * EmbeddingsCreate200ResponseDataInner} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "EmbeddingsCreate200ResponseDataInner has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link EmbeddingsCreate200ResponseDataInner} instance.
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final EmbeddingsCreate200ResponseDataInner embeddingsCreate200ResponseDataInner =
        (EmbeddingsCreate200ResponseDataInner) o;
    return Objects.equals(
            this.cloudSdkCustomFields, embeddingsCreate200ResponseDataInner.cloudSdkCustomFields)
        && Objects.equals(this.index, embeddingsCreate200ResponseDataInner.index)
        && Objects.equals(this._object, embeddingsCreate200ResponseDataInner._object)
        && Objects.equals(this.embedding, embeddingsCreate200ResponseDataInner.embedding);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, _object, embedding, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class EmbeddingsCreate200ResponseDataInner {\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    embedding: ").append(toIndentedString(embedding)).append("\n");
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
}
