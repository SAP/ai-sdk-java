package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiArtifact;
import com.sap.ai.sdk.core.client.model.AiEnactmentCreationRequest;
import com.sap.ai.sdk.core.client.model.AiExecution;
import com.sap.ai.sdk.core.client.model.AiExecutionBulkModificationRequest;
import com.sap.ai.sdk.core.client.model.AiExecutionBulkModificationResponse;
import com.sap.ai.sdk.core.client.model.AiExecutionCreationResponse;
import com.sap.ai.sdk.core.client.model.AiExecutionDeletionResponse;
import com.sap.ai.sdk.core.client.model.AiExecutionList;
import com.sap.ai.sdk.core.client.model.AiExecutionModificationRequest;
import com.sap.ai.sdk.core.client.model.AiExecutionModificationRequestWithIdentifier;
import com.sap.ai.sdk.core.client.model.AiExecutionModificationResponse;
import com.sap.ai.sdk.core.client.model.AiExecutionModificationResponseListInner;
import com.sap.ai.sdk.core.client.model.AiExecutionResponseWithDetails;
import com.sap.ai.sdk.core.client.model.AiExecutionStatus;
import com.sap.ai.sdk.core.client.model.RTALogCommonResponse;
import com.sap.ai.sdk.core.client.model.RTALogCommonResultItem;
import java.util.Set;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ExecutionUnitTest extends WireMockTestServer {
  @Test
  void getExecutions() {
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

    final AiExecution execution = executionList.getResources().get(0);

    assertThat(execution.getCompletionTime()).isEqualTo("2023-08-05T14:10:16Z");
    assertThat(execution.getConfigurationId()).isEqualTo("e0a9eb2e-9ea1-43bf-aff5-7660db166676");
    assertThat(execution.getConfigurationName()).isEqualTo("spam-detection-execution-config-0");
    assertThat(execution.getCreatedAt()).isEqualTo("2023-08-05T14:07:52Z");
    assertThat(execution.getExecutableId()).isEqualTo("wt-spam-detection-i343697");
    assertThat(execution.getScenarioId()).isEqualTo("scenario-spam-detection-i343697");
    assertThat(execution.getStartTime()).isEqualTo("2023-08-05T14:09:21Z");
    assertThat(execution.getStatus()).isEqualTo(AiExecutionStatus.COMPLETED);
    assertThat(execution.getSubmissionTime()).isEqualTo("2023-08-05T14:09:21Z");
    assertThat(execution.getTargetStatus()).isEqualTo(AiExecution.TargetStatusEnum.COMPLETED);
    assertThat(execution.getOutputArtifacts().size()).isEqualTo(1);

    final AiArtifact aiArtifact = execution.getOutputArtifacts().get(0);

    assertThat(aiArtifact.getCreatedAt()).isEqualTo("2023-08-05T14:10:05Z");
    assertThat(aiArtifact.getDescription()).isEqualTo("");
    assertThat(aiArtifact.getExecutionId()).isEqualTo("eab289226fe981da");
    assertThat(aiArtifact.getId()).isEqualTo("be0d728f-1cb2-4ff4-97ad-45c54ac592f6");
    assertThat(aiArtifact.getKind()).isEqualTo(AiArtifact.KindEnum.MODEL);
    assertThat(aiArtifact.getModifiedAt()).isEqualTo("2023-08-05T14:10:05Z");
    assertThat(aiArtifact.getName()).isEqualTo("classifier-model-output");
    assertThat(aiArtifact.getScenarioId()).isEqualTo("scenario-spam-detection-i343697");
    assertThat(aiArtifact.getUrl())
        .isEqualTo("ai://default/eab289226fe981da/classifier-model-output");
  }

  @Test
  void postExecution() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/lm/executions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_CREATED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "eab289226fe981da",
                          "message": "AiExecution acknowledged",
                          "url": "ai://default/eab289226fe981da"
                        }
                        """)));

    final AiEnactmentCreationRequest enactmentCreationRequest =
        AiEnactmentCreationRequest.create().configurationId("e0a9eb2e-9ea1-43bf-aff5-7660db166676");
    final AiExecutionCreationResponse execution =
        new ExecutionApi(getClient(destination))
            .executionCreate("default", enactmentCreationRequest);

    assertThat(execution).isNotNull();
    assertThat(execution.getId()).isEqualTo("eab289226fe981da");
    assertThat(execution.getMessage()).isEqualTo("AiExecution acknowledged");
    assertThat(execution.getCustomField("url")).isEqualTo("ai://default/eab289226fe981da");

    wireMockServer.verify(
        postRequestedFor(urlPathEqualTo("/lm/executions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "configurationId": "e0a9eb2e-9ea1-43bf-aff5-7660db166676"
                    }
                    """)));
  }

  @Test
  void getExecutionById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/executions/e529e8bd58740bc9"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "completionTime": "2024-09-09T19:10:58Z",
                            "configurationId": "218b651d-113d-4da0-be15-b63a644a92fb",
                            "configurationName": "i749902_exec_conf",
                            "createdAt": "2024-09-09T19:09:57Z",
                            "executableId": "wt-spam-detection-i749902",
                            "id": "e529e8bd58740bc9",
                            "modifiedAt": "2024-09-09T19:11:17Z",
                            "outputArtifacts": [
                              {
                                "createdAt": "2024-09-09T19:10:48Z",
                                "description": "",
                                "executionId": "e529e8bd58740bc9",
                                "id": "c4792df8-da67-44fe-ad99-b5ea74bbb248",
                                "kind": "model",
                                "modifiedAt": "2024-09-09T19:10:48Z",
                                "name": "classifier-model-output",
                                "scenarioId": "scenario-spam-detection-i749902",
                                "url": "ai://default/e529e8bd58740bc9/classifier-model-output"
                              }
                            ],
                            "scenarioId": "scenario-spam-detection-i749902",
                            "startTime": "2024-09-09T19:10:11Z",
                            "status": "COMPLETED",
                            "submissionTime": "2024-09-09T19:10:11Z",
                            "targetStatus": "COMPLETED"
                        }
                        """)));

    final AiExecutionResponseWithDetails execution =
        new ExecutionApi(getClient(destination)).executionGet("default", "e529e8bd58740bc9");

    assertThat(execution).isNotNull();
    assertThat(execution.getCompletionTime()).isEqualTo("2024-09-09T19:10:58Z");
    assertThat(execution.getConfigurationId()).isEqualTo("218b651d-113d-4da0-be15-b63a644a92fb");
    assertThat(execution.getConfigurationName()).isEqualTo("i749902_exec_conf");
    assertThat(execution.getCreatedAt()).isEqualTo("2024-09-09T19:09:57Z");
    assertThat(execution.getExecutableId()).isEqualTo("wt-spam-detection-i749902");
    assertThat(execution.getId()).isEqualTo("e529e8bd58740bc9");
    assertThat(execution.getModifiedAt()).isEqualTo("2024-09-09T19:11:17Z");
    assertThat(execution.getScenarioId()).isEqualTo("scenario-spam-detection-i749902");
    assertThat(execution.getStartTime()).isEqualTo("2024-09-09T19:10:11Z");
    assertThat(execution.getStatus()).isEqualTo(AiExecutionStatus.COMPLETED);
    assertThat(execution.getSubmissionTime()).isEqualTo("2024-09-09T19:10:11Z");
    assertThat(execution.getTargetStatus())
        .isEqualTo(AiExecutionResponseWithDetails.TargetStatusEnum.COMPLETED);
    assertThat(execution.getOutputArtifacts().size()).isEqualTo(1);

    final AiArtifact aiArtifact = execution.getOutputArtifacts().get(0);

    assertThat(aiArtifact.getCreatedAt()).isEqualTo("2024-09-09T19:10:48Z");
    assertThat(aiArtifact.getDescription()).isEqualTo("");
    assertThat(aiArtifact.getExecutionId()).isEqualTo("e529e8bd58740bc9");
    assertThat(aiArtifact.getId()).isEqualTo("c4792df8-da67-44fe-ad99-b5ea74bbb248");
    assertThat(aiArtifact.getKind()).isEqualTo(AiArtifact.KindEnum.MODEL);
    assertThat(aiArtifact.getModifiedAt()).isEqualTo("2024-09-09T19:10:48Z");
    assertThat(aiArtifact.getName()).isEqualTo("classifier-model-output");
    assertThat(aiArtifact.getScenarioId()).isEqualTo("scenario-spam-detection-i749902");
    assertThat(aiArtifact.getUrl())
        .isEqualTo("ai://default/e529e8bd58740bc9/classifier-model-output");
  }

  @Test
  void deleteExecution() {
    wireMockServer.stubFor(
        delete(urlPathEqualTo("/lm/executions/e529e8bd58740bc9"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                           "id": "e529e8bd58740bc9",
                           "message": "Deletion scheduled",
                           "targetStatus": "DELETED"
                         }
                        """)));

    final AiExecutionDeletionResponse execution =
        new ExecutionApi(getClient(destination)).executionDelete("default", "e529e8bd58740bc9");

    assertThat(execution).isNotNull();
    assertThat(execution.getId()).isEqualTo("e529e8bd58740bc9");
    assertThat(execution.getMessage()).isEqualTo("Deletion scheduled");
    // targetStatus is not in the generated client.
    assertThat(execution.getCustomField("targetStatus")).isEqualTo("DELETED");
  }

  @Test
  void patchExecution() {
    wireMockServer.stubFor(
        patch(urlPathEqualTo("/lm/executions/eec3c6ea18bac6da"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "eec3c6ea18bac6da",
                          "message": "Execution modification scheduled"
                        }
                        """)));

    final AiExecutionModificationRequest aiExecutionModificationRequest =
        AiExecutionModificationRequest.create()
            .targetStatus(AiExecutionModificationRequest.TargetStatusEnum.STOPPED);
    final AiExecutionModificationResponse aiExecutionModificationResponse =
        new ExecutionApi(getClient(destination))
            .executionModify("default", "eec3c6ea18bac6da", aiExecutionModificationRequest);

    assertThat(aiExecutionModificationResponse).isNotNull();
    assertThat(aiExecutionModificationResponse.getId()).isEqualTo("eec3c6ea18bac6da");
    assertThat(aiExecutionModificationResponse.getMessage())
        .isEqualTo("Execution modification scheduled");

    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/lm/executions/eec3c6ea18bac6da"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(equalToJson("{\"targetStatus\":\"STOPPED\"}")));
  }

  @Test
  void getExecutionCount() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/executions/$count"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody("""
                        1
                        """)));

    final int count = new ExecutionApi(getClient(destination)).executionCount("default");

    assertThat(count).isEqualTo(1);
  }

  @Test
  void getExecutionLogs() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/executions/ee467bea5af28adb/logs"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                           "data": {
                             "result": [
                               {
                                 "container": "init",
                                 "msg": "time=\\"2024-09-18T13:19:40.527Z\\" level=info msg=\\"Starting Workflow Executor\\" version=v3.5.4+960af33.dirty",
                                 "pod": "ee467bea5af28adb",
                                 "timestamp": "2024-09-18T13:19:40.527449310+00:00"
                               }
                             ]
                           }
                         }
                        """)));

    final RTALogCommonResponse logResponse =
        new ExecutionApi(getClient(destination).addDefaultHeader("AI-Resource-Group", "default"))
            .kubesubmitV4ExecutionsGetLogs("ee467bea5af28adb");

    assertThat(logResponse).isNotNull();
    assertThat(logResponse.getData().getResult().size()).isEqualTo(1);

    RTALogCommonResultItem rtaLogCommonResultItem = logResponse.getData().getResult().get(0);

    assertThat(rtaLogCommonResultItem.getTimestamp())
        .isEqualTo("2024-09-18T13:19:40.527449310+00:00");
    assertThat(rtaLogCommonResultItem.getMsg())
        .isEqualTo(
            "time=\"2024-09-18T13:19:40.527Z\" level=info msg=\"Starting Workflow Executor\" version=v3.5.4+960af33.dirty");
    // `container` and `pod` properties are not in the generated client.
    assertThat(rtaLogCommonResultItem.getCustomField("container")).isEqualTo("init");
    assertThat(rtaLogCommonResultItem.getCustomField("pod")).isEqualTo("ee467bea5af28adb");
  }

  @Test
  void patchBulkExecutions() {
    wireMockServer.stubFor(
        patch(urlPathEqualTo("/lm/executions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                           "executions": [
                             {
                               "id": "e00ff1560daa8a86",
                               "message": "Execution modification scheduled"
                             }
                           ]
                         }
                        """)));

    final AiExecutionBulkModificationRequest executionBulkModificationRequest =
        AiExecutionBulkModificationRequest.create()
            .executions(
                Set.of(
                    AiExecutionModificationRequestWithIdentifier.create()
                        .id("e00ff1560daa8a86")
                        .targetStatus(
                            AiExecutionModificationRequestWithIdentifier.TargetStatusEnum
                                .STOPPED)));
    final AiExecutionBulkModificationResponse executionBulkModificationResponse =
        new ExecutionApi(getClient(destination))
            .executionBatchModify("default", executionBulkModificationRequest);

    assertThat(executionBulkModificationResponse).isNotNull();
    assertThat(executionBulkModificationResponse.getExecutions().size()).isEqualTo(1);

    AiExecutionModificationResponseListInner executionModificationResponseListInner =
        executionBulkModificationResponse.getExecutions().get(0);

    assertThat(executionModificationResponseListInner.getId()).isEqualTo("e00ff1560daa8a86");
    assertThat(executionModificationResponseListInner.getMessage())
        .isEqualTo("Execution modification scheduled");

    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/lm/executions"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "executions": [
                        {
                          "id": "e00ff1560daa8a86",
                          "targetStatus": "STOPPED"
                        }
                      ]
                    }
                    """)));
  }
}
