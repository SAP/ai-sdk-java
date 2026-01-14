package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndCommonResourceQuotaResponse;
import com.sap.ai.sdk.core.model.BckndDeploymentResourceQuotaResponse;
import com.sap.ai.sdk.core.model.BckndExecutableResourceQuotaResponse;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
public class ResourceQuotaApi extends BaseApi {

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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/applications";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndCommonResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/deployments";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndDeploymentResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndDeploymentResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/dockerRegistrySecrets";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndCommonResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/executables";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndExecutableResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndExecutableResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/secrets";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiTenantScope != null)
      localVarHeaderParams.put("AI-Tenant-Scope", ApiClient.parameterToString(aiTenantScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndCommonResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/repositories";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndCommonResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // create path and map variables
    final String localVarPath = "/admin/resourceQuota/resourceGroups";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("quotaOnly", quotaOnly));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndCommonResourceQuotaResponse> localVarReturnType =
        new TypeReference<BckndCommonResourceQuotaResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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
