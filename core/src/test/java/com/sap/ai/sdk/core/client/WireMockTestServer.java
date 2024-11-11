package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

/** Test server for all unit tests. */
public abstract class WireMockTestServer {
  private static final WireMockConfiguration WIREMOCK_CONFIGURATION =
      wireMockConfig().dynamicPort();

  public static WireMockServer wireMockServer;
  public static AiCoreService aiCoreService;

  @BeforeAll
  static void setup() {
    wireMockServer = new WireMockServer(WIREMOCK_CONFIGURATION);
    wireMockServer.start();

    val destination = DefaultHttpDestination.builder(wireMockServer.baseUrl()).build();
    aiCoreService = new AiCoreService().withDestination(destination);
  }

  // Reset WireMock before each test to ensure clean state
  @AfterEach
  void reset() {
    wireMockServer.resetAll();
  }
}
