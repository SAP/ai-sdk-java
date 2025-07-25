/*
 * Grounding
 * Grounding is a service designed to handle data-related tasks, such as grounding and retrieval, using vector databases. It provides specialized data retrieval through these databases, grounding the retrieval process with your own external and context-relevant data. Grounding combines generative AI capabilities with the ability to use real-time, precise data to improve decision-making and business operations for specific AI-driven business solutions.
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

/** TextOnlyBaseChunk */
// CHECKSTYLE:OFF
public class TextOnlyBaseChunk
// CHECKSTYLE:ON
{
  @JsonProperty("content")
  private String content;

  @JsonProperty("metadata")
  private List<KeyValueListPair> metadata = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TextOnlyBaseChunk. */
  protected TextOnlyBaseChunk() {}

  /**
   * Set the content of this {@link TextOnlyBaseChunk} instance and return the same instance.
   *
   * @param content The content of this {@link TextOnlyBaseChunk}
   * @return The same instance of this {@link TextOnlyBaseChunk} class
   */
  @Nonnull
  public TextOnlyBaseChunk content(@Nonnull final String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link TextOnlyBaseChunk} instance.
   */
  @Nonnull
  public String getContent() {
    return content;
  }

  /**
   * Set the content of this {@link TextOnlyBaseChunk} instance.
   *
   * @param content The content of this {@link TextOnlyBaseChunk}
   */
  public void setContent(@Nonnull final String content) {
    this.content = content;
  }

  /**
   * Set the metadata of this {@link TextOnlyBaseChunk} instance and return the same instance.
   *
   * @param metadata The metadata of this {@link TextOnlyBaseChunk}
   * @return The same instance of this {@link TextOnlyBaseChunk} class
   */
  @Nonnull
  public TextOnlyBaseChunk metadata(@Nonnull final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link TextOnlyBaseChunk}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link TextOnlyBaseChunk}
   */
  @Nonnull
  public TextOnlyBaseChunk addMetadataItem(@Nonnull final KeyValueListPair metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
   * @return metadata The metadata of this {@link TextOnlyBaseChunk} instance.
   */
  @Nonnull
  public List<KeyValueListPair> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link TextOnlyBaseChunk} instance.
   *
   * @param metadata The metadata of this {@link TextOnlyBaseChunk}
   */
  public void setMetadata(@Nonnull final List<KeyValueListPair> metadata) {
    this.metadata = metadata;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TextOnlyBaseChunk}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TextOnlyBaseChunk} instance.
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
      throw new NoSuchElementException("TextOnlyBaseChunk has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link TextOnlyBaseChunk} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (content != null) declaredFields.put("content", content);
    if (metadata != null) declaredFields.put("metadata", metadata);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link TextOnlyBaseChunk} instance. If the map
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
    final TextOnlyBaseChunk textOnlyBaseChunk = (TextOnlyBaseChunk) o;
    return Objects.equals(this.cloudSdkCustomFields, textOnlyBaseChunk.cloudSdkCustomFields)
        && Objects.equals(this.content, textOnlyBaseChunk.content)
        && Objects.equals(this.metadata, textOnlyBaseChunk.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, metadata, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TextOnlyBaseChunk {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TextOnlyBaseChunk}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (content) -> (metadata) -> new TextOnlyBaseChunk().content(content).metadata(metadata);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the content of this {@link TextOnlyBaseChunk} instance.
     *
     * @param content The content of this {@link TextOnlyBaseChunk}
     * @return The TextOnlyBaseChunk builder.
     */
    Builder1 content(@Nonnull final String content);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the metadata of this {@link TextOnlyBaseChunk} instance.
     *
     * @param metadata The metadata of this {@link TextOnlyBaseChunk}
     * @return The TextOnlyBaseChunk instance.
     */
    TextOnlyBaseChunk metadata(@Nonnull final List<KeyValueListPair> metadata);

    /**
     * Set the metadata of this {@link TextOnlyBaseChunk} instance.
     *
     * @param metadata The metadata of this {@link TextOnlyBaseChunk}
     * @return The TextOnlyBaseChunk instance.
     */
    default TextOnlyBaseChunk metadata(@Nonnull final KeyValueListPair... metadata) {
      return metadata(Arrays.asList(metadata));
    }
  }
}
