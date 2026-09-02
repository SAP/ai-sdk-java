package com.sap.ai.sdk.tabular;

import static com.sap.ai.sdk.tabular.generated.model.DataDestinationStatus.ACTIVE;
import static com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationGetResponse.AdapterTypeEnum.FILE;
import static com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationGetResponse.TypeEnum.HDL;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.tabular.generated.client.DataDestinationsApi;
import com.sap.ai.sdk.tabular.generated.client.ScenarioConfigurationManagerApi;
import com.sap.ai.sdk.tabular.generated.client.TabularArtifactsApi;
import com.sap.ai.sdk.tabular.generated.model.GetDataDestination;
import com.sap.ai.sdk.tabular.generated.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.model.HDLDataDestinationGetResponse;
import com.sap.ai.sdk.tabular.generated.predict.client.PredictApi;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.net.URI;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
public class TabularUnitTest {
  private static DataDestinationsApi dataDestinationClient;
  private static TabularArtifactsApi tabularArtifactsClient;
  private static ScenarioConfigurationManagerApi scenarioConfigClient;
  private static PredictApi predictClient;

  private static HttpDestination buildDestination() {
    final HttpDestination base = new AiCoreService().getBaseDestination();
    final URI rootUri = base.getUri().resolve("/v2/admin/tcr/");
    return DefaultHttpDestination.fromDestination(base).uri(rootUri).build();
  }

  static final String resourceGroup = "default";
  static final String dataDestinationName = "ai-sdk-hdl-destination";
  static final String artifactName = "product-artifact";
  static final String artifactPath = "/data/product_data_hana.parquet";
  static final String scenarioConfigName = "product-prediction-scenario";

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    val base = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    val rootUri = base.getUri().resolve("/v2/admin/tcr/");
    val destination = DefaultHttpDestination.fromDestination(base).uri(rootUri).build();
    dataDestinationClient = new DataDestinationsApi(destination);
    tabularArtifactsClient = new TabularArtifactsApi(destination);
    scenarioConfigClient = new ScenarioConfigurationManagerApi(destination);

    predictClient = new PredictApi(base);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testGetAllDataDestinations() {
    final GetDataDestinations response =
        dataDestinationClient.getAllDataDestinations(resourceGroup);
    assertThat(response.getCount()).isEqualTo(1);
    final List<GetDataDestination> resources = response.getResources();
    assertThat(resources.size()).isEqualTo(1);
    final HDLDataDestinationGetResponse dataDestination =
        (HDLDataDestinationGetResponse) resources.get(0);
    assertThat(dataDestination.getName()).isEqualTo("ai-sdk-hdl-destination");
    assertThat(dataDestination.getType()).isEqualTo(HDL);
    assertThat(dataDestination.getDescription()).isEqualTo("HDL data destination for AI Core SDK");
    assertThat(dataDestination.getAdapterType()).isEqualTo(FILE);
    assertThat(dataDestination.getCreatedAt()).isEqualTo("2026-05-19T11:15:40.062350Z");
    assertThat(dataDestination.getUpdatedAt()).isEqualTo("2026-08-24T08:25:35.730000Z");
    assertThat(dataDestination.getLabels()).isEmpty();
    assertThat(dataDestination.getStatus()).isEqualTo(ACTIVE);
  }
}
