package com.sap.ai.sdk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
class Application {
  /**
   * Main method to start the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
