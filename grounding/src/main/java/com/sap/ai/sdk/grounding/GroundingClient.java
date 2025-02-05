package com.sap.ai.sdk.grounding;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.grounding.api.PipelinesApi;
import com.sap.ai.sdk.grounding.api.RetrievalApi;
import com.sap.ai.sdk.grounding.api.VectorApi;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Service class for the Grounding APIs.
 *
 * @since 1.3.0
 */
@Beta
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GroundingClient {

  final AiCoreService aiCoreService;
  final String basePath;

  static final String DEFAULT_BASE_PATH = "lm/document-grounding/";

  /**
   * Create a new instance of the GroundingService.
   *
   * @return A new instance of the GroundingService.
   */
  @Nonnull
  public static GroundingClient create() {
    return create(new AiCoreService());
  }

  /**
   * Create a new instance of the GroundingService.
   *
   * @param aiCoreService The AiCoreService instance to use.
   * @return A new instance of the GroundingService.
   */
  @Nonnull
  public static GroundingClient create(@Nonnull final AiCoreService aiCoreService) {
    return create(aiCoreService, DEFAULT_BASE_PATH);
  }

  /**
   * Create a new instance of the GroundingService.
   *
   * @param aiCoreService The AiCoreService instance to use.
   * @param basePath The base path to use for the API calls.
   * @return A new instance of the GroundingService.
   */
  @Nonnull
  public static GroundingClient create(
      @Nonnull final AiCoreService aiCoreService, @Nonnull final String basePath) {
    return new GroundingClient(aiCoreService, basePath);
  }

  /**
   * Get the Pipelines API.
   *
   * @return The Pipelines API.
   */
  @Nonnull
  public PipelinesApi pipelines() {
    return new PipelinesApi(aiCoreService.getApiClient().setBasePath(basePath));
  }

  /**
   * Get the Vector API.
   *
   * @return The Vector API.
   */
  @Nonnull
  public VectorApi vector() {
    return new VectorApi(aiCoreService.getApiClient().setBasePath(basePath));
  }

  /**
   * Get the Retrieval API.
   *
   * @return The Retrieval API.
   */
  @Nonnull
  public RetrievalApi retrieval() {
    return new RetrievalApi(aiCoreService.getApiClient().setBasePath(basePath));
  }
}
