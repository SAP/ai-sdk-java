package com.sap.ai.sdk.foundationmodels.rpt.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * SAP RPT in version 1.5.0.
 *
 * <p>A REST API for in-context learning with SAP RPT models.
 */
public class DefaultApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the SAP RPT.
   *
   * @param httpDestination The destination that API should be used with
   */
  public DefaultApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the SAP RPT based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public DefaultApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public DefaultApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new DefaultApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Health Check
   *
   * <p>
   *
   * <p><b>200</b> - Successful Response
   *
   * @return Object
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Object health() throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/health";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<Object> localVarReturnType = new TypeReference<Object>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Make predictions from JSON (optionally gzip-compressed).
   *
   * <p>
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * <p><b>503</b> - Service Unavailable
   *
   * @param predictRequestPayload (required) The value for the parameter predictRequestPayload
   * @param contentEncoding (optional) Content encoding of the request body. Use &#39;gzip&#39; for
   *     gzip-compressed payloads. Use compression level 1.
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predict(
      @Nonnull final PredictRequestPayload predictRequestPayload,
      @Nullable final String contentEncoding)
      throws OpenApiRequestException {

    // verify the required parameter 'predictRequestPayload' is set
    if (predictRequestPayload == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'predictRequestPayload' when calling predict")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/predict";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (contentEncoding != null)
      localVarHeaderParams.put("Content-Encoding", ApiClient.parameterToString(contentEncoding));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PredictResponsePayload> localVarReturnType =
        new TypeReference<PredictResponsePayload>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        predictRequestPayload,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Make predictions from JSON (optionally gzip-compressed).
   *
   * <p>
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * <p><b>503</b> - Service Unavailable
   *
   * @param predictRequestPayload The value for the parameter predictRequestPayload
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predict(@Nonnull final PredictRequestPayload predictRequestPayload)
      throws OpenApiRequestException {
    return predict(predictRequestPayload, null);
  }

  /**
   * Make predictions from Parquet file
   *
   * <p>
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * <p><b>503</b> - Service Unavailable
   *
   * @param _file (required) The value for the parameter _file
   * @param predictionConfig (required) JSON string containing the prediction configuration (see
   *     PredictionConfig schema).
   * @param indexColumn (optional) The value for the parameter indexColumn
   * @param parseDataTypes (optional, default to false) The value for the parameter parseDataTypes
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predictParquet(
      @Nonnull final File _file,
      @Nonnull final File predictionConfig,
      @Nullable final String indexColumn,
      @Nullable final Boolean parseDataTypes)
      throws OpenApiRequestException {

    // verify the required parameter '_file' is set
    if (_file == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter '_file' when calling predictParquet")
          .statusCode(400);
    }

    // verify the required parameter 'predictionConfig' is set
    if (predictionConfig == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'predictionConfig' when calling predictParquet")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/predict_parquet";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (_file != null) localVarFormParams.put("file", _file);
    if (predictionConfig != null) localVarFormParams.put("prediction_config", predictionConfig);
    if (indexColumn != null) localVarFormParams.put("index_column", indexColumn);
    if (parseDataTypes != null) localVarFormParams.put("parse_data_types", parseDataTypes);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PredictResponsePayload> localVarReturnType =
        new TypeReference<PredictResponsePayload>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Make predictions from Parquet file
   *
   * <p>
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * <p><b>503</b> - Service Unavailable
   *
   * @param _file The value for the parameter _file
   * @param predictionConfig JSON string containing the prediction configuration (see
   *     PredictionConfig schema).
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predictParquet(
      @Nonnull final File _file, @Nonnull final File predictionConfig)
      throws OpenApiRequestException {
    return predictParquet(_file, predictionConfig, null, null);
  }
}
