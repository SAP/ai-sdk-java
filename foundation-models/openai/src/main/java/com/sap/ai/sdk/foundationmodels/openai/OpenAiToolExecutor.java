package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;

/**
 * A class for OpenAI tool execution.
 *
 * @since 1.7.0
 */
@Beta
@AllArgsConstructor(access = PRIVATE)
public class OpenAiToolExecutor {

  private static final ObjectMapper JACKSON = new ObjectMapper();

  /**
   * Executes the given tool calls with the provided tools and returns the results as a list of
   * {@link OpenAiToolMessage}.
   *
   * @param tools the list of tools to execute
   * @param toolCalls the list of tool calls with arguments
   * @return the list of tool messages with the results
   */
  @Nonnull
  public static List<OpenAiToolMessage> executeTools(
      @Nonnull final List<OpenAiTool<?>> tools, @Nonnull final List<OpenAiToolCall> toolCalls) {

    final var toolMap = tools.stream().collect(Collectors.toMap(OpenAiTool::getName, tool -> tool));

    return toolCalls.stream()
        .filter(OpenAiFunctionCall.class::isInstance)
        .map(OpenAiFunctionCall.class::cast)
        .filter(functionCall -> toolMap.containsKey(functionCall.getName()))
        .map(
            functionCall -> {
              final var tool = toolMap.get(functionCall.getName());
              final var result = executeFunction(tool, functionCall);
              return OpenAiMessage.tool(serializeObject(result), functionCall.getId());
            })
        .toList();
  }

  @Nonnull
  private static <I> Object executeFunction(
      @Nonnull final OpenAiTool<I> tool, @Nonnull final OpenAiFunctionCall toolCall) {
    final I arguments = toolCall.getArgumentsAsObject(tool);
    return tool.execute(arguments);
  }

  @Nonnull
  private static String serializeObject(@Nonnull final Object obj) {
    try {
      return JACKSON.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
