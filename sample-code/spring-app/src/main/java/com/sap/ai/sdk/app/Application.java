package com.sap.ai.sdk.app;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/** Main class to start the Spring Boot application. */
@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
public class Application {
  /** The API client connected to the AI Core service. */
  public static final ApiClient API_CLIENT = new AiCoreService().client();

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
