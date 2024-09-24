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

/** BckndResourcePatchBody */
// CHECKSTYLE:OFF
public class BckndResourcePatchBody
// CHECKSTYLE:ON
{
  @JsonProperty("resourcePlans")
  private List<BckndResourcePatchNodes> resourcePlans = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected BckndResourcePatchBody() {}

  /**
   * Set the resourcePlans of this {@link BckndResourcePatchBody} instance and return the same
   * instance.
   *
   * @param resourcePlans The resourcePlans of this {@link BckndResourcePatchBody}
   * @return The same instance of this {@link BckndResourcePatchBody} class
   */
  @Nonnull
  public BckndResourcePatchBody resourcePlans(
      @Nonnull final List<BckndResourcePatchNodes> resourcePlans) {
    this.resourcePlans = resourcePlans;
    return this;
  }

  /**
   * Add one resourcePlans instance to this {@link BckndResourcePatchBody}.
   *
   * @param resourcePlansItem The resourcePlans that should be added
   * @return The same instance of type {@link BckndResourcePatchBody}
   */
  @Nonnull
  public BckndResourcePatchBody addResourcePlansItem(
      @Nonnull final BckndResourcePatchNodes resourcePlansItem) {
    if (this.resourcePlans == null) {
      this.resourcePlans = new ArrayList<>();
    }
    this.resourcePlans.add(resourcePlansItem);
    return this;
  }

  /**
   * Get resourcePlans
   *
   * @return resourcePlans The resourcePlans of this {@link BckndResourcePatchBody} instance.
   */
  @Nonnull
  public List<BckndResourcePatchNodes> getResourcePlans() {
    return resourcePlans;
  }

  /**
   * Set the resourcePlans of this {@link BckndResourcePatchBody} instance.
   *
   * @param resourcePlans The resourcePlans of this {@link BckndResourcePatchBody}
   */
  public void setResourcePlans(@Nonnull final List<BckndResourcePatchNodes> resourcePlans) {
    this.resourcePlans = resourcePlans;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndResourcePatchBody}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndResourcePatchBody} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndResourcePatchBody has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndResourcePatchBody} instance. If the map
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
    final BckndResourcePatchBody bckndResourcePatchBody = (BckndResourcePatchBody) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndResourcePatchBody.cloudSdkCustomFields)
        && Objects.equals(this.resourcePlans, bckndResourcePatchBody.resourcePlans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourcePlans, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndResourcePatchBody {\n");
    sb.append("    resourcePlans: ").append(toIndentedString(resourcePlans)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link BckndResourcePatchBody}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (resourcePlans) -> new BckndResourcePatchBody().resourcePlans(resourcePlans);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the resourcePlans of this {@link BckndResourcePatchBody} instance.
     *
     * @param resourcePlans The resourcePlans of this {@link BckndResourcePatchBody}
     * @return The BckndResourcePatchBody instance.
     */
    BckndResourcePatchBody resourcePlans(
        @Nonnull final List<BckndResourcePatchNodes> resourcePlans);

    /**
     * Set the resourcePlans of this {@link BckndResourcePatchBody} instance.
     *
     * @param resourcePlans The resourcePlans of this {@link BckndResourcePatchBody}
     * @return The BckndResourcePatchBody instance.
     */
    default BckndResourcePatchBody resourcePlans(
        @Nonnull final BckndResourcePatchNodes... resourcePlans) {
      return resourcePlans(Arrays.asList(resourcePlans));
    }
  }
}
