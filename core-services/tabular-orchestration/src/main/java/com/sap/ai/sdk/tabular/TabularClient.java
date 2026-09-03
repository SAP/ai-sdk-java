package com.sap.ai.sdk.tabular;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.tabular.generated.orchestration.client.DataDestinationsApi;
import com.sap.ai.sdk.tabular.generated.orchestration.client.ScenarioConfigurationManagerApi;
import com.sap.ai.sdk.tabular.generated.orchestration.client.TabularArtifactsApi;
import com.sap.ai.sdk.tabular.generated.predict.client.PredictApi;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public class TabularClient {
  @Nonnull private final AiCoreService service;
  @Nonnull private final String basePath;
  @Nonnull private final List<Header> customHeaders = new ArrayList<>();

  // Only set in tests, to bypass real deployment resolution for the Predict API.
  @Nullable private HttpDestination predictDestinationOverride;

  static final String DEFAULT_BASE_PATH = "/v2/tcr/";
  private static final String PREDICT_SCENARIO_ID = "tabular-orchestration";

  /** Default constructor. */
  @Tolerate
  public TabularClient() {
    this(new AiCoreService());
  }

  /**
   * Constructor with custom AI Core service instance.
   *
   * @param service The instance of AI Core service
   */
  public TabularClient(final @Nonnull AiCoreService service) {
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
   * Get the Predict API.
   *
   * @return The Predict API.
   */
  @Nonnull
  public PredictApi predict() {
    final HttpDestination inferenceDestination =
        predictDestinationOverride != null
            ? predictDestinationOverride
            : getService().getInferenceDestination().forScenario(PREDICT_SCENARIO_ID);
    val destination =
        DefaultHttpDestination.fromDestination(inferenceDestination).headers(customHeaders).build();
    return new PredictApi(ApiClient.create(destination));
  }

  /**
   * Create a new Tabular client with a custom header added to every call made with this client
   *
   * @param key the key of the custom header to add
   * @param value the value of the custom header to add
   * @return a new client.
   */
  @Nonnull
  public TabularClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new TabularClient(this.service, this.basePath);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }

  /**
   * Override the destination used for {@link #predict()}, bypassing deployment resolution.
   *
   * <p>This is intended for testing purposes only.
   *
   * @param destination The destination to use for Predict API calls.
   * @return this client, for chaining.
   */
  @Nonnull
  TabularClient withPredictDestination(@Nonnull final HttpDestination destination) {
    this.predictDestinationOverride = destination;
    return this;
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
