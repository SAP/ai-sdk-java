package com.sap.ai.sdk.core;

import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.annotations.Beta;
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

  private static final JsonMapper objectMapper = getDefaultObjectMapper();
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
   * Get a destination to perform inference calls against a deployment under the default resource
   * group on AI Core.
   *
   * @return The destination pointing to the specific deployment ID.
   * @throws DestinationAccessException If there was an issue creating the base destination, e.g. in
   *     case of invalid credentials.
   * @throws DestinationNotFoundException If there was an issue creating the base destination, e.g.
   *     in case of missing credentials.
   * @see #getInferenceDestination(String) for specifying a custom resource group.
   */
  @Nonnull
  public InferenceDestinationBuilder getInferenceDestination()
      throws DestinationAccessException, DestinationNotFoundException {
    return new InferenceDestinationBuilder(DEFAULT_RESOURCE_GROUP);
  }

  /**
   * Get a destination to perform inference calls against a deployment for the given resource group
   * on AI Core.
   *
   * @param resourceGroup The resource group to be used for the new endpoint.
   * @return The destination pointing to the specific deployment ID.
   * @see #getInferenceDestination() for using the default resource group.
   */
  @Nonnull
  public InferenceDestinationBuilder getInferenceDestination(@Nonnull final String resourceGroup) {
    return new InferenceDestinationBuilder(resourceGroup);
  }

  /**
   * Get an {@link ApiClient} to execute requests based on clients generated from OpenAPI
   * specifications.
   *
   * @return A new client object based on {@link #getBaseDestination()}.
   */
  @Nonnull
  @Beta
  public ApiClient getApiClient() {
    val destination = getBaseDestination();
    val httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

    val rt = new RestTemplate();
    Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
        .forEach(converter -> converter.setObjectMapper(objectMapper));
    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
  }

  /**
   * Helper method to build the <b>relative</b> URL path for the inference endpoint of a deployment.
   * The result of this together with the base path defined on the destination will be used for
   * inference calls towards this deployment.
   *
   * @param deploymentId The deployment ID to be used for the path.
   * @return The path to the deployment.
   */
  @Nonnull
  @Beta
  protected String buildDeploymentPath(@Nonnull final String deploymentId) {
    return "inference/deployments/%s/".formatted(deploymentId);
  }

  /**
   * Remove all entries from the cache then load all deployments into the cache.
   *
   * <p><b>Call this whenever a deployment is deleted.</b>
   *
   * @param resourceGroup the resource group of the deleted deployment, usually "default".
   */
  @Beta
  public void reloadCachedDeployments(@Nonnull final String resourceGroup) {
    deploymentResolver.reloadDeployments(resourceGroup);
  }

  /** Builder for creating inference destinations. */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public class InferenceDestinationBuilder {
    @Nonnull private final String resourceGroup;

    /**
     * Use a fixed deployment ID to identify the deployment.
     *
     * @param deploymentId The ID of the deployment to target.
     * @return A new destination targeting the specified deployment.
     * @throws DestinationAccessException If there was an issue creating the base destination, e.g.
     *     in case of invalid credentials.
     * @throws DestinationNotFoundException If there was an issue creating the base destination,
     *     e.g. in case of missing credentials.
     * @see #forModel(AiModel)
     * @see #forScenario(String)
     */
    @Nonnull
    public HttpDestination usingDeploymentId(@Nonnull final String deploymentId)
        throws DestinationAccessException, DestinationNotFoundException {
      return build(deploymentId);
    }

    /**
     * Lookup a deployment based on the given {@link AiModel}. If there are multiple deployments for
     * the given model, the first one is returned.
     *
     * @param model The model to be used for inference calls.
     * @return A new destination targeting a deployment for the given model.
     * @throws DeploymentResolutionException If no running deployment is found for the model.
     * @see #forScenario(String)
     * @see #usingDeploymentId(String)
     */
    @Nonnull
    public HttpDestination forModel(@Nonnull final AiModel model)
        throws DeploymentResolutionException {
      val id = deploymentResolver.getDeploymentIdByModel(resourceGroup, model);
      return build(id);
    }

    /**
     * Lookup a deployment based on the given scenario. If there are multiple deployments within the
     * same scenario, the first one is returned.
     *
     * @param scenarioId The scenario to discover deployments for.
     * @return A new destination targeting a deployment within the given scenario.
     * @throws DeploymentResolutionException If no running deployment is found within the scenario.
     * @see #forModel(AiModel)
     * @see #usingDeploymentId(String)
     */
    @Nonnull
    public HttpDestination forScenario(@Nonnull final String scenarioId)
        throws DeploymentResolutionException {
      val id = deploymentResolver.getDeploymentIdByScenario(resourceGroup, scenarioId);
      return build(id);
    }

    @Nonnull
    HttpDestination build(@Nonnull final String deploymentId) {
      val destination = getBaseDestination();
      val path = buildDeploymentPath(deploymentId);

      return DefaultHttpDestination.fromDestination(destination)
          .uri(destination.getUri().resolve(path))
          .property(RESOURCE_GROUP_HEADER_PROPERTY, resourceGroup)
          .build();
    }
  }

  /**
   * Default object mapper used for JSON de-/serialization. <b>Only intended for internal usage
   * within this SDK</b>. Largely follows the defaults set by Spring.
   *
   * @return A new object mapper with the default configuration.
   * @see <a
   *     href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/json/Jackson2ObjectMapperBuilder.html">Jackson2ObjectMapperBuilder</a>
   */
  @Nonnull
  @Beta
  public static JsonMapper getDefaultObjectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .addModule(new ParameterNamesModule())
        // Disable automatic detection of getters and setters
        .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
        .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        // for generated code unknown properties should always be stored as custom fields
        // still added for any non-generated code and additional safety
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DEFAULT_VIEW_INCLUSION)
        .build();
  }
}
