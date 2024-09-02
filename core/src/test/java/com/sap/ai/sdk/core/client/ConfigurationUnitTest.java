package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiArtifactArgumentBinding;
import com.sap.ai.sdk.core.client.model.AiConfiguration;
import com.sap.ai.sdk.core.client.model.AiConfigurationBaseData;
import com.sap.ai.sdk.core.client.model.AiConfigurationCreationResponse;
import com.sap.ai.sdk.core.client.model.AiConfigurationList;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ConfigurationUnitTest extends WireMockTestServer {
  @Test
  void getConfigurations() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/configurations"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "count": 1,
                          "resources": [
                            {
                              "createdAt": "2024-04-17T15:19:45Z",
                              "executableId": "azure-openai",
                              "id": "7652a231-ba9b-4fcc-b473-2c355cb21b61",
                              "inputArtifactBindings": [],
                              "name": "gpt-4-32k",
                              "parameterBindings": [
                                {
                                  "key": "modelName",
                                  "value": "gpt-4-32k"
                                },
                                {
                                  "key": "modelVersion",
                                  "value": "latest"
                                }
                              ],
                              "scenarioId": "foundation-models"
                            }
                          ]
                        }
                        """)));

    final AiConfigurationList configurationList =
        new ConfigurationApi(getClient(destination)).configurationQuery("default");
    assertThat(configurationList).isNotNull();
    assertThat(configurationList.getCount()).isEqualTo(1);
    assertThat(configurationList.getResources().size()).isEqualTo(1);
    AiConfiguration configuration = configurationList.getResources().get(0);
    assertThat(configuration.getCreatedAt()).isEqualTo("2024-04-17T15:19:45Z");
    assertThat(configuration.getExecutableId()).isEqualTo("azure-openai");
    assertThat(configuration.getId()).isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(configuration.getInputArtifactBindings().size()).isEqualTo(0);
    assertThat(configuration.getName()).isEqualTo("gpt-4-32k");
    assertThat(configuration.getParameterBindings().get(0).getKey()).isEqualTo("modelName");
    assertThat(configuration.getParameterBindings().get(0).getValue()).isEqualTo("gpt-4-32k");
    assertThat(configuration.getParameterBindings().get(1).getKey()).isEqualTo("modelVersion");
    assertThat(configuration.getParameterBindings().get(1).getValue()).isEqualTo("latest");
    assertThat(configuration.getScenarioId()).isEqualTo("foundation-models");
  }

  @Test
  void postConfiguration() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/lm/configurations"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "f88e7581-ade7-45c6-94e9-807889b523ec",
                          "message": "Configuration created"
                        }
                        """)));

    AiConfigurationBaseData configurationBaseData =
        AiConfigurationBaseData.create()
            .name("i538344_exec_config")
            .executableId("aicore-nvidia")
            .scenarioId("foundation-models")
            .addInputArtifactBindingsItem(
                AiArtifactArgumentBinding.create()
                    .key("spam-data")
                    .artifactId("744b0136-ed4b-49b1-bd10-08c236ed5ce7"));
    final AiConfigurationCreationResponse configuration =
        new ConfigurationApi(getClient(destination))
            .configurationCreate("default", configurationBaseData);
    assertThat(configuration).isNotNull();
    assertThat(configuration.getId()).isEqualTo("f88e7581-ade7-45c6-94e9-807889b523ec");
    assertThat(configuration.getMessage()).isEqualTo("Configuration created");
  }
}
