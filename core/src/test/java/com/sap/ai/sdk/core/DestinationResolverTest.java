package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

class DestinationResolverTest {

  @Test
  void getDestinationWithoutEnvVarFailsLocally() {
    assertThatThrownBy(() -> DestinationResolver.getDestination(null))
        .isExactlyInstanceOf(DestinationAccessException.class)
        .hasMessage("Could not find any matching service bindings for service identifier 'aicore'");
  }

  @Test
  void getDestinationWithBrokenEnvVarFailsLocally() {
    assertThatThrownBy(() -> DestinationResolver.getDestination(""))
        .isExactlyInstanceOf(DestinationResolver.AiCoreCredentialsInvalidException.class)
        .hasMessage(
            "Error in parsing service key from the \"AICORE_SERVICE_KEY\" environment variable.");
  }

  @Test
  void getDestinationWithEnvVarSucceedsLocally() {
    val serviceKey =
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
    var result = DestinationResolver.getDestination(serviceKey).asHttp();
    assertThat(result.getUri()).hasToString("https://api.ai.core");
  }
}
