package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import java.util.function.Supplier;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiCoreServiceTest {
  private static final HttpDestination baseDestination =
      DefaultHttpDestination.builder("https://api.ai.com/v2/").build();

  private Supplier<HttpDestination> destinationResolver;
  private AiCoreService service;

  @BeforeEach
  void setUp() {
    destinationResolver = () -> baseDestination;

    service = new AiCoreService(destinationResolver);
  }

  @Test
  void testLazyDestinationLoading() {
    destinationResolver =
        () -> {
          throw new DestinationAccessException("Test");
        };

    assertThatCode(() -> new AiCoreService(destinationResolver))
        .describedAs("This must not perform any destination loading upon initialization")
        .doesNotThrowAnyException();
  }

  @Test
  void testDefaultBaseDestination() {
    assertThat(service.getBaseDestination())
        .describedAs(
            "By default the destination obtained from the destination resolver should be used as-is")
        .isEqualTo(baseDestination);
  }

  @Test
  void testCustomBaseDestination() {
    val customDestination =
        DefaultHttpDestination.builder("https://custom.ai.com/custom/base/path").build();
    val customService = service.withBaseDestination(customDestination);

    assertThat(customService.getBaseDestination().getUri())
        .hasHost("custom.ai.com")
        .hasPath("/custom/base/path/");
    assertThat(customService.getBaseDestination().getHeaders())
        .describedAs("The client type should be added to ensure its present in all requests")
        .containsExactly(new Header("AI-Client-Type", "AI SDK Java"));
  }

  @Test
  void testGetInferenceDestination() {
    var builder = service.getInferenceDestination();
    assertThatThrownBy(() -> builder.forScenario("doesn't exist"))
        .isExactlyInstanceOf(DeploymentResolutionException.class);

    val destination = service.getInferenceDestination("foo").usingDeploymentId("123");

    assertThat(destination.getUri())
        .hasHost("api.ai.com")
        .hasPath("/v2/inference/deployments/123/");

    assertThat(destination.getHeaders()).containsExactly(new Header("AI-Resource-Group", "foo"));
  }

  @Test
  void testBuildApiClient() {
    var apiClient = service.getApiClient();

    assertThat(apiClient.getBasePath()).hasToString("https://api.ai.com/v2/");
  }
}
