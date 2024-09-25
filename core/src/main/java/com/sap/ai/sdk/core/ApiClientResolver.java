package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface ApiClientResolver {

    Destination getDestination();

    default  <T> T forService(@Nonnull final Service<T> service) {
        return service.builder(this);
    }


    interface Service<T> {
        Service<OrchestrationBuilder> ORCHESTRATION = resolver -> resourceGroup -> () -> {
                    Predicate<AiDeployment> predicate = d -> "orchestration".equals(d.getScenarioId());
                    String deploymentId = getDeploymentId(resolver, resourceGroup, predicate);
                    return getDestinationForDeployment(resolver, deploymentId, resourceGroup);
                };

        Service<OpenAiBuilder> OPENAI =  resolver -> modelName -> resourceGroup -> () -> {
            Predicate<AiDeployment> predicate = d -> isDeploymentOfModel(modelName, d);
            String deploymentId = getDeploymentId(resolver, resourceGroup, predicate);
            return getDestinationForDeployment(resolver, deploymentId, resourceGroup);
        };

        Service<CoreBuilder> CORE = (resolver) -> () -> resolver.getDestination();

        T builder(ApiClientResolver resolver);

        @FunctionalInterface
        interface Build {
            Destination getDestination();

            default Build withNull() {
                Build that = this;
                return new Build() {
                    @Override
                    public Destination getDestination() {
                        return that.getDestination();
                    }

                    @Override
                    public ApiClient getClient() {
                        return ApiClientBuilder.ApiClientConstructor.SERIALIZE_WITH_NULL_VALUES.finalizer.apply(getDestination());
                    }
                };
            }
            default Build withoutNull() {
                Build that = this;
                return new Build() {
                    @Override
                    public Destination getDestination() {
                        return that.getDestination();
                    }

                    @Override
                    public ApiClient getClient() {
                        return ApiClientBuilder.ApiClientConstructor.SERIALIZE_WITHOUT_NULL_VALUES.finalizer.apply(getDestination());
                    }
                };
            }

            default ApiClient getClient() {
                return ApiClientBuilder.ApiClientConstructor.SERIALIZE_WITHOUT_NULL_VALUES.finalizer.apply(getDestination());
            }
        }
        @FunctionalInterface
        interface CoreBuilder extends Build{
        }
        @FunctionalInterface
        interface OrchestrationBuilder {
            @Nonnull
            Build resourceGroup(@Nonnull final String resourceGroup);
        }
        @FunctionalInterface
        interface OpenAiBuilder {
            @Nonnull
            OpenAiBuilder1 model(@Nonnull final String modelName);
            @Nonnull
            default OpenAiBuilder1 deploymentId(@Nonnull final String model) {
                return null;
            }
        }
        @FunctionalInterface
        interface OpenAiBuilder1 {
            @Nonnull
            Build resourceGroup(@Nonnull final String resourceGroup);
        }
    }


    /**
     * Get the deployment id from the scenario id. If there are multiple deployments of the same
     * scenario id, the first one is returned.
     *
     * @param resourceGroup the resource group.
     * @return the deployment id
     * @throws NoSuchElementException if no deployment is found for the scenario id.
     */
    private static String getDeploymentId(@Nonnull final ApiClientResolver resolver, @Nonnull final String resourceGroup, @Nonnull final Predicate<AiDeployment> predicate)
            throws NoSuchElementException {
        final var deploymentService = new DeploymentApi(resolver.forService(Service.CORE).getClient());
        final var deployments =
                deploymentService.deploymentQuery(resourceGroup);

        return deployments.getResources().stream()
                .filter(predicate)
                .map(AiDeployment::getId)
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
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
    private static Destination getDestinationForDeployment(
            @Nonnull final ApiClientResolver resolver,
            @Nonnull final String deploymentId, @Nonnull final String resourceGroup) {
        final var destination = resolver.getDestination().asHttp();
        final DefaultHttpDestination.Builder builder =
                DefaultHttpDestination.fromDestination(destination)
                        .uri(
                                destination
                                        .getUri()
                                        .resolve("/v2/inference/deployments/%s/".formatted(deploymentId)));

        builder.header("AI-Resource-Group", resourceGroup);

        return builder.build();
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
