package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.LlmConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO can this be a static inner class?
@ConfigurationProperties(prefix = OrchestrationSpringProperties.CONFIG_PREFIX)
public record OrchestrationSpringProperties(LlmConfig llm) {
  public static final String CONFIG_PREFIX = "com.sap.ai.sdk.orchestration";
}
