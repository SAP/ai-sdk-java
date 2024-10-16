package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AiCoreDeployment implements AiCoreDestination {
  private final AiCoreService aiCoreService;

  // the deployment id handler to be used, based on instance
  @Nonnull private final Function<AiCoreDeployment, String> deploymentId;

  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    aiCoreService.setResourceGroup(resourceGroup);
    return this;
  }

  @Nonnull
  public Destination destination() throws DestinationAccessException, DestinationNotFoundException {
    aiCoreService.deploymentId = deploymentId.apply(this);
    return aiCoreService.destination();
  }

  @Nonnull
  public ApiClient client() {
    aiCoreService.deploymentId = deploymentId.apply(this);
    return aiCoreService.client();
  }

  String getResourceGroup() {
    return aiCoreService.getResourceGroup();
  }
}
