package com.sap.ai.sdk.app;

import jakarta.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
class Application extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * This exists only to import ServletException. This import exists only to justify the dependency
   * jetty-jakarta-servlet-api for the maven-dependency-plugin to succeed.
   */
  @Override
  public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
  }
}
