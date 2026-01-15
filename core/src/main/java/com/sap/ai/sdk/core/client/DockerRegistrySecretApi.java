package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretCreationResponse;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretDeletionResponse;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretModificationResponse;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretStatus;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretStatusResponse;
import com.sap.ai.sdk.core.model.BcknddockerRegistrySecretWithSensitiveDataRequest;
import com.sap.ai.sdk.core.model.KubesubmitV4DockerRegistrySecretsCreateRequest;
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
public class DockerRegistrySecretApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public DockerRegistrySecretApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public DockerRegistrySecretApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Create a secret
   *
   * <p>Create a secret based on the configuration in the request body.
   *
   * <p><b>202</b> - The request to create a k8s secret based on the given configuration has been
   * accepted.
   *
   * <p><b>400</b> - One of the following failure cases has occurred: &lt;ul&gt; &lt;li&gt; Neither
   * JSON nor YAML was able to be parsed. &lt;li&gt; The request was invalidly formatted
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param kubesubmitV4DockerRegistrySecretsCreateRequest (required) The value for the parameter
   *     kubesubmitV4DockerRegistrySecretsCreateRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BcknddockerRegistrySecretCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretCreationResponse create(
      @Nonnull
          final KubesubmitV4DockerRegistrySecretsCreateRequest
              kubesubmitV4DockerRegistrySecretsCreateRequest,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = kubesubmitV4DockerRegistrySecretsCreateRequest;

    // verify the required parameter 'kubesubmitV4DockerRegistrySecretsCreateRequest' is set
    if (kubesubmitV4DockerRegistrySecretsCreateRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'kubesubmitV4DockerRegistrySecretsCreateRequest' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/admin/dockerRegistrySecrets";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BcknddockerRegistrySecretCreationResponse> localVarReturnType =
        new TypeReference<BcknddockerRegistrySecretCreationResponse>() {};

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
   * Create a secret
   *
   * <p>Create a secret based on the configuration in the request body.
   *
   * <p><b>202</b> - The request to create a k8s secret based on the given configuration has been
   * accepted.
   *
   * <p><b>400</b> - One of the following failure cases has occurred: &lt;ul&gt; &lt;li&gt; Neither
   * JSON nor YAML was able to be parsed. &lt;li&gt; The request was invalidly formatted
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param kubesubmitV4DockerRegistrySecretsCreateRequest The value for the parameter
   *     kubesubmitV4DockerRegistrySecretsCreateRequest
   * @return BcknddockerRegistrySecretCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretCreationResponse create(
      @Nonnull
          final KubesubmitV4DockerRegistrySecretsCreateRequest
              kubesubmitV4DockerRegistrySecretsCreateRequest)
      throws OpenApiRequestException {
    return create(kubesubmitV4DockerRegistrySecretsCreateRequest, null);
  }

  /**
   * Delete docker registry secret
   *
   * <p>Delete a secret with the name of dockerRegistryName if it exists.
   *
   * <p><b>202</b> - The request to delete the secret has been accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName (required) Name of the docker Registry store for the secret.
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BcknddockerRegistrySecretDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretDeletionResponse delete(
      @Nonnull final String dockerRegistryName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'dockerRegistryName' is set
    if (dockerRegistryName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dockerRegistryName' when calling delete")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/dockerRegistrySecrets/{dockerRegistryName}"
            .replaceAll(
                "\\{" + "dockerRegistryName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dockerRegistryName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BcknddockerRegistrySecretDeletionResponse> localVarReturnType =
        new TypeReference<BcknddockerRegistrySecretDeletionResponse>() {};

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
   * Delete docker registry secret
   *
   * <p>Delete a secret with the name of dockerRegistryName if it exists.
   *
   * <p><b>202</b> - The request to delete the secret has been accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName Name of the docker Registry store for the secret.
   * @return BcknddockerRegistrySecretDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretDeletionResponse delete(@Nonnull final String dockerRegistryName)
      throws OpenApiRequestException {
    return delete(dockerRegistryName, null);
  }

  /**
   * Returns the of metadata of secrets which match the query parameter.
   *
   * <p>Retrieve the stored secret metadata which matches the parameter dockerRegistryName. The
   * base64 encoded field for the stored secret is not returned.
   *
   * <p><b>200</b> - The request was processed successfully and the metadata of the of stored
   * secrets wil be returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName (required) Name of the docker Registry store for the secret.
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BcknddockerRegistrySecretStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretStatus get(
      @Nonnull final String dockerRegistryName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'dockerRegistryName' is set
    if (dockerRegistryName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dockerRegistryName' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/dockerRegistrySecrets/{dockerRegistryName}"
            .replaceAll(
                "\\{" + "dockerRegistryName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dockerRegistryName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BcknddockerRegistrySecretStatus> localVarReturnType =
        new TypeReference<BcknddockerRegistrySecretStatus>() {};

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
   * Returns the of metadata of secrets which match the query parameter.
   *
   * <p>Retrieve the stored secret metadata which matches the parameter dockerRegistryName. The
   * base64 encoded field for the stored secret is not returned.
   *
   * <p><b>200</b> - The request was processed successfully and the metadata of the of stored
   * secrets wil be returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName Name of the docker Registry store for the secret.
   * @return BcknddockerRegistrySecretStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretStatus get(@Nonnull final String dockerRegistryName)
      throws OpenApiRequestException {
    return get(dockerRegistryName, null);
  }

  /**
   * Update a secret
   *
   * <p>Update a secret with name of dockerRegistryName if it exists.
   *
   * <p><b>202</b> - The request to update the secret based on the the given configuration has been
   * accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName (required) Name of the docker Registry store for the secret.
   * @param bcknddockerRegistrySecretWithSensitiveDataRequest (required) The value for the parameter
   *     bcknddockerRegistrySecretWithSensitiveDataRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BcknddockerRegistrySecretModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretModificationResponse patch(
      @Nonnull final String dockerRegistryName,
      @Nonnull
          final BcknddockerRegistrySecretWithSensitiveDataRequest
              bcknddockerRegistrySecretWithSensitiveDataRequest,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bcknddockerRegistrySecretWithSensitiveDataRequest;

    // verify the required parameter 'dockerRegistryName' is set
    if (dockerRegistryName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dockerRegistryName' when calling patch")
          .statusCode(400);
    }

    // verify the required parameter 'bcknddockerRegistrySecretWithSensitiveDataRequest' is set
    if (bcknddockerRegistrySecretWithSensitiveDataRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'bcknddockerRegistrySecretWithSensitiveDataRequest' when calling patch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/dockerRegistrySecrets/{dockerRegistryName}"
            .replaceAll(
                "\\{" + "dockerRegistryName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dockerRegistryName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/merge-patch+json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BcknddockerRegistrySecretModificationResponse> localVarReturnType =
        new TypeReference<BcknddockerRegistrySecretModificationResponse>() {};

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
   * Update a secret
   *
   * <p>Update a secret with name of dockerRegistryName if it exists.
   *
   * <p><b>202</b> - The request to update the secret based on the the given configuration has been
   * accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param dockerRegistryName Name of the docker Registry store for the secret.
   * @param bcknddockerRegistrySecretWithSensitiveDataRequest The value for the parameter
   *     bcknddockerRegistrySecretWithSensitiveDataRequest
   * @return BcknddockerRegistrySecretModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretModificationResponse patch(
      @Nonnull final String dockerRegistryName,
      @Nonnull
          final BcknddockerRegistrySecretWithSensitiveDataRequest
              bcknddockerRegistrySecretWithSensitiveDataRequest)
      throws OpenApiRequestException {
    return patch(dockerRegistryName, bcknddockerRegistrySecretWithSensitiveDataRequest, null);
  }

  /**
   * Get a list of metadata of secrets.
   *
   * <p>Retrieve a list of metadata of the stored secrets
   *
   * <p><b>200</b> - The request was successful and the requested metadata for the secret will be
   * returned. This includes a list of attributes of the stored secret like - creationTimestamp,
   * namespace etc. The secret&#39;s data field is not returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BcknddockerRegistrySecretStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretStatusResponse query(
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // create path and map variables
    final String localVarPath = "/admin/dockerRegistrySecrets";

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

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BcknddockerRegistrySecretStatusResponse> localVarReturnType =
        new TypeReference<BcknddockerRegistrySecretStatusResponse>() {};

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
   * Get a list of metadata of secrets.
   *
   * <p>Retrieve a list of metadata of the stored secrets
   *
   * <p><b>200</b> - The request was successful and the requested metadata for the secret will be
   * returned. This includes a list of attributes of the stored secret like - creationTimestamp,
   * namespace etc. The secret&#39;s data field is not returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BcknddockerRegistrySecretStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BcknddockerRegistrySecretStatusResponse query() throws OpenApiRequestException {
    return query(null, null, null, null);
  }
}
