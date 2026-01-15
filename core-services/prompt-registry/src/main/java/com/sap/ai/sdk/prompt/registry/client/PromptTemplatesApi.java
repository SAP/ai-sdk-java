package com.sap.ai.sdk.prompt.registry.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateGetResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
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
public class PromptTemplatesApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the Prompt Registry API */
  public PromptTemplatesApi() {}

  /**
   * Instantiates this API class to invoke operations on the Prompt Registry API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public PromptTemplatesApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Prompt Registry API based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public PromptTemplatesApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create or update a prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplatePostRequest (required) The value for the parameter
   *     promptTemplatePostRequest
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplatePostResponse createUpdatePromptTemplate(
      @Nonnull final PromptTemplatePostRequest promptTemplatePostRequest,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {

    // verify the required parameter 'promptTemplatePostRequest' is set
    if (promptTemplatePostRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'promptTemplatePostRequest' when calling createUpdatePromptTemplate")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/promptTemplates";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplatePostResponse> localVarReturnType =
        new TypeReference<PromptTemplatePostResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        promptTemplatePostRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Create or update a prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplatePostRequest The value for the parameter promptTemplatePostRequest
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplatePostResponse createUpdatePromptTemplate(
      @Nonnull final PromptTemplatePostRequest promptTemplatePostRequest)
      throws OpenApiRequestException {
    return createUpdatePromptTemplate(promptTemplatePostRequest, null, null);
  }

  /**
   * Delete prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId (required) The value for the parameter promptTemplateId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @return PromptTemplateDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateDeleteResponse deletePromptTemplate(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'promptTemplateId' when calling deletePromptTemplate")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/promptTemplates/{promptTemplateId}"
            .replaceAll(
                "\\{" + "promptTemplateId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(promptTemplateId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateDeleteResponse> localVarReturnType =
        new TypeReference<PromptTemplateDeleteResponse>() {};

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
   * Delete prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return PromptTemplateDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateDeleteResponse deletePromptTemplate(@Nonnull final UUID promptTemplateId)
      throws OpenApiRequestException {
    return deletePromptTemplate(promptTemplateId, null, null);
  }

  /**
   * Export prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId (required) The value for the parameter promptTemplateId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @return byte[]
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public byte[] exportPromptTemplate(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'promptTemplateId' when calling exportPromptTemplate")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/promptTemplates/{promptTemplateId}/export"
            .replaceAll(
                "\\{" + "promptTemplateId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(promptTemplateId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

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
   * Export prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return byte[]
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public byte[] exportPromptTemplate(@Nonnull final UUID promptTemplateId)
      throws OpenApiRequestException {
    return exportPromptTemplate(promptTemplateId, null, null);
  }

  /**
   * Get prompt template by UUID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId (required) The value for the parameter promptTemplateId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @return PromptTemplateGetResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateGetResponse getPromptTemplateByUuid(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'promptTemplateId' when calling getPromptTemplateByUuid")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/promptTemplates/{promptTemplateId}"
            .replaceAll(
                "\\{" + "promptTemplateId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(promptTemplateId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateGetResponse> localVarReturnType =
        new TypeReference<PromptTemplateGetResponse>() {};

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
   * Get prompt template by UUID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return PromptTemplateGetResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateGetResponse getPromptTemplateByUuid(@Nonnull final UUID promptTemplateId)
      throws OpenApiRequestException {
    return getPromptTemplateByUuid(promptTemplateId, null, null);
  }

  /**
   * Import prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param _file (optional) The value for the parameter _file
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplatePostResponse importPromptTemplate(
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final byte[] _file)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/lm/promptTemplates/import";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));
    if (_file != null) localVarFormParams.put("file", _file);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplatePostResponse> localVarReturnType =
        new TypeReference<PromptTemplatePostResponse>() {};

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
   * Import prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplatePostResponse importPromptTemplate() throws OpenApiRequestException {
    return importPromptTemplate(null, null, null);
  }

  /**
   * List prompt template history
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
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplateHistory(
      @Nonnull final String scenario,
      @Nonnull final String version,
      @Nonnull final String name,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenario' when calling listPromptTemplateHistory")
          .statusCode(400);
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'version' when calling listPromptTemplateHistory")
          .statusCode(400);
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'name' when calling listPromptTemplateHistory")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenario}/promptTemplates/{name}/versions/{version}/history"
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

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateListResponse> localVarReturnType =
        new TypeReference<PromptTemplateListResponse>() {};

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
   * List prompt template history
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
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplateHistory(
      @Nonnull final String scenario, @Nonnull final String version, @Nonnull final String name)
      throws OpenApiRequestException {
    return listPromptTemplateHistory(scenario, version, name, null, null);
  }

  /**
   * List prompt templates
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
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param scenario (optional) The value for the parameter scenario
   * @param name (optional) The value for the parameter name
   * @param version (optional) The value for the parameter version
   * @param retrieve (optional, default to both) The value for the parameter retrieve
   * @param includeSpec (optional, default to false) The value for the parameter includeSpec
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplates(
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final String scenario,
      @Nullable final String name,
      @Nullable final String version,
      @Nullable final String retrieve,
      @Nullable final Boolean includeSpec)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/lm/promptTemplates";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("scenario", scenario));
    localVarQueryParams.addAll(ApiClient.parameterToPair("name", name));
    localVarQueryParams.addAll(ApiClient.parameterToPair("version", version));
    localVarQueryParams.addAll(ApiClient.parameterToPair("retrieve", retrieve));
    localVarQueryParams.addAll(ApiClient.parameterToPair("includeSpec", includeSpec));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateListResponse> localVarReturnType =
        new TypeReference<PromptTemplateListResponse>() {};

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
   * List prompt templates
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplates() throws OpenApiRequestException {
    return listPromptTemplates(null, null, null, null, null, null, null);
  }

  /**
   * Parse prompt template by ID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId (required) The value for the parameter promptTemplateId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param metadata (optional, default to false) The value for the parameter metadata
   * @param promptTemplateSubstitutionRequest (optional) The value for the parameter
   *     promptTemplateSubstitutionRequest
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateSubstitutionResponse parsePromptTemplateById(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final Boolean metadata,
      @Nullable final PromptTemplateSubstitutionRequest promptTemplateSubstitutionRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'promptTemplateId' when calling parsePromptTemplateById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/promptTemplates/{promptTemplateId}/substitution"
            .replaceAll(
                "\\{" + "promptTemplateId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(promptTemplateId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("metadata", metadata));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateSubstitutionResponse> localVarReturnType =
        new TypeReference<PromptTemplateSubstitutionResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        promptTemplateSubstitutionRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Parse prompt template by ID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateSubstitutionResponse parsePromptTemplateById(
      @Nonnull final UUID promptTemplateId) throws OpenApiRequestException {
    return parsePromptTemplateById(promptTemplateId, null, null, null, null);
  }

  /**
   * Parse prompt template by name and version
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
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param metadata (optional, default to false) The value for the parameter metadata
   * @param promptTemplateSubstitutionRequest (optional) The value for the parameter
   *     promptTemplateSubstitutionRequest
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateSubstitutionResponse parsePromptTemplateByNameVersion(
      @Nonnull final String scenario,
      @Nonnull final String version,
      @Nonnull final String name,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final Boolean metadata,
      @Nullable final PromptTemplateSubstitutionRequest promptTemplateSubstitutionRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenario' when calling parsePromptTemplateByNameVersion")
          .statusCode(400);
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'version' when calling parsePromptTemplateByNameVersion")
          .statusCode(400);
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'name' when calling parsePromptTemplateByNameVersion")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenario}/promptTemplates/{name}/versions/{version}/substitution"
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

    localVarQueryParams.addAll(ApiClient.parameterToPair("metadata", metadata));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.put(
          "AI-Resource-Group-Scope", ApiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PromptTemplateSubstitutionResponse> localVarReturnType =
        new TypeReference<PromptTemplateSubstitutionResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        promptTemplateSubstitutionRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Parse prompt template by name and version
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
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateSubstitutionResponse parsePromptTemplateByNameVersion(
      @Nonnull final String scenario, @Nonnull final String version, @Nonnull final String name)
      throws OpenApiRequestException {
    return parsePromptTemplateByNameVersion(scenario, version, name, null, null, null, null);
  }
}
