package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_KEY;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sap.cloud.environment.servicebinding.api.DefaultServiceBinding;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperty;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class AiCoreServiceTest {

  // setup
  private static final Map<String, Object> URLS = Map.of("AI_API_URL", "https://srv");
  private static final Map<String, Object> CREDENTIALS =
      Map.of("clientid", "id", "clientsecret", "pw", "url", "https://auth", "serviceurls", URLS);
  private static final DefaultServiceBinding BINDING =
      DefaultServiceBinding.builder()
          .copy(Map.of())
          .withServiceIdentifier(ServiceIdentifier.AI_CORE)
          .withCredentials(CREDENTIALS)
          .build();

  @AfterEach
  void tearDown() {
    DestinationResolver.setAccessor(null);
  }

  @Test
  void testLazyEvaluation() {
    // setup
    val accessor = mock(ServiceBindingAccessor.class);
    DestinationResolver.setAccessor(accessor);

    // execution without errors
    new AiCoreService();

    // verification
    verify(accessor, never()).getServiceBindings();
  }

  @Test
  void testDefaultCase() {
    // setup
    val accessor = mock(ServiceBindingAccessor.class);
    DestinationResolver.setAccessor(accessor);
    doReturn(List.of(BINDING)).when(accessor).getServiceBindings();

    // execution without errors
    val core = new AiCoreService();
    val destination = core.destination();
    val client = core.client();

    // verification
    assertThat(destination.get(DestinationProperty.URI)).contains("https://srv/v2/");
    assertThat(destination.get(DestinationProperty.AUTH_TYPE)).isEmpty();
    assertThat(destination.get(DestinationProperty.NAME)).singleElement(STRING).contains("aicore");
    assertThat(destination.get(AI_CLIENT_TYPE_KEY)).contains(AI_CLIENT_TYPE_VALUE);
    assertThat(client.getBasePath()).isEqualTo("https://srv/v2/");
    verify(accessor, times(2)).getServiceBindings();
  }

  @Test
  void testBaseDestination() {
    // setup
    DestinationResolver.setAccessor(Collections::emptyList);

    // execution without errors
    val customDestination = DefaultHttpDestination.builder("https://foo.bar").build();
    val core = new AiCoreService().withDestination(customDestination);
    val destination = core.destination();
    val client = core.client();

    // verification
    assertThat(destination.get(DestinationProperty.URI)).contains("https://foo.bar/v2/");
    assertThat(destination.get(DestinationProperty.AUTH_TYPE)).isEmpty();
    assertThat(destination.get(DestinationProperty.NAME)).isEmpty();
    assertThat(destination.get(AI_CLIENT_TYPE_KEY)).contains(AI_CLIENT_TYPE_VALUE);
    assertThat(client.getBasePath()).isEqualTo("https://foo.bar/v2/");
  }

  @Test
  void testCustomization() {
    val customService =
        new AiCoreService() {
          @Nonnull
          @Override
          protected Destination getBaseDestination() {
            return DefaultHttpDestination.builder("https://ai").build();
          }

          @Nonnull
          @Override
          protected ApiClient getApiClient(@Nonnull Destination destination) {
            return new ApiClient().setBasePath("https://fizz.buzz").setUserAgent("SAP");
          }
        };

    val customServiceForDeployment =
        customService.forDeployment("deployment").withResourceGroup("group");

    val client = customServiceForDeployment.client();
    assertThat(client.getBasePath()).isEqualTo("https://fizz.buzz");

    val destination = customServiceForDeployment.destination().asHttp();
    assertThat(destination.getUri()).hasToString("https://ai/v2/inference/deployments/deployment/");

    val resourceGroup = customService.resourceGroup;
    assertThat(resourceGroup).isEqualTo("group");
  }
}
