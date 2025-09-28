package com.sap.ai.sdk.prompt.registry.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigGetResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigListResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostRequest;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostResponse;
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
public class OrchestrationConfigsApi extends AbstractOpenApiService {
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
  @Beta
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
    final Object localVarPostBody = orchestrationConfigPostRequest;

    // verify the required parameter 'orchestrationConfigPostRequest' is set
    if (orchestrationConfigPostRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'orchestrationConfigPostRequest' when calling createUpdateOrchestrationConfig");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/registry/v2/orchestrationConfigs").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigPostResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigPostResponse>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'orchestrationConfigId' when calling deleteOrchestrationConfig");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("orchestrationConfigId", orchestrationConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/registry/v2/orchestrationConfigs/{orchestrationConfigId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigDeleteResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigDeleteResponse>() {};
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
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File exportOrchestrationConfig(
      @Nonnull final UUID orchestrationConfigId, @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'orchestrationConfigId' when calling exportOrchestrationConfig");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("orchestrationConfigId", orchestrationConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/registry/v2/orchestrationConfigs/{orchestrationConfigId}/export")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

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
   * Export orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param orchestrationConfigId The value for the parameter orchestrationConfigId
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File exportOrchestrationConfig(@Nonnull final UUID orchestrationConfigId)
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
    final Object localVarPostBody = null;

    // verify the required parameter 'orchestrationConfigId' is set
    if (orchestrationConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'orchestrationConfigId' when calling getOrchestrationConfigByUuid");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("orchestrationConfigId", orchestrationConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/registry/v2/orchestrationConfigs/{orchestrationConfigId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "resolve_template_ref", resolveTemplateRef));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigGetResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigGetResponse>() {};
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
      @Nullable final String aiResourceGroup, @Nullable final File _file)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/registry/v2/orchestrationConfigs/import")
            .build()
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    if (_file != null) localVarFormParams.add("file", new FileSystemResource(_file));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"multipart/form-data"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigPostResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigPostResponse>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'scenario' is set
    if (scenario == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenario' when calling listOrchestrationConfigHistory");
    }

    // verify the required parameter 'version' is set
    if (version == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'version' when calling listOrchestrationConfigHistory");
    }

    // verify the required parameter 'name' is set
    if (name == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'name' when calling listOrchestrationConfigHistory");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenario", scenario);
    localVarPathParams.put("version", version);
    localVarPathParams.put("name", name);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/registry/v2/scenarios/{scenario}/orchestrationConfigs/{name}/versions/{version}/history")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "include_spec", includeSpec));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "resolve_template_ref", resolveTemplateRef));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigListResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigListResponse>() {};
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
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/registry/v2/orchestrationConfigs").build().toUriString();

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
        apiClient.parameterToMultiValueMap(null, "include_spec", includeSpec));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "resolve_template_ref", resolveTemplateRef));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<OrchestrationConfigListResponse> localVarReturnType =
        new ParameterizedTypeReference<OrchestrationConfigListResponse>() {};
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
