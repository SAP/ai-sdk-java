package com.sap.ai.sdk.core;

import static com.sap.cloud.sdk.cloudplatform.connectivity.OnBehalfOf.TECHNICAL_USER_PROVIDER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingBuilder;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingMerger;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationLoader;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationOptions;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

/** Utility class to resolve the destination pointing to the AI Core service. */
@Slf4j
class DestinationResolver {
  static final String AI_CLIENT_TYPE_KEY = "URL.headers.AI-Client-Type";
  static final String AI_CLIENT_TYPE_VALUE = "AI SDK Java";
  static ServiceBindingAccessor accessor = DefaultServiceBindingAccessor.getInstance();

  /**
   * <b>For testing only</b>
   *
   * <p>Get a destination pointing to the AI Core service.
   *
   * @param serviceKey The service key in JSON format.
   * @return a destination pointing to the AI Core service.
   */
  @SuppressWarnings("UnstableApiUsage")
  static HttpDestination getDestination(@Nullable final String serviceKey) {
    final var serviceKeyPresent = serviceKey != null;
    final var aiCoreBindingPresent =
        accessor.getServiceBindings().stream()
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
            .property(AI_CLIENT_TYPE_KEY, AI_CLIENT_TYPE_VALUE)
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
}
