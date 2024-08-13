

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.AiDeploymentBulkModificationRequest ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentBulkModificationResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentCreationRequest ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentCreationResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentDeletionResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentList ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationRequest ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentModificationResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.AiDeploymentResponseWithDetails ; //NOPMD
import com.sap.ai.sdk.core.client.model.ArtifactQuery400Response ; //NOPMD
import java.time.OffsetDateTime ; //NOPMD
import com.sap.ai.sdk.core.client.model.RTAErrorResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.RTALogCommonResponse ; //NOPMD

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

public class DeploymentApi extends AbstractOpenApiService {
    /**
    * Instantiates this API class to invoke operations on the AI Core.
    *
    * @param httpDestination The destination that API should be used with
    */
    public DeploymentApi( @Nonnull final Destination httpDestination )
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
    public DeploymentApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

    
    /**
     * <p>Get number of deployments</p>
     *<p>Retrieve the number of available deployments. The number can be filtered by scenarioId, configurationId, executableIdsList or by deployment status. </p>
     * <p><b>200</b> - Number of deployments
     * <p><b>400</b> - The specification of the resource was incorrect
     * @param aiResourceGroup  (required)
        Specify a resource group id
     * @param executableIds  (optional
        Limit query to only these executable IDs
     * @param configurationId  (optional)
        Configuration identifier
     * @param scenarioId  (optional)
        Scenario identifier
     * @param status  (optional)
        Filter by status
     * @return Integer
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public Integer deploymentCount( @Nonnull final String aiResourceGroup,  @Nullable final List<String> executableIds,  @Nullable final String configurationId,  @Nullable final String scenarioId,  @Nullable final String status) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentCount");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments/$count").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "executableIds", executableIds));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
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
    * <p>Get number of deployments</p>
     *<p>Retrieve the number of available deployments. The number can be filtered by scenarioId, configurationId, executableIdsList or by deployment status. </p>
     * <p><b>200</b> - Number of deployments
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @return Integer
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public Integer deploymentCount( @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
        return deploymentCount(aiResourceGroup, null, null, null, null);
    }
    /**
    * <p>Create deployment</p>
     *<p>Create a deployment using the configuration specified by configurationId after synchronously checking the correctness of the configuration. </p>
     * <p><b>202</b> - The deployment has been scheduled successfully
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @param aiDeploymentCreationRequest
            The value for the parameter aiDeploymentCreationRequest
* @return AiDeploymentCreationResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentCreationResponse deploymentCreate( @Nonnull final String aiResourceGroup,  @Nonnull final AiDeploymentCreationRequest aiDeploymentCreationRequest) throws OpenApiRequestException {
        final Object localVarPostBody = aiDeploymentCreationRequest;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentCreate");
        }
        
        // verify the required parameter 'aiDeploymentCreationRequest' is set
        if (aiDeploymentCreationRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiDeploymentCreationRequest' when calling deploymentCreate");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments").build().toUriString();

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

        final ParameterizedTypeReference<AiDeploymentCreationResponse> localVarReturnType = new ParameterizedTypeReference<AiDeploymentCreationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
    * <p>Mark deployment as deleted</p>
     *<p>Mark deployment with deploymentId as deleted.</p>
     * <p><b>202</b> - The deletion of the deployment has been scheduled successfully
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
     * <p><b>412</b> - The service didn&#39;t meet the precondition needed to execute this operation
* @param aiResourceGroup
        Specify a resource group id
* @param deploymentId
        Deployment identifier
* @return AiDeploymentDeletionResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentDeletionResponse deploymentDelete( @Nonnull final String aiResourceGroup,  @Nonnull final String deploymentId) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentDelete");
        }
        
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'deploymentId' when calling deploymentDelete");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("deploymentId", deploymentId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments/{deploymentId}").buildAndExpand(localVarPathParams).toUriString();

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

        final ParameterizedTypeReference<AiDeploymentDeletionResponse> localVarReturnType = new ParameterizedTypeReference<AiDeploymentDeletionResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Get information about specific deployment</p>
     *<p>Retrieve details for execution with deploymentId.</p>
     * <p><b>200</b> - Information about the deployment
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
     * @param aiResourceGroup  (required)
        Specify a resource group id
     * @param deploymentId  (required)
        Deployment identifier
     * @param $select  (optional)
        Allows to request a specified set of properties for each entity
     * @return AiDeploymentResponseWithDetails
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public AiDeploymentResponseWithDetails deploymentGet( @Nonnull final String aiResourceGroup,  @Nonnull final String deploymentId,  @Nullable final String $select) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentGet");
        }
        
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'deploymentId' when calling deploymentGet");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("deploymentId", deploymentId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments/{deploymentId}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$select", $select));
        

        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiDeploymentResponseWithDetails> localVarReturnType = new ParameterizedTypeReference<AiDeploymentResponseWithDetails>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get information about specific deployment</p>
     *<p>Retrieve details for execution with deploymentId.</p>
     * <p><b>200</b> - Information about the deployment
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
* @param aiResourceGroup
        Specify a resource group id
* @param deploymentId
        Deployment identifier
* @return AiDeploymentResponseWithDetails
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentResponseWithDetails deploymentGet( @Nonnull final String aiResourceGroup,  @Nonnull final String deploymentId) throws OpenApiRequestException {
        return deploymentGet(aiResourceGroup, deploymentId, null);
    }
    /**
    * <p>Update target status or configuration of a deployment</p>
     *<p>Update target status of a deployment to stop a deployment or change the configuration to be used by the deployment after synchronously checking the correctness of the configuration. A change of configuration is only allowed for RUNNING and PENDING deployments. </p>
     * <p><b>202</b> - The modification of the deployment has been scheduled successfully
     * <p><b>400</b> - The specification of the resource was incorrect
     * <p><b>404</b> - The specified resource was not found
     * <p><b>412</b> - The service didn&#39;t meet the precondition needed to execute this operation
* @param aiResourceGroup
        Specify a resource group id
* @param deploymentId
        Deployment identifier
* @param aiDeploymentModificationRequest
            The value for the parameter aiDeploymentModificationRequest
* @return AiDeploymentModificationResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentModificationResponse deploymentModify( @Nonnull final String aiResourceGroup,  @Nonnull final String deploymentId,  @Nonnull final AiDeploymentModificationRequest aiDeploymentModificationRequest) throws OpenApiRequestException {
        final Object localVarPostBody = aiDeploymentModificationRequest;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentModify");
        }
        
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'deploymentId' when calling deploymentModify");
        }
        
        // verify the required parameter 'aiDeploymentModificationRequest' is set
        if (aiDeploymentModificationRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiDeploymentModificationRequest' when calling deploymentModify");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("deploymentId", deploymentId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments/{deploymentId}").buildAndExpand(localVarPathParams).toUriString();

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

        final ParameterizedTypeReference<AiDeploymentModificationResponse> localVarReturnType = new ParameterizedTypeReference<AiDeploymentModificationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.PATCH, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Get list of deployments</p>
     *<p>Retrieve a list of deployments that match the specified filter criteria. Filter criteria include a list of executableIds, a scenarioId, a configurationId, or a deployment status. With top/skip parameters it is possible to paginate the result list. With select parameter it is possible to select only status. </p>
     * <p><b>200</b> - A list of deployments
     * <p><b>400</b> - The specification of the resource was incorrect
     * @param aiResourceGroup  (required)
        Specify a resource group id
     * @param executableIds  (optional
        Limit query to only these executable IDs
     * @param configurationId  (optional)
        Configuration identifier
     * @param scenarioId  (optional)
        Scenario identifier
     * @param status  (optional)
        Filter by status
     * @param $top  (optional, default to 10000)
        Number of results to display
     * @param $skip  (optional)
        Number of results to be skipped from the ordered list of results
     * @param $select  (optional)
        Allows to request a specified set of properties for each entity
     * @return AiDeploymentList
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public AiDeploymentList deploymentQuery( @Nonnull final String aiResourceGroup,  @Nullable final List<String> executableIds,  @Nullable final String configurationId,  @Nullable final String scenarioId,  @Nullable final String status,  @Nullable final Integer $top,  @Nullable final Integer $skip,  @Nullable final String $select) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling deploymentQuery");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "executableIds", executableIds));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$select", $select));
        

        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiDeploymentList> localVarReturnType = new ParameterizedTypeReference<AiDeploymentList>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get list of deployments</p>
     *<p>Retrieve a list of deployments that match the specified filter criteria. Filter criteria include a list of executableIds, a scenarioId, a configurationId, or a deployment status. With top/skip parameters it is possible to paginate the result list. With select parameter it is possible to select only status. </p>
     * <p><b>200</b> - A list of deployments
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @return AiDeploymentList
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentList deploymentQuery( @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
        return deploymentQuery(aiResourceGroup, null, null, null, null, null, null, null);
    }

    /**
     * <p>Get logs of specific deployment</p>
     *<p>Retrieve logs of a deployment for getting insight into the deployment results or failures.</p>
     * <p><b>200</b> - The query was processed successfully and logs of the requested deployment will be returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>401</b> - Lacks valid authentication credentials for the target resource.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param deploymentId  (required)
        Deployment identifier
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param $top  (optional, default to 1000)
        The max number of entries to return. Defaults to 1000. Limited to 5000 max.
     * @param start  (optional)
        The start time for the query as a RFC 3339 datetime format. Defaults to one hour ago. + in timezone need to be encoded to %2B.
     * @param end  (optional)
        The end time for the query as a RFC 3339 datetime format. Defaults to now. + in timezone need to be encoded to %2B.
     * @param $order  (optional)
        Determines the sort order. Supported values are asc or desc. Defaults to asc. Sort order:   * &#x60;asc&#x60; - Ascending, earliest in the order will appear at the top of the list   * &#x60;desc&#x60; - Descending, last in the order will appear at the top of the list 
     * @return RTALogCommonResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public RTALogCommonResponse logs( @Nonnull final String deploymentId,  @Nullable final String authorization,  @Nullable final Integer $top,  @Nullable final OffsetDateTime start,  @Nullable final OffsetDateTime end,  @Nullable final String $order) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new OpenApiRequestException("Missing the required parameter 'deploymentId' when calling logs");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("deploymentId", deploymentId);
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments/{deploymentId}/logs").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end", end));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$order", $order));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<RTALogCommonResponse> localVarReturnType = new ParameterizedTypeReference<RTALogCommonResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get logs of specific deployment</p>
     *<p>Retrieve logs of a deployment for getting insight into the deployment results or failures.</p>
     * <p><b>200</b> - The query was processed successfully and logs of the requested deployment will be returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>401</b> - Lacks valid authentication credentials for the target resource.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @param deploymentId
        Deployment identifier
* @return RTALogCommonResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public RTALogCommonResponse logs( @Nonnull final String deploymentId) throws OpenApiRequestException {
        return logs(deploymentId, null, null, null, null, null);
    }
    /**
    * <p>Patch multiple deployments</p>
     *<p>Update status of multiple deployments. stop or delete multiple deployments.</p>
     * <p><b>202</b> - The modification of the deployments have been scheduled successfully
     * <p><b>400</b> - The specification of the resource was incorrect
* @param aiResourceGroup
        Specify a resource group id
* @param aiDeploymentBulkModificationRequest
            The value for the parameter aiDeploymentBulkModificationRequest
* @return AiDeploymentBulkModificationResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public AiDeploymentBulkModificationResponse modify( @Nonnull final String aiResourceGroup,  @Nonnull final AiDeploymentBulkModificationRequest aiDeploymentBulkModificationRequest) throws OpenApiRequestException {
        final Object localVarPostBody = aiDeploymentBulkModificationRequest;
        
        // verify the required parameter 'aiResourceGroup' is set
        if (aiResourceGroup == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiResourceGroup' when calling modify");
        }
        
        // verify the required parameter 'aiDeploymentBulkModificationRequest' is set
        if (aiDeploymentBulkModificationRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'aiDeploymentBulkModificationRequest' when calling modify");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/lm/deployments").build().toUriString();

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
            "application/merge-patch+json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<AiDeploymentBulkModificationResponse> localVarReturnType = new ParameterizedTypeReference<AiDeploymentBulkModificationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.PATCH, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
