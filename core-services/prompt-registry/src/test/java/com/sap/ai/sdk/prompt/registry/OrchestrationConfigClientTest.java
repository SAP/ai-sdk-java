package com.sap.ai.sdk.prompt.registry;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptRegistryOrchestrationConfig;
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
  void testListOrchestrationConfigs() {
    final var result = client.listOrchestrationConfigs();
    assertThat(result.getCount()).isEqualTo(2);
    assertThat(result.getResources()).hasSize(2);
    final var first = result.getResources().get(0);
    assertThat(first.getId()).isEqualTo(UUID.fromString("62e8638a-ae87-4bd5-9027-a0bc67db1609"));
    assertThat(first.getName()).isEqualTo("test-config-for-OrchestrationTest");
    assertThat(first.getVersion()).isEqualTo("0.0.1");
    assertThat(first.getScenario()).isEqualTo("sdk-test-scenario");
    assertThat(first.getCreationTimestamp()).isEqualTo("2025-12-19T16:24:27.442000");
    assertThat(first.getManagedBy()).isEqualTo("imperative");
    assertThat(first.isIsVersionHead()).isTrue();
    final var second = result.getResources().get(1);
    assertThat(second.getId()).isEqualTo(UUID.fromString("f9f2875a-4c92-471b-a403-51a50e70fe52"));
    assertThat(second.getName()).isEqualTo("test-config");
  }

  @Test
  void testCreateUpdateOrchestrationConfig() {
    final var request =
        OrchestrationConfigPostRequest.create()
            .name("test-config-for-OrchestrationTest")
            .version("0.0.1")
            .scenario("sdk-test-scenario")
            .spec(PromptRegistryOrchestrationConfig.create());

    final var result = client.createUpdateOrchestrationConfig(request);

    assertThat(result.getMessage()).isEqualTo("Orchestration config created successfully");
    assertThat(result.getId()).isEqualTo(UUID.fromString("62e8638a-ae87-4bd5-9027-a0bc67db1609"));
    assertThat(result.getScenario()).isEqualTo("sdk-test-scenario");
    assertThat(result.getName()).isEqualTo("test-config-for-OrchestrationTest");
    assertThat(result.getVersion()).isEqualTo("0.0.1");
  }

  @Test
  void testDeleteOrchestrationConfig() {
    final var id = UUID.fromString("62e8638a-ae87-4bd5-9027-a0bc67db1609");

    final var result = client.deleteOrchestrationConfig(id);

    assertThat(result.getMessage()).isEqualTo("Orchestration config deleted successfully");
  }
}
