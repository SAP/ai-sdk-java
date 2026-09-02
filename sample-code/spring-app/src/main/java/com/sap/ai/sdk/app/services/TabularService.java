package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.tabular.generated.model.CreateTARequest.TypeEnum.PARQUET;
import static com.sap.ai.sdk.tabular.generated.model.DefinitionType.DOCUMENT;
import static com.sap.ai.sdk.tabular.generated.model.GCSDataDestinationCreateRequest.TypeEnum.GCS;
import static com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationCreateRequest.TypeEnum.HDL;
import static com.sap.ai.sdk.tabular.generated.model.S3DataDestinationCreateRequest.TypeEnum.S3;
import static com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionStrategyEnum.RANDOM;
import static com.sap.ai.sdk.tabular.generated.predict.model.TaskTypeEnum.CLASSIFICATION;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.tabular.generated.client.DataDestinationsApi;
import com.sap.ai.sdk.tabular.generated.client.ScenarioConfigurationManagerApi;
import com.sap.ai.sdk.tabular.generated.client.TabularArtifactsApi;
import com.sap.ai.sdk.tabular.generated.model.AsyncCreateDataDestinationResponse;
import com.sap.ai.sdk.tabular.generated.model.ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response;
import com.sap.ai.sdk.tabular.generated.model.CreateScenarioConfiguration;
import com.sap.ai.sdk.tabular.generated.model.CreateTARequest;
import com.sap.ai.sdk.tabular.generated.model.CreateTARequestCsnMetadata;
import com.sap.ai.sdk.tabular.generated.model.DocumentDefinition;
import com.sap.ai.sdk.tabular.generated.model.GCSConnectionConfig;
import com.sap.ai.sdk.tabular.generated.model.GCSDataDestinationCreateRequest;
import com.sap.ai.sdk.tabular.generated.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.model.GetScenarioConfigurations;
import com.sap.ai.sdk.tabular.generated.model.HDLConnectionConfig;
import com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationCreateRequest;
import com.sap.ai.sdk.tabular.generated.model.S3ConnectionConfig;
import com.sap.ai.sdk.tabular.generated.model.S3DataDestinationCreateRequest;
import com.sap.ai.sdk.tabular.generated.model.ScenarioConfigurationNameObject;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactConfig;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactListResponse;
import com.sap.ai.sdk.tabular.generated.predict.client.PredictApi;
import com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictRequest;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictResponse;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.StrategyConfigs;
import com.sap.ai.sdk.tabular.generated.predict.model.TFMEnum;
import com.sap.ai.sdk.tabular.generated.predict.model.TargetColumn;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.net.URI;
import java.util.List;
import java.util.Map;
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
      new DataDestinationsApi(buildDestination());
  static final TabularArtifactsApi TABULAR_ARTIFACTS_CLIENT =
      new TabularArtifactsApi(buildDestination());
  static final ScenarioConfigurationManagerApi SCENARIO_CONFIG_CLIENT =
      new ScenarioConfigurationManagerApi(buildDestination());
  static final PredictApi PREDICT_CLIENT =
      new PredictApi(
          new AiCoreService().getInferenceDestination().forScenario("tabular-orchestration"));

  private static HttpDestination buildDestination() {
    final HttpDestination base = new AiCoreService().getBaseDestination();
    final URI rootUri = base.getUri().resolve("/v2/tcr/");
    return DefaultHttpDestination.fromDestination(base).uri(rootUri).build();
  }

  static final String resourceGroup = "default";
  static final String dataDestinationName = "ai-sdk-hdl-destination";
  static final String artifactName = "ai-sdk-tabular-artifact";
  static final String artifactPath = "/data/product_data_hana_lowercase.parquet";
  static final String scenarioConfigName = "product-prediction-scenario-lowercase";

  /** Manage data destinations (S3, Azure, GCP, HDL) for unified data source integration. */
  public static class DataDestinationService {

    public GetDataDestinations getAllDataDestinations() {
      return DATA_DESTINATIONS_CLIENT.getAllDataDestinations(resourceGroup);
    }

    public AsyncCreateDataDestinationResponse createHDLDataDestination() {
      val request =
          HDLDataDestinationCreateRequest.create()
              .type(HDL)
              .config(HDLConnectionConfig.create().host(""))
              .description("HDL data destination for AI Core SDK");
      return DATA_DESTINATIONS_CLIENT.createUpdateDataDestination(
          resourceGroup, dataDestinationName, request);
    }

    public AsyncCreateDataDestinationResponse createS3DataDestination() {
      val request =
          S3DataDestinationCreateRequest.create()
              .type(S3)
              .config(
                  S3ConnectionConfig.create()
                      .bucket("")
                      .region("")
                      .accessKeyId("")
                      .secretAccessKey(""))
              .description("S3 data destination for AI Core SDK");
      return DATA_DESTINATIONS_CLIENT.createUpdateDataDestination(
          resourceGroup, dataDestinationName, request);
    }

    public AsyncCreateDataDestinationResponse createGCSDataDestination() {
      val request =
          GCSDataDestinationCreateRequest.create()
              .type(GCS)
              .config(
                  GCSConnectionConfig.create()
                      .bucket("")
                      .base64EncodedPrivateKeyData("".getBytes()))
              .description("GCS data destination for AI Core SDK");
      return DATA_DESTINATIONS_CLIENT.createUpdateDataDestination(
          resourceGroup, dataDestinationName, request);
    }
  }

  /** Manage tabular artifacts for structured files from data-destinations. */
  public static class ArtifactService {

    public TabularArtifactListResponse getAllArtifacts() {
      return TABULAR_ARTIFACTS_CLIENT.getAllTabularArtifacts(resourceGroup);
    }

    public ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response createArtifact() {
      val productEntityElements =
          Map.of(
              "PRODUCT", Map.of("type", "cds.String"),
              "PRICE", Map.of("type", "cds.Double"),
              "PRODUCTION_DATE", Map.of("type", "cds.String"),
              "__row_idx__", Map.of("type", "cds.String"),
              "SALESGROUP", Map.of("type", "cds.String"));
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

    public GetScenarioConfigurations getAllScenarioConfigurations() {
      return SCENARIO_CONFIG_CLIENT.getAllScenarioConfigurations(resourceGroup);
    }

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
      return PREDICT_CLIENT.predictV1PredictPost(request);
    }
  }
}
