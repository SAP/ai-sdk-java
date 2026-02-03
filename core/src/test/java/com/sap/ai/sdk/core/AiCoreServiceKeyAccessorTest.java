package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sap.cloud.environment.servicebinding.api.exception.ServiceBindingAccessException;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AiCoreServiceKeyAccessorTest {

  @Test
  void testValidDotenv() {
    var dotenv = spy(Dotenv.configure().filename("valid.testenv"));

    var accessor = new AiCoreServiceKeyAccessor(dotenv);
    assertThat(accessor.getServiceBindings()).isNotEmpty().hasSize(1);
    assertThat(accessor.getServiceBindings().get(0).getCredentials())
        .containsEntry("clientid", "cid")
        .containsEntry("clientsecret", "csecret")
        .containsEntry("url", "https://authenticationsap")
        .containsEntry("serviceurls", Map.of("AI_API_URL", "https://api.ai.sap"));

    verify(dotenv, times(1)).load();
  }

  @Test
  void testMissingClientIdDotenv() {
    var dotenv = Dotenv.configure().filename("missingclientid.testenv");
    var accessor = new AiCoreServiceKeyAccessor(dotenv);
    assertThatThrownBy(accessor::getServiceBindings)
        .isInstanceOf(ServiceBindingAccessException.class)
        .hasCauseInstanceOf(AiCoreCredentialsInvalidException.class)
        .hasMessageContaining("clientid");
  }

  @Test
  void testMissingDotenv() {
    var dotenv = Dotenv.configure().directory("doesnt").filename("exist").ignoreIfMissing();
    var accessor = new AiCoreServiceKeyAccessor(dotenv);
    assertThat(accessor.getServiceBindings()).isEmpty();
  }

  @Test
  void testMalformedDotenv() {
    var dotenv = Dotenv.configure().filename("malformed.testenv");

    // Dotenv should be loaded lazily and not throw before actually loading the file
    var accessor = new AiCoreServiceKeyAccessor(dotenv);

    assertThatThrownBy(accessor::getServiceBindings)
        .isInstanceOf(ServiceBindingAccessException.class);
  }

  @Test
  void testServiceBindingCreation() {
    var serviceKeyWithoutCredentialType =
        """
        {
          "clientid": "",
          "clientsecret": ""
        }
        """;
    var binding1 = AiCoreServiceKeyAccessor.createServiceBinding(serviceKeyWithoutCredentialType);
    assertThat(binding1.getCredentials().containsKey("credential-type"))
        .describedAs("The missing 'credential-type' should have automatically been added")
        .isTrue();
    var serviceKeyWithCredentialType =
        """
        {
          "clientid": "",
          "clientsecret": "",
          "credential-type": "foo"
          }
        }
        """;
    var binding2 = AiCoreServiceKeyAccessor.createServiceBinding(serviceKeyWithCredentialType);
    assertThat(binding2.getCredentials().get("credential-type"))
        .describedAs("The 'credential-type' field should not get overwritten")
        .isEqualTo("foo");
  }
}
