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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    assertThat(templateReferenceId).isEqualTo(expectedTemplateReferenceId);
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
    assertThat(templateReferenceScenarioNameVersion)
        .isEqualTo(expectedTemplateReferenceScenarioNameVersion);
    assertThat(templateReferenceScenarioNameVersion.toLowLevel())
        .isEqualTo(templateReferenceScenarioNameVersionLowLevel);
  }

  @Test
  void testTemplateFromLocalFileWithJsonSchemaAndTools() throws IOException {
    String promptTemplateYaml =
        Files.readString(Path.of("src/test/resources/promptTemplateExample.yaml"));
    var templateWithJsonSchemaTools = TemplateConfig.create().fromYaml(promptTemplateYaml);
    var schema =
        Map.of(
            "type",
            "object",
            "properties",
            Map.of(
                "language", Map.of("type", "string"),
                "translation", Map.of("type", "string")),
            "required",
            List.of("language", "translation"),
            "additionalProperties",
            false);
    var expectedTemplateWithJsonSchemaTools =
        OrchestrationTemplate.create()
            .withTemplate(
                List.of(
                    SingleChatMessage.create()
                        .role("system")
                        .content("You are a language translator."),
                    SingleChatMessage.create()
                        .role("user")
                        .content("Whats {{ ?word }} in {{ ?language }}?")))
            .withDefaults(Map.of("word", "apple"))
            .withJsonSchemaResponse(
                ResponseJsonSchema.fromMap(schema, "translation-schema")
                    .withDescription("Translate the given word into the provided language.")
                    .withStrict(true))
            .withTools(
                List.of(
                    ChatCompletionTool.create()
                        .type(ChatCompletionTool.TypeEnum.FUNCTION)
                        .function(
                            FunctionObject.create()
                                .name("translate")
                                .parameters(
                                    Map.of(
                                        "type",
                                        "object",
                                        "additionalProperties",
                                        false,
                                        "required",
                                        List.of("language", "wordToTranslate"),
                                        "properties",
                                        Map.of(
                                            "language", Map.of("type", "string"),
                                            "wordToTranslate", Map.of("type", "string"))))
                                .description("Translate a word.")
                                .strict(true))));
    assertThat(templateWithJsonSchemaTools).isEqualTo(expectedTemplateWithJsonSchemaTools);
  }

  @Test
  void testTemplateFromLocalFileWithJsonObject() throws IOException {
    String promptTemplateWithJsonObject =
        """
 name: translator
 version: 0.0.1
 scenario: translation scenario
 spec:
  template:
    - role: "system"
      content: |-
        You are a language translator.
    - role: "user"
      content: |-
        Whats {{ ?word }} in {{ ?language }}?
  defaults:
    word: "apple"
  response_format:
    type: json_object
 """;
    var templateWithJsonObject = TemplateConfig.create().fromYaml(promptTemplateWithJsonObject);
    var expectedTemplateWithJsonObject =
        OrchestrationTemplate.create()
            .withTemplate(
                List.of(
                    SingleChatMessage.create()
                        .role("system")
                        .content("You are a language translator."),
                    SingleChatMessage.create()
                        .role("user")
                        .content("Whats {{ ?word }} in {{ ?language }}?")))
            .withDefaults(Map.of("word", "apple"))
            .withJsonResponse();
    assertThat(templateWithJsonObject).isEqualTo(expectedTemplateWithJsonObject);
  }
}
