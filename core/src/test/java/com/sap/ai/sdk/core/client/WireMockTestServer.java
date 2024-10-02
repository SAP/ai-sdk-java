package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

/** Test server for all unit tests. */
abstract class WireMockTestServer {
  private static final WireMockConfiguration WIREMOCK_CONFIGURATION =
      wireMockConfig().dynamicPort();

  static WireMockServer wireMockServer;
  static Destination destination;

  @BeforeAll
  static void setup() {
    wireMockServer = new WireMockServer(WIREMOCK_CONFIGURATION);
    wireMockServer.start();
    destination = DefaultHttpDestination.builder(wireMockServer.baseUrl()).build();
  }

  // Reset WireMock before each test to ensure clean state
  @AfterEach
  void reset() {
    wireMockServer.resetAll();
  }
}
