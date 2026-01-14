package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.ai.sdk.core.model.AiScenario;
import com.sap.ai.sdk.core.model.AiScenarioList;
import com.sap.ai.sdk.core.model.AiVersionList;
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
public class ScenarioApi extends BaseApi {

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
              "Missing the required parameter 'aiResourceGroup' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioId' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenarioId}"
            .replaceAll(
                "\\{" + "scenarioId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
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

    final TypeReference<AiScenario> localVarReturnType = new TypeReference<AiScenario>() {};
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
              "Missing the required parameter 'aiResourceGroup' when calling query")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/scenarios";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
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

    final TypeReference<AiScenarioList> localVarReturnType = new TypeReference<AiScenarioList>() {};
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
              "Missing the required parameter 'scenarioId' when calling queryModels")
          .statusCode(400);
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling queryModels")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenarioId}/models"
            .replaceAll(
                "\\{" + "scenarioId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
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

    final TypeReference<AiModelList> localVarReturnType = new TypeReference<AiModelList>() {};
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
   * @param labelSelector (optional) filter by labels. Pass in expressions in the form of
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
              "Missing the required parameter 'aiResourceGroup' when calling queryVersions")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioId' when calling queryVersions")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenarioId}/versions"
            .replaceAll(
                "\\{" + "scenarioId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "labelSelector", labelSelector));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiVersionList> localVarReturnType = new TypeReference<AiVersionList>() {};
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
