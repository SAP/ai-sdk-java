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

/** SearchDocumentKeyValueListPair */
@Beta // CHECKSTYLE:OFF
public class SearchDocumentKeyValueListPair
// CHECKSTYLE:ON
{
  @JsonProperty("key")
  private String key;

  @JsonProperty("value")
  private List<String> value = new ArrayList<>();

  @JsonProperty("selectMode")
  private List<SearchSelectOptionEnum> selectMode = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for SearchDocumentKeyValueListPair. */
  protected SearchDocumentKeyValueListPair() {}

  /**
   * Set the key of this {@link SearchDocumentKeyValueListPair} instance and return the same
   * instance.
   *
   * @param key The key of this {@link SearchDocumentKeyValueListPair}
   * @return The same instance of this {@link SearchDocumentKeyValueListPair} class
   */
  @Nonnull
  public SearchDocumentKeyValueListPair key(@Nonnull final String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key The key of this {@link SearchDocumentKeyValueListPair} instance.
   */
  @Nonnull
  public String getKey() {
    return key;
  }

  /**
   * Set the key of this {@link SearchDocumentKeyValueListPair} instance.
   *
   * @param key The key of this {@link SearchDocumentKeyValueListPair}
   */
  public void setKey(@Nonnull final String key) {
    this.key = key;
  }

  /**
   * Set the value of this {@link SearchDocumentKeyValueListPair} instance and return the same
   * instance.
   *
   * @param value The value of this {@link SearchDocumentKeyValueListPair}
   * @return The same instance of this {@link SearchDocumentKeyValueListPair} class
   */
  @Nonnull
  public SearchDocumentKeyValueListPair value(@Nonnull final List<String> value) {
    this.value = value;
    return this;
  }

  /**
   * Add one value instance to this {@link SearchDocumentKeyValueListPair}.
   *
   * @param valueItem The value that should be added
   * @return The same instance of type {@link SearchDocumentKeyValueListPair}
   */
  @Nonnull
  public SearchDocumentKeyValueListPair addValueItem(@Nonnull final String valueItem) {
    if (this.value == null) {
      this.value = new ArrayList<>();
    }
    this.value.add(valueItem);
    return this;
  }

  /**
   * Get value
   *
   * @return value The value of this {@link SearchDocumentKeyValueListPair} instance.
   */
  @Nonnull
  public List<String> getValue() {
    return value;
  }

  /**
   * Set the value of this {@link SearchDocumentKeyValueListPair} instance.
   *
   * @param value The value of this {@link SearchDocumentKeyValueListPair}
   */
  public void setValue(@Nonnull final List<String> value) {
    this.value = value;
  }

  /**
   * Set the selectMode of this {@link SearchDocumentKeyValueListPair} instance and return the same
   * instance.
   *
   * @param selectMode Select mode for search filters
   * @return The same instance of this {@link SearchDocumentKeyValueListPair} class
   */
  @Nonnull
  public SearchDocumentKeyValueListPair selectMode(
      @Nullable final List<SearchSelectOptionEnum> selectMode) {
    this.selectMode = selectMode;
    return this;
  }

  /**
   * Add one selectMode instance to this {@link SearchDocumentKeyValueListPair}.
   *
   * @param selectModeItem The selectMode that should be added
   * @return The same instance of type {@link SearchDocumentKeyValueListPair}
   */
  @Nonnull
  public SearchDocumentKeyValueListPair addSelectModeItem(
      @Nonnull final SearchSelectOptionEnum selectModeItem) {
    if (this.selectMode == null) {
      this.selectMode = new ArrayList<>();
    }
    this.selectMode.add(selectModeItem);
    return this;
  }

  /**
   * Select mode for search filters
   *
   * @return selectMode The selectMode of this {@link SearchDocumentKeyValueListPair} instance.
   */
  @Nonnull
  public List<SearchSelectOptionEnum> getSelectMode() {
    return selectMode;
  }

  /**
   * Set the selectMode of this {@link SearchDocumentKeyValueListPair} instance.
   *
   * @param selectMode Select mode for search filters
   */
  public void setSelectMode(@Nullable final List<SearchSelectOptionEnum> selectMode) {
    this.selectMode = selectMode;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link SearchDocumentKeyValueListPair}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link SearchDocumentKeyValueListPair}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "SearchDocumentKeyValueListPair has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link SearchDocumentKeyValueListPair} instance. If the
   * map previously contained a mapping for the key, the old value is replaced by the specified
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
    final SearchDocumentKeyValueListPair searchDocumentKeyValueListPair =
        (SearchDocumentKeyValueListPair) o;
    return Objects.equals(
            this.cloudSdkCustomFields, searchDocumentKeyValueListPair.cloudSdkCustomFields)
        && Objects.equals(this.key, searchDocumentKeyValueListPair.key)
        && Objects.equals(this.value, searchDocumentKeyValueListPair.value)
        && Objects.equals(this.selectMode, searchDocumentKeyValueListPair.selectMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, selectMode, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class SearchDocumentKeyValueListPair {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    selectMode: ").append(toIndentedString(selectMode)).append("\n");
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
   * SearchDocumentKeyValueListPair} instance with all required arguments.
   */
  public static Builder create() {
    return (key) -> (value) -> new SearchDocumentKeyValueListPair().key(key).value(value);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the key of this {@link SearchDocumentKeyValueListPair} instance.
     *
     * @param key The key of this {@link SearchDocumentKeyValueListPair}
     * @return The SearchDocumentKeyValueListPair builder.
     */
    Builder1 key(@Nonnull final String key);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the value of this {@link SearchDocumentKeyValueListPair} instance.
     *
     * @param value The value of this {@link SearchDocumentKeyValueListPair}
     * @return The SearchDocumentKeyValueListPair instance.
     */
    SearchDocumentKeyValueListPair value(@Nonnull final List<String> value);

    /**
     * Set the value of this {@link SearchDocumentKeyValueListPair} instance.
     *
     * @param value The value of this {@link SearchDocumentKeyValueListPair}
     * @return The SearchDocumentKeyValueListPair instance.
     */
    default SearchDocumentKeyValueListPair value(@Nonnull final String... value) {
      return value(Arrays.asList(value));
    }
  }
}
