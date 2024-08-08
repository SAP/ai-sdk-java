package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
public class CoreTest {

  @SystemStub private final EnvironmentVariables variables = new EnvironmentVariables();

  @Test
  @SneakyThrows
  void getDestinationWithoutEnvVarFailsLocally() {
    variables.set("aicore", null);
    assertThatThrownBy(Core::getDestination)
        .isExactlyInstanceOf(DestinationAccessException.class)
        .hasMessage("Could not find any matching service bindings for service identifier 'aicore'");
  }

  @Test
  @SneakyThrows
  void getDestinationWithBrokenEnvVarFailsLocally() {
    variables.set("aicore", "");
    assertThatThrownBy(Core::getDestination)
        .isExactlyInstanceOf(Core.AiCoreCredentialsInvalidException.class)
        .hasMessage("Error in parsing service key from the \"aicore\" environment variable.");
  }

  @Test
  @SneakyThrows
  void getDestinationWithEnvVarSucceedsLocally() {
    variables.set(
        "aicore",
        """
        {
          "clientid": "",
          "clientsecret": "",
          "url": "",
          "identityzone": "",
          "identityzoneid": "",
          "appname": "",
          "serviceurls": {
            "AI_API_URL": "https://api.ai.core"
          }
        }
        """);
    assertThat(Core.getDestination().asHttp().getUri().toString())
        .isEqualTo("https://api.ai.core/v2");
  }
}
