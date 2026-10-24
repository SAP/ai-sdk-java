package com.sap.ai.sdk.tabular.orchestration;

import static com.sap.ai.sdk.tabular.orchestration.generated.model.TaskTypeEnum.CLASSIFICATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.tabular.orchestration.generated.model.ContextSelectionConfig;
import com.sap.ai.sdk.tabular.orchestration.generated.model.ContextSelectionStrategyEnum;
import com.sap.ai.sdk.tabular.orchestration.generated.model.PredictRequest;
import com.sap.ai.sdk.tabular.orchestration.generated.model.PredictResponse;
import com.sap.ai.sdk.tabular.orchestration.generated.model.PredictionConfig;
import com.sap.ai.sdk.tabular.orchestration.generated.model.StrategyConfigs;
import com.sap.ai.sdk.tabular.orchestration.generated.model.TFMEnum;
import com.sap.ai.sdk.tabular.orchestration.generated.model.TargetColumn;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
public class TabularOrchestrationUnitTest {
  private static TabularOrchestrationClient client;

  static final String scenarioConfigName = "product-prediction-scenario-lowercase";

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    val base = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new TabularOrchestrationClient().withPredictDestination(base);

    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
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

  //  @Test
  //  void testCustomHeaders() {
  //    WireMock.stubFor(
  //        get(anyUrl())
  //            .withHeader("x-test-header", equalTo("test-value"))
  //            .willReturn(
  //                okJson(
  //                    """
  //                    {
  //                      "count": 0,
  //                      "resources": []
  //                    }
  //                    """)));
  //
  //    val response = client.predict(resourceGroup).predict();
  //    assertThat(response.getCount()).isEqualTo(0);
  //
  //    WireMock.verify(getRequestedFor(anyUrl()).withHeader("x-test-header",
  // equalTo("test-value")));
  //  }
}
