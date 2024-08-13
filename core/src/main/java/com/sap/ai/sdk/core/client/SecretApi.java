

package com.sap.ai.sdk.core.client;

import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import com.sap.ai.sdk.core.client.model.BckndErrorResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndGenericSecretDataResponse ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndGenericSecretPatchBody ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndGenericSecretPostBody ; //NOPMD
import com.sap.ai.sdk.core.client.model.BckndListGenericSecretsResponse ; //NOPMD

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

public class SecretApi extends AbstractOpenApiService {
    /**
    * Instantiates this API class to invoke operations on the AI Core.
    *
    * @param httpDestination The destination that API should be used with
    */
    public SecretApi( @Nonnull final Destination httpDestination )
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
    public SecretApi( @Nonnull final ApiClient apiClient )
    {
         super(apiClient);
    }

    
    /**
     * <p>Create a new generic secret</p>
     *<p>Create a new generic secret in the corresponding resource group or at main tenant level.</p>
     * <p><b>200</b> - Secret has been created
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param bckndGenericSecretPostBody  (required)
        The value for the parameter bckndGenericSecretPostBody
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param aiResourceGroup  (optional)
        Specify an existing resource group id to use
     * @param aiTenantScope  (optional)
        Specify whether the main tenant scope is to be used
     * @return BckndGenericSecretDataResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndGenericSecretDataResponse create( @Nonnull final BckndGenericSecretPostBody bckndGenericSecretPostBody,  @Nullable final String authorization,  @Nullable final String aiResourceGroup,  @Nullable final Boolean aiTenantScope) throws OpenApiRequestException {
        final Object localVarPostBody = bckndGenericSecretPostBody;
        
        // verify the required parameter 'bckndGenericSecretPostBody' is set
        if (bckndGenericSecretPostBody == null) {
            throw new OpenApiRequestException("Missing the required parameter 'bckndGenericSecretPostBody' when calling create");
        }
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/secrets").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
        if (aiTenantScope != null)
            localVarHeaderParams.add("AI-Tenant-Scope", apiClient.parameterToString(aiTenantScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndGenericSecretDataResponse> localVarReturnType = new ParameterizedTypeReference<BckndGenericSecretDataResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Create a new generic secret</p>
     *<p>Create a new generic secret in the corresponding resource group or at main tenant level.</p>
     * <p><b>200</b> - Secret has been created
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @param bckndGenericSecretPostBody
            The value for the parameter bckndGenericSecretPostBody
* @return BckndGenericSecretDataResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndGenericSecretDataResponse create( @Nonnull final BckndGenericSecretPostBody bckndGenericSecretPostBody) throws OpenApiRequestException {
        return create(bckndGenericSecretPostBody, null, null, null);
    }

    /**
     * <p>Deletes the secret</p>
     *<p>Deletes the secret from provided resource group namespace</p>
     * <p><b>200</b> - The secret has been removed
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param secretName  (required)
        The value for the parameter secretName
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param aiResourceGroup  (optional)
        Specify an existing resource group id to use
     * @param aiTenantScope  (optional)
        Specify whether the main tenant scope is to be used
     * @return An OpenApiResponse containing the status code of the HttpResponse.
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
     @Nonnull public OpenApiResponse delete( @Nonnull final String secretName,  @Nullable final String authorization,  @Nullable final String aiResourceGroup,  @Nullable final Boolean aiTenantScope) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        // verify the required parameter 'secretName' is set
        if (secretName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'secretName' when calling delete");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("secretName", secretName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/secrets/{secretName}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
        if (aiTenantScope != null)
            localVarHeaderParams.add("AI-Tenant-Scope", apiClient.parameterToString(aiTenantScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
        return new OpenApiResponse(apiClient);
    }

    /**
    * <p>Deletes the secret</p>
     *<p>Deletes the secret from provided resource group namespace</p>
     * <p><b>200</b> - The secret has been removed
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @param secretName
            The value for the parameter secretName
* @return An OpenApiResponse containing the status code of the HttpResponse.
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
     @Nonnull  public OpenApiResponse delete( @Nonnull final String secretName) throws OpenApiRequestException {
        return delete(secretName, null, null, null);
    }

    /**
     * <p>Lists all secrets corresponding to tenant</p>
     *<p>Lists all secrets corresponding to tenant. This retrieves metadata only, not the secret data itself.</p>
     * <p><b>200</b> - The secrets were fetched
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
     * @param aiResourceGroup  (optional)
        Specify an existing resource group id to use
     * @param aiTenantScope  (optional)
        Specify whether the main tenant scope is to be used
     * @return BckndListGenericSecretsResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndListGenericSecretsResponse get( @Nullable final String authorization,  @Nullable final Integer $top,  @Nullable final Integer $skip,  @Nullable final Boolean $count,  @Nullable final String aiResourceGroup,  @Nullable final Boolean aiTenantScope) throws OpenApiRequestException {
        final Object localVarPostBody = null;
        
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/secrets").build().toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
                localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));
        

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
        if (aiTenantScope != null)
            localVarHeaderParams.add("AI-Tenant-Scope", apiClient.parameterToString(aiTenantScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndListGenericSecretsResponse> localVarReturnType = new ParameterizedTypeReference<BckndListGenericSecretsResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Lists all secrets corresponding to tenant</p>
     *<p>Lists all secrets corresponding to tenant. This retrieves metadata only, not the secret data itself.</p>
     * <p><b>200</b> - The secrets were fetched
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @return BckndListGenericSecretsResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndListGenericSecretsResponse get() throws OpenApiRequestException {
        return get(null, null, null, null, null, null);
    }

    /**
     * <p>Update secret credentials</p>
     *<p>Update secret credentials. Replace secret data with the provided data.</p>
     * <p><b>200</b> - The secret has been updated
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
     * @param secretName  (required)
        The value for the parameter secretName
     * @param bckndGenericSecretPatchBody  (required)
        The value for the parameter bckndGenericSecretPatchBody
     * @param authorization  (optional)
        Authorization bearer token containing a JWT token.
     * @param aiResourceGroup  (optional)
        Specify an existing resource group id to use
     * @param aiTenantScope  (optional)
        Specify whether the main tenant scope is to be used
     * @return BckndGenericSecretDataResponse
     * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable  public BckndGenericSecretDataResponse update( @Nonnull final String secretName,  @Nonnull final BckndGenericSecretPatchBody bckndGenericSecretPatchBody,  @Nullable final String authorization,  @Nullable final String aiResourceGroup,  @Nullable final Boolean aiTenantScope) throws OpenApiRequestException {
        final Object localVarPostBody = bckndGenericSecretPatchBody;
        
        // verify the required parameter 'secretName' is set
        if (secretName == null) {
            throw new OpenApiRequestException("Missing the required parameter 'secretName' when calling update");
        }
        
        // verify the required parameter 'bckndGenericSecretPatchBody' is set
        if (bckndGenericSecretPatchBody == null) {
            throw new OpenApiRequestException("Missing the required parameter 'bckndGenericSecretPatchBody' when calling update");
        }
        
        // create path and map variables
        final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
        localVarPathParams.put("secretName", secretName);
        final String localVarPath = UriComponentsBuilder.fromPath("/admin/secrets/{secretName}").buildAndExpand(localVarPathParams).toUriString();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        if (authorization != null)
            localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
        if (aiResourceGroup != null)
            localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
        if (aiTenantScope != null)
            localVarHeaderParams.add("AI-Tenant-Scope", apiClient.parameterToString(aiTenantScope));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        final String[] localVarAuthNames = new String[] { "Oauth2" };

        final ParameterizedTypeReference<BckndGenericSecretDataResponse> localVarReturnType = new ParameterizedTypeReference<BckndGenericSecretDataResponse>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.PATCH, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
    * <p>Update secret credentials</p>
     *<p>Update secret credentials. Replace secret data with the provided data.</p>
     * <p><b>200</b> - The secret has been updated
     * <p><b>404</b> - The specified resource was not found
     * <p><b>400</b> - The request was malformed and could thus not be processed.
     * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
* @param secretName
            The value for the parameter secretName
* @param bckndGenericSecretPatchBody
            The value for the parameter bckndGenericSecretPatchBody
* @return BckndGenericSecretDataResponse
* @throws OpenApiRequestException if an error occurs while attempting to invoke the API
     */
    @Nullable   public BckndGenericSecretDataResponse update( @Nonnull final String secretName,  @Nonnull final BckndGenericSecretPatchBody bckndGenericSecretPatchBody) throws OpenApiRequestException {
        return update(secretName, bckndGenericSecretPatchBody, null, null, null);
    }
}
