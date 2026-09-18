package com.sap.ai.sdk.context.registry;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.context.registry.generated.client.DataDestinationsApi;
import com.sap.ai.sdk.context.registry.generated.client.ScenarioConfigurationManagerApi;
import com.sap.ai.sdk.context.registry.generated.client.TabularArtifactsApi;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Tolerate;
import lombok.val;

/**
 * Service class for the Tabular Orchestration APIs.
 *
 * @since 1.26.0
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(value = AccessLevel.PROTECTED)
@Beta
public class ContextRegistryClient {
  @Nonnull private final AiCoreService service;
  @Nonnull private final String basePath;
  @Nonnull private final List<Header> customHeaders = new ArrayList<>();

  static final String DEFAULT_BASE_PATH = "/v2/tcr/";

  /** Default constructor. */
  @Tolerate
  public ContextRegistryClient() {
    this(new AiCoreService());
  }

  /**
   * Constructor with custom AI Core service instance.
   *
   * @param service The instance of AI Core service
   */
  public ContextRegistryClient(final @Nonnull AiCoreService service) {
    this(service, DEFAULT_BASE_PATH);
  }

  /**
   * Get the Data Destinations API.
   *
   * @return The Data Destinations API.
   */
  @Nonnull
  public DataDestinationsApi dataDestinations() {
    return new DataDestinationsApi(getOrchestrationClient());
  }

  /**
   * Get the Tabular Artifacts API.
   *
   * @return The Tabular Artifacts API.
   */
  @Nonnull
  public TabularArtifactsApi tabularArtifacts() {
    return new TabularArtifactsApi(getOrchestrationClient());
  }

  /**
   * Get the Scenario Configuration Manager API.
   *
   * @return The Scenario Configuration Manager API.
   */
  @Nonnull
  public ScenarioConfigurationManagerApi scenarioConfiguration() {
    return new ScenarioConfigurationManagerApi(getOrchestrationClient());
  }

  /**
   * Create a new Tabular client with a custom header added to every call made with this client
   *
   * @param key the key of the custom header to add
   * @param value the value of the custom header to add
   * @return a new client.
   */
  @Nonnull
  public ContextRegistryClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new ContextRegistryClient(this.service, this.basePath);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }

  @Nonnull
  private ApiClient getOrchestrationClient() {
    final HttpDestination base = getService().getBaseDestination();
    final URI rootUri = base.getUri().resolve(getBasePath());
    val destination =
        DefaultHttpDestination.fromDestination(base).uri(rootUri).headers(customHeaders).build();
    return ApiClient.create(destination);
  }
}
