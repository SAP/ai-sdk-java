package com.sap.ai.sdk.prompt.registry.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateGetResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Prompt Registry API in version 0.0.1.
 *
 * <p>Prompt Storage service for Design time & Runtime prompt templates.
 */
public class PromptTemplatesApi extends AbstractOpenApiService {
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
  @Beta
  public PromptTemplatesApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create or update a prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>400</b> - Bad Request
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
    final Object localVarPostBody = promptTemplatePostRequest;

    // verify the required parameter 'promptTemplatePostRequest' is set
    if (promptTemplatePostRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'promptTemplatePostRequest' when calling createUpdatePromptTemplate");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplatePostResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplatePostResponse>() {};
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
   * Create or update a prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>400</b> - Bad Request
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
   * <p><b>404</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
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
    final Object localVarPostBody = null;

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'promptTemplateId' when calling deletePromptTemplate");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("promptTemplateId", promptTemplateId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates/{promptTemplateId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateDeleteResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateDeleteResponse>() {};
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
   * Delete prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
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
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File exportPromptTemplate(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'promptTemplateId' when calling exportPromptTemplate");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("promptTemplateId", promptTemplateId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates/{promptTemplateId}/export")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/octet-stream", "application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<File> localVarReturnType =
        new ParameterizedTypeReference<File>() {};
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
   * Export prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File exportPromptTemplate(@Nonnull final UUID promptTemplateId)
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
    final Object localVarPostBody = null;

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'promptTemplateId' when calling getPromptTemplateByUuid");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("promptTemplateId", promptTemplateId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates/{promptTemplateId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateGetResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateGetResponse>() {};
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
      @Nullable final File _file)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates/import").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    if (_file != null) localVarFormParams.add("file", new FileSystemResource(_file));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplatePostResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplatePostResponse>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenario' when calling listPromptTemplateHistory");
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'version' when calling listPromptTemplateHistory");
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'name' when calling listPromptTemplateHistory");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenario", scenario);
    localVarPathParams.put("version", version);
    localVarPathParams.put("name", name);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/lm/scenarios/{scenario}/promptTemplates/{name}/versions/{version}/history")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateListResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateListResponse>() {};
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
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenario", scenario));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "retrieve", retrieve));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "includeSpec", includeSpec));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateListResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateListResponse>() {};
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
    final Object localVarPostBody = promptTemplateSubstitutionRequest;

    // verify the required parameter 'promptTemplateId' is set
    if (promptTemplateId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'promptTemplateId' when calling parsePromptTemplateById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("promptTemplateId", promptTemplateId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/promptTemplates/{promptTemplateId}/substitution")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "metadata", metadata));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateSubstitutionResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateSubstitutionResponse>() {};
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
    final Object localVarPostBody = promptTemplateSubstitutionRequest;

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenario' when calling parsePromptTemplateByNameVersion");
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'version' when calling parsePromptTemplateByNameVersion");
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'name' when calling parsePromptTemplateByNameVersion");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenario", scenario);
    localVarPathParams.put("version", version);
    localVarPathParams.put("name", name);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/lm/scenarios/{scenario}/promptTemplates/{name}/versions/{version}/substitution")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "metadata", metadata));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));
    if (aiResourceGroupScope != null)
      localVarHeaderParams.add(
          "AI-Resource-Group-Scope", apiClient.parameterToString(aiResourceGroupScope));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PromptTemplateSubstitutionResponse> localVarReturnType =
        new ParameterizedTypeReference<PromptTemplateSubstitutionResponse>() {};
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
