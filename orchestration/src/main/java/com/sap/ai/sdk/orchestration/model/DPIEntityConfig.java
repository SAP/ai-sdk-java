/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** DPIEntityConfig */
@Beta // CHECKSTYLE:OFF
public class DPIEntityConfig
// CHECKSTYLE:ON
{
  @JsonProperty("type")
  private DPIEntities type;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DPIEntityConfig. */
  protected DPIEntityConfig() {}

  /**
   * Set the type of this {@link DPIEntityConfig} instance and return the same instance.
   *
   * @param type Type of entity to be masked
   * @return The same instance of this {@link DPIEntityConfig} class
   */
  @Nonnull
  public DPIEntityConfig type(@Nonnull final DPIEntities type) {
    this.type = type;
    return this;
  }

  /**
   * Type of entity to be masked
   *
   * @return type The type of this {@link DPIEntityConfig} instance.
   */
  @Nonnull
  public DPIEntities getType() {
    return type;
  }

  /**
   * Set the type of this {@link DPIEntityConfig} instance.
   *
   * @param type Type of entity to be masked
   */
  public void setType(@Nonnull final DPIEntities type) {
    this.type = type;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DPIEntityConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DPIEntityConfig} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("DPIEntityConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DPIEntityConfig} instance. If the map previously
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
    final DPIEntityConfig dpIEntityConfig = (DPIEntityConfig) o;
    return Objects.equals(this.cloudSdkCustomFields, dpIEntityConfig.cloudSdkCustomFields)
        && Objects.equals(this.type, dpIEntityConfig.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DPIEntityConfig {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DPIEntityConfig}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (type) -> new DPIEntityConfig().type(type);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the type of this {@link DPIEntityConfig} instance.
     *
     * @param type Type of entity to be masked
     * @return The DPIEntityConfig instance.
     */
    DPIEntityConfig type(@Nonnull final DPIEntities type);
  }
}
