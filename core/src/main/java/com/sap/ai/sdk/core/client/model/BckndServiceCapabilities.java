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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndServiceCapabilities */
// CHECKSTYLE:OFF
public class BckndServiceCapabilities
// CHECKSTYLE:ON
{
  @JsonProperty("logs")
  private BckndServiceCapabilitiesLogs logs;

  @JsonProperty("basic")
  private BckndServiceCapabilitiesBasic basic;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected BckndServiceCapabilities() {}

  /**
   * Set the logs of this {@link BckndServiceCapabilities} instance and return the same instance.
   *
   * @param logs The logs of this {@link BckndServiceCapabilities}
   * @return The same instance of this {@link BckndServiceCapabilities} class
   */
  @Nonnull
  public BckndServiceCapabilities logs(@Nullable final BckndServiceCapabilitiesLogs logs) {
    this.logs = logs;
    return this;
  }

  /**
   * Get logs
   *
   * @return logs The logs of this {@link BckndServiceCapabilities} instance.
   */
  @Nonnull
  public BckndServiceCapabilitiesLogs getLogs() {
    return logs;
  }

  /**
   * Set the logs of this {@link BckndServiceCapabilities} instance.
   *
   * @param logs The logs of this {@link BckndServiceCapabilities}
   */
  public void setLogs(@Nullable final BckndServiceCapabilitiesLogs logs) {
    this.logs = logs;
  }

  /**
   * Set the basic of this {@link BckndServiceCapabilities} instance and return the same instance.
   *
   * @param basic The basic of this {@link BckndServiceCapabilities}
   * @return The same instance of this {@link BckndServiceCapabilities} class
   */
  @Nonnull
  public BckndServiceCapabilities basic(@Nullable final BckndServiceCapabilitiesBasic basic) {
    this.basic = basic;
    return this;
  }

  /**
   * Get basic
   *
   * @return basic The basic of this {@link BckndServiceCapabilities} instance.
   */
  @Nonnull
  public BckndServiceCapabilitiesBasic getBasic() {
    return basic;
  }

  /**
   * Set the basic of this {@link BckndServiceCapabilities} instance.
   *
   * @param basic The basic of this {@link BckndServiceCapabilities}
   */
  public void setBasic(@Nullable final BckndServiceCapabilitiesBasic basic) {
    this.basic = basic;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndServiceCapabilities}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndServiceCapabilities} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndServiceCapabilities has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndServiceCapabilities} instance. If the map
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
    final BckndServiceCapabilities bckndServiceCapabilities = (BckndServiceCapabilities) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndServiceCapabilities.cloudSdkCustomFields)
        && Objects.equals(this.logs, bckndServiceCapabilities.logs)
        && Objects.equals(this.basic, bckndServiceCapabilities.basic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logs, basic, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndServiceCapabilities {\n");
    sb.append("    logs: ").append(toIndentedString(logs)).append("\n");
    sb.append("    basic: ").append(toIndentedString(basic)).append("\n");
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

  /** Create a new {@link BckndServiceCapabilities} instance. No arguments are required. */
  public static BckndServiceCapabilities create() {
    return new BckndServiceCapabilities();
  }
}
