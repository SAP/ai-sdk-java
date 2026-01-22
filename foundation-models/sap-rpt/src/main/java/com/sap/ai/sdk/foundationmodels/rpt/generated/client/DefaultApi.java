package com.sap.ai.sdk.foundationmodels.rpt.generated.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.io.File;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SAP-RPT-1 Tabular AI in version 0.1.0.
 *
 * <p>A REST API for in-context learning with the SAP-RPT-1 model.
 */
public class DefaultApi extends AbstractOpenApiService {
  /**
   * Instantiates this API class to invoke operations on the SAP-RPT-1 Tabular AI.
   *
   * @param httpDestination The destination that API should be used with
   */
  public DefaultApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the SAP-RPT-1 Tabular AI based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  @Beta
  public DefaultApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Make in-context predictions for specified target columns based on provided table data JSON
   * (optionally gzip-compressed).
   *
   * <p>Make in-context predictions for specified target columns. Either \&quot;rows\&quot; or
   * \&quot;columns\&quot; must be provided and must contain both context and query rows. You can
   * optionally send gzip-compressed JSON payloads and set a \&quot;Content-Encoding: gzip\&quot;
   * header.
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
   * @param predictRequestPayload The value for the parameter predictRequestPayload
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predict(@Nonnull final PredictRequestPayload predictRequestPayload)
      throws OpenApiRequestException {
    final Object localVarPostBody = predictRequestPayload;

    // verify the required parameter 'predictRequestPayload' is set
    if (predictRequestPayload == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'predictRequestPayload' when calling predict");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/predict").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PredictResponsePayload> localVarReturnType =
        new ParameterizedTypeReference<PredictResponsePayload>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.POST,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * Make in-context predictions for specified target columns based on provided table data Parquet
   * file.
   *
   * <p>Make in-context predictions for specified target columns based on provided table data
   * Parquet file.
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
   * @param _file (required) Parquet file containing the data
   * @param predictionConfig (required) JSON string for prediction_config
   * @param indexColumn (optional) Optional index column name
   * @param parseDataTypes (optional, default to true) Whether to parse data types
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predictParquet(
      @Nonnull final File _file,
      @Nonnull final String predictionConfig,
      @Nullable final String indexColumn,
      @Nullable final Boolean parseDataTypes)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter '_file' is set
    if (_file == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter '_file' when calling predictParquet");
    }

    // verify the required parameter 'predictionConfig' is set
    if (predictionConfig == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'predictionConfig' when calling predictParquet");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/predict_parquet").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (_file != null) localVarFormParams.add("file", new FileSystemResource(_file));
    if (predictionConfig != null) localVarFormParams.add("prediction_config", predictionConfig);
    if (indexColumn != null) localVarFormParams.add("index_column", indexColumn);
    if (parseDataTypes != null) localVarFormParams.add("parse_data_types", parseDataTypes);

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PredictResponsePayload> localVarReturnType =
        new ParameterizedTypeReference<PredictResponsePayload>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.POST,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * Make in-context predictions for specified target columns based on provided table data Parquet
   * file.
   *
   * <p>Make in-context predictions for specified target columns based on provided table data
   * Parquet file.
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
   * @param _file Parquet file containing the data
   * @param predictionConfig JSON string for prediction_config
   * @return PredictResponsePayload
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PredictResponsePayload predictParquet(
      @Nonnull final File _file, @Nonnull final String predictionConfig)
      throws OpenApiRequestException {
    return predictParquet(_file, predictionConfig, null, null);
  }
}
