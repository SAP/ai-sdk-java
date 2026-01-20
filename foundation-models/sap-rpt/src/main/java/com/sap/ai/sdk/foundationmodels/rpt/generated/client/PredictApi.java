package com.sap.ai.sdk.foundationmodels.rpt.generated.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TabCompletionPostRequest;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TabCompletionPostResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * SAP RPT API in version 0.0.1-SNAPSHOT.
 *
 * <p>SAP RPT API for predictive insights using in-context learning on structured business data.
 */
public class PredictApi extends AbstractOpenApiService {
  /**
   * Instantiates this API class to invoke operations on the SAP RPT API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public PredictApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the SAP RPT API based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  @Beta
  public PredictApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Predict targets using SAP RPT model with structured data.
   *
   * <p>
   *
   * <p><b>200</b> - Successful response with predictive insights.
   *
   * <p><b>400</b> - Bad Request - Invalid input.
   *
   * <p><b>422</b> - Unprocessable Content - Invalid input.
   *
   * <p><b>500</b> - Internal Server Error.
   *
   * @param tabCompletionPostRequest The value for the parameter tabCompletionPostRequest
   * @return TabCompletionPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TabCompletionPostResponse completionsCreate(
      @Nonnull final TabCompletionPostRequest tabCompletionPostRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = tabCompletionPostRequest;

    // verify the required parameter 'tabCompletionPostRequest' is set
    if (tabCompletionPostRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'tabCompletionPostRequest' when calling completionsCreate");
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

    final ParameterizedTypeReference<TabCompletionPostResponse> localVarReturnType =
        new ParameterizedTypeReference<TabCompletionPostResponse>() {};
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
}
