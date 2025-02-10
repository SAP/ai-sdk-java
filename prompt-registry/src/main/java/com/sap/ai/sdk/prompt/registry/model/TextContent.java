/*
 * Prompt Registry API
 * Prompt Storage service for Design time & Runtime prompt templates.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.prompt.registry.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** TextContent */
@Beta // CHECKSTYLE:OFF
public class TextContent implements MultiChatContent
// CHECKSTYLE:ON
{
  /** Gets or Sets type */
  public enum TypeEnum {
    /** The TEXT option of this TextContent */
    TEXT("text"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this TextContent */
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
     * @return The enum value of type TextContent
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

  @JsonProperty("text")
  private String text;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TextContent. */
  protected TextContent() {}

  /**
   * Set the type of this {@link TextContent} instance and return the same instance.
   *
   * @param type The type of this {@link TextContent}
   * @return The same instance of this {@link TextContent} class
   */
  @Nonnull
  public TextContent type(@Nonnull final TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type The type of this {@link TextContent} instance.
   */
  @Nonnull
  public TypeEnum getType() {
    return type;
  }

  /**
   * Set the type of this {@link TextContent} instance.
   *
   * @param type The type of this {@link TextContent}
   */
  public void setType(@Nonnull final TypeEnum type) {
    this.type = type;
  }

  /**
   * Set the text of this {@link TextContent} instance and return the same instance.
   *
   * @param text The text of this {@link TextContent}
   * @return The same instance of this {@link TextContent} class
   */
  @Nonnull
  public TextContent text(@Nonnull final String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   *
   * @return text The text of this {@link TextContent} instance.
   */
  @Nonnull
  public String getText() {
    return text;
  }

  /**
   * Set the text of this {@link TextContent} instance.
   *
   * @param text The text of this {@link TextContent}
   */
  public void setText(@Nonnull final String text) {
    this.text = text;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TextContent}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TextContent} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TextContent has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TextContent} instance. If the map previously
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
    final TextContent textContent = (TextContent) o;
    return Objects.equals(this.cloudSdkCustomFields, textContent.cloudSdkCustomFields)
        && Objects.equals(this.type, textContent.type)
        && Objects.equals(this.text, textContent.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, text, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TextContent {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TextContent} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (type) -> (text) -> new TextContent().type(type).text(text);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the type of this {@link TextContent} instance.
     *
     * @param type The type of this {@link TextContent}
     * @return The TextContent builder.
     */
    Builder1 type(@Nonnull final TypeEnum type);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the text of this {@link TextContent} instance.
     *
     * @param text The text of this {@link TextContent}
     * @return The TextContent instance.
     */
    TextContent text(@Nonnull final String text);
  }
}
