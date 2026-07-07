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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
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
  public static void main(final String[] args) throws Exception {
    var ctx = SpringApplication.run(Application.class, args);
    var svc = ctx.getBean(OrchestrationService.class);
    var outputBytes = new ArrayList<byte[]>();
    var done = new AtomicBoolean(false);
    try (var channel = svc.textToSpeech((bytes, last) -> {
      System.err.println("appended output bytes: " + bytes.length);
      outputBytes.add(bytes);
      if (last) {
        done.set(true);
      }
    })) {
      System.err.println("BEFORE SENDING TEXT");
      channel.sendText("Eat more of these fresh french baguettes and drink the tea!");
      System.err.println("AFTER SENDING TEXT");
      long timeout = System.currentTimeMillis() + 20000;
      while (!done.get() && System.currentTimeMillis() < timeout) {
        Thread.yield();
        System.err.println("Waiting for messages...");
        Thread.sleep(500);
      }
    }

    //var res = svc.textToSpeech("Eat more of these fresh french baguettes").toList();
    String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + System.currentTimeMillis() + ".pcm";
    File file = new File(path);
    try (FileOutputStream in = new FileOutputStream(file)) {
      var ttl = 0;
      for (byte[] chunk: outputBytes) {
        System.out.println("Writing bytes: " + chunk.length);
        in.write(chunk);
        ttl += chunk.length;
      }
      System.out.println("Written bytes: " + ttl);
    }

  }
}
