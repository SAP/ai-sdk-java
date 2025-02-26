package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchemaJsonSchema;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value
@With
@AllArgsConstructor(
    access = AccessLevel.PRIVATE) // TODO: why do we need PRIVATE here instead of NONE?
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class OrchestrationTemplate extends TemplateConfig {
  List<ChatMessage> template;
  Map<String, String> defaults = new HashMap<>();

  @With(AccessLevel.PACKAGE)
  TemplateResponseFormat responseFormat;

  List<ChatCompletionTool> tools = new ArrayList<>();
  Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  @Override
  protected TemplatingModuleConfig toLowLevel() {
    List<ChatMessage> template = this.template != null ? this.template : List.of();
    return Template.create()
        .template(template)
        .defaults(defaults)
        .responseFormat(responseFormat)
        .tools(tools);
  }

  public OrchestrationTemplate withJsonSchemaResponse(ResponseJsonSchema schema) {
    val responseFormatJsonSchema =
        ResponseFormatJsonSchema.create()
            .type(ResponseFormatJsonSchema.TypeEnum.JSON_SCHEMA)
            .jsonSchema(
                ResponseFormatJsonSchemaJsonSchema.create()
                    .name(schema.getName())
                    .schema(schema.getSchemaMap())
                    .strict(schema.getIsStrict())
                    .description(schema.getDescription()));
    return this.withResponseFormat(responseFormatJsonSchema);
  }

  public OrchestrationTemplate withJsonResponse() {
    val responseFormatJsonObject =
        ResponseFormatJsonObject.create().type(ResponseFormatJsonObject.TypeEnum.JSON_OBJECT);
    return this.withResponseFormat(responseFormatJsonObject);
  }
}
