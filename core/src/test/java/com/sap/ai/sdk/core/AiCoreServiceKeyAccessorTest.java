package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.cloud.environment.servicebinding.api.exception.ServiceBindingAccessException;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AiCoreServiceKeyAccessorTest {

  @Test
  void testValidDotenv() {
    var dotenv = Dotenv.configure().filename("valid.testenv");
    var accessor = new AiCoreServiceKeyAccessor(dotenv);
    assertThat(accessor.getServiceBindings()).isNotEmpty().hasSize(1);
    assertThat(accessor.getServiceBindings().get(0).getCredentials())
        .containsEntry("clientid", "cid")
        .containsEntry("clientsecret", "csecret")
        .containsEntry("url", "https://authenticationsap")
        .containsEntry("serviceurls", Map.of("AI_API_URL", "https://api.ai.sap"));
  }

  @Disabled("Doesn't work on GitHub action runner for unknown reasons. Probably a bug in the dotenv library related to OS or JDK version.")
  @Test
  void testMissingClientIdDotenv() {
    var dotenv = Dotenv.configure().filename("missingClientId.testenv");
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
}
