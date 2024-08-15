package com.sap.ai.sdk.core;

import static com.sap.cloud.sdk.cloudplatform.connectivity.OnBehalfOf.TECHNICAL_USER_PROVIDER;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingBuilder;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingMerger;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationLoader;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationOptions;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** Connectivity convenience methods for AI Core. */
@Slf4j
public class Core {

  /**
   * <b>Requires an AI Core service binding.</b>
   *
   * @param resourceGroup the resource group.
   * @return a generic <code>Orchestration</code> ApiClient.
   */
  @Nonnull
  public static ApiClient getOrchestrationClient(@Nonnull final String resourceGroup) {
    return getClient(
        getDestinationForDeployment(getOrchestrationDeployment(resourceGroup), resourceGroup));
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the same
   * scenario id, the first one is returned.
   *
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the scenario id.
   */
  private static String getOrchestrationDeployment(@Nonnull final String resourceGroup)
      throws NoSuchElementException {
    final var deployments = new DeploymentApi(getClient(getDestination())).deploymentQuery(resourceGroup);

    return deployments.getResources().stream()
        .filter(deployment -> "orchestration".equals(deployment.getScenarioId()))
        .map(AiDeployment::getId)
        .findFirst()
        .orElseThrow(
            () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
  }

  /**
   * <b>Requires an AI Core service binding OR a service key in the environment variable {@code
   * AICORE_SERVICE_KEY}.</b>
   *
   * @return a generic <code>AI Core</code> ApiClient.
   */
  @Nonnull
  public static ApiClient getClient() {
    return getClient(getDestination());
  }

  /**
   * Get a generic <code>AI Core</code> ApiClient <b>for testing purposes</b>.
   *
   * @param destination The destination to use.
   * @return a generic <code>AI Core</code> ApiClient.
   */
  @Nonnull
  @SuppressWarnings("UnstableApiUsage")
  public static ApiClient getClient(@Nonnull final Destination destination) {
    final var objectMapper =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
            .build();

    final var httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

    final var restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().stream()
        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
        .map(MappingJackson2HttpMessageConverter.class::cast)
        .forEach(converter -> converter.setObjectMapper(objectMapper));
    restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(restTemplate).setBasePath(destination.asHttp().getUri().toString());
  }

  /**
   * <b>Requires an AI Core service binding OR a service key in the environment variable {@code
   * AICORE_SERVICE_KEY}.</b>
   *
   * @return a destination pointing to the AI Core service.
   */
  @Nonnull
  public static Destination getDestination() {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    final var serviceKeyPresent = serviceKey != null;
    final var aiCoreBindingPresent =
        DefaultServiceBindingAccessor.getInstance().getServiceBindings().stream()
            .anyMatch(
                serviceBinding ->
                    ServiceIdentifier.AI_CORE.equals(
                        serviceBinding.getServiceIdentifier().orElse(null)));

    if (!aiCoreBindingPresent && serviceKeyPresent) {
      addServiceBinding(serviceKey);
    }

    // get a destination pointing to the AI Core service
    final var opts =
        ServiceBindingDestinationOptions.forService(ServiceIdentifier.AI_CORE)
            .onBehalfOf(TECHNICAL_USER_PROVIDER)
            .build();
    var destination = ServiceBindingDestinationLoader.defaultLoaderChain().getDestination(opts);

    destination =
        DefaultHttpDestination.fromDestination(destination)
            // append the /v2 path here, so we don't have to do it in every request when using the
            // generated code this is actually necessary, because the generated code assumes this
            // path to be present on the destination
            .uri(destination.getUri().resolve("/v2"))
            .build();
    return destination;
  }

  /**
   * Set the AI Core service key as the service binding. This is used for local testing.
   *
   * @param serviceKey The service key in JSON format.
   * @throws AiCoreCredentialsInvalidException if the JSON service key cannot be parsed.
   */
  private static void addServiceBinding(@Nonnull final String serviceKey) {
    log.info(
        """
          Found a service key in environment variable "AICORE_SERVICE_KEY".
          Using a service key is recommended for local testing only.
          Bind the AI Core service to the application for productive usage.""");

    var credentials = new HashMap<String, Object>();
    try {
      credentials = new ObjectMapper().readValue(serviceKey, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new AiCoreCredentialsInvalidException(
          "Error in parsing service key from the \"AICORE_SERVICE_KEY\" environment variable.", e);
    }

    final var binding =
        new DefaultServiceBindingBuilder()
            .withServiceIdentifier(ServiceIdentifier.AI_CORE)
            .withCredentials(credentials)
            .build();
    final ServiceBindingAccessor accessor = DefaultServiceBindingAccessor.getInstance();
    final var newAccessor =
        new ServiceBindingMerger(
            List.of(accessor, () -> List.of(binding)), ServiceBindingMerger.KEEP_EVERYTHING);
    DefaultServiceBindingAccessor.setInstance(newAccessor);
  }

  /** Exception thrown when the JSON AI Core service key is invalid. */
  static class AiCoreCredentialsInvalidException extends RuntimeException {
    public AiCoreCredentialsInvalidException(
        @Nonnull final String message, @Nonnull final Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Get a destination pointing to the inference endpoint of a deployment on AI Core. <b>Requires an
   * AI Core service binding.</b>
   *
   * @param deploymentId The deployment id.
   * @param resourceGroup The resource group.
   * @return a destination that can be used for inference calls.
   */
  @Nonnull
  public static Destination getDestinationForDeployment(
      @Nonnull final String deploymentId, @Nonnull final String resourceGroup) {
    final var destination = getDestination().asHttp();
    final DefaultHttpDestination.Builder builder =
        DefaultHttpDestination.fromDestination(destination)
            .uri(
                destination
                    .getUri()
                    .resolve("/v2/inference/deployments/%s/".formatted(deploymentId)));

    builder.header("AI-Resource-Group", resourceGroup);

    return builder.build();
  }

  /**
   * Get a destination pointing to the inference endpoint of a deployment on AI Core. <b>Requires an
   * AI Core service binding.</b>
   *
   * @param modelName The name of the foundation model that is used by a deployment.
   * @param resourceGroup The resource group.
   * @return a destination that can be used for inference calls.
   */
  @Nonnull
  public static Destination getDestinationForModel(
      @Nonnull final String modelName, @Nonnull final String resourceGroup) {
    return getDestinationForDeployment(
        getDeploymentForModel(modelName, resourceGroup), resourceGroup);
  }

  /**
   * Get the deployment id from the model name. If there are multiple deployments of the same model,
   * the first one is returned.
   *
   * @param modelName the model name.
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the model name.
   */
  private static String getDeploymentForModel(
      @Nonnull final String modelName, @Nonnull final String resourceGroup)
      throws NoSuchElementException {
    final var deployments = new DeploymentApi(getClient()).deploymentQuery(resourceGroup);

    return deployments.getResources().stream()
        .filter(deployment -> isDeploymentOfModel(modelName, deployment))
        .map(AiDeployment::getId)
        .findFirst()
        .orElseThrow(
            () -> new NoSuchElementException("No deployment found with model name " + modelName));
  }

  /** This exists because getBackendDetails() is broken */
  private static boolean isDeploymentOfModel(
      @Nonnull final String modelName, @Nonnull final AiDeployment deployment) {
    final var deploymentDetails = deployment.getDetails();
    // The AI Core specification doesn't mention that this is nullable, but it can be.
    // Remove this check when the specification is fixed.
    if (deploymentDetails == null) {
      return false;
    }
    final var resources = deploymentDetails.getResources();
    if (resources == null) {
      return false;
    }
    if (!resources.getCustomFieldNames().contains("backend_details")) {
      return false;
    }
    final var detailsObject = resources.getCustomField("backend_details");

    if (detailsObject instanceof Map<?, ?> details
        && details.get("model") instanceof Map<?, ?> model
        && model.get("name") instanceof String name) {
      return modelName.equals(name);
    }
    return false;
  }
}
