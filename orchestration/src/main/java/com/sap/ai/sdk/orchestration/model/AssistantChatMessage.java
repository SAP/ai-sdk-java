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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** AssistantChatMessage */
// CHECKSTYLE:OFF
public class AssistantChatMessage implements ChatMessage
// CHECKSTYLE:ON
{
  /** Gets or Sets role */
  public enum RoleEnum {
    /** The ASSISTANT option of this AssistantChatMessage */
    ASSISTANT("assistant"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this AssistantChatMessage */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    RoleEnum(String value) {
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
     * @return The enum value of type AssistantChatMessage
     */
    @JsonCreator
    @Nonnull
    public static RoleEnum fromValue(@Nonnull final String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("role")
  private RoleEnum role;

  @JsonProperty("content")
  private ChatMessageContent content;

  @JsonProperty("refusal")
  private String refusal;

  @JsonProperty("tool_calls")
  private List<MessageToolCall> toolCalls = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AssistantChatMessage. */
  protected AssistantChatMessage() {}

  /**
   * Set the role of this {@link AssistantChatMessage} instance and return the same instance.
   *
   * @param role The role of this {@link AssistantChatMessage}
   * @return The same instance of this {@link AssistantChatMessage} class
   */
  @Nonnull
  public AssistantChatMessage role(@Nonnull final RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link AssistantChatMessage} instance.
   */
  @Nonnull
  public RoleEnum getRole() {
    return role;
  }

  /**
   * Set the role of this {@link AssistantChatMessage} instance.
   *
   * @param role The role of this {@link AssistantChatMessage}
   */
  public void setRole(@Nonnull final RoleEnum role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link AssistantChatMessage} instance and return the same instance.
   *
   * @param content The content of this {@link AssistantChatMessage}
   * @return The same instance of this {@link AssistantChatMessage} class
   */
  @Nonnull
  public AssistantChatMessage content(@Nullable final ChatMessageContent content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link AssistantChatMessage} instance.
   */
  @Nonnull
  public ChatMessageContent getContent() {
    return content;
  }

  /**
   * Set the content of this {@link AssistantChatMessage} instance.
   *
   * @param content The content of this {@link AssistantChatMessage}
   */
  public void setContent(@Nullable final ChatMessageContent content) {
    this.content = content;
  }

  /**
   * Set the refusal of this {@link AssistantChatMessage} instance and return the same instance.
   *
   * @param refusal The refusal of this {@link AssistantChatMessage}
   * @return The same instance of this {@link AssistantChatMessage} class
   */
  @Nonnull
  public AssistantChatMessage refusal(@Nullable final String refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * Get refusal
   *
   * @return refusal The refusal of this {@link AssistantChatMessage} instance.
   */
  @Nonnull
  public String getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link AssistantChatMessage} instance.
   *
   * @param refusal The refusal of this {@link AssistantChatMessage}
   */
  public void setRefusal(@Nullable final String refusal) {
    this.refusal = refusal;
  }

  /**
   * Set the toolCalls of this {@link AssistantChatMessage} instance and return the same instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   * @return The same instance of this {@link AssistantChatMessage} class
   */
  @Nonnull
  public AssistantChatMessage toolCalls(@Nullable final List<MessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
    return this;
  }

  /**
   * Add one toolCalls instance to this {@link AssistantChatMessage}.
   *
   * @param toolCallsItem The toolCalls that should be added
   * @return The same instance of type {@link AssistantChatMessage}
   */
  @Nonnull
  public AssistantChatMessage addToolCallsItem(@Nonnull final MessageToolCall toolCallsItem) {
    if (this.toolCalls == null) {
      this.toolCalls = new ArrayList<>();
    }
    this.toolCalls.add(toolCallsItem);
    return this;
  }

  /**
   * The tool calls generated by the model, such as function calls.
   *
   * @return toolCalls The toolCalls of this {@link AssistantChatMessage} instance.
   */
  @Nonnull
  public List<MessageToolCall> getToolCalls() {
    return toolCalls;
  }

  /**
   * Set the toolCalls of this {@link AssistantChatMessage} instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   */
  public void setToolCalls(@Nullable final List<MessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AssistantChatMessage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AssistantChatMessage} instance.
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
      throw new NoSuchElementException(
          "AssistantChatMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link AssistantChatMessage} instance including
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
    if (refusal != null) declaredFields.put("refusal", refusal);
    if (toolCalls != null) declaredFields.put("toolCalls", toolCalls);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link AssistantChatMessage} instance. If the map
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
    final AssistantChatMessage assistantChatMessage = (AssistantChatMessage) o;
    return Objects.equals(this.cloudSdkCustomFields, assistantChatMessage.cloudSdkCustomFields)
        && Objects.equals(this.role, assistantChatMessage.role)
        && Objects.equals(this.content, assistantChatMessage.content)
        && Objects.equals(this.refusal, assistantChatMessage.refusal)
        && Objects.equals(this.toolCalls, assistantChatMessage.toolCalls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, refusal, toolCalls, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AssistantChatMessage {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AssistantChatMessage}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (role) -> new AssistantChatMessage().role(role);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the role of this {@link AssistantChatMessage} instance.
     *
     * @param role The role of this {@link AssistantChatMessage}
     * @return The AssistantChatMessage instance.
     */
    AssistantChatMessage role(@Nonnull final RoleEnum role);
  }
}
