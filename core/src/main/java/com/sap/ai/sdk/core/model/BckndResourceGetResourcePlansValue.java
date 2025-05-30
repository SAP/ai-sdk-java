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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndResourceGetResourcePlansValue */
// CHECKSTYLE:OFF
public class BckndResourceGetResourcePlansValue
// CHECKSTYLE:ON
{
  @JsonProperty("provisioned")
  private Integer provisioned;

  @JsonProperty("requested")
  private Integer requested;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndResourceGetResourcePlansValue. */
  protected BckndResourceGetResourcePlansValue() {}

  /**
   * Set the provisioned of this {@link BckndResourceGetResourcePlansValue} instance and return the
   * same instance.
   *
   * @param provisioned The provisioned of this {@link BckndResourceGetResourcePlansValue}
   * @return The same instance of this {@link BckndResourceGetResourcePlansValue} class
   */
  @Nonnull
  public BckndResourceGetResourcePlansValue provisioned(@Nonnull final Integer provisioned) {
    this.provisioned = provisioned;
    return this;
  }

  /**
   * Get provisioned
   *
   * @return provisioned The provisioned of this {@link BckndResourceGetResourcePlansValue}
   *     instance.
   */
  @Nonnull
  public Integer getProvisioned() {
    return provisioned;
  }

  /**
   * Set the provisioned of this {@link BckndResourceGetResourcePlansValue} instance.
   *
   * @param provisioned The provisioned of this {@link BckndResourceGetResourcePlansValue}
   */
  public void setProvisioned(@Nonnull final Integer provisioned) {
    this.provisioned = provisioned;
  }

  /**
   * Set the requested of this {@link BckndResourceGetResourcePlansValue} instance and return the
   * same instance.
   *
   * @param requested The requested of this {@link BckndResourceGetResourcePlansValue}
   * @return The same instance of this {@link BckndResourceGetResourcePlansValue} class
   */
  @Nonnull
  public BckndResourceGetResourcePlansValue requested(@Nonnull final Integer requested) {
    this.requested = requested;
    return this;
  }

  /**
   * Get requested
   *
   * @return requested The requested of this {@link BckndResourceGetResourcePlansValue} instance.
   */
  @Nonnull
  public Integer getRequested() {
    return requested;
  }

  /**
   * Set the requested of this {@link BckndResourceGetResourcePlansValue} instance.
   *
   * @param requested The requested of this {@link BckndResourceGetResourcePlansValue}
   */
  public void setRequested(@Nonnull final Integer requested) {
    this.requested = requested;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndResourceGetResourcePlansValue}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndResourceGetResourcePlansValue}
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
          "BckndResourceGetResourcePlansValue has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link BckndResourceGetResourcePlansValue} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (provisioned != null) declaredFields.put("provisioned", provisioned);
    if (requested != null) declaredFields.put("requested", requested);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link BckndResourceGetResourcePlansValue} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final BckndResourceGetResourcePlansValue bckndResourceGetResourcePlansValue =
        (BckndResourceGetResourcePlansValue) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndResourceGetResourcePlansValue.cloudSdkCustomFields)
        && Objects.equals(this.provisioned, bckndResourceGetResourcePlansValue.provisioned)
        && Objects.equals(this.requested, bckndResourceGetResourcePlansValue.requested);
  }

  @Override
  public int hashCode() {
    return Objects.hash(provisioned, requested, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndResourceGetResourcePlansValue {\n");
    sb.append("    provisioned: ").append(toIndentedString(provisioned)).append("\n");
    sb.append("    requested: ").append(toIndentedString(requested)).append("\n");
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
   * BckndResourceGetResourcePlansValue} instance with all required arguments.
   */
  public static Builder create() {
    return (provisioned) ->
        (requested) ->
            new BckndResourceGetResourcePlansValue().provisioned(provisioned).requested(requested);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the provisioned of this {@link BckndResourceGetResourcePlansValue} instance.
     *
     * @param provisioned The provisioned of this {@link BckndResourceGetResourcePlansValue}
     * @return The BckndResourceGetResourcePlansValue builder.
     */
    Builder1 provisioned(@Nonnull final Integer provisioned);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the requested of this {@link BckndResourceGetResourcePlansValue} instance.
     *
     * @param requested The requested of this {@link BckndResourceGetResourcePlansValue}
     * @return The BckndResourceGetResourcePlansValue instance.
     */
    BckndResourceGetResourcePlansValue requested(@Nonnull final Integer requested);
  }
}
