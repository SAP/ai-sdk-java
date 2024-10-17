package com.sap.ai.sdk.core;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.sap.ai.sdk.core.client.WireMockTestServer;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTest extends WireMockTestServer {

  private final DeploymentCache cacheUnderTest = new DeploymentCache();

  @BeforeEach
  void setupCache() {
    wireMockServer.resetRequests();
  }

  private static void stubGPT4() {
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
  }

  private static void stubEmpty() {
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
                          "count": 0,
                          "resources": []
                        }
                        """)));
  }

  /**
   * The user creates a deployment.
   *
   * <p>The user uses the OpenAI client and specifies only the name "foo".
   *
   * <p>The user repeatedly uses the API in the same way
   *
   * <p>Simple case, the deployment should be served from cache as much as possible
   */
  @Test
  void newDeployment() {
    stubGPT4();
    cacheUnderTest.loadCache(client, "default");

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void clearCache() {
    stubGPT4();
    cacheUnderTest.loadCache(client, "default");

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.clearCache();

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    // the deployment is not in the cache anymore, so we need to query it again
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  /**
   * The user creates a deployment after starting with an empty cache.
   *
   * <p>The user uses the OpenAI client and specifies only the name "foo".
   *
   * <p>The user repeatedly uses the API in the same way
   *
   * <p>Simple case, the deployment should be served from cache as much as possible
   */
  @Test
  void newDeploymentAfterReset() {
    stubEmpty();
    cacheUnderTest.loadCache(client, "default");
    stubGPT4();

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    // 1 reset empty and 1 cache miss
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, "default", "gpt-4-32k");
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }
}
