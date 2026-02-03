package com.sap.ai.sdk.foundationmodels.rpt.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;

/**
 * SAP-RPT-1 Tabular AI in version 0.1.0.
 *
 * <p>A REST API for in-context learning with the SAP-RPT-1 model.
 */
public class DefaultApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the SAP-RPT-1 Tabular AI */
  public DefaultApi() {}

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
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
}
