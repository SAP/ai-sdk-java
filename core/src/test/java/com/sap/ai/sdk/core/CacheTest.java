package com.sap.ai.sdk.core;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.core.client.WireMockTestServer;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentDetails;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.client.model.AiResourcesDetails;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import lombok.val;
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
    val resourceGroup = "default";
    stubGPT4(resourceGroup);

    val gpt4 = createAiModel("gpt-4-32k", null);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, gpt4);
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, gpt4);
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
    val resourceGroup = "default";
    stubEmpty(resourceGroup);
    cacheUnderTest.resetCache(client, resourceGroup);
    stubGPT4(resourceGroup);

    val gpt4 = createAiModel("gpt-4-32k", null);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, gpt4);
    // 1 reset empty and 1 cache miss
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, gpt4);
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void resourceGroupIsolation() {
    val resourceGroupA = "A";
    val resourceGroupB = "B";
    stubGPT4(resourceGroupA);
    stubGPT4(resourceGroupB);

    val gpt4 = createAiModel("gpt-4-32k", null);

    cacheUnderTest.getDeploymentIdByModel(client, resourceGroupA, gpt4);
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
    val resourceGroup = "default";
    stubEmpty(resourceGroup);

    val gpt4 = createAiModel("gpt-4-32k", null);

    assertThatThrownBy(() -> cacheUnderTest.getDeploymentIdByModel(client, resourceGroup, gpt4))
        .isExactlyInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("No running deployment found for model: gpt-4-32k");
  }

  @Test
  void resetCache() {
    val resourceGroup = "default";
    stubGPT4(resourceGroup);
    cacheUnderTest.resetCache(client, resourceGroup);
    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    val destination = DefaultHttpDestination.builder(wireMockServer.baseUrl()).build();
    new AiCoreService().withDestination(destination).reloadCachedDeployments(resourceGroup);
    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  public void isDeploymentOfModel() {
    // Create a target model
    val gpt4AnyVersion = createAiModel("gpt-4-32k", null);
    val gpt4Version1 = createAiModel("gpt-4-32k", "1.0");
    val gpt4VersionLatest = createAiModel("gpt-4-32k", "latest");

    // Create a deployment with a different model by version
    val model = Map.of("model", Map.of("name", "gpt-4-32k", "version", "latest"));
    val deployment =
        AiDeployment.create()
            .id("test-deployment")
            .configurationId("test-configuration")
            .status(AiDeploymentStatus.RUNNING)
            .createdAt(OffsetDateTime.parse("2024-01-22T17:57:23+00:00"))
            .modifiedAt(OffsetDateTime.parse("2024-02-08T08:41:23+00:00"));
    deployment.setDetails(AiDeploymentDetails.create().resources(AiResourcesDetails.create()));
    deployment.getDetails().getResources().setCustomField("backend_details", model);

    // Check if the deployment is of the target model
    assertThat(DeploymentCache.isDeploymentOfModel(gpt4AnyVersion, deployment)).isTrue();
    assertThat(DeploymentCache.isDeploymentOfModel(gpt4Version1, deployment)).isFalse();
    assertThat(DeploymentCache.isDeploymentOfModel(gpt4VersionLatest, deployment)).isTrue();
  }

  static AiModel createAiModel(String name, String version) {
    return new AiModel() {
      @Nonnull
      @Override
      public String name() {
        return name;
      }

      @Override
      public String version() {
        return version;
      }

      public String toString() {
        return name;
      }
    };
  }
}
