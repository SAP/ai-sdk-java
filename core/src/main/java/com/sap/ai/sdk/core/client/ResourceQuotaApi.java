

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.BckndCommonResourceQuotaResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndErrorResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndExecutableResourceQuotaResponse ; //NOPMD

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

public class ResourceQuotaApi extends AbstractOpenApiService {
    /**
    * Instantiates this API class to invoke operations on the AI Core.
    *
    * @param httpDestination The destination that API should be used with
    */
    public ResourceQuotaApi( @Nonnull final Destination httpDestination )
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
    public ResourceQuotaApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

    
    /**
     * <p>Get the quota for applications</p>
     *<p>Get the details about quota and usage for applications</p>
     * <p><b>200</b> - quota for applications
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndCommonResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndCommonResourceQuotaResponse getApplicationQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/applications").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for applications</p>
     *<p>Get the details about quota and usage for applications</p>
     * <p><b>200</b> - quota for applications
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndCommonResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndCommonResourceQuotaResponse getApplicationQuota() throws OpenApiRequestException {
        return getApplicationQuota(null, null);
    }

    /**
     * <p>Get the quota for docker registry secrets</p>
     *<p>Get the details about quota and usage for docker registry secrets</p>
     * <p><b>200</b> - quota for generic secrets on the tenant level
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndCommonResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndCommonResourceQuotaResponse getDockerRegistrySecretQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/dockerRegistrySecrets").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for docker registry secrets</p>
     *<p>Get the details about quota and usage for docker registry secrets</p>
     * <p><b>200</b> - quota for generic secrets on the tenant level
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndCommonResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndCommonResourceQuotaResponse getDockerRegistrySecretQuota() throws OpenApiRequestException {
        return getDockerRegistrySecretQuota(null, null);
    }

    /**
     * <p>Get the quota for executables</p>
     *<p>Get the details about quota and usage for executables</p>
     * <p><b>200</b> - quota for executable
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndExecutableResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndExecutableResourceQuotaResponse getExecutableQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/executables").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndExecutableResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndExecutableResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for executables</p>
     *<p>Get the details about quota and usage for executables</p>
     * <p><b>200</b> - quota for executable
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndExecutableResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndExecutableResourceQuotaResponse getExecutableQuota() throws OpenApiRequestException {
        return getExecutableQuota(null, null);
    }

    /**
     * <p>Get the quota for tenant-level generic secrets</p>
     *<p>Get the details about quota and usage for tenant-level generic secrets</p>
     * <p><b>200</b> - quota for generic secrets on the tenant level
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndCommonResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndCommonResourceQuotaResponse getGenericSecretQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/secrets").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for tenant-level generic secrets</p>
     *<p>Get the details about quota and usage for tenant-level generic secrets</p>
     * <p><b>200</b> - quota for generic secrets on the tenant level
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndCommonResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndCommonResourceQuotaResponse getGenericSecretQuota() throws OpenApiRequestException {
        return getGenericSecretQuota(null, null);
    }

    /**
     * <p>Get the quota for repositories</p>
     *<p>Get the details about quota and usage for repositories</p>
     * <p><b>200</b> - quota for repositories
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndCommonResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndCommonResourceQuotaResponse getRepositoryQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/repositories").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for repositories</p>
     *<p>Get the details about quota and usage for repositories</p>
     * <p><b>200</b> - quota for repositories
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndCommonResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndCommonResourceQuotaResponse getRepositoryQuota() throws OpenApiRequestException {
        return getRepositoryQuota(null, null);
    }

    /**
     * <p>Get the quota for resource groups</p>
     *<p>Get the details about quota and usage for resource groups</p>
     * <p><b>200</b> - quota for resourcegroups
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param quotaOnly  (optional)
        When being set to true, the response contains only the quota of the resource and not the quota usage.
     * @return BckndCommonResourceQuotaResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndCommonResourceQuotaResponse getResourceGroupQuota( @Nullable final String authorization,  @Nullable final Boolean quotaOnly) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/resourceQuota/resourceGroups").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType = new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Get the quota for resource groups</p>
     *<p>Get the details about quota and usage for resource groups</p>
     * <p><b>200</b> - quota for resourcegroups
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndCommonResourceQuotaResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndCommonResourceQuotaResponse getResourceGroupQuota() throws OpenApiRequestException {
        return getResourceGroupQuota(null, null);
    }
}
