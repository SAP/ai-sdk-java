package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiScenario;
import com.sap.ai.sdk.core.client.model.AiScenarioList;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ScenarioUnitTest extends WireMockTestServer {
  @Test
  void testGetScenarios() {
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
    AiScenario scenario = scenarioList.getResources().get(0);
    assertThat(scenario.getId()).isEqualTo("foundation-models");
    assertThat(scenario.getName()).isEqualTo("foundation-models");
    assertThat(scenario.getLabels().get(0).getKey()).isEqualTo("scenarios.ai.sap.com/llm");
    assertThat(scenario.getLabels().get(0).getValue()).isEqualTo("true");
    assertThat(scenario.getModifiedAt()).isEqualTo("2024-05-08T08:41:23+00:00");
  }
}
