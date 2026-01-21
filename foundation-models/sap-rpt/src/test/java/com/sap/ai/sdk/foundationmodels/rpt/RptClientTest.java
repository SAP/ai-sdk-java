package com.sap.ai.sdk.foundationmodels.rpt;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.*;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class RptClientTest {
  private static RptClient client;

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = RptClient.forDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testRptModels() {
    assertThat(RptModel.SAP_RPT_1_SMALL.name()).isEqualTo("sap-rpt-1-small");
    assertThat(RptModel.SAP_RPT_1_SMALL.version()).isNull();

    assertThat(RptModel.SAP_RPT_1_LARGE.name()).isEqualTo("sap-rpt-1-large");
    assertThat(RptModel.SAP_RPT_1_LARGE.version()).isNull();

    final var modelWithVersion = RptModel.SAP_RPT_1_SMALL.withVersion("v1.0");
    assertThat(modelWithVersion.name()).isEqualTo("sap-rpt-1-small");
    assertThat(modelWithVersion.version()).isEqualTo("v1.0");
  }

  static TabCompletionPostRequest createBaseRequest() {
    final var dataSchema =
        Map.of(
            "PRODUCT", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
            "PRICE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.NUMERIC),
            "ORDERDATE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.DATE),
            "ID", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
            "COSTCENTER", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING));
    final var targetColumns =
        List.of(
            TargetColumn.create()
                .predictionPlaceholder("[PREDICT]")
                .name("COSTCENTER")
                .taskType(TargetColumn.TaskTypeEnum.CLASSIFICATION));

    return TabCompletionPostRequest.create()
        .predictionConfig(PredictionConfig.create().targetColumns(targetColumns))
        .indexColumn("ID")
        .dataSchema(dataSchema)
        .parseDataTypes(true);
  }

  @Test
  void testTabCompletionWithRowsFormat() {

    final List<Map<String, Object>> rows =
        List.of(
            Map.of(
                "PRODUCT", "Couch",
                "PRICE", 999.99,
                "ORDERDATE", "28-11-2025",
                "ID", "35",
                "COSTCENTER", "[PREDICT]"),
            Map.of(
                "PRODUCT", "Office Chair",
                "PRICE", 150.8,
                "ORDERDATE", "02-11-2025",
                "ID", "44",
                "COSTCENTER", "Office Furniture"),
            Map.of(
                "PRODUCT", "Server Rack",
                "PRICE", 2200.00,
                "ORDERDATE", "01-11-2025",
                "ID", "104",
                "COSTCENTER", "Data Infrastructure"));
    final var request = createBaseRequest().rows(rows);

    final var response = client.tabCompletion(request);
    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("0381c575-9ee5-46f1-9223-4f9caf039e48");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(TabCompletionStatus.CodeEnum.NUMBER_0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictionResultsValue> prediction = response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictionResultsValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictionResultsValue.createListOfPredictionItems(
                List.of(
                    PredictionItem.create()
                        .prediction(PredictionItemPrediction.create("Office Furniture"))
                        .confidence(0.97f))));
  }

  @Test
  void testTabCompletionWithColumnsFormat() {
    final Map<String, List<Object>> columns =
        Map.of(
            "PRODUCT", List.of("Couch", "Office Chair", "Server Rack"),
            "PRICE", List.of(999.99, 150.8, 2200.00),
            "ORDERDATE", List.of("28-11-2025", "02-11-2025", "01-11-2025"),
            "ID", List.of("35", "44", "104"),
            "COSTCENTER", List.of("[PREDICT]", "Office Furniture", "Data Infrastructure"));
    final var request = createBaseRequest().columns(columns);

    final var response = client.tabCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("f89fb682-8d6b-4ef5-97f4-bd3c9aab8c49");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(TabCompletionStatus.CodeEnum.NUMBER_0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictionResultsValue> prediction = response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictionResultsValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictionResultsValue.createListOfPredictionItems(
                List.of(
                    PredictionItem.create()
                        .prediction(PredictionItemPrediction.create("Office Furniture"))
                        .confidence(0.97f))));
  }
}
