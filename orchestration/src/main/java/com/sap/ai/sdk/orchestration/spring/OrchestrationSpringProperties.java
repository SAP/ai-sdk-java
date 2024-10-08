package com.sap.ai.sdk.orchestration.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO can this be a static inner class?
@ConfigurationProperties(prefix = OrchestrationSpringProperties.CONFIG_PREFIX)
public record OrchestrationSpringProperties(Llm llm) {
  public static final String CONFIG_PREFIX = "com.sap.ai.sdk.orchestration";

  public record Llm(String modelName, String modelVersion) {}
}
