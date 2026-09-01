package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.rpt.RptModel.SAP_RPT_1_5;
import static com.sap.ai.sdk.foundationmodels.rpt.generated.model.TargetColumnConfig.TaskTypeEnum.CLASSIFICATION;

import com.sap.ai.sdk.foundationmodels.rpt.RptClient;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.ColumnType;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.ExplanationConfig;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayloadOneOf;
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
import java.util.Objects;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Service;

/** Service to interact with the RPT model for predictions. */
@Service
public class RptService {

  static final RptClient rptClient = RptClient.forModel(SAP_RPT_1_5);

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
                .taskType(CLASSIFICATION));
    final List<Map<String, RowsInnerValue>> rows =
        List.of(
            Map.of(
                "PRODUCT", RowsInnerValue.create("Couch"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(999.99)),
                "ORDERDATE", RowsInnerValue.create("2025-11-28"),
                "ID", RowsInnerValue.create("35"),
                "COSTCENTER", RowsInnerValue.create("[PREDICT]")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Office Chair"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(150.8)),
                "ORDERDATE", RowsInnerValue.create("2025-11-02"),
                "ID", RowsInnerValue.create("44"),
                "COSTCENTER", RowsInnerValue.create("Office Furniture")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Server Rack"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(2200.00)),
                "ORDERDATE", RowsInnerValue.create("2025-11-01"),
                "ID", RowsInnerValue.create("104"),
                "COSTCENTER", RowsInnerValue.create("Data Infrastructure")));

    final var predictionConfig =
        PredictionConfig.create()
            .targetColumns(targetColumns)
            .contextMode(null) // BE API is not fully migrated ??
            .explanations(ExplanationConfig.create().topColumnScores(3).topRelevantContextRows(3));

    final var request =
        PredictRequestPayloadOneOf.create()
            .predictionConfig(predictionConfig)
            .rows(rows)
            .indexColumn("ID")
            .dataSchema(dataSchema)
            .parseDataTypes(true);
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
      final var parquetFile =
          new File(Objects.requireNonNull(getClass().getResource("/sample.parquet")).toURI());
      final var targetColumns =
          List.of(
              TargetColumnConfig.create()
                  .name("COSTCENTER")
                  .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
                  .taskType(CLASSIFICATION));
      final var predictionConfig =
          PredictionConfig.create()
              .targetColumns(targetColumns)
              .contextMode(null) // BE API is not fully migrated ??
              .explanations(
                  ExplanationConfig.create().topColumnScores(3).topRelevantContextRows(3));

      return rptClient.tableCompletion(parquetFile, predictionConfig);

    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException("Failed to load Parquet file for prediction", e);
    }
  }
}
