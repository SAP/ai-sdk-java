package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.openai.core.ClientOptions;
import com.openai.services.async.ResponseServiceAsync;
import com.openai.services.async.ResponseServiceAsyncImpl;
import com.openai.services.blocking.ResponseService;
import com.openai.services.blocking.ResponseServiceImpl;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory for creating OpenAI SDK clients configured for SAP AI Core deployments.
 *
 * <p>This class provides factory methods that return fully configured OpenAI SDK clients using SAP
 * Cloud SDK's Apache HttpClient with automatic OAuth token refresh.
 *
 * @since 1.19.0
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public class AiCoreOpenAiClient {

  private final HttpDestination destination;
  private static final String DEFAULT_RESOURCE_GROUP = "default";

  /**
   * Create an OpenAI client for a deployment serving the specified model using the default resource
   * group.
   *
   * @param model The AI model to target.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static AiCoreOpenAiClient forModel(@Nonnull final OpenAiModel model) {
    return forModel(model, DEFAULT_RESOURCE_GROUP);
  }

  /**
   * Create an OpenAI client for a deployment serving the specified model in the given resource
   * group.
   *
   * @param model The AI model to target.
   * @param resourceGroup The resource group containing the deployment.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static AiCoreOpenAiClient forModel(
      @Nonnull final OpenAiModel model, @Nonnull final String resourceGroup) {
    final HttpDestination destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);
    return forDestination(destination);
  }

  @Nonnull
  static AiCoreOpenAiClient forDestination(@Nonnull final HttpDestination destination) {
    return new AiCoreOpenAiClient(destination);
  }

  @Nonnull
  private static ClientOptions buildClientOptions(@Nonnull final HttpDestination destination) {
    return ClientOptions.builder()
        .baseUrl(destination.getUri().toString())
        .httpClient(new AiCoreHttpClientImpl(destination))
        .apiKey("unused")
        .build();
  }

  /**
   * Get a synchronous ResponseService client for the configured model and resource group.
   *
   * @return A configured synchronous OpenAI ResponseService client.
   */
  @Nonnull
  public ResponseService responses() {
    return new ResponseServiceImpl(buildClientOptions(destination));
  }

  /**
   * Get an asynchronous client factory for the configured model and resource group.
   *
   * @return An Async factory for creating asynchronous OpenAI clients.
   */
  @Nonnull
  public Async async() {
    return new Async(destination);
  }

  /**
   * Asynchronous client factory for accessing OpenAI services with a pre-configured model and
   * resource group.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Async {
    private final HttpDestination destination;

    /**
     * Get an asynchronous ResponseService client for the configured model and resource group.
     *
     * @return A configured OpenAI ResponseServiceAsync client.
     */
    @Nonnull
    public ResponseServiceAsync responses() {
      return new ResponseServiceAsyncImpl(buildClientOptions(destination));
    }
  }
}
