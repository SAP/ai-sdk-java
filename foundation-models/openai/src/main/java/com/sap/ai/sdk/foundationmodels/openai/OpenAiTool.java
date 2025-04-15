package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum.FUNCTION;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Represents an OpenAI function tool that can be used to define a function call in an OpenAI Chat
 * Completion request. This tool generates a JSON schema based on the provided class representing
 * the function's request structure.
 *
 * @param <I> the type of the input argument for the function
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.7.0
 */
@Beta
@Data
@Getter(AccessLevel.PACKAGE)
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiTool<I> {

  /** The name of the function. */
  private @Nonnull String name;

  /** The model class for function request. */
  private @Nonnull Class<I> requestClass;

  /** An optional description of the function. */
  private @Nullable String description;

  /** An optional flag indicating whether the function parameters should be treated strictly. */
  private @Nullable Boolean strict;

  /** The function to be called. */
  private @Nullable Function<I, ?> function;

  /**
   * Constructs an {@code OpenAiFunctionTool} with the specified name and a model class that
   * captures the request to the function.
   *
   * @param name the name of the function
   * @param requestClass the model class for function request
   */
  public OpenAiTool(@Nonnull final String name, @Nonnull final Class<I> requestClass) {
    this(name, requestClass, null, null, null);
  }

  @Nonnull
  Object execute(@Nonnull final I argument) {
    if (getFunction() == null) {
      throw new IllegalStateException("Function must not be set to execute the tool.");
    }
    return getFunction().apply(argument);
  }

  ChatCompletionTool createChatCompletionTool() {
    final var objectMapper = new ObjectMapper();
    JsonSchema schema = null;
    try {
      schema = new JsonSchemaGenerator(objectMapper).generateSchema(getRequestClass());
    } catch (JsonMappingException e) {
      throw new IllegalArgumentException("Could not generate schema for " + getRequestClass(), e);
    }

    final var schemaMap =
        objectMapper.convertValue(schema, new TypeReference<Map<String, Object>>() {});

    final var function =
        new FunctionObject()
            .name(getName())
            .description(getDescription())
            .parameters(schemaMap)
            .strict(getStrict());
    return new ChatCompletionTool().type(FUNCTION).function(function);
  }
}
