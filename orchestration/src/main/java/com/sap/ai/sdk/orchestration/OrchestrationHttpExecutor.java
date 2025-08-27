package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationClientException.FACTORY;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.HttpClientInstantiationException;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

@Slf4j
class OrchestrationHttpExecutor {
  private final Supplier<HttpDestination> destinationSupplier;

  private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  OrchestrationHttpExecutor(@Nonnull final Supplier<HttpDestination> destinationSupplier)
      throws OrchestrationClientException {
    this.destinationSupplier = destinationSupplier;
  }

  @Nonnull
  <T> T execute(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<T> responseType,
      @Nonnull final List<Header> customHeaders) {
    try {
      val json = JACKSON.writeValueAsString(payload);
      log.debug("Successfully serialized request into JSON payload");
      val request = new HttpPost(path);
      request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
      customHeaders.forEach(h -> request.addHeader(h.getName(), h.getValue()));

      val client = getHttpClient();

      val handler =
          new ClientResponseHandler<>(responseType, OrchestrationError.Synchronous.class, FACTORY)
              .objectMapper(JACKSON);
      return client.execute(request, handler);

    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request payload for " + path, e);
    } catch (DeploymentResolutionException
        | DestinationAccessException
        | DestinationNotFoundException
        | HttpClientInstantiationException
        | IOException e) {
      throw new OrchestrationClientException(
          "Request to Orchestration service failed for " + path, e);
    }
  }

  @Nonnull
  Stream<OrchestrationChatCompletionDelta> stream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final List<Header> customHeaders) {
    try {
      val json = JACKSON.writeValueAsString(payload);
      val request = new HttpPost(path);
      request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
      customHeaders.forEach(h -> request.addHeader(h.getName(), h.getValue()));

      val client = getHttpClient();

      return new ClientStreamingHandler<>(
              OrchestrationChatCompletionDelta.class, OrchestrationError.Streaming.class, FACTORY)
          .objectMapper(JACKSON)
          .handleStreamingResponse(client.executeOpen(null, request, null));

    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException(
          "Failed to serialize payload for streaming request", e);
    } catch (IOException e) {
      throw new OrchestrationClientException(
          "Streaming request to the Orchestration service failed", e);
    }
  }

  @Nonnull
  private HttpClient getHttpClient() {
    val destination = destinationSupplier.get();
    log.debug("Using destination {} to connect to orchestration service", destination);
    return ApacheHttpClient5Accessor.getHttpClient(destination);
  }
}
