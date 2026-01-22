package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.rpt.RptClient;
import com.sap.ai.sdk.foundationmodels.rpt.RptModel;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.ColumnType;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayloadRowsInnerValue;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionPlaceholder;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.SchemaFieldConfig;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TargetColumnConfig;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RptService {

  static final RptClient rptClient = RptClient.forModel(RptModel.SAP_RPT_1_SMALL);

  public PredictResponsePayload predict() {
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

    final var request =
        PredictRequestPayload.create()
            .predictionConfig(PredictionConfig.create().targetColumns(targetColumns))
            .indexColumn("ID")
            .dataSchema(dataSchema)
            .parseDataTypes(true)
            .rows(rows);
    return rptClient.tabCompletion(request);
  }
}
