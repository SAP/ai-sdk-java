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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** InlineObject2 */
@Beta // CHECKSTYLE:OFF
public class InlineObject2
// CHECKSTYLE:ON
{
  @JsonProperty("error")
  private KpiApiError error;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for InlineObject2. */
  protected InlineObject2() {}

  /**
   * Set the error of this {@link InlineObject2} instance and return the same instance.
   *
   * @param error The error of this {@link InlineObject2}
   * @return The same instance of this {@link InlineObject2} class
   */
  @Nonnull
  public InlineObject2 error(@Nullable final KpiApiError error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   *
   * @return error The error of this {@link InlineObject2} instance.
   */
  @Nonnull
  public KpiApiError getError() {
    return error;
  }

  /**
   * Set the error of this {@link InlineObject2} instance.
   *
   * @param error The error of this {@link InlineObject2}
   */
  public void setError(@Nullable final KpiApiError error) {
    this.error = error;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link InlineObject2}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link InlineObject2} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("InlineObject2 has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link InlineObject2} instance. If the map previously
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
    final InlineObject2 inlineObject2 = (InlineObject2) o;
    return Objects.equals(this.cloudSdkCustomFields, inlineObject2.cloudSdkCustomFields)
        && Objects.equals(this.error, inlineObject2.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class InlineObject2 {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

  /** Create a new {@link InlineObject2} instance. No arguments are required. */
  public static InlineObject2 create() {
    return new InlineObject2();
  }
}
