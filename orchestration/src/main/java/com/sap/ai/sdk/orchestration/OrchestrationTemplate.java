package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchemaJsonSchema;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * A template to use in {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode(callSuper = true)
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@Beta
public class OrchestrationTemplate extends TemplateConfig {
  @Nullable List<ChatMessage> template;
  @Nullable Map<String, String> defaults;

  @With(AccessLevel.PRIVATE)
  @Nullable
  TemplateResponseFormat responseFormat;

  @Nullable List<ChatCompletionTool> tools;

  /**
   * Create a low-level representation of the template.
   *
   * @return The low-level representation of the template.
   */
  @Override
  @Nonnull
  protected TemplatingModuleConfig toLowLevel() {
    final List<ChatMessage> template = this.template != null ? this.template : List.of();
    final Map<String, String> defaults = this.defaults != null ? this.defaults : new HashMap<>();
    final List<ChatCompletionTool> tools = this.tools != null ? this.tools : new ArrayList<>();
    return Template.create()
        .template(template)
        .defaults(defaults)
        .responseFormat(responseFormat)
        .tools(tools);
  }

  /**
   * Set the response format to the given JSON schema.
   *
   * @param schema The JSON schema to use.
   * @return The updated template.
   */
  @Nonnull
  public OrchestrationTemplate withJsonSchemaResponse(@Nonnull final ResponseJsonSchema schema) {
    val responseFormatJsonSchema =
        ResponseFormatJsonSchema.create()
            .type(ResponseFormatJsonSchema.TypeEnum.JSON_SCHEMA)
            .jsonSchema(
                ResponseFormatJsonSchemaJsonSchema.create()
                    .name(schema.getName())
                    .schema(schema.getSchemaMap())
                    .strict(schema.getStrict())
                    .description(schema.getDescription()));
    return this.withResponseFormat(responseFormatJsonSchema);
  }

  /**
   * Set the response format to JSON object.
   *
   * @return The updated template.
   */
  @Nonnull
  public OrchestrationTemplate withJsonResponse() {
    val responseFormatJsonObject =
        ResponseFormatJsonObject.create().type(ResponseFormatJsonObject.TypeEnum.JSON_OBJECT);
    return this.withResponseFormat(responseFormatJsonObject);
  }

  /**
   * Create a {@link Template} object from a JSON provided as String.
   *
   * @param inputString the provided JSON
   * @return A Template object representing the provided JSON
   */
  @Nullable
  public Template fromJSON(@Nonnull final String inputString) {
    Template template1 = null;
    final ObjectMapper objectMapper =
        OrchestrationJacksonConfiguration.getOrchestrationObjectMapper();
    try {
      final JsonNode rootNode = objectMapper.readTree(inputString);
      template1 = objectMapper.treeToValue(rootNode.get("spec"), Template.class);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex.getMessage());
    }

    return template1;
  }

  /**
   * Create a {@link Template} object from a YAML provided as String.
   *
   * @param inputYaml the provided YAML
   * @return A Template object representing the provided YAML
   */
  @Nullable
  public Template fromYAML(@Nonnull final String inputYaml) {
    try {
      final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
      final Object obj = yamlReader.readValue(inputYaml, Object.class);

      final ObjectMapper jsonWriter = new ObjectMapper();
      return fromJSON(jsonWriter.writeValueAsString(obj));
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  //  public static class CustomSerializer extends JsonDeserializer<Template> {
  //    @Override
  //    public Template deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
  //      JsonNode node = p.getCodec().readTree(p);
  //
  //      if (node.has("content")) {
  //        if (node.get("content").isArray()) {
  //          return p.getCodec().treeToValue(node, MultiChatMessage.class);
  //        } else {
  //          return p.getCodec().treeToValue(node, SingleChatMessage.class);
  //        }
  //      }
  //
  //      // Default handling
  //      return p.getCodec().treeToValue(node, ChatMessage.class);
  //    }
  //  }
}
