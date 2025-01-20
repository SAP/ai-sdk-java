/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.38.0
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

/** MetaAiApiLimitsTimeToLiveDeployments */
@Beta // CHECKSTYLE:OFF
public class MetaAiApiLimitsTimeToLiveDeployments
// CHECKSTYLE:ON
{
  @JsonProperty("minimum")
  private String minimum = "10m";

  @JsonProperty("maximum")
  private String maximum = "-1";

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MetaAiApiLimitsTimeToLiveDeployments. */
  protected MetaAiApiLimitsTimeToLiveDeployments() {}

  /**
   * Set the minimum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance and return the
   * same instance.
   *
   * @param minimum The minimum of this {@link MetaAiApiLimitsTimeToLiveDeployments}
   * @return The same instance of this {@link MetaAiApiLimitsTimeToLiveDeployments} class
   */
  @Nonnull
  public MetaAiApiLimitsTimeToLiveDeployments minimum(@Nullable final String minimum) {
    this.minimum = minimum;
    return this;
  }

  /**
   * Get minimum
   *
   * @return minimum The minimum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance.
   */
  @Nonnull
  public String getMinimum() {
    return minimum;
  }

  /**
   * Set the minimum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance.
   *
   * @param minimum The minimum of this {@link MetaAiApiLimitsTimeToLiveDeployments}
   */
  public void setMinimum(@Nullable final String minimum) {
    this.minimum = minimum;
  }

  /**
   * Set the maximum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance and return the
   * same instance.
   *
   * @param maximum The maximum of this {@link MetaAiApiLimitsTimeToLiveDeployments}
   * @return The same instance of this {@link MetaAiApiLimitsTimeToLiveDeployments} class
   */
  @Nonnull
  public MetaAiApiLimitsTimeToLiveDeployments maximum(@Nullable final String maximum) {
    this.maximum = maximum;
    return this;
  }

  /**
   * Get maximum
   *
   * @return maximum The maximum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance.
   */
  @Nonnull
  public String getMaximum() {
    return maximum;
  }

  /**
   * Set the maximum of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance.
   *
   * @param maximum The maximum of this {@link MetaAiApiLimitsTimeToLiveDeployments}
   */
  public void setMaximum(@Nullable final String maximum) {
    this.maximum = maximum;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * MetaAiApiLimitsTimeToLiveDeployments}.
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
   * MetaAiApiLimitsTimeToLiveDeployments} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "MetaAiApiLimitsTimeToLiveDeployments has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link MetaAiApiLimitsTimeToLiveDeployments} instance.
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
    final MetaAiApiLimitsTimeToLiveDeployments metaAiApiLimitsTimeToLiveDeployments =
        (MetaAiApiLimitsTimeToLiveDeployments) o;
    return Objects.equals(
            this.cloudSdkCustomFields, metaAiApiLimitsTimeToLiveDeployments.cloudSdkCustomFields)
        && Objects.equals(this.minimum, metaAiApiLimitsTimeToLiveDeployments.minimum)
        && Objects.equals(this.maximum, metaAiApiLimitsTimeToLiveDeployments.maximum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimum, maximum, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MetaAiApiLimitsTimeToLiveDeployments {\n");
    sb.append("    minimum: ").append(toIndentedString(minimum)).append("\n");
    sb.append("    maximum: ").append(toIndentedString(maximum)).append("\n");
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
   * Create a new {@link MetaAiApiLimitsTimeToLiveDeployments} instance. No arguments are required.
   */
  public static MetaAiApiLimitsTimeToLiveDeployments create() {
    return new MetaAiApiLimitsTimeToLiveDeployments();
  }
}
