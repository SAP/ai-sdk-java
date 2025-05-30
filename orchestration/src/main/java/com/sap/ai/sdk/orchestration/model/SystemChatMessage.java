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

/** SystemChatMessage */
// CHECKSTYLE:OFF
public class SystemChatMessage implements ChatMessage
// CHECKSTYLE:ON
{
  /** Gets or Sets role */
  public enum RoleEnum {
    /** The SYSTEM option of this SystemChatMessage */
    SYSTEM("system"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this SystemChatMessage */
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
     * @return The enum value of type SystemChatMessage
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

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for SystemChatMessage. */
  protected SystemChatMessage() {}

  /**
   * Set the role of this {@link SystemChatMessage} instance and return the same instance.
   *
   * @param role The role of this {@link SystemChatMessage}
   * @return The same instance of this {@link SystemChatMessage} class
   */
  @Nonnull
  public SystemChatMessage role(@Nonnull final RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link SystemChatMessage} instance.
   */
  @Nonnull
  public RoleEnum getRole() {
    return role;
  }

  /**
   * Set the role of this {@link SystemChatMessage} instance.
   *
   * @param role The role of this {@link SystemChatMessage}
   */
  public void setRole(@Nonnull final RoleEnum role) {
    this.role = role;
  }

  /**
   * Set the content of this {@link SystemChatMessage} instance and return the same instance.
   *
   * @param content The content of this {@link SystemChatMessage}
   * @return The same instance of this {@link SystemChatMessage} class
   */
  @Nonnull
  public SystemChatMessage content(@Nonnull final ChatMessageContent content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content The content of this {@link SystemChatMessage} instance.
   */
  @Nonnull
  public ChatMessageContent getContent() {
    return content;
  }

  /**
   * Set the content of this {@link SystemChatMessage} instance.
   *
   * @param content The content of this {@link SystemChatMessage}
   */
  public void setContent(@Nonnull final ChatMessageContent content) {
    this.content = content;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link SystemChatMessage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link SystemChatMessage} instance.
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
      throw new NoSuchElementException("SystemChatMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link SystemChatMessage} instance including
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
   * Set an unrecognizable property of this {@link SystemChatMessage} instance. If the map
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
    final SystemChatMessage systemChatMessage = (SystemChatMessage) o;
    return Objects.equals(this.cloudSdkCustomFields, systemChatMessage.cloudSdkCustomFields)
        && Objects.equals(this.role, systemChatMessage.role)
        && Objects.equals(this.content, systemChatMessage.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class SystemChatMessage {\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link SystemChatMessage}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (role) -> (content) -> new SystemChatMessage().role(role).content(content);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the role of this {@link SystemChatMessage} instance.
     *
     * @param role The role of this {@link SystemChatMessage}
     * @return The SystemChatMessage builder.
     */
    Builder1 role(@Nonnull final RoleEnum role);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the content of this {@link SystemChatMessage} instance.
     *
     * @param content The content of this {@link SystemChatMessage}
     * @return The SystemChatMessage instance.
     */
    SystemChatMessage content(@Nonnull final ChatMessageContent content);
  }
}
