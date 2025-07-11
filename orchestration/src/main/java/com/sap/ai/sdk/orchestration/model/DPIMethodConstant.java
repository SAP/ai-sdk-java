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

/** Replaces the entity with the specified value followed by an incrementing number */
// CHECKSTYLE:OFF
public class DPIMethodConstant implements DPIStandardEntityReplacementStrategy
// CHECKSTYLE:ON
{
  /** Gets or Sets method */
  public enum MethodEnum {
    /** The CONSTANT option of this DPIMethodConstant */
    CONSTANT("constant"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this DPIMethodConstant */
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
     * @return The enum value of type DPIMethodConstant
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

  @JsonProperty("value")
  private String value;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DPIMethodConstant. */
  protected DPIMethodConstant() {}

  /**
   * Set the method of this {@link DPIMethodConstant} instance and return the same instance.
   *
   * @param method The method of this {@link DPIMethodConstant}
   * @return The same instance of this {@link DPIMethodConstant} class
   */
  @Nonnull
  public DPIMethodConstant method(@Nonnull final MethodEnum method) {
    this.method = method;
    return this;
  }

  /**
   * Get method
   *
   * @return method The method of this {@link DPIMethodConstant} instance.
   */
  @Nonnull
  public MethodEnum getMethod() {
    return method;
  }

  /**
   * Set the method of this {@link DPIMethodConstant} instance.
   *
   * @param method The method of this {@link DPIMethodConstant}
   */
  public void setMethod(@Nonnull final MethodEnum method) {
    this.method = method;
  }

  /**
   * Set the value of this {@link DPIMethodConstant} instance and return the same instance.
   *
   * @param value Value to be used for replacement
   * @return The same instance of this {@link DPIMethodConstant} class
   */
  @Nonnull
  public DPIMethodConstant value(@Nonnull final String value) {
    this.value = value;
    return this;
  }

  /**
   * Value to be used for replacement
   *
   * @return value The value of this {@link DPIMethodConstant} instance.
   */
  @Nonnull
  public String getValue() {
    return value;
  }

  /**
   * Set the value of this {@link DPIMethodConstant} instance.
   *
   * @param value Value to be used for replacement
   */
  public void setValue(@Nonnull final String value) {
    this.value = value;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DPIMethodConstant}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DPIMethodConstant} instance.
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
      throw new NoSuchElementException("DPIMethodConstant has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link DPIMethodConstant} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (method != null) declaredFields.put("method", method);
    if (value != null) declaredFields.put("value", value);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link DPIMethodConstant} instance. If the map
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
    final DPIMethodConstant dpIMethodConstant = (DPIMethodConstant) o;
    return Objects.equals(this.cloudSdkCustomFields, dpIMethodConstant.cloudSdkCustomFields)
        && Objects.equals(this.method, dpIMethodConstant.method)
        && Objects.equals(this.value, dpIMethodConstant.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method, value, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DPIMethodConstant {\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DPIMethodConstant}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (method) -> (value) -> new DPIMethodConstant().method(method).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the method of this {@link DPIMethodConstant} instance.
     *
     * @param method The method of this {@link DPIMethodConstant}
     * @return The DPIMethodConstant builder.
     */
    Builder1 method(@Nonnull final MethodEnum method);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link DPIMethodConstant} instance.
     *
     * @param value Value to be used for replacement
     * @return The DPIMethodConstant instance.
     */
    DPIMethodConstant value(@Nonnull final String value);
  }
}
