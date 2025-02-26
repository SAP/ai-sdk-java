package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import java.lang.reflect.Type;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * The schema object to use for the response format parameter in {@link OrchestrationTemplate}.
 *
 * @since 1.4.0
 */
@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@With
public class ResponseJsonSchema {
  @Nonnull Map<String, Object> schemaMap;
  @Nonnull String name;
  @Nullable String description;

  @With(AccessLevel.NONE)
  @Nullable
  Boolean isStrict;

  /**
   * Create a new instance of {@link ResponseJsonSchema} with the given strictness.
   *
   * @return A new ResponseJsonSchema instance with the given strictness
   * @since 1.4.0
   */
  @Nonnull
  public ResponseJsonSchema withStrict(@Nullable final Boolean isStrict) {
    return new ResponseJsonSchema(schemaMap, name, description, isStrict);
  }

  /**
   * Create a new instance of {@link ResponseJsonSchema} with the given schema map and name.
   *
   * @param schemaMap The schema map
   * @param name The name of the schema
   * @return The new instance of {@link ResponseJsonSchema}
   * @since 1.4.0
   */
  @Nonnull
  public static ResponseJsonSchema of(
      @Nonnull final Map<String, Object> schemaMap, @Nonnull final String name) {
    return new ResponseJsonSchema(schemaMap, name, null, null);
  }

  /**
   * Create a new instance of {@link ResponseJsonSchema} from a given class.
   *
   * @param classType The class to generate the schema from
   * @return The new instance of {@link ResponseJsonSchema}
   * @since 1.4.0
   */
  @Nonnull
  public static ResponseJsonSchema from(@Nonnull final Type classType) {
    val module =
        new JacksonModule(
            JacksonOption.RESPECT_JSONPROPERTY_REQUIRED, JacksonOption.RESPECT_JSONPROPERTY_ORDER);
    val generator =
        new SchemaGenerator(
            new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .without(Option.SCHEMA_VERSION_INDICATOR)
                .with(Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT)
                .with(module)
                .build());
    val jsonSchema = generator.generateSchema(classType);
    val mapper = new ObjectMapper();
    final Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<>() {});
    val schemaName = ((Class<?>) classType).getSimpleName() + "-Schema";
    return new ResponseJsonSchema(schemaMap, schemaName, null, null);
  }

  /**
   * Create a new instance of a {@link Template} that is a copy of the given one but with the given
   * response format.
   *
   * @param originalTemplate The template to copy
   * @param responseFormat The response format to set in the copy
   * @return The new instance of {@link Template}
   */
  @Nonnull
  static Template newTemplateWithResponseFormat(
      @Nonnull final Template originalTemplate,
      @Nullable final TemplateResponseFormat responseFormat) {
    val newTemplate = Template.create().template(originalTemplate.getTemplate());
    newTemplate.setDefaults(originalTemplate.getDefaults());
    newTemplate.setTools(originalTemplate.getTools());
    for (val customFieldName : originalTemplate.getCustomFieldNames()) {
      newTemplate.setCustomField(customFieldName, originalTemplate.getCustomField(customFieldName));
    }
    newTemplate.setResponseFormat(responseFormat);
    return newTemplate;
  }
}
