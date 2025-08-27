package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum.FUNCTION;
import static java.util.function.UnaryOperator.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
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
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents an OpenAI tool that can be used to define a function call in an OpenAI Chat Completion
 * request. This tool generates a JSON schema based on the provided class representing the
 * function's request structure.
 *
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.8.0
 */
@Slf4j
@Value
@With
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiTool {

  private static final ObjectMapper JACKSON = new ObjectMapper();

  /** The schema generator used to create JSON schemas. */
  @Nonnull private static final SchemaGenerator GENERATOR = createSchemaGenerator();

  /** The name of the function. */
  @Setter(AccessLevel.NONE)
  @Nonnull
  String name;

  /** The function to execute a string argument to tool result object. */
  @Setter(AccessLevel.NONE)
  @Nonnull
  Function<String, Object> functionExecutor;

  /** schema to be used for the function call. */
  @Setter(AccessLevel.NONE)
  @Nonnull
  ObjectNode schema;

  /** An optional description of the function. */
  @Nullable String description;

  /** An optional flag indicating whether the function parameters should be treated strictly. */
  @Nullable Boolean strict;

  /**
   * Instantiates a OpenAiTool builder instance on behalf of an executable function.
   *
   * @param function the function to be executed.
   * @return an OpenAiTool builder instance.
   * @param <InputT> the type of the function input-argument class.
   */
  @Nonnull
  public static <InputT> Builder1<InputT> forFunction(@Nonnull final Function<InputT, ?> function) {
    return inputClass ->
        name -> {
          final Function<String, Object> exec =
              s -> function.apply(deserializeArgument(inputClass, s));
          final var schema = GENERATOR.generateSchema(inputClass);
          return new OpenAiTool(name, exec, schema, null, null);
        };
  }

  /**
   * Creates a new OpenAiTool instance with the specified function and input class.
   *
   * @param <InputT> the type of the input class.
   */
  public interface Builder1<InputT> {
    /**
     * Sets the name of the function.
     *
     * @param inputClass the class of the input object.
     * @return a new OpenAiTool instance with the specified function and input class.
     */
    @Nonnull
    Builder2 withArgument(@Nonnull final Class<InputT> inputClass);
  }

  /** Creates a new OpenAiTool instance with the specified name. */
  public interface Builder2 {
    /**
     * Sets the name of the function.
     *
     * @param name the name of the function
     * @return a new OpenAiTool instance with the specified name
     */
    @Nonnull
    OpenAiTool withName(@Nonnull final String name);
  }

  @Nullable
  static <T> T deserializeArgument(@Nonnull final Class<T> cl, @Nonnull final String s) {
    try {
      return JACKSON.readValue(s, cl);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to parse JSON string to class " + cl, e);
    }
  }

  ChatCompletionTool createChatCompletionTool() {
    final var schemaMap =
        OpenAiUtils.getOpenAiObjectMapper()
            .convertValue(getSchema(), new TypeReference<Map<String, Object>>() {});

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
   * Executes each tool call found in the specified assistant message using the provided tools.
   * Returns a list of {@link OpenAiToolMessage} objects, each containing the execution result
   * encoded as a JSON string.
   *
   * @param tools the list of tools to execute
   * @param msg the assistant message containing a list of tool calls with arguments
   * @return The list of tool messages with the results.
   */
  @Nonnull
  static List<OpenAiToolMessage> execute(
      @Nonnull final List<OpenAiTool> tools, @Nonnull final OpenAiAssistantMessage msg) {
    final var toolResults = executeInternal(tools, msg);
    final var result = new ArrayList<OpenAiToolMessage>();
    for (final var entry : toolResults.entrySet()) {
      final var functionCall = entry.getKey().getId();
      final var serializedValue = serializeObject(entry.getValue());
      result.add(OpenAiMessage.tool(serializedValue, functionCall));
    }
    return result;
  }

  /**
   * Executes each tool call found in the specified assistant message using the provided tools.
   * Returns a map that links each executed tool call to its corresponding result.
   *
   * @param tools the list of tools to execute
   * @param msg the assistant message containing a list of tool calls with arguments
   * @return a map that contains the function calls and their respective tool results.
   */
  @Nonnull
  private static Map<OpenAiFunctionCall, Object> executeInternal(
      @Nonnull final List<OpenAiTool> tools, @Nonnull final OpenAiAssistantMessage msg) {
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
    return result;
  }

  @Nonnull
  private static Object executeFunction(
      @Nonnull final OpenAiTool tool, @Nonnull final OpenAiFunctionCall toolCall) {
    final Function<String, Object> executor = tool.getFunctionExecutor();
    final String arguments = toolCall.getArguments();
    return executor.apply(arguments);
  }

  @Nonnull
  private static String serializeObject(@Nonnull final Object obj) throws IllegalArgumentException {
    try {
      return JACKSON.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize object to JSON", e);
    }
  }
}
