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
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentBulkModificationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentCreationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequest;
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequestWithIdentifier;
import com.sap.ai.sdk.core.client.model.AiDeploymentResponseWithDetails;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.client.model.AiDeploymentTargetStatus;
import com.sap.ai.sdk.core.client.model.AiExecutionStatus;
import com.sap.ai.sdk.core.client.model.RTALogCommonResultItem;
import java.util.Map;
import java.util.Set;
import lombok.val;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
class DeploymentUnitTest extends WireMockTestServer {
  @Test
  void getDeployments() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
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
                              "id": "d889e3a61050c085",
                              "deploymentUrl": "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/d889e3a61050c085",
                              "configurationId": "7652a231-ba9b-4fcc-b473-2c355cb21b61",
                              "configurationName": "gpt-4-32k",
                              "executableId": null,
                              "scenarioId": "foundation-models",
                              "status": "RUNNING",
                              "statusMessage": null,
                              "targetStatus": "RUNNING",
                              "lastOperation": "CREATE",
                              "latestRunningConfigurationId": "7652a231-ba9b-4fcc-b473-2c355cb21b61",
                              "ttl": null,
                              "details": {
                                "scaling": {
                                  "backendDetails": {}
                                },
                                "resources": {
                                  "backendDetails": {
                                    "model": {
                                      "name": "gpt-4-32k",
                                      "version": "latest"
                                    }
                                  },
                                  "backend_details": {
                                    "model": {
                                      "name": "gpt-4-32k",
                                      "version": "latest"
                                    }
                                  }
                                }
                              },
                              "createdAt": "2024-09-20T11:31:24Z",
                              "modifiedAt": "2024-10-30T13:36:38Z",
                              "submissionTime": "2024-09-20T11:31:38Z",
                              "startTime": "2024-09-20T11:32:42Z",
                              "completionTime": null
                            }
                          ]
                        }
                        """)));

    val deploymentList = new DeploymentApi(aiCoreService).query("default");

    assertThat(deploymentList).isNotNull();
    assertThat(deploymentList.getCount()).isEqualTo(1);
    assertThat(deploymentList.getResources()).hasSize(1);

    val deployment = deploymentList.getResources().get(0);

    assertThat(deployment.getConfigurationId()).isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(deployment.getConfigurationName()).isEqualTo("gpt-4-32k");
    assertThat(deployment.getCreatedAt()).isEqualTo("2024-09-20T11:31:24Z");
    assertThat(deployment.getDeploymentUrl())
        .isEqualTo(
            "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/d889e3a61050c085");
    val expected = Map.of("model", Map.of("name", "gpt-4-32k", "version", "latest"));
    assertThat(deployment.getDetails().getResources().getBackendDetails()).isEqualTo(expected);
    assertThat(deployment.getDetails().getScaling().getBackendDetails()).isEqualTo(Map.of());
    assertThat(deployment.getId()).isEqualTo("d889e3a61050c085");
    assertThat(deployment.getLastOperation()).isEqualTo(AiDeployment.LastOperationEnum.CREATE);
    assertThat(deployment.getLatestRunningConfigurationId())
        .isEqualTo("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    assertThat(deployment.getModifiedAt()).isEqualTo("2024-10-30T13:36:38Z");
    assertThat(deployment.getStartTime()).isEqualTo("2024-09-20T11:32:42Z");
    assertThat(deployment.getStatus()).isEqualTo(AiDeploymentStatus.RUNNING);
    assertThat(deployment.getSubmissionTime()).isEqualTo("2024-09-20T11:31:38Z");
    assertThat(deployment.getTargetStatus()).isEqualTo(AiDeployment.TargetStatusEnum.RUNNING);
  }

  @Test
  void postDeployment() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/v2/lm/deployments"))
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

    val deploymentCreationRequest =
        AiDeploymentCreationRequest.create()
            .configurationId("7652a231-ba9b-4fcc-b473-2c355cb21b61");
    val deployment = new DeploymentApi(aiCoreService).create("default", deploymentCreationRequest);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getDeploymentUrl()).isEmpty();
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    assertThat(deployment.getMessage()).isEqualTo("AiDeployment scheduled.");
    assertThat(deployment.getStatus()).isEqualTo(AiExecutionStatus.UNKNOWN_DEFAULT_OPEN_API);

    wireMockServer.verify(
        postRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
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
        patch(urlPathEqualTo("/v2/lm/deployments/d19b998f347341aa"))
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

    val configModification =
        AiDeploymentModificationRequest.create().targetStatus(AiDeploymentTargetStatus.STOPPED);
    val deployment =
        new DeploymentApi(aiCoreService).modify("default", "d19b998f347341aa", configModification);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    assertThat(deployment.getMessage()).isEqualTo("AiDeployment modification scheduled");

    // verify that null fields are absent from the sent request
    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/v2/lm/deployments/d19b998f347341aa"))
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
        delete(urlPathEqualTo("/v2/lm/deployments/d5b764fe55b3e87c"))
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

    val deployment = new DeploymentApi(aiCoreService).delete("default", "d5b764fe55b3e87c");

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d5b764fe55b3e87c");
    // targetStatus is not in the generated client
    assertThat(deployment.getCustomField("targetStatus")).isEqualTo("DELETED");
  }

  @Test
  void getDeploymentById() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments/db1d64d9f06be467"))
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
                               "backendDetails": {},
                               "backend_details": {}
                             },
                             "scaling": {
                               "backendDetails": {},
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

    val deployment = new DeploymentApi(aiCoreService).get("default", "db1d64d9f06be467");

    assertThat(deployment).isNotNull();
    assertThat(deployment.getConfigurationId()).isEqualTo("dd80625e-ad86-426a-b1a7-1494c083428f");
    assertThat(deployment.getConfigurationName()).isEqualTo("orchestration");
    assertThat(deployment.getCreatedAt()).isEqualTo("2024-08-05T16:17:29Z");
    assertThat(deployment.getDeploymentUrl())
        .isEqualTo(
            "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/db1d64d9f06be467");
    assertThat(deployment.getDetails().getResources().getBackendDetails()).isEqualTo(Map.of());
    assertThat(deployment.getDetails().getResources().getCustomField("backend_details"))
        .isEqualTo(Map.of());
    assertThat(deployment.getDetails().getScaling().getBackendDetails()).isEqualTo(Map.of());
    assertThat(deployment.getDetails().getScaling().getCustomField("backend_details"))
        .isEqualTo(Map.of());
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
        patch(urlPathEqualTo("/v2/lm/deployments/d03050a2ab7055cc"))
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

    val configModification =
        AiDeploymentModificationRequest.create()
            .configurationId("6ff6cb80-87db-45f0-b718-4e1d96e66332");
    val deployment =
        new DeploymentApi(aiCoreService).modify("default", "d03050a2ab7055cc", configModification);

    assertThat(deployment).isNotNull();
    assertThat(deployment.getId()).isEqualTo("d03050a2ab7055cc");
    assertThat(deployment.getMessage()).isEqualTo("Deployment modification scheduled");

    // verify that null fields are absent from the sent request
    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/v2/lm/deployments/d03050a2ab7055cc"))
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
        get(urlPathEqualTo("/v2/lm/deployments/$count"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody("1")));

    val count = new DeploymentApi(aiCoreService).count("default");

    assertThat(count).isEqualTo(1);
  }

  @Test
  void getDeploymentLogs() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments/d19b998f347341aa/logs"))
            // TODO: The spec does not define the header
            // .withHeader("AI-Resource-Group", equalTo("default"))
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

    val logs = new DeploymentApi(aiCoreService).getLogs("d19b998f347341aa");

    assertThat(logs).isNotNull();
    assertThat(logs.getData().getResult()).hasSize(1);

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
        patch(urlPathEqualTo("/v2/lm/deployments"))
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

    val bulkModificationRequest =
        AiDeploymentBulkModificationRequest.create()
            .deployments(
                Set.of(
                    AiDeploymentModificationRequestWithIdentifier.create()
                        .id("d1e736f50d15f357")
                        .targetStatus(
                            AiDeploymentModificationRequestWithIdentifier.TargetStatusEnum
                                .STOPPED)));
    val bulkModificationResponse =
        new DeploymentApi(aiCoreService).batchModify("default", bulkModificationRequest);

    assertThat(bulkModificationResponse).isNotNull();
    assertThat(bulkModificationResponse.getDeployments()).hasSize(1);

    val modificationResponseListInner = bulkModificationResponse.getDeployments().get(0);

    assertThat(modificationResponseListInner.getId()).isEqualTo("d1e736f50d15f357");
    assertThat(modificationResponseListInner.getMessage())
        .isEqualTo("Deployment modification scheduled");

    wireMockServer.verify(
        patchRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
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
