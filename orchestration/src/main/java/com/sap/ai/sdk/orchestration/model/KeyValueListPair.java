/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.36.1
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

/** KeyValueListPair */
@Beta // CHECKSTYLE:OFF
public class KeyValueListPair
// CHECKSTYLE:ON
{
  @JsonProperty("key")
  private String key;

  @JsonProperty("value")
  private List<String> value = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for KeyValueListPair. */
  protected KeyValueListPair() {}

  /**
   * Set the key of this {@link KeyValueListPair} instance and return the same instance.
   *
   * @param key The key of this {@link KeyValueListPair}
   * @return The same instance of this {@link KeyValueListPair} class
   */
  @Nonnull
  public KeyValueListPair key(@Nonnull final String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key The key of this {@link KeyValueListPair} instance.
   */
  @Nonnull
  public String getKey() {
    return key;
  }

  /**
   * Set the key of this {@link KeyValueListPair} instance.
   *
   * @param key The key of this {@link KeyValueListPair}
   */
  public void setKey(@Nonnull final String key) {
    this.key = key;
  }

  /**
   * Set the value of this {@link KeyValueListPair} instance and return the same instance.
   *
   * @param value The value of this {@link KeyValueListPair}
   * @return The same instance of this {@link KeyValueListPair} class
   */
  @Nonnull
  public KeyValueListPair value(@Nonnull final List<String> value) {
    this.value = value;
    return this;
  }

  /**
   * Add one value instance to this {@link KeyValueListPair}.
   *
   * @param valueItem The value that should be added
   * @return The same instance of type {@link KeyValueListPair}
   */
  @Nonnull
  public KeyValueListPair addValueItem(@Nonnull final String valueItem) {
    if (this.value == null) {
      this.value = new ArrayList<>();
    }
    this.value.add(valueItem);
    return this;
  }

  /**
   * Get value
   *
   * @return value The value of this {@link KeyValueListPair} instance.
   */
  @Nonnull
  public List<String> getValue() {
    return value;
  }

  /**
   * Set the value of this {@link KeyValueListPair} instance.
   *
   * @param value The value of this {@link KeyValueListPair}
   */
  public void setValue(@Nonnull final List<String> value) {
    this.value = value;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link KeyValueListPair}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link KeyValueListPair} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("KeyValueListPair has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link KeyValueListPair} instance. If the map previously
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
    final KeyValueListPair keyValueListPair = (KeyValueListPair) o;
    return Objects.equals(this.cloudSdkCustomFields, keyValueListPair.cloudSdkCustomFields)
        && Objects.equals(this.key, keyValueListPair.key)
        && Objects.equals(this.value, keyValueListPair.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class KeyValueListPair {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link KeyValueListPair}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (key) -> (value) -> new KeyValueListPair().key(key).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the key of this {@link KeyValueListPair} instance.
     *
     * @param key The key of this {@link KeyValueListPair}
     * @return The KeyValueListPair builder.
     */
    Builder1 key(@Nonnull final String key);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link KeyValueListPair} instance.
     *
     * @param value The value of this {@link KeyValueListPair}
     * @return The KeyValueListPair instance.
     */
    KeyValueListPair value(@Nonnull final List<String> value);

    /**
     * Set the value of this {@link KeyValueListPair} instance.
     *
     * @param value The value of this {@link KeyValueListPair}
     * @return The KeyValueListPair instance.
     */
    default KeyValueListPair value(@Nonnull final String... value) {
      return value(Arrays.asList(value));
    }
  }
}