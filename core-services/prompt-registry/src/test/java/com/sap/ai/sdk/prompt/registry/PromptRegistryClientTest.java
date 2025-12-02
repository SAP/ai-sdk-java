package com.sap.ai.sdk.prompt.registry;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateGetResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class PromptRegistryClientTest {
  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private final HttpDestination DESTINATION = DefaultHttpDestination.builder(WM.baseUrl()).build();
  private final AiCoreService SERVICE = new AiCoreService().withBaseDestination(DESTINATION);

  @Test
  void testPipelines() {
    var client = new PromptClient(SERVICE);
    var result = client.listPromptTemplates();
    assertThat(result.getCount()).isEqualTo(2);
    assertThat(result.getResources()).hasSize(2);
    PromptTemplateGetResponse template = result.getResources().get(0);
    assertThat(template.getId()).isEqualTo(UUID.fromString("312a9b9c-a532-4c1c-8852-bf75de887d74"));
    assertThat(template.getName()).isEqualTo("prompt_template_name");
    assertThat(template.getVersion()).isEqualTo("1.0.0");
    assertThat(template.getScenario()).isEqualTo("MyScenario");
    assertThat(template.getCreationTimestamp()).isEqualTo("2025-02-26T12:29:55.875000");
    assertThat(template.getManagedBy()).isEqualTo("imperative");
    assertThat(template.isIsVersionHead()).isEqualTo(true);
    assertThat(template.getSpec()).isNull();
  }
  
  
}
