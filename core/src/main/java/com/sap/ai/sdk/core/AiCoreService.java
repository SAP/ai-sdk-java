package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.AiCoreDeployment.getDeploymentIdByModel;
import static com.sap.ai.sdk.core.AiCoreDeployment.getDeploymentIdByScenario;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_KEY;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_VALUE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperty;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** Connectivity convenience methods for AI Core. */
@Slf4j
@RequiredArgsConstructor
public class AiCoreService implements AiCoreDestination {

  final Function<AiCoreService, Destination> baseDestinationHandler;
  final BiFunction<AiCoreService, Destination, ApiClient> clientHandler;
  final BiFunction<AiCoreService, Destination, DefaultHttpDestination.Builder> builderHandler;

  /** The default constructor. */
  public AiCoreService() {
    this(
        AiCoreService::getBaseDestination,
        AiCoreService::createApiClient,
        AiCoreService::refineDestinationBuilder);
  }

  @Nonnull
  @Override
  public ApiClient client() {
    final var destination = destination();
    return clientHandler.apply(this, destination);
  }

  @Nonnull
  @Override
  public Destination destination() {
    final var dest = baseDestinationHandler.apply(this);
    return builderHandler.apply(this, dest).build();
  }

  /**
   * Set a specific base destination.
   *
   * @param destination The destination to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreService withDestination(@Nonnull final Destination destination) {
    return new AiCoreService((_ignore) -> destination, clientHandler, builderHandler);
  }

  /**
   * Set a specific deployment by id.
   *
   * @param deploymentId The deployment id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeployment(@Nonnull final String deploymentId) {
    return new AiCoreDeployment(this, res -> deploymentId);
  }

  /**
   * Set a specific deployment by model name. If there are multiple deployments of the same model,
   * the first one is returned.
   *
   * @param modelName The model name to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   * @throws NoSuchElementException if no running deployment is found for the model.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByModel(@Nonnull final String modelName) {
    return new AiCoreDeployment(
        res -> getDeploymentIdByModel(client(), res, modelName), this::destination);
  }

  /**
   * Set a specific deployment by scenario id. If there are multiple deployments of the * same
   * model, the first one is returned.
   *
   * @param scenarioId The scenario id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   * @throws NoSuchElementException if no running deployment is found for the scenario.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByScenario(@Nonnull final String scenarioId) {
    return new AiCoreDeployment(
        res -> getDeploymentIdByScenario(client(), res, scenarioId), this::destination);
  }

  /**
   * Get a destination using the default service binding loading logic.
   *
   * @return The destination.
   * @throws DestinationAccessException If the destination cannot be accessed.
   * @throws DestinationNotFoundException If the destination cannot be found.
   */
  @Nonnull
  protected Destination getBaseDestination()
      throws DestinationAccessException, DestinationNotFoundException {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return DestinationResolver.getDestination(serviceKey);
  }

  @Nonnull
  protected DefaultHttpDestination.Builder refineDestinationBuilder(
      @Nonnull final Destination destination) {
    final var builder = DefaultHttpDestination.fromDestination(destination);
    String uri = destination.get(DestinationProperty.URI).get();
    if (!uri.endsWith("/")) {
      uri = uri + "/";
    }
    builder.uri(uri + "v2/").property(AI_CLIENT_TYPE_KEY, AI_CLIENT_TYPE_VALUE);
    return builder;
  }

  /**
   * Get a destination using the default service binding loading logic.
   *
   * @return The destination.
   * @throws DestinationAccessException If the destination cannot be accessed.
   * @throws DestinationNotFoundException If the destination cannot be found.
   */
  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  protected ApiClient createApiClient(@Nonnull final Destination destination) {
    final var objectMapper =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
            .build();

    final var httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

    final var rt = new RestTemplate();
    Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
        .forEach(converter -> converter.setObjectMapper(objectMapper));
    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
  }
}
