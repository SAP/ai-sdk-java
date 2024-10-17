package com.sap.ai.sdk.core;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.core.client.WireMockTestServer;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTest extends WireMockTestServer {

  private final DeploymentCache cacheUnderTest = new DeploymentCache();

  @BeforeEach
  void setupCache() {
    wireMockServer.resetRequests();
  }

  private static void stubGPT4(String resourceGroup) {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroup))
            .willReturn(
                aResponse()
                    .withBodyFile("GPT4DeploymentResponse.json")
                    .withHeader("Content-Type", "application/json")));
  }

  private static void stubEmpty(String resourceGroup) {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroup))
            .willReturn(
                aResponse()
                    .withBodyFile("emptyDeploymentResponse.json")
                    .withHeader("content-type", "application/json")));
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
    String resourceGroup = "default";
    stubGPT4(resourceGroup);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, "gpt-4-32k");
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, "gpt-4-32k");
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
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
    String resourceGroup = "default";
    stubEmpty(resourceGroup);
    cacheUnderTest.resetCache(client, resourceGroup);
    stubGPT4(resourceGroup);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, "gpt-4-32k");
    // 1 reset empty and 1 cache miss
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, "gpt-4-32k");
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void resourceGroupIsolation() {
    String resourceGroupA = "A";
    String resourceGroupB = "B";
    stubGPT4(resourceGroupA);
    stubGPT4(resourceGroupB);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroupA, "gpt-4-32k");
    wireMockServer.verify(
        1,
        getRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroupA)));
    wireMockServer.verify(
        0,
        getRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroupB)));
  }

  @Test
  void exceptionDeploymentNotFound() {
    String resourceGroup = "default";
    stubEmpty(resourceGroup);

    assertThatThrownBy(
            () -> cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, "gpt-4-32k"))
        .isExactlyInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("No running deployment found for model: gpt-4-32k");
  }

  @Test
  void resetCache() {
    String resourceGroup = "default";
    stubGPT4(resourceGroup);
    cacheUnderTest.resetCache(client, resourceGroup);
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    AiCoreService.resetCache(client, resourceGroup);
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }
}
