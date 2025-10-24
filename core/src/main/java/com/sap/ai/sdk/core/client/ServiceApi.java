package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndExtendedService;
import com.sap.ai.sdk.core.model.BckndServiceList;
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
public class ServiceApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ServiceApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ServiceApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get a service
   *
   * <p>Get an service of a given main tenant.
   *
   * <p><b>200</b> - A service object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param serviceName (required) Name of the Service
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndExtendedService
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndExtendedService get(
      @Nonnull final String serviceName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'serviceName' is set
    if (serviceName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'serviceName' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("ServiceName", serviceName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/services/{ServiceName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndExtendedService> localVarReturnType =
        new ParameterizedTypeReference<BckndExtendedService>() {};
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
   * Get a service
   *
   * <p>Get an service of a given main tenant.
   *
   * <p><b>200</b> - A service object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param serviceName Name of the Service
   * @return BckndExtendedService
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndExtendedService get(@Nonnull final String serviceName)
      throws OpenApiRequestException {
    return get(serviceName, null);
  }

  /**
   * Gets all services of a given main tenant
   *
   * <p>Retrieve a list of services for a given main tenant.
   *
   * <p><b>200</b> - A list of services
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndServiceList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndServiceList getAll(@Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/services").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndServiceList> localVarReturnType =
        new ParameterizedTypeReference<BckndServiceList>() {};
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
   * Gets all services of a given main tenant
   *
   * <p>Retrieve a list of services for a given main tenant.
   *
   * <p><b>200</b> - A list of services
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndServiceList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndServiceList getAll() throws OpenApiRequestException {
    return getAll(null);
  }
}
