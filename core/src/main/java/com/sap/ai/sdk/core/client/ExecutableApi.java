package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiExecutable;
import com.sap.ai.sdk.core.model.AiExecutableList;
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
public class ExecutableApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ExecutableApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ExecutableApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get details about specific executable
   *
   * <p>Retrieve details about an executable identified by executableId belonging to a scenario
   * identified by scenarioId.
   *
   * <p><b>200</b> - An Executable
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param scenarioId Scenario identifier
   * @param executableId Executable identifier
   * @param aiResourceGroup Specify a resource group id
   * @return AiExecutable
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutable get(
      @Nonnull final String scenarioId,
      @Nonnull final String executableId,
      @Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioId' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'executableId' is set
    if (executableId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executableId' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenarioId}/executables/{executableId}"
            .replaceAll(
                "\\{" + "scenarioId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioId)))
            .replaceAll(
                "\\{" + "executableId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executableId)));

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

    final TypeReference<AiExecutable> localVarReturnType = new TypeReference<AiExecutable>() {};

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
   * Get list of executables
   *
   * <p>Retrieve a list of executables for a scenario. Filter by version ID, if required.
   *
   * <p><b>200</b> - A list of executables
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param scenarioId (required) Scenario identifier
   * @param aiResourceGroup (required) Specify a resource group id
   * @param versionId (optional) Version ID, if defined - returns the specified version
   * @return AiExecutableList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutableList query(
      @Nonnull final String scenarioId,
      @Nonnull final String aiResourceGroup,
      @Nullable final String versionId)
      throws OpenApiRequestException {

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioId' when calling query")
          .statusCode(400);
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling query")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/scenarios/{scenarioId}/executables"
            .replaceAll(
                "\\{" + "scenarioId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("versionId", versionId));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutableList> localVarReturnType =
        new TypeReference<AiExecutableList>() {};

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
   * Get list of executables
   *
   * <p>Retrieve a list of executables for a scenario. Filter by version ID, if required.
   *
   * <p><b>200</b> - A list of executables
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param scenarioId Scenario identifier
   * @param aiResourceGroup Specify a resource group id
   * @return AiExecutableList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutableList query(
      @Nonnull final String scenarioId, @Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return query(scenarioId, aiResourceGroup, null);
  }
}
