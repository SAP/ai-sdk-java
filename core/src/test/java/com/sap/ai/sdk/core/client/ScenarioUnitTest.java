package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiModelBaseData;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.core.client.model.AiModelVersion;
import com.sap.ai.sdk.core.client.model.AiScenario;
import com.sap.ai.sdk.core.client.model.AiScenarioLabel;
import com.sap.ai.sdk.core.client.model.AiScenarioList;
import com.sap.ai.sdk.core.client.model.AiVersion;
import com.sap.ai.sdk.core.client.model.AiVersionList;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ScenarioUnitTest extends WireMockTestServer {
  @Test
  void getScenarios() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/scenarios"))
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
                              "createdAt": "2024-02-22T17:57:23+00:00",
                              "description": "AI Core Global AiScenario for LLM Access",
                              "id": "foundation-models",
                              "labels": [
                                {
                                  "key": "scenarios.ai.sap.com/llm",
                                  "value": "true"
                                }
                              ],
                              "modifiedAt": "2024-05-08T08:41:23+00:00",
                              "name": "foundation-models"
                            }
                          ]
                        }
                        """)));

    final AiScenarioList scenarioList =
        new ScenarioApi(getClient(destination)).scenarioQuery("default");

    assertThat(scenarioList).isNotNull();
    assertThat(scenarioList.getCount()).isEqualTo(1);
    assertThat(scenarioList.getResources().size()).isEqualTo(1);

    final AiScenario scenario = scenarioList.getResources().get(0);

    assertThat(scenario.getId()).isEqualTo("foundation-models");
    assertThat(scenario.getName()).isEqualTo("foundation-models");
    assertThat(scenario.getLabels().get(0).getKey()).isEqualTo("scenarios.ai.sap.com/llm");
    assertThat(scenario.getLabels().get(0).getValue()).isEqualTo("true");
    assertThat(scenario.getModifiedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
  }

  @Test
  void getScenarioVersions() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/scenarios/foundation-models/versions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withHeader("content-type", "application/json")
                    .withStatus(HttpStatus.SC_OK)
                    .withBody(
                        """
                        {
                          "count": 1,
                          "resources": [
                            {
                              "createdAt": "2024-05-08T08:41:23+00:00",
                              "id": "0.0.1",
                              "modifiedAt": "2024-05-08T08:41:23+00:00",
                              "scenarioId": "foundation-models"
                            }
                          ]
                        }
                        """)));

    final AiVersionList versionList =
        new ScenarioApi(getClient(destination))
            .scenarioQueryVersions("default", "foundation-models");

    assertThat(versionList).isNotNull();
    assertThat(versionList.getCount()).isEqualTo(1);
    assertThat(versionList.getResources().size()).isEqualTo(1);

    final AiVersion version = versionList.getResources().get(0);

    assertThat(version.getCreatedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
    assertThat(version.getId()).isEqualTo("0.0.1");
    assertThat(version.getModifiedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
    assertThat(version.getScenarioId()).isEqualTo("foundation-models");
  }

  @Test
  void getScenarioById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/scenarios/foundation-models"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withHeader("content-type", "application/json")
                    .withStatus(HttpStatus.SC_OK)
                    .withBody(
                        """
                        {
                          "createdAt": "2023-11-03T14:02:46+00:00",
                          "description": "AI Core Global Scenario for LLM Access",
                          "id": "foundation-models",
                          "labels": [
                            {
                              "key": "scenarios.ai.sap.com/llm",
                              "value": "true"
                            }
                          ],
                          "modifiedAt": "2024-05-24T08:36:29+00:00",
                          "name": "foundation-models"
                        }
                        """)));

    final AiScenario scenario =
        new ScenarioApi(getClient(destination)).scenarioGet("default", "foundation-models");

    assertThat(scenario).isNotNull();
    assertThat(scenario.getCreatedAt()).isEqualTo("2023-11-03T14:02:46+00:00");
    assertThat(scenario.getDescription()).isEqualTo("AI Core Global Scenario for LLM Access");
    assertThat(scenario.getId()).isEqualTo("foundation-models");
    assertThat(scenario.getName()).isEqualTo("foundation-models");
    assertThat(scenario.getModifiedAt()).isEqualTo("2024-05-24T08:36:29+00:00");

    AiScenarioLabel aiScenarioLabel = scenario.getLabels().get(0);
    assertThat(aiScenarioLabel.getKey()).isEqualTo("scenarios.ai.sap.com/llm");
    assertThat(aiScenarioLabel.getValue()).isEqualTo("true");
  }

  @Test
  void getScenarioModels() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/scenarios/foundation-models/models"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withHeader("content-type", "application/json")
                    .withStatus(HttpStatus.SC_OK)
                    .withBody(
                        """
                            {
                              "count": 1,
                              "resources": [
                                {
                                  "description": "Mistral mixtral-8x7b-instruct-v01 model",
                                  "executableId": "aicore-opensource",
                                  "model": "mistralai--mixtral-8x7b-instruct-v01",
                                  "versions": [
                                    {
                                      "deprecated": false,
                                      "isLatest": true,
                                      "name": "202407",
                                      "retirementDate": ""
                                    }
                                  ]
                                }
                              ]
                            }
                            """)));

    final AiModelList scenarioList =
        new ScenarioApi(getClient(destination)).modelsGet("foundation-models", "default");

    assertThat(scenarioList).isNotNull();
    assertThat(scenarioList.getCount()).isEqualTo(1);
    assertThat(scenarioList.getResources().size()).isEqualTo(1);

    final AiModelBaseData scenario = scenarioList.getResources().get(0);
    assertThat(scenario.getDescription()).isEqualTo("Mistral mixtral-8x7b-instruct-v01 model");
    assertThat(scenario.getExecutableId()).isEqualTo("aicore-opensource");
    assertThat(scenario.getModel()).isEqualTo("mistralai--mixtral-8x7b-instruct-v01");

    AiModelVersion aiModelVersion = scenario.getVersions().get(0);
    assertThat(aiModelVersion.getName()).isEqualTo("202407");
    assertThat(aiModelVersion.isIsLatest()).isEqualTo(true);
    // deprecated and retirementDate properties are not defined in spec.
    assertThat(aiModelVersion.getCustomField("deprecated")).isEqualTo(false);
    assertThat(aiModelVersion.getCustomField("retirementDate")).isEqualTo("");
  }
}
