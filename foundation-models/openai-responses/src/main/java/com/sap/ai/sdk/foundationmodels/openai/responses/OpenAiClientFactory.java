package com.sap.ai.sdk.foundationmodels.openai.responses;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.credential.BearerTokenCredential;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides factory methods for creating OpenAI SDK clients configured for SAP AI Core deployments.
 *
 * <p>This class handles deployment URL resolution, authentication credential extraction, and
 * automatic OAuth token refresh required for SAP AI Core integration.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * OpenAIClient client = OpenAiClientFactory.forModel(OpenAiModel.GPT_4);
 * }</pre>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenAiClientFactory {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String DEFAULT_RESOURCE_GROUP = "default";

  /**
   * Create an OpenAI client configured for a deployment that serves the specified model using the
   * default resource group. If multiple deployments serve the model, the first one found will be
   * used.
   *
   * @param model The AI model to target.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static OpenAIClient forModel(@Nonnull final AiModel model) {
    return forModel(model, DEFAULT_RESOURCE_GROUP);
  }

  /**
   * Create an OpenAI client configured for a deployment that serves the specified model in the
   * given resource group. If multiple deployments serve the model, the first one found will be
   * used.
   *
   * @param model The AI model to target.
   * @param resourceGroup The resource group containing the deployment.
   * @return A configured OpenAI client instance.
   * @throws DeploymentResolutionException If no running deployment is found for the model.
   */
  @Nonnull
  public static OpenAIClient forModel(
      @Nonnull final AiModel model, @Nonnull final String resourceGroup) {
    final HttpDestination destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forModel(model);
    return fromDestination(destination);
  }

  /**
   * Create an OpenAI client from an HttpDestination.
   *
   * <p>The client uses a token supplier for automatic OAuth token refresh and includes custom
   * headers from the destination for SAP AI Core routing.
   *
   * @param destination The destination to use for the OpenAI client.
   * @return A configured OpenAI client instance.
   */
  @Nonnull
  static OpenAIClient fromDestination(@Nonnull final HttpDestination destination) {
    final String baseUrl = destination.getUri().toString();
    final Supplier<String> tokenSupplier = () -> getAuthToken(destination);

    final OpenAIOkHttpClient.Builder builder =
        OpenAIOkHttpClient.builder()
            .baseUrl(baseUrl)
            .credential(BearerTokenCredential.create(tokenSupplier));

    addCustomHeaders(builder, destination);

    return builder.build();
  }

  @Nonnull
  private static String getAuthToken(@Nonnull final HttpDestination destination) {
    return destination.getHeaders().stream()
        .filter(header -> AUTHORIZATION_HEADER.equalsIgnoreCase(header.getName()))
        .findFirst()
        .map(Header::getValue)
        .map(authValue -> authValue.replace("Bearer", "").trim())
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "No Authorization header found in destination. Unable to extract bearer token."));
  }

  private static void addCustomHeaders(
      @Nonnull final OpenAIOkHttpClient.Builder builder,
      @Nonnull final HttpDestination destination) {
    destination.getHeaders().stream()
        .filter(header -> !AUTHORIZATION_HEADER.equalsIgnoreCase(header.getName()))
        .forEach(header -> builder.putHeader(header.getName(), header.getValue()));
  }
}
