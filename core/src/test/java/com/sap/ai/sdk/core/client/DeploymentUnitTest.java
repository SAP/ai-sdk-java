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

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentBulkModificationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentBulkModificationResponse;
import com.sap.ai.sdk.core.client.model.AiDeploymentCreationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentCreationResponse;
import com.sap.ai.sdk.core.client.model.AiDeploymentDeletionResponse;
import com.sap.ai.sdk.core.client.model.AiDeploymentList;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequestWithIdentifier;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationResponse;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationResponseListInner;
import com.sap.ai.sdk.core.client.model.AiDeploymentResponseWithDetails;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.client.model.AiDeploymentTargetStatus;
import com.sap.ai.sdk.core.client.model.AiExecutionStatus;
import com.sap.ai.sdk.core.client.model.RTALogCommonResultItem;
import java.util.Map;
import java.util.Set;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class DeploymentUnitTest extends WireMockTestServer {
  @Test
  void getDeployments() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/deployments"))
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
                              "configurationId": "7652a231-ba9b-4fcc-b473-2c355cb21b61",
                              "configurationName": "gpt-4-32k",
                              "createdAt": "2024-04-17T15:19:53Z",
                              "deploymentUrl": "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/d19b998f347341aa",
                              "details": {
                                "resources": {
                                  "backend_details": {
                                    "model": {
                                      "name": "gpt-4-32k",
                                      "version": "latest"
                                    }
                                  }
                                },
                                "scaling": {
                                  "backend_details": {}
                                }
                              },
                              "id": "d19b998f347341aa",
                              "lastOperation": "CREATE",
                              "latestRunningConfigurationId": "7652a231-ba9b-4fcc-b473-2c355cb21b61",
                              "modifiedAt": "2024-05-07T13:05:45Z",
                              "scenarioId": "foundation-models",
                              "startTime": "2024-04-17T15:21:15Z",
                              "status": "RUNNING",
                              "submissionTime": "2024-04-17T15:20:11Z",
                              "targetStatus": "RUNNING"
                            }
                          ]
                        }
                        """)));

    final AiDeploymentList deploymentList =
        new DeploymentApi(getClient(destination)).deploymentQuery("default");

    assertThat(deploymentList).isNotNull();
    assertThat(deploymentList.getCount()).isEqualTo(1);
    assertThat(deploymentList.getResources().size()).isEqualTo(1);

    final AiDeployment deployment = deploymentList.getResources().get(0);

    assertThat(deployment.getConfigurationId()).isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(deployment.getConfigurationName()).isEqualTo("gpt-4-32k");
    assertThat(deployment.getCreatedAt()).isEqualTo("2024-04-17T15:19:53Z");
    assertThat(deployment.getDeploymentUrl())
        .isEqualTo(
            "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/d19b998f347341aa");
    // Response contains key "backend_details" while spec (mistakenly) defines key "backendDetails".
    final var expected = Map.of("model", Map.of("name", "gpt-4-32k", "version", "latest"));
    assertThat(deployment.getDetails().getResources().getCustomField("backend_details"))
        .isEqualTo(expected);
    assertThat(
            deployment.getDetails().getScaling().getCustomFieldNames().contains("backend_details"))
        .isTrue();
    assertThat(deployment.getId()).isEqualTo("d19b998f347341aa");
    assertThat(deployment.getLastOperation()).isEqualTo(AiDeployment.LastOperationEnum.CREATE);
    assertThat(deployment.getLatestRunningConfigurationId())
        .isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(deployment.getModifiedAt()).isEqualTo("2024-05-07T13:05:45Z");
    assertThat(deployment.getStartTime()).isEqualTo("2024-04-17T15:21:15Z");
    assertThat(deployment.getStatus()).isEqualTo(AiDeploymentStatus.RUNNING);
    assertThat(deployment.getSubmissionTime()).isEqualTo("2024-04-17T15:20:11Z");
    assertThat(deployment.getTargetStatus()).isEqualTo(AiDeployment.TargetStatusEnum.RUNNING);
  }

  @Test
  void postDeployment() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "deploymentUrl": "",
                          "id": "d5b764fe55b3e87c",
                          "message": "AiDeployment scheduled.",
                          "status": "FOOBAR"
                        }
                        """)));

    final AiDeploymentCreationRequest deploymentCreationRequest =
        AiDeploymentCreationRequest.create()
            .configurationId("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    final AiDeploymentCreationResponse deployment =
        new DeploymentApi(getClient(destination))
            .deploymentCreate("default", deploymentCreationRequest);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getDeploymentUrl()).isEqualTo("");
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    assertThat(deployment.getMessage()).isEqualTo("AiDeployment scheduled.");
    assertThat(deployment.getStatus()).isEqualTo(AiExecutionStatus.UNKNOWN_DEFAULT_OPEN_API);

    wireMockServer.verify(
        postRequestedFor(urlPathEqualTo("/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                          {
                            "configurationId" : "7652a231-ba9b-4fcc-b473-2c355cb21b61"
                          }
                          """)));
  }

  @Test
  void patchDeploymentStatus() {
    wireMockServer.stubFor(
        patch(urlPathEqualTo("/lm/deployments/d19b998f347341aa"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "d5b764fe55b3e87c",
                          "message": "AiDeployment modification scheduled"
                        }
                        """)));

    final AiDeploymentModificationRequest configModification =
        AiDeploymentModificationRequest.create().targetStatus(AiDeploymentTargetStatus.STOPPED);
    final AiDeploymentModificationResponse deployment =
        new DeploymentApi(getClient(destination))
            .deploymentModify("default", "d19b998f347341aa", configModification);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    assertThat(deployment.getMessage()).isEqualTo("AiDeployment modification scheduled");

    // verify that null fields are absent from the sent request
    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/lm/deployments/d19b998f347341aa"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                          {
                            "targetStatus": "STOPPED"
                          }
                          """)));
  }

  @Test
  void deleteDeployment() {
    wireMockServer.stubFor(
        delete(urlPathEqualTo("/lm/deployments/d5b764fe55b3e87c"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "d5b764fe55b3e87c",
                          "message": "Deletion scheduled",
                          "targetStatus": "DELETED"
                        }
                        """)));

    final AiDeploymentDeletionResponse deployment =
        new DeploymentApi(getClient(destination)).deploymentDelete("default", "d5b764fe55b3e87c");

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    // targetStatus is not in the generated client
    assertThat(deployment.getCustomField("targetStatus")).isEqualTo("DELETED");
  }

  @Test
  void getDeploymentById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/deployments/db1d64d9f06be467"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                           "configurationId": "dd80625e-ad86-426a-b1a7-1494c083428f",
                           "configurationName": "orchestration",
                           "createdAt": "2024-08-05T16:17:29Z",
                           "deploymentUrl": "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/db1d64d9f06be467",
                           "details": {
                             "resources": {
                               "backend_details": {}
                             },
                             "scaling": {
                               "backend_details": {}
                             }
                           },
                           "id": "db1d64d9f06be467",
                           "lastOperation": "CREATE",
                           "latestRunningConfigurationId": "dd80625e-ad86-426a-b1a7-1494c083428f",
                           "modifiedAt": "2024-08-26T12:43:18Z",
                           "scenarioId": "orchestration",
                           "startTime": "2024-08-05T16:18:41Z",
                           "status": "RUNNING",
                           "submissionTime": "2024-08-05T16:17:40Z",
                           "targetStatus": "RUNNING"
                        }
                        """)));

    final AiDeploymentResponseWithDetails deployment =
        new DeploymentApi(getClient(destination)).deploymentGet("default", "db1d64d9f06be467");

    assertThat(deployment).isNotNull();
    assertThat(deployment.getConfigurationId()).isEqualTo("dd80625e-ad86-426a-b1a7-1494c083428f");
    assertThat(deployment.getConfigurationName()).isEqualTo("orchestration");
    assertThat(deployment.getCreatedAt()).isEqualTo("2024-08-05T16:17:29Z");
    assertThat(deployment.getDeploymentUrl())
        .isEqualTo(
            "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/db1d64d9f06be467");
    // Response contains key "backend_details" while spec (mistakenly) defines key "backendDetails".
    assertThat(
            deployment
                .getDetails()
                .getResources()
                .getCustomFieldNames()
                .contains("backend_details"))
        .isTrue();
    assertThat(
            deployment.getDetails().getScaling().getCustomFieldNames().contains("backend_details"))
        .isTrue();
    assertThat(deployment.getId()).isEqualTo("db1d64d9f06be467");
    assertThat(deployment.getLastOperation())
        .isEqualTo(AiDeploymentResponseWithDetails.LastOperationEnum.CREATE);
    assertThat(deployment.getLatestRunningConfigurationId())
        .isEqualTo("dd80625e-ad86-426a-b1a7-1494c083428f");
    assertThat(deployment.getModifiedAt()).isEqualTo("2024-08-26T12:43:18Z");
    assertThat(deployment.getStartTime()).isEqualTo("2024-08-05T16:18:41Z");
    assertThat(deployment.getStatus()).isEqualTo(AiDeploymentStatus.RUNNING);
    assertThat(deployment.getSubmissionTime()).isEqualTo("2024-08-05T16:17:40Z");
    assertThat(deployment.getTargetStatus())
        .isEqualTo(AiDeploymentResponseWithDetails.TargetStatusEnum.RUNNING);
  }

  @Test
  void patchDeploymentConfiguration() {
    wireMockServer.stubFor(
        patch(urlPathEqualTo("/lm/deployments/d03050a2ab7055cc"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "d03050a2ab7055cc",
                          "message": "Deployment modification scheduled"
                        }
                        """)));

    final AiDeploymentModificationRequest configModification =
        AiDeploymentModificationRequest.create()
            .configurationId("6ff6cb80-87db-45f0-b718-4e1d96e66332");
    final AiDeploymentModificationResponse deployment =
        new DeploymentApi(getClient(destination))
            .deploymentModify("default", "d03050a2ab7055cc", configModification);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d03050a2ab7055cc");
    assertThat(deployment.getMessage()).isEqualTo("Deployment modification scheduled");

    // verify that null fields are absent from the sent request
    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/lm/deployments/d03050a2ab7055cc"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                          {
                            "configurationId": "6ff6cb80-87db-45f0-b718-4e1d96e66332"
                          }
                          """)));
  }

  @Test
  void getDeploymentCount() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/deployments/$count"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody("""
                        1
                        """)));

    final int count = new DeploymentApi(getClient(destination)).deploymentCount("default");

    assertThat(count).isEqualTo(1);
  }

  @Test
  void getDeploymentLogs() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/deployments/d19b998f347341aa/logs"))
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
                                 "container": "storage-initializer",
                                 "msg": "INFO:root:Copying contents of s3://hcp-8b6797d5-fc66-4fde-bdcf-0800987e1837/i749902/e529e8bd58740bc9/classifier-model-output to local",
                                 "pod": "d058d4774af7edfb-predictor-00001-deployment-74fbb96d88-bqlqb",
                                 "timestamp": "2024-09-18T16:06:50.914532860+00:00"
                               }
                             ]
                           }
                         }
                        """)));

    final var logs =
        new DeploymentApi(getClient(destination).addDefaultHeader("Ai-Resource-Group", "default"))
            .kubesubmitV4DeploymentsGetLogs("d19b998f347341aa");

    assertThat(logs).isNotNull();
    assertThat(logs.getData().getResult().size()).isEqualTo(1);

    RTALogCommonResultItem rtaLogCommonResultItem = logs.getData().getResult().get(0);

    assertThat(rtaLogCommonResultItem.getTimestamp())
        .isEqualTo("2024-09-18T16:06:50.914532860+00:00");
    assertThat(rtaLogCommonResultItem.getMsg())
        .isEqualTo(
            "INFO:root:Copying contents of s3://hcp-8b6797d5-fc66-4fde-bdcf-0800987e1837/i749902/e529e8bd58740bc9/classifier-model-output to local");
    // `container` and `pod` are not defined in spec
    assertThat(rtaLogCommonResultItem.getCustomField("container")).isEqualTo("storage-initializer");
    assertThat(rtaLogCommonResultItem.getCustomField("pod"))
        .isEqualTo("d058d4774af7edfb-predictor-00001-deployment-74fbb96d88-bqlqb");
  }

  @Test
  void patchBulkDeployments() {
    wireMockServer.stubFor(
        patch(urlPathEqualTo("/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "deployments": [
                            {
                              "id": "d1e736f50d15f357",
                              "message": "Deployment modification scheduled"
                            }
                          ]
                        }
                        """)));

    final AiDeploymentBulkModificationRequest bulkModificationRequest =
        AiDeploymentBulkModificationRequest.create()
            .deployments(
                Set.of(
                    AiDeploymentModificationRequestWithIdentifier.create()
                        .id("d1e736f50d15f357")
                        .targetStatus(
                            AiDeploymentModificationRequestWithIdentifier.TargetStatusEnum
                                .STOPPED)));
    final AiDeploymentBulkModificationResponse bulkModificationResponse =
        new DeploymentApi(getClient(destination))
            .deploymentBatchModify("default", bulkModificationRequest);

    assertThat(bulkModificationResponse).isNotNull();
    assertThat(bulkModificationResponse.getDeployments()).hasSize(1);

    final AiDeploymentModificationResponseListInner modificationResponseListInner =
        bulkModificationResponse.getDeployments().get(0);

    assertThat(modificationResponseListInner.getId()).isEqualTo("d1e736f50d15f357");
    assertThat(modificationResponseListInner.getMessage())
        .isEqualTo("Deployment modification scheduled");

    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .withRequestBody(
                equalToJson(
                    """
                          {
                            "deployments": [
                              {
                                "id": "d1e736f50d15f357",
                                "targetStatus": "STOPPED"
                              }
                            ]
                          }
                          """)));
  }
}
