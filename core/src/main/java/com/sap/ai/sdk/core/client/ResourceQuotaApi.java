package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndCommonResourceQuotaResponse;
import com.sap.ai.sdk.core.model.BckndDeploymentResourceQuotaResponse;
import com.sap.ai.sdk.core.model.BckndExecutableResourceQuotaResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.List;
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
public class ResourceQuotaApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ResourceQuotaApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ResourceQuotaApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get the quota for applications
   *
   * <p>Get the details about quota and usage for applications
   *
   * <p><b>200</b> - quota for applications
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getApplicationQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/applications").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
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
   * Get the quota for applications
   *
   * <p>Get the details about quota and usage for applications
   *
   * <p><b>200</b> - quota for applications
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getApplicationQuota() throws OpenApiRequestException {
    return getApplicationQuota(null, null);
  }

  /**
   * Get the quota for deployments
   *
   * <p>Get the details about quota and usage for deployments
   *
   * <p><b>200</b> - A resource quota object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndDeploymentResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndDeploymentResourceQuotaResponse getDeploymentQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/deployments").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndDeploymentResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndDeploymentResourceQuotaResponse>() {};
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
   * Get the quota for deployments
   *
   * <p>Get the details about quota and usage for deployments
   *
   * <p><b>200</b> - A resource quota object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndDeploymentResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndDeploymentResourceQuotaResponse getDeploymentQuota() throws OpenApiRequestException {
    return getDeploymentQuota(null, null);
  }

  /**
   * Get the quota for docker registry secrets
   *
   * <p>Get the details about quota and usage for docker registry secrets
   *
   * <p><b>200</b> - quota for generic secrets on the tenant level
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getDockerRegistrySecretQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/dockerRegistrySecrets")
            .build()
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
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
   * Get the quota for docker registry secrets
   *
   * <p>Get the details about quota and usage for docker registry secrets
   *
   * <p><b>200</b> - quota for generic secrets on the tenant level
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getDockerRegistrySecretQuota()
      throws OpenApiRequestException {
    return getDockerRegistrySecretQuota(null, null);
  }

  /**
   * Get the quota for executables
   *
   * <p>Get the details about quota and usage for executables
   *
   * <p><b>200</b> - quota for executable
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndExecutableResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndExecutableResourceQuotaResponse getExecutableQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/executables").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndExecutableResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndExecutableResourceQuotaResponse>() {};
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
   * Get the quota for executables
   *
   * <p>Get the details about quota and usage for executables
   *
   * <p><b>200</b> - quota for executable
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndExecutableResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndExecutableResourceQuotaResponse getExecutableQuota() throws OpenApiRequestException {
    return getExecutableQuota(null, null);
  }

  /**
   * Get the quota for tenant-scoped or tenant-wide generic secrets
   *
   * <p>Get the details about quota and usage for tenant-scoped or tenant-wide generic secrets
   *
   * <p><b>200</b> - quota for generic secrets on the tenant level
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getGenericSecretQuota(
      @Nullable final String authorization,
      @Nullable final Boolean quotaOnly,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/secrets").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiTenantScope != null)
      localVarHeaderParams.add("AI-Tenant-Scope", apiClient.parameterToString(aiTenantScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
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
   * Get the quota for tenant-scoped or tenant-wide generic secrets
   *
   * <p>Get the details about quota and usage for tenant-scoped or tenant-wide generic secrets
   *
   * <p><b>200</b> - quota for generic secrets on the tenant level
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getGenericSecretQuota() throws OpenApiRequestException {
    return getGenericSecretQuota(null, null, null, null);
  }

  /**
   * Get the quota for repositories
   *
   * <p>Get the details about quota and usage for repositories
   *
   * <p><b>200</b> - quota for repositories
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getRepositoryQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/repositories").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
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
   * Get the quota for repositories
   *
   * <p>Get the details about quota and usage for repositories
   *
   * <p><b>200</b> - quota for repositories
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getRepositoryQuota() throws OpenApiRequestException {
    return getRepositoryQuota(null, null);
  }

  /**
   * Get the quota for resource groups
   *
   * <p>Get the details about quota and usage for resource groups
   *
   * <p><b>200</b> - quota for resourcegroups
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param quotaOnly (optional) When being set to true, the response contains only the quota of the
   *     resource and not the quota usage.
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getResourceGroupQuota(
      @Nullable final String authorization, @Nullable final Boolean quotaOnly)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceQuota/resourceGroups").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "quotaOnly", quotaOnly));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndCommonResourceQuotaResponse>() {};
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
   * Get the quota for resource groups
   *
   * <p>Get the details about quota and usage for resource groups
   *
   * <p><b>200</b> - quota for resourcegroups
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndCommonResourceQuotaResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndCommonResourceQuotaResponse getResourceGroupQuota() throws OpenApiRequestException {
    return getResourceGroupQuota(null, null);
  }
}
