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
import com.google.common.annotations.Beta;
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
@Beta
public class ResponseJsonSchema {
  @Nonnull Map<String, Object> schemaMap;
  @Nonnull String name;
  @Nullable String description;

  /**
   * ⚠️ Fields of the schema class should be annotated with {@code @JsonProperty(required = true)}
   * to not fail requests if set to true.
   */
  @Nullable Boolean strict;

  /**
   * Create a new instance of {@link ResponseJsonSchema} with the given schema map and name.
   *
   * @param schemaMap The schema map
   * @param name The name of the schema
   * @return The new instance of {@link ResponseJsonSchema}
   */
  @Nonnull
  public static ResponseJsonSchema fromMap(
      @Nonnull final Map<String, Object> schemaMap, @Nonnull final String name) {
    return new ResponseJsonSchema(schemaMap, name, null, null);
  }

  /**
   * Create a new instance of {@link ResponseJsonSchema} from a given class.
   *
   * <p>⚠️ Fields of the schema class should be annotated with {@code @JsonProperty(required =
   * true)}.
   *
   * @param classType The class to generate the schema from
   * @return The new instance of {@link ResponseJsonSchema}
   */
  @Nonnull
  public static ResponseJsonSchema fromType(@Nonnull final Type classType) {
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
    val schemaMap = mapper.convertValue(jsonSchema, new TypeReference<Map<String, Object>>() {});
    val schemaName = ((Class<?>) classType).getSimpleName() + "-Schema";
    return new ResponseJsonSchema(schemaMap, schemaName, null, null);
  }
}
