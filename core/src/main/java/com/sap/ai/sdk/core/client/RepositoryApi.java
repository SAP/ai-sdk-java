package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryCreationResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryCredentials;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryData;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryDataResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryDeletionResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryDetails;
import com.sap.ai.sdk.core.model.BckndArgoCDRepositoryModificationResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * AI Core in version 2.41.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class RepositoryApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public RepositoryApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public RepositoryApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * On-board a new GitOps repository
   *
   * <p>On-board a new GitOps repository as specified in the content payload
   *
   * <p><b>200</b> - The repository has been on-boarded
   *
   * <p><b>409</b> - The provided repository already exists
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndArgoCDRepositoryData (required) The value for the parameter
   *     bckndArgoCDRepositoryData
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDRepositoryCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryCreationResponse create(
      @Nonnull final BckndArgoCDRepositoryData bckndArgoCDRepositoryData,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndArgoCDRepositoryData;

    // verify the required parameter 'bckndArgoCDRepositoryData' is set
    if (bckndArgoCDRepositoryData == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndArgoCDRepositoryData' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/repositories").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDRepositoryCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDRepositoryCreationResponse>() {};
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

  /**
   * On-board a new GitOps repository
   *
   * <p>On-board a new GitOps repository as specified in the content payload
   *
   * <p><b>200</b> - The repository has been on-boarded
   *
   * <p><b>409</b> - The provided repository already exists
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndArgoCDRepositoryData The value for the parameter bckndArgoCDRepositoryData
   * @return BckndArgoCDRepositoryCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryCreationResponse create(
      @Nonnull final BckndArgoCDRepositoryData bckndArgoCDRepositoryData)
      throws OpenApiRequestException {
    return create(bckndArgoCDRepositoryData, null);
  }

  /**
   * Off-board a repository.
   *
   * <p>Remove a repository from GitOps.
   *
   * <p><b>200</b> - The repository has been off-boarded successfully.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName (required) Name of the repository
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDRepositoryDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDeletionResponse delete(
      @Nonnull final String repositoryName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'repositoryName' is set
    if (repositoryName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'repositoryName' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("repositoryName", repositoryName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/repositories/{repositoryName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDRepositoryDeletionResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDRepositoryDeletionResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.DELETE,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * Off-board a repository.
   *
   * <p>Remove a repository from GitOps.
   *
   * <p><b>200</b> - The repository has been off-boarded successfully.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName Name of the repository
   * @return BckndArgoCDRepositoryDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDeletionResponse delete(@Nonnull final String repositoryName)
      throws OpenApiRequestException {
    return delete(repositoryName, null);
  }

  /**
   * Get the access details for a repository
   *
   * <p>Retrieve the access details for a repository if it exists.
   *
   * <p><b>200</b> - The repository details have been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName (required) Name of the repository
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDRepositoryDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDetails get(
      @Nonnull final String repositoryName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'repositoryName' is set
    if (repositoryName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'repositoryName' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("repositoryName", repositoryName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/repositories/{repositoryName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDRepositoryDetails> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDRepositoryDetails>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * Get the access details for a repository
   *
   * <p>Retrieve the access details for a repository if it exists.
   *
   * <p><b>200</b> - The repository details have been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName Name of the repository
   * @return BckndArgoCDRepositoryDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDetails get(@Nonnull final String repositoryName)
      throws OpenApiRequestException {
    return get(repositoryName, null);
  }

  /**
   * List all GitOps repositories for a tenant
   *
   * <p>Retrieve a list of all GitOps repositories for a tenant.
   *
   * <p><b>200</b> - Returns a list of all GitOps repositories for the tenant.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return BckndArgoCDRepositoryDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDataResponse getAll(
      @Nullable final String authorization,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/repositories").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDRepositoryDataResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDRepositoryDataResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * List all GitOps repositories for a tenant
   *
   * <p>Retrieve a list of all GitOps repositories for a tenant.
   *
   * <p><b>200</b> - Returns a list of all GitOps repositories for the tenant.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndArgoCDRepositoryDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryDataResponse getAll() throws OpenApiRequestException {
    return getAll(null, null, null, null);
  }

  /**
   * Update the repository credentials.
   *
   * <p>Update the referenced repository credentials to synchronize a repository.
   *
   * <p><b>200</b> - The repository credentials have been updated and will eventually be synced.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName (required) Name of the repository
   * @param bckndArgoCDRepositoryCredentials (required) The value for the parameter
   *     bckndArgoCDRepositoryCredentials
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDRepositoryModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryModificationResponse update(
      @Nonnull final String repositoryName,
      @Nonnull final BckndArgoCDRepositoryCredentials bckndArgoCDRepositoryCredentials,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndArgoCDRepositoryCredentials;

    // verify the required parameter 'repositoryName' is set
    if (repositoryName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'repositoryName' when calling update");
    }

    // verify the required parameter 'bckndArgoCDRepositoryCredentials' is set
    if (bckndArgoCDRepositoryCredentials == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndArgoCDRepositoryCredentials' when calling update");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("repositoryName", repositoryName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/repositories/{repositoryName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDRepositoryModificationResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDRepositoryModificationResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.PATCH,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  /**
   * Update the repository credentials.
   *
   * <p>Update the referenced repository credentials to synchronize a repository.
   *
   * <p><b>200</b> - The repository credentials have been updated and will eventually be synced.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param repositoryName Name of the repository
   * @param bckndArgoCDRepositoryCredentials The value for the parameter
   *     bckndArgoCDRepositoryCredentials
   * @return BckndArgoCDRepositoryModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDRepositoryModificationResponse update(
      @Nonnull final String repositoryName,
      @Nonnull final BckndArgoCDRepositoryCredentials bckndArgoCDRepositoryCredentials)
      throws OpenApiRequestException {
    return update(repositoryName, bckndArgoCDRepositoryCredentials, null);
  }
}
