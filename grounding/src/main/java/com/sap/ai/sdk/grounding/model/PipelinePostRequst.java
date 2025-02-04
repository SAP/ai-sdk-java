/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

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

/** PipelinePostRequst */
@Beta // CHECKSTYLE:OFF
public class PipelinePostRequst
// CHECKSTYLE:ON
{
  @JsonProperty("type")
  private String type;

  @JsonProperty("configuration")
  private PipelinePostRequstConfiguration _configuration;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for PipelinePostRequst. */
  protected PipelinePostRequst() {}

  /**
   * Set the type of this {@link PipelinePostRequst} instance and return the same instance.
   *
   * @param type The type of this {@link PipelinePostRequst}
   * @return The same instance of this {@link PipelinePostRequst} class
   */
  @Nonnull
  public PipelinePostRequst type(@Nonnull final String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type The type of this {@link PipelinePostRequst} instance.
   */
  @Nonnull
  public String getType() {
    return type;
  }

  /**
   * Set the type of this {@link PipelinePostRequst} instance.
   *
   * @param type The type of this {@link PipelinePostRequst}
   */
  public void setType(@Nonnull final String type) {
    this.type = type;
  }

  /**
   * Set the _configuration of this {@link PipelinePostRequst} instance and return the same
   * instance.
   *
   * @param _configuration The _configuration of this {@link PipelinePostRequst}
   * @return The same instance of this {@link PipelinePostRequst} class
   */
  @Nonnull
  public PipelinePostRequst _configuration(
      @Nonnull final PipelinePostRequstConfiguration _configuration) {
    this._configuration = _configuration;
    return this;
  }

  /**
   * Get _configuration
   *
   * @return _configuration The _configuration of this {@link PipelinePostRequst} instance.
   */
  @Nonnull
  public PipelinePostRequstConfiguration getConfiguration() {
    return _configuration;
  }

  /**
   * Set the _configuration of this {@link PipelinePostRequst} instance.
   *
   * @param _configuration The _configuration of this {@link PipelinePostRequst}
   */
  public void setConfiguration(@Nonnull final PipelinePostRequstConfiguration _configuration) {
    this._configuration = _configuration;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link PipelinePostRequst}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link PipelinePostRequst} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("PipelinePostRequst has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link PipelinePostRequst} instance. If the map
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
    final PipelinePostRequst pipelinePostRequst = (PipelinePostRequst) o;
    return Objects.equals(this.cloudSdkCustomFields, pipelinePostRequst.cloudSdkCustomFields)
        && Objects.equals(this.type, pipelinePostRequst.type)
        && Objects.equals(this._configuration, pipelinePostRequst._configuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, _configuration, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PipelinePostRequst {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link PipelinePostRequst}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (type) ->
        (_configuration) -> new PipelinePostRequst().type(type)._configuration(_configuration);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the type of this {@link PipelinePostRequst} instance.
     *
     * @param type The type of this {@link PipelinePostRequst}
     * @return The PipelinePostRequst builder.
     */
    Builder1 type(@Nonnull final String type);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the _configuration of this {@link PipelinePostRequst} instance.
     *
     * @param _configuration The _configuration of this {@link PipelinePostRequst}
     * @return The PipelinePostRequst instance.
     */
    PipelinePostRequst _configuration(
        @Nonnull final PipelinePostRequstConfiguration _configuration);
  }
}
