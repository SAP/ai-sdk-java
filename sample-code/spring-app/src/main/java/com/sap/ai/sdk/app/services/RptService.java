package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.rpt.RptClient;
import com.sap.ai.sdk.foundationmodels.rpt.RptModel;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.ColumnType;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionPlaceholder;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.RowsInnerValue;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.SchemaFieldConfig;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TargetColumnConfig;
import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Service;

/** Service to interact with the RPT model for predictions. */
@Service
public class RptService {

  static final RptClient rptClient = RptClient.forModel(RptModel.SAP_RPT_1_SMALL);

  /**
   * Makes a prediction request to the RPT model. *
   *
   * @return the prediction response payload from the RPT model
   */
  @Nonnull
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
    final List<Map<String, RowsInnerValue>> rows =
        List.of(
            Map.of(
                "PRODUCT", RowsInnerValue.create("Couch"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(999.99)),
                "ORDERDATE", RowsInnerValue.create("28-11-2025"),
                "ID", RowsInnerValue.create("35"),
                "COSTCENTER", RowsInnerValue.create("[PREDICT]")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Office Chair"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(150.8)),
                "ORDERDATE", RowsInnerValue.create("02-11-2025"),
                "ID", RowsInnerValue.create("44"),
                "COSTCENTER", RowsInnerValue.create("Office Furniture")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Server Rack"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(2200.00)),
                "ORDERDATE", RowsInnerValue.create("01-11-2025"),
                "ID", RowsInnerValue.create("104"),
                "COSTCENTER", RowsInnerValue.create("Data Infrastructure")));

    final var request =
        PredictRequestPayload.create()
            .predictionConfig(PredictionConfig.create().targetColumns(targetColumns))
            .indexColumn("ID")
            .dataSchema(dataSchema)
            .parseDataTypes(true)
            .rows(rows);
    return rptClient.tableCompletion(request);
  }

  /**
   * Makes a prediction request to the RPT model using a Parquet file as input.
   *
   * @return the prediction response payload from the RPT model
   */
  @Nonnull
  public PredictResponsePayload predictParquet() {
    try {
      final File parquetData = new File(getClass().getResource("/sample.parquet").toURI());
      final var targetColumns =
          List.of(
              TargetColumnConfig.create()
                  .name("COSTCENTER")
                  .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
                  .taskType(TargetColumnConfig.TaskTypeEnum.CLASSIFICATION));

      return rptClient.tableCompletion(
          parquetData, PredictionConfig.create().targetColumns(targetColumns));
    } catch (final URISyntaxException e) {
      throw new RuntimeException("Failed to load Parquet file for prediction", e);
    }
  }
}
