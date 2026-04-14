package com.sap.ai.sdk.foundationmodels.openaiwrapper;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.credential.BearerTokenCredential;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.AiModel;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.Collection;
import java.util.Locale;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/**
 * Provider for the official OpenAI Java SDK client, configured to work with SAP AI Core.
 *
 * <p>This is a thin wrapper around the <a href="https://github.com/openai/openai-java">official
 * OpenAI Java SDK</a> that handles:
 *
 * <ul>
 *   <li><b>Base URL construction</b>: Resolves the correct deployment URL from SAP AI Core using
 *       the model name (and optionally resource group).
 *   <li><b>Authentication</b>: Injects the SAP AI Core OAuth bearer token into each request via a
 *       dynamic {@link BearerTokenCredential}.
 * </ul>
 *
 * <p>The returned {@link OpenAIClient} is the official SDK client and can be used with the full
 * OpenAI API surface (chat completions, embeddings, streaming, function calling, etc.) as
 * documented in the <a href="https://platform.openai.com/docs">OpenAI API documentation</a>.
 *
 * <p><b>No additional abstraction layers are added.</b> Request and response types are the official
 * SDK types.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * import com.openai.models.chat.completions.ChatCompletionCreateParams;
 * import com.openai.models.ChatModel;
 * import com.openai.models.chat.completions.ChatCompletion;
 *
 * OpenAIClient client = OpenAiClientProvider.forModel(OpenAiModel.GPT_4O);
 *
 * ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
 *     .addUserMessage("Hello, world!")
 *     .model(ChatModel.GPT_4O)
 *     .build();
 *
 * ChatCompletion completion = client.chat().completions().create(params);
 * }</pre>
 *
 * @see <a href="https://github.com/openai/openai-java">OpenAI Java SDK</a>
 * @see <a href="https://api.sap.com/api/AI_CORE_API/overview">SAP AI Core</a>
 */
@Slf4j
public final class OpenAiClientProvider {

  private OpenAiClientProvider() {}

  /**
   * Creates an {@link OpenAIClient} for the given model using the default {@link AiCoreService}.
   *
   * <p>This resolves the deployment URL for the given model from SAP AI Core and configures the
   * official OpenAI client with the correct base URL and authentication.
   *
   * @param model The {@link AiModel} to use (e.g. an {@code OpenAiModel} constant).
   * @return A configured {@link OpenAIClient} ready to make requests via SAP AI Core.
   */
  @Nonnull
  public static OpenAIClient forModel(@Nonnull final AiModel model) {
    return forModel(new AiCoreService(), model);
  }

  /**
   * Creates an {@link OpenAIClient} for the given model using the provided {@link AiCoreService}.
   *
   * @param aiCoreService The {@link AiCoreService} to use for deployment resolution.
   * @param model The {@link AiModel} to use.
   * @return A configured {@link OpenAIClient} ready to make requests via SAP AI Core.
   */
  @Nonnull
  public static OpenAIClient forModel(
      @Nonnull final AiCoreService aiCoreService, @Nonnull final AiModel model) {
    final HttpDestination destination = aiCoreService.getInferenceDestination().forModel(model);
    return buildClient(destination);
  }

  /**
   * Creates an {@link OpenAIClient} using a custom {@link HttpDestination}.
   *
   * <p>This is useful for advanced scenarios where you need full control over the destination
   * configuration.
   *
   * @param destination The destination to use.
   * @return A configured {@link OpenAIClient}.
   */
  @Nonnull
  public static OpenAIClient withCustomDestination(@Nonnull final HttpDestination destination) {
    return buildClient(destination);
  }

  @Nonnull
  private static OpenAIClient buildClient(@Nonnull final HttpDestination destination) {
    final DefaultHttpDestination defaultDest = (DefaultHttpDestination) destination;
    final String baseUrl = defaultDest.getUri().toString() + "v1/";
    log.debug("Building OpenAI client with base URL: {}", baseUrl);

    return OpenAIOkHttpClient.builder()
        .baseUrl(baseUrl)
        .credential(BearerTokenCredential.create(() -> extractBearerToken(defaultDest)))
        .putHeader("AI-Resource-Group", getResourceGroupHeader(defaultDest))
        .build();
  }

  /**
   * Extracts the bearer token from the destination's authorization headers.
   *
   * <p>The SAP Cloud SDK destination resolves the OAuth token from the service binding. This method
   * extracts it so it can be passed to the OpenAI SDK's credential mechanism.
   */
  @Nonnull
  static String extractBearerToken(@Nonnull final DefaultHttpDestination destination) {
    final Collection<Header> headers = destination.getHeaders(destination.getUri());
    return headers.stream()
        .filter(h -> "Authorization".equalsIgnoreCase(h.getName()))
        .map(Header::getValue)
        .filter(v -> v.toLowerCase(Locale.ROOT).startsWith("bearer "))
        .map(v -> v.substring("Bearer ".length()).trim())
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "No Bearer token found in destination headers. "
                        + "Ensure the destination is configured with OAuth authentication."));
  }

  /** Extracts the AI-Resource-Group header value from the destination, defaulting to "default". */
  @Nonnull
  static String getResourceGroupHeader(@Nonnull final DefaultHttpDestination destination) {
    final Collection<Header> headers = destination.getHeaders(destination.getUri());
    return headers.stream()
        .filter(h -> "AI-Resource-Group".equalsIgnoreCase(h.getName()))
        .map(Header::getValue)
        .findFirst()
        .orElse("default");
  }
}
