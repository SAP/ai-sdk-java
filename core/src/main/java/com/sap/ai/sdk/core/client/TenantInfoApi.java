package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.TntTenantInfo;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;

/**
 * AI Core in version 2.42.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class TenantInfoApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public TenantInfoApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public TenantInfoApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  private TenantInfoApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public TenantInfoApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new TenantInfoApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Information about a specified tenant
   *
   * <p>Tenant information containing the service plan that the tenant is subscribed to.
   *
   * <p><b>200</b> - A tenant info object
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @return TntTenantInfo
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TntTenantInfo get() throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/admin/tenantInfo";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<TntTenantInfo> localVarReturnType = new TypeReference<TntTenantInfo>() {};

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
}
