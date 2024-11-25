/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
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

/** DPIConfig */
@Beta // CHECKSTYLE:OFF
public class DPIConfig implements MaskingProviderConfig
// CHECKSTYLE:ON
{
  /** Type of masking service provider */
  public enum TypeEnum {
    /** The SAP_DATA_PRIVACY_INTEGRATION option of this DPIConfig */
    SAP_DATA_PRIVACY_INTEGRATION("sap_data_privacy_integration"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this DPIConfig */
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
     * @return The enum value of type DPIConfig
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

  /** Type of masking method to be used */
  public enum MethodEnum {
    /** The ANONYMIZATION option of this DPIConfig */
    ANONYMIZATION("anonymization"),

    /** The PSEUDONYMIZATION option of this DPIConfig */
    PSEUDONYMIZATION("pseudonymization"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this DPIConfig */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    MethodEnum(String value) {
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
     * @return The enum value of type DPIConfig
     */
    @JsonCreator
    @Nonnull
    public static MethodEnum fromValue(@Nonnull final String value) {
      for (MethodEnum b : MethodEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("method")
  private MethodEnum method;

  @JsonProperty("entities")
  private List<DPIEntityConfig> entities = new ArrayList<>();

  @JsonProperty("allowlist")
  private List<String> allowlist = new ArrayList<>();

  @JsonProperty("mask_grounding_input")
  private DPIConfigMaskGroundingInput maskGroundingInput;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DPIConfig. */
  protected DPIConfig() {}

  /**
   * Set the type of this {@link DPIConfig} instance and return the same instance.
   *
   * @param type Type of masking service provider
   * @return The same instance of this {@link DPIConfig} class
   */
  @Nonnull
  public DPIConfig type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of masking service provider
   *
   * @return type The type of this {@link DPIConfig} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link DPIConfig} instance.
   *
   * @param type Type of masking service provider
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the method of this {@link DPIConfig} instance and return the same instance.
   *
   * @param method Type of masking method to be used
   * @return The same instance of this {@link DPIConfig} class
   */
  @Nonnull
  public DPIConfig method(@Nonnull final MethodEnum method) {
    this.method = method;
    return this;
  }

  /**
   * Type of masking method to be used
   *
   * @return method The method of this {@link DPIConfig} instance.
   */
  @Nonnull
  public MethodEnum getMethod() {
    return method;
  }

  /**
   * Set the method of this {@link DPIConfig} instance.
   *
   * @param method Type of masking method to be used
   */
  public void setMethod(@Nonnull final MethodEnum method) {
    this.method = method;
  }

  /**
   * Set the entities of this {@link DPIConfig} instance and return the same instance.
   *
   * @param entities List of entities to be masked
   * @return The same instance of this {@link DPIConfig} class
   */
  @Nonnull
  public DPIConfig entities(@Nonnull final List<DPIEntityConfig> entities) {
    this.entities = entities;
    return this;
  }

  /**
   * Add one entities instance to this {@link DPIConfig}.
   *
   * @param entitiesItem The entities that should be added
   * @return The same instance of type {@link DPIConfig}
   */
  @Nonnull
  public DPIConfig addEntitiesItem(@Nonnull final DPIEntityConfig entitiesItem) {
    if (this.entities == null) {
      this.entities = new ArrayList<>();
    }
    this.entities.add(entitiesItem);
    return this;
  }

  /**
   * List of entities to be masked
   *
   * @return entities The entities of this {@link DPIConfig} instance.
   */
  @Nonnull
  public List<DPIEntityConfig> getEntities() {
    return entities;
  }

  /**
   * Set the entities of this {@link DPIConfig} instance.
   *
   * @param entities List of entities to be masked
   */
  public void setEntities(@Nonnull final List<DPIEntityConfig> entities) {
    this.entities = entities;
  }

  /**
   * Set the allowlist of this {@link DPIConfig} instance and return the same instance.
   *
   * @param allowlist List of strings that should not be masked
   * @return The same instance of this {@link DPIConfig} class
   */
  @Nonnull
  public DPIConfig allowlist(@Nullable final List<String> allowlist) {
    this.allowlist = allowlist;
    return this;
  }

  /**
   * Add one allowlist instance to this {@link DPIConfig}.
   *
   * @param allowlistItem The allowlist that should be added
   * @return The same instance of type {@link DPIConfig}
   */
  @Nonnull
  public DPIConfig addAllowlistItem(@Nonnull final String allowlistItem) {
    if (this.allowlist == null) {
      this.allowlist = new ArrayList<>();
    }
    this.allowlist.add(allowlistItem);
    return this;
  }

  /**
   * List of strings that should not be masked
   *
   * @return allowlist The allowlist of this {@link DPIConfig} instance.
   */
  @Nonnull
  public List<String> getAllowlist() {
    return allowlist;
  }

  /**
   * Set the allowlist of this {@link DPIConfig} instance.
   *
   * @param allowlist List of strings that should not be masked
   */
  public void setAllowlist(@Nullable final List<String> allowlist) {
    this.allowlist = allowlist;
  }

  /**
   * Set the maskGroundingInput of this {@link DPIConfig} instance and return the same instance.
   *
   * @param maskGroundingInput The maskGroundingInput of this {@link DPIConfig}
   * @return The same instance of this {@link DPIConfig} class
   */
  @Nonnull
  public DPIConfig maskGroundingInput(
      @Nullable final DPIConfigMaskGroundingInput maskGroundingInput) {
    this.maskGroundingInput = maskGroundingInput;
    return this;
  }

  /**
   * Get maskGroundingInput
   *
   * @return maskGroundingInput The maskGroundingInput of this {@link DPIConfig} instance.
   */
  @Nonnull
  public DPIConfigMaskGroundingInput getMaskGroundingInput() {
    return maskGroundingInput;
  }

  /**
   * Set the maskGroundingInput of this {@link DPIConfig} instance.
   *
   * @param maskGroundingInput The maskGroundingInput of this {@link DPIConfig}
   */
  public void setMaskGroundingInput(
      @Nullable final DPIConfigMaskGroundingInput maskGroundingInput) {
    this.maskGroundingInput = maskGroundingInput;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DPIConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DPIConfig} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("DPIConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DPIConfig} instance. If the map previously
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
    final DPIConfig dpIConfig = (DPIConfig) o;
    return Objects.equals(this.cloudSdkCustomFields, dpIConfig.cloudSdkCustomFields)
        && Objects.equals(this.type, dpIConfig.type)
        && Objects.equals(this.method, dpIConfig.method)
        && Objects.equals(this.entities, dpIConfig.entities)
        && Objects.equals(this.allowlist, dpIConfig.allowlist)
        && Objects.equals(this.maskGroundingInput, dpIConfig.maskGroundingInput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        type, method, entities, allowlist, maskGroundingInput, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DPIConfig {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
    sb.append("    allowlist: ").append(toIndentedString(allowlist)).append("\n");
    sb.append("    maskGroundingInput: ").append(toIndentedString(maskGroundingInput)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DPIConfig} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (type) ->
        (method) -> (entities) -> new DPIConfig().type(type).method(method).entities(entities);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the type of this {@link DPIConfig} instance.
     *
     * @param type Type of masking service provider
     * @return The DPIConfig builder.
     */
    Builder1 type(@Nonnull final TypeEnum type);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the method of this {@link DPIConfig} instance.
     *
     * @param method Type of masking method to be used
     * @return The DPIConfig builder.
     */
    Builder2 method(@Nonnull final MethodEnum method);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the entities of this {@link DPIConfig} instance.
     *
     * @param entities List of entities to be masked
     * @return The DPIConfig instance.
     */
    DPIConfig entities(@Nonnull final List<DPIEntityConfig> entities);

    /**
     * Set the entities of this {@link DPIConfig} instance.
     *
     * @param entities List of entities to be masked
     * @return The DPIConfig instance.
     */
    default DPIConfig entities(@Nonnull final DPIEntityConfig... entities) {
      return entities(Arrays.asList(entities));
    }
  }
}
