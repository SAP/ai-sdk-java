package com.sap.ai.sdk.grounding;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.grounding.client.PipelinesApi;
import com.sap.ai.sdk.grounding.client.RetrievalApi;
import com.sap.ai.sdk.grounding.client.VectorApi;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Tolerate;

/**
 * Service class for the Grounding APIs.
 *
 * @since 1.3.0
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(value = AccessLevel.PROTECTED)
public class GroundingClient {
  @Nonnull private final AiCoreService service;
  @Nonnull private final String basePath;

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
    return new PipelinesApi(getService().getApiClient().setBasePath(getBasePath()));
  }

  /**
   * Get the Vector API.
   *
   * @return The Vector API.
   */
  @Nonnull
  public VectorApi vector() {
    return new VectorApi(getService().getApiClient().setBasePath(getBasePath()));
  }

  /**
   * Get the Retrieval API.
   *
   * @return The Retrieval API.
   */
  @Nonnull
  public RetrievalApi retrieval() {
    return new RetrievalApi(getService().getApiClient().setBasePath(getBasePath()));
  }
}
