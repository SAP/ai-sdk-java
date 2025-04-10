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
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents an OpenAI function tool that can be used to define a function call in an OpenAI Chat
 * Completion request. This tool generates a JSON schema based on the provided class representing
 * the function's request structure.
 *
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.7.0
 */
@Beta
@Data
@Getter(AccessLevel.PACKAGE)
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiTool<T, R> {

  /** The name of the function. */
  @Nonnull String name;

  /** The model class for function request. */
  @Nonnull Class<T> requestClass;

  /** An optional description of the function. */
  @Nullable String description;

  /** An optional flag indicating whether the function parameters should be treated strictly. */
  @Nullable Boolean strict;

  /** The function to be called. */
  @Setter(AccessLevel.NONE)
  @Nullable
  Function<T, R> function;

  /** The response class for the function. */
  @Setter(AccessLevel.NONE)
  @Nullable
  Class<R> responseClass;

  public static <I, O> OpenAiTool<I, O> of(@Nonnull String name, @Nonnull Class<I> requestClass) {
    return new OpenAiTool<>(name, requestClass);
  }

  /**
   * Constructs an {@code OpenAiFunctionTool} with the specified name and a model class that
   * captures the request to the function.
   *
   * @param name the name of the function
   * @param requestClass the model class for function request
   */
  private OpenAiTool(@Nonnull final String name, @Nonnull final Class<T> requestClass) {
    this(name, requestClass, null, null, null, null);
  }

  /**
   * Sets the function to be called and the response class for the function.
   *
   * @param function the function to be called
   * @param responseClass the response class for the function
   * @return this instance of {@code OpenAiFunctionTool}
   */
  @Nonnull
  public OpenAiTool<T, R> setCallback(
      @Nonnull final Function<T, R> function, @Nonnull final Class<R> responseClass) {
    this.function = function;
    this.responseClass = responseClass;
    return this;
  }

  @Nonnull
  R execute(@Nonnull final T argument) {
    return function.apply(argument);
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
