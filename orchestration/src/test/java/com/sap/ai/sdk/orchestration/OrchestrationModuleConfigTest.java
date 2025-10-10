package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE_LOW_MEDIUM;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.model.DataRepositoryType.VECTOR;
import static com.sap.ai.sdk.orchestration.model.GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPICustomEntity;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DPIMethodConstant;
import com.sap.ai.sdk.orchestration.model.DPIStandardEntity;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfigFiltersInner;
import com.sap.ai.sdk.orchestration.model.MaskingModuleConfigProviders;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OrchestrationModuleConfigTest {

  static class TestClassForSchemaGeneration {
    @JsonProperty(required = true)
    private String stringField;

    @JsonProperty(required = true)
    private int intField;

    @JsonProperty(required = true)
    private OrchestrationConvenienceUnitTest.TestClassForSchemaGeneration.InsideTestClass
        complexField;

    static class InsideTestClass {
      @JsonProperty(required = true)
      private String anotherStringField;
    }
  }

  @Test
  void testStackingInputAndOutputFilter() {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O);

    final var filter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE_LOW_MEDIUM)
            .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
            .sexual(ALLOW_SAFE_LOW_MEDIUM)
            .violence(ALLOW_SAFE_LOW_MEDIUM);

    final var configWithInputFirst = config.withInputFiltering(filter).withOutputFiltering(filter);
    assertThat(configWithInputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithInputFirst.getFilteringConfig().getInput()).isNotNull();

    final var configWithOutputFirst = config.withOutputFiltering(filter).withInputFiltering(filter);
    assertThat(configWithOutputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithOutputFirst.getFilteringConfig().getOutput()).isNotNull();
  }

  @Test
  void testThrowOnEmptyFilterConfig() {

    final var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O);

    assertThatThrownBy(() -> config.withInputFiltering(new AzureContentFilter()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter category must be set");
    assertThatThrownBy(() -> config.withOutputFiltering(new AzureContentFilter()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter category must be set");
  }

  @Test
  void testDpiMaskingConfig() {
    var maskingConfig =
        DpiMasking.anonymization()
            .withEntities(DPIEntities.ADDRESS)
            .withMaskGroundingInput(true)
            .withAllowList(List.of("Alice"));
    var config =
        new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withMaskingConfig(maskingConfig);

    assertThat(config.getMaskingConfig()).isNotNull();
    assertThat(((MaskingModuleConfigProviders) config.getMaskingConfig()).getProviders())
        .hasSize(1);
    DPIConfig dpiConfig =
        ((MaskingModuleConfigProviders) config.getMaskingConfig()).getProviders().get(0);
    assertThat(dpiConfig.getMethod()).isEqualTo(DPIConfig.MethodEnum.ANONYMIZATION);
    assertThat(dpiConfig.getEntities()).hasSize(1);
    assertThat(((DPIStandardEntity) dpiConfig.getEntities().get(0)).getType())
        .isEqualTo(DPIEntities.ADDRESS);
    assertThat(dpiConfig.getMaskGroundingInput().isEnabled()).isEqualTo(true);
    assertThat(dpiConfig.getAllowlist()).containsExactly("Alice");

    var configModified = config.withMaskingConfig(maskingConfig);
    assertThat(configModified.getMaskingConfig()).isNotNull();
    assertThat(((MaskingModuleConfigProviders) configModified.getMaskingConfig()).getProviders())
        .withFailMessage("withMaskingConfig() should overwrite the existing config and not append")
        .hasSize(1);
  }

  @Test
  void testDpiMaskingRegex() {
    var masking =
        DpiMasking.anonymization()
            .withRegex("\\d{3}-\\d{2}-\\d{4}", "***-**-****")
            .withRegex("\\d{2}-\\d{2}-\\d{5}", "**-**-*****");
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withMaskingConfig(masking);
    assertThat(config.getMaskingConfig()).isNotNull();
    assertThat(((MaskingModuleConfigProviders) config.getMaskingConfig()).getProviders())
        .hasSize(1);
    DPIConfig dpiConfig =
        ((MaskingModuleConfigProviders) config.getMaskingConfig()).getProviders().get(0);
    assertThat(dpiConfig.getMethod()).isEqualTo(DPIConfig.MethodEnum.ANONYMIZATION);
    assertThat(dpiConfig.getEntities()).hasSize(2);
    assertThat(((DPICustomEntity) dpiConfig.getEntities().get(0)).getRegex())
        .isEqualTo("\\d{3}-\\d{2}-\\d{4}");
    assertThat(((DPICustomEntity) dpiConfig.getEntities().get(0)).getReplacementStrategy())
        .isEqualTo(
            DPIMethodConstant.create()
                .method(DPIMethodConstant.MethodEnum.CONSTANT)
                .value("***-**-****"));
    assertThat(((DPICustomEntity) dpiConfig.getEntities().get(1)).getRegex())
        .isEqualTo("\\d{2}-\\d{2}-\\d{5}");
    assertThat(((DPICustomEntity) dpiConfig.getEntities().get(1)).getReplacementStrategy())
        .isEqualTo(
            DPIMethodConstant.create()
                .method(DPIMethodConstant.MethodEnum.CONSTANT)
                .value("**-**-*****"));
  }

  @Test
  void testParams() {
    // test withParams(Map<String, Object>)
    {
      var params = Map.<String, Object>of("foo", "bar", "fizz", "buzz");

      var modelA = GPT_4O.withParams(params);
      var modelB = modelA.withParams(params);
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParams(Map.of("foo", "bar"));
      assertThat(modelA).isNotEqualTo(modelC);

      var modelD = modelA.withParams(Map.of("foo", "bazz"));
      assertThat(modelA).isNotEqualTo(modelD);
    }

    // test withParam(String, Object)
    {
      var modelA = GPT_4O.withParam("foo", "bar");
      var modelB = modelA.withParam("foo", "bar");
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParam("foo", "bazz");
      assertThat(modelA).isNotEqualTo(modelC);
    }

    // test withParam(Parameter, Object)
    {
      var modelA = GPT_4O.withParam(MAX_TOKENS, 10);
      var modelB = modelA.withParam(MAX_TOKENS, 10);
      assertThat(modelA).isEqualTo(modelB);

      var modelC = modelA.withParam(MAX_TOKENS, 20);
      assertThat(modelA).isNotEqualTo(modelC);
    }
  }

  @Test
  void testLLMConfig() {
    Map<String, Object> params = Map.of("foo", "bar");
    String version = "2024-05-13";
    OrchestrationAiModel aiModel = GPT_4O.withParams(params).withVersion(version);
    var config = new OrchestrationModuleConfig().withLlmConfig(aiModel);

    assertThat(config.getLlmConfig()).isNotNull();
    assertThat(config.getLlmConfig().getName()).isEqualTo(GPT_4O.getName());
    assertThat(config.getLlmConfig().getParams()).isEqualTo(params);
    assertThat(config.getLlmConfig().getVersion()).isEqualTo(version);

    assertThat(GPT_4O.getParams()).withFailMessage("Static models should be unchanged").isEmpty();
    assertThat(GPT_4O.getVersion())
        .withFailMessage("Static models should be unchanged")
        .isEqualTo("latest");
  }

  @Test
  void testGroundingConfig() {
    var groundingConfig = Grounding.create();
    var config =
        new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withGrounding(groundingConfig);

    assertThat(config.getGroundingConfig()).isNotNull();
    assertThat(config.getGroundingConfig().getType()).isEqualTo(DOCUMENT_GROUNDING_SERVICE);

    GroundingModuleConfigConfig configConfig = config.getGroundingConfig().getConfig();
    assertThat(configConfig).isNotNull();
    assertThat(configConfig.getPlaceholders().getInput()).containsExactly("userMessage");
    assertThat(configConfig.getPlaceholders().getOutput()).isEqualTo("groundingContext");

    List<GroundingModuleConfigConfigFiltersInner> filters = configConfig.getFilters();
    assertThat(filters).hasSize(1);
    DocumentGroundingFilter filter = (DocumentGroundingFilter) filters.get(0);
    assertThat(filter.getId()).isNull();
    assertThat(filter.getDataRepositoryType()).isEqualTo(VECTOR);
  }

  @Test
  void testGroundingConfigWithFilters() {
    var filter1 = DocumentGroundingFilter.create().dataRepositoryType(VECTOR).id("123");
    var filter2 = DocumentGroundingFilter.create().dataRepositoryType(VECTOR).id("234");
    var groundingConfig = Grounding.create().filters(filter1, filter2);
    var config =
        new OrchestrationModuleConfig().withLlmConfig(GPT_4O).withGrounding(groundingConfig);

    assertThat(config.getGroundingConfig()).isNotNull();
    var configConfig = config.getGroundingConfig().getConfig();
    assertThat(configConfig).isNotNull();

    assertThat(config.getGroundingConfig().getConfig().getFilters()).hasSize(2);
  }

  @Test
  void testGroundingPrompt() {
    var prompt = Grounding.create().createGroundingPrompt("Hello, World!");
    assertThat(prompt.getMessages()).hasSize(1);
    var message = prompt.getMessages().get(0);
    assertThat(((TextItem) message.content().items().get(0)).text())
        .isEqualTo(
            "{{?userMessage}} Use the following information as additional context: {{?groundingContext}}");
  }

  @Test
  void testResponseFormatSchema() {
    var schema = ResponseJsonSchema.fromType(TestClassForSchemaGeneration.class);
    var config =
        new OrchestrationModuleConfig()
            .withTemplateConfig(TemplateConfig.create().withJsonSchemaResponse(schema));
    assertThat(((Template) config.getTemplateConfig())).isNotNull();
    assertThat(
            ((ResponseFormatJsonSchema) ((Template) config.getTemplateConfig()).getResponseFormat())
                .getJsonSchema()
                .getSchema())
        .isEqualTo(schema.getSchemaMap());
  }

  @Test
  void testResponseFormatObject() {
    var config =
        new OrchestrationModuleConfig()
            .withTemplateConfig(TemplateConfig.create().withJsonResponse());
    assertThat(((Template) config.getTemplateConfig())).isNotNull();
    assertThat(
            ((ResponseFormatJsonObject)
                ((Template) config.getTemplateConfig()).getResponseFormat()))
        .isInstanceOf(ResponseFormatJsonObject.class);
  }

  @Test
  void testResponseFormatOverwrittenByNewTemplateRef() {
    var schema = ResponseJsonSchema.fromType(TestClassForSchemaGeneration.class);
    var config =
        new OrchestrationModuleConfig()
            .withTemplateConfig(TemplateConfig.create().withJsonSchemaResponse(schema));
    assertThat(((Template) config.getTemplateConfig())).isNotNull();
    assertThat(
            ((ResponseFormatJsonSchema) ((Template) config.getTemplateConfig()).getResponseFormat())
                .getJsonSchema()
                .getSchema())
        .isEqualTo(schema.getSchemaMap());

    config =
        config.withTemplateConfig(
            TemplateRef.create().templateRef(TemplateRefByID.create().id("123")));
    assertThat(config.getTemplateConfig()).isInstanceOf(TemplateRef.class);
  }
}
