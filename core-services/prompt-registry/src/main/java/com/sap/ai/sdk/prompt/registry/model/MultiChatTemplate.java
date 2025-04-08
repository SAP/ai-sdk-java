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

/** MultiChatTemplate */
// CHECKSTYLE:OFF
public class MultiChatTemplate implements Template
// CHECKSTYLE:ON
{
  @JsonProperty("role")
  private String role;

  @JsonProperty("content")
  private List<MultiChatContent> content = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MultiChatTemplate. */
  protected MultiChatTemplate() {}

  /**
   * Set the role of this {@link MultiChatTemplate} instance and return the same instance.
   *
   * @param role The role of this {@link MultiChatTemplate}
   * @return The same instance of this {@link MultiChatTemplate} class
   */
  @Nonnull
  public MultiChatTemplate role(@Nonnull final String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link MultiChatTemplate} instance.
   */
  @Nonnull
  public String getRole() {
    return role;
  }

  /**
   * Set the role of this {@link MultiChatTemplate} instance.
   *
   * @param role The role of this {@link MultiChatTemplate}
   */
  public void setRole(@Nonnull final String role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link MultiChatTemplate} instance and return the same instance.
   *
   * @param content The content of this {@link MultiChatTemplate}
   * @return The same instance of this {@link MultiChatTemplate} class
   */
  @Nonnull
  public MultiChatTemplate content(@Nonnull final List<MultiChatContent> content) {
    this.content = content;
    return this;
  }

  /**
   * Add one content instance to this {@link MultiChatTemplate}.
   *
   * @param contentItem The content that should be added
   * @return The same instance of type {@link MultiChatTemplate}
   */
  @Nonnull
  public MultiChatTemplate addContentItem(@Nonnull final MultiChatContent contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link MultiChatTemplate} instance.
   */
  @Nonnull
  public List<MultiChatContent> getContent() {
    return content;
  }

  /**
   * Set the content of this {@link MultiChatTemplate} instance.
   *
   * @param content The content of this {@link MultiChatTemplate}
   */
  public void setContent(@Nonnull final List<MultiChatContent> content) {
    this.content = content;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MultiChatTemplate}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MultiChatTemplate} instance.
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
      throw new NoSuchElementException("MultiChatTemplate has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link MultiChatTemplate} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (role != null) declaredFields.put("role", role);
    if (content != null) declaredFields.put("content", content);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link MultiChatTemplate} instance. If the map
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
    final MultiChatTemplate multiChatTemplate = (MultiChatTemplate) o;
    return Objects.equals(this.cloudSdkCustomFields, multiChatTemplate.cloudSdkCustomFields)
        && Objects.equals(this.role, multiChatTemplate.role)
        && Objects.equals(this.content, multiChatTemplate.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MultiChatTemplate {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link MultiChatTemplate}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (role) -> (content) -> new MultiChatTemplate().role(role).content(content);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the role of this {@link MultiChatTemplate} instance.
     *
     * @param role The role of this {@link MultiChatTemplate}
     * @return The MultiChatTemplate builder.
     */
    Builder1 role(@Nonnull final String role);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the content of this {@link MultiChatTemplate} instance.
     *
     * @param content The content of this {@link MultiChatTemplate}
     * @return The MultiChatTemplate instance.
     */
    MultiChatTemplate content(@Nonnull final List<MultiChatContent> content);

    /**
     * Set the content of this {@link MultiChatTemplate} instance.
     *
     * @param content The content of this {@link MultiChatTemplate}
     * @return The MultiChatTemplate instance.
     */
    default MultiChatTemplate content(@Nonnull final MultiChatContent... content) {
      return content(Arrays.asList(content));
    }
  }
}
