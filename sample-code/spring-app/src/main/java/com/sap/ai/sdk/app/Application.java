package com.sap.ai.sdk.app;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.core.AiCoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

/** Main class to start the Spring Boot application. */
@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.sap.ai.sdk.app"})
public class Application {

  /**
   * Changes Spring Boot's default object mapper to fix serialization issues.
   *
   * @return a modified object mapper
   */
  @Bean
  @Primary
  @SuppressWarnings("unused")
  @Nonnull
  public ObjectMapper objectMapper() {
    return getDefaultObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
  }

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args Command line arguments.
   */
  public static void main(final String[] args) throws IOException {
    var ctx = SpringApplication.run(Application.class, args);
    var svc = ctx.getBean(OrchestrationService.class);
    var res = svc.speech("Eat more of these fresh french baguettes").toList();
    String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + System.currentTimeMillis() + ".pcm";
    File file = new File(path);
    try (FileOutputStream in = new FileOutputStream(file)) {
      var ttl = 0;
      for (byte[] chunk: res) {
        System.out.println("Writing bytes: " + chunk.length);
        in.write(chunk);
        ttl += chunk.length;
      }
      System.out.println("Written bytes: " + ttl);
    }

  }
}
