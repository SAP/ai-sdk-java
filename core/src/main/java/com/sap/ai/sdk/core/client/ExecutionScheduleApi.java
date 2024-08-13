

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.AiExecutionSchedule ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleCreationData ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleCreationResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleDeletionResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleList ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleModificationRequest ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiExecutionScheduleModificationResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.ArtifactQuery400Response ; //NOPMD

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
* AI Core in version 2.33.0.
*
* Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
*/

public class ExecutionScheduleApi extends AbstractOpenApiService {
    /**
    * Instantiates this API class to invoke operations on the AI Core.
    *
    * @param httpDestination The destination that API should be used with
    */
    public ExecutionScheduleApi( @Nonnull final Destination httpDestination )
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
    public ExecutionScheduleApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

    
    /**
     * <p>Get number of execution schedules</p>
     *<p>Retrieve the number of scheduled executions. The number can be filtered by configurationId or executionScheduleStatus. </p>
     * <p><b>200</b> - Number of execution schedules
     * <p><b>400</b> - The specification of the resource was incorrect
     * @param aiResourceGroup  (required)
        Specify a resource group id
     * @param configurationId  (optional)
        Configuration identifier
     * @param status  (optional)
        Execution Schedule status
     * @return Integer
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public Integer scheduleCount( @Nonnull final String aiResourceGroup,  @Nullable final String configurationId,  @Nullable final String status) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleCount");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules/$count").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
        

        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "text/plain", "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<Integer> localVarReturnType = new ParameterizedTypeReference<Integer>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get number of execution schedules</p>
     *<p>Retrieve the number of scheduled executions. The number can be filtered by configurationId or executionScheduleStatus. </p>
     * <p><b>200</b> - Number of execution schedules
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @return Integer
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public Integer scheduleCount( @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
        return scheduleCount(aiResourceGroup, null, null);
    }
    /**
    * <p>Create execution schedule</p>
     *<p>Create an execution schedule using the configuration specified by configurationId, and schedule.</p>
     * <p><b>202</b> - The execution schedule has been created successfully
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @param aiExecutionScheduleCreationData
            The value for the parameter aiExecutionScheduleCreationData
* @return AiExecutionScheduleCreationResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiExecutionScheduleCreationResponse scheduleCreate( @Nonnull final String aiResourceGroup,  @Nonnull final AiExecutionScheduleCreationData aiExecutionScheduleCreationData) throws OpenApiRequestException {
        final Object localVarPostBody = aiExecutionScheduleCreationData;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleCreate");
        }
        
        // verify the required parameter 'aiExecutionScheduleCreationData' is set
        if (aiExecutionScheduleCreationData == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiExecutionScheduleCreationData' when calling scheduleCreate");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (aiResourceGroup != null)
        localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiExecutionScheduleCreationResponse> localVarReturnType = new ParameterizedTypeReference<AiExecutionScheduleCreationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
    * <p>Delete execution schedule</p>
     *<p>Delete the execution schedule with executionScheduleId.</p>
     * <p><b>202</b> - The execution schedule has been deleted successfully
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
* @param aiResourceGroup
        Specify a resource group id
* @param executionScheduleId
        Execution Schedule identifier
* @return AiExecutionScheduleDeletionResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiExecutionScheduleDeletionResponse scheduleDelete( @Nonnull final String aiResourceGroup,  @Nonnull final String executionScheduleId) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleDelete");
        }
        
        // verify the required parameter 'executionScheduleId' is set
        if (executionScheduleId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'executionScheduleId' when calling scheduleDelete");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("executionScheduleId", executionScheduleId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules/{executionScheduleId}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (aiResourceGroup != null)
        localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiExecutionScheduleDeletionResponse> localVarReturnType = new ParameterizedTypeReference<AiExecutionScheduleDeletionResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
    * <p>Get information about an execution schedule</p>
     *<p>Retrieve details for execution schedule with executionScheduleId.</p>
     * <p><b>200</b> - Information about the execution schedule
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
* @param aiResourceGroup
        Specify a resource group id
* @param executionScheduleId
        Execution Schedule identifier
* @return AiExecutionSchedule
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiExecutionSchedule scheduleGet( @Nonnull final String aiResourceGroup,  @Nonnull final String executionScheduleId) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleGet");
        }
        
        // verify the required parameter 'executionScheduleId' is set
        if (executionScheduleId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'executionScheduleId' when calling scheduleGet");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("executionScheduleId", executionScheduleId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules/{executionScheduleId}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (aiResourceGroup != null)
        localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiExecutionSchedule> localVarReturnType = new ParameterizedTypeReference<AiExecutionSchedule>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
    * <p>Update an execution schedule</p>
     *<p>Update details of an execution schedule</p>
     * <p><b>202</b> - The execution schedule has been modified successfully
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
* @param aiResourceGroup
        Specify a resource group id
* @param executionScheduleId
        Execution Schedule identifier
* @param aiExecutionScheduleModificationRequest
            The value for the parameter aiExecutionScheduleModificationRequest
* @return AiExecutionScheduleModificationResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiExecutionScheduleModificationResponse scheduleModify( @Nonnull final String aiResourceGroup,  @Nonnull final String executionScheduleId,  @Nonnull final AiExecutionScheduleModificationRequest aiExecutionScheduleModificationRequest) throws OpenApiRequestException {
        final Object localVarPostBody = aiExecutionScheduleModificationRequest;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleModify");
        }
        
        // verify the required parameter 'executionScheduleId' is set
        if (executionScheduleId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'executionScheduleId' when calling scheduleModify");
        }
        
        // verify the required parameter 'aiExecutionScheduleModificationRequest' is set
        if (aiExecutionScheduleModificationRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiExecutionScheduleModificationRequest' when calling scheduleModify");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("executionScheduleId", executionScheduleId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules/{executionScheduleId}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (aiResourceGroup != null)
        localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiExecutionScheduleModificationResponse> localVarReturnType = new ParameterizedTypeReference<AiExecutionScheduleModificationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.PATCH, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Get list of execution schedules</p>
     *<p>Retrieve a list of execution schedules that match the specified filter criteria. Filter criteria include executionScheduleStatus or a configurationId. With top/skip parameters it is possible to paginate the result list. </p>
     * <p><b>200</b> - A list of execution schedules
     * <p><b>400</b> - The specification of the resource was incorrect
     * @param aiResourceGroup  (required)
        Specify a resource group id
     * @param configurationId  (optional)
        Configuration identifier
     * @param status  (optional)
        Execution Schedule status
     * @param $top  (optional, default to 10000)
        Number of results to display
     * @param $skip  (optional)
        Number of results to be skipped from the ordered list of results
     * @return AiExecutionScheduleList
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public AiExecutionScheduleList scheduleQuery( @Nonnull final String aiResourceGroup,  @Nullable final String configurationId,  @Nullable final String status,  @Nullable final Integer $top,  @Nullable final Integer $skip) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling scheduleQuery");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/executionSchedules").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
        

        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiExecutionScheduleList> localVarReturnType = new ParameterizedTypeReference<AiExecutionScheduleList>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get list of execution schedules</p>
     *<p>Retrieve a list of execution schedules that match the specified filter criteria. Filter criteria include executionScheduleStatus or a configurationId. With top/skip parameters it is possible to paginate the result list. </p>
     * <p><b>200</b> - A list of execution schedules
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @return AiExecutionScheduleList
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiExecutionScheduleList scheduleQuery( @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
        return scheduleQuery(aiResourceGroup, null, null, null, null);
    }
}
