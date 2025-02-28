package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.FunctionObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchemaJsonSchema;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.Test;

public class OrchestrationConvenienceUnitTest {

  static class TestClassForSchemaGeneration {
    @JsonProperty(required = true)
    private String stringField;

    @JsonProperty(required = true)
    private int intField;

    @JsonProperty(required = true)
    private InsideTestClass complexField;

    static class InsideTestClass {
      @JsonProperty(required = true)
      private String anotherStringField;
    }
  }

  private Map<String, Object> generateSchemaMap() {
    var schemaMap = new LinkedHashMap<String, Object>();
    var propertiesMap = new LinkedHashMap<String, Object>();
    var complexFieldMap = new LinkedHashMap<String, Object>();
    complexFieldMap.put("type", "object");
    complexFieldMap.put("properties", Map.of("anotherStringField", Map.of("type", "string")));
    complexFieldMap.put("required", List.of("anotherStringField"));
    complexFieldMap.put("additionalProperties", false);
    propertiesMap.put("complexField", complexFieldMap);
    propertiesMap.put("intField", Map.of("type", "integer"));
    propertiesMap.put("stringField", Map.of("type", "string"));
    schemaMap.put("type", "object");
    schemaMap.put("properties", propertiesMap);
    schemaMap.put("required", List.of("complexField", "intField", "stringField"));
    schemaMap.put("additionalProperties", false);
    return schemaMap;
  }

  @Test
  void testMessageConstructionText() {
    var userMessageViaStaticFactory = Message.user("Text 1");
    var userMessageViaConstructor = new UserMessage(("Text 1"));
    assertThat(userMessageViaStaticFactory).isEqualTo(userMessageViaConstructor);

    var systemMessageViaStaticFactory = Message.system("Text 1");
    var systemMessageViaConstructor = new SystemMessage("Text 1");
    assertThat(systemMessageViaStaticFactory).isEqualTo(systemMessageViaConstructor);
  }

  @Test
  void testMessageConstructionImage() {
    var userMessageOnlyImageConvenience = Message.user(new ImageItem("url"));
    var userMessageOnlyImageBase =
        new UserMessage(new MessageContent(List.of(new ImageItem("url"))));
    assertThat(userMessageOnlyImageBase).isEqualTo(userMessageOnlyImageConvenience);

    var userMessageWithImage = Message.user("Text 1").withImage("url");
    var userMessageWithImageAndDetail =
        Message.user("Text 1").withImage("url", ImageItem.DetailLevel.AUTO);
    assertThat(userMessageWithImage).isEqualTo(userMessageWithImageAndDetail);
  }

  @Test
  void testResponseFormatSchemaConstruction() {
    val schemaFromClass = ResponseJsonSchema.fromType(TestClassForSchemaGeneration.class);

    val schemaMap = generateSchemaMap();
    val schemaFromMap =
        ResponseJsonSchema.fromMap(schemaMap, "TestClassForSchemaGeneration-Schema");

    assertThat(schemaFromClass).isEqualTo(schemaFromMap);
  }

  @Test
  void testConfigWithResponseSchema() {
    val schemaFromClass = ResponseJsonSchema.fromType(TestClassForSchemaGeneration.class);
    val schemaMap = generateSchemaMap();

    var configWithResponseSchemaFromClass =
        new OrchestrationModuleConfig()
            .withTemplateConfig(
                TemplateConfig.create()
                    .withJsonSchemaResponse(
                        schemaFromClass.withDescription("Description").withStrict(true)));
    var configWithResponseSchemaLowLevel =
        new OrchestrationModuleConfig()
            .withTemplateConfig(
                Template.create()
                    .template(List.of())
                    .responseFormat(
                        ResponseFormatJsonSchema.create()
                            .type(ResponseFormatJsonSchema.TypeEnum.JSON_SCHEMA)
                            .jsonSchema(
                                ResponseFormatJsonSchemaJsonSchema.create()
                                    .name("TestClassForSchemaGeneration-Schema")
                                    .schema(schemaMap)
                                    .strict(true)
                                    .description("Description"))));
    assertThat(configWithResponseSchemaFromClass).isEqualTo(configWithResponseSchemaLowLevel);
  }

  @Test
  void testTemplateConstruction() {
    List<ChatMessage> templateMessages =
        List.of(SingleChatMessage.create().role("user").content("message"));
    var defaults = Map.of("key", "value");
    var tools =
        List.of(
            ChatCompletionTool.create()
                .type(ChatCompletionTool.TypeEnum.FUNCTION)
                .function(FunctionObject.create().name("func")));
    var template =
        TemplateConfig.create()
            .withTemplate(templateMessages)
            .withDefaults(defaults)
            .withTools(tools)
            .withJsonResponse();

    var templateLowLevel =
        Template.create()
            .template(templateMessages)
            .defaults(defaults)
            .responseFormat(
                ResponseFormatJsonObject.create()
                    .type(ResponseFormatJsonObject.TypeEnum.JSON_OBJECT))
            .tools(tools);
    assertThat(template.toLowLevel()).isEqualTo(templateLowLevel);
  }

  @Test
  void testTemplateReferenceConstruction() {
    var templateReferenceId = TemplateConfig.reference().byId("id");
    var expectedTemplateReferenceId =
        new OrchestrationTemplateReference(TemplateRefByID.create().id("id"));
    var templateReferenceIdLowLevel =
        TemplateRef.create().templateRef(TemplateRefByID.create().id("id"));
    assertThat(templateReferenceId.getReference())
        .isEqualTo(expectedTemplateReferenceId.getReference());
    assertThat(templateReferenceId.toLowLevel()).isEqualTo(templateReferenceIdLowLevel);

    var templateReferenceScenarioNameVersion =
        TemplateConfig.reference().byScenario("scenario").name("name").version("version");
    var expectedTemplateReferenceScenarioNameVersion =
        new OrchestrationTemplateReference(
            TemplateRefByScenarioNameVersion.create()
                .scenario("scenario")
                .name("name")
                .version("version"));
    var templateReferenceScenarioNameVersionLowLevel =
        TemplateRef.create()
            .templateRef(
                TemplateRefByScenarioNameVersion.create()
                    .scenario("scenario")
                    .name("name")
                    .version("version"));
    assertThat(templateReferenceScenarioNameVersion.getReference())
        .isEqualTo(expectedTemplateReferenceScenarioNameVersion.getReference());
    assertThat(templateReferenceScenarioNameVersion.toLowLevel())
        .isEqualTo(templateReferenceScenarioNameVersionLowLevel);
  }
}
