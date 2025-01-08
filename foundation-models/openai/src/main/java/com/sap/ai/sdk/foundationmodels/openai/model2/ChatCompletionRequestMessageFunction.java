/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.model2;

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

/** ChatCompletionRequestMessageFunction */
// CHECKSTYLE:OFF
public class ChatCompletionRequestMessageFunction
// CHECKSTYLE:ON
{
  @JsonProperty("content")
  private String content;

  /** The role of the messages author, in this case &#x60;function&#x60;. */
  public enum RoleEnum {
    /** The FUNCTION option of this ChatCompletionRequestMessageFunction */
    FUNCTION("function");

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
     * @return The enum value of type ChatCompletionRequestMessageFunction
     */
    @JsonCreator
    @Nonnull
    public static RoleEnum fromValue(@Nonnull final String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("role")
  private RoleEnum role;

  @JsonProperty("name")
  private String name;

  @JsonProperty("refusal")
  private String refusal;

  @JsonProperty("tool_calls")
  private List<ChatCompletionMessageToolCall> toolCalls = new ArrayList<>();

  @JsonProperty("function_call")
  private ChatCompletionRequestAssistantMessageFunctionCall functionCall;

  @JsonProperty("tool_call_id")
  private String toolCallId;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the content of this {@link ChatCompletionRequestMessageFunction} instance and return the
   * same instance.
   *
   * @param content The contents of the message.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction content(@Nullable final String content) {
    this.content = content;
    return this;
  }

  /**
   * The contents of the message.
   *
   * @return content The content of this {@link ChatCompletionRequestMessageFunction} instance.
   */
  @Nullable
  public String getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param content The contents of the message.
   */
  public void setContent(@Nullable final String content) {
    this.content = content;
  }

  /**
   * Set the role of this {@link ChatCompletionRequestMessageFunction} instance and return the same
   * instance.
   *
   * @param role The role of the messages author, in this case &#x60;function&#x60;.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction role(@Nonnull final RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * The role of the messages author, in this case &#x60;function&#x60;.
   *
   * @return role The role of this {@link ChatCompletionRequestMessageFunction} instance.
   */
  @Nonnull
  public RoleEnum getRole() {
    return role;
  }

  /**
   * Set the role of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param role The role of the messages author, in this case &#x60;function&#x60;.
   */
  public void setRole(@Nonnull final RoleEnum role) {
    this.role = role;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestMessageFunction} instance and return the same
   * instance.
   *
   * @param name The contents of the message.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * The contents of the message.
   *
   * @return name The name of this {@link ChatCompletionRequestMessageFunction} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param name The contents of the message.
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the refusal of this {@link ChatCompletionRequestMessageFunction} instance and return the
   * same instance.
   *
   * @param refusal The refusal message by the assistant.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction refusal(@Nullable final String refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * The refusal message by the assistant.
   *
   * @return refusal The refusal of this {@link ChatCompletionRequestMessageFunction} instance.
   */
  @Nullable
  public String getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param refusal The refusal message by the assistant.
   */
  public void setRefusal(@Nullable final String refusal) {
    this.refusal = refusal;
  }

  /**
   * Set the toolCalls of this {@link ChatCompletionRequestMessageFunction} instance and return the
   * same instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction toolCalls(
      @Nullable final List<ChatCompletionMessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
    return this;
  }

  /**
   * Add one toolCalls instance to this {@link ChatCompletionRequestMessageFunction}.
   *
   * @param toolCallsItem The toolCalls that should be added
   * @return The same instance of type {@link ChatCompletionRequestMessageFunction}
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction addToolCallsItem(
      @Nonnull final ChatCompletionMessageToolCall toolCallsItem) {
    if (this.toolCalls == null) {
      this.toolCalls = new ArrayList<>();
    }
    this.toolCalls.add(toolCallsItem);
    return this;
  }

  /**
   * The tool calls generated by the model, such as function calls.
   *
   * @return toolCalls The toolCalls of this {@link ChatCompletionRequestMessageFunction} instance.
   */
  @Nonnull
  public List<ChatCompletionMessageToolCall> getToolCalls() {
    return toolCalls;
  }

  /**
   * Set the toolCalls of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   */
  public void setToolCalls(@Nullable final List<ChatCompletionMessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
  }

  /**
   * Set the functionCall of this {@link ChatCompletionRequestMessageFunction} instance and return
   * the same instance.
   *
   * @param functionCall The functionCall of this {@link ChatCompletionRequestMessageFunction}
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction functionCall(
      @Nullable final ChatCompletionRequestAssistantMessageFunctionCall functionCall) {
    this.functionCall = functionCall;
    return this;
  }

  /**
   * Get functionCall
   *
   * @return functionCall The functionCall of this {@link ChatCompletionRequestMessageFunction}
   *     instance.
   * @deprecated
   */
  @Deprecated
  @Nullable
  public ChatCompletionRequestAssistantMessageFunctionCall getFunctionCall() {
    return functionCall;
  }

  /**
   * Set the functionCall of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param functionCall The functionCall of this {@link ChatCompletionRequestMessageFunction}
   */
  public void setFunctionCall(
      @Nullable final ChatCompletionRequestAssistantMessageFunctionCall functionCall) {
    this.functionCall = functionCall;
  }

  /**
   * Set the toolCallId of this {@link ChatCompletionRequestMessageFunction} instance and return the
   * same instance.
   *
   * @param toolCallId Tool call that this message is responding to.
   * @return The same instance of this {@link ChatCompletionRequestMessageFunction} class
   */
  @Nonnull
  public ChatCompletionRequestMessageFunction toolCallId(@Nonnull final String toolCallId) {
    this.toolCallId = toolCallId;
    return this;
  }

  /**
   * Tool call that this message is responding to.
   *
   * @return toolCallId The toolCallId of this {@link ChatCompletionRequestMessageFunction}
   *     instance.
   */
  @Nonnull
  public String getToolCallId() {
    return toolCallId;
  }

  /**
   * Set the toolCallId of this {@link ChatCompletionRequestMessageFunction} instance.
   *
   * @param toolCallId Tool call that this message is responding to.
   */
  public void setToolCallId(@Nonnull final String toolCallId) {
    this.toolCallId = toolCallId;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ChatCompletionRequestMessageFunction}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * ChatCompletionRequestMessageFunction} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ChatCompletionRequestMessageFunction has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionRequestMessageFunction} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
    cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChatCompletionRequestMessageFunction chatCompletionRequestMessageFunction =
        (ChatCompletionRequestMessageFunction) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionRequestMessageFunction.cloudSdkCustomFields)
        && Objects.equals(this.content, chatCompletionRequestMessageFunction.content)
        && Objects.equals(this.role, chatCompletionRequestMessageFunction.role)
        && Objects.equals(this.name, chatCompletionRequestMessageFunction.name)
        && Objects.equals(this.refusal, chatCompletionRequestMessageFunction.refusal)
        && Objects.equals(this.toolCalls, chatCompletionRequestMessageFunction.toolCalls)
        && Objects.equals(this.functionCall, chatCompletionRequestMessageFunction.functionCall)
        && Objects.equals(this.toolCallId, chatCompletionRequestMessageFunction.toolCallId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        content, role, name, refusal, toolCalls, functionCall, toolCallId, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionRequestMessageFunction {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    refusal: ").append(toIndentedString(refusal)).append("\n");
    sb.append("    toolCalls: ").append(toIndentedString(toolCalls)).append("\n");
    sb.append("    functionCall: ").append(toIndentedString(functionCall)).append("\n");
    sb.append("    toolCallId: ").append(toIndentedString(toolCallId)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
