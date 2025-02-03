/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ChatDelta */
@Beta // CHECKSTYLE:OFF
public class ChatDelta
// CHECKSTYLE:ON
{
  @JsonProperty("role")
  private String role;

  @JsonProperty("content")
  private String content = "";

  @JsonProperty("refusal")
  private String refusal;

  @JsonProperty("tool_calls")
  private List<ToolCallChunk> toolCalls = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ChatDelta. */
  protected ChatDelta() {}

  /**
   * Set the role of this {@link ChatDelta} instance and return the same instance.
   *
   * @param role The role of this {@link ChatDelta}
   * @return The same instance of this {@link ChatDelta} class
   */
  @Nonnull
  public ChatDelta role(@Nullable final String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link ChatDelta} instance.
   */
  @Nonnull
  public String getRole() {
    return role;
  }

  /**
   * Set the role of this {@link ChatDelta} instance.
   *
   * @param role The role of this {@link ChatDelta}
   */
  public void setRole(@Nullable final String role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link ChatDelta} instance and return the same instance.
   *
   * @param content The content of this {@link ChatDelta}
   * @return The same instance of this {@link ChatDelta} class
   */
  @Nonnull
  public ChatDelta content(@Nonnull final String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link ChatDelta} instance.
   */
  @Nonnull
  public String getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatDelta} instance.
   *
   * @param content The content of this {@link ChatDelta}
   */
  public void setContent(@Nonnull final String content) {
    this.content = content;
  }

  /**
   * Set the refusal of this {@link ChatDelta} instance and return the same instance.
   *
   * @param refusal The refusal message generated by the model.
   * @return The same instance of this {@link ChatDelta} class
   */
  @Nonnull
  public ChatDelta refusal(@Nullable final String refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * The refusal message generated by the model.
   *
   * @return refusal The refusal of this {@link ChatDelta} instance.
   */
  @Nonnull
  public String getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link ChatDelta} instance.
   *
   * @param refusal The refusal message generated by the model.
   */
  public void setRefusal(@Nullable final String refusal) {
    this.refusal = refusal;
  }

  /**
   * Set the toolCalls of this {@link ChatDelta} instance and return the same instance.
   *
   * @param toolCalls The toolCalls of this {@link ChatDelta}
   * @return The same instance of this {@link ChatDelta} class
   */
  @Nonnull
  public ChatDelta toolCalls(@Nullable final List<ToolCallChunk> toolCalls) {
    this.toolCalls = toolCalls;
    return this;
  }

  /**
   * Add one toolCalls instance to this {@link ChatDelta}.
   *
   * @param toolCallsItem The toolCalls that should be added
   * @return The same instance of type {@link ChatDelta}
   */
  @Nonnull
  public ChatDelta addToolCallsItem(@Nonnull final ToolCallChunk toolCallsItem) {
    if (this.toolCalls == null) {
      this.toolCalls = new ArrayList<>();
    }
    this.toolCalls.add(toolCallsItem);
    return this;
  }

  /**
   * Get toolCalls
   *
   * @return toolCalls The toolCalls of this {@link ChatDelta} instance.
   */
  @Nonnull
  public List<ToolCallChunk> getToolCalls() {
    return toolCalls;
  }

  /**
   * Set the toolCalls of this {@link ChatDelta} instance.
   *
   * @param toolCalls The toolCalls of this {@link ChatDelta}
   */
  public void setToolCalls(@Nullable final List<ToolCallChunk> toolCalls) {
    this.toolCalls = toolCalls;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatDelta}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatDelta} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("ChatDelta has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatDelta} instance. If the map previously
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
    final ChatDelta chatDelta = (ChatDelta) o;
    return Objects.equals(this.cloudSdkCustomFields, chatDelta.cloudSdkCustomFields)
        && Objects.equals(this.role, chatDelta.role)
        && Objects.equals(this.content, chatDelta.content)
        && Objects.equals(this.refusal, chatDelta.refusal)
        && Objects.equals(this.toolCalls, chatDelta.toolCalls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, refusal, toolCalls, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatDelta {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    refusal: ").append(toIndentedString(refusal)).append("\n");
    sb.append("    toolCalls: ").append(toIndentedString(toolCalls)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link ChatDelta} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (content) -> new ChatDelta().content(content);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the content of this {@link ChatDelta} instance.
     *
     * @param content The content of this {@link ChatDelta}
     * @return The ChatDelta instance.
     */
    ChatDelta content(@Nonnull final String content);
  }
}
