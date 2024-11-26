/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.36.0
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A dictionary of name-value pairs to support segregation at execution level. */
// CHECKSTYLE:OFF
public class TrckTag
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private TrckTagName name;

  @JsonProperty("value")
  private String value;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TrckTag. */
  protected TrckTag() {}

  /**
   * Set the name of this {@link TrckTag} instance and return the same instance.
   *
   * @param name The name of this {@link TrckTag}
   * @return The same instance of this {@link TrckTag} class
   */
  @Nonnull
  public TrckTag name(@Nonnull final TrckTagName name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name The name of this {@link TrckTag} instance.
   */
  @Nonnull
  public TrckTagName getName() {
    return name;
  }

  /**
   * Set the name of this {@link TrckTag} instance.
   *
   * @param name The name of this {@link TrckTag}
   */
  public void setName(@Nonnull final TrckTagName name) {
    this.name = name;
  }

  /**
   * Set the value of this {@link TrckTag} instance and return the same instance.
   *
   * @param value tag value
   * @return The same instance of this {@link TrckTag} class
   */
  @Nonnull
  public TrckTag value(@Nonnull final String value) {
    this.value = value;
    return this;
  }

  /**
   * tag value
   *
   * @return value The value of this {@link TrckTag} instance.
   */
  @Nonnull
  public String getValue() {
    return value;
  }

  /**
   * Set the value of this {@link TrckTag} instance.
   *
   * @param value tag value
   */
  public void setValue(@Nonnull final String value) {
    this.value = value;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TrckTag}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TrckTag} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TrckTag has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TrckTag} instance. If the map previously
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
    final TrckTag trckTag = (TrckTag) o;
    return Objects.equals(this.cloudSdkCustomFields, trckTag.cloudSdkCustomFields)
        && Objects.equals(this.name, trckTag.name)
        && Objects.equals(this.value, trckTag.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TrckTag {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TrckTag} instance with
   * all required arguments.
   */
  public static Builder create() {
    return (name) -> (value) -> new TrckTag().name(name).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link TrckTag} instance.
     *
     * @param name The name of this {@link TrckTag}
     * @return The TrckTag builder.
     */
    Builder1 name(@Nonnull final TrckTagName name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link TrckTag} instance.
     *
     * @param value tag value
     * @return The TrckTag instance.
     */
    TrckTag value(@Nonnull final String value);
  }
}
