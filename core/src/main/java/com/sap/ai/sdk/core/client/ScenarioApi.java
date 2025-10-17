package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.ai.sdk.core.model.AiScenario;
import com.sap.ai.sdk.core.model.AiScenarioList;
import com.sap.ai.sdk.core.model.AiVersionList;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class ScenarioApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ScenarioApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ScenarioApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get scenario by id
   *
   * <p>Retrieve details for a scenario specified by scenarioId.
   *
   * <p><b>200</b> - A scenario
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param scenarioId Scenario identifier
   * @return AiScenario
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiScenario get(@Nonnull final String aiResourceGroup, @Nonnull final String scenarioId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling get");
    }

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenarioId' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenarioId", scenarioId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}")
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

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiScenario> localVarReturnType =
        new ParameterizedTypeReference<AiScenario>() {};
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
   * Get list of scenarios
   *
   * <p>Retrieve a list of all available scenarios.
   *
   * <p><b>200</b> - A list of scenarios
   *
   * @param aiResourceGroup Specify a resource group id
   * @return AiScenarioList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiScenarioList query(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling query");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios").build().toUriString();

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

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiScenarioList> localVarReturnType =
        new ParameterizedTypeReference<AiScenarioList>() {};
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
   * Get information about all models available in LLM global scenario
   *
   * <p>Retrieve information about all models available in LLM global scenario
   *
   * <p><b>200</b> - The request was successful and information of all LLM models will be returned.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param scenarioId Scenario identifier
   * @param aiResourceGroup Specify a resource group id
   * @return AiModelList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiModelList queryModels(
      @Nonnull final String scenarioId, @Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenarioId' when calling queryModels");
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling queryModels");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenarioId", scenarioId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}/models")
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

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiModelList> localVarReturnType =
        new ParameterizedTypeReference<AiModelList>() {};
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
   * Get list of versions for scenario
   *
   * <p>Retrieve a list of scenario versions based on the versions of executables available within
   * that scenario.
   *
   * <p><b>200</b> - A list of versions for the scenario
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param scenarioId (required) Scenario identifier
   * @param labelSelector (optional filter by labels. Pass in expressions in the form of
   *     \&quot;key&#x3D;value\&quot; or \&quot;key!&#x3D;value\&quot; separated by commas and the
   *     result will be filtered to only those resources that have labels that match all provided
   *     expressions (i.e. logical AND)
   * @return AiVersionList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiVersionList queryVersions(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String scenarioId,
      @Nullable final List<String> labelSelector)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling queryVersions");
    }

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenarioId' when calling queryVersions");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenarioId", scenarioId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}/versions")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "labelSelector",
            labelSelector));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiVersionList> localVarReturnType =
        new ParameterizedTypeReference<AiVersionList>() {};
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
   * Get list of versions for scenario
   *
   * <p>Retrieve a list of scenario versions based on the versions of executables available within
   * that scenario.
   *
   * <p><b>200</b> - A list of versions for the scenario
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param scenarioId Scenario identifier
   * @return AiVersionList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiVersionList queryVersions(
      @Nonnull final String aiResourceGroup, @Nonnull final String scenarioId)
      throws OpenApiRequestException {
    return queryVersions(aiResourceGroup, scenarioId, null);
  }
}
