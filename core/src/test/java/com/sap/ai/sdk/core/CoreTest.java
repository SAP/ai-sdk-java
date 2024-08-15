package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.Core.getDestination;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class CoreTest {

  @Test
  @SneakyThrows
  void getDestinationWithoutEnvVarFailsLocally() {
    assertThatThrownBy(() -> getDestination(null))
        .isExactlyInstanceOf(DestinationAccessException.class)
        .hasMessage("Could not find any matching service bindings for service identifier 'aicore'");
  }

  @Test
  @SneakyThrows
  void getDestinationWithBrokenEnvVarFailsLocally() {
    assertThatThrownBy(() -> getDestination(""))
        .isExactlyInstanceOf(Core.AiCoreCredentialsInvalidException.class)
        .hasMessage(
            "Error in parsing service key from the \"AICORE_SERVICE_KEY\" environment variable.");
  }

  @Test
  @SneakyThrows
  void getDestinationWithEnvVarSucceedsLocally() {
    final String AICORE_SERVICE_KEY =
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
        """;
    assertThat(getDestination(AICORE_SERVICE_KEY).asHttp().getUri().toString())
        .isEqualTo("https://api.ai.core/v2");
  }
}
