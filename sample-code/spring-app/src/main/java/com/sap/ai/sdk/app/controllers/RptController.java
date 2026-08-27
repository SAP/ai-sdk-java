package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.RptService;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.Prediction;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionsInnerValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for RPT model operations */
@RestController
public class RptController {

  @Autowired private RptService rptService;

  /**
   * Endpoint to get table completion predictions from the RPT model.
   *
   * @param format optional query parameter to specify the response format (e.g., "json")
   * @return the prediction result in the specified format
   */
  @Nonnull
  @GetMapping("/tableCompletion")
  public Object tableCompletion(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = rptService.predict();
    if ("json".equals(format)) {
      return response;
    }
    return getResponseMessage(response);
  }

  /**
   * Endpoint to get table completion predictions from the RPT model with Parquet file input.
   *
   * @param format optional query parameter to specify the response format (e.g., "json")
   * @return the prediction result in the specified format
   */
  @Nonnull
  @GetMapping("/tableCompletionWithParquet")
  public Object tableCompletionWithParquet(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = rptService.predictParquet();
    if ("json".equals(format)) {
      return response;
    }
    return getResponseMessage(response);
  }

  @Nonnull
  private static String getResponseMessage(final PredictResponsePayload response) {
    val predictionResult =
        ((PredictionsInnerValue.ListOfPredictionResults)
            response.getPredictions().get(0).get("COSTCENTER"));
    val prediction = (Prediction.InnerString) predictionResult.values().get(0).getPrediction();
    val explanation = response.getExplanations();
    val message = new StringBuilder().append("Prediction: ").append(prediction.value());
    if (explanation != null
        && explanation.getTopColumnScores() != null
        && explanation.getTopRelevantContextRows() != null) {
      message
          .append("<br><br>Explanation:<br>Top Column Scores: ")
          .append(explanation.getTopColumnScores().get(0))
          .append("<br>Top Relevant Context Rows: ")
          .append(explanation.getTopRelevantContextRows().get(0));
    }
    return message.toString();
  }
}
