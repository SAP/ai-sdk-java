package com.sap.ai.sdk.grounding;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.grounding.client.PipelinesApi;
import com.sap.ai.sdk.grounding.client.RetrievalApi;
import com.sap.ai.sdk.grounding.client.VectorApi;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Tolerate;

/**
 * Service class for the Document Grounding APIs.
 *
 * @since 1.3.0
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(value = AccessLevel.PROTECTED)
public class GroundingClient {
  @Nonnull private final AiCoreService service;
  @Nonnull private final String basePath;
  @Nonnull private final List<Header> customHeaders = new ArrayList<>();

  static final String DEFAULT_BASE_PATH = "lm/document-grounding/";

  /** Default constructor. */
  @Tolerate
  public GroundingClient() {
    this(new AiCoreService());
  }

  /**
   * Constructor with custom AI Core service instance.
   *
   * @param service The instance of AI Core service
   */
  public GroundingClient(final @Nonnull AiCoreService service) {
    this(service, DEFAULT_BASE_PATH);
  }

  /**
   * Get the Pipelines API.
   *
   * @return The Pipelines API.
   */
  @Nonnull
  public PipelinesApi pipelines() {
    return new PipelinesApi(getClient());
  }

  /**
   * Get the Vector API.
   *
   * @return The Vector API.
   */
  @Nonnull
  public VectorApi vector() {
    return new VectorApi(getClient());
  }

  /**
   * Get the Retrieval API.
   *
   * @return The Retrieval API.
   */
  @Nonnull
  public RetrievalApi retrieval() {
    return new RetrievalApi(getClient());
  }

  /**
   * Create a new OpenAI client with a custom header added to every call made with this client
   *
   * @param key the key of the custom header to add
   * @param value the value of the custom header to add
   * @return a new client.
   * @since 1.17.0
   */
  @Beta
  @Nonnull
  public GroundingClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new GroundingClient(this.service, this.basePath);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }

  @Nonnull
  private ApiClient getClient() {
    ApiClient apiClient = getService().getApiClient().setBasePath(getBasePath());
    for (Header header : customHeaders) {
      if (header.getValue() != null) {
        apiClient.addDefaultHeader(header.getName(), header.getValue());
      }
    }
    return apiClient;
  }
}
