package com.sap.ai.sdk.core;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.AiCoreService.DEFAULT_RESOURCE_GROUP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.core.client.WireMockTestServer;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentDetails;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.client.model.AiResourcesDetails;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeploymentResolverTest extends WireMockTestServer {

  private static final String GPT_4 = "GPT4DeploymentResponse.json";
  private static final String EMPTY = "emptyDeploymentResponse.json";
  private static final AiModel GPT_4_Model = new TestModel("gpt-4-32k", null);

  private DeploymentResolver resolver;
  private Map<String, Set<AiDeployment>> cache;

  @BeforeEach
  void setup() {
    cache = new ConcurrentHashMap<>();
    resolver = new DeploymentResolver(WireMockTestServer.aiCoreService, cache);
  }

  @Test
  void testDeploymentsAreCached() {
    stubResponse(DEFAULT_RESOURCE_GROUP, GPT_4);

    resolver.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model);
    resolver.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model);

    assertThat(cache).containsOnlyKeys(DEFAULT_RESOURCE_GROUP);
    assertThat(cache.get(DEFAULT_RESOURCE_GROUP))
        .hasSize(1)
        .allMatch(deployment -> deployment.getId().equals("d19b998f347341aa"));

    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void testCacheReload() {
    assertThat(cache).isEmpty();

    stubResponse(DEFAULT_RESOURCE_GROUP, EMPTY);
    resolver.reloadDeployments(DEFAULT_RESOURCE_GROUP);
    assertThat(cache.get(DEFAULT_RESOURCE_GROUP)).isEmpty();

    stubResponse(DEFAULT_RESOURCE_GROUP, GPT_4);
    resolver.reloadDeployments(DEFAULT_RESOURCE_GROUP);
    assertThat(cache.get(DEFAULT_RESOURCE_GROUP)).hasSize(1);

    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void testCacheMissCausesReload() {
    stubResponse(DEFAULT_RESOURCE_GROUP, EMPTY);
    resolver.reloadDeployments(DEFAULT_RESOURCE_GROUP);

    assertThatThrownBy(() -> resolver.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model))
        .isExactlyInstanceOf(DeploymentResolutionException.class)
        .hasMessageContaining("No running deployment found for model: gpt-4-32k");

    wireMockServer.verify(2, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));
  }

  @Test
  void resourceGroupIsolation() {
    val resourceGroupA = "A";
    val resourceGroupB = "B";
    stubResponse(resourceGroupA, GPT_4);
    stubResponse(resourceGroupB, EMPTY);

    resolver.reloadDeployments(resourceGroupA);
    resolver.reloadDeployments(resourceGroupB);

    assertThat(cache).containsOnlyKeys(resourceGroupA, resourceGroupB);
    assertThat(cache.get(resourceGroupA)).hasSize(1);
    assertThat(cache.get(resourceGroupB)).isEmpty();

    wireMockServer.verify(
        1,
        getRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroupA)));
    wireMockServer.verify(
        1,
        getRequestedFor(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroupB)));
  }

  @Test
  void testDeploymentCacheIsGlobalByDefault() {
    val resolver1 = new DeploymentResolver(WireMockTestServer.aiCoreService);
    val resolver2 = new DeploymentResolver(WireMockTestServer.aiCoreService);

    stubResponse(DEFAULT_RESOURCE_GROUP, GPT_4);
    resolver1.reloadDeployments(DEFAULT_RESOURCE_GROUP);

    assertThat(resolver1.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model))
        .isSameAs(resolver2.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model));

    wireMockServer.verify(1, getRequestedFor(urlPathEqualTo("/v2/lm/deployments")));

    resolver2.reloadDeployments(DEFAULT_RESOURCE_GROUP);
  }

  @Test
  void testErrorHandling() {
    wireMockServer.stubFor(get(anyUrl()).willReturn(badRequest()));

    assertThatThrownBy(() -> resolver.reloadDeployments(DEFAULT_RESOURCE_GROUP))
        .isExactlyInstanceOf(DeploymentResolutionException.class);

    assertThatThrownBy(() -> resolver.getDeploymentIdByModel(DEFAULT_RESOURCE_GROUP, GPT_4_Model))
        .isExactlyInstanceOf(DeploymentResolutionException.class);

    var serviceWithBrokenCredentials =
        new AiCoreService(
            () -> {
              throw new DestinationAccessException();
            });
    resolver = new DeploymentResolver(serviceWithBrokenCredentials, cache);

    assertThatThrownBy(() -> resolver.reloadDeployments(DEFAULT_RESOURCE_GROUP))
        .isExactlyInstanceOf(DeploymentResolutionException.class)
        .hasCauseInstanceOf(DestinationAccessException.class);
  }

  @Test
  void testIsDeploymentOfModel() {
    // Create a target model
    val gpt4AnyVersion = new TestModel("gpt-4-32k", null);
    val gpt4Version1 = new TestModel("gpt-4-32k", "1.0");
    val gpt4VersionLatest = new TestModel("gpt-4-32k", "latest");

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
    deployment.getDetails().getResources().setBackendDetails(model);

    // Check if the deployment is of the target model
    assertThat(DeploymentResolver.isDeploymentOfModel(gpt4AnyVersion, deployment)).isTrue();
    assertThat(DeploymentResolver.isDeploymentOfModel(gpt4Version1, deployment)).isFalse();
    assertThat(DeploymentResolver.isDeploymentOfModel(gpt4VersionLatest, deployment)).isTrue();
  }

  private record TestModel(String name, String version) implements AiModel {}

  private static void stubResponse(String resourceGroup, String fileName) {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo(resourceGroup))
            .willReturn(
                aResponse().withBodyFile(fileName).withHeader("content-type", "application/json")));
  }
}
