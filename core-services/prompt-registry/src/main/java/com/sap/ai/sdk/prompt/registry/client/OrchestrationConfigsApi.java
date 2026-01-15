package com.sap.ai.sdk.prompt.registry.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigGetResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigListResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostRequest;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Prompt Registry API in version 0.0.1.
 *
 * <p>Prompt Storage service for Design time & Runtime prompt templates.
 */
public class OrchestrationConfigsApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the Prompt Registry API */
  public OrchestrationConfigsApi() {}

  /**
   * Instantiates this API class to invoke operations on the Prompt Registry API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public OrchestrationConfigsApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Prompt Registry API based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public OrchestrationConfigsApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create or update an orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigPostRequest (required) The value for the parameter
   *     orchestrationConfigPostRequest
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @return OrchestrationConfigPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigPostResponse createUpdateOrchestrationConfig(
      @Nonnull final OrchestrationConfigPostRequest orchestrationConfigPostRequest,
      @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {

    // verify the required parameter 'orchestrationConfigPostRequest' is set
    if (orchestrationConfigPostRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'orchestrationConfigPostRequest' when calling createUpdateOrchestrationConfig")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/registry/v2/orchestrationConfigs";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigPostResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigPostResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        orchestrationConfigPostRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Create or update an orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigPostRequest The value for the parameter
   *     orchestrationConfigPostRequest
   * @return OrchestrationConfigPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigPostResponse createUpdateOrchestrationConfig(
      @Nonnull final OrchestrationConfigPostRequest orchestrationConfigPostRequest)
      throws OpenApiRequestException {
    return createUpdateOrchestrationConfig(orchestrationConfigPostRequest, null);
  }

  /**
   * Delete orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId (required) The value for the parameter orchestrationConfigId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @return OrchestrationConfigDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigDeleteResponse deleteOrchestrationConfig(
      @Nonnull final UUID orchestrationConfigId, @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'orchestrationConfigId' when calling deleteOrchestrationConfig")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/registry/v2/orchestrationConfigs/{orchestrationConfigId}"
            .replaceAll(
                "\\{" + "orchestrationConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(orchestrationConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigDeleteResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigDeleteResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId The value for the parameter orchestrationConfigId
   * @return OrchestrationConfigDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigDeleteResponse deleteOrchestrationConfig(
      @Nonnull final UUID orchestrationConfigId) throws OpenApiRequestException {
    return deleteOrchestrationConfig(orchestrationConfigId, null);
  }

  /**
   * Export orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId (required) The value for the parameter orchestrationConfigId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @return byte[]
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public byte[] exportOrchestrationConfig(
      @Nonnull final UUID orchestrationConfigId, @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'orchestrationConfigId' when calling exportOrchestrationConfig")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/registry/v2/orchestrationConfigs/{orchestrationConfigId}/export"
            .replaceAll(
                "\\{" + "orchestrationConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(orchestrationConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/octet-stream", "application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<byte[]> localVarReturnType = new TypeReference<byte[]>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Export orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId The value for the parameter orchestrationConfigId
   * @return byte[]
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public byte[] exportOrchestrationConfig(@Nonnull final UUID orchestrationConfigId)
      throws OpenApiRequestException {
    return exportOrchestrationConfig(orchestrationConfigId, null);
  }

  /**
   * Get orchestration config by UUID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId (required) The value for the parameter orchestrationConfigId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param resolveTemplateRef (optional, default to false) The value for the parameter
   *     resolveTemplateRef
   * @return OrchestrationConfigGetResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigGetResponse getOrchestrationConfigByUuid(
      @Nonnull final UUID orchestrationConfigId,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean resolveTemplateRef)
      throws OpenApiRequestException {

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'orchestrationConfigId' when calling getOrchestrationConfigByUuid")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/registry/v2/orchestrationConfigs/{orchestrationConfigId}"
            .replaceAll(
                "\\{" + "orchestrationConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(orchestrationConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(
        ApiClient.parameterToPair("resolve_template_ref", resolveTemplateRef));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigGetResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigGetResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get orchestration config by UUID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId The value for the parameter orchestrationConfigId
   * @return OrchestrationConfigGetResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigGetResponse getOrchestrationConfigByUuid(
      @Nonnull final UUID orchestrationConfigId) throws OpenApiRequestException {
    return getOrchestrationConfigByUuid(orchestrationConfigId, null, null);
  }

  /**
   * Import orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param _file (optional) The value for the parameter _file
   * @return OrchestrationConfigPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigPostResponse importOrchestrationConfig(
      @Nullable final String aiResourceGroup, @Nullable final byte[] _file)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/registry/v2/orchestrationConfigs/import";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (_file != null) localVarFormParams.put("file", _file);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigPostResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigPostResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Import orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @return OrchestrationConfigPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigPostResponse importOrchestrationConfig()
      throws OpenApiRequestException {
    return importOrchestrationConfig(null, null);
  }

  /**
   * List orchestration config history
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param scenario (required) The value for the parameter scenario
   * @param version (required) The value for the parameter version
   * @param name (required) The value for the parameter name
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param includeSpec (optional, default to false) The value for the parameter includeSpec
   * @param resolveTemplateRef (optional, default to false) The value for the parameter
   *     resolveTemplateRef
   * @return OrchestrationConfigListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigListResponse listOrchestrationConfigHistory(
      @Nonnull final String scenario,
      @Nonnull final String version,
      @Nonnull final String name,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean includeSpec,
      @Nullable final Boolean resolveTemplateRef)
      throws OpenApiRequestException {

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenario' when calling listOrchestrationConfigHistory")
          .statusCode(400);
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'version' when calling listOrchestrationConfigHistory")
          .statusCode(400);
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'name' when calling listOrchestrationConfigHistory")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/registry/v2/scenarios/{scenario}/orchestrationConfigs/{name}/versions/{version}/history"
            .replaceAll(
                "\\{" + "scenario" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenario)))
            .replaceAll(
                "\\{" + "version" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(version)))
            .replaceAll(
                "\\{" + "name" + "\\}", ApiClient.escapeString(ApiClient.parameterToString(name)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("include_spec", includeSpec));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("resolve_template_ref", resolveTemplateRef));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigListResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigListResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * List orchestration config history
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param scenario The value for the parameter scenario
   * @param version The value for the parameter version
   * @param name The value for the parameter name
   * @return OrchestrationConfigListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigListResponse listOrchestrationConfigHistory(
      @Nonnull final String scenario, @Nonnull final String version, @Nonnull final String name)
      throws OpenApiRequestException {
    return listOrchestrationConfigHistory(scenario, version, name, null, null, null);
  }

  /**
   * List orchestration configs
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param scenario (optional) The value for the parameter scenario
   * @param name (optional) The value for the parameter name
   * @param version (optional) The value for the parameter version
   * @param retrieve (optional, default to both) The value for the parameter retrieve
   * @param includeSpec (optional, default to false) The value for the parameter includeSpec
   * @param resolveTemplateRef (optional, default to false) The value for the parameter
   *     resolveTemplateRef
   * @return OrchestrationConfigListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigListResponse listOrchestrationConfigs(
      @Nullable final String aiResourceGroup,
      @Nullable final String scenario,
      @Nullable final String name,
      @Nullable final String version,
      @Nullable final String retrieve,
      @Nullable final Boolean includeSpec,
      @Nullable final Boolean resolveTemplateRef)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/registry/v2/orchestrationConfigs";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("scenario", scenario));
    localVarQueryParams.addAll(ApiClient.parameterToPair("name", name));
    localVarQueryParams.addAll(ApiClient.parameterToPair("version", version));
    localVarQueryParams.addAll(ApiClient.parameterToPair("retrieve", retrieve));
    localVarQueryParams.addAll(ApiClient.parameterToPair("include_spec", includeSpec));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("resolve_template_ref", resolveTemplateRef));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OrchestrationConfigListResponse> localVarReturnType =
        new TypeReference<OrchestrationConfigListResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * List orchestration configs
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @return OrchestrationConfigListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigListResponse listOrchestrationConfigs() throws OpenApiRequestException {
    return listOrchestrationConfigs(null, null, null, null, null, null, null);
  }
}
