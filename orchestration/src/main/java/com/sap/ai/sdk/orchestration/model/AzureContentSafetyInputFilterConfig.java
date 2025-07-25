/*
 * Internal Orchestration Service API
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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** AzureContentSafetyInputFilterConfig */
// CHECKSTYLE:OFF
public class AzureContentSafetyInputFilterConfig implements InputFilterConfig
// CHECKSTYLE:ON
{
  /** Name of the filter provider type */
  public enum TypeEnum {
    /** The AZURE_CONTENT_SAFETY option of this AzureContentSafetyInputFilterConfig */
    AZURE_CONTENT_SAFETY("azure_content_safety"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AzureContentSafetyInputFilterConfig */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type AzureContentSafetyInputFilterConfig
     */
    @JsonCreator
    @Nonnull
    public static TypeEnum fromValue(@Nonnull final String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("config")
  private AzureContentSafetyInput config;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AzureContentSafetyInputFilterConfig. */
  protected AzureContentSafetyInputFilterConfig() {}

  /**
   * Set the type of this {@link AzureContentSafetyInputFilterConfig} instance and return the same
   * instance.
   *
   * @param type Name of the filter provider type
   * @return The same instance of this {@link AzureContentSafetyInputFilterConfig} class
   */
  @Nonnull
  public AzureContentSafetyInputFilterConfig type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Name of the filter provider type
   *
   * @return type The type of this {@link AzureContentSafetyInputFilterConfig} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link AzureContentSafetyInputFilterConfig} instance.
   *
   * @param type Name of the filter provider type
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the config of this {@link AzureContentSafetyInputFilterConfig} instance and return the same
   * instance.
   *
   * @param config The config of this {@link AzureContentSafetyInputFilterConfig}
   * @return The same instance of this {@link AzureContentSafetyInputFilterConfig} class
   */
  @Nonnull
  public AzureContentSafetyInputFilterConfig config(
      @Nullable final AzureContentSafetyInput config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   *
   * @return config The config of this {@link AzureContentSafetyInputFilterConfig} instance.
   */
  @Nonnull
  public AzureContentSafetyInput getConfig() {
    return config;
  }

  /**
   * Set the config of this {@link AzureContentSafetyInputFilterConfig} instance.
   *
   * @param config The config of this {@link AzureContentSafetyInputFilterConfig}
   */
  public void setConfig(@Nullable final AzureContentSafetyInput config) {
    this.config = config;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * AzureContentSafetyInputFilterConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AzureContentSafetyInputFilterConfig}
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
          "AzureContentSafetyInputFilterConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AzureContentSafetyInputFilterConfig} instance
   * including unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (type != null) declaredFields.put("type", type);
    if (config != null) declaredFields.put("config", config);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AzureContentSafetyInputFilterConfig} instance. If
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
    final AzureContentSafetyInputFilterConfig azureContentSafetyInputFilterConfig =
        (AzureContentSafetyInputFilterConfig) o;
    return Objects.equals(
            this.cloudSdkCustomFields, azureContentSafetyInputFilterConfig.cloudSdkCustomFields)
        && Objects.equals(this.type, azureContentSafetyInputFilterConfig.type)
        && Objects.equals(this.config, azureContentSafetyInputFilterConfig.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, config, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AzureContentSafetyInputFilterConfig {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
   * AzureContentSafetyInputFilterConfig} instance with all required arguments.
   */
  public static Builder create() {
    return (type) -> new AzureContentSafetyInputFilterConfig().type(type);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the type of this {@link AzureContentSafetyInputFilterConfig} instance.
     *
     * @param type Name of the filter provider type
     * @return The AzureContentSafetyInputFilterConfig instance.
     */
    AzureContentSafetyInputFilterConfig type(@Nonnull final TypeEnum type);
  }
}
