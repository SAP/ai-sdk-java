package com.sap.ai.sdk.foundationmodels.rpt;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.foundationmodels.rpt.generated.model.*;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RptClientTest {

  @Test
  void testTabCompletionWithRowsFormat() {
    final var request =
        TabCompletionPostRequest.create()
            .predictionConfig(
                PredictionConfig.create()
                    .targetColumns(
                        List.of(
                            TargetColumn.create()
                                .predictionPlaceholder("[PREDICT]")
                                .name("COSTCENTER")
                                .taskType(TargetColumn.TaskTypeEnum.CLASSIFICATION))))
            .indexColumn("ID")
            .rows(
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
                        "COSTCENTER", "Data Infrastructure")))
            .dataSchema(
                Map.of(
                    "PRODUCT", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
                    "PRICE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.NUMERIC),
                    "ORDERDATE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.DATE),
                    "ID", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
                    "COSTCENTER", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING)))
            .parseDataTypes(true);

    final var response = RptClient.forModel(RptModel.SAP_RPT_1_SMALL).tabCompletion(request);

    assertThat(response).isNotNull();
  }

  @Test
  void testTabCompletionWithColumnsFormat() {
    var request =
        TabCompletionPostRequest.create()
            .predictionConfig(
                PredictionConfig.create()
                    .targetColumns(
                        List.of(
                            TargetColumn.create()
                                .predictionPlaceholder("[PREDICT]")
                                .name("COSTCENTER")
                                .taskType(TargetColumn.TaskTypeEnum.CLASSIFICATION))))
            .indexColumn("ID")
            .columns(
                Map.of(
                    "PRODUCT", List.of("Couch", "Office Chair", "Server Rack"),
                    "PRICE", List.of(999.99, 150.8, 2200.00),
                    "ORDERDATE", List.of("28-11-2025", "02-11-2025", "01-11-2025"),
                    "ID", List.of("35", "44", "104"),
                    "COSTCENTER", List.of("[PREDICT]", "Office Furniture", "Data Infrastructure")))
            .dataSchema(
                Map.of(
                    "PRODUCT", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
                    "PRICE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.NUMERIC),
                    "ORDERDATE", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.DATE),
                    "ID", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING),
                    "COSTCENTER", ColumnDataType.create().dtype(ColumnDataType.DtypeEnum.STRING)))
            .parseDataTypes(true);

    RptClient.forModel(RptModel.SAP_RPT_1_SMALL).tabCompletion(request);
  }
}
