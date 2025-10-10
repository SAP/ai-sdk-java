package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretCreationResponse;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretDeletionResponse;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretModificationResponse;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretStatus;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretStatusResponse;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretWithSensitiveDataRequest;
import com.sap.ai.sdk.core.model.BckndobjectStoreSecretWithSensitiveDataRequestForPostCall;
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
public class ObjectStoreSecretApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ObjectStoreSecretApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ObjectStoreSecretApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Create a secret
   *
   * <p>Create a secret based on the configuration in the request body
   *
   * <p><b>202</b> - The request to create a k8s secret based on the given configuration has been
   * accepted.
   *
   * <p><b>400</b> - One of the following failure cases has occurred: &lt;ul&gt; &lt;li&gt; Neither
   * JSON nor YAML was able to be parsed. &lt;li&gt; The request was invalidly formatted
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndobjectStoreSecretWithSensitiveDataRequestForPostCall (required) The value for the
   *     parameter bckndobjectStoreSecretWithSensitiveDataRequestForPostCall
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional, default to default) Specify an existing resource group id to
   *     use. Uses \&quot;default\&quot; if value not provided.
   * @return BckndobjectStoreSecretCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretCreationResponse create(
      @Nonnull
          final BckndobjectStoreSecretWithSensitiveDataRequestForPostCall
              bckndobjectStoreSecretWithSensitiveDataRequestForPostCall,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndobjectStoreSecretWithSensitiveDataRequestForPostCall;

    // verify the required parameter 'bckndobjectStoreSecretWithSensitiveDataRequestForPostCall' is
    // set
    if (bckndobjectStoreSecretWithSensitiveDataRequestForPostCall == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndobjectStoreSecretWithSensitiveDataRequestForPostCall' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/objectStoreSecrets").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndobjectStoreSecretCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndobjectStoreSecretCreationResponse>() {};
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
   * Create a secret
   *
   * <p>Create a secret based on the configuration in the request body
   *
   * <p><b>202</b> - The request to create a k8s secret based on the given configuration has been
   * accepted.
   *
   * <p><b>400</b> - One of the following failure cases has occurred: &lt;ul&gt; &lt;li&gt; Neither
   * JSON nor YAML was able to be parsed. &lt;li&gt; The request was invalidly formatted
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndobjectStoreSecretWithSensitiveDataRequestForPostCall The value for the parameter
   *     bckndobjectStoreSecretWithSensitiveDataRequestForPostCall
   * @return BckndobjectStoreSecretCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretCreationResponse create(
      @Nonnull
          final BckndobjectStoreSecretWithSensitiveDataRequestForPostCall
              bckndobjectStoreSecretWithSensitiveDataRequestForPostCall)
      throws OpenApiRequestException {
    return create(bckndobjectStoreSecretWithSensitiveDataRequestForPostCall, null, null);
  }

  /**
   * Delete object store secret
   *
   * <p>Delete a secret with the name of objectStoreName if it exists.
   *
   * <p><b>202</b> - The request to delete the secret has been accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param objectStoreName (required) Name of the object store for the secret.
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional, default to default) Specify an existing resource group id to
   *     use. Uses \&quot;default\&quot; if value not provided.
   * @return BckndobjectStoreSecretDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretDeletionResponse delete(
      @Nonnull final String objectStoreName,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'objectStoreName' is set
    if (objectStoreName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'objectStoreName' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("objectStoreName", objectStoreName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/objectStoreSecrets/{objectStoreName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndobjectStoreSecretDeletionResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndobjectStoreSecretDeletionResponse>() {};
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
   * Delete object store secret
   *
   * <p>Delete a secret with the name of objectStoreName if it exists.
   *
   * <p><b>202</b> - The request to delete the secret has been accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param objectStoreName Name of the object store for the secret.
   * @return BckndobjectStoreSecretDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretDeletionResponse delete(@Nonnull final String objectStoreName)
      throws OpenApiRequestException {
    return delete(objectStoreName, null, null);
  }

  /**
   * Returns the of metadata of secrets which match the query parameter.
   *
   * <p>This retrieves the metadata of the stored secret which match the parameter objectStoreName.
   * The fetched secret is constructed like objectStoreName-object-store-secret The base64 encoded
   * field for the stored secret is not returned.
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
   * @param objectStoreName (required) Name of the object store for the secret.
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional, default to default) Specify an existing resource group id to
   *     use. Uses \&quot;default\&quot; if value not provided.
   * @return BckndobjectStoreSecretStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretStatus get(
      @Nonnull final String objectStoreName,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'objectStoreName' is set
    if (objectStoreName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'objectStoreName' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("objectStoreName", objectStoreName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/objectStoreSecrets/{objectStoreName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndobjectStoreSecretStatus> localVarReturnType =
        new ParameterizedTypeReference<BckndobjectStoreSecretStatus>() {};
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
   * Returns the of metadata of secrets which match the query parameter.
   *
   * <p>This retrieves the metadata of the stored secret which match the parameter objectStoreName.
   * The fetched secret is constructed like objectStoreName-object-store-secret The base64 encoded
   * field for the stored secret is not returned.
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
   * @param objectStoreName Name of the object store for the secret.
   * @return BckndobjectStoreSecretStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretStatus get(@Nonnull final String objectStoreName)
      throws OpenApiRequestException {
    return get(objectStoreName, null, null);
  }

  /**
   * Update object store secret
   *
   * <p>Update a secret with name of objectStoreName if it exists.
   *
   * <p><b>202</b> - The request to update the secret based on the given configuration has been
   * accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param objectStoreName (required) Name of the object store for the secret.
   * @param bckndobjectStoreSecretWithSensitiveDataRequest (required) The value for the parameter
   *     bckndobjectStoreSecretWithSensitiveDataRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param aiResourceGroup (optional, default to default) Specify an existing resource group id to
   *     use. Uses \&quot;default\&quot; if value not provided.
   * @return BckndobjectStoreSecretModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretModificationResponse patch(
      @Nonnull final String objectStoreName,
      @Nonnull
          final BckndobjectStoreSecretWithSensitiveDataRequest
              bckndobjectStoreSecretWithSensitiveDataRequest,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndobjectStoreSecretWithSensitiveDataRequest;

    // verify the required parameter 'objectStoreName' is set
    if (objectStoreName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'objectStoreName' when calling patch");
    }

    // verify the required parameter 'bckndobjectStoreSecretWithSensitiveDataRequest' is set
    if (bckndobjectStoreSecretWithSensitiveDataRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndobjectStoreSecretWithSensitiveDataRequest' when calling patch");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("objectStoreName", objectStoreName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/objectStoreSecrets/{objectStoreName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndobjectStoreSecretModificationResponse>
        localVarReturnType =
            new ParameterizedTypeReference<BckndobjectStoreSecretModificationResponse>() {};
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
   * Update object store secret
   *
   * <p>Update a secret with name of objectStoreName if it exists.
   *
   * <p><b>202</b> - The request to update the secret based on the given configuration has been
   * accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param objectStoreName Name of the object store for the secret.
   * @param bckndobjectStoreSecretWithSensitiveDataRequest The value for the parameter
   *     bckndobjectStoreSecretWithSensitiveDataRequest
   * @return BckndobjectStoreSecretModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretModificationResponse patch(
      @Nonnull final String objectStoreName,
      @Nonnull
          final BckndobjectStoreSecretWithSensitiveDataRequest
              bckndobjectStoreSecretWithSensitiveDataRequest)
      throws OpenApiRequestException {
    return patch(objectStoreName, bckndobjectStoreSecretWithSensitiveDataRequest, null, null);
  }

  /**
   * Get a list of metadata of available secrets.
   *
   * <p>Retrieve a list of metadata of the stored secrets.
   *
   * <p><b>200</b> - The request was successful and the requested metadata for the secret will be
   * returned. This includes a list of attributes of the stored secret like - creationTimestamp,
   * namespace etc. The secret&#39;s data field is not returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
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
   * @param aiResourceGroup (optional, default to default) Specify an existing resource group id to
   *     use. Uses \&quot;default\&quot; if value not provided.
   * @return BckndobjectStoreSecretStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretStatusResponse query(
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count,
      @Nullable final String authorization,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/objectStoreSecrets").build().toUriString();

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
    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndobjectStoreSecretStatusResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndobjectStoreSecretStatusResponse>() {};
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
   * Get a list of metadata of available secrets.
   *
   * <p>Retrieve a list of metadata of the stored secrets.
   *
   * <p><b>200</b> - The request was successful and the requested metadata for the secret will be
   * returned. This includes a list of attributes of the stored secret like - creationTimestamp,
   * namespace etc. The secret&#39;s data field is not returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndobjectStoreSecretStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndobjectStoreSecretStatusResponse query() throws OpenApiRequestException {
    return query(null, null, null, null, null);
  }
}
