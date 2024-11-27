package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AiCoreDeployment implements AiCoreDestination {
  private final AiCoreService aiCoreService;

  @Nonnull private final Supplier<String> deploymentId;

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group, default value "default".
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    aiCoreService.resourceGroup = resourceGroup;
    return this;
  }

  @Nonnull
  @Override
  public HttpDestination destination()
      throws DestinationAccessException, DestinationNotFoundException {
    aiCoreService.deploymentId = deploymentId.get();
    return aiCoreService.destination();
  }

  @Nonnull
  @Override
  public ApiClient client() {
    aiCoreService.deploymentId = deploymentId.get();
    return aiCoreService.client();
  }
}
