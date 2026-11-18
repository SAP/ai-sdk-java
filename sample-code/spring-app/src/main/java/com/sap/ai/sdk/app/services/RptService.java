package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.rpt.RptModel.SAP_RPT_1_5;
import static com.sap.ai.sdk.foundationmodels.rpt.RptModel.SAP_RPT_DECISIONS;
import static com.sap.ai.sdk.foundationmodels.rpt.generated.model.TargetColumnConfig.TaskTypeEnum.CLASSIFICATION;

import com.sap.ai.sdk.foundationmodels.rpt.RptClient;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.AnswersValue1;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.CriteriaValue;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.Instructions;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.State;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.State1;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneChoiceQuestion;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneChoiceQuestion.TypeEnum;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneExample;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneRequest;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneResponse;
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
  static final RptClient decisionsClient = RptClient.forModel(SAP_RPT_DECISIONS);

  /**
   * Makes a prediction request to the RPT model. *
   *
   * @link <a
   *     href="https://sap.github.io/ai-sdk/docs/java/foundation-models/sap-rpt/table-completion">SAP
   *     AI SDK for Java: RPT - Table Completion</a>
   * @return the prediction response payload from the RPT model.
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

    final var predictionConfig =
        PredictionConfig.create()
            .targetColumns(targetColumns)
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
   * @link <a
   *     href="https://sap.github.io/ai-sdk/docs/java/foundation-models/sap-rpt/table-completion">SAP
   *     AI SDK for Java: RPT - Table Completion</a>
   * @return the prediction response payload from the RPT model.
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
              .explanations(
                  ExplanationConfig.create().topColumnScores(3).topRelevantContextRows(3));

      return rptClient.tableCompletion(parquetFile, predictionConfig);

    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException("Failed to load Parquet file for prediction", e);
    }
  }

  /**
   * Assigns a cost center to an order using labeled examples of previous orders.
   *
   * @return the decision response containing the predicted cost center.
   */
  @Nonnull
  public SystemOneResponse decision() {
    return decisionsClient.decision(
        SystemOneRequest.create()
            .state(
                State1.createInnerMapOfStringAndObject(
                    Map.of(
                        "PRODUCT", "Couch",
                        "PRICE", BigDecimal.valueOf(999.99),
                        "ORDERDATE", "2025-11-28",
                        "ID", "35")))
            .questions(
                Map.of(
                    "COSTCENTER",
                    SystemOneChoiceQuestion.create()
                        .type(TypeEnum.CHOICE)
                        .instructions(
                            Instructions.create(
                                "Which cost center should this order be assigned to?"))
                        .criteria(
                            Map.of(
                                "Office Furniture",
                                CriteriaValue.create(
                                    "Furniture for offices and shared workspaces, such as chairs,"
                                        + " desks, and couches."),
                                "Data Infrastructure",
                                CriteriaValue.create(
                                    "Equipment for data centers and IT infrastructure, such as"
                                        + " servers and server racks.")))))
            .examples(
                SystemOneExample.create()
                    .state(
                        State.createInnerMapOfStringAndObject(
                            Map.of(
                                "PRODUCT", "Office Chair",
                                "PRICE", BigDecimal.valueOf(150.8),
                                "ORDERDATE", "2025-11-02",
                                "ID", "44")))
                    .answers(Map.of("COSTCENTER", AnswersValue1.create("Office Furniture"))),
                SystemOneExample.create()
                    .state(
                        State.createInnerMapOfStringAndObject(
                            Map.of(
                                "PRODUCT", "Server Rack",
                                "PRICE", BigDecimal.valueOf(2200.00),
                                "ORDERDATE", "2025-11-01",
                                "ID", "104")))
                    .answers(Map.of("COSTCENTER", AnswersValue1.create("Data Infrastructure")))));
  }
}
