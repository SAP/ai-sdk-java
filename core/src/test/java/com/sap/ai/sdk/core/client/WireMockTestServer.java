package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

/** Test server for all unit tests. */
public abstract class WireMockTestServer {
  private static final WireMockConfiguration WIREMOCK_CONFIGURATION =
      wireMockConfig().dynamicPort();

  static WireMockServer wireMockServer;
  static Destination destination;
  static ApiClient client;

  @BeforeAll
  static void setup() {
    wireMockServer = new WireMockServer(WIREMOCK_CONFIGURATION);
    wireMockServer.start();
    destination = DefaultHttpDestination.builder(wireMockServer.baseUrl()).build();
    client = new AiCoreService().withDestination(destination).client();
  }

  // Reset WireMock before each test to ensure clean state
  @AfterEach
  void reset() {
    wireMockServer.resetAll();
  }
}
