

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.BckndAllArgoCDApplicationData;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationBaseData;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationCreationResponse;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationData;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationDeletionResponse;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationModificationResponse;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationRefreshResponse;
import com.sap.ai.sdk.core.client.model.BckndArgoCDApplicationStatus;
import com.sap.ai.sdk.core.client.model.BckndErrorResponse;
import com.sap.ai.sdk.core.client.model.KubesubmitV4ApplicationsCreateRequest;

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
public class ApplicationApi extends AbstractOpenApiService {
    /**
     * Instantiates this API class to invoke operations on the AI Core.
     *
     * @param httpDestination The destination that API should be used with
     */
    public ApplicationApi( @Nonnull final Destination httpDestination )
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
    public ApplicationApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

    
    /**
     * <p>Create an application</p>
     *<p>Create an ArgoCD application to synchronise a repository. </p>
     * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised with the repository.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param kubesubmitV4ApplicationsCreateRequest  (required)
        The value for the parameter kubesubmitV4ApplicationsCreateRequest
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationCreationResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationCreationResponse applicationsCreate( @Nonnull final KubesubmitV4ApplicationsCreateRequest kubesubmitV4ApplicationsCreateRequest,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = kubesubmitV4ApplicationsCreateRequest;
        
        // verify the required parameter 'kubesubmitV4ApplicationsCreateRequest' is set
        if (kubesubmitV4ApplicationsCreateRequest == null) {
            throw new OpenApiRequestException("Missing the required parameter 'kubesubmitV4ApplicationsCreateRequest' when calling applicationsCreate");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationCreationResponse> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationCreationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Create an application</p>
     * <p>Create an ArgoCD application to synchronise a repository. </p>
     * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised with the repository.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param kubesubmitV4ApplicationsCreateRequest
     *      The value for the parameter kubesubmitV4ApplicationsCreateRequest
     * @return BckndArgoCDApplicationCreationResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationCreationResponse applicationsCreate( @Nonnull final KubesubmitV4ApplicationsCreateRequest kubesubmitV4ApplicationsCreateRequest) throws OpenApiRequestException {
        return applicationsCreate(kubesubmitV4ApplicationsCreateRequest, null);
    }

    /**
     * <p>Delete application</p>
     *<p>Delete an ArgoCD application</p>
     * <p><b>200</b> - The argoCD application has been deleted successfully.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName  (required)
        Name of the ArgoCD application
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationDeletionResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationDeletionResponse applicationsDelete( @Nonnull final String applicationName,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'applicationName' when calling applicationsDelete");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("applicationName", applicationName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications/{applicationName}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationDeletionResponse> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationDeletionResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Delete application</p>
     * <p>Delete an ArgoCD application</p>
     * <p><b>200</b> - The argoCD application has been deleted successfully.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName
     *      Name of the ArgoCD application
     * @return BckndArgoCDApplicationDeletionResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationDeletionResponse applicationsDelete( @Nonnull final String applicationName) throws OpenApiRequestException {
        return applicationsDelete(applicationName, null);
    }

    /**
     * <p>Get ArgoCD application</p>
     *<p>Retrieve the ArgoCD application details. </p>
     * <p><b>200</b> - The application has been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName  (required)
        Name of the ArgoCD application
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationData
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationData applicationsGet( @Nonnull final String applicationName,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'applicationName' when calling applicationsGet");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("applicationName", applicationName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications/{applicationName}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationData> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationData>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Get ArgoCD application</p>
     * <p>Retrieve the ArgoCD application details. </p>
     * <p><b>200</b> - The application has been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName
     *      Name of the ArgoCD application
     * @return BckndArgoCDApplicationData
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationData applicationsGet( @Nonnull final String applicationName) throws OpenApiRequestException {
        return applicationsGet(applicationName, null);
    }

    /**
     * <p>Return all applications</p>
     *<p>Return all Argo CD application data objects. </p>
     * <p><b>200</b> - All applications have been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param $top  (optional)
        Number of results to display
     * @param $skip  (optional)
        Number of results to be skipped from the ordered list of results
     * @param $count  (optional)
        When the $count field is set to false, the response contains a count of the items present in the response. When the $count field is set to true, the response contains a count of all the items present on the server, and not just the ones in the response. When the $count field is not passed, it is false by default.
     * @return BckndAllArgoCDApplicationData
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndAllArgoCDApplicationData applicationsGetAll( @Nullable final String authorization,  @Nullable final Integer $top,  @Nullable final Integer $skip,  @Nullable final Boolean $count) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndAllArgoCDApplicationData> localVarReturnType = new ParameterizedTypeReference<BckndAllArgoCDApplicationData>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Return all applications</p>
     * <p>Return all Argo CD application data objects. </p>
     * <p><b>200</b> - All applications have been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @return BckndAllArgoCDApplicationData
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndAllArgoCDApplicationData applicationsGetAll() throws OpenApiRequestException {
        return applicationsGetAll(null, null, null, null);
    }

    /**
     * <p>Returns the ArgoCD application status</p>
     *<p>Returns the ArgoCD application health and sync status. </p>
     * <p><b>200</b> - The application status has been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName  (required)
        Name of the ArgoCD application
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationStatus
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationStatus applicationsGetStatus( @Nonnull final String applicationName,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'applicationName' when calling applicationsGetStatus");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("applicationName", applicationName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications/{applicationName}/status").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationStatus> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationStatus>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Returns the ArgoCD application status</p>
     * <p>Returns the ArgoCD application health and sync status. </p>
     * <p><b>200</b> - The application status has been found and returned.
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName
     *      Name of the ArgoCD application
     * @return BckndArgoCDApplicationStatus
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationStatus applicationsGetStatus( @Nonnull final String applicationName) throws OpenApiRequestException {
        return applicationsGetStatus(applicationName, null);
    }

    /**
     * <p>Makes ArgoDC refresh the specified application</p>
     *<p>Schedules a refresh of the specified application that will be picked up by ArgoCD asynchronously </p>
     * <p><b>202</b> - A refresh of the application has been scheduled
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName  (required)
        Name of the ArgoCD application
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationRefreshResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationRefreshResponse applicationsRefresh( @Nonnull final String applicationName,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'applicationName' when calling applicationsRefresh");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("applicationName", applicationName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications/{applicationName}/refresh").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationRefreshResponse> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationRefreshResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Makes ArgoDC refresh the specified application</p>
     * <p>Schedules a refresh of the specified application that will be picked up by ArgoCD asynchronously </p>
     * <p><b>202</b> - A refresh of the application has been scheduled
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName
     *      Name of the ArgoCD application
     * @return BckndArgoCDApplicationRefreshResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationRefreshResponse applicationsRefresh( @Nonnull final String applicationName) throws OpenApiRequestException {
        return applicationsRefresh(applicationName, null);
    }

    /**
     * <p>Update the ArgoCD application.</p>
     *<p>Update the referenced ArgoCD application to synchronize the repository. </p>
     * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised with the repository.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName  (required)
        Name of the ArgoCD application
     * @param bckndArgoCDApplicationBaseData  (required)
        The value for the parameter bckndArgoCDApplicationBaseData
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @return BckndArgoCDApplicationModificationResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationModificationResponse applicationsUpdate( @Nonnull final String applicationName,  @Nonnull final BckndArgoCDApplicationBaseData bckndArgoCDApplicationBaseData,  @Nullable final String authorization) throws OpenApiRequestException {
        final Object localVarPostBody = bckndArgoCDApplicationBaseData;
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'applicationName' when calling applicationsUpdate");
        }
        
        // verify the required parameter 'bckndArgoCDApplicationBaseData' is set
        if (bckndArgoCDApplicationBaseData == null) {
            throw new OpenApiRequestException("Missing the required parameter 'bckndArgoCDApplicationBaseData' when calling applicationsUpdate");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("applicationName", applicationName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/applications/{applicationName}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndArgoCDApplicationModificationResponse> localVarReturnType = new ParameterizedTypeReference<BckndArgoCDApplicationModificationResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.PATCH, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * <p>Update the ArgoCD application.</p>
     * <p>Update the referenced ArgoCD application to synchronize the repository. </p>
     * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised with the repository.
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param applicationName
     *      Name of the ArgoCD application
     * @param bckndArgoCDApplicationBaseData
     *      The value for the parameter bckndArgoCDApplicationBaseData
     * @return BckndArgoCDApplicationModificationResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nonnull
    public BckndArgoCDApplicationModificationResponse applicationsUpdate( @Nonnull final String applicationName,  @Nonnull final BckndArgoCDApplicationBaseData bckndArgoCDApplicationBaseData) throws OpenApiRequestException {
        return applicationsUpdate(applicationName, bckndArgoCDApplicationBaseData, null);
    }
}
