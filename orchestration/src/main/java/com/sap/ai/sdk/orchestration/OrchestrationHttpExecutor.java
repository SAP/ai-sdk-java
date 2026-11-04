package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.core.common.RequestLogContext.Mode.STREAMING;
import static com.sap.ai.sdk.core.common.RequestLogContext.Mode.SYNCHRONOUS;
import static com.sap.ai.sdk.core.common.RequestLogContext.Service.ORCHESTRATION;
import static com.sap.ai.sdk.orchestration.OrchestrationClientException.FACTORY;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.ai.sdk.core.common.RequestLogContext;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.HttpClientInstantiationException;
import java.io.IOException;
import java.net.SocketTimeoutException;
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
    String json;
    try {
      json = JACKSON.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request payload for " + path, e);
    }
    val request = new HttpPost(path);
    request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    customHeaders.forEach(h -> request.addHeader(h.getName(), h.getValue()));
    try {
      val client = getHttpClient();

      val handler =
          new ClientResponseHandler<>(
                  responseType, OrchestrationError.Synchronous.class, FACTORY.apply(request))
              .objectMapper(JACKSON);
      RequestLogContext.setEndpoint(path);
      RequestLogContext.setMode(SYNCHRONOUS);
      RequestLogContext.setService(ORCHESTRATION);
      RequestLogContext.logRequestStart();
      return client.execute(request, handler);

    } catch (SocketTimeoutException e) {
      throw FACTORY
          .apply(request)
          .build(
              "Request to Orchestration service took longer than 2 minutes. Please shorten your request or use streaming.",
              e);
      //      throw new OrchestrationClientException(
      //          "Request to Orchestration service took longer than 2 minutes. Please shorten your request or use streaming.",
      //          e).setHttpRequest(request);
    } catch (DeploymentResolutionException
        | DestinationAccessException
        | DestinationNotFoundException
        | HttpClientInstantiationException
        | IOException e) {
      throw FACTORY.apply(request).build("Request to Orchestration service failed for " + path, e);
      //      throw new OrchestrationClientException(
      //          "Request to Orchestration service failed for " + path, e).setHttpRequest(request);
    } finally {
      RequestLogContext.clear();
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
      RequestLogContext.setEndpoint(path);
      RequestLogContext.setMode(STREAMING);
      RequestLogContext.setService(ORCHESTRATION);
      RequestLogContext.logRequestStart();
      return new ClientStreamingHandler<>(
              OrchestrationChatCompletionDelta.class,
              OrchestrationError.Streaming.class,
              FACTORY.apply(request))
          .objectMapper(JACKSON)
          .handleStreamingResponse(client.executeOpen(null, request, null));

    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException(
          "Failed to serialize payload for streaming request", e);
    } catch (IOException e) {
      throw new OrchestrationClientException(
          "Streaming request to the Orchestration service failed", e);
    } finally {
      RequestLogContext.clear();
    }
  }

  @Nonnull
  private HttpClient getHttpClient() {
    val destination = destinationSupplier.get();
    RequestLogContext.setDestination(destination.getUri().toString());
    return ApacheHttpClient5Accessor.getHttpClient(destination);
  }
}
