package com.sap.ai.sdk.foundationmodels.openai.responses;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;
import static com.sap.ai.sdk.foundationmodels.openai.responses.OpenAiResponsesClientException.FACTORY;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import java.io.IOException;
import javax.annotation.Nonnull;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Client for interacting with the OpenAI Responses API.
 *
 * <p>This client provides access to OpenAI's Responses API, which is a newer API that supersedes
 * the legacy Chat Completions API. It supports both synchronous and streaming responses.
 *
 * @since 1.17.0
 */
public class OpenAiResponsesClient {
  private final Destination destination;
  private static final String DEFAULT_SCENARIO = "foundation-models";
  private static final ObjectMapper JACKSON = getDefaultObjectMapper();

  /** Creates a new OpenAI Responses API client using the default resource group. */
  public OpenAiResponsesClient() {
    destination =
        new AiCoreService()
            .getInferenceDestination()
            .forModel(
                new AiModel() {
                  @Override
                  public @NonNull String name() {
                    return "gpt-4o";
                  }

                  @Override
                  public @Nullable String version() {
                    return "latest";
                  }
                });
  }

  /**
   * Creates a response synchronously for the given request.
   *
   * @param request The request configuration for the response
   * @return The complete response from the model
   * @throws OpenAiResponsesClientException if the request fails
   */
  @Nonnull
  public Response createResponse(@Nonnull final ResponseCreateParams request)
      throws OpenAiResponsesClientException {
    return execute("/responses", request);
  }

  @Nonnull
  private Response execute(@Nonnull final String path, @Nonnull final ResponseCreateParams request)
      throws OpenAiResponsesClientException {
    try {
      final var httpPost = new HttpPost(path);
      String json = JACKSON.writeValueAsString(request);
      httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

      final var client = ApacheHttpClient5Accessor.getHttpClient(destination);

      return client.execute(
          httpPost,
          new ClientResponseHandler<>(Response.class, OpenAiResponsesError.class, FACTORY));
    } catch (final IOException e) {
      throw new OpenAiResponsesClientException("Failed to serialize request", e);
    }
  }
}
