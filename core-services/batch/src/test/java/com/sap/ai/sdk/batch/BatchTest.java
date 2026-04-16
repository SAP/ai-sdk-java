package com.sap.ai.sdk.batch;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.batch.generated.client.BatchesApi;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class BatchTest {
  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private static BatchesApi client;

  @BeforeEach
  void setup() {
    final HttpDestination destination = DefaultHttpDestination.builder(WM.baseUrl()).build();
    client = new BatchesApi(destination);
  }

  @Test
  public void testBatchesApi() {}
}
