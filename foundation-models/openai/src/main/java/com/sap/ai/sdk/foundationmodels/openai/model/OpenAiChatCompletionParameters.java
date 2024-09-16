package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI chat completion input parameters. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpenAiChatCompletionParameters extends OpenAiCompletionParameters {
  /** A list of messages comprising the conversation so far. */
  @JsonProperty("messages")
  private List<OpenAiChatMessage> messages;

  /**
   * An object specifying the format that the model must output. Setting to { "type": "json_object"
   * } enables JSON mode, which guarantees the message the model generates is valid JSON.
   *
   * <p><b>Important</b>: when using JSON mode, you <b>must</b> also instruct the model to produce
   * JSON yourself via a system or user message. Without this, the model may generate an unending
   * stream of whitespace until the generation reaches the token limit, resulting in a long-running
   * and seemingly "stuck" request. Also note that the message content may be partially cut off if
   * {@code finish_reason="length"}, which indicates the generation exceeded {@code max_tokens} or
   * the conversation exceeded the max context length.
   */
  @JsonProperty("response_format")
  @Setter(onParam_ = @Nullable)
  private ResponseFormat responseFormat;

  /**
   * If specified, our system will make a best effort to sample deterministically, such that
   * repeated requests with the same {@code seed} and parameters should return the same result.
   * Determinism is not guaranteed, and you should refer to the {@code system_fingerprint} response
   * parameter to monitor changes in the backend.
   */
  @JsonProperty("seed")
  @Setter(onParam_ = @Nullable)
  private Integer seed;

  /**
   * Array of function signatures to be called.
   *
   * @deprecated Use {@code tools} instead.
   */
  @JsonProperty("functions")
  @Deprecated
  @Setter(onParam_ = @Nullable)
  private List<OpenAiChatCompletionFunction> functions;

  /**
   * A list of tools the model may call. Currently, only functions are supported as a tool. Use this
   * to provide a list of functions the model may generate JSON inputs for.
   */
  @JsonProperty("tools")
  @Setter(onParam_ = @Nullable)
  private List<OpenAiChatCompletionTool> tools;

  /**
   * Controls which (if any) function is called by the model.
   *
   * <p>- {@code none} means the model will not call a function and instead generates a message. -
   * {@code auto} means the model can pick between generating a message or calling a function. -
   * Specifying a particular function via {@code {"type": "function", "function": {"name":
   * "my_function"}}} forces the model to call that function.
   */
  @JsonProperty("tool_choice")
  @Setter(AccessLevel.NONE)
  @Nullable
  private ToolChoice toolChoice;

  /**
   * <b>NOTE:</b> This method is currently not supported. Therefore, it stays protected.<br>
   * <br>
   * The configuration entries for Azure OpenAI chat extensions that use them. This additional
   * specification is only compatible with Azure OpenAI.
   */
  @JsonProperty("data_sources")
  @Setter(onParam_ = @Nullable, value = AccessLevel.PROTECTED)
  private List<Object> dataSources; // TODO for implementation details, please find

  // https://github.com/Azure/azure-rest-api-specs/blob/3cb1b51638616435470fc10ea00de92512186ece/specification/cognitiveservices/data-plane/AzureOpenAI/inference/stable/2024-02-01/inference.json#L1223

  /** "response_format": { "type": "json_object" } */
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @RequiredArgsConstructor
  public enum ResponseFormat {
    /**
     * Setting to `json_object` enables JSON mode. This guarantees that the message the model
     * generates is valid JSON.
     */
    JSON_OBJECT("json_object"),

    /** Response format is a plain text string. */
    TEXT("text");

    @JsonProperty("type")
    private final String value;

    @JsonCreator
    static ResponseFormat fromValue(@JsonProperty("type") final String type) {
      final var l = Arrays.stream(values()).filter(f -> f.value.equalsIgnoreCase(type));
      return l.findFirst().orElseThrow(() -> new IllegalArgumentException("Unexpected: " + type));
    }
  }

  /**
   * Controls which (if any) function is called by the model. `none` means the model will not call a
   * function and instead generates a message.
   *
   * @return ${code this} instance for chaining.
   */
  @Nonnull
  public OpenAiChatCompletionParameters setToolChoiceNone() {
    toolChoice = ToolChoiceType.NONE;
    return this;
  }

  /**
   * Controls which (if any) function is called by the model. `none` means the model will not call a
   * function and instead generates a message.
   *
   * @return ${code this} instance for chaining.
   */
  @Nonnull
  public OpenAiChatCompletionParameters setToolChoiceAuto() {
    toolChoice = ToolChoiceType.AUTO;
    return this;
  }

  /**
   * Controls which (if any) function is called by the model. Specifying a particular function
   * forces the model to call that function.
   *
   * @param functionName The name of the function to call.
   * @return ${code this} instance for chaining.
   */
  @Nonnull
  public OpenAiChatCompletionParameters setToolChoiceFunction(@Nonnull final String functionName) {
    toolChoice =
        new FunctionToolChoice().setFunction(new FunctionToolChoice.Function(functionName));
    return this;
  }

  /** Tool choice options. */
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.DEDUCTION,
      property = "type",
      defaultImpl = ToolChoiceType.class) // This is the field that determines the class type
  @JsonSubTypes({@JsonSubTypes.Type(value = FunctionToolChoice.class, name = "function")})
  interface ToolChoice {}

  @RequiredArgsConstructor
  private enum ToolChoiceType implements ToolChoice {
    /** `none` means the model will not call a function and instead generates a message. */
    NONE("none"),

    /** `auto` means the model can pick between generating a message or calling a function. */
    AUTO("auto");

    @JsonValue @Nonnull private final String type;
  }

  @EqualsAndHashCode
  @ToString
  private static class FunctionToolChoice implements ToolChoice {
    @JsonProperty("function")
    @Setter(onParam_ = @Nonnull)
    private Function function;

    @JsonProperty("type")
    @Getter(onMethod_ = @Nullable)
    private final String type = "function";

    /** Use to force the model to call a specific function. */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private record Function(@JsonProperty("name") @Nonnull String name) {}
  }

  @Override
  @Nonnull
  public OpenAiChatCompletionParameters setStop(@Nullable final String... values) {
    return (OpenAiChatCompletionParameters) super.setStop(values);
  }

  @Nonnull
  public OpenAiChatCompletionParameters addMessages(@Nonnull final OpenAiChatMessage... messages) {
    if (this.messages == null) {
      this.messages = new ArrayList<>();
    }
    this.messages.addAll(Arrays.asList(messages));
    return this;
  }
}
