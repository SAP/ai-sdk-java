package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.tabular.generated.orchestration.model.CreateTARequest.TypeEnum.PARQUET;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.DefinitionType.DOCUMENT;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationCreateRequest.TypeEnum.HDL;
import static com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionStrategyEnum.RANDOM;
import static com.sap.ai.sdk.tabular.generated.predict.model.TaskTypeEnum.CLASSIFICATION;

import com.sap.ai.sdk.tabular.TabularClient;
import com.sap.ai.sdk.tabular.generated.orchestration.client.DataDestinationsApi;
import com.sap.ai.sdk.tabular.generated.orchestration.client.ScenarioConfigurationManagerApi;
import com.sap.ai.sdk.tabular.generated.orchestration.client.TabularArtifactsApi;
import com.sap.ai.sdk.tabular.generated.orchestration.model.AsyncCreateDataDestinationResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CreateScenarioConfiguration;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CreateTARequest;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CreateTARequestCsnMetadata;
import com.sap.ai.sdk.tabular.generated.orchestration.model.DocumentDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetScenarioConfigurations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.HDLConnectionConfig;
import com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationCreateRequest;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationNameObject;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactConfig;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactListResponse;
import com.sap.ai.sdk.tabular.generated.predict.client.PredictApi;
import com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictRequest;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictResponse;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.StrategyConfigs;
import com.sap.ai.sdk.tabular.generated.predict.model.TFMEnum;
import com.sap.ai.sdk.tabular.generated.predict.model.TargetColumn;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * Context Registry is a service for managing tabular data contexts for AI applications. It provides
 * capabilities to register data destinations (object stores and data sharing platforms), create
 * tabular artifacts from various file formats (CSV, Parquet, Delta), and configure scenarios for
 * context selection in AI-driven business solutions.
 */
@Service
public class TabularService {
  static final DataDestinationsApi DATA_DESTINATIONS_CLIENT =
      new TabularClient().dataDestinations();
  static final TabularArtifactsApi TABULAR_ARTIFACTS_CLIENT =
      new TabularClient().tabularArtifacts();
  static final ScenarioConfigurationManagerApi SCENARIO_CONFIG_CLIENT =
      new TabularClient().scenarioConfiguration();
  static final PredictApi PREDICT_CLIENT = new TabularClient().predict();

  static final String resourceGroup = "default";
  static final String dataDestinationName = "ai-sdk-hdl-destination";
  static final String artifactName = "product-artifact-lowercase";
  static final String artifactPath = "/data/product_data_hana_lowercase.parquet";
  static final String scenarioConfigName = "product-prediction-scenario-lowercase";

  /**
   * Manage data destinations (S3 Bucket, Google Cloud Storage, Hana Data lake) for unified data
   * source integration.
   */
  public static class DataDestinationService {

    /**
     * Get all data destinations for the default resource group.
     *
     * @return The list of data destinations.
     */
    @Nonnull
    public GetDataDestinations getAllDataDestinations() {
      return DATA_DESTINATIONS_CLIENT.getAllDataDestinations(resourceGroup);
    }

    /**
     * Create a new data destination for Hana Data Lake.
     *
     * @return The response of the data destination creation request.
     */
    @Nonnull
    public AsyncCreateDataDestinationResponse createHanaDataLakeDataDestination() {
      val request =
          HDLDataDestinationCreateRequest.create()
              .type(HDL)
              .config(
                  HDLConnectionConfig.create()
                      .host("123-456-789-abc-def123.files.hdl.prod-eu12.hanacloud.ondemand.com"))
              .description("Hana Data lake data destination for AI Core SDK");
      return DATA_DESTINATIONS_CLIENT.createUpdateDataDestination(
          resourceGroup, dataDestinationName, request);
    }
  }

  /** Manage tabular artifacts for structured files from data-destinations. */
  public static class ArtifactService {

    /**
     * Get all tabular artifacts for the default resource group.
     *
     * @return The list of tabular artifacts.
     */
    @Nonnull
    public TabularArtifactListResponse getAllArtifacts() {
      return TABULAR_ARTIFACTS_CLIENT.getAllTabularArtifacts(resourceGroup);
    }

    /**
     * Create a new tabular artifact from a Parquet file in the specified data destination.
     *
     * @return The response of the tabular artifact creation request.
     */
    @Nonnull
    public ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response createArtifact() {
      val productEntityElements =
          Map.of(
              "product", Map.of("type", "cds.String"),
              "price", Map.of("type", "cds.Double"),
              "date", Map.of("type", "cds.String"),
              "id", Map.of("type", "cds.String"),
              "salesgroup", Map.of("type", "cds.String"));
      val definitions =
          Map.of(
              "definitions",
              Map.of("ProductEntity", Map.of("kind", "entity", "elements", productEntityElements)));
      val request =
          CreateTARequest.create()
              .dataDestinationName(dataDestinationName)
              .type(PARQUET)
              .path(artifactPath)
              .csnMetadata(
                  CreateTARequestCsnMetadata.create()
                      .definition(
                          DocumentDefinition.create()
                              .definitionType(DOCUMENT)
                              .document(definitions))
                      .entityName("ProductEntity")
                      .selectedColumns(productEntityElements.keySet()));
      return TABULAR_ARTIFACTS_CLIENT.createTabularArtifact(resourceGroup, artifactName, request);
    }
  }

  /** Manage scenario configurations for context selection. */
  public static class ScenarioConfigurationService {

    /**
     * Get all scenario configurations for the default resource group.
     *
     * @return The list of scenario configurations.
     */
    @Nonnull
    public GetScenarioConfigurations getAllScenarioConfigurations() {
      return SCENARIO_CONFIG_CLIENT.getAllScenarioConfigurations(resourceGroup);
    }

    /**
     * Create a new scenario configuration for product prediction using the specified tabular
     * artifact.
     *
     * @return The response of the scenario configuration creation request.
     */
    @Nonnull
    public ScenarioConfigurationNameObject createScenarioConfiguration() {
      val request =
          CreateScenarioConfiguration.create()
              .tabularArtifacts(TabularArtifactConfig.create().name(artifactName))
              .description("Sample scenario configuration for product prediction");
      return SCENARIO_CONFIG_CLIENT.createScenarioConfiguration(
          resourceGroup, scenarioConfigName, request);
    }
  }

  /** Make predictions for tabular data using a deployed Tabular Foundation Model. */
  public static class PredictionService {

    /**
     * Run a prediction using a running tabular-orchestration deployment.
     *
     * @return The prediction response.
     */
    @Nonnull
    public PredictResponse predict() {
      val request =
          PredictRequest.create()
              .modelName(TFMEnum._1_5)
              .scenarioConfigName(scenarioConfigName)
              .predictionConfig(
                  PredictionConfig.create()
                      .targetColumns(
                          TargetColumn.create().name("salesgroup").taskType(CLASSIFICATION)))
              .contextSelectionConfig(
                  ContextSelectionConfig.create()
                      .numRows(3)
                      .strategy(RANDOM)
                      .strategyConfigs(
                          StrategyConfigs.create().indexColumn("id").deterministic(true)))
              .rows(
                  List.of(
                      Map.of(
                          "product", "Desktop Computer",
                          "price", 921.5,
                          "date", "2024-12-02",
                          "id", "42",
                          "salesgroup", "[PREDICT]"),
                      Map.of(
                          "product", "Macbook",
                          "price", 1220.99,
                          "date", "2026-01-31",
                          "id", "99",
                          "salesgroup", "[PREDICT]"),
                      Map.of(
                          "product", "Office Desk",
                          "price", 750.5,
                          "date", "2024-12-05",
                          "id", "689",
                          "salesgroup", "[PREDICT]")))
              .modelConfig(Map.of());
      return PREDICT_CLIENT.predict(request);
    }
  }
}
