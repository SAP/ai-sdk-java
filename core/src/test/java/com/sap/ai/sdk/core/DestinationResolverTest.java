package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.exception.ServiceBindingAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;

class DestinationResolverTest {

  @Test
  void testGetDestinationFromBinding() {
    val serviceKey =
        """
        {
          "clientid": "",
          "clientsecret": "",
          "url": "",
          "serviceurls": {
            "AI_API_URL": "https://api.ai.core"
          }
        }
        """;
    var binding = AiCoreServiceKeyAccessor.createServiceBinding(serviceKey);
    var resolver = new DestinationResolver(() -> List.of(binding));
    var result = resolver.getDestination();
    assertThat(result.getUri())
        .describedAs("The destination should already contain the /v2 base path")
        .hasToString("https://api.ai.core/v2/");
    assertThat(result.get("URL.headers.AI-Client-Type"))
        .describedAs("The destination must contain the AI-Client-Type header")
        .containsExactly("AI SDK Java");
  }

  @Test
  void testNoServiceBindingFound() {
    var resolver = new DestinationResolver();
    assertThatThrownBy(resolver::getDestination)
        .isExactlyInstanceOf(ServiceBindingAccessException.class)
        .hasMessageContaining("Could not find any matching service bindings");
  }

  @Test
  void testNoServiceBindingLoadingThrows() {
    var mock = mock(ServiceBindingAccessor.class);
    var exception = new ServiceBindingAccessException("Invalid AI Core service key");
    doThrow(exception).when(mock).getServiceBindings();

    var resolver = new DestinationResolver(mock);
    assertThatThrownBy(resolver::getDestination).isSameAs(exception);
  }

  private record DestinationTestCase(String givenBasePath, String expectedBasePath) {}

  @Test
  void testFromCustomBaseDestination() {
    var testCases =
        new DestinationTestCase[] {
          new DestinationTestCase("", "/v2/"),
          new DestinationTestCase("/", "/v2/"),
          new DestinationTestCase("/foo", "/foo/"),
          new DestinationTestCase("/foo/", "/foo/")
        };
    for (var testCase : testCases) {
      var url = "https://api.ai.sap" + testCase.givenBasePath;
      var destination = DefaultHttpDestination.builder(url).build();
      assertThat(DestinationResolver.fromCustomBaseDestination(destination).getUri().getPath())
          .describedAs(
              "Expecting given base path %s to resolve to %s",
              testCase.givenBasePath, testCase.expectedBasePath)
          .hasToString(testCase.expectedBasePath);
    }
  }
}
