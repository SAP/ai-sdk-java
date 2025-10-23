package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiExecutable;
import com.sap.ai.sdk.core.model.AiExecutableList;
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
public class ExecutableApi extends AbstractOpenApiService {

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
    final Object localVarPostBody = null;

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenarioId' when calling get");
    }

    // verify the required parameter 'executableId' is set
    if (executableId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'executableId' when calling get");
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenarioId", scenarioId);
    localVarPathParams.put("executableId", executableId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}/executables/{executableId}")
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

    final ParameterizedTypeReference<AiExecutable> localVarReturnType =
        new ParameterizedTypeReference<AiExecutable>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'scenarioId' is set
    if (scenarioId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'scenarioId' when calling query");
    }

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling query");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("scenarioId", scenarioId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/scenarios/{scenarioId}/executables")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "versionId", versionId));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutableList> localVarReturnType =
        new ParameterizedTypeReference<AiExecutableList>() {};
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
