package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum.FUNCTION;
import static java.util.function.UnaryOperator.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents an OpenAI tool that can be used to define a function call in an OpenAI Chat Completion
 * request. This tool generates a JSON schema based on the provided class representing the
 * function's request structure.
 *
 * @param <InputT> the type of the input argument for the function
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.7.0
 */
@Slf4j
@Beta
@Data
@Getter(AccessLevel.PACKAGE)
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiTool<InputT> {

  private static final ObjectMapper JACKSON = new ObjectMapper();

  /** The schema generator used to create JSON schemas. */
  @Nonnull private static final SchemaGenerator GENERATOR = createSchemaGenerator();

  /** The name of the function. */
  @Nonnull private String name;

  /** The model class for function request. */
  @Nonnull private Class<InputT> requestClass;

  /** An optional description of the function. */
  @Nullable private String description;

  /** An optional flag indicating whether the function parameters should be treated strictly. */
  @Nullable private Boolean strict;

  /** The function to be called. */
  @Nullable private Function<InputT, ?> function;

  /**
   * Constructs an {@code OpenAiFunctionTool} with the specified name and a model class that
   * captures the request to the function.
   *
   * @param name the name of the function
   * @param requestClass the model class for function request
   */
  public OpenAiTool(@Nonnull final String name, @Nonnull final Class<InputT> requestClass) {
    this(name, requestClass, null, null, null);
  }

  @Nonnull
  Object execute(@Nonnull final InputT argument) {
    if (getFunction() == null) {
      throw new IllegalStateException(
          "Tool " + name + " is missing a method reference to execute.");
    }
    return getFunction().apply(argument);
  }

  ChatCompletionTool createChatCompletionTool() {
    final var schema = GENERATOR.generateSchema(getRequestClass());
    final var schemaMap =
        OpenAiUtils.getOpenAiObjectMapper()
            .convertValue(schema, new TypeReference<Map<String, Object>>() {});

    return new ChatCompletionTool()
        .type(FUNCTION)
        .function(
            new FunctionObject()
                .name(getName())
                .description(getDescription())
                .parameters(schemaMap)
                .strict(getStrict()));
  }

  private static SchemaGenerator createSchemaGenerator() {
    final var module =
        new JacksonModule(
            JacksonOption.RESPECT_JSONPROPERTY_REQUIRED, JacksonOption.RESPECT_JSONPROPERTY_ORDER);
    return new SchemaGenerator(
        new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
            .without(Option.SCHEMA_VERSION_INDICATOR)
            .with(module)
            .build());
  }

  /**
   * Executes the given tool calls with the provided tools and returns the results as a list of
   * {@link OpenAiToolMessage} containing execution results encoded as JSON string.
   *
   * @param tools the list of tools to execute
   * @param msg the assistant message containing a list of tool calls with arguments
   * @return a result object that contains the list of tool messages with the results
   * @throws IllegalStateException if a tool is missing a method reference for function execution.
   */
  @Beta
  @Nonnull
  public static Execution execute(
      @Nonnull final List<OpenAiTool<?>> tools, @Nonnull final OpenAiAssistantMessage msg)
      throws IllegalArgumentException {
    final var result = new LinkedHashMap<OpenAiFunctionCall, Object>();

    final var toolMap = tools.stream().collect(Collectors.toMap(OpenAiTool::getName, identity()));
    for (final OpenAiToolCall toolCall : msg.toolCalls()) {
      if (toolCall instanceof OpenAiFunctionCall functionCall) {
        final var tool = toolMap.get(functionCall.getName());
        if (tool == null) {
          log.warn("Tool not found for function call: {}", functionCall.getName());
          continue;
        }
        final var toolResult = executeFunction(tool, functionCall);
        result.put(functionCall, toolResult);
      }
    }
    return new Execution(result);
  }

  @Nonnull
  private static <I> Object executeFunction(
      @Nonnull final OpenAiTool<I> tool, @Nonnull final OpenAiFunctionCall toolCall) {
    final I arguments = toolCall.getArgumentsAsObject(tool.getRequestClass());
    return tool.execute(arguments);
  }

  @Nonnull
  private static String serializeObject(@Nonnull final Object obj) throws IllegalArgumentException {
    try {
      return JACKSON.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize object to JSON", e);
    }
  }

  /**
   * Represents the result of executing a tool call, containing the results of the function calls.
   */
  @RequiredArgsConstructor
  @Beta
  public static class Execution {
    @Getter @Beta @Nonnull private final Map<OpenAiFunctionCall, Object> results;

    /**
     * Creates a new list of serialized OpenAI tool messages.
     *
     * @return the list of serialized OpenAI tool messages.
     * @throws IllegalArgumentException if the tool results cannot be serialized to JSON
     */
    @Beta
    @Nonnull
    public List<OpenAiToolMessage> getMessages() {
      final var result = new ArrayList<OpenAiToolMessage>();
      for (final var entry : getResults().entrySet()) {
        final var functionCall = entry.getKey().getId();
        final var serializedValue = serializeObject(entry.getValue());
        result.add(OpenAiMessage.tool(serializedValue, functionCall));
      }
      return result;
    }
  }
}
