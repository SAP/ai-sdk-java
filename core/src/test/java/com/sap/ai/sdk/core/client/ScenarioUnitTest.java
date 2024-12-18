package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.model.AiModelVersion;
import com.sap.ai.sdk.core.model.AiScenarioLabel;
import lombok.val;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
class ScenarioUnitTest extends WireMockTestServer {
  @Test
  void getScenarios() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/scenarios"))
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

    val scenarioList = new ScenarioApi(aiCoreService).query("default");

    assertThat(scenarioList).isNotNull();
    assertThat(scenarioList.getCount()).isEqualTo(1);
    assertThat(scenarioList.getResources()).hasSize(1);

    val scenario = scenarioList.getResources().get(0);

    assertThat(scenario.getId()).isEqualTo("foundation-models");
    assertThat(scenario.getName()).isEqualTo("foundation-models");
    assertThat(scenario.getLabels().get(0).getKey()).isEqualTo("scenarios.ai.sap.com/llm");
    assertThat(scenario.getLabels().get(0).getValue()).isEqualTo("true");
    assertThat(scenario.getModifiedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
  }

  @Test
  void getScenarioVersions() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/scenarios/foundation-models/versions"))
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

    val versionList = new ScenarioApi(aiCoreService).queryVersions("default", "foundation-models");

    assertThat(versionList).isNotNull();
    assertThat(versionList.getCount()).isEqualTo(1);
    assertThat(versionList.getResources()).hasSize(1);

    val version = versionList.getResources().get(0);

    assertThat(version.getCreatedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
    assertThat(version.getId()).isEqualTo("0.0.1");
    assertThat(version.getModifiedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
    assertThat(version.getScenarioId()).isEqualTo("foundation-models");
  }

  @Test
  void getScenarioById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/scenarios/foundation-models"))
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

    val scenario = new ScenarioApi(aiCoreService).get("default", "foundation-models");

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
        get(urlPathEqualTo("/v2/lm/scenarios/foundation-models/models"))
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

    val scenarioList = new ScenarioApi(aiCoreService).queryModels("foundation-models", "default");

    assertThat(scenarioList).isNotNull();
    assertThat(scenarioList.getCount()).isEqualTo(1);
    assertThat(scenarioList.getResources()).hasSize(1);

    val scenario = scenarioList.getResources().get(0);
    assertThat(scenario.getDescription()).isEqualTo("Mistral mixtral-8x7b-instruct-v01 model");
    assertThat(scenario.getExecutableId()).isEqualTo("aicore-opensource");
    assertThat(scenario.getModel()).isEqualTo("mistralai--mixtral-8x7b-instruct-v01");

    AiModelVersion aiModelVersion = scenario.getVersions().get(0);
    assertThat(aiModelVersion.getName()).isEqualTo("202407");
    assertThat(aiModelVersion.isIsLatest()).isTrue();
    assertThat(aiModelVersion.isDeprecated()).isFalse();
    assertThat(aiModelVersion.getRetirementDate()).isEmpty();
  }
}
