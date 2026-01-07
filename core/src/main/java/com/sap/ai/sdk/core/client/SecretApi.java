package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndGenericSecretDataResponse;
import com.sap.ai.sdk.core.model.BckndGenericSecretDetails;
import com.sap.ai.sdk.core.model.BckndGenericSecretPatchBody;
import com.sap.ai.sdk.core.model.BckndGenericSecretPostBody;
import com.sap.ai.sdk.core.model.BckndListGenericSecretsResponse;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.OpenApiResponse;
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
public class SecretApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public SecretApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public SecretApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Create a new generic secret
   *
   * <p>Create a new generic secret in the corresponding resource group or at main tenant level.
   *
   * <p><b>200</b> - Secret has been created
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndGenericSecretPostBody (required) The value for the parameter
   *     bckndGenericSecretPostBody
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return BckndGenericSecretDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDataResponse create(
      @Nonnull final BckndGenericSecretPostBody bckndGenericSecretPostBody,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndGenericSecretPostBody;

    // verify the required parameter 'bckndGenericSecretPostBody' is set
    if (bckndGenericSecretPostBody == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'bckndGenericSecretPostBody' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/admin/secrets";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiTenantScope != null)
      localVarHeaderParams.put("AI-Tenant-Scope", ApiClient.parameterToString(aiTenantScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndGenericSecretDataResponse> localVarReturnType =
        new TypeReference<BckndGenericSecretDataResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
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
   * Create a new generic secret
   *
   * <p>Create a new generic secret in the corresponding resource group or at main tenant level.
   *
   * <p><b>200</b> - Secret has been created
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndGenericSecretPostBody The value for the parameter bckndGenericSecretPostBody
   * @return BckndGenericSecretDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDataResponse create(
      @Nonnull final BckndGenericSecretPostBody bckndGenericSecretPostBody)
      throws OpenApiRequestException {
    return create(bckndGenericSecretPostBody, null, null, null);
  }

  /**
   * Deletes the secret
   *
   * <p>Deletes the secret from provided resource group namespace
   *
   * <p><b>200</b> - The secret has been removed
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName (required) The value for the parameter secretName
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse delete(
      @Nonnull final String secretName,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'secretName' is set
    if (secretName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'secretName' when calling delete")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/secrets/{secretName}"
            .replaceAll(
                "\\{" + "secretName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(secretName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
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
   * Deletes the secret
   *
   * <p>Deletes the secret from provided resource group namespace
   *
   * <p><b>200</b> - The secret has been removed
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName The value for the parameter secretName
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse delete(@Nonnull final String secretName) throws OpenApiRequestException {
    return delete(secretName, null, null, null);
  }

  /**
   * Get a specific secret
   *
   * <p>Retrieve a single generic secret. This retrieves metadata only, not the secret data itself.
   *
   * <p><b>200</b> - The secret was fetched
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName (required) The value for the parameter secretName
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return BckndGenericSecretDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDetails get(
      @Nonnull final String secretName,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'secretName' is set
    if (secretName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'secretName' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/secrets/{secretName}"
            .replaceAll(
                "\\{" + "secretName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(secretName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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

    final TypeReference<BckndGenericSecretDetails> localVarReturnType =
        new TypeReference<BckndGenericSecretDetails>() {};

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
   * Get a specific secret
   *
   * <p>Retrieve a single generic secret. This retrieves metadata only, not the secret data itself.
   *
   * <p><b>200</b> - The secret was fetched
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName The value for the parameter secretName
   * @return BckndGenericSecretDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDetails get(@Nonnull final String secretName)
      throws OpenApiRequestException {
    return get(secretName, null, null, null);
  }

  /**
   * Lists all secrets corresponding to tenant
   *
   * <p>Lists all secrets corresponding to tenant. This retrieves metadata only, not the secret data
   * itself.
   *
   * <p><b>200</b> - The secrets were fetched
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
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
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return BckndListGenericSecretsResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndListGenericSecretsResponse getAll(
      @Nullable final String authorization,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // create path and map variables
    final String localVarPath = "/admin/secrets";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));
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

    final TypeReference<BckndListGenericSecretsResponse> localVarReturnType =
        new TypeReference<BckndListGenericSecretsResponse>() {};

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
   * Lists all secrets corresponding to tenant
   *
   * <p>Lists all secrets corresponding to tenant. This retrieves metadata only, not the secret data
   * itself.
   *
   * <p><b>200</b> - The secrets were fetched
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndListGenericSecretsResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndListGenericSecretsResponse getAll() throws OpenApiRequestException {
    return getAll(null, null, null, null, null, null);
  }

  /**
   * Update secret credentials
   *
   * <p>Update secret credentials. Replace secret data with the provided data.
   *
   * <p><b>200</b> - The secret has been updated
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName (required) The value for the parameter secretName
   * @param bckndGenericSecretPatchBody (required) The value for the parameter
   *     bckndGenericSecretPatchBody
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional) Specify an existing resource group id to use
   * @param aiTenantScope (optional) Specify whether the main tenant scope is to be used
   * @return BckndGenericSecretDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDataResponse update(
      @Nonnull final String secretName,
      @Nonnull final BckndGenericSecretPatchBody bckndGenericSecretPatchBody,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean aiTenantScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndGenericSecretPatchBody;

    // verify the required parameter 'secretName' is set
    if (secretName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'secretName' when calling update")
          .statusCode(400);
    }

    // verify the required parameter 'bckndGenericSecretPatchBody' is set
    if (bckndGenericSecretPatchBody == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'bckndGenericSecretPatchBody' when calling update")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/secrets/{secretName}"
            .replaceAll(
                "\\{" + "secretName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(secretName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiTenantScope != null)
      localVarHeaderParams.put("AI-Tenant-Scope", ApiClient.parameterToString(aiTenantScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndGenericSecretDataResponse> localVarReturnType =
        new TypeReference<BckndGenericSecretDataResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
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
   * Update secret credentials
   *
   * <p>Update secret credentials. Replace secret data with the provided data.
   *
   * <p><b>200</b> - The secret has been updated
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param secretName The value for the parameter secretName
   * @param bckndGenericSecretPatchBody The value for the parameter bckndGenericSecretPatchBody
   * @return BckndGenericSecretDataResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndGenericSecretDataResponse update(
      @Nonnull final String secretName,
      @Nonnull final BckndGenericSecretPatchBody bckndGenericSecretPatchBody)
      throws OpenApiRequestException {
    return update(secretName, bckndGenericSecretPatchBody, null, null, null);
  }
}
