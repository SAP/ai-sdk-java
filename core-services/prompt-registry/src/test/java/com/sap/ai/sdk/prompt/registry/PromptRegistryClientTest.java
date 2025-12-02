package com.sap.ai.sdk.prompt.registry;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateGetResponse;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class PromptRegistryClientTest {
  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private static PromptClient client;

  @BeforeEach
  void setup() {
    final HttpDestination destination = DefaultHttpDestination.builder(WM.baseUrl()).build();
    final AiCoreService service = new AiCoreService().withBaseDestination(destination);
    client = new PromptClient(service);
  }

  @Test
  void testPipelines() {
    final var result = client.listPromptTemplates();
    assertThat(result.getCount()).isEqualTo(2);
    assertThat(result.getResources()).hasSize(2);
    final var template = result.getResources().get(0);
    assertThat(template.getId()).isEqualTo(UUID.fromString("312a9b9c-a532-4c1c-8852-bf75de887d74"));
    assertThat(template.getName()).isEqualTo("prompt_template_name");
    assertThat(template.getVersion()).isEqualTo("1.0.0");
    assertThat(template.getScenario()).isEqualTo("MyScenario");
    assertThat(template.getCreationTimestamp()).isEqualTo("2025-02-26T12:29:55.875000");
    assertThat(template.getManagedBy()).isEqualTo("imperative");
    assertThat(template.isIsVersionHead()).isEqualTo(true);
    assertThat(template.getSpec()).isNull();
  }

  @Test
  void testGetTemplateWithResponseFormatText() {
    final var uuid = UUID.fromString("22117a64-9f2c-481b-9402-8acb66eeb707");
    final PromptTemplateGetResponse response = client.getPromptTemplateByUuid(uuid);

    assertThat(response.getName()).isEqualTo("test");
    assertThat(response.getVersion()).isEqualTo("0.0.1");
    assertThat(response.getScenario()).isEqualTo("test-retrival");
    assertThat(response.getSpec()).isNotNull();
    assertThat(response.getSpec().getResponseFormat()).isInstanceOf(ResponseFormatText.class);

    final var format = (ResponseFormatText) response.getSpec().getResponseFormat();
    assertThat(format.getType()).isEqualTo(ResponseFormatText.TypeEnum.TEXT);
  }

  @Test
  void testGetTemplateWithResponseFormatJsonObject() {
    final var uuid = UUID.fromString("21cb1358-0bf1-4f43-870b-00f14d0f9f16");
    final var response = client.getPromptTemplateByUuid(uuid);

    assertThat(response.getName()).isEqualTo("test");
    assertThat(response.getVersion()).isEqualTo("0.0.1");
    assertThat(response.getScenario()).isEqualTo("test-retrival");
    assertThat(response.getSpec()).isNotNull();
    assertThat(response.getSpec().getResponseFormat()).isInstanceOf(ResponseFormatJsonObject.class);

    final var format = (ResponseFormatJsonObject) response.getSpec().getResponseFormat();
    assertThat(format.getType()).isEqualTo(ResponseFormatJsonObject.TypeEnum.JSON_OBJECT);
  }

  @Test
  void testGetTemplateWithResponseFormatJsonSchema() {
    final var uuid = UUID.fromString("0f79fec4-ae07-4c35-96e3-df7f4a3f1df5");
    final var response = client.getPromptTemplateByUuid(uuid);

    assertThat(response.getName()).isEqualTo("test");
    assertThat(response.getVersion()).isEqualTo("0.0.1");
    assertThat(response.getScenario()).isEqualTo("test-retrival");
    assertThat(response.getSpec()).isNotNull();
    assertThat(response.getSpec().getResponseFormat()).isInstanceOf(ResponseFormatJsonSchema.class);

    final var format = (ResponseFormatJsonSchema) response.getSpec().getResponseFormat();
    assertThat(format.getType()).isEqualTo(ResponseFormatJsonSchema.TypeEnum.JSON_SCHEMA);
    assertThat(format.getJsonSchema()).isNotNull();
    assertThat(format.getJsonSchema().getName()).isEqualTo("TestSchema");
    assertThat(format.getJsonSchema().getDescription()).isEqualTo("Test schema description");
    assertThat(format.getJsonSchema().isStrict()).isFalse();
    assertThat(format.getJsonSchema().getSchema()).isNotNull();
  }
}
