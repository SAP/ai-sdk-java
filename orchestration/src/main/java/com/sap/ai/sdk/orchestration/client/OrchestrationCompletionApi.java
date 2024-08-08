

package com.sap.ai.sdk.orchestration.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest ; //NOPMD
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse ; //NOPMD
import com.sap.ai.sdk.orchestration.client.model.ErrorResponse ; //NOPMD

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.google.common.annotations.Beta;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;

/**
* Internal Orchestration Service API in version 0.0.1.
*
* SAP AI Core - Orchestration Service API
*/

public class OrchestrationCompletionApi extends AbstractOpenApiService {
    /**
    * Instantiates this API class to invoke operations on the Internal Orchestration Service API.
    *
    * @param httpDestination The destination that API should be used with
    */
    public OrchestrationCompletionApi( @Nonnull final Destination httpDestination )
    {
        super(httpDestination);
    }

    /**
    * Instantiates this API class to invoke operations on the Internal Orchestration Service API based on a given {@link ApiClient}.
    *
    * @param apiClient
    *            ApiClient to invoke the API on
    */
    @Beta
    public OrchestrationCompletionApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

        /**
    * <p></p>
     *<p></p>
     * <p><b>200</b> - Successful response
     * <p><b>400</b> - Bad Request
     * <p><b>0</b> - Common Error
* @param completionPostRequest
            The value for the parameter completionPostRequest
* @return CompletionPostResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public CompletionPostResponse orchestrationV1EndpointsCreate( @Nonnull final CompletionPostRequest completionPostRequest) throws OpenApiRequestException {
        final Object localVarPostBody = completionPostRequest;
        
        // verify the required parameter 'completionPostRequest' is set
        if (completionPostRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'completionPostRequest' when calling orchestrationV1EndpointsCreate");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/completion").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] {  };

        final ParameterizedTypeReference<CompletionPostResponse> localVarReturnType = new ParameterizedTypeReference<CompletionPostResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
