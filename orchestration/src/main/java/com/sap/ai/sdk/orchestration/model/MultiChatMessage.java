/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.48.2
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

/** MultiChatMessage */
@Beta // CHECKSTYLE:OFF
public class MultiChatMessage implements ChatMessage
// CHECKSTYLE:ON
{
  @JsonProperty("role")
  private String role;

  @JsonProperty("content")
  private List<MultiChatMessageContent> content = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for MultiChatMessage. */
  protected MultiChatMessage() {}

  /**
   * Set the role of this {@link MultiChatMessage} instance and return the same instance.
   *
   * @param role The role of this {@link MultiChatMessage}
   * @return The same instance of this {@link MultiChatMessage} class
   */
  @Nonnull
  public MultiChatMessage role(@Nonnull final String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link MultiChatMessage} instance.
   */
  @Nonnull
  public String getRole() {
    return role;
  }

  /**
   * Set the role of this {@link MultiChatMessage} instance.
   *
   * @param role The role of this {@link MultiChatMessage}
   */
  public void setRole(@Nonnull final String role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link MultiChatMessage} instance and return the same instance.
   *
   * @param content The content of this {@link MultiChatMessage}
   * @return The same instance of this {@link MultiChatMessage} class
   */
  @Nonnull
  public MultiChatMessage content(@Nonnull final List<MultiChatMessageContent> content) {
    this.content = content;
    return this;
  }

  /**
   * Add one content instance to this {@link MultiChatMessage}.
   *
   * @param contentItem The content that should be added
   * @return The same instance of type {@link MultiChatMessage}
   */
  @Nonnull
  public MultiChatMessage addContentItem(@Nonnull final MultiChatMessageContent contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link MultiChatMessage} instance.
   */
  @Nonnull
  public List<MultiChatMessageContent> getContent() {
    return content;
  }

  /**
   * Set the content of this {@link MultiChatMessage} instance.
   *
   * @param content The content of this {@link MultiChatMessage}
   */
  public void setContent(@Nonnull final List<MultiChatMessageContent> content) {
    this.content = content;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link MultiChatMessage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link MultiChatMessage} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("MultiChatMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link MultiChatMessage} instance. If the map previously
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
    final MultiChatMessage multiChatMessage = (MultiChatMessage) o;
    return Objects.equals(this.cloudSdkCustomFields, multiChatMessage.cloudSdkCustomFields)
        && Objects.equals(this.role, multiChatMessage.role)
        && Objects.equals(this.content, multiChatMessage.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class MultiChatMessage {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link MultiChatMessage}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (role) -> (content) -> new MultiChatMessage().role(role).content(content);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the role of this {@link MultiChatMessage} instance.
     *
     * @param role The role of this {@link MultiChatMessage}
     * @return The MultiChatMessage builder.
     */
    Builder1 role(@Nonnull final String role);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the content of this {@link MultiChatMessage} instance.
     *
     * @param content The content of this {@link MultiChatMessage}
     * @return The MultiChatMessage instance.
     */
    MultiChatMessage content(@Nonnull final List<MultiChatMessageContent> content);

    /**
     * Set the content of this {@link MultiChatMessage} instance.
     *
     * @param content The content of this {@link MultiChatMessage}
     * @return The MultiChatMessage instance.
     */
    default MultiChatMessage content(@Nonnull final MultiChatMessageContent... content) {
      return content(Arrays.asList(content));
    }
  }
}
