package com.sap.ai.sdk.core;

import static com.sap.cloud.sdk.cloudplatform.connectivity.OnBehalfOf.TECHNICAL_USER_PROVIDER;

import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceBinding;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingMerger;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.environment.servicebinding.api.exception.ServiceBindingAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationLoader;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationOptions;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/** Utility class to resolve the destination pointing to the AI Core service. */
@Slf4j
@AllArgsConstructor
class DestinationResolver {
  @Nonnull private final ServiceBindingAccessor accessor;

  DestinationResolver() {
    this(
        new ServiceBindingMerger(
            List.of(DefaultServiceBindingAccessor.getInstance(), new AiCoreServiceKeyAccessor()),
            ServiceBindingMerger.KEEP_EVERYTHING));
  }

  @SuppressWarnings("UnstableApiUsage")
  HttpDestination getDestination() {
    val binding =
        accessor.getServiceBindings().stream()
            .filter(DestinationResolver::isAiCoreService)
            .findFirst()
            .orElseThrow(
                () ->
                    new ServiceBindingAccessException(
                        "Could not find any matching service bindings for service identifier "
                            + ServiceIdentifier.AI_CORE));

    // get a destination pointing to the AI Core service
    val opts =
        ServiceBindingDestinationOptions.forService(binding)
            .onBehalfOf(TECHNICAL_USER_PROVIDER)
            .build();

    return ServiceBindingDestinationLoader.defaultLoaderChain().getDestination(opts);
  }

  private static boolean isAiCoreService(@Nonnull final ServiceBinding binding) {
    return ServiceIdentifier.AI_CORE.equals(binding.getServiceIdentifier().orElse(null));
  }
}
