

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.core.client.model.ArtifactQuery400Response;

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
 * AI Core in version 2.32.1.
 *
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 */
public class ModelApi extends AbstractOpenApiService {
    /**
     * Instantiates this API class to invoke operations on the AI Core.
     *
     * @param httpDestination The destination that API should be used with
     */
    public ModelApi( @Nonnull final Destination httpDestination )
    {
        super(httpDestination);
    }

    /**
     * Instantiates this API class to invoke operations on the AI Core based on a given {@link ApiClient}.
     *
     * @param apiClient
     *            ApiClient to invoke the API on
     */
    @Beta
    public ModelApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

        /**
     * <p>Get information about all models available in LLM global scenario</p>
     * <p>Retrieve information about all models available in LLM global scenario</p>
     * <p><b>200</b> - The request was successful and information of all LLM models will be returned.
     * <p><b>400</b> - The specification of the resource was incorrect
     * @param scenarioId
     *      Scenario identifier
     * @return AiModelList
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public AiModelList modelsGet( @Nonnull final String scenarioId) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'scenarioId' is set
        if (scenarioId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'scenarioId' when calling modelsGet");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("scenarioId", scenarioId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}/models").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiModelList> localVarReturnType = new ParameterizedTypeReference<AiModelList>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
