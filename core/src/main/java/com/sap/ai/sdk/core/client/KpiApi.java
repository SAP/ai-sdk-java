package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.KpiColumnName;
import com.sap.ai.sdk.core.model.KpiResultSet;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class KpiApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public KpiApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public KpiApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get KPIs
   *
   * <p>Retrieve the number of executions, artifacts, and deployments for each resource group,
   * scenario, and executable. The columns to be returned can be specified in a query parameter.
   *
   * <p><b>200</b> - KPIs
   *
   * <p><b>400</b> - Invalid request
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>429</b> - Too many requests
   *
   * @param $select (optional) Columns to select
   * @return KpiResultSet
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public KpiResultSet get(@Nullable final Set<KpiColumnName> $select)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // create path and map variables
    final String localVarPath = "/analytics/kpis";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarCollectionQueryParams.addAll(ApiClient.parameterToPairs("csv", "$select", $select));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<KpiResultSet> localVarReturnType = new TypeReference<KpiResultSet>() {};
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
   * Get KPIs
   *
   * <p>Retrieve the number of executions, artifacts, and deployments for each resource group,
   * scenario, and executable. The columns to be returned can be specified in a query parameter.
   *
   * <p><b>200</b> - KPIs
   *
   * <p><b>400</b> - Invalid request
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>429</b> - Too many requests
   *
   * @return KpiResultSet
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public KpiResultSet get() throws OpenApiRequestException {
    return get(null);
  }
}
