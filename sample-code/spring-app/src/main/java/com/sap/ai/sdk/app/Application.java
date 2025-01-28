package com.sap.ai.sdk.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration;
import javax.annotation.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

/** Main class to start the Spring Boot application. */
@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
public class Application {

  /**
   * Temporary workaround to fix the issue with the Orchestration spec.
   *
   * @see OrchestrationJacksonConfiguration#getOrchestrationObjectMapper()
   * @return a modified object mapper that works for Orchestration.
   */
  @Bean
  @Primary
  @SuppressWarnings("unused")
  @Nonnull
  public ObjectMapper objectMapper() {
    return OrchestrationJacksonConfiguration.getOrchestrationObjectMapper();
  }

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
