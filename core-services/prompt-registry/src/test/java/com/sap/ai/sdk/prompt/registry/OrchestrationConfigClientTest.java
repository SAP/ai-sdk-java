package com.sap.ai.sdk.prompt.registry;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class OrchestrationConfigClientTest {
  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private static OrchestrationConfigClient client;

  @BeforeEach
  void setup() {
    final HttpDestination destination = DefaultHttpDestination.builder(WM.baseUrl()).build();
    final AiCoreService service = new AiCoreService().withBaseDestination(destination);
    client = new OrchestrationConfigClient(service);
  }

  @Test
  void testPipelines() {
    final var result = client.listOrchestrationConfigs();
    assertThat(result.getCount()).isEqualTo(2);
    assertThat(result.getResources()).hasSize(2);
    final var template = result.getResources().get(0);
    assertThat(template.getId()).isEqualTo(UUID.fromString("62e8638a-ae87-4bd5-9027-a0bc67db1609"));
    assertThat(template.getName()).isEqualTo("test-config-for-OrchestrationTest");
    assertThat(template.getVersion()).isEqualTo("0.0.1");
    assertThat(template.getScenario()).isEqualTo("sdk-test-scenario");
    assertThat(template.getCreationTimestamp()).isEqualTo("2025-12-19T16:24:27.442000");
    assertThat(template.getManagedBy()).isEqualTo("imperative");
    assertThat(template.isIsVersionHead()).isEqualTo(true);
  }
}
