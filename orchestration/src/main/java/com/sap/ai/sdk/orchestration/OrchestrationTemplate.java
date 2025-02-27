package com.sap.ai.sdk.orchestration;

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
 * @since 1.5.0
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
}
