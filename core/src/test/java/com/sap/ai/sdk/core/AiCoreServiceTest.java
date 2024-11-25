package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiCoreServiceTest {
  private static final HttpDestination serviceBindingDestination =
      DefaultHttpDestination.builder("https://api.ai.com").build();

  private DestinationResolver resolver;
  private AiCoreService service;

  @BeforeEach
  void setUp() {
    resolver = mock(DestinationResolver.class);
    doReturn(serviceBindingDestination).when(resolver).getDestination();

    service = new AiCoreService(resolver);
  }

  @Test
  void testLazyDestinationLoading() {
    doThrow(new DestinationAccessException()).when(resolver).getDestination();

    assertThatCode(() -> new AiCoreService(resolver))
        .describedAs("This must not perform any destination loading upon initialization")
        .doesNotThrowAnyException();

    verify(resolver, never()).getDestination();
  }

  @Test
  void testBaseDestination() {
    assertThat(service.getBaseDestination()).isEqualTo(serviceBindingDestination);
  }

  @Test
  void testGetDestination() {
    var destination = service.destination();
    assertThat(destination).isNotEqualTo(serviceBindingDestination);
    assertThat(destination.getUri()).hasHost("api.ai.com").hasPath("/v2/");
    assertThat(destination.getHeaders())
        .containsExactly(new Header("AI-Client-Type", "AI SDK Java"));
  }

  @Test
  void testDeploymentDestination() {
    var destination = service.forDeployment("123").withResourceGroup("foo").destination();

    assertThat(destination.getUri())
        .hasHost("api.ai.com")
        .hasPath("/v2/inference/deployments/123/");
    assertThat(destination.getHeaders())
        .containsExactly(
            new Header("AI-Client-Type", "AI SDK Java"), new Header("AI-Resource-Group", "foo"));

    verify(resolver, times(1)).getDestination();
  }

  @Test
  void testBuildApiClient() {
    var apiClient = service.client();

    assertThat(apiClient.getBasePath()).hasToString("https://api.ai.com/v2/");

    verify(resolver, times(1)).getDestination();
  }

  @Test
  void testCustomization() {
    val customService =
        new AiCoreService() {
          @Nonnull
          @Override
          protected HttpDestination getBaseDestination() {
            return DefaultHttpDestination.builder("https://ai").build();
          }

          @Nonnull
          @Override
          protected ApiClient buildApiClient(@Nonnull Destination destination) {
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
    assertThat(destination.getHeaders()).contains(new Header("AI-Resource-Group", "group"));
  }
}
