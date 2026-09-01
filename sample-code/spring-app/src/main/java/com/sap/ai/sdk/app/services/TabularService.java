package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.tabular.generated.model.CreateTARequest.TypeEnum.PARQUET;
import static com.sap.ai.sdk.tabular.generated.model.DefinitionType.DOCUMENT;
import static com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationCreateRequest.TypeEnum.HDL;
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
import com.sap.ai.sdk.tabular.generated.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.model.GetScenarioConfigurations;
import com.sap.ai.sdk.tabular.generated.model.HDLConnectionConfig;
import com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationCreateRequest;
import com.sap.ai.sdk.tabular.generated.model.ScenarioConfigurationNameObject;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactConfig;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactListResponse;
import com.sap.ai.sdk.tabular.generated.predict.client.PredictApi;
import com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictRequest;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictResponse;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.Strategyconfig;
import com.sap.ai.sdk.tabular.generated.predict.model.TFMEnum;
import com.sap.ai.sdk.tabular.generated.predict.model.TargetColumn;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class TabularService {

  static final DataDestinationsApi dataDestinationsClient =
      new DataDestinationsApi(buildDestination());
  static final TabularArtifactsApi tabularClient = new TabularArtifactsApi(buildDestination());
  static final ScenarioConfigurationManagerApi scenarioConfigClient =
      new ScenarioConfigurationManagerApi(buildDestination());
  static final PredictApi predictClient =
      new PredictApi(
          new AiCoreService().getInferenceDestination().forScenario("tabular-orchestration"));

  private static HttpDestination buildDestination() {
    final HttpDestination base = new AiCoreService().getBaseDestination();
    final URI rootUri = base.getUri().resolve("/v2/admin/tcr/");
    return DefaultHttpDestination.fromDestination(base).uri(rootUri).build();
  }

  static final String resourceGroup = "default";
  static final String dataDestinationName = "ai-sdk-hdl-destination";
  static final String artifactName = "product-artifact";
  static final String artifactPath = "/data/product_data_hana.parquet";
  static final String scenarioConfigName = "product-prediction-scenario";

  public GetDataDestinations getAllDataDestinations() {
    return dataDestinationsClient.getAllDataDestinations(resourceGroup);
  }

  public TabularArtifactListResponse getAllArtifacts() {
    return tabularClient.getAllTabularArtifacts(resourceGroup);
  }

  public GetScenarioConfigurations getAllScenarioConfigurations() {
    return scenarioConfigClient.getAllScenarioConfigurations(resourceGroup);
  }

  public AsyncCreateDataDestinationResponse createDataDestination() {
    val request =
        HDLDataDestinationCreateRequest.create()
            .type(HDL)
            .config(HDLConnectionConfig.create().host(""))
            .description("HDL data destination for AI Core SDK");
    return dataDestinationsClient.createUpdateDataDestination(
        resourceGroup, dataDestinationName, request);
  }

  private static final Map<String, Object> PRODUCT_ENTITY_ELEMENTS =
      Map.of(
          "PRODUCT", Map.of("type", "cds.String"),
          "PRICE", Map.of("type", "cds.Double"),
          "PRODUCTION_DATE", Map.of("type", "cds.String"),
          "__row_idx__", Map.of("type", "cds.String"),
          "SALESGROUP", Map.of("type", "cds.String"));

  public ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response createArtifact() {
    val definitions =
        Map.of(
            "definitions",
            Map.of("ProductEntity", Map.of("kind", "entity", "elements", PRODUCT_ENTITY_ELEMENTS)));
    val request =
        CreateTARequest.create()
            .dataDestinationName(dataDestinationName)
            .type(PARQUET)
            .path(artifactPath)
            .csnMetadata(
                CreateTARequestCsnMetadata.create()
                    .definition(
                        DocumentDefinition.create().definitionType(DOCUMENT).document(definitions))
                    .entityName("ProductEntity")
                    .selectedColumns(PRODUCT_ENTITY_ELEMENTS.keySet()));
    return tabularClient.createTabularArtifact(resourceGroup, artifactName, request);
  }

  public ScenarioConfigurationNameObject createScenarioConfiguration() {
    val request =
        CreateScenarioConfiguration.create()
            .tabularArtifacts(TabularArtifactConfig.create().name(artifactName))
            .description("Sample scenario configuration for product prediction");
    return scenarioConfigClient.createScenarioConfiguration(
        resourceGroup, scenarioConfigName, request);
  }

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
                    .strategyConfig(
                        Strategyconfig.create().indexColumn("id").deterministic(true)))
            .rows(
                List.of(
                    Map.of(
                        "product", "Laptop",
                        "price", 999.99,
                        "production_date", "2025-01-15",
                        "__row_idx__", "prediction-1",
                        "salesgroup", "[PREDICT]"),
                    Map.of(
                        "product", "Office Chair",
                        "price", 142.99,
                        "production_date", "2025-07-13",
                        "__row_idx__", "prediction-2",
                        "salesgroup", "[PREDICT]")));
    return predictClient.predictV1PredictPost(request);
  }
}
