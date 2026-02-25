package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.RptService;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionsInnerValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    if ("json".equals(format)) {
      return rptService.predict();
    }
    final var prediction =
        (PredictionsInnerValue.ListOfPredictionResults)
            rptService.predict().getPredictions().get(0).get("COSTCENTER");
    return prediction.values().get(0).getPrediction();
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
    if ("json".equals(format)) {
      return rptService.predictParquet();
    }
    final var prediction =
        (PredictionsInnerValue.ListOfPredictionResults)
            rptService.predict().getPredictions().get(0).get("COSTCENTER");
    return prediction.values().get(0).getPrediction();
  }
}
