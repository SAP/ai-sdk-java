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

/** DocumentKeyValueListPair */
@Beta // CHECKSTYLE:OFF
public class DocumentKeyValueListPair
// CHECKSTYLE:ON
{
  @JsonProperty("key")
  private String key;

  @JsonProperty("value")
  private List<String> value = new ArrayList<>();

  /** Gets or Sets matchMode */
  public enum MatchModeEnum {
    /** The ANY option of this DocumentKeyValueListPair */
    ANY("ANY"),

    /** The ALL option of this DocumentKeyValueListPair */
    ALL("ALL"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this DocumentKeyValueListPair */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    MatchModeEnum(String value) {
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
     * @return The enum value of type DocumentKeyValueListPair
     */
    @JsonCreator
    @Nonnull
    public static MatchModeEnum fromValue(@Nonnull final String value) {
      for (MatchModeEnum b : MatchModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("matchMode")
  private MatchModeEnum matchMode;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DocumentKeyValueListPair. */
  protected DocumentKeyValueListPair() {}

  /**
   * Set the key of this {@link DocumentKeyValueListPair} instance and return the same instance.
   *
   * @param key The key of this {@link DocumentKeyValueListPair}
   * @return The same instance of this {@link DocumentKeyValueListPair} class
   */
  @Nonnull
  public DocumentKeyValueListPair key(@Nonnull final String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key The key of this {@link DocumentKeyValueListPair} instance.
   */
  @Nonnull
  public String getKey() {
    return key;
  }

  /**
   * Set the key of this {@link DocumentKeyValueListPair} instance.
   *
   * @param key The key of this {@link DocumentKeyValueListPair}
   */
  public void setKey(@Nonnull final String key) {
    this.key = key;
  }

  /**
   * Set the value of this {@link DocumentKeyValueListPair} instance and return the same instance.
   *
   * @param value The value of this {@link DocumentKeyValueListPair}
   * @return The same instance of this {@link DocumentKeyValueListPair} class
   */
  @Nonnull
  public DocumentKeyValueListPair value(@Nonnull final List<String> value) {
    this.value = value;
    return this;
  }

  /**
   * Add one value instance to this {@link DocumentKeyValueListPair}.
   *
   * @param valueItem The value that should be added
   * @return The same instance of type {@link DocumentKeyValueListPair}
   */
  @Nonnull
  public DocumentKeyValueListPair addValueItem(@Nonnull final String valueItem) {
    if (this.value == null) {
      this.value = new ArrayList<>();
    }
    this.value.add(valueItem);
    return this;
  }

  /**
   * Get value
   *
   * @return value The value of this {@link DocumentKeyValueListPair} instance.
   */
  @Nonnull
  public List<String> getValue() {
    return value;
  }

  /**
   * Set the value of this {@link DocumentKeyValueListPair} instance.
   *
   * @param value The value of this {@link DocumentKeyValueListPair}
   */
  public void setValue(@Nonnull final List<String> value) {
    this.value = value;
  }

  /**
   * Set the matchMode of this {@link DocumentKeyValueListPair} instance and return the same
   * instance.
   *
   * @param matchMode The matchMode of this {@link DocumentKeyValueListPair}
   * @return The same instance of this {@link DocumentKeyValueListPair} class
   */
  @Nonnull
  public DocumentKeyValueListPair matchMode(@Nullable final MatchModeEnum matchMode) {
    this.matchMode = matchMode;
    return this;
  }

  /**
   * Get matchMode
   *
   * @return matchMode The matchMode of this {@link DocumentKeyValueListPair} instance.
   */
  @Nullable
  public MatchModeEnum getMatchMode() {
    return matchMode;
  }

  /**
   * Set the matchMode of this {@link DocumentKeyValueListPair} instance.
   *
   * @param matchMode The matchMode of this {@link DocumentKeyValueListPair}
   */
  public void setMatchMode(@Nullable final MatchModeEnum matchMode) {
    this.matchMode = matchMode;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DocumentKeyValueListPair}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DocumentKeyValueListPair} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "DocumentKeyValueListPair has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DocumentKeyValueListPair} instance. If the map
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
    final DocumentKeyValueListPair documentKeyValueListPair = (DocumentKeyValueListPair) o;
    return Objects.equals(this.cloudSdkCustomFields, documentKeyValueListPair.cloudSdkCustomFields)
        && Objects.equals(this.key, documentKeyValueListPair.key)
        && Objects.equals(this.value, documentKeyValueListPair.value)
        && Objects.equals(this.matchMode, documentKeyValueListPair.matchMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, matchMode, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DocumentKeyValueListPair {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    matchMode: ").append(toIndentedString(matchMode)).append("\n");
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
   * DocumentKeyValueListPair} instance with all required arguments.
   */
  public static Builder create() {
    return (key) -> (value) -> new DocumentKeyValueListPair().key(key).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the key of this {@link DocumentKeyValueListPair} instance.
     *
     * @param key The key of this {@link DocumentKeyValueListPair}
     * @return The DocumentKeyValueListPair builder.
     */
    Builder1 key(@Nonnull final String key);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link DocumentKeyValueListPair} instance.
     *
     * @param value The value of this {@link DocumentKeyValueListPair}
     * @return The DocumentKeyValueListPair instance.
     */
    DocumentKeyValueListPair value(@Nonnull final List<String> value);

    /**
     * Set the value of this {@link DocumentKeyValueListPair} instance.
     *
     * @param value The value of this {@link DocumentKeyValueListPair}
     * @return The DocumentKeyValueListPair instance.
     */
    default DocumentKeyValueListPair value(@Nonnull final String... value) {
      return value(Arrays.asList(value));
    }
  }
}
