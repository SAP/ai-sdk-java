/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.29.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

/** ChatMessage */
@JsonPropertyOrder({ChatMessage.JSON_PROPERTY_ROLE, ChatMessage.JSON_PROPERTY_CONTENT})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2024-11-08T18:02:22.585601+01:00[Europe/Berlin]",
    comments = "Generator version: 7.9.0")
public class ChatMessage {
  public static final String JSON_PROPERTY_ROLE = "role";
  private String role;

  public static final String JSON_PROPERTY_CONTENT = "content";
  private String content;

  public ChatMessage() {}

  public ChatMessage role(String role) {

    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ROLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getRole() {
    return role;
  }

  @JsonProperty(JSON_PROPERTY_ROLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRole(String role) {
    this.role = role;
  }

  public ChatMessage content(String content) {

    this.content = content;
    return this;
  }

  /**
   * Get content
   *
   * @return content
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CONTENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getContent() {
    return content;
  }

  @JsonProperty(JSON_PROPERTY_CONTENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatMessage chatMessage = (ChatMessage) o;
    return Objects.equals(this.role, chatMessage.role)
        && Objects.equals(this.content, chatMessage.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatMessage {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public static class Builder {

    private ChatMessage instance;

    public Builder() {
      this(new ChatMessage());
    }

    protected Builder(ChatMessage instance) {
      this.instance = instance;
    }

    public ChatMessage.Builder role(String role) {
      this.instance.role = role;
      return this;
    }

    public ChatMessage.Builder content(String content) {
      this.instance.content = content;
      return this;
    }

    /**
     * returns a built ChatMessage instance.
     *
     * <p>The builder is not reusable.
     */
    public ChatMessage build() {
      try {
        return this.instance;
      } finally {
        // ensure that this.instance is not reused
        this.instance = null;
      }
    }

    @Override
    public String toString() {
      return getClass() + "=(" + instance + ")";
    }
  }

  /** Create a builder with no initialized field. */
  public static ChatMessage.Builder builder() {
    return new ChatMessage.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public ChatMessage.Builder toBuilder() {
    return new ChatMessage.Builder().role(getRole()).content(getContent());
  }
}
