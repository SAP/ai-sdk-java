package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiArtifactArgumentBinding;
import com.sap.ai.sdk.core.client.model.AiConfiguration;
import com.sap.ai.sdk.core.client.model.AiConfigurationBaseData;
import com.sap.ai.sdk.core.client.model.AiConfigurationCreationResponse;
import com.sap.ai.sdk.core.client.model.AiConfigurationList;
import com.sap.ai.sdk.core.client.model.AiParameterArgumentBinding;
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
        new ConfigurationApi(client).configurationQuery("default");

    assertThat(configurationList).isNotNull();
    assertThat(configurationList.getCount()).isEqualTo(1);
    assertThat(configurationList.getResources()).hasSize(1);
    AiConfiguration configuration = configurationList.getResources().get(0);
    assertThat(configuration.getCreatedAt()).isEqualTo("2024-04-17T15:19:45Z");
    assertThat(configuration.getExecutableId()).isEqualTo("azure-openai");
    assertThat(configuration.getId()).isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(configuration.getInputArtifactBindings()).isEmpty();
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
                    .withStatus(HttpStatus.SC_CREATED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "f88e7581-ade7-45c6-94e9-807889b523ec",
                          "message": "Configuration created"
                        }
                        """)));

    final AiArtifactArgumentBinding inputArtifactBindingsItem =
        AiArtifactArgumentBinding.create()
            .key("spam-data")
            .artifactId("744b0136-ed4b-49b1-bd10-08c236ed5ce7");
    final AiConfigurationBaseData configurationBaseData =
        AiConfigurationBaseData.create()
            .name("i538344_exec_config")
            .executableId("aicore-nvidia")
            .scenarioId("foundation-models")
            .addInputArtifactBindingsItem(inputArtifactBindingsItem);
    final AiConfigurationCreationResponse configuration =
        new ConfigurationApi(client).configurationCreate("default", configurationBaseData);

    assertThat(configuration).isNotNull();
    assertThat(configuration.getId()).isEqualTo("f88e7581-ade7-45c6-94e9-807889b523ec");
    assertThat(configuration.getMessage()).isEqualTo("Configuration created");

    wireMockServer.verify(
        postRequestedFor(urlPathEqualTo("/lm/configurations"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "name": "i538344_exec_config",
                      "executableId": "aicore-nvidia",
                      "scenarioId": "foundation-models",
                      "parameterBindings":[],
                      "inputArtifactBindings": [
                        {
                          "key": "spam-data",
                          "artifactId": "744b0136-ed4b-49b1-bd10-08c236ed5ce7"
                        }
                      ]
                    }
                    """)));
  }

  @Test
  void getConfigurationCount() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/configurations/$count"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody("""
                        3
                        """)));

    final int configurationCount = new ConfigurationApi(client).configurationCount("default");

    assertThat(configurationCount).isEqualTo(3);
  }

  @Test
  void getConfigurationById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/configurations/6ff6cb80-87db-45f0-b718-4e1d96e66332"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                            "createdAt": "2024-09-11T09:14:31Z",
                            "executableId": "st-spam-detection-i749902",
                            "id": "6ff6cb80-87db-45f0-b718-4e1d96e66332",
                            "inputArtifactBindings": [
                              {
                                "artifactId": "c4792df8-da67-44fe-ad99-b5ea74bbb248",
                                "key": "modeluri"
                              }
                            ],
                            "name": "i749902_depl_conf",
                            "parameterBindings": [
                              {
                                "key": "minreplicas",
                                "value": "1"
                              }
                            ],
                            "scenarioId": "scenario-spam-detection-i749902"
                          }
                        """)));

    final AiConfiguration configuration =
        new ConfigurationApi(client)
            .configurationGet("default", "6ff6cb80-87db-45f0-b718-4e1d96e66332");

    assertThat(configuration).isNotNull();
    assertThat(configuration.getCreatedAt()).isEqualTo("2024-09-11T09:14:31Z");
    assertThat(configuration.getExecutableId()).isEqualTo("st-spam-detection-i749902");
    assertThat(configuration.getId()).isEqualTo("6ff6cb80-87db-45f0-b718-4e1d96e66332");
    assertThat(configuration.getInputArtifactBindings()).hasSize(1);
    assertThat(configuration.getName()).isEqualTo("i749902_depl_conf");
    assertThat(configuration.getParameterBindings()).hasSize(1);
    assertThat(configuration.getScenarioId()).isEqualTo("scenario-spam-detection-i749902");

    AiArtifactArgumentBinding aiArtifactArgumentBinding =
        configuration.getInputArtifactBindings().get(0);
    assertThat(aiArtifactArgumentBinding.getArtifactId())
        .isEqualTo("c4792df8-da67-44fe-ad99-b5ea74bbb248");
    assertThat(aiArtifactArgumentBinding.getKey()).isEqualTo("modeluri");

    AiParameterArgumentBinding aiParameterArgumentBinding =
        configuration.getParameterBindings().get(0);
    assertThat(aiParameterArgumentBinding.getKey()).isEqualTo("minreplicas");
    assertThat(aiParameterArgumentBinding.getValue()).isEqualTo("1");
  }
}
