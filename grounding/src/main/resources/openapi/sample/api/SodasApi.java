/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

package openapi.sample.api;

import com.google.common.annotations.Beta;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.datamodel.openapi.sample.model.Soda;
import com.sap.cloud.sdk.datamodel.openapi.sample.model.SodaWithId;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
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
 * SodaStore API in version 1.0.0.
 *
 * <p>API for managing soda products and orders in SodaStore.
 */
public class SodasApi extends AbstractOpenApiService {
  /**
   * Instantiates this API class to invoke operations on the SodaStore API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public SodasApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the SodaStore API based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  @Beta
  public SodasApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Get all soda products
   *
   * <p>
   *
   * <p><b>200</b> - A list of soda products
   *
   * @return List&lt;SodaWithId&gt;
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public List<SodaWithId> sodasGet() throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath = UriComponentsBuilder.fromPath("/sodas").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"apiKeyAuth"};

    final ParameterizedTypeReference<List<SodaWithId>> localVarReturnType =
        new ParameterizedTypeReference<List<SodaWithId>>() {};
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
   * Get a specific soda product by ID
   *
   * <p>
   *
   * <p><b>200</b> - The soda product
   *
   * <p><b>404</b> - Soda product not found
   *
   * @param id ID of the soda product to retrieve
   * @return SodaWithId
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SodaWithId sodasIdGet(@Nonnull final Long id) throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'id' when calling sodasIdGet");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("id", id);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/sodas/{id}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"apiKeyAuth"};

    final ParameterizedTypeReference<SodaWithId> localVarReturnType =
        new ParameterizedTypeReference<SodaWithId>() {};
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
   * Update a specific soda product by ID
   *
   * <p>
   *
   * <p><b>200</b> - The updated soda product
   *
   * <p><b>204</b> - Nothing has changed
   *
   * <p><b>404</b> - Soda product not found
   *
   * @param sodaWithId The updated soda product
   * @return Soda
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nullable
  public Soda sodasPut(@Nonnull final SodaWithId sodaWithId) throws OpenApiRequestException {
    final Object localVarPostBody = sodaWithId;

    // verify the required parameter 'sodaWithId' is set
    if (sodaWithId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'sodaWithId' when calling sodasPut");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/sodas").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"apiKeyAuth"};

    final ParameterizedTypeReference<Soda> localVarReturnType =
        new ParameterizedTypeReference<Soda>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.PUT,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }
}
