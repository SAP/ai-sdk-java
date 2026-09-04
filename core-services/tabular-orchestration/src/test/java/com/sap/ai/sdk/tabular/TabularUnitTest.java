package com.sap.ai.sdk.tabular;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.DataDestinationStatus.ACTIVE;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.DefinitionType.DOCUMENT;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse.AdapterTypeEnum.FILE;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse.TypeEnum.HDL;
import static com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactDetails.TypeEnum.PARQUET;
import static com.sap.ai.sdk.tabular.generated.predict.model.TaskTypeEnum.CLASSIFICATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ContextSelectionStrategy;
import com.sap.ai.sdk.tabular.generated.orchestration.model.DocumentDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestination;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetScenarioConfigurations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationObject;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationStatus;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactDetails;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactListResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactStatus;
import com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.ContextSelectionStrategyEnum;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictRequest;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictResponse;
import com.sap.ai.sdk.tabular.generated.predict.model.PredictionConfig;
import com.sap.ai.sdk.tabular.generated.predict.model.StrategyConfigs;
import com.sap.ai.sdk.tabular.generated.predict.model.TFMEnum;
import com.sap.ai.sdk.tabular.generated.predict.model.TargetColumn;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
public class TabularUnitTest {
  private static TabularClient client;

  static final String resourceGroup = "default";
  static final String dataDestinationName = "ai-sdk-hdl-destination";
  static final String artifactName = "product-artifact-lowercase";
  static final String artifactPath = "/data/product_data_hana_lowercase.parquet";
  static final String scenarioConfigName = "product-prediction-scenario-lowercase";

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    val base = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    val service = new AiCoreService().withBaseDestination(base);
    client = new TabularClient(service).withPredictDestination(base);

    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testGetAllDataDestinations() {
    final GetDataDestinations response =
        client.dataDestinations().getAllDataDestinations(resourceGroup);
    assertThat(response.getCount()).isEqualTo(1);
    final List<GetDataDestination> resources = response.getResources();
    assertThat(resources.size()).isEqualTo(1);
    final HDLDataDestinationGetResponse dataDestination =
        (HDLDataDestinationGetResponse) resources.get(0);
    assertThat(dataDestination.getName()).isEqualTo("ai-sdk-hdl-destination");
    assertThat(dataDestination.getType()).isEqualTo(HDL);
    assertThat(dataDestination.getDescription()).isEqualTo("HDL data destination for AI Core SDK");
    assertThat(dataDestination.getAdapterType()).isEqualTo(FILE);
    assertThat(dataDestination.getCreatedAt()).isEqualTo("2026-05-19T11:15:40.062350Z");
    assertThat(dataDestination.getUpdatedAt()).isEqualTo("2026-08-24T08:25:35.730000Z");
    assertThat(dataDestination.getLabels()).isEmpty();
    assertThat(dataDestination.getStatus()).isEqualTo(ACTIVE);
  }

  @Test
  void testGetAllTabularArtifacts() {
    final TabularArtifactListResponse response =
        client.tabularArtifacts().getAllTabularArtifacts(resourceGroup);
    assertThat(response.getCount()).isEqualTo(1);
    final List<TabularArtifactDetails> resources = response.getResources();
    assertThat(resources.size()).isEqualTo(1);

    final TabularArtifactDetails artifact = resources.get(0);
    assertThat(artifact.getId()).isEqualTo("1abcd328-9701-4e21-822d-a7f93b12ce12");
    assertThat(artifact.getName()).isEqualTo(artifactName);
    assertThat(artifact.getTenantId()).isEqualTo("94bbb59e-56ff-4cf3-9787-f6b28d5e565b");
    assertThat(artifact.getResourceGroupId()).isEqualTo(resourceGroup);
    assertThat(artifact.getDataDestinationName()).isEqualTo(dataDestinationName);
    assertThat(artifact.getVirtualTableName())
        .isEqualTo("v_fc09a120-360a-5364-b061-a43c2f3d07d3_product-artifact-lowercase");
    assertThat(artifact.getRemoteSourceName()).isEqualTo("rs_b745d23e_ff45_53db_9d66_3573fc42fc80");
    assertThat(artifact.getPath()).isEqualTo(artifactPath);
    assertThat(artifact.getType()).isEqualTo(PARQUET);
    assertThat(artifact.getStatus()).isEqualTo(TabularArtifactStatus.ACTIVE);
    assertThat(artifact.getErrorMessage()).isNull();
    assertThat(artifact.getMetadata()).isEmpty();
    assertThat(artifact.getCreatedAt()).isEqualTo("2026-05-22T06:48:54.858000Z");
    assertThat(artifact.getUpdatedAt()).isEqualTo("2026-08-24T08:25:42.036000Z");

    final var csnMetadata = artifact.getCsnMetadata();
    assertThat(csnMetadata).isNotNull();
    assertThat(csnMetadata.getEntityName()).isEqualTo("ProductEntity");
    assertThat(csnMetadata.getSelectedColumns())
        .containsExactly("product", "price", "date", "id", "salesgroup");

    final DocumentDefinition definition = (DocumentDefinition) csnMetadata.getDefinition();
    assertThat(definition.getDefinitionType()).isEqualTo(DOCUMENT);
    assertThat(definition.getDocument()).isNotNull();
  }

  @Test
  void testGetAllScenarioConfigurations() {
    final GetScenarioConfigurations response =
        client.scenarioConfiguration().getAllScenarioConfigurations(resourceGroup);
    assertThat(response.getCount()).isEqualTo(1);
    final List<ScenarioConfigurationObject> resources = response.getResources();
    assertThat(resources.size()).isEqualTo(1);

    final ScenarioConfigurationObject scenario = resources.get(0);
    assertThat(scenario.getName()).isEqualTo(scenarioConfigName);
    assertThat(scenario.getDescription()).isNull();
    assertThat(scenario.getContextSelectionStrategy()).isEqualTo(ContextSelectionStrategy.RANDOM);
    assertThat(scenario.getTabularArtifacts()).hasSize(1);
    assertThat(scenario.getTabularArtifacts().get(0).getName()).isEqualTo(artifactName);
    assertThat(scenario.getLabels()).isEmpty();
    assertThat(scenario.getStatus()).isEqualTo(ScenarioConfigurationStatus.ACTIVE);
    assertThat(scenario.getErrorMessage()).isNull();
    assertThat(scenario.getCreatedAt()).isEqualTo("2026-05-22T06:49:16.596000Z");
    assertThat(scenario.getUpdatedAt()).isEqualTo("2026-05-22T06:49:16.596000Z");
  }

  @Test
  void testPredict() {
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
                    .strategy(ContextSelectionStrategyEnum.RANDOM)
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

    final PredictResponse response = client.predict().predict(request);
    assertThat(response.getId()).isEqualTo("babec616-8085-43ad-a36a-57f0c1484202");

    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(6);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(3);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(3);

    final List<Map<String, Object>> predictions = response.getPredictions();
    assertThat(predictions).hasSize(3);

    @SuppressWarnings("unchecked")
    final List<Map<String, Object>> firstPrediction =
        (List<Map<String, Object>>) predictions.get(0).get("salesgroup");
    assertThat(firstPrediction).hasSize(1);
    assertThat(firstPrediction.get(0).get("confidence")).isEqualTo(1.0);
    assertThat(firstPrediction.get(0).get("confidence_interval")).isNull();
    assertThat(firstPrediction.get(0).get("prediction")).isEqualTo("Electronics");

    @SuppressWarnings("unchecked")
    final List<Map<String, Object>> secondPrediction =
        (List<Map<String, Object>>) predictions.get(1).get("salesgroup");
    assertThat(secondPrediction.get(0).get("prediction")).isEqualTo("Electronics");

    @SuppressWarnings("unchecked")
    final List<Map<String, Object>> thirdPrediction =
        (List<Map<String, Object>>) predictions.get(2).get("salesgroup");
    assertThat(thirdPrediction.get(0).get("prediction")).isEqualTo("Furniture");

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getAdditionalInformation()).isEmpty();
  }

  @Test
  void testCustomHeaders() {
    WireMock.stubFor(
        get(anyUrl())
            .withHeader("x-test-header", equalTo("test-value"))
            .willReturn(
                okJson(
                    """
                    {
                      "count": 0,
                      "resources": []
                    }
                    """)));

    val response =
        client
            .withHeader("x-test-header", "test-value")
            .dataDestinations()
            .getAllDataDestinations(resourceGroup);
    assertThat(response.getCount()).isEqualTo(0);

    WireMock.verify(getRequestedFor(anyUrl()).withHeader("x-test-header", equalTo("test-value")));
  }
}
