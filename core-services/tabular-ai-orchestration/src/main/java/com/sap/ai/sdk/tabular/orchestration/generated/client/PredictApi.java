package com.sap.ai.sdk.tabular.orchestration.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.tabular.orchestration.generated.model.PredictRequest;
import com.sap.ai.sdk.tabular.orchestration.generated.model.ResponsePredictV1PredictEndpointsPredict;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;

/**
 * Tabular AI Orchestration in version 1.11.2.
 *
 * <p>The Tabular AI Orchestration service acts as the unified tabular inference layer for the AI
 * Core platform, exposing a single prediction endpoint that handles request validation, context
 * selection, and Tabular Foundation Model interface harmonization automatically. It leverages
 * Tabular Foundation Models to generate predictions from structured enterprise data using
 * in-context learning, eliminating the need for dataset-specific training. By abstracting the
 * complexity of data connectivity and schema interpretation, this service allows developers to
 * seamlessly integrate predictive AI capabilities into business processes through a standardized
 * invocation interface.
 */
public class PredictApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the Tabular AI Orchestration.
   *
   * @param httpDestination The destination that API should be used with
   */
  public PredictApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Tabular AI Orchestration based on a
   * given {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public PredictApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public PredictApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new PredictApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Make predictions using a Tabular Foundation Model
   *
   * <p>Make predictions with a supported Tabular Foundation Model using rows or columns. **TabPFN**
   * Select modelName: tabpfn-3.5-plus. TabPFN supports exactly one target column per request. Set
   * the target task_type to classification or regression; the default is classification. Supply
   * labelled training rows through contextRows/contextColumns, labelled query rows, or context
   * selection. At least one labelled training row and one query row containing the prediction
   * placeholder are required. Regression training targets must be numeric. Classification training
   * targets must not be null or empty; entirely numeric targets are rejected by this
   * orchestrator&#39;s classification preprocessing. **Response format** responseFormat defaults to
   * native, which returns the model-native response. Select harmonized for the common response
   * schema. The legacy spelling harmonised is also accepted and normalized to harmonized. For
   * TabPFN, native responses contain metadata, prediction, usage, and optionally model_id.
   * Harmonized responses contain id, modelName, metadata, predictions, and explanations. TabPFN
   * currently returns null for id and explanations in harmonized responses. **Output types** Set
   * modelConfig.task_config.predict_params.output_type. Native classification supports probas,
   * preds, and top_k. Harmonized classification supports top_k only. Native regression supports
   * mean, median, mode, quantiles, full, and main. Harmonized regression supports main, mean,
   * median, and mode. When neither the request nor deployment defaults supply output_type, native
   * classification defaults to probas and native regression to mean; harmonized classification
   * defaults to top_k and harmonized regression to main. Omit output_type to use these defaults.
   * For harmonized regression, main uses the model&#39;s mean and the first and last quantile
   * arrays as confidenceInterval (10th and 90th percentiles with the model&#39;s default
   * quantiles). mean, median, and mode return the selected statistic without confidenceInterval.
   * They do not trigger a second inference request for intervals. **Configuration** TabPFN
   * configuration is passed under modelConfig.task_config.tabpfn_config and
   * modelConfig.task_config.predict_params. Do not send RPT-only options such as data_schema,
   * parse_data_types, or context_mode. balance_probabilities is classification-only. Set
   * predictionConfig.targetColumns[].top_k for classification; an explicit
   * modelConfig.task_config.predict_params.top_k takes precedence. When neither is set, top_k
   * defaults to 1. modelConfig.index_column identifies a column to exclude from model features; it
   * is not forwarded to TabPFN. **Data format** Use either rows with contextRows, or columns with
   * contextColumns. Query and supplied context data must have the same column names. Target-column
   * keys task_type, prediction_placeholder, and top_k also accept the camelCase aliases taskType,
   * predictionPlaceholder, and topK. Use contextSelectionConfig.strategy: none when all training
   * context is supplied in the request. Required header: ai-resource-group. Authenticate with
   * OAuth2 through the SAP AI Core deployment endpoint. The request and response examples use
   * synthetic data and illustrative predictions; they are not accuracy guarantees.
   *
   * <p><b>200</b> - Prediction response selected by responseFormat. Native TabPFN uses
   * TabPFN3p5plusResponse; native RPT uses its corresponding RPT schema. Harmonized uses
   * PredictResponse.
   *
   * <p><b>400</b> - Invalid request data
   *
   * <p><b>401</b> - Authentication failed
   *
   * <p><b>404</b> - Model not found
   *
   * <p><b>500</b> - Internal server error
   *
   * <p><b>502</b> - External service error
   *
   * <p><b>504</b> - Request timeout
   *
   * <p><b>422</b> - Validation Error
   *
   * @param aiResourceGroup AI Core resource group identifier
   * @param predictRequest The value for the parameter predictRequest
   * @return ResponsePredictV1PredictEndpointsPredict
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ResponsePredictV1PredictEndpointsPredict predict(
      @Nonnull final String aiResourceGroup, @Nonnull final PredictRequest predictRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling predict")
          .statusCode(400);
    }

    // verify the required parameter 'predictRequest' is set
    if (predictRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'predictRequest' when calling predict")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/predict";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("ai-resource-group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ResponsePredictV1PredictEndpointsPredict> localVarReturnType =
        new TypeReference<ResponsePredictV1PredictEndpointsPredict>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        predictRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }
}
