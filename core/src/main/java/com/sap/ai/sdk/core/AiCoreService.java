package com.sap.ai.sdk.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Iterables;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Connectivity convenience methods for AI Core, offering convenient access to destinations
 * targeting the AI Core service. Loads base destinations from the environment or allows for setting
 * a custom base destination.
 */
@Slf4j
@Getter(AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AiCoreService {
  /** The default resource group. */
  public static final String DEFAULT_RESOURCE_GROUP = "default";

  private static final String RESOURCE_GROUP_HEADER_PROPERTY = "URL.headers.AI-Resource-Group";

  @Nonnull private final Supplier<HttpDestination> baseDestinationResolver;
  @Nonnull private final DeploymentResolver deploymentResolver;

  /** The default constructor. */
  public AiCoreService() {
    val resolver = new DestinationResolver();
    this.baseDestinationResolver = resolver::getDestination;
    this.deploymentResolver = new DeploymentResolver(this);
  }

  AiCoreService(@Nonnull final Supplier<HttpDestination> baseDestinationResolver) {
    this.baseDestinationResolver = baseDestinationResolver;
    this.deploymentResolver = new DeploymentResolver(this);
  }

  /**
   * Set a specific base destination. This is useful when loading a destination from the BTP
   * destination service or some other source.
   *
   * <p><b>Note:</b> For typical scenarios, the destination is expected to have the {@code /v2/}
   * base path set. But for special cases a different base path may be required (e.g. when consuming
   * AI Core via some proxy that expects a different base path).
   *
   * @param destination The base destination to be used for AI Core service calls.
   * @return A new AI Core Service object using the provided destination as basis.
   */
  @Nonnull
  public AiCoreService withBaseDestination(@Nonnull final HttpDestination destination) {
    return new AiCoreService(() -> DestinationResolver.fromCustomBaseDestination(destination));
  }

  /**
   * Get the base destination for AI Core service calls. This destination won't have any resource
   * group set.
   *
   * @return The base destination.
   * @throws DestinationAccessException If there was an issue creating the base destination, e.g. in
   *     case of invalid credentials.
   * @throws DestinationNotFoundException If there was an issue creating the base destination, e.g.
   *     in case of missing credentials.
   * @see #withBaseDestination(HttpDestination)
   */
  @Nonnull
  public HttpDestination getBaseDestination()
      throws DestinationAccessException, DestinationNotFoundException {
    return baseDestinationResolver.get();
  }

  /**
   * Get a new endpoint object, targeting a specific deployment ID.
   *
   * @param deploymentId The deployment id to be used for the new endpoint.
   * @return the new instance.
   */
  @Nonnull
  public HttpDestination getDestinationForDeploymentById(
      @Nonnull final String resourceGroup, @Nonnull final String deploymentId) {
    return toInferenceDestination(resourceGroup, deploymentId);
  }

  /**
   * Get a destination to perform inference calls for a specific model. If there are multiple
   * deployments of the same model, the first one is returned.
   *
   * @param model The model to be used for inference calls.
   * @return A new instance of the AI Core Deployment.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  public HttpDestination getDestinationForDeploymentByModel(
      @Nonnull final String resourceGroup, @Nonnull final AiModel model)
      throws DeploymentResolutionException {
    val deploymentId = deploymentResolver.getDeploymentIdByModel(resourceGroup, model);
    return toInferenceDestination(resourceGroup, deploymentId);
  }

  /**
   * Set a specific deployment by scenario id. If there are multiple deployments of the same model,
   * the first one is returned.
   *
   * @param scenarioId The scenario id to be used for AI Core service calls.
   * @return A new instance of the AI Core Deployment.
   * @throws DeploymentResolutionException if no running deployment is found for the scenario.
   */
  @Nonnull
  public HttpDestination getDestinationForDeploymentByScenario(
      @Nonnull final String resourceGroup, @Nonnull final String scenarioId)
      throws DeploymentResolutionException {
    val deploymentId = deploymentResolver.getDeploymentIdByScenario(resourceGroup, scenarioId);
    return toInferenceDestination(resourceGroup, deploymentId);
  }

  @Nonnull
  public ApiClient getApiClient() {
    val destination = getBaseDestination();
    val httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

    val rt = new RestTemplate();
    val objectMapper =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
            .build();

    Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
        .forEach(converter -> converter.setObjectMapper(objectMapper));
    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
  }

  HttpDestination toInferenceDestination(
      @Nonnull final String resourceGroup, @Nonnull final String deploymentId) {
    val destination = getBaseDestination();
    val path = buildDeploymentPath(deploymentId);

    return DefaultHttpDestination.fromDestination(destination)
        .uri(destination.getUri().resolve(path))
        .property(RESOURCE_GROUP_HEADER_PROPERTY, resourceGroup)
        .build();
  }

  @Nonnull
  private static String buildDeploymentPath(@Nonnull final String deploymentId) {
    return "inference/deployments/%s/".formatted(deploymentId);
  }

  /**
   * Remove all entries from the cache then load all deployments into the cache.
   *
   * <p><b>Call this whenever a deployment is deleted.</b>
   *
   * @param resourceGroup the resource group of the deleted deployment, usually "default".
   */
  public void reloadCachedDeployments(@Nonnull final String resourceGroup) {
    new DeploymentResolver(this).reloadDeployments(resourceGroup);
  }
}
