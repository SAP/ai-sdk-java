package com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneRequest;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.SystemOneResponse;
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
 * SAP-RPT-Decisions in version 1.6.0.
 *
 * <p>REST API for sap-rpt-decisions.
 */
public class DefaultApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the SAP-RPT-Decisions.
   *
   * @param httpDestination The destination that API should be used with
   */
  public DefaultApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the SAP-RPT-Decisions based on a given
   * {@link ApiClient}.
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
   * Answer questions about a state based on examples.
   *
   * <p>
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>422</b> - Validation Error - The request body failed schema validation
   *
   * <p><b>499</b> - Client Disconnected - The client closed the connection before the response was
   * sent
   *
   * <p><b>500</b> - Internal Server Error
   *
   * <p><b>503</b> - Service Unavailable - Server under high load
   *
   * <p><b>504</b> - Gateway Timeout - The request took too long to process
   *
   * @param systemOneRequest The value for the parameter systemOneRequest
   * @return SystemOneResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SystemOneResponse systemone(@Nonnull final SystemOneRequest systemOneRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'systemOneRequest' is set
    if (systemOneRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'systemOneRequest' when calling systemone")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/systemone";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<SystemOneResponse> localVarReturnType =
        new TypeReference<SystemOneResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        systemOneRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }
}
