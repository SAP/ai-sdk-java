package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor
public class AiCoreDeployment implements AiCoreDestination {
  private final AiCoreService aiCoreService;

  @Nonnull private final Function<AiCoreDeployment, String> deploymentId;

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    aiCoreService.setResourceGroup(resourceGroup);
    return this;
  }

  @Nonnull
  @Override
  public Destination destination() throws DestinationAccessException, DestinationNotFoundException {
    aiCoreService.deploymentId = deploymentId.apply(this);
    return aiCoreService.destination();
  }

  @Nonnull
  @Override
  public ApiClient client() {
    aiCoreService.deploymentId = deploymentId.apply(this);
    return aiCoreService.client();
  }

  String getResourceGroup() {
    return aiCoreService.getResourceGroup();
  }
}
