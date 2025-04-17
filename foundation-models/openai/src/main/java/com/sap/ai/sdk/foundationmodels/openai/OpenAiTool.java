package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum.FUNCTION;

import com.fasterxml.jackson.core.type.TypeReference;
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
 * Represents an OpenAI tool that can be used to define a function call in an OpenAI Chat Completion
 * request. This tool generates a JSON schema based on the provided class representing the
 * function's request structure.
 *
 * @param <InputT> the type of the input argument for the function
 * @see <a href="https://platform.openai.com/docs/guides/gpt/function-calling"/>OpenAI Function
 * @since 1.7.0
 */
@Beta
@Data
@Getter(AccessLevel.PACKAGE)
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiTool<InputT> {

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
      throw new IllegalStateException("No function configured to execute.");
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
}
