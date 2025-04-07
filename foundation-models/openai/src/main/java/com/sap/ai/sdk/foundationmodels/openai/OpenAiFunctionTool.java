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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

/**
 * Represents an OpenAI function tool that can be used to define a function call in an OpenAI Chat
 * Completion request. This tool generates a JSON schema based on the provided class representing
 * the function's request structure.
 *
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.7.0
 */
@Beta
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiFunctionTool implements OpenAiTool {

  /** The name of the function. */
  @Nonnull String name;

  /** The model class for function request. */
  @Nonnull Class<?> clazz;

  /** An optional description of the function. */
  @Nullable String description;

  /** An optional flag indicating whether the function parameters should be treated strictly. */
  @Nullable Boolean strict;

  /**
   * Constructs an {@code OpenAiFunctionTool} with the specified name and request class.
   *
   * @param name the name of the function
   * @param clazz the model class for the function request
   */
  public <T> OpenAiFunctionTool(@Nonnull final String name, @Nonnull final Class<T> clazz) {
    this(name, clazz, null, null);
  }

  ChatCompletionTool createChatCompletionTool() {
    final var objectMapper = new ObjectMapper();
    JsonSchema schema = null;
    try {
      schema = new JsonSchemaGenerator(objectMapper).generateSchema(clazz);
    } catch (JsonMappingException e) {
      throw new IllegalArgumentException("Could not generate schema for " + clazz.getTypeName(), e);
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
