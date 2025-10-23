package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.KpiColumnName;
import com.sap.ai.sdk.core.model.KpiResultSet;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
public class KpiApi extends AbstractOpenApiService {

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
   * @param $select (optional Columns to select
   * @return KpiResultSet
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public KpiResultSet get(@Nullable final Set<KpiColumnName> $select)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/analytics/kpis").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "$select",
            $select));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<KpiResultSet> localVarReturnType =
        new ParameterizedTypeReference<KpiResultSet>() {};
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
