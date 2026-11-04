package com.sap.ai.sdk.foundationmodels.anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.AnthropicClientImpl;
import com.anthropic.core.ClientOptions;
import com.anthropic.services.blocking.MessageService;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating official Anthropic SDK clients configured for SAP AI Core deployments.
 *
 * <p>Returns a fully configured {@link AnthropicClient} that routes all HTTP traffic through the
 * SAP Cloud SDK's Apache HttpClient, providing automatic OAuth token refresh and destination
 * management. Requests are automatically adapted from the native Anthropic Messages API format to
 * the SAP AI Core Bedrock-style invoke endpoint.
 *
 * <p>Usage:
 *
 * <pre>{@code
 * var client = AiCoreAnthropicClient.forModel(AnthropicModel.CLAUDE_45_SONNET).client();
 * var response = client.messages().create(
 *     MessageCreateParams.builder()
 *         .model(Model.CLAUDE_CLAUDE_4_5_SONNET)
 *         .maxTokens(1024)
 *         .addUserMessage("What is the capital of France?")
 *         .build());
 * }</pre>
 *
 * @since 1.26.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AiCoreAnthropicClient {

  private static final String DEFAULT_RESOURCE_GROUP = "default";

  private final HttpDestination destination;

  /**
   * Create a client for a deployment serving the specified model using the default resource group.
   *
   * @param model the Anthropic model to target.
   * @return a configured factory instance.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  public static AiCoreAnthropicClient forModel(@Nonnull final AnthropicModel model) {
    return forModel(model, DEFAULT_RESOURCE_GROUP);
  }

  /**
   * Create a client for a deployment serving the specified model in the given resource group.
   *
   * @param model the Anthropic model to target.
   * @param resourceGroup the resource group containing the deployment.
   * @return a configured factory instance.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  public static AiCoreAnthropicClient forModel(
      @Nonnull final AnthropicModel model, @Nonnull final String resourceGroup) {
    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);
    return forDestination(destination);
  }

  @Nonnull
  static AiCoreAnthropicClient forDestination(@Nonnull final HttpDestination destination) {
    return new AiCoreAnthropicClient(destination);
  }

  /**
   * Returns configured client for Anthropic messages API.
   *
   * @return client implementation
   */
  @Nonnull
  public MessageService messages() {
    return forDestination(destination).client().messages();
  }

  /**
   * Get a configured {@link AnthropicClient} for the deployment.
   *
   * <p>The client uses SAP Cloud SDK's Apache HttpClient for all HTTP communication. Anthropic API
   * key authentication is disabled since the SAP AI Core gateway handles OAuth token-based
   * authorization.
   *
   * @return a ready-to-use Anthropic client.
   */
  @Nonnull
  private AnthropicClient client() {
    final var options =
        ClientOptions.builder()
            .httpClient(new SapAnthropicHttpClient(destination))
            .baseUrl(destination.getUri().toString())
            .build();
    return new AnthropicClientImpl(options);
  }
}
