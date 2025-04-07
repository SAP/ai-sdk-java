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

@Beta
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAiFunctionTool implements OpenAiTool {

  @Nonnull String name;
  @Nonnull Class<?> clazz;

  @Nullable String description;
  @Nullable Boolean strict;

  public <T> OpenAiFunctionTool(@Nonnull final String name, @Nonnull final Class<T> clazz) {
    this(name, clazz, null, null);
  }

  ChatCompletionTool createChatCompletionTool() {
    var objectMapper = new ObjectMapper();
    JsonSchema schema = null;
    try {
      schema = new JsonSchemaGenerator(objectMapper).generateSchema(clazz);
    } catch (JsonMappingException e) {
      throw new IllegalArgumentException("Could not generate schema for " + clazz.getTypeName(), e);
    }

    var schemaMap = objectMapper.convertValue(schema, new TypeReference<Map<String, Object>>() {});

    final var function =
        new FunctionObject()
            .name(getName())
            .description(getDescription())
            .parameters(schemaMap)
            .strict(getStrict());
    return new ChatCompletionTool().type(FUNCTION).function(function);
  }
}
