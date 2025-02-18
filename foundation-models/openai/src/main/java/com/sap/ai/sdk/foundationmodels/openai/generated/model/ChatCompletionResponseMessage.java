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

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A chat completion message generated by the model. */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class ChatCompletionResponseMessage
// CHECKSTYLE:ON
{
  @JsonProperty("role")
  private ChatCompletionResponseMessageRole role;

  @JsonProperty("refusal")
  private String refusal;

  @JsonProperty("content")
  private String content;

  @JsonProperty("tool_calls")
  private List<ChatCompletionMessageToolCall> toolCalls = new ArrayList<>();

  @JsonProperty("function_call")
  private ChatCompletionFunctionCall functionCall;

  // @JsonProperty("context") // TODO: add context
  // private AzureChatExtensionsMessageContext context;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the role of this {@link ChatCompletionResponseMessage} instance and return the same
   * instance.
   *
   * @param role The role of this {@link ChatCompletionResponseMessage}
   * @return The same instance of this {@link ChatCompletionResponseMessage} class
   */
  @Nonnull
  public ChatCompletionResponseMessage role(@Nonnull final ChatCompletionResponseMessageRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role The role of this {@link ChatCompletionResponseMessage} instance.
   */
  @Nonnull
  public ChatCompletionResponseMessageRole getRole() {
    return role;
  }

  /**
   * Set the role of this {@link ChatCompletionResponseMessage} instance.
   *
   * @param role The role of this {@link ChatCompletionResponseMessage}
   */
  public void setRole(@Nonnull final ChatCompletionResponseMessageRole role) {
    this.role = role;
  }

  /**
   * Set the refusal of this {@link ChatCompletionResponseMessage} instance and return the same
   * instance.
   *
   * @param refusal The refusal message generated by the model.
   * @return The same instance of this {@link ChatCompletionResponseMessage} class
   */
  @Nonnull
  public ChatCompletionResponseMessage refusal(@Nullable final String refusal) {
    this.refusal = refusal;
    return this;
  }

  /**
   * The refusal message generated by the model.
   *
   * @return refusal The refusal of this {@link ChatCompletionResponseMessage} instance.
   */
  @Nullable
  public String getRefusal() {
    return refusal;
  }

  /**
   * Set the refusal of this {@link ChatCompletionResponseMessage} instance.
   *
   * @param refusal The refusal message generated by the model.
   */
  public void setRefusal(@Nullable final String refusal) {
    this.refusal = refusal;
  }

  /**
   * Set the content of this {@link ChatCompletionResponseMessage} instance and return the same
   * instance.
   *
   * @param content The contents of the message.
   * @return The same instance of this {@link ChatCompletionResponseMessage} class
   */
  @Nonnull
  public ChatCompletionResponseMessage content(@Nullable final String content) {
    this.content = content;
    return this;
  }

  /**
   * The contents of the message.
   *
   * @return content The content of this {@link ChatCompletionResponseMessage} instance.
   */
  @Nullable
  public String getContent() {
    return content;
  }

  /**
   * Set the content of this {@link ChatCompletionResponseMessage} instance.
   *
   * @param content The contents of the message.
   */
  public void setContent(@Nullable final String content) {
    this.content = content;
  }

  /**
   * Set the toolCalls of this {@link ChatCompletionResponseMessage} instance and return the same
   * instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   * @return The same instance of this {@link ChatCompletionResponseMessage} class
   */
  @Nonnull
  public ChatCompletionResponseMessage toolCalls(
      @Nullable final List<ChatCompletionMessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
    return this;
  }

  /**
   * Add one toolCalls instance to this {@link ChatCompletionResponseMessage}.
   *
   * @param toolCallsItem The toolCalls that should be added
   * @return The same instance of type {@link ChatCompletionResponseMessage}
   */
  @Nonnull
  public ChatCompletionResponseMessage addToolCallsItem(
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
   * @return toolCalls The toolCalls of this {@link ChatCompletionResponseMessage} instance.
   */
  @Nonnull
  public List<ChatCompletionMessageToolCall> getToolCalls() {
    return toolCalls;
  }

  /**
   * Set the toolCalls of this {@link ChatCompletionResponseMessage} instance.
   *
   * @param toolCalls The tool calls generated by the model, such as function calls.
   */
  public void setToolCalls(@Nullable final List<ChatCompletionMessageToolCall> toolCalls) {
    this.toolCalls = toolCalls;
  }

  /**
   * Set the functionCall of this {@link ChatCompletionResponseMessage} instance and return the same
   * instance.
   *
   * @param functionCall The functionCall of this {@link ChatCompletionResponseMessage}
   * @return The same instance of this {@link ChatCompletionResponseMessage} class
   */
  @Nonnull
  public ChatCompletionResponseMessage functionCall(
      @Nullable final ChatCompletionFunctionCall functionCall) {
    this.functionCall = functionCall;
    return this;
  }

  /**
   * Get functionCall
   *
   * @return functionCall The functionCall of this {@link ChatCompletionResponseMessage} instance.
   */
  @Nonnull
  public ChatCompletionFunctionCall getFunctionCall() {
    return functionCall;
  }

  /**
   * Set the functionCall of this {@link ChatCompletionResponseMessage} instance.
   *
   * @param functionCall The functionCall of this {@link ChatCompletionResponseMessage}
   */
  public void setFunctionCall(@Nullable final ChatCompletionFunctionCall functionCall) {
    this.functionCall = functionCall;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ChatCompletionResponseMessage}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ChatCompletionResponseMessage}
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
          "ChatCompletionResponseMessage has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ChatCompletionResponseMessage} instance. If the
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChatCompletionResponseMessage chatCompletionResponseMessage =
        (ChatCompletionResponseMessage) o;
    return Objects.equals(
            this.cloudSdkCustomFields, chatCompletionResponseMessage.cloudSdkCustomFields)
        && Objects.equals(this.role, chatCompletionResponseMessage.role)
        && Objects.equals(this.refusal, chatCompletionResponseMessage.refusal)
        && Objects.equals(this.content, chatCompletionResponseMessage.content)
        && Objects.equals(this.toolCalls, chatCompletionResponseMessage.toolCalls)
        && Objects.equals(this.functionCall, chatCompletionResponseMessage.functionCall);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, refusal, content, toolCalls, functionCall, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ChatCompletionResponseMessage {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    refusal: ").append(toIndentedString(refusal)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    toolCalls: ").append(toIndentedString(toolCalls)).append("\n");
    sb.append("    functionCall: ").append(toIndentedString(functionCall)).append("\n");
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
