package com.sap.ai.sdk.foundationmodels.rpt;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.*;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.math.BigDecimal;
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

  static PredictRequestPayload createBaseRequest() {
    final var dataSchema =
        Map.of(
            "PRODUCT", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "PRICE", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "ORDERDATE", SchemaFieldConfig.create().dtype(ColumnType.DATE),
            "ID", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "COSTCENTER", SchemaFieldConfig.create().dtype(ColumnType.STRING));
    final var targetColumns =
        List.of(
            TargetColumnConfig.create()
                .name("COSTCENTER")
                .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
                .taskType(TargetColumnConfig.TaskTypeEnum.CLASSIFICATION));

    return PredictRequestPayload.create()
        .predictionConfig(PredictionConfig.create().targetColumns(targetColumns))
        .indexColumn("ID")
        .dataSchema(dataSchema)
        .parseDataTypes(true);
  }

  @Test
  void testTabCompletionWithRowsFormat() {

    final List<Map<String, PredictRequestPayloadRowsInnerValue>> rows =
        List.of(
            Map.of(
                "PRODUCT", PredictRequestPayloadRowsInnerValue.create("Couch"),
                "PRICE", PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(999.99)),
                "ORDERDATE", PredictRequestPayloadRowsInnerValue.create("28-11-2025"),
                "ID", PredictRequestPayloadRowsInnerValue.create("35"),
                "COSTCENTER", PredictRequestPayloadRowsInnerValue.create("[PREDICT]")),
            Map.of(
                "PRODUCT", PredictRequestPayloadRowsInnerValue.create("Office Chair"),
                "PRICE", PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(150.8)),
                "ORDERDATE", PredictRequestPayloadRowsInnerValue.create("02-11-2025"),
                "ID", PredictRequestPayloadRowsInnerValue.create("44"),
                "COSTCENTER", PredictRequestPayloadRowsInnerValue.create("Office Furniture")),
            Map.of(
                "PRODUCT", PredictRequestPayloadRowsInnerValue.create("Server Rack"),
                "PRICE", PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(2200.00)),
                "ORDERDATE", PredictRequestPayloadRowsInnerValue.create("01-11-2025"),
                "ID", PredictRequestPayloadRowsInnerValue.create("104"),
                "COSTCENTER", PredictRequestPayloadRowsInnerValue.create("Data Infrastructure")));
    final var request = createBaseRequest().rows(rows);

    final var response = client.tabCompletion(request);
    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("0381c575-9ee5-46f1-9223-4f9caf039e48");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictResponsePayloadPredictionsInnerValue> prediction =
        response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictResponsePayloadPredictionsInnerValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictResponsePayloadPredictionsInnerValue.createListOfPredictionResults(
                List.of(
                    PredictionResult.create()
                        .prediction(Prediction.create("Office Furniture"))
                        .confidence(BigDecimal.valueOf(0.97)))));
  }

  @Test
  void testTabCompletionWithColumnsFormat() {
    final Map<String, List<PredictRequestPayloadRowsInnerValue>> columns =
        Map.of(
            "PRODUCT",
                List.of(
                    PredictRequestPayloadRowsInnerValue.create("Couch"),
                    PredictRequestPayloadRowsInnerValue.create("Office Chair"),
                    PredictRequestPayloadRowsInnerValue.create("Server Rack")),
            "PRICE",
                List.of(
                    PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(999.99)),
                    PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(150.8)),
                    PredictRequestPayloadRowsInnerValue.create(BigDecimal.valueOf(2200.00))),
            "ORDERDATE",
                List.of(
                    PredictRequestPayloadRowsInnerValue.create("28-11-2025"),
                    PredictRequestPayloadRowsInnerValue.create("02-11-2025"),
                    PredictRequestPayloadRowsInnerValue.create("01-11-2025")),
            "ID",
                List.of(
                    PredictRequestPayloadRowsInnerValue.create("35"),
                    PredictRequestPayloadRowsInnerValue.create("44"),
                    PredictRequestPayloadRowsInnerValue.create("104")),
            "COSTCENTER",
                List.of(
                    PredictRequestPayloadRowsInnerValue.create("[PREDICT]"),
                    PredictRequestPayloadRowsInnerValue.create("Office Furniture"),
                    PredictRequestPayloadRowsInnerValue.create("Data Infrastructure")));
    final var request = createBaseRequest().columns(columns);

    final var response = client.tabCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("f89fb682-8d6b-4ef5-97f4-bd3c9aab8c49");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictResponsePayloadPredictionsInnerValue> prediction =
        response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictResponsePayloadPredictionsInnerValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictResponsePayloadPredictionsInnerValue.createListOfPredictionResults(
                List.of(
                    PredictionResult.create()
                        .prediction(Prediction.create("Office Furniture"))
                        .confidence(BigDecimal.valueOf(0.97)))));
  }
}
