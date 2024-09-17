package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.WireMockTestServer;
import com.sap.ai.sdk.core.client.model.AiArtifact;
import com.sap.ai.sdk.core.client.model.AiEnactmentCreationRequest;
import com.sap.ai.sdk.core.client.model.AiExecution;
import com.sap.ai.sdk.core.client.model.AiExecutionCreationResponse;
import com.sap.ai.sdk.core.client.model.AiExecutionList;
import com.sap.ai.sdk.core.client.model.AiExecutionStatus;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ExecutionUnitTest extends WireMockTestServer {
  @Test
  void testGetExecutions() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/executions"))
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
                            "completionTime": "2023-08-05T14:10:16Z",
                            "configurationId": "e0a9eb2e-9ea1-43bf-aff5-7660db166676",
                            "configurationName": "spam-detection-execution-config-0",
                            "createdAt": "2023-08-05T14:07:52Z",
                            "executableId": "wt-spam-detection-i343697",
                            "id": "eab289226fe981da",
                            "modifiedAt": "2023-08-05T14:10:54Z",
                            "outputArtifacts": [
                              {
                                "createdAt": "2023-08-05T14:10:05Z",
                                "description": "",
                                "executionId": "eab289226fe981da",
                                "id": "be0d728f-1cb2-4ff4-97ad-45c54ac592f6",
                                "kind": "model",
                                "modifiedAt": "2023-08-05T14:10:05Z",
                                "name": "classifier-model-output",
                                "scenarioId": "scenario-spam-detection-i343697",
                                "url": "ai://default/eab289226fe981da/classifier-model-output"
                              }
                            ],
                            "scenarioId": "scenario-spam-detection-i343697",
                            "startTime": "2023-08-05T14:09:21Z",
                            "status": "COMPLETED",
                            "submissionTime": "2023-08-05T14:09:21Z",
                            "targetStatus": "COMPLETED"
                            }
                          ]
                        }
                        """)));

    final AiExecutionList executionList =
        new ExecutionApi(getClient(destination)).executionQuery("default");
    assertThat(executionList).isNotNull();
    assertThat(executionList.getCount()).isEqualTo(1);
    assertThat(executionList.getResources().size()).isEqualTo(1);
    AiExecution execution = executionList.getResources().get(0);
    assertThat(execution.getCompletionTime()).isEqualTo("2023-08-05T14:10:16Z");
    assertThat(execution.getConfigurationId()).isEqualTo("e0a9eb2e-9ea1-43bf-aff5-7660db166676");
    assertThat(execution.getConfigurationName()).isEqualTo("spam-detection-execution-config-0");
    assertThat(execution.getCreatedAt()).isEqualTo("2023-08-05T14:07:52Z");
    // executableId is not in the generated client, but we can still get it from the
    // cloudSdkCustomFields
    assertThat(execution.getCustomField("executableId")).isEqualTo("wt-spam-detection-i343697");
    assertThat(execution.getOutputArtifacts().get(0).getId())
        .isEqualTo("be0d728f-1cb2-4ff4-97ad-45c54ac592f6");
    assertThat(execution.getOutputArtifacts().get(0).getKind())
        .isEqualTo(AiArtifact.KindEnum.MODEL);
    assertThat(execution.getOutputArtifacts().get(0).getUrl())
        .isEqualTo("ai://default/eab289226fe981da/classifier-model-output");
    assertThat(execution.getStatus()).isEqualTo(AiExecutionStatus.COMPLETED);
  }

  @Test
  void testPostExecution() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/lm/executions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "eab289226fe981da",
                          "message": "AiExecution acknowledged",
                          "url": "ai://default/eab289226fe981da"
                        }
                        """)));

    AiEnactmentCreationRequest enactmentCreationRequest =
        AiEnactmentCreationRequest.create().configurationId("e0a9eb2e-9ea1-43bf-aff5-7660db166676");
    final AiExecutionCreationResponse execution =
        new ExecutionApi(getClient(destination))
            .executionCreate("default", enactmentCreationRequest);
    assertThat(execution).isNotNull();
    assertThat(execution.getId()).isEqualTo("eab289226fe981da");
    assertThat(execution.getMessage()).isEqualTo("AiExecution acknowledged");
    assertThat(execution.getCustomField("url")).isEqualTo("ai://default/eab289226fe981da");
  }
}
