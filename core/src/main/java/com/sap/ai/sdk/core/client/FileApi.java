package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.DSetFileCreationResponse;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.io.File;
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
public class FileApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public FileApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public FileApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Delete file
   *
   * <p>Delete the file specified by the path parameter.
   *
   * <p><b>204</b> - The request was processed successfully.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param path (required) path relative to the object store root URL in the secret
   * @param aiResourceGroup (optional) Specify a resource group id
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse delete(@Nonnull final String path, @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'path' is set
    if (path == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'path' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("path", path);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/dataset/files/{path}")
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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Delete file
   *
   * <p>Delete the file specified by the path parameter.
   *
   * <p><b>204</b> - The request was processed successfully.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param path path relative to the object store root URL in the secret
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse delete(@Nonnull final String path) throws OpenApiRequestException {
    return delete(path, null);
  }

  /**
   * Download file
   *
   * <p>Endpoint for downloading file. The path must point to an individual file.
   *
   * <p><b>200</b> - OK
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param path (required) path relative to the object store root URL in the secret
   * @param aiResourceGroup (optional) Specify a resource group id
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File download(@Nonnull final String path, @Nullable final String aiResourceGroup)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'path' is set
    if (path == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'path' when calling download");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("path", path);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/dataset/files/{path}")
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

    final String[] localVarAuthNames = new String[] {"Oauth2"};

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
   * Download file
   *
   * <p>Endpoint for downloading file. The path must point to an individual file.
   *
   * <p><b>200</b> - OK
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param path path relative to the object store root URL in the secret
   * @return File
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public File download(@Nonnull final String path) throws OpenApiRequestException {
    return download(path, null);
  }

  /**
   * Upload file (size &lt;&#x3D; 100Mb)
   *
   * <p>Endpoint for uploading file. The maximum file size depends on the actual implementation but
   * must not exceed 100MB. The actual file size limit can be obtained by querying the AI API
   * Runtime Capabilities Endpoint and checking the limits in the section of the
   * &#x60;fileUpload&#x60; extension. Path cannot be a prefix, it must be a path to an object.
   * Clients may group the objects in any manner they choose by specifying path prefixes. Allowed
   * mime-types will be decided by the implementation. Content-Type header can be set to
   * \&quot;application/octet-stream\&quot; but the implementation is responsible for detecting the
   * actual mime type and checking against the allowed list of mime types. For security reasons,
   * implementations cannot trust the mime type sent by the client. Example URLs:
   * /files/dar/schemas/schema.json /files/icr/datasets/training/20201001/20201001-01.csv
   * /files/icr/datasets/training/20201001/20201001-02.csv
   * /files/mask-detection/training/mask-detection-20210301.tar.gz
   *
   * <p><b>201</b> - Created
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>409</b> - The specified file already exists and cannot be overwritten.
   *
   * <p><b>413</b> - The file size exceeds the supported limit.
   *
   * @param path (required) path relative to the object store root URL in the secret
   * @param aiResourceGroup (optional) Specify a resource group id
   * @param overwrite (optional) If true, then file is overwritten. Default is false.
   * @param body (optional) Body of the file upload request
   * @return DSetFileCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DSetFileCreationResponse upload(
      @Nonnull final String path,
      @Nullable final String aiResourceGroup,
      @Nullable final Boolean overwrite,
      @Nullable final Object body)
      throws OpenApiRequestException {
    final Object localVarPostBody = body;

    // verify the required parameter 'path' is set
    if (path == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'path' when calling upload");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("path", path);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/dataset/files/{path}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "overwrite", overwrite));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<DSetFileCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<DSetFileCreationResponse>() {};
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

  /**
   * Upload file (size &lt;&#x3D; 100Mb)
   *
   * <p>Endpoint for uploading file. The maximum file size depends on the actual implementation but
   * must not exceed 100MB. The actual file size limit can be obtained by querying the AI API
   * Runtime Capabilities Endpoint and checking the limits in the section of the
   * &#x60;fileUpload&#x60; extension. Path cannot be a prefix, it must be a path to an object.
   * Clients may group the objects in any manner they choose by specifying path prefixes. Allowed
   * mime-types will be decided by the implementation. Content-Type header can be set to
   * \&quot;application/octet-stream\&quot; but the implementation is responsible for detecting the
   * actual mime type and checking against the allowed list of mime types. For security reasons,
   * implementations cannot trust the mime type sent by the client. Example URLs:
   * /files/dar/schemas/schema.json /files/icr/datasets/training/20201001/20201001-01.csv
   * /files/icr/datasets/training/20201001/20201001-02.csv
   * /files/mask-detection/training/mask-detection-20210301.tar.gz
   *
   * <p><b>201</b> - Created
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>409</b> - The specified file already exists and cannot be overwritten.
   *
   * <p><b>413</b> - The file size exceeds the supported limit.
   *
   * @param path path relative to the object store root URL in the secret
   * @return DSetFileCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DSetFileCreationResponse upload(@Nonnull final String path)
      throws OpenApiRequestException {
    return upload(path, null, null, null);
  }
}
